package com.mib.simpilist.repository;

import com.mib.simpilist.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepo extends JpaRepository<Group,Long>, JpaSpecificationExecutor<Group> {
    Optional<Group> findByIdAndUser_Id(Long id, Long userId);
}
