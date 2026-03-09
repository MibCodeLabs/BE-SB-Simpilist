package com.mib.simpilist.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "group_categories")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GroupCategory extends BaseEntity{

    @Column(name = "category_name",nullable = false)
    private String categoryName;

    @Column(name = "priority",nullable = false)
    private Integer priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Override
    public String toString() {
        return "GroupCategory{" +
                "id=" + this.getId() +
                ", categoryName='" + this.getCategoryName() +
                ", priority=" + this.getPriority() +
                '}';
    }
}
