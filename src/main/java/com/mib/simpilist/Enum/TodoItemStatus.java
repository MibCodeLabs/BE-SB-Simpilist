package com.mib.simpilist.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum TodoItemStatus {

    DONE("Done", 1),
    ON_HOLD("On Hold", 2),
    TODO("To-Do", 3);

    private String label;
    private Integer id;
}