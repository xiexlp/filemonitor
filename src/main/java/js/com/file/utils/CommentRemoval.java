package js.com.file.utils;//import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
//import java.io.FileNotFoundException;
import java.io.IOException;

/***
 * 删除java文件中的注释
 */
public class CommentRemoval {
    public static void main(String[] args){
        //String inpath = "E:/workstation/github/Computer_Algorithms/OJs/others/SampleClass.java";

        String inpath= "F:\\git\\java\\mar3\\filemonitor\\target\\contract-module\\contract-module-0.doc";
        //File file = new File(toFile);

        String outpath = inpath.substring(0, inpath.length() - 4) + ".txt";
        try{
            File infile = new File(inpath);
            File outfile = new File(outpath);
            if(infile.isFile() && infile.exists()){
                BufferedReader bufferedReader = new BufferedReader(new FileReader(infile));
                PrintWriter bufferedWriter = new PrintWriter(new FileWriter(outfile));
                char c;
                int i;
                while((i = bufferedReader.read()) != -1){
                    // state 0
                    c = (char)i;
                    if(c == '/'){ // state 3
                        i = bufferedReader.read();
                        c = (char)i;
                        if(c == '*'){ // state 4
                            while((i = bufferedReader.read()) != -1){
                                c = (char) i;
                                if(c == '*'){ // state 6
                                    i = bufferedReader.read();
                                    c = (char)i;
                                    if(c == '/'){
                                        break;
                                    }else{
                                        continue;
                                    }
                                }else{
                                    continue;
                                }
                            }
                        }else if(c == '/'){ // state 5
                            while((i = bufferedReader.read()) != -1){
                                c = (char)i;
                                if(c == '\n'){
                                    bufferedWriter.print(c);
                                    break;
                                }else{
                                    continue;
                                }
                            }
                        }else{
                            bufferedWriter.print("/" + c);
                            continue;
                        }
                    }else if(c == '"'){ // state 1
                        bufferedWriter.print(c);
                        while((i = bufferedReader.read()) != -1){
                            c = (char)i;
                            bufferedWriter.print(c);
                            if(c == '\\'){ // state 2
                                if((i = bufferedReader.read()) != -1){
                                    c = (char)i;
                                    bufferedWriter.print(c);
                                    if(c == '"'){
                                        continue;
                                    }
                                }
                            }else if(c == '"'){
                                break;
                            }
                        }
                    }else{
                        bufferedWriter.print(c);
                    }
                }
                bufferedWriter.close();
                bufferedReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}