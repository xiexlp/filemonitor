package js.com.file.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 删除Java代码中的注释
 *
 * @author Alive
 * @build 2010-12-23
 */
public class DeleteComments {

    private static int count = 0;

    /**
     * 删除文件中的各种注释，包含//、/* * /等
     *
     * @param charset 文件编码
     * @param file    文件
     */
    public static void clearComment(File file, String charset) {
        String inpath = file.getAbsolutePath();
        String outpath = inpath.substring(0, inpath.length() - 4) + ".txt";
        File outFile = new File(outpath);
        try {
//递归处理文件夹
            if (!file.exists()) {
                return;
            }

            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    clearComment(f, charset); //递归调用
                }
                return;
            } else if (!file.getName().endsWith(".doc")) {
//非java文件直接返回
                return;
            }
            System.out.println("-----开始处理文件：" + file.getAbsolutePath());

//根据对应的编码格式读取
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
            StringBuffer content = new StringBuffer();
            String tmp = null;
            while ((tmp = reader.readLine()) != null) {
                content.append(tmp);
                content.append("\n");
            }
            String target = content.toString();
//String s = target.replaceAll("\\/\\/[^\\n]*|\\/\\*([^\\*^\\/]*|[\\*^\\/*]*|[^\\**\\/]*)*\\*\\/", ""); //本段正则摘自网上，有一种情况无法满足（/* ...**/），略作修改

            String mulLineComment="/\\*(.|\\r\\n)*\\*/";
            String lineComment="/\\*(\\*)?\\\r\\\n\\* MIT License(.*?)\\*/";
            String lineDoubleStarComment="/\\*(.*?)\\*/";
            Pattern pattern3 = Pattern.compile(lineComment, Pattern.DOTALL);  //特征是以/**开始，以*/结尾
            Matcher matcher3 = pattern3.matcher(target);
            target = matcher3.replaceAll("");
            String s = target;
            //String s = target.replaceAll(mulLineComment,"");
            //注释的，先注释一下
            //String s = target.replaceAll("\\/\\/[^\\n]*|\\/\\*([^\\*^\\/]*|[\\*^\\/*]*|[^\\**\\/]*)*\\*+\\/", "");
            //System.out.println(s);
            //使用对应的编码格式输出
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), charset));
            out.write(s);
            out.flush();
            out.close();
            count++;
            System.out.println("-----文件处理完成---" + count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearComment(String filePath, String charset) {
        clearComment(new File(filePath), charset);
    }

    public static void clearComment(String filePath) {
        clearComment(new File(filePath), "UTF-8");
    }

    public static void clearComment(File file) {
        clearComment(file, "UTF-8");
    }

    public static void main(String[] args) {
        clearComment("F:\\git\\java\\mar3\\filemonitor\\target\\contract-module\\contract-module-0.doc"); //删除目录下所有java文件注释
//删除某个具体文件的注释
//clearComment("D:\\proj\\scm\\action\\AbcdefgAction.java");
    }

}