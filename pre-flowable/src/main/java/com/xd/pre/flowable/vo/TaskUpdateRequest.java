package com.xd.pre.flowable.vo;

import java.util.Date;

import lombok.Data;

/**
 * @author
 * @date
 */
@Data
public class TaskUpdateRequest {
    private String id;
    private String name;
    private String assignee;
    private String owner;
    private Date dueDate;
    private String category;
    private String description;
    private int priority;
}
