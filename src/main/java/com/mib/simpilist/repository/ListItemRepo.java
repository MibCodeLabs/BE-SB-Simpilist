package com.mib.simpilist.repository;

import com.mib.simpilist.model.ListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListItemRepo extends JpaRepository<ListItem,Long>, JpaSpecificationExecutor<ListItem> {
    Optional<ListItem> findByIdAndGroup_IdAndGroup_UserId(Long itemId, Long groupId, Long userId);
}
