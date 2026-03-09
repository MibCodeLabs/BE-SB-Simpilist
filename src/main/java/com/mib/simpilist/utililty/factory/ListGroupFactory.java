package com.mib.simpilist.utililty.factory;

import com.mib.simpilist.dto.GroupCategory.GroupCategoryDto;
import com.mib.simpilist.dto.ListGroup.ListGroupDto;
import com.mib.simpilist.model.GroupCategory;
import com.mib.simpilist.model.ListGroup;
import com.mib.simpilist.model.User;

public class ListGroupFactory {
    public static ListGroupDto buildListGroupDto(ListGroup listGroup){
        GroupCategoryDto groupCategoryDto= GroupCategoryFactory.buildGroupCategoryDto(listGroup.getGroupCategory());
        return ListGroupDto
                .builder()
                .groupCategory(groupCategoryDto)
                .groupName(listGroup.getGroupName())
                .priority(listGroup.getPriority())
                .id(listGroup.getId())
                .build();
    }

    public static ListGroup buildListGroup(ListGroupDto listGroupDto, GroupCategory groupCategory, User user){
        return ListGroup
                .builder()
                .priority(listGroupDto.getPriority())
                .groupCategory(groupCategory)
                .user(user)
                .groupName(listGroupDto.getGroupName())
                .id(listGroupDto.getId())
                .build();
    }
}
