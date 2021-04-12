package com.xd.pre.flowable.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xd.pre.flowable.entity.FlowableForm;

/**
 * 流程表单Service
 *
 * @author
 */
public interface FlowableFormService extends BaseService<FlowableForm> {
    /**
     * 分页查询流程表单
     *
     * @param page
     * @param flowableForm
     * @return
     */
    IPage<FlowableForm> list(IPage<FlowableForm> page, FlowableForm flowableForm);
}
