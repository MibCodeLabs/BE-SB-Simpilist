package com.mib.simpilist.model;

import com.mib.simpilist.Enum.TodoItemStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "list_items")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
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

    @Override
    public String toString() {
        return "ListItem{" +
                "id=" + this.getId() +
                ", title='" + this.getTitle()  +
                ", description='" + this.getDescription()  +
                ", priority=" + this.getPriority() +
                ", status=" + this.getTodoItemStatus() +
                ", listGroupId=" + (listGroup != null ? listGroup.getId() : null) +
                ", listGroupName=" + (listGroup != null ? listGroup.getGroupName() : null) +
                '}';
    }
}
