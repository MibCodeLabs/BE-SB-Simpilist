package com.mib.simpilist.utililty.factory;

import com.mib.simpilist.dto.Group.GroupDto;
import com.mib.simpilist.dto.ListItem.ListItemDto;
import com.mib.simpilist.model.Group;
import com.mib.simpilist.model.ListItem;

public class ListItemFactory {
    public static ListItemDto buildListItemDto(ListItem listItem){
        GroupDto groupDto = GroupFactory.buildGroupDto(listItem.getGroup());
        return ListItemDto
                .builder()
                .group(groupDto)
                .description(listItem.getDescription())
                .title(listItem.getTitle())
                .id(listItem.getId())
                .priority(listItem.getPriority())
                .todoItemStatus(listItem.getTodoItemStatus())
                .build();
    }

    public static ListItem buildListItem(ListItemDto listItemDto, Group group){
        return ListItem
                .builder()
                .id(listItemDto.getId())
                .title(listItemDto.getTitle())
                .group(group)
                .todoItemStatus(listItemDto.getTodoItemStatus())
                .description(listItemDto.getDescription())
                .priority(listItemDto.getPriority())
                .build();
    }
}
