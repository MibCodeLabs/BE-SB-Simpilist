package com.mib.simpilist.dto.Utility;

import com.mib.simpilist.utililty.Constants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PageableRequest{
    private Integer pageNumber = Constants.DEFAULT_PAGE_NUMBER;
    private Integer pageSize = Constants.DEFAULT_PAGE_SIZE;

    private List<String> sortFields = new ArrayList<>();
    private Sort.Direction direction = Constants.DEFAULT_PAGE_SORT_DIRECTION;

    public Pageable getPageable() {
        List<Sort.Order> orders = sortFields.stream()
                .map(field -> new Sort.Order(direction, field))
                .toList();
        return PageRequest.of(pageNumber, pageSize, Sort.by(orders));
    }
}
