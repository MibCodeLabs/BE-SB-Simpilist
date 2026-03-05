package com.mib.simpilist.utililty.factory;

import com.mib.simpilist.dto.GroupCategory.GroupCategoryDto;
import com.mib.simpilist.model.GroupCategory;

public class GroupCategoryFactory {
    public static GroupCategoryDto buildGroupCategoryDto(GroupCategory groupCategory){
        return GroupCategoryDto
                .builder()
                .categoryName(groupCategory.getCategoryName())
                .priority(groupCategory.getPriority())
                .id(groupCategory.getId())
                .build();
    }
}
