package com.mib.simpilist.service;

import com.mib.simpilist.Specification.GroupCategorySpecification;
import com.mib.simpilist.annotation.RollbackTransaction;
import com.mib.simpilist.dto.GroupCategory.GroupCategoryDto;
import com.mib.simpilist.dto.GroupCategory.GroupCategoryFilterRequest;
import com.mib.simpilist.exception.ResourceNotFoundException;
import com.mib.simpilist.model.GroupCategory;
import com.mib.simpilist.repository.GroupCategoryRepo;
import com.mib.simpilist.utililty.Security.UserContext;
import com.mib.simpilist.utililty.factory.GroupCategoryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j(topic = "GroupCategoryService")
@Service
public class GroupCategoryService {
    private final GroupCategoryRepo groupCategoryRepo;
    private final UserService userService;

    public GroupCategoryService(GroupCategoryRepo groupCategoryRepo, UserService userService) {
        this.groupCategoryRepo = groupCategoryRepo;
        this.userService = userService;
    }

    public Page<GroupCategory> getAllGroupCategories(GroupCategoryFilterRequest groupCategoryFilterRequest){
        return groupCategoryRepo.findAll(buildSpecification(groupCategoryFilterRequest),
                groupCategoryFilterRequest.getPageable());
    }

    private Specification<GroupCategory> buildSpecification(GroupCategoryFilterRequest groupCategoryFilterRequest){
        return Specification.allOf(GroupCategorySpecification.belongsToCurrentUser());
    }

    public GroupCategory findById(Long id){
        return groupCategoryRepo.findById(id).orElseThrow(()->{
            log.error("GroupCategory with id:{} does not exists",id);
            return new ResourceNotFoundException("not found");
        });
    }

    public GroupCategory findByIdAndCurrentUserId(Long id){
        return groupCategoryRepo.findByIdAndUser_Id(id,
                UserContext.getCurrentUser().id()).orElseThrow(()->{
            log.error("GroupCategory with id:{} does not exists for user id:{}",id,UserContext.getCurrentUser().id());
            return new ResourceNotFoundException("not found");
        });
    }

    public GroupCategory addGroupCategory(GroupCategoryDto groupCategoryDto) {
            return save(GroupCategoryFactory.buildGroupCategory(groupCategoryDto,
                    userService.getCurrentUser()));
    }

    public GroupCategory save(GroupCategory groupCategory){
        log.info("saving list group: {}",groupCategory.toString());
        return groupCategoryRepo.save(groupCategory);
    }

    @RollbackTransaction
    public GroupCategory modifiyGroupCategory(Long id,GroupCategoryDto groupCategoryDto) {
        GroupCategory category = findByIdAndCurrentUserId(id);
        handleGroupCategoryFieldUpdate(category, groupCategoryDto);
        return save(category);
    }

    private void handleGroupCategoryFieldUpdate(GroupCategory category, GroupCategoryDto groupCategoryDto) {
        if(!groupCategoryDto.getCategoryName().equals(category.getCategoryName())){
            category.setCategoryName(groupCategoryDto.getCategoryName());
        }

        if(!groupCategoryDto.getPriority().equals(category.getPriority())){
            category.setPriority(groupCategoryDto.getPriority());
        }
    }

    @RollbackTransaction
    public void removeListGroup(Long id) {
        delete(findByIdAndCurrentUserId(id));
    }

    @RollbackTransaction
    public void delete(GroupCategory groupCategory){
        groupCategoryRepo.delete(groupCategory);
    }
}
