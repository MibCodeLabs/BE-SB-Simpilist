package com.mib.simpilist.model;

import com.mib.simpilist.utililty.Utilities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "categories")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity{

    @Column(name = "category_name",nullable = false)
    private String categoryName;

    @Column(name = "priority",nullable = false)
    private Integer priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(name = "group_count",nullable = false)
    private Integer groupCount;

    @Override
    public String toString() {
        return "Category{" +
                "id=" + this.getId() +
                ", categoryName='" + this.getCategoryName() +
                ", priority=" + this.getPriority() +
                '}';
    }

    @PrePersist
    @PreUpdate
    private void setDefaults(){
        if(Utilities.isNull(this.groupCount)){
            this.groupCount=0;
        }
    }
}
