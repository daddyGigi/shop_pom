package com.qf.aop;

import java.lang.annotation.*;

/**
 * Created by  .Life on 2019/07/21/0021 13:19
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsLogin {
    boolean mustLogin() default false;
}
