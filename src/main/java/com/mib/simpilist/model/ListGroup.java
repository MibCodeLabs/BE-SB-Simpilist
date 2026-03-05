package com.mib.simpilist.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "list_groups",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user", "group_name"})
        })
public class ListGroup extends BaseEntity {
    @Column(name = "group_name", nullable = false)
    private String groupName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_category_id")
    private GroupCategory groupCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(name = "priority")
    private Integer priority;
}
