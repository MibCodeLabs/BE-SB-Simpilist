package com.mib.simpilist.dto.GroupCategory;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GroupCategoryDto {
    private Long id;
    private String categoryName;
    private Integer priority;
}
