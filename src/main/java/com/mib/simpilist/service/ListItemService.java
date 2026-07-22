package com.mib.simpilist.service;

import com.mib.simpilist.Specification.ListItemSpecification;
import com.mib.simpilist.annotation.RollbackTransaction;
import com.mib.simpilist.dto.ListItem.ListItemDto;
import com.mib.simpilist.dto.ListItem.ListItemFilterRequest;
import com.mib.simpilist.exception.ResourceNotFoundException;
import com.mib.simpilist.model.ListItem;
import com.mib.simpilist.repository.ListItemRepo;
import com.mib.simpilist.utililty.Security.UserContext;
import com.mib.simpilist.utililty.factory.ListItemFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic ="ListItemService" )
public class ListItemService {

    private final GroupService groupService;
    private final ListItemRepo listItemRepo;

    public ListItemService(GroupService groupService, ListItemRepo listItemRepo) {
        this.groupService = groupService;
        this.listItemRepo = listItemRepo;
    }

    public Page<ListItem> listItems(Long groupId, ListItemFilterRequest listItemFilterRequest){
        return listItemRepo.findAll(
                buildSpecifications(groupId, listItemFilterRequest),
                listItemFilterRequest.getPageable());
    }

    private Specification<ListItem> buildSpecifications(Long groupId, ListItemFilterRequest listItemFilterRequest){
        return Specification.allOf(
                ListItemSpecification.searchQuery(listItemFilterRequest.getSearchQuery()),
                ListItemSpecification.groupIdEquals(groupId),
                ListItemSpecification.belongsToCurrentUser()
        );
    }

    @RollbackTransaction
    public ListItem addListItem(Long groupId,ListItemDto listItemDto){
        return save(ListItemFactory.buildListItem(listItemDto,
                groupService.findById(groupId)));
    }

    @RollbackTransaction
    public ListItem modifyListItem(Long groupId,Long id,ListItemDto listItemDto){
        ListItem item= findByIdAndGroup_IdAndGroup_UserId(
                id,
                groupId,
                UserContext.getCurrentUser().id());
        handleItemFieldUpdate(item,listItemDto);
        return save(item);
    }


    private void handleItemFieldUpdate(ListItem item, ListItemDto listItemDto) {
        if (!listItemDto.getTodoItemStatus().equals(item.getTodoItemStatus())) {
            item.setTodoItemStatus(listItemDto.getTodoItemStatus());
        }

        if (!listItemDto.getTitle().equals(item.getTitle())) {
            item.setTitle(listItemDto.getTitle());
        }

        if (!listItemDto.getDescription().equals(item.getDescription())
        ) {
            item.setDescription(listItemDto.getDescription());
        }

        if (!listItemDto.getPriority().equals(item.getPriority())) {
            item.setPriority(listItemDto.getPriority());
        }

        if(!listItemDto.getGroup().getId().equals(item.getGroup().getId())){
            item.setGroup(groupService.findByIdAndCurrentUserId(listItemDto.getGroup().getId()));
        }
    }

    private ListItem findByIdAndGroup_IdAndGroup_UserId(Long itemId, Long groupId, Long userId){
        //todo add log error
        return listItemRepo.findByIdAndGroup_IdAndGroup_UserId(
                itemId,
                groupId,
                userId).orElseThrow(
                ()-> new ResourceNotFoundException("Not Found for id:"+itemId+" and groupId:"+groupId)
        );
    }

    @RollbackTransaction
    public ListItem save(ListItem listItem){
        return listItemRepo.save(listItem);
    }

    @RollbackTransaction
    public void delete(ListItem listItem){
        listItemRepo.delete(listItem);
    }

    @RollbackTransaction
    public void deleteItem(Long groupId, Long id) {
        ListItem item= findByIdAndGroup_IdAndGroup_UserId(id,
                groupId,
                UserContext.getCurrentUser().id());
        delete(item);
    }
}
