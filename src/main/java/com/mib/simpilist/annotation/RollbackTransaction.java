package com.mib.simpilist.annotation;


import jakarta.transaction.Transactional;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Transactional(rollbackOn = Exception.class)
public @interface RollbackTransaction {
}