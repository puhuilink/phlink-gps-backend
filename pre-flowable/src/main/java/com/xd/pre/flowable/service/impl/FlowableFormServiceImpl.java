package com.xd.pre.flowable.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xd.pre.flowable.entity.FlowableForm;
import com.xd.pre.flowable.mapper.FlowableFormMapper;
import com.xd.pre.flowable.service.FlowableFormService;

/**
 * 流程Service
 *
 * @author
 */
@Service
public class FlowableFormServiceImpl extends BaseServiceImpl<FlowableFormMapper, FlowableForm> implements FlowableFormService {
    @Override
    public IPage<FlowableForm> list(IPage<FlowableForm> page, FlowableForm flowableForm) {
        return page.setRecords(baseMapper.list(page, flowableForm));
    }
}
