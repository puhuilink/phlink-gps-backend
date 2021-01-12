package com.xd.pre.modules.file.domain;

import lombok.Data;

@Data
public abstract class PersistFileItem extends FileItem {
    protected Integer id;
    protected Integer userId;
    protected Integer orgId;
    protected String bizType;
    protected String bizId;
}
