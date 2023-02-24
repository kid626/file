package com.bruce.file.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName file
 * @Date 2021/4/1 11:05
 * @Author fzh
 */
public class FileUtilTest {

    @Test
    public void test1() {
        String fileName = "test1.txt";
        FileUtil.fileWriter(fileName, "test1");
        System.out.println(FileUtil.fileReader(fileName));
    }


    @Test
    public void test2() {
        String fileName = "test2.txt";
        FileUtil.bufferWriter(fileName, "test2");
        System.out.println(FileUtil.bufferReader(fileName));
    }

    @Test
    public void test3() {
        String fileName = "test3.txt";
        FileUtil.streamWriter(fileName, "test3");
        System.out.println(FileUtil.streamReader(fileName));
    }

    @Test
    public void printExif() throws ImageProcessingException, IOException {
        File file = new File("D:\\pictures\\hutool\\ceshi.jpg");
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.println(tag + " = " + tag.getDescription());
            }
        }
    }

    @Test
    public void correctImg() {
        FileOutputStream fos = null;
        try {
            // 原始图片
            File srcFile = new File("D:\\pictures\\hutool\\ceshi.jpg");

            // 获取偏转角度
            int angle = getAngle(srcFile);
            if (angle != 90 && angle != 270) {
                return;
            }

            // 原始图片缓存
            BufferedImage srcImg = ImageIO.read(srcFile);

            // 宽高互换
            // 原始宽度
            int imgWidth = srcImg.getHeight();
            // 原始高度
            int imgHeight = srcImg.getWidth();

            // 中心点位置
            double centerWidth = ((double) imgWidth) / 2;
            double centerHeight = ((double) imgHeight) / 2;

            // 图片缓存
            BufferedImage targetImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);

            // 旋转对应角度
            Graphics2D g = targetImg.createGraphics();
            g.rotate(Math.toRadians(angle), centerWidth, centerHeight);
            g.drawImage(srcImg, (imgWidth - srcImg.getWidth()) / 2, (imgHeight - srcImg.getHeight()) / 2, null);
            g.rotate(Math.toRadians(-angle), centerWidth, centerHeight);
            g.dispose();

            // 输出图片
            fos = new FileOutputStream("D:\\pictures\\hutool\\ceshi_correct.jpg");
            ImageIO.write(targetImg, "jpg", fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取图片旋转角度
     *
     * @param file 上传图片
     * @return
     */
    private int getAngle(File file) throws Exception {
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                if ("Orientation".equals(tag.getTagName())) {
                    String orientation = tag.getDescription();
                    if (orientation.contains("90")) {
                        return 90;
                    } else if (orientation.contains("180")) {
                        return 180;
                    } else if (orientation.contains("270")) {
                        return 270;
                    }
                }
            }
        }
        return 0;
    }

}
