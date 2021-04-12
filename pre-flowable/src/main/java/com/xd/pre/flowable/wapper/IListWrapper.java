package com.xd.pre.flowable.wapper;

import java.util.List;

/**
 * @author
 * @date
 */
public interface IListWrapper {
    /**
     * 执行List转换封装
     *
     * @param list
     * @return
     */
    @SuppressWarnings("rawtypes")
    public List execute(List list);
}