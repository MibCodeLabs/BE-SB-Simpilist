package com.mib.simpilist.controller;

import com.mib.simpilist.dto.ListGroup.ListGroupDto;
import com.mib.simpilist.dto.ListGroup.ListGroupFilterRequest;
import com.mib.simpilist.dto.Utility.PageResponse;
import com.mib.simpilist.service.ListGroupService;
import com.mib.simpilist.utililty.Utilities;
import com.mib.simpilist.utililty.factory.ListGroupFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController()
@Slf4j(topic = "ListGroupController")
@RequestMapping("/groups")
public class ListGroupController {
    private final ListGroupService listGroupService;

    public ListGroupController(ListGroupService listGroupService) {
        this.listGroupService = listGroupService;
    }

    @GetMapping
    public PageResponse<ListGroupDto> getAllGroups(ListGroupFilterRequest listGroupFilterRequest) {
        return Utilities.mapPageToPageResponse(
                listGroupService.getListGroups(listGroupFilterRequest),
                ListGroupFactory::buildListGroupDto);

    }

    @PostMapping
    public ListGroupDto addListGroup(@RequestBody ListGroupDto listGroupDto) {
        return ListGroupFactory.buildListGroupDto(listGroupService.addListGroup(listGroupDto));

    }

    @PatchMapping("/{id}")
    public ListGroupDto modifyListGroup(@PathVariable Long id,@RequestBody ListGroupDto listGroupDto) {
        return ListGroupFactory.buildListGroupDto(listGroupService.modifyListGroup(id,listGroupDto));
    }

    @DeleteMapping("/{id}")
    public void deleteListGroup(@PathVariable Long id) {
        listGroupService.removeListGroup(id);
    }


}
