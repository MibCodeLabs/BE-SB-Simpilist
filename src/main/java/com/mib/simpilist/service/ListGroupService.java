package com.mib.simpilist.service;

import com.mib.simpilist.Specification.ListGroupSpecification;
import com.mib.simpilist.annotation.RollbackTransaction;
import com.mib.simpilist.dto.ListGroup.ListGroupDto;
import com.mib.simpilist.dto.ListGroup.ListGroupFilterRequest;
import com.mib.simpilist.exception.ResourceNotFoundException;
import com.mib.simpilist.model.GroupCategory;
import com.mib.simpilist.model.ListGroup;
import com.mib.simpilist.repository.ListGroupsRepo;
import com.mib.simpilist.utililty.Security.UserContext;
import com.mib.simpilist.utililty.factory.ListGroupFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "ListGroupService")
public class ListGroupService {
    private final ListGroupsRepo listGroupsRepo;
    private final GroupCategoryService groupCategoryService;
    private final UserService userService;

    public ListGroupService(ListGroupsRepo listGroupsRepo, GroupCategoryService groupCategoryService, UserService userService) {
        this.listGroupsRepo = listGroupsRepo;
        this.groupCategoryService = groupCategoryService;
        this.userService = userService;
    }

    public Page<ListGroup> getListGroups(ListGroupFilterRequest listGroupFilterRequest){
        return listGroupsRepo.findAll(buildSpecification(listGroupFilterRequest),listGroupFilterRequest.getPageable());
    }

    private Specification<ListGroup> buildSpecification(ListGroupFilterRequest listGroupFilterRequest){
        return Specification.allOf(
                ListGroupSpecification.belongsToCurrentUser()
        );
    }

    public ListGroup addListGroup(ListGroupDto listGroupDto) {
        return save(ListGroupFactory.buildListGroup(listGroupDto,
                groupCategoryService.findById(listGroupDto.getGroupCategory().getId()),
                userService.getCurrentUser())
        );
    }


    public ListGroup save(ListGroup listGroup){
        log.info("saving list group: {}",listGroup.toString());
        return listGroupsRepo.save(listGroup);
    }

    public ListGroup findById(Long id){
       return listGroupsRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Not Found for id:"+id)
        );
    }

    public ListGroup findByIdAndCurrentUserId(Long id){
        //todo add log error
        return listGroupsRepo.findByIdAndUser_Id(id,
                UserContext.getCurrentUser().id()).orElseThrow(
                ()-> new ResourceNotFoundException("Not Found for id:"+id)
        );
    }


    public ListGroup modifyListGroup(Long id,ListGroupDto listGroupDto) {
        ListGroup listGroup=findByIdAndCurrentUserId(id);
        handleGroupFieldUpdate(listGroup,listGroupDto);
        return save(listGroup);
    }

    private void handleGroupFieldUpdate(ListGroup listGroup, ListGroupDto listGroupDto) {
        if(!listGroupDto.getGroupName().equals(listGroup.getGroupName())){
            listGroup.setGroupName(listGroupDto.getGroupName());
        }

        if(!listGroupDto.getPriority().equals(listGroup.getPriority())){
         listGroup.setPriority(listGroupDto.getPriority());
        }

        if(!listGroup.getGroupCategory().getId().equals(listGroupDto.getGroupCategory().getId())){
            GroupCategory groupCategory=groupCategoryService.findByIdAndCurrentUserId(listGroupDto.getGroupCategory().getId());
            listGroup.setGroupCategory(groupCategory);
        }
    }

    @RollbackTransaction
    public void removeListGroup(Long id){
        delete(findByIdAndCurrentUserId(id));
    }

    @RollbackTransaction
    public void delete(ListGroup group){
        listGroupsRepo.delete(group);
    }

}
