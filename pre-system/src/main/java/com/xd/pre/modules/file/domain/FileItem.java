package com.xd.pre.modules.file.domain;

import lombok.Data;
import lombok.Getter;

import java.io.File;
import java.io.OutputStream;

@Data
public abstract class FileItem {
    protected Integer id;
    protected String name;
    protected String path;
    protected byte[] fileByte;

    public abstract void copy(OutputStream os);
    public abstract boolean delete();
}
