package com.mib.simpilist.repository;

import com.mib.simpilist.model.GroupCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupCategoryRepo extends JpaRepository<GroupCategory,Long> {
}
