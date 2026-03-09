package com.mib.simpilist.service;

import com.mib.simpilist.Specification.ListItemSpecification;
import com.mib.simpilist.dto.ListItem.ListItemDto;
import com.mib.simpilist.dto.ListItem.ListItemFilterRequest;
import com.mib.simpilist.model.ListItem;
import com.mib.simpilist.repository.ListItemsRepo;
import com.mib.simpilist.utililty.factory.ListItemFactory;
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

    public Page<ListItem> listItems(Long groupId, ListItemFilterRequest listItemFilterRequest){
        return listItemsRepo.findAll(
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
    public ListItem addListItem(Long groupId,ListItemDto listItemDto){
        return save(ListItemFactory.buildListItem(listItemDto,
                listGroupService.findById(groupId)));
    }

    public ListItem save(ListItem listItem){
        return listItemsRepo.save(listItem);
    }

}
