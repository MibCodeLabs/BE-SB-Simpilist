package com.mib.simpilist.model;

import jakarta.persistence.*;

@Entity
@Table(name = "group_categories")
public class GroupCategories extends BaseEntity{

    @Column(name = "category_name",nullable = false)
    private String categoryName;

    @Column(name = "priority",nullable = false)
    private Integer priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private Users users;
}
