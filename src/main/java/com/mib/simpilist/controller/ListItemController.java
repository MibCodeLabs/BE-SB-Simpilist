package com.mib.simpilist.controller;

import com.mib.simpilist.Enum.TodoItemStatus;
import com.mib.simpilist.dto.ListItem.ListItemDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@Slf4j(topic = "ListItemController")
@RequestMapping("/item")
public class ListItemController {


    private final List<ListItemDto> list = List.of(
            new ListItemDto(1L, "test 1", "test description 1", 1, null, TodoItemStatus.TODO),
            new ListItemDto(2L, "test 2", "test description 2", 1, null, TodoItemStatus.TODO),
            new ListItemDto(3L, "test 3", "test description 3", 1, null, TodoItemStatus.TODO)
    );

    @GetMapping("/")
    public Page<ListItemDto> getAllItems() {
        return new PageImpl<>(
                list,
                PageRequest.of(1,list.size()),
                list.size()
        );
    }
}
