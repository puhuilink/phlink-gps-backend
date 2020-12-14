package com.xd.pre.log.annotation;


import java.lang.annotation.*;

/**
 * 1、RetentionPolicy.SOURCE 注解只保留在源文件中，在编译成class文件的时候被遗弃
 * 2、RetentionPolicy.CLASS 注解被保留在class中，但是在jvm加载的时候会被抛弃，这个是默认的生命周期
 * 3、RetentionPolicy.RUNTIME 注解在jvm加载的时候仍被保留
 **/
@Retention(RetentionPolicy.RUNTIME) //元注解，定义注解被保留策略
@Target({ElementType.METHOD}) //定义了注解声明在哪些元素之前
@Documented
public @interface SysOperaLog {
    String descrption() default "" ;
}
