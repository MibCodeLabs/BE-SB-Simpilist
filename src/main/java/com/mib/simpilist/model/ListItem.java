package com.mib.simpilist.model;

import com.mib.simpilist.Enum.TodoItemStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "list_items")
@Getter
@Setter
public class ListItem extends BaseEntity{

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name="description")
    private String description;

    @Column(name = "priority")
    private Integer priority;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id",nullable = false)
    private ListGroup listGroup;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status",nullable = false)
    private TodoItemStatus todoItemStatus;
}
