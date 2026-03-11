package com.mib.simpilist.controller;

import com.mib.simpilist.dto.ListItem.ListItemDto;
import com.mib.simpilist.dto.ListItem.ListItemFilterRequest;
import com.mib.simpilist.dto.Utility.PageResponse;
import com.mib.simpilist.service.ListItemService;
import com.mib.simpilist.utililty.Utilities;
import com.mib.simpilist.utililty.factory.ListItemFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController()
@Slf4j(topic = "ListItemController")
@RequestMapping("/groups/{groupId}/items")
public class ListItemController {
    private final ListItemService listItemService;

    public ListItemController(ListItemService listItemService) {
        this.listItemService = listItemService;
    }

    @GetMapping
    public PageResponse<ListItemDto> listItems(@PathVariable Long groupId, ListItemFilterRequest listItemFilterRequest) {
        return Utilities.mapPageToPageResponse(
                listItemService.listItems(groupId, listItemFilterRequest),
                ListItemFactory::buildListItemDto);

    }

    @PostMapping
    public ListItemDto addListItem(@PathVariable Long groupId,@RequestBody ListItemDto listItemDto) {
        return ListItemFactory.buildListItemDto(listItemService.addListItem(groupId,listItemDto));
    }

    @PatchMapping("/{id}")
    public ListItemDto modifyListItem(@PathVariable Long groupId,
                                      @PathVariable Long id,
                                      @RequestBody ListItemDto listItemDto) {
        return ListItemFactory.buildListItemDto(listItemService.modifyListItem(groupId,id,listItemDto));
    }

    @DeleteMapping("/{id}")
    public void deleteListItem(@PathVariable Long groupId,
                                      @PathVariable Long id) {
        listItemService.deleteItem(groupId,id);
    }
}
