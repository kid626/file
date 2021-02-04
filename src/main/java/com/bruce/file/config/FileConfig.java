package com.bruce.file.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName file
 * @Date 2021/2/4 21:49
 * @Author Bruce
 */
@Configuration
public class FileConfig {
    /**
     * 文件上传配置
     *
     * @return MultipartConfigElement
     */

    private DataSize maxFileSize = DataSize.ofMegabytes(300L);
    private DataSize maxRequestSize = DataSize.ofMegabytes(1000L);
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个文件最大
        factory.setMaxFileSize(maxFileSize);
        // 设置总上传数据总大小
        factory.setMaxRequestSize(maxRequestSize);
        return factory.createMultipartConfig();
    }
}
