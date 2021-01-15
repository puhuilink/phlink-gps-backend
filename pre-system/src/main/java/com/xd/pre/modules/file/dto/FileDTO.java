package com.xd.pre.modules.file.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
public class FileDTO {
    @NotBlank
    private String batchFileUUID;

    private String bizType;
    private String bizId;
    private MultipartFile[] files;
}
