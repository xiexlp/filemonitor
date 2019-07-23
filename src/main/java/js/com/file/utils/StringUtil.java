package js.com.file.utils;

public class StringUtil {

    public static void main(String[] args) {

        String path ="F:\\git\\java\\mycat\\Mycat-Server";
        String s = getNameFromPath(path);
        System.out.println(s);
    }

    public static String getNameFromPath(String path){
        int index = path.lastIndexOf("\\");
        String s = path.substring(index+1,path.length());
        return s;
    }


}
