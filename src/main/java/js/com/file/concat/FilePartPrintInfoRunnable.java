package js.com.file.concat;

public class FilePartPrintInfoRunnable implements Runnable{
	
	private FilePart fileItem;
	
	public FilePartPrintInfoRunnable(FilePart fileItem){
		this.fileItem = fileItem;
	}
	
	public void run(){
		fileItem.printInfoStat();
	}
	

}
