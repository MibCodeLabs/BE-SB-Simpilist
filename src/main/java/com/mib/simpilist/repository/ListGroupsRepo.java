package com.mib.simpilist.repository;

import com.mib.simpilist.model.ListGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListGroupsRepo extends JpaRepository<ListGroup,Long>, JpaSpecificationExecutor<ListGroup> {
    Optional<ListGroup> findByIdAndUser_Id(Long id,Long userId);
}
