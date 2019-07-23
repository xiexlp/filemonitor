package js.com.file.utils;

import js.com.file.concat.FileUtil;

import java.io.File;

public class FileContentUtil {

    public static void main(String[] args) {
        String toDelStr="/**\n" +
                " * MIT License\n" +
                " *\n" +
                " * Copyright (c) 2017-2018 nuls.io\n" +
                " *\n" +
                " * Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
                " * of this software and associated documentation files (the \"Software\"), to deal\n" +
                " * in the Software without restriction, including without limitation the rights\n" +
                " * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
                " * copies of the Software, and to permit persons to whom the Software is\n" +
                " * furnished to do so, subject to the following conditions:\n" +
                " *\n" +
                " * The above copyright notice and this permission notice shall be included in all\n" +
                " * copies or substantial portions of the Software.\n" +
                " *\n" +
                " * THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
                " * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
                " * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
                " * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
                " * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
                " * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" +
                " * SOFTWARE.\n" +
                " */";

            String toFile= "F:\\git\\java\\mar3\\filemonitor\\target\\contract-module\\contract-module-0.doc";
            File file = new File(toFile);
            dealFileContent(file,toDelStr);


    }

    //对生成的内容进行过滤,如删除等操作
    public static void dealFileContent(File toFile, String toDelStr){
        String fileContent = FileUtil.readFileByLines(toFile.getAbsolutePath());
        fileContent.replaceAll(toDelStr,"");
        System.out.println(fileContent);
    }
}
