package com.mib.simpilist.service;

import com.mib.simpilist.Specification.GroupSpecification;
import com.mib.simpilist.annotation.RollbackTransaction;
import com.mib.simpilist.dto.Group.GroupDto;
import com.mib.simpilist.dto.Group.GroupFilterRequest;
import com.mib.simpilist.exception.ResourceNotFoundException;
import com.mib.simpilist.model.Category;
import com.mib.simpilist.model.Group;
import com.mib.simpilist.repository.GroupRepo;
import com.mib.simpilist.utililty.Security.UserContext;
import com.mib.simpilist.utililty.factory.GroupFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "GroupService")
public class GroupService {
    private final GroupRepo groupRepo;
    private final CategoryService categoryService;
    private final UserService userService;

    public GroupService(GroupRepo groupRepo, CategoryService categoryService, UserService userService) {
        this.groupRepo = groupRepo;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    public Page<Group> getGroups(GroupFilterRequest groupFilterRequest){
        return groupRepo.findAll(buildSpecification(groupFilterRequest), groupFilterRequest.getPageable());
    }

    private Specification<Group> buildSpecification(GroupFilterRequest groupFilterRequest){
        return Specification.allOf(
                GroupSpecification.belongsToCurrentUser()
        );
    }

    public Group addGroup(GroupDto groupDto) {
        return save(GroupFactory.buildGroup(groupDto,
                categoryService.findById(groupDto.getCategory().getId()),
                userService.getCurrentUser())
        );
    }


    public Group save(Group group){
        log.info("saving list group: {}", group.toString());
        return groupRepo.save(group);
    }

    public Group findById(Long id){
       return groupRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Not Found for id:"+id)
        );
    }

    public Group findByIdAndCurrentUserId(Long id){
        //todo add log error
        return groupRepo.findByIdAndUser_Id(id,
                UserContext.getCurrentUser().id()).orElseThrow(
                ()-> new ResourceNotFoundException("Not Found for id:"+id)
        );
    }


    public Group modifyGroup(Long id, GroupDto groupDto) {
        Group group =findByIdAndCurrentUserId(id);
        handleGroupFieldUpdate(group, groupDto);
        return save(group);
    }

    private void handleGroupFieldUpdate(Group group, GroupDto groupDto) {
        if(!groupDto.getGroupName().equals(group.getGroupName())){
            group.setGroupName(groupDto.getGroupName());
        }

        if(!groupDto.getPriority().equals(group.getPriority())){
         group.setPriority(groupDto.getPriority());
        }

        if(!group.getCategory().getId().equals(groupDto.getCategory().getId())){
            Category category = categoryService.findByIdAndCurrentUserId(groupDto.getCategory().getId());
            group.setCategory(category);
        }
    }

    @RollbackTransaction
    public void removeGroup(Long id){
        delete(findByIdAndCurrentUserId(id));
    }

    @RollbackTransaction
    public void delete(Group group){
        groupRepo.delete(group);
    }

}
