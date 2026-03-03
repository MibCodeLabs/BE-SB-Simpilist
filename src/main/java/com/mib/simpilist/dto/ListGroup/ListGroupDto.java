package com.mib.simpilist.dto.ListGroup;

import com.mib.simpilist.dto.ListItem.ListItemDto;
import com.mib.simpilist.model.GroupCategory;
import com.mib.simpilist.model.User;
import lombok.Data;

import java.util.Set;

@Data
public class ListGroupDto {
    private Long id;
    private String groupName;
    private GroupCategory groupCategory;
    private User user;
    private Integer priority;
    private Set<ListItemDto> items;
}
