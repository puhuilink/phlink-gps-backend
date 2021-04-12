package com.xd.pre.flowable.vo;

import java.util.Map;

import lombok.Data;

/**
 * @author
 * @date
 */
@Data
public class TaskRequest {
    private String taskId;
    private String userId;
    private String message;
    private String activityId;
    private String activityName;
    private Map<String, Object> values;
    private CcToVo[] ccToVos;
    private String[] taskIds;
}
