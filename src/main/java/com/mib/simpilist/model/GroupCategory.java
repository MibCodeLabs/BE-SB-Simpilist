package com.mib.simpilist.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "group_categories")
@Getter
@Setter
public class GroupCategory extends BaseEntity{

    @Column(name = "category_name",nullable = false)
    private String categoryName;

    @Column(name = "priority",nullable = false)
    private Integer priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
}
