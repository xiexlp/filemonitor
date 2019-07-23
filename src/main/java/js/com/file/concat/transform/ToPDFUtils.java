package js.com.file.concat.transform;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
  import com.itextpdf.text.DocumentException;
  import com.itextpdf.text.Paragraph;
  import com.itextpdf.text.pdf.PdfWriter;

 public class ToPDFUtils {

     public static void main(String[] args) throws IOException, DocumentException {

         // 1.新建document对象
        Document document = new Document();

        // 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
       // 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
        File f = new File("F:\\pdftest\\transform.pdf");
        if(!f.exists()) f.createNewFile();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(f));

       // 3.打开文档
        document.open();

       // 4.添加一个内容段落
        document.add(new Paragraph("Hello World!"));

       // 5.关闭文档
        document.close();

   }

   public static void toPdf(String content,File f){
      try {
       // 1.新建document对象
       Document document = new Document();
       // 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
       // 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
       //File f = new File("F:\\pdftest\\transform.pdf");
       if(!f.exists()) f.createNewFile();
       PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(f));
       // 3.打开文档
       document.open();
       // 4.添加一个内容段落
       document.add(new Paragraph(content));
       // 5.关闭文档
       document.close();
      }catch (Exception e){
       e.printStackTrace();
      }

   }

 }