package com.mib.simpilist.utililty.factory;

import com.mib.simpilist.dto.Category.CategoryDto;
import com.mib.simpilist.dto.Group.GroupDto;
import com.mib.simpilist.model.Category;
import com.mib.simpilist.model.Group;
import com.mib.simpilist.model.User;

public class GroupFactory {
    public static GroupDto buildGroupDto(Group group){
        CategoryDto categoryDto = CategoryFactory.buildCategoryDto(group.getCategory());
        return GroupDto
                .builder()
                .category(categoryDto)
                .groupName(group.getGroupName())
                .priority(group.getPriority())
                .id(group.getId())
                .build();
    }

    public static Group buildGroup(GroupDto groupDto, Category category, User user){
        return Group
                .builder()
                .priority(groupDto.getPriority())
                .category(category)
                .user(user)
                .groupName(groupDto.getGroupName())
                .id(groupDto.getId())
                .build();
    }
}
