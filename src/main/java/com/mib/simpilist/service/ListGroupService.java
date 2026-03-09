package com.mib.simpilist.service;

import com.mib.simpilist.dto.ListGroup.ListGroupDto;
import com.mib.simpilist.dto.ListGroup.ListGroupFilterRequest;
import com.mib.simpilist.exception.ResourceNotFoundException;
import com.mib.simpilist.model.ListGroup;
import com.mib.simpilist.repository.ListGroupsRepo;
import com.mib.simpilist.utililty.factory.ListGroupFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
        //TODO add specification stuff for this
        return listGroupsRepo.findAll(listGroupFilterRequest.getPageable());
    }

    public ListGroup addListGroup(ListGroupDto listGroupDto) {
        return save(ListGroupFactory.buildListGroup(listGroupDto,
                groupCategoryService.findById(listGroupDto.getGroupCategory().getId()),
                userService.getCurrentUser())
        );
    }

    //TODO ADD TRANSACTIONAL
    public ListGroup save(ListGroup listGroup){
        log.info("saving list group: {}",listGroup.toString());
        return listGroupsRepo.save(listGroup);
    }

    public ListGroup findById(Long id){
       return listGroupsRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Not Found for id:"+id)
        );
    }


}
