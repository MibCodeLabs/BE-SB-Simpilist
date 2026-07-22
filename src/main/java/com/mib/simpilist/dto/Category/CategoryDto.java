package com.mib.simpilist.dto.Category;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryDto {
    private Long id;
    private String categoryName;
    private Integer priority;
    private Integer groupCount;
}
