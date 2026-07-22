package com.mib.simpilist.controller;

import com.mib.simpilist.dto.Category.CategoryDto;
import com.mib.simpilist.dto.Category.CategoryFilterRequest;
import com.mib.simpilist.dto.Utility.PageResponse;
import com.mib.simpilist.service.CategoryService;
import com.mib.simpilist.utililty.Utilities;
import com.mib.simpilist.utililty.factory.CategoryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController()
@Slf4j(topic = "CategoryController")
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public PageResponse<CategoryDto> getAllCategories(CategoryFilterRequest categoryFilterRequest) {
        return Utilities.mapPageToPageResponse(
                categoryService.getAllCategories(categoryFilterRequest),
                CategoryFactory::buildCategoryDto);
    }

    @PostMapping
    public CategoryDto addCategory(@RequestBody CategoryDto categoryDto) {
        return CategoryFactory.buildCategoryDto(categoryService.addCategory(categoryDto));
    }

    @PatchMapping("/{id}")
    public CategoryDto modifyCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        return CategoryFactory.buildCategoryDto(categoryService.modifiyCategory(id, categoryDto));
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.removeCategory(id);
    }

}
