package com.mib.simpilist.dto.ListGroup;

import com.mib.simpilist.dto.GroupCategory.GroupCategoryDto;
import com.mib.simpilist.dto.ListItem.ListItemDto;
import com.mib.simpilist.model.GroupCategory;
import com.mib.simpilist.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class ListGroupDto {
    private Long id;
    private String groupName;
    private GroupCategoryDto groupCategory;
    private Integer priority;
}
