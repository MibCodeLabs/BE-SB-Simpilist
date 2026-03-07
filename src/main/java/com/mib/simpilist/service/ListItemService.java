package com.mib.simpilist.service;

import com.mib.simpilist.Specification.ListItemSpecification;
import com.mib.simpilist.dto.ListItem.ListItemRequest;
import com.mib.simpilist.model.ListGroup;
import com.mib.simpilist.model.ListItem;
import com.mib.simpilist.repository.ListItemsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic ="ListItemService" )
public class ListItemService {

    private final ListGroupService listGroupService;
    private final ListItemsRepo listItemsRepo;

    public ListItemService(ListGroupService listGroupService, ListItemsRepo listItemsRepo) {
        this.listGroupService = listGroupService;
        this.listItemsRepo = listItemsRepo;
    }

    public Page<ListItem> listItems(Long groupId, ListItemRequest listItemRequest){
        return listItemsRepo.findAll(
                buildSpecifications(groupId,listItemRequest),
                listItemRequest.getPageable());
    }

    private Specification<ListItem> buildSpecifications(Long groupId,ListItemRequest listItemRequest){
        return Specification.allOf(
                ListItemSpecification.searchQuery(listItemRequest.getSearchQuery()),
                ListItemSpecification.groupIdEquals(groupId),
                ListItemSpecification.belongsToCurrentUser()
        );
    }
    public void addListItem(ListGroup listGroup){
        return;
    }

}
