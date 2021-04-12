package com.xd.pre.flowable.vo;

import lombok.Data;

/**
 * @author
 * @date
 */
@Data
public class IdentityRequest {
    private String processDefinitionId;
    private String taskId;
    private String identityId;
    private String identityType;
}
