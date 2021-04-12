package com.xd.pre.flowable.vo;

import lombok.Data;

/**
 * @author
 * @date
 */
@Data
public class ProcessInstanceDetailResponse extends HistoricProcessInstanceResponse {
    private boolean suspended;
    private String deleteReason;
    private String startUserName;

}
