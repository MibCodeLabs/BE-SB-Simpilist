package com.mib.simpilist.dto.ListItem;

import com.mib.simpilist.Enum.TodoItemStatus;
import com.mib.simpilist.dto.Group.GroupDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ListItemDto {
    private Long id;
    private String title;
    private String description;
    private Integer priority;
    private GroupDto group;
    private TodoItemStatus todoItemStatus;
}
