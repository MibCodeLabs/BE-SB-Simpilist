package com.mib.simpilist.dto.Utility;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchablePageRequest extends PageableRequest{
    String searchQuery;
}
