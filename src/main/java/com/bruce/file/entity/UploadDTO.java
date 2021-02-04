package com.bruce.file.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName file
 * @Date 2021/2/4 21:49
 * @Author Bruce
 */
@Data
public class UploadDTO {

    /**
     * 文件
     */
    @NotNull
    private MultipartFile file;

    /**
     * 当前块
     */
    private Integer chunk;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件的 md5 值
     */
    @NotEmpty
    private String md5;

}
