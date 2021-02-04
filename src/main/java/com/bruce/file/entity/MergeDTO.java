package com.bruce.file.entity;

import lombok.Data;

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
public class MergeDTO {

    /**
     * 总块数
     */
    @NotNull
    private Integer chunks;

    /**
     * 文件名
     */
    @NotEmpty
    private String name;

    /**
     * 文件的 md5 值
     */
    @NotEmpty
    private String md5;

}
