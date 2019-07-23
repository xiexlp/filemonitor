package js.com.file.concat;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class FilePart {
	
	
	String name;
	String preName;
	String suffixName;
	
	Pair pair;
	
	String toDirs;
	
	int index;
	
	String content;
	int beginLineNumber;
	
	List<File> srcFiles= new ArrayList();
	List<FileScan> fileScanList = new ArrayList();
	
	
	int totalLine=0;
	int totalFiles=0;
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<File> getSrcFiles() {
		return srcFiles;
	}
	public void setSrcFiles(List<File> srcFiles) {
		this.srcFiles = srcFiles;
	}
	public Pair getPair() {
		return pair;
	}
	public void setPair(Pair pair) {
		this.pair = pair;
	}
	
	public int getTotalLine() {
		return totalLine;
	}
	public void setTotalLine(int totalLine) {
		this.totalLine = totalLine;
	}
	public int getTotalFiles() {
		return totalFiles;
	}
	public void setTotalFiles(int totalFiles) {
		this.totalFiles = totalFiles;
	}
	
	
	
	
	
	public String getSuffixName() {
		return suffixName;
	}
	public void setSuffixName(String suffixName) {
		this.suffixName = suffixName;
	}
	
	public void showInfo(){
		int seq=0;
		int i=0;
		for(File f:srcFiles){
			seq = getIndex()*pair.getSize()+i;
			System.out.println("序号:"+seq);
			System.out.println(f.getAbsolutePath());
			i++;
		}
	}
	
	/**
	 * contribute a single file
	 */
	public void printInfoStat(){
		
		File toFile = new File(FileConcatFrame.targetDir+"//"+name);
		
		if(toFile.exists()){
			FileUtil.clearFile(toFile);
		}else {
			try {
				toFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		int seq=0;
		int i=0;
		for(File f:srcFiles){
			//seq = getIndex()*pair.getSize()+i;
			seq = pair.getBeginIndex()+i;
			System.out.println("序号:"+seq);
			StringBuffer sb= new StringBuffer(Integer.toString(seq));
			sb.append(":");
			//String content=FileUtil.readFileByLines(f.getAbsolutePath());
			
			FileScan fs = FileUtil.readFileScanFromBeginLineNumber(f.getAbsolutePath(),getBeginLineNumber());
			
			//sb.append(content);
			sb.append(fs.getContent());
			//FileUtil.appendMethodA(toFile.getAbsolutePath(), sb.toString());
			i++;
			totalLine=totalLine+fs.getLine();
			totalFiles++;
		}
	}
	

	public String createFileFullName(String toDirs,String name,String suffixName){
		String fullName = toDirs+"\\"+name+suffixName;
		return fullName;
	}


	public void process(){
		File toDir = new File(toDirs);
		if(!toDir.exists()){
			toDir.mkdirs();
		}
		System.out.println("getsuffixName:"+getSuffixName());
		//要输出的文件名
		File toFile = new File(createFileFullName(toDirs,name,suffixName));
		if(toFile.exists()){
			//先有个清空的动作
			FileUtil.clearFile(toFile);
		}else {
			try {
				toFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		int seq=0;
		int i=0;
		for(File f:srcFiles){
			seq = getIndex()*pair.getSize()+i;
			System.out.println("序号:"+seq);
			StringBuffer sb= new StringBuffer(Integer.toString(seq));
			sb.append(":");
			//String content=FileUtil.readFileByLines(f.getAbsolutePath());
			
			FileScan fs = FileUtil.readFileScanFromBeginLineNumber(f.getAbsolutePath(),getBeginLineNumber());
			
			//sb.append(content);
			sb.append(fs.getContent());
			FileUtil.appendMethodA(toFile.getAbsolutePath(), sb.toString());
			i++;
			
			totalLine=totalLine+fs.getLine();
			totalFiles++;
		}
	}

	//对生成的内容进行过滤,如删除等操作
	public void dealFileContent(File toFile,String toDelStr){
		String fileContent = FileUtil.readFileByLines(toFile.getAbsolutePath());
		fileContent.replaceAll(toDelStr,"");
	}


	public String getPreName() {
		return preName;
	}
	public void setPreName(String preName) {
		this.preName = preName;
	}
	public String getToDirs() {
		return toDirs;
	}
	public void setToDirs(String toDirs) {
		this.toDirs = toDirs;
	}

	public int getBeginLineNumber() {
		return beginLineNumber;
	}

	public void setBeginLineNumber(int beginLineNumber) {
		this.beginLineNumber = beginLineNumber;
	}
}
