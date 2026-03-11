package com.mib.simpilist.repository;

import com.mib.simpilist.model.GroupCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupCategoryRepo extends JpaRepository<GroupCategory,Long>, JpaSpecificationExecutor<GroupCategory> {
    Optional<GroupCategory> findByIdAndUser_Id(Long id, Long userId);
}
