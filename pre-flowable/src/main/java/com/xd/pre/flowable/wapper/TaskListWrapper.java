package com.xd.pre.flowable.wapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xd.pre.flowable.common.ResponseFactory;

/**
 * @author
 * @date
 */
@Component
public class TaskListWrapper implements IListWrapper {

    @Autowired
    private ResponseFactory responseFactory;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public List execute(List list) {
        return responseFactory.createTaskResponseList(list);
    }
}
