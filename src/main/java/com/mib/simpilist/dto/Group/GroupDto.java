package com.mib.simpilist.dto.Group;

import com.mib.simpilist.dto.Category.CategoryDto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GroupDto {
    private Long id;
    private String groupName;
    private CategoryDto category;
    private Integer priority;
}
