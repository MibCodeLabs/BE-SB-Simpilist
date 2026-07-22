package com.mib.simpilist.controller;

import com.mib.simpilist.dto.Group.GroupDto;
import com.mib.simpilist.dto.Group.GroupFilterRequest;
import com.mib.simpilist.dto.Utility.PageResponse;
import com.mib.simpilist.service.GroupService;
import com.mib.simpilist.utililty.Utilities;
import com.mib.simpilist.utililty.factory.GroupFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController()
@Slf4j(topic = "GroupController")
@RequestMapping("/group")
@CrossOrigin
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public PageResponse<GroupDto> getAllGroups(GroupFilterRequest groupFilterRequest) {
        return Utilities.mapPageToPageResponse(
                groupService.getGroups(groupFilterRequest),
                GroupFactory::buildGroupDto);

    }

    @PostMapping
    public GroupDto addGroup(@RequestBody GroupDto groupDto) {
        return GroupFactory.buildGroupDto(groupService.addGroup(groupDto));

    }

    @PatchMapping("/{id}")
    public GroupDto modifyGroup(@PathVariable Long id, @RequestBody GroupDto groupDto) {
        return GroupFactory.buildGroupDto(groupService.modifyGroup(id, groupDto));
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable Long id) {
        groupService.removeGroup(id);
    }


}
