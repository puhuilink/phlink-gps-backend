package com.xd.pre.modules.fence.dto;

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
    private String fence;

    /**
     * 围栏名称
     */
    private String name;

}
