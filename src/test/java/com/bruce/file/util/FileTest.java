package com.bruce.file.util;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName file
 * @Date 2023/2/24 14:25
 * @Author fzh
 */
public class FileTest {

    // 获取所有 controller 文件
    private static List<File> allFile = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        // 1 获取所有 Controller 文件
        String basePath = "D:\\document\\workspace\\idea_workspace\\smart-park\\local-park\\park3-local-base-api\\park3-local-base-api-web\\src\\main\\java\\com\\unicom\\park\\web\\controller";
        File file = new File(basePath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                fun(file1);
            }
        }
        // allFile.clear();
        // allFile.add(new File("D:\\document\\workspace\\idea_workspace\\smart-park\\local-park\\park3-local-base-api\\park3-local-base-api-web\\src\\main\\java\\com\\unicom\\park\\web\\controller\\contentOperation\\admin\\MessageCommentController.java"));
        // 2 遍历文件，往含有 @AuthResource 注解的行前面添加 @SaCheckPermission
        for (File controller : allFile) {
            write2File(controller, "@AuthResource");
        }

    }

    private static void fun(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                fun(file1);
            }
        } else {
            if (file.getName().contains("Controller")) {
                allFile.add(file);
            }
        }
    }

    private static void write2File(File testFile, String flagStr) throws IOException {
        // 创建临时文件
        File outFile = File.createTempFile(testFile.getName(), ".tmp");
        // 源文件输入流
        FileInputStream fis = new FileInputStream(testFile);
        BufferedReader in = new BufferedReader(new InputStreamReader(fis));
        // 源文件输出流
        FileOutputStream fos = new FileOutputStream(outFile);
        PrintWriter out = new PrintWriter(fos);
        // 保存一行数据
        String thisLine;
        // jvm 退出 临时文件删除
        outFile.deleteOnExit();
        // 是否已经修改过了
        boolean flag = false;
        while ((thisLine = in.readLine()) != null) {
            //  当读取到目标行时 写入需要写入的内容
            if (thisLine.contains("import cn.dev33.satoken.annotation.SaCheckPermission;")) {
                flag = true;
                break;
            }
            if (thisLine.contains("import com.unicom.middleware.unicom.common.dto.Result;")) {
                out.println("import cn.dev33.satoken.annotation.SaCheckPermission;");
            }
            if (thisLine.contains(flagStr)) {
                String code = getCode(thisLine);
                out.println(MessageFormat.format("\t@SaCheckPermission(\"{0}\")", code));
            }
            // 输出读取到的数据
            if (!thisLine.contains(flagStr)) {
                out.println(thisLine);
            }
        }
        // 各种关
        out.flush();
        out.close();
        in.close();
        // 没有修改过
        if (!flag) {
            // 删除原始文件
            testFile.delete();
            // 把临时文件改名为原文件名
            outFile.renameTo(testFile);
        } else {
            outFile.delete();
        }
    }


    private static String getCode(String line) {
        String regex = "code = \"(.*?)\"";
//    @AuthResource(name = "楼宇管理-保存", code = "buildingManage:add", parentCode = "buildingManage_menu", method = MethodEnum.POST)
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(line);
        if (matcher.find()) {
            String result = matcher.group();
            return result.replaceAll("code", "").replaceAll("=", "").replaceAll("\"", "").trim();
        }
        return "";
    }

}
