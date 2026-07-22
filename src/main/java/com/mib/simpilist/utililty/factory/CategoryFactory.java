package com.mib.simpilist.utililty.factory;

import com.mib.simpilist.dto.Category.CategoryDto;
import com.mib.simpilist.model.Category;
import com.mib.simpilist.model.User;

public class CategoryFactory {
    public static CategoryDto buildCategoryDto(Category category){
        return CategoryDto
                .builder()
                .categoryName(category.getCategoryName())
                .priority(category.getPriority())
                .id(category.getId())
                .groupCount(category.getGroupCount())
                .build();
    }

    public static Category buildCategory(CategoryDto categoryDto, User user) {
        return Category
                .builder()
                .categoryName(categoryDto.getCategoryName())
                .priority(categoryDto.getPriority())
                .user(user)
                .id(categoryDto.getId())
                .build();
    }
}
