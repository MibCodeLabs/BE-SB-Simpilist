package com.mib.simpilist.repository;

import com.mib.simpilist.model.ListGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListGroupRepo extends JpaRepository<ListGroup,Long> {
}
