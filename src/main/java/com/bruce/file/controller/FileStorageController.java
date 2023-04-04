package com.bruce.file.controller;

import cn.xuyanwu.spring.file.storage.FileInfo;
import cn.xuyanwu.spring.file.storage.FileStorageService;
import cn.xuyanwu.spring.file.storage.ProgressListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName file
 * @Date 2023/4/4 15:20
 * @Author fzh
 */
@RestController
@RequestMapping("/storage")
public class FileStorageController {

    @Resource
    private FileStorageService fileStorageService;
    @Resource
    private HttpServletResponse response;

    @PostMapping("/upload")
    public FileInfo upload(MultipartFile file) {
        FileInfo fileInfo = fileStorageService.of(file).setObjectId(UUID.randomUUID().toString()).upload();
        return fileInfo;
    }

    @PostMapping("/uploadThumbnail")
    public FileInfo uploadThumbnail(MultipartFile file) {
        FileInfo fileInfo = fileStorageService.of(file).setObjectId(UUID.randomUUID().toString())
                .image(img -> img.size(1000, 1000)).thumbnail(200, 200).upload();
        return fileInfo;
    }

    @GetMapping("/download")
    public void download(String url) throws IOException {
        FileInfo fileInfo = fileStorageService.getFileInfoByUrl(url);
        fileStorageService.download(fileInfo).setProgressMonitor(new ProgressListener() {
            @Override
            public void start() {
                System.out.println("下载开始");
            }

            @Override
            public void progress(long progressSize, long allSize) {
                System.out.println("已下载 " + progressSize + " 总大小" + allSize);
            }

            @Override
            public void finish() {
                System.out.println("下载结束");
            }
        }).outputStream(response.getOutputStream());
    }

}
