package com.mib.simpilist.controller;

import com.mib.simpilist.dto.ListItem.ListItemDto;
import com.mib.simpilist.service.ListItemService;
import com.mib.simpilist.utililty.Utilities;
import com.mib.simpilist.utililty.factory.ListItemFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@Slf4j(topic = "ListItemController")
@RequestMapping("/list/item")
public class ListItemController {
    private final ListItemService listItemService;

    public ListItemController(ListItemService listItemService) {
        this.listItemService = listItemService;
    }

    @GetMapping("/")
    public Page<ListItemDto> getAllItems() {
        return Utilities.mapPage(
                listItemService.getAllListItemsPaged(),
                ListItemFactory::buildListItemDto);
    }
}
