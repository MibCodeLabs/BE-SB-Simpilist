package com.mib.simpilist.dto.ListGroup;

import com.mib.simpilist.dto.ListItem.ListItemDto;
import com.mib.simpilist.model.GroupCategories;
import com.mib.simpilist.model.Users;
import lombok.Data;

import java.util.Set;

@Data
public class ListGroupDto {
    private Long id;
    private String groupName;
    private GroupCategories groupCategories;
    private Users users;
    private Integer priority;
    private Set<ListItemDto> items;
}
