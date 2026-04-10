package com.mib.simpilist.dto.Utility;

import com.mib.simpilist.utililty.Constants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class SortField {
    private String field;
    private Sort.Direction direction = Constants.DEFAULT_PAGE_SORT_DIRECTION;
}
