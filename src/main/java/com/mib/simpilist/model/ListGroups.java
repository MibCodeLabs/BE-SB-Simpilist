package com.mib.simpilist.model;

import jakarta.persistence.*;

@Entity
@Table(name = "list_groups",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user", "group_name"})
        })
public class ListGroups extends BaseEntity {
    @Column(name = "group_name", nullable = false)
    private String groupName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_category_id")
    private GroupCategories groupCategories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private Users users;

    @Column(name = "priority")
    private Integer priority;
}
