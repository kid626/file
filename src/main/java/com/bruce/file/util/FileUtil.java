package com.bruce.file.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName file
 * @Date 2021/4/1 10:41
 * @Author fzh
 */
@Slf4j
public class FileUtil {


    public static void fileWriter(String fileName, String str) {
        File file = new File(fileName);
        //如果文件不存在，创建文件
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                log.error("创建文件出错:{}", e.getMessage());
            }
        }
        //创建FileWriter对象
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            //向文件中写入内容
            writer.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
            } catch (IOException e) {
                log.error("关闭文件流失败:{}", e.getMessage());
            }

        }
    }

    public static String fileReader(String fileName) {
        //创建FileReader对象，读取文件中的内容
        FileReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new FileReader(new File(fileName));
            char[] chars = new char[100];
            while (reader.read(chars, 0, chars.length) != -1) {
                sb.append(chars);
            }
        } catch (Exception e) {
            log.error("文件读取出错:{}", e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                log.error("关闭文件流失败:{}", e.getMessage());
            }
        }
        return sb.toString();
    }

    public static void bufferWriter(String fileName, String str) {
        File file = new File(fileName);
        //如果文件不存在，创建文件
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                log.error("创建文件出错:{}", e.getMessage());
            }
        }
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            //向文件中写入内容
            bw.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.flush();
                    bw.close();
                }
            } catch (IOException e) {
                log.error("关闭文件流失败:{}", e.getMessage());
            }

        }
    }

    public static String bufferReader(String fileName) {
        //创建FileReader对象，读取文件中的内容
        FileReader reader = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new FileReader(new File(fileName));
            br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            log.error("文件读取出错:{}", e.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                log.error("关闭文件流失败:{}", e.getMessage());
            }
        }
        return sb.toString();
    }

    /**
     * 使用字节流写入
     * @param fileName
     * @param str
     */
    public static void streamWriter(String fileName, String str) {
        File file = new File(fileName);
        //如果文件不存在，创建文件
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                log.error("创建文件出错:{}", e.getMessage());
            }
        }
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        try {
            fos = new FileOutputStream(file);
            osw = new OutputStreamWriter(fos);
            //向文件中写入内容
            osw.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (osw != null) {
                    osw.flush();
                    osw.close();
                }
                if (fos != null) {
                    fos.flush();
                    fos.close();
                }
            } catch (IOException e) {
                log.error("关闭文件流失败:{}", e.getMessage());
            }

        }
    }

    /**
     * 使用字节流读取
     * @param fileName
     * @return
     */
    public static String streamReader(String fileName) {
        //创建FileReader对象，读取文件中的内容
        FileInputStream fis = null;
        StringBuilder sb = new StringBuilder();
        try {
            fis = new FileInputStream(new File(fileName));
            byte[] bys = new byte[100];
            while (fis.read(bys) != -1) {
                sb.append(new String(bys));
            }
        } catch (Exception e) {
            log.error("文件读取出错:{}", e.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                log.error("关闭文件流失败:{}", e.getMessage());
            }
        }
        return sb.toString();
    }

}
