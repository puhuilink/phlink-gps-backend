package com.xd.pre.modules.template.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zcc
 * @since 2021-01-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysTemplate implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "tid", type = IdType.AUTO)
    private Integer tid;

    private String name;

    @TableField("delFlag")
    private String delFlag;

    @TableField("bizType")
    private String bizType;

    @TableField("bizId")
    private String bizId;

    private String batchId;

    @TableField(exist =  false)
    private String fileName;

    @TableField(exist =  false)
    private String filePath;

    private Integer fileId;


    private Integer tenantId;


}
