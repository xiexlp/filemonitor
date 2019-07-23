package js.com.file.concat;

public class FilePartShowRunnable implements Runnable{
	
	
	private FilePart fileItem;
	
	public FilePartShowRunnable(FilePart fileItem){
		this.fileItem = fileItem;
	}
	
	public void run(){
		fileItem.showInfo();
	}
	

}
