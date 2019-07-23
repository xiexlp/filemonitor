package js.com.file.utils;

/***
 * java文件批量改名
 */
		
		
import java.io.File;

public class FileMultRename {
	
	public static void main(String[] args) {
		
		String dir= "E:\\nodejs\\nodejslulingniu\\chapter14";
		renameMul(dir, "代码清单", "code");
		
	}
	
	public static void renameMul(String dir,String word,String newWord){
		File file = new File(dir);
		if(file.isFile()){
			return;
		}else {
			File[] files = file.listFiles();
			for(File f:files){
				String name = f.getName();
				String newName=name.replaceAll(word, newWord);
				System.out.println(name+":"+newName);
				
				String fromDir = dir+"\\"+name;
				String toDir = dir+"\\"+newName;
				renameDirectory(fromDir, toDir);
			}
		}
	}
	
	
	public static void renameDirectory(String fromDir, String toDir) {

	    File from = new File(fromDir);

	    if (!from.exists() || !from.isDirectory()) {
	      System.out.println("Directory does not exist: " + fromDir);
	      return;
	    }

	    File to = new File(toDir);

	    //Rename
	    if (from.renameTo(to))
	      System.out.println("Success!");
	    else
	      System.out.println("Error");

	  }

}
