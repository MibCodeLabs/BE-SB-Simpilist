package com.mib.simpilist.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
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

    @Override
    public String toString() {
        return "ListGroup{" +
                "id=" + this.getId() +
                ", groupName='" + this.getGroupName() +
                ", priority=" + this.getPriority() +
                '}';
    }
}
