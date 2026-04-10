package com.mib.simpilist.utililty;

import org.springframework.data.domain.Sort;

public class Constants {
    public static final String[] PUBLIC_URLS= {
            "/swagger-ui/*",
            "/v3/api-docs",
            "/v3/api-docs/*",
            "/auth/login",
            "/auth/register"
    };
    public static final String GENERIC_ERROR_MESSAGE="Internal Error";
    public static final String GENERIC_ERROR_PARAMETER="-";
    public static final Integer GENERIC_ERROR_STATUS=500;

    //Default Pagination Settings
    public static final Sort.Direction DEFAULT_PAGE_SORT_DIRECTION=Sort.Direction.ASC;
    public static final Integer DEFAULT_PAGE_NUMBER=0;
    public static final Integer DEFAULT_PAGE_SIZE=10;
}
