package com.mib.simpilist.controller;

import com.mib.simpilist.dto.GroupCategory.GroupCategoryDto;
import com.mib.simpilist.dto.GroupCategory.GroupCategoryFilterRequest;
import com.mib.simpilist.dto.Utility.PageResponse;
import com.mib.simpilist.service.GroupCategoryService;
import com.mib.simpilist.utililty.Utilities;
import com.mib.simpilist.utililty.factory.GroupCategoryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController()
@Slf4j(topic = "GroupCategoryController")
@RequestMapping("/category")
public class GroupCategoryController {

    private final GroupCategoryService groupCategoryService;

    public GroupCategoryController(GroupCategoryService groupCategoryService) {
        this.groupCategoryService = groupCategoryService;
    }

    @GetMapping
    public PageResponse<GroupCategoryDto> getAllGroups(GroupCategoryFilterRequest groupCategoryFilterRequest) {
        return Utilities.mapPageToPageResponse(
                groupCategoryService.getAllGroupCategories(groupCategoryFilterRequest),
                GroupCategoryFactory::buildGroupCategoryDto);

    }

    @PostMapping
    public GroupCategoryDto addListGroup(@RequestBody GroupCategoryDto groupCategoryDto) {
        return GroupCategoryFactory.buildGroupCategoryDto(groupCategoryService.addGroupCategory(groupCategoryDto));

    }
}
