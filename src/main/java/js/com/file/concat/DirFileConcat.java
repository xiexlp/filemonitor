package js.com.file.concat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import js.com.file.concat.transform.StringTest;


/**
 * 
 * 将目录中的文件集中起来，组成一个文件，打印
 * @author Administrator
 *
 */
public class DirFileConcat {
	
	
	private List<File> fileList= new ArrayList();
	
	//private String[] filterFileTypes={".java",".bat"};
	
	private Set<String> filterTypeSet= new HashSet();
	
	
	String encoding="utf-8";
	

	String srcDir;
	List<String> srcDirList=new ArrayList();

	List<String> noSrcDirList = new ArrayList<>();
	
	List<FilePart> fileItemList = new ArrayList<FilePart>();
	
	Map<Integer, FileScan> beginLineFileScanMap = new TreeMap();
	
	Map<Integer, FileScan> indexFileScanMap = new HashMap<Integer, FileScan>();
	
	
	List<Pair> pairList = new ArrayList<Pair>();
	
	int partionNum=1;
	String preName;
	String suffixName;
	int beginLineNumber=0;
	
	
	int fileDirLevel=0;
	
	
	//总行数
	int totalLines;
	//总文件数
	int totalFileNum;
	
	String toDirs;
	
	
	
		
	public static void main(String[] args) {
		DirFileConcat dirFileConcat = new DirFileConcat();

		//指定目录
		String dir= new String("E:\\git\\coin\\inchain\\inchain-core\\src\\main\\java");
		
		File toFile = new File("inchain.txt");
		if(!toFile.exists()){
			try {
				toFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			dirFileConcat.clearFile(toFile);
		}
		
		File file = new File(dir);
		
		dirFileConcat.processDir(file);
		List<File> fileList = dirFileConcat.getFileList();
		System.out.println(fileList.size());
		
		
		for(File f:fileList){
			String content=dirFileConcat.readFileByLines(f.getAbsolutePath());
			dirFileConcat.appendMethodA(toFile.getAbsolutePath(), content);
		}
		
	
		String sumContent = dirFileConcat.readFileByLines(toFile.getAbsolutePath());
		System.out.println(sumContent);
		
	}
	
	
	public void computerTotalFiles(){
		//processDir(new File(srcDir));
		
		
		int sizeOfDirList = srcDirList.size();
		for(String srcDirItem:srcDirList){
			processDir(new File(srcDirItem));
		}
		
		
		//所有的文件
		int size = fileList.size();
		
		
	}
	
	public void computerFileScanLineIndex(){
		int size1 = srcDirList.size();
		System.out.println("size of dir list222222222222222222:"+size1);
		for(String srcDirItem:srcDirList){
			processDir(new File(srcDirItem));
		}
		
		//processDir(new File(srcDir));
		
		//所有的文件
		int size = fileList.size();
		
		int total =0;
		
		for(int i=0;i<size;i++){
			//按顺序的
			File file = fileList.get(i);
			FileScan fs = FileUtil.readFileScan(file.getAbsolutePath());
			fs.setTotalIndex(i);
			fs.setFromLine(total);
			total = total + fs.getLine();
			fs.setEndLine(total);
			System.out.println("map formLine:"+fs.getFromLine());
			beginLineFileScanMap.put(fs.getFromLine(), fs);
		}
		totalLines = total;
		System.out.println("总行数:"+totalLines);
		int averagePartLine = total/getPartionNum();
		System.out.println("每块行数:"+averagePartLine);
		
		//显示当前所有的key
		getMyFileScanKey(1400);
		
		for(int i=0;i<partionNum;i++){
			System.out.println("partionNum th:"+i);
			Pair pair = new Pair();
			int beginLine = i*averagePartLine;
			int endLine = beginLine+averagePartLine;
			pair.setBeginTotalLine(beginLine);
			pair.setEndTotalLine(endLine);
			pair.setLineSize(averagePartLine);
			System.out.println("pair beginTotalLine:"+beginLine);
			
			int keyFileScan = getMyFileScanKey(beginLine); 
			
			FileScan fsFileScan = beginLineFileScanMap.get(keyFileScan);
			
			pair.setBeginIndex(fsFileScan.getTotalIndex());
			
			//FileScan fsFileScan1 = beginLineFileScanMap.get(endLine);
			//pair.setEndIndex(fsFileScan1.getTotalIndex());
			
			pairList.add(pair);
		}
		
		
		for(int i=0;i<partionNum-1;i++){
			Pair pair = pairList.get(i);
			Pair nextPair = pairList.get(i+1);
			pair.setEndIndex(nextPair.getBeginIndex()-1);
		}
		
		Pair lastPair=pairList.get(partionNum-1);
		lastPair.setEndIndex(size-1);
		
		int nump = pairList.size();
		fileItemList.clear();
		for(int i=0;i<nump;i++){
			String name=preName+"-"+i;
			FilePart fileItem = new FilePart();
			fileItem.setName(name);
			fileItem.setPreName(preName);
			fileItem.setSuffixName(suffixName);
			fileItem.setBeginLineNumber(beginLineNumber);
			
			toDirs = FileConcatFrame.targetDir+"\\"+preName;
			fileItem.setToDirs(toDirs);
			
			Pair pair=pairList.get(i); 
			fileItem.setPair(pair);
			
			
			
			System.out.println("pair begin:"+pair.getBeginIndex()+" pair end:"+pair.getEndIndex());
			
			fileItem.setSrcFiles(fileList.subList(pair.getBeginIndex(), pair.getEndIndex()));
			fileItem.setIndex(i);
			fileItemList.add(fileItem);
		}
	}
	
	
	private int getMyFileScanKey(int randomline){
		
		Set<Integer> gridKeySet = beginLineFileScanMap.keySet();
		Map<Integer,Integer> subListMap = new HashMap<Integer, Integer>();  
		int lastKey=0;
		for(Integer key:gridKeySet){
			System.out.println("key:"+key);
			int sub = key-randomline;
			if(sub>=0) {
				return lastKey;
				//break;
			}
			lastKey = key;
		}
		
		return 0;
		
		
	}
	
	
	public void computerFileItems(){
		//file list fill
		processDir(new File(srcDir));
		int size = fileList.size();
		
		List<Pair> pairs = TestSplit.Split(size, getPartionNum());
		int nump = pairs.size();
		for(int i=0;i<nump;i++){
			String name=preName+"-"+i;
			FilePart fileItem = new FilePart();
			fileItem.setName(name);
			fileItem.setPreName(preName);
			fileItem.setSuffixName(getSuffixName());
			
			String toDirs = FileConcatFrame.targetDir+"\\"+preName;
			fileItem.setToDirs(toDirs);
			
			Pair pair=pairs.get(i); 
			fileItem.setPair(pair);
			fileItem.setSrcFiles(fileList.subList(pair.getBeginIndex(), pair.getEndIndex()));
			fileItem.setIndex(i);
			fileItemList.add(fileItem);
		}
	}
	
	
	
	/***
	 * 这个方法是最关键的
	 * @param dirFile
	 */
	public void processDir(File dirFile){
		try{
			File[] files= dirFile.listFiles();
			int len = files.length;
			for (int i = 0; i < len; i++) {
				File f = files[i];
				if(f.isDirectory()){
					//如果这个文件夹属于NoSrcDirList里面则不处理
					if(getFileDirLevel()==0){
						if(!noSrcDirList.contains(f.getAbsolutePath())) {
							processDir(f);
						}
					}else if(getFileDirLevel()==1){
						//跳出来
						continue;
					}
				}else{
					//这个地方应该有过滤机制，什么文件
					//System.out.println(f.getAbsolutePath());
					String fname = f.getName();
					
					//System.out.println("文件名:"+fname);
					
					String type = StringTest.getType(fname);
					//System.out.println("文件类型:"+type);
					
					if(filterTypeSet.contains(type)){
						//System.out.println("被选中:"+f.getAbsolutePath());
						fileList.add(f);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
    public String readFileByLines(String fileName) {
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
	
	
	public void appendMethodA(String fileName, String content) {
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
	
	
	private void clearFile(File f){
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


	public List<File> getFileList() {
		return fileList;
	}


	public void setFileList(List<File> fileList) {
		this.fileList = fileList;
	}


	public String getSrcDir() {
		return srcDir;
	}


	public void setSrcDir(String srcDir) {
		this.srcDir = srcDir;
	}

	public List<String> getNoSrcDirList() {
		return noSrcDirList;
	}

	public void setNoSrcDirList(List<String> noSrcDirList) {
		this.noSrcDirList = noSrcDirList;
	}

	public List<FilePart> getFileItemList() {
		return fileItemList;
	}


	public void setFileItemList(List<FilePart> fileItemList) {
		this.fileItemList = fileItemList;
	}


	


	public int getPartionNum() {
		return partionNum;
	}


	public void setPartionNum(int partionNum) {
		this.partionNum = partionNum;
	}


	public String getPreName() {
		return preName;
	}


	public void setPreName(String preName) {
		this.preName = preName;
	}


	public Set<String> getFilterTypeSet() {
		return filterTypeSet;
	}


	public void setFilterTypeSet(Set<String> filterTypeSet) {
		this.filterTypeSet = filterTypeSet;
	}


	public int getTotalLines() {
		return totalLines;
	}


	public void setTotalLines(int totalLines) {
		this.totalLines = totalLines;
	}


	public int getTotalFileNum() {
		return totalFileNum;
	}


	public void setTotalFileNum(int totalFileNum) {
		this.totalFileNum = totalFileNum;
	}


	public String getToDirs() {
		return toDirs;
	}


	public void setToDirs(String toDirs) {
		this.toDirs = toDirs;
	}


	public String getSuffixName() {
		return suffixName;
	}


	public void setSuffixName(String suffixName) {
		this.suffixName = suffixName;
	}


	public int getFileDirLevel() {
		return fileDirLevel;
	}


	public void setFileDirLevel(int fileDirLevel) {
		this.fileDirLevel = fileDirLevel;
	}


	public List<String> getSrcDirList() {
		return srcDirList;
	}


	public void setSrcDirList(List<String> srcDirList) {
		this.srcDirList = srcDirList;
	}

	public int getBeginLineNumber() {
		return beginLineNumber;
	}

	public void setBeginLineNumber(int beginLineNumber) {
		this.beginLineNumber = beginLineNumber;
	}
}
