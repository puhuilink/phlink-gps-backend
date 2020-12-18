package com.xd.pre.modules.fence.dto;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

/**
 * <p>
 * 围栏信息表
 * </p>
 *
 * @author phlink
 * @since 2020-12-09
 */
@Data
public class IotFenceDTO {

    private Integer id;

    /**
     * 围栏坐标点信息
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private JSONArray fence;

    /**
     * 围栏名称
     */
    private String name;

}
