package com.mib.simpilist.repository;

import com.mib.simpilist.model.ListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ListItemsRepo extends JpaRepository<ListItem,Long>, JpaSpecificationExecutor<ListItem> {
}
