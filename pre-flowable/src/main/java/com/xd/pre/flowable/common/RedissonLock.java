package com.xd.pre.flowable.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedissonLock {
    /**
     * 要锁的参数角标
     */
    int[] lockIndexs() default {};

    /**
     * 要锁的参数的属性名
     */
    String[] fieldNames() default {};

    /**
     * 等待多久（单位：秒）
     */
    int waitTime() default 10;

    /**
     * 锁多久后自动释放（单位：秒）
     */
    int leaseTime() default 10;

    /**
     * 未取到锁时提示信息
     *
     * @return
     */
    String msg() default "交易执行失败，请稍后重试";
}
