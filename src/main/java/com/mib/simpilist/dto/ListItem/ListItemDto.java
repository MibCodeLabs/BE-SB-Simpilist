package com.mib.simpilist.dto.ListItem;

import com.mib.simpilist.Enum.TodoItemStatus;
import com.mib.simpilist.model.ListGroup;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListItemDto {
    private Long id;
    private String title;
    private String description;
    private Integer priority;
    private ListGroup listGroup;
    private TodoItemStatus todoItemStatus;
}
