package com.xd.pre.flowable.vo;

import java.util.Map;

import lombok.Data;

/**
 * @author
 * @date
 */
@Data
public class ProcessInstanceRequest {
    private String processDefinitionId;
    private String processDefinitionKey;
    private String tenantId;
    private String businessKey;
    private Map<String, Object> values;
    private String processInstanceId;
    private CcToVo[] ccToVos;
}
