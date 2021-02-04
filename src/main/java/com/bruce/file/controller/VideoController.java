package com.bruce.file.controller;

import com.bruce.file.util.FFmpegUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName file
 * @Date 2021/2/4 21:49
 * @Author Bruce
 */
@RestController
public class VideoController {

    /**
     * 获取视频略缩图
     *
     * @return 是否成功
     * @throws Exception
     */
    @GetMapping(value = "/video")
    public String getPic() throws Exception {
        String videoFile = "H:/video/big_buck_bunny.mp4";
        String frameFile = "H:/video/big_buck_bunny.jpg";
        FFmpegUtil.fetchFrame(videoFile, frameFile);
        return "success";
    }

}
