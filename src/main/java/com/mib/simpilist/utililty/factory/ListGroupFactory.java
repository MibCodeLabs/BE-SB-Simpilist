package com.mib.simpilist.utililty.factory;

import com.mib.simpilist.dto.GroupCategory.GroupCategoryDto;
import com.mib.simpilist.dto.ListGroup.ListGroupDto;
import com.mib.simpilist.model.ListGroup;

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
}
