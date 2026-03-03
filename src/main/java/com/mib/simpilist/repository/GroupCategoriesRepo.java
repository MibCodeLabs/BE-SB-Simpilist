package com.mib.simpilist.repository;

import com.mib.simpilist.model.GroupCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupCategoriesRepo extends JpaRepository<GroupCategories,Long> {
}
