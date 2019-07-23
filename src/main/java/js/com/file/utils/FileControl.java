package js.com.file.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FileControl {
	
	
	public static void main(String[] args) throws Exception{
		
		//testSingleDirSize(null);
		
		File rootDir = new File("F:\\");
		File[] files = rootDir.listFiles();
		int l=files.length;
	
		
		for(File f:files){
			if(f.isFile()){
				long size = f.length();
				System.out.println("name:"+f.getName()+" size:"+size);
			}
			//if(f.isFile()) continue;
			long size = getFileSize(f);
			System.out.println("name:"+f.getName()+" size:"+FormetFileSize(size));
		}
	}
	
	
	public static void testSingleDirSize(String[] args) throws Exception{
		//singleDirSize();
		File f = new File("E:\\Users");
		long size = getFileSize(f);
		System.out.println(FormetFileSize(size));
		//System.out.println(getFileSize(f));
	}
	
	
	/***
	 * 可用的
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFileSize(File f)throws Exception//取得文件夹大小
    {
       long size = 0;
       File flist[] = f.listFiles();
       if(flist==null) return 0;
       for (int i = 0; i < flist.length; i++)
       {
           if (flist[i].isDirectory())
           {
               size = size + getFileSize(flist[i]);
           } else
           {
               size = size + flist[i].length();
           }
       }
       return size;
    }
	
	public static String FormetFileSize(long fileS) {//转换文件大小
	       DecimalFormat df = new DecimalFormat("#.00");
	       String fileSizeString = "";
	       if (fileS < 1024) {
	           fileSizeString = df.format((double) fileS) + "B";
	       } else if (fileS < 1048576) {
	           fileSizeString = df.format((double) fileS / 1024) + "K";
	       } else if (fileS < 1073741824) {
	           fileSizeString = df.format((double) fileS / 1048576) + "M";
	       } else {
	           fileSizeString = df.format((double) fileS / 1073741824) +"G";
	       }
	       return fileSizeString;
	    }
	
	
	public static void multiChildSize() {
		File f = new File("e:\\");
		File[] files = f.listFiles();
		
		List<FileEx> fileExs = new ArrayList<>();
		
		for(File ff:files){
			FileEx fileEx = new FileEx();
			fileEx.setFile(ff);
			int initSize =0;
			long size = getDirSize(ff, initSize);
			fileEx.setSize(size);
			fileExs.add(fileEx);
		}
		
		for(FileEx fEx:fileExs){
			System.out.println(fEx.getFile().getName()+" size:"+fEx.getSize()+"");
		}
	}
	
	public static void singleDirSize() {
		//File f = new File("e:\\");'
				long initSize= 0;
				String dir = "E:\\net";
				File file = new File(dir);
				long dirSize=getDirSize(file,initSize);
				System.out.println(getSizeFormat(dirSize));
	}
	
	
	public static String getSizeFormat(long fileS){
		 String size="";
		 DecimalFormat df = new DecimalFormat("#.00"); 
         if (fileS < 1024) {
             size = df.format((double) fileS) + "BT";
         } else if (fileS < 1048576) {
             size = df.format((double) fileS / 1024) + "KB";
         } else if (fileS < 1073741824) {
             size = df.format((double) fileS / 1048576) + "MB";
         } else {
             size = df.format((double) fileS / 1073741824) +"GB";
         }
         return size;
	}
	
	
	public static long getDirSize(File file,long initSize) {
		//File file = new File(dir);
		System.out.println("file name:"+file.getName());
		//为文件则直接跳出来
		if(file.isFile()){
			return file.length()+initSize;
		}
		
		File[] files = file.listFiles();
		//空文件夹
		if(files==null) return initSize;
		for(File f:files){
			if(f.isDirectory()){
				initSize=initSize+getDirSize(f,initSize);
			}else {
				initSize=initSize+f.length();
			}
		}
		return initSize;
	}
	
	
	private static void listSize(String dir) {
		File f = new File(dir);
		File[] files = f.listFiles();
		
		System.out.println("file num:"+files.length);
		
		
		for(File fil:files){
			System.out.println(fil.getName());
		}
		
		
		List<FileEx> fileExs = new ArrayList<>();
		
	
		for(File fil:files){
			//if(fil!=null){
				FileEx fileEx = new FileEx();
				long size = countSize(fil,0);
				fileEx.setSize(size);
				fileEx.setFile(fil);
				fileExs.add(fileEx);
			//}
		}
		
		System.out.println(fileExs.size());
		
		for(FileEx fileEx:fileExs){
			//if(fileEx!=null){
				System.out.println(fileEx.getFile().getName()+" size:"+fileEx.getSize()/(1024)+" MB");
			//}
		}
	
	}
	
	
	public static long countSize(File fil,long size){
		//File f = new File(dir);
		
		if(fil.isDirectory()){
			File[] files = fil.listFiles();
			//空文件夹
			if(files!=null){
				for(File file:files){
					if(!file.isFile()){
						size = size +countSize(file,size);
					}else {
						size = getSize(file, size);
					}	
				}
			}else {
				
			}
		//文件
		}else{
			size = size+getSize(fil, 0);
		}
		
		return size;
	}
	
	//以GB为单位
	public static long getSize(File file,long size) {
		if(file.isFile()){
			size  = (size+ file.length())/1024;
			System.out.println("filename:"+file.getAbsolutePath()+" size:"+size+"KB");
		}
		return size;
	}
	
	
	
	
	public static void test(String[] args) throws Exception{
		
		
		String baseDir = "";
		String packageStr = "com.js.transform.testdir";
		String newDir = packageStr.replace(".", "/");
		System.out.println(newDir);
		String[] dirs = packageStr.split("\\.");
		//����ֱ�ӽ����༶�ļ�Ŀ¼
		File f = new File(newDir);
		if(!f.exists()) {
			System.out.println(f.exists());
			//f.mkdirs();
			System.out.println(f.exists());
		}
		
		File javaFile = new File(newDir+"/"+"b.java");
		if(!javaFile.exists()){
			System.out.println("not exist");
			javaFile.createNewFile();
		}else {
			System.out.println("file exists! file will be replaced");
		}
		
		
		FileOutputStream fos = new FileOutputStream(javaFile);
		OutputStreamWriter os = new OutputStreamWriter(fos,"utf-8");
		os.write("new 12345");
		os.close();
		fos.close();
		
		
		
		for(String dir:dirs){
			System.out.println(dir);
			
			File file = new File(newDir);
			//�ж�Ŀ¼�Ƿ����
			System.out.println(file.exists());
			//if(!file.exists()) file.mkdirs();
			System.out.println(file.exists());
			
		}
		
		System.out.println(System.getProperty("user.dir"));
		
		File directory1 = new File("");//�趨Ϊ��ǰ�ļ��� 
		try{ 
		    System.out.println(directory1.getCanonicalPath());//��ȡ��׼��·�� 
		    System.out.println(directory1.getAbsolutePath());//��ȡ����·�� 
		}catch(Exception e){
			e.printStackTrace();
		} 
		
		try {
			File directory = new File("E:\\pic"); 
			System.out.println(directory.getCanonicalPath()); //�õ�����C:/transform/abc
			System.out.println(directory.getAbsolutePath());    //�õ�����C:/transform/abc
			System.out.println(directory.getPath());                    //�õ�����abc 

			directory = new File("."); 
			System.out.println(directory.getCanonicalPath()); //�õ�����C:/transform
			System.out.println(directory.getAbsolutePath());    //�õ�����C:/transform/.
			System.out.println(directory.getPath());                    //�õ�����. 

			directory = new File(".."); 
			System.out.println(directory.getCanonicalPath()); //�õ�����C:/ 
			System.out.println(directory.getAbsolutePath());    //�õ�����C:/transform/..
			System.out.println(directory.getPath());      
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		         
		
	}

}
