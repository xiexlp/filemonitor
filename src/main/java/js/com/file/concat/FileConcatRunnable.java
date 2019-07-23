package js.com.file.concat;

public class FileConcatRunnable implements Runnable{
	
	private FilePart fileItem;
	
	public FileConcatRunnable(FilePart fileItem){
		this.fileItem = fileItem;
	}
	
	public void run(){
		fileItem.process();
	}

}
