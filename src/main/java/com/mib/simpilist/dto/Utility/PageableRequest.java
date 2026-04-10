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

    private List<SortField> sortFields = new ArrayList<>();

    public Pageable getPageable() {
        List<Sort.Order> orders = sortFields.stream()
                .map(sortField -> new Sort.Order(sortField.getDirection(),
                        sortField.getField()))
                .toList();
        return PageRequest.of(pageNumber, pageSize, Sort.by(orders));
    }
}
