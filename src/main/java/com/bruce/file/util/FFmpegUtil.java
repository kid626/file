package com.bruce.file.util;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName file
 * @Date 2021/2/4 21:49
 * @Author Bruce
 */
public class FFmpegUtil {


    /**
     * @param videoFile 视频路径
     * @param frameFile 缩略图保存路径
     * @throws Exception exception
     */
    public static void fetchFrame(String videoFile, String frameFile) throws Exception {
        long start = System.currentTimeMillis();
        File targetFile = new File(frameFile);
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videoFile);
        ff.start();
        //获取时长：总帧数 / 每帧所花费的秒数
        int ftp = ff.getLengthInFrames();
        System.out.println("时长 " + ftp / ff.getFrameRate());
        int length = ff.getLengthInFrames();
        int i = 0;
        Frame f = null;
        while (i < length) {
            // 去掉前50帧，避免出现全黑的图片，依自己情况而定
            f = ff.grabImage();
            if ((i > 50) && (f.image != null)) {
                break;
            }
            i++;
        }
        ImageIO.write(frameToBufferedImage(f), "jpg", targetFile);
        ff.flush();
        ff.stop();
        System.out.println(System.currentTimeMillis() - start);
    }

    public static BufferedImage frameToBufferedImage(Frame frame) {
        //创建BufferedImage对象
        Java2DFrameConverter converter = new Java2DFrameConverter();
        return converter.getBufferedImage(frame);
    }

}
