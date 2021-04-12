package com.xd.pre.flowable.entity;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xd.pre.flowable.validator.LengthForUtf8;

import lombok.Data;

/**
 * @author
 * @date
 */
@Data
@TableName("T_FLOWABLE_FORM")
public class FlowableForm extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId
    @NotNull
    @LengthForUtf8(max = 100)
    private String formKey;

    @NotNull
    @LengthForUtf8(max = 100)
    private String formName;

    private String formJson;
}