package com.xd.pre.flowable.vo;

import lombok.Data;

/**
 * @author
 * @date
 */
@Data
public class ProcessDefinitionResponse {
    private String id;
    private String key;
    private int version;
    private String name;
    private String description;
    private String tenantId;
    private String category;
    private String formKey;
    private boolean graphicalNotationDefined = false;
    private boolean suspended = false;
}
