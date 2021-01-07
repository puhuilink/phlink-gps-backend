package com.xd.pre.modules.file.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zcc
 * @since 2021-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysFile implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 路径
     */
    private String path;

    /**
     * 业务ID
     */
    private String bizId;

    /**
     * 上传人id
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    private Integer orgId;

    private String bizType;

    private String fileBatchId;


}
