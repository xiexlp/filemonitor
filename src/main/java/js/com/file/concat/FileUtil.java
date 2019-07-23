package js.com.file.concat;

import java.io.*;

public class FileUtil {
	
	static String encoding="utf-8";
	
	 public static String readFileByLines(String fileName) {
	        File file = new File(fileName);
	        BufferedReader reader = null;
	        StringBuffer sb = new StringBuffer(fileName+"\r\n");
	        try {
	            //System.out.println("以行为单位读取文件内容，一次读一整行：");
	            reader = new BufferedReader(new InputStreamReader(  
	                    new FileInputStream(file),encoding));
	            String tempString = null;
	            int line = 1;
	            // 一次读入一行，直到读入null为文件结束
	            while ((tempString = reader.readLine()) != null) {
	                // 显示行号
	               // System.out.println("line " + line + ": " + tempString);
	                sb.append(tempString).append("\r\n");
	                line++;
	            }
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	        
	        return sb.toString();
	    }
	 
	 
	 
	 /***
	  * single file info,such as content,line,original line etc..
	  * @param fileName
	  * @return
	  */
	 public static FileScan readFileScan(String fileName){
		 FileScan fs = new FileScan();
		 File file = new File(fileName);
	     BufferedReader reader = null;
	     StringBuffer sb = new StringBuffer(fileName+"\r\n");
	     
	     int line = 1;
	        try {
	            //System.out.println("以行为单位读取文件内容，一次读一整行：");
	            reader = new BufferedReader(new InputStreamReader(  
	                    new FileInputStream(file),encoding));
	            String tempString = null;
	           
	            // 一次读入一行，直到读入null为文件结束
	            while ((tempString = reader.readLine()) != null) {
	                // 显示行号
	               // System.out.println("line " + line + ": " + tempString);
					//前面的注释不要
					if(line>=24)
						sb.append(tempString).append("\r\n");
	                line++;
	            }
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	        
	        fs.setContent(sb.toString());
	        fs.setLine(line);
	        fs.setLineOriginal(line-1);
	        return fs;	 
	 }

	/***
	 * single file info,such as content,line,original line etc..
	 * @param fileName
	 * @return
	 */
	public static FileScan readFileScanFromBeginLineNumber(String fileName,int beginLineNumber){
		FileScan fs = new FileScan();
		File file = new File(fileName);
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer(fileName+"\r\n");

		int line = 1;
		//
		int appendLineNumber=1;
		try {
			//System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file),encoding));
			String tempString = null;

			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				// System.out.println("line " + line + ": " + tempString);
				//前面的注释不要
				if(line>=beginLineNumber) {
					sb.append(tempString).append("\r\n");
					appendLineNumber++;
				}
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}

		fs.setContent(sb.toString());
		fs.setLine(appendLineNumber);
		fs.setLineOriginal(line-1);
		return fs;
	}
	 	
		public static void appendMethodA(String fileName, String content) {
	        try {
	            // 打开一个随机访问文件流，按读写方式
	            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
	            // 文件长度，字节数
	            long fileLength = randomFile.length();
	            //将写文件指针移到文件尾。
	            randomFile.seek(fileLength);
	            randomFile.writeBytes("\r\n");
	            randomFile.write(content.getBytes(encoding));
	            randomFile.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }


	public static void writeFileContent(String path, String content) throws Exception {
		File f = new File(path);
		FileOutputStream fos = new FileOutputStream(f);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
		osw.write(content);
		osw.flush();
		osw.close();
		fos.close();

	}


		
	 
	 
	 public  static void clearFile(File f){
			FileWriter fw;
			try {
				fw = new FileWriter(f);
				fw.write("");
				fw.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	 
	 
	 
	 public static String getLastDirName(String dirname){
		 int index = dirname.lastIndexOf("\\");
		 String lastDirName = dirname.substring(index+1);
		 return lastDirName;
	 }
	 
	 public static void main(String[] args) {
		 String dirname= "E:\\git\\coin\\inchain\\inchain-core";
		 String lastDirName = getLastDirName(dirname);
		 System.out.println(lastDirName);
		 
		 
	}
	
	

}
