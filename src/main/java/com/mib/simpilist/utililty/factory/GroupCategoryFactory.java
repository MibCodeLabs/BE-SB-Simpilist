package com.mib.simpilist.utililty.factory;

import com.mib.simpilist.dto.GroupCategory.GroupCategoryDto;
import com.mib.simpilist.model.GroupCategory;
import com.mib.simpilist.model.User;

public class GroupCategoryFactory {
    public static GroupCategoryDto buildGroupCategoryDto(GroupCategory groupCategory){
        return GroupCategoryDto
                .builder()
                .categoryName(groupCategory.getCategoryName())
                .priority(groupCategory.getPriority())
                .id(groupCategory.getId())
                .build();
    }

    public static GroupCategory buildGroupCategory(GroupCategoryDto groupCategoryDto, User user) {
        return GroupCategory
                .builder()
                .categoryName(groupCategoryDto.getCategoryName())
                .priority(groupCategoryDto.getPriority())
                .user(user)
                .id(groupCategoryDto.getId())
                .build();
    }
}
