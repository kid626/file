package com.bruce.file.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName file
 * @Date 2023/3/21 14:35
 * @Author fzh
 */
public class PdfUtil {

    /**
     * 创建一个简单的 pdf
     *
     * @param path
     * @param content
     */
    public static void createTextPDF(String path, String content) {
        //Step 1—Create a Document.
        Document document = new Document();
        try {
            //Step 2—Get a PdfWriter instance.
            PdfWriter.getInstance(document, new FileOutputStream(path));
            //Step 3—Open the Document.
            document.open();
            //Step 4—Add content.
            document.add(new Paragraph(content));
            //Step 5—Close the Document.
            document.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 页面大小，页面背景色，页边空白（外边距），标题，作者，主题，关键词
     */
    public static void pdf2(String path, String content) throws Exception {
        //页面大小
        Rectangle rect = new Rectangle(PageSize.B5.rotate());
        //页面背景色
        rect.setBackgroundColor(BaseColor.ORANGE);

        Document doc = new Document(rect);

        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(path));

        //PDF版本(默认1.4)
        writer.setPdfVersion(PdfWriter.PDF_VERSION_1_2);

        //文档属性
        doc.addTitle("Title@sample");
        doc.addAuthor("Author@rensanning");
        doc.addSubject("Subject@iText sample");
        doc.addKeywords("Keywords@iText");
        doc.addCreator("Creator@iText");

        //页边空白
        doc.setMargins(10, 20, 30, 40);

        doc.open();
        doc.add(new Paragraph(content));
        doc.close();
    }

}
