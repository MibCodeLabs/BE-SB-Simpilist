package com.mib.simpilist.dto.Group;

import com.mib.simpilist.dto.Utility.SearchablePageRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GroupFilterRequest extends SearchablePageRequest {
    private Integer categoryId;
}
