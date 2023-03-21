package com.bruce.file.util;

import org.junit.Test;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName file
 * @Date 2023/3/21 14:46
 * @Author fzh
 */
public class PdfUtilTest {

    @Test
    public void pdf() {
        PdfUtil.createTextPDF("C:\\Users\\fanzh23\\Desktop\\pdf\\test.pdf", "hello world");
    }


    @Test
    public void pdf2() throws Exception {
        PdfUtil.pdf2("C:\\Users\\fanzh23\\Desktop\\pdf\\test2.pdf", "hello world");
    }

}
