package com.bruce.file.util;

import org.junit.Test;

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

}
