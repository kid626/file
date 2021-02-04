package com.bruce.file.controller;

import com.bruce.file.entity.MergeDTO;
import com.bruce.file.entity.Result;
import com.bruce.file.entity.UploadDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName file
 * @Date 2021/2/4 21:49
 * @Author Bruce
 */
@RestController
@Slf4j
public class FilePartController {

    @Value("${upload.file.path}")
    private String destPath;

    @Value("${upload.file.path.temp}")
    private String tempPath;

    private static final String SUFFIX = ".part";

    private static final List<String> SUFFIX_LIST = Arrays.asList("png", "mp4", "pdf", "txt", "mp3", "avi", "flv");

    /**
     * 校验当前md5的文件是否存在
     *
     * @return ResponseEntity<Void>
     */
    @PostMapping("/chunk/file/check")
    public Result<String> checkFile(@RequestParam(value = "md5") String md5, @RequestParam(value = "name") String name) {
        log.info("检查文件是否存在,md5={},name={}", md5, name);
        String exist = "N";
        //文件存放目录
        File outFile = new File(destPath + File.separator + name);
        if (outFile.exists()) {
            exist = "Y";
        }
        //实际项目中，这个md5唯一值，应该保存到数据库或者缓存中，通过判断唯一值存不存在，来判断文件存不存在，这里我就不演示了
        return Result.success(exist);
    }

    /**
     * 校验当前分片是否存在
     *
     * @return ResponseEntity<Void>
     */
    @PostMapping("/chunk/check")
    public Result<String> check(@RequestParam(value = "md5") String md5,
                                @RequestParam(value = "chunk") Integer chunk) {
        log.info("检验当前分片是否存在,md5={},chunk={}", md5, chunk);
        String exist = "N";
        //分片存放目录
        File outFile = new File(tempPath + File.separator + md5, chunk + SUFFIX);
        if (outFile.exists()) {
            exist = "Y";
        }
        return Result.success(exist);
    }

    /**
     * 分片上传
     *
     * @return ResponseEntity<Void>
     */
    @PostMapping("/chunk/upload")
    public Result<String> upload(@Validated UploadDTO dto) throws IOException {
        log.info("分片上传:dto={}", dto);
        MultipartFile file = dto.getFile();
        if (file == null) {
            return Result.fail("文件不存在!");
        }
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isEmpty(originalFilename)) {
            return Result.fail("文件不存在!");
        }
        String[] split = originalFilename.split("\\.");
        String suffix = split[split.length - 1];
        if (!SUFFIX_LIST.contains(suffix)) {
            return Result.fail("不支持的文件格式!");
        }
        if (dto.getChunk() == null) {
            dto.setChunk(0);
        }
        File outFile = new File(tempPath + File.separator + dto.getMd5(), dto.getChunk() + SUFFIX);
        InputStream inputStream = file.getInputStream();
        FileUtils.copyInputStreamToFile(inputStream, outFile);
        return Result.success();
    }

    /**
     * 合并所有分片
     */
    @PostMapping("/chunk/merge")
    public Result<String> merge(@Validated MergeDTO dto) throws Exception {
        log.info("合并:dto={}", dto);
        // 临时文件夹
        File tempFileDir = new File(tempPath + File.separator + dto.getMd5());

        if (!tempFileDir.exists()) {
            if (!tempFileDir.isDirectory()) {
                FileUtils.forceDelete(tempFileDir);
            }
            return Result.fail("临时目录不存在!");
        }

        File[] files = tempFileDir.listFiles();
        if (files == null || files.length == 0 || dto.getChunks() != files.length) {
            FileUtils.deleteDirectory(tempFileDir);
            return Result.fail("分片文件缺失，请重新上传!");
        }

        // 目标文件
        File destFile = new File(destPath + File.separator + dto.getName());
        FileOutputStream fos = new FileOutputStream(destFile, true);
        FileInputStream fis = new FileInputStream(destFile);
        String md5;
        try {
            for (int i = 0; i < dto.getChunks(); i++) {
                File partFile = new File(tempPath + File.separator + dto.getMd5(), i + SUFFIX);
                FileUtils.copyFile(partFile, fos);
            }
        } finally {
            // 合并成功后获取 md5 值
            md5 = DigestUtils.md5DigestAsHex(fis);
            fos.close();
            fis.close();
        }
        if (!md5.equals(dto.getMd5())) {
            log.info("合并后的文件md5={}", md5);
            FileUtils.forceDelete(destFile);
            FileUtils.deleteDirectory(tempFileDir);
            return Result.fail("文件 md5 不匹配");
        }
        FileUtils.deleteDirectory(tempFileDir);
        return Result.success();

    }
}
