package com.mib.simpilist.service;

import com.mib.simpilist.dto.GroupCategory.GroupCategoryDto;
import com.mib.simpilist.dto.GroupCategory.GroupCategoryFilterRequest;
import com.mib.simpilist.exception.ResourceNotFoundException;
import com.mib.simpilist.model.GroupCategory;
import com.mib.simpilist.repository.GroupCategoryRepo;
import com.mib.simpilist.utililty.factory.GroupCategoryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j(topic = "GroupCategoryService")
@Service
public class GroupCategoryService {
    private final GroupCategoryRepo groupCategoryRepo;
    private final UserService userService;

    public GroupCategoryService(GroupCategoryRepo groupCategoryRepo, UserService userService) {
        this.groupCategoryRepo = groupCategoryRepo;
        this.userService = userService;
    }

    public Page<GroupCategory> getAllGroupCategories(GroupCategoryFilterRequest groupCategoryFilterRequest){
        //todo add specifications
        return groupCategoryRepo.findAll(groupCategoryFilterRequest.getPageable());
    }

    public GroupCategory findById(Long id){
        return groupCategoryRepo.findById(id).orElseThrow(()->{
            log.error("GroupCategory with id:{} does not exists",id);
            return new ResourceNotFoundException("not found");
        });
    }

    public GroupCategory addGroupCategory(GroupCategoryDto groupCategoryDto) {
            return save(GroupCategoryFactory.buildGroupCategory(groupCategoryDto,
                    userService.getCurrentUser()));
    }

    public GroupCategory save(GroupCategory groupCategory){
        log.info("saving list group: {}",groupCategory.toString());
        return groupCategoryRepo.save(groupCategory);
    }
}
