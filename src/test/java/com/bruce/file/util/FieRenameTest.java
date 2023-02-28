package com.bruce.file.util;

import java.io.File;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName file
 * @Date 2023/2/28 17:46
 * @Author fzh
 */
public class FieRenameTest {

    public static void main(String[] args) {
        String basePath = "C:\\Users\\fanzh23\\Desktop\\icon\\menu";
        File file = new File(basePath);
        File[] files = file.listFiles();

        for (File aFile : files) {
            String newName = "C:\\Users\\fanzh23\\Desktop\\icon\\menu\\icon-" + aFile.getName();
            aFile.renameTo(new File(newName));
        }

    }

}
