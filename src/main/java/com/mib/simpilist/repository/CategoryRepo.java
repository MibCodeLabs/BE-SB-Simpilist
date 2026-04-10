package com.mib.simpilist.repository;

import com.mib.simpilist.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long>, JpaSpecificationExecutor<Category> {
    Optional<Category> findByIdAndUser_Id(Long id, Long userId);
}
