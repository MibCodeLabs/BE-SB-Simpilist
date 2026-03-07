package com.mib.simpilist.controller;

import com.mib.simpilist.dto.ListItem.ListItemDto;
import com.mib.simpilist.dto.ListItem.ListItemRequest;
import com.mib.simpilist.dto.Utility.PageResponse;
import com.mib.simpilist.service.ListItemService;
import com.mib.simpilist.utililty.Utilities;
import com.mib.simpilist.utililty.factory.ListItemFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@Slf4j(topic = "ListItemController")
@RequestMapping("/groups/{groupId}/items")
public class ListItemController {
    private final ListItemService listItemService;

    public ListItemController(ListItemService listItemService) {
        this.listItemService = listItemService;
    }

    @GetMapping
    public PageResponse<ListItemDto> listItems(@PathVariable Long groupId, ListItemRequest listItemRequest) {
        return Utilities.mapPageToPageResponse(
                listItemService.listItems(groupId,listItemRequest),
                ListItemFactory::buildListItemDto);

    }
}
