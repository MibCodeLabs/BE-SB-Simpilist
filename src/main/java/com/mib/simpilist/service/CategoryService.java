package com.mib.simpilist.service;

import com.mib.simpilist.Specification.CategorySpecification;
import com.mib.simpilist.annotation.RollbackTransaction;
import com.mib.simpilist.dto.Category.CategoryDto;
import com.mib.simpilist.dto.Category.CategoryFilterRequest;
import com.mib.simpilist.exception.ClientException;
import com.mib.simpilist.exception.ResourceNotFoundException;
import com.mib.simpilist.model.Category;
import com.mib.simpilist.repository.CategoryRepo;
import com.mib.simpilist.repository.GroupRepo;
import com.mib.simpilist.utililty.Security.UserContext;
import com.mib.simpilist.utililty.factory.CategoryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j(topic = "CategoryService")
@Service
public class CategoryService {

    private final CategoryRepo categoryRepo;
    private final UserService userService;
    private final GroupRepo groupRepo;

    public CategoryService(CategoryRepo categoryRepo, UserService userService, GroupRepo groupRepo) {
        this.categoryRepo = categoryRepo;
        this.userService = userService;
        this.groupRepo = groupRepo;
    }

    public Page<Category> getAllCategories(CategoryFilterRequest categoryFilterRequest) {
        return categoryRepo.findAll(buildSpecification(categoryFilterRequest),
                categoryFilterRequest.getPageable());
    }

    private Specification<Category> buildSpecification(CategoryFilterRequest categoryFilterRequest) {
        return Specification.allOf(CategorySpecification.belongsToCurrentUser());
    }

    public Category findById(Long id) {
        return categoryRepo.findById(id).orElseThrow(() -> {
            log.error("Category with id:{} does not exists", id);
            return new ResourceNotFoundException("not found");
        });
    }

    public Category findByIdAndCurrentUserId(Long id) {
        return categoryRepo.findByIdAndUser_Id(id,
                UserContext.getCurrentUser().id()).orElseThrow(() -> {
            log.error("Category with id:{} does not exists for user id:{}", id, UserContext.getCurrentUser().id());
            return new ResourceNotFoundException("not found");
        });
    }

    public Category addCategory(CategoryDto categoryDto) {
        return save(CategoryFactory.buildCategory(categoryDto,
                userService.getCurrentUser()));
    }

    public Category save(Category category) {
        log.info("saving list group: {}", category.toString());
        return categoryRepo.save(category);
    }

    @RollbackTransaction
    public Category modifiyCategory(Long id, CategoryDto categoryDto) {
        Category category = findByIdAndCurrentUserId(id);
        handleCategoryFieldUpdate(category, categoryDto);
        return save(category);
    }

    private void handleCategoryFieldUpdate(Category category, CategoryDto categoryDto) {
        if (!categoryDto.getCategoryName().equals(category.getCategoryName())) {
            category.setCategoryName(categoryDto.getCategoryName());
        }

        if (!categoryDto.getPriority().equals(category.getPriority())) {
            category.setPriority(categoryDto.getPriority());
        }
    }

    @RollbackTransaction
    public void removeCategory(Long id) {
        Category category = findByIdAndCurrentUserId(id);
        validateBeforeDelete(category);
        delete(category);
    }

    private void validateBeforeDelete(Category category) {
        if (groupRepo.existsByCategory_Id((category.getId()))) {
            log.error("Cannot delete category with id:{} as it is associated with group", category.getId());
            throw new ClientException("Cannot delete this category as it is associated with Group(s)");
        }
    }

    @RollbackTransaction
    public void delete(Category category) {
        categoryRepo.delete(category);
    }
}
