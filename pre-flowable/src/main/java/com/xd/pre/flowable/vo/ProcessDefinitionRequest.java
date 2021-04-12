package com.xd.pre.flowable.vo;

import java.util.Date;

import lombok.Data;

/**
 * @author
 * @date
 */
@Data
public class ProcessDefinitionRequest {
    private String processDefinitionId;
    private boolean includeProcessInstances = false;
    private Date date;
}
