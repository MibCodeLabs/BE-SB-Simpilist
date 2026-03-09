package com.mib.simpilist.utililty.factory;

import com.mib.simpilist.dto.ListGroup.ListGroupDto;
import com.mib.simpilist.dto.ListItem.ListItemDto;
import com.mib.simpilist.model.ListGroup;
import com.mib.simpilist.model.ListItem;

public class ListItemFactory {
    public static ListItemDto buildListItemDto(ListItem listItem){
        ListGroupDto listGroupDto=ListGroupFactory.buildListGroupDto(listItem.getListGroup());
        return ListItemDto
                .builder()
                .listGroup(listGroupDto)
                .description(listItem.getDescription())
                .title(listItem.getTitle())
                .id(listItem.getId())
                .priority(listItem.getPriority())
                .todoItemStatus(listItem.getTodoItemStatus())
                .build();
    }

    public static ListItem buildListItem(ListItemDto listItemDto,ListGroup listGroup){
        return ListItem
                .builder()
                .id(listItemDto.getId())
                .title(listItemDto.getTitle())
                .listGroup(listGroup)
                .todoItemStatus(listItemDto.getTodoItemStatus())
                .description(listItemDto.getDescription())
                .priority(listItemDto.getPriority())
                .build();
    }
}
