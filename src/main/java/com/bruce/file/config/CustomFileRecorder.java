package com.bruce.file.config;

import cn.xuyanwu.spring.file.storage.FileInfo;
import cn.xuyanwu.spring.file.storage.recorder.FileRecorder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName file
 * @Date 2023/4/4 16:16
 * @Author fzh
 */
@Service
public class CustomFileRecorder implements FileRecorder {

    private static final ConcurrentHashMap<String, FileInfo> MAP = new ConcurrentHashMap<>();

    @Override
    public boolean record(FileInfo fileInfo) {
        MAP.putIfAbsent(fileInfo.getUrl(), fileInfo);
        return true;
    }

    @Override
    public FileInfo getByUrl(String url) {
        return MAP.get(url);
    }

    @Override
    public boolean delete(String url) {
        MAP.remove(url);
        return true;
    }
}
