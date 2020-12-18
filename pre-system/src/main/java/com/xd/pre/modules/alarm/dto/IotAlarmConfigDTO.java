package com.xd.pre.modules.alarm.dto;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 告警配置表
 * </p>
 *
 * @author zappa
 * @since 2020-12-18
 */
@Data
public class IotAlarmConfigDTO {

    private static final long serialVersionUID= 533974772312457991L;

    /**
     * 告警配置主键ID
     */
    @TableId(value = "config_id", type = IdType.AUTO)
    private Integer configId;

    /**
     * 配置名称
     */
    private String name;

    /**
     * 围栏ID
     */
    private Integer fenceId;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 设备类型
     */
    private String deviceType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private JSONObject validTime;

}
