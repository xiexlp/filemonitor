/***
 * 
 * 文件分割集中打印器
 * 
 * 
 */

package js.com.file.concat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JToolBar;



public class FileConcatFrame extends JFrame implements ActionListener,DocumentListener{

	private JPanel contentPane;
	
	private JToolBar toolBar;
	private JButton clearButton;
	
	private JLabel infoLabel;
	private JLabel filePathLabel;
	private JLabel fileNameLabel;
	
	private JLabel fileSuffexNameLabel;
	private JLabel linesEachPageLabel;
	
	private JLabel fileOptionLabel;
	
	private JTextField linesEachPageTextField;
	
	
	
	
	private JTextField filePathTextField;
	
	
	private JTextField fileNameTypeTextField;
	private JPanel fileNameTypeJPanel;
	private JCheckBox fileNameTypeCheckBox;
	
	private JLabel fileNumLabel;
	private JTextField fileNumTextField;
	private JComboBox fileNumComboBox;
	
	
	private JLabel fileOutputTypeLabel;
	private JComboBox fileOutputTypeComboxBox;
	
	private JComboBox fileDirLevelComboBox;
 	
	private JScrollPane textAreaScrollPane;
	private JTextArea infoTextArea;
	
	
	private JButton selectPathButton;
	
	
	private JButton totalFileNumButton;
	
	private JButton printByAverageLineButton;
	private JButton printStatByAverageLineButton;
	
	private JButton printFileButton;
	private JButton printStatButton;
	private JButton showInfoButton;
	
	private JPanel buttonsPanel;
	
	//GridBagLayout rootLayout;
	BorderLayout rootLayout;
	JPanel container;
		
	private JFileChooser fc;
	
	//需要打印的文件后缀
	private String[] typeChoiceArray = {"java","bat","xml","h","cpp","c","hpp","sh","txt","php","html","go"}; 
	
	private List<JCheckBox> typeChoiceCheckboxList=new ArrayList<JCheckBox>();
	
	
	double linesEachPage=0;
	
	//生成的目标文件存放目录
	public static String targetDir="target";
	
	
	Set<String> filterTypeSet;
	String srcPath;
	String name;
	int particionNums;
	
	public static int normalPages=450;
	
	DirFileConcat dirFileConcat;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileConcatFrame frame = new FileConcatFrame();
					
					Dimension dimension = new Dimension(1000, 600);
					Point point = LocationUtils.getLocationPointTopAjust(dimension, 100);
					frame.setSize(dimension);
					frame.setLocation(point);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FileConcatFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		init();
		addComponent();
		setlayout();
		addListener();
	}
	
	private void init(){
		//contentPane = new JPanel();
		//GridBagLayout gridBagLayout = new GridBagLayout();
		//contentPane.setLayout(gridBagLayout);
		
		container = new JPanel();
		
		toolBar = new JToolBar();
		clearButton = new JButton("清除");
		
		infoLabel = new JLabel("组合信息");
		filePathLabel=new JLabel("文件路径");
		fileNameLabel=new JLabel("生成文件名前缀");
		
		fileSuffexNameLabel=new JLabel("文件名后缀过滤选择");
		
		
		filePathTextField=new JTextField();
		fileNameTypeTextField=new JTextField();
		fileNameTypeJPanel = new JPanel();
		
		//fileNameTypeCheckBox = new JCheckBox();
		
		for(String type:typeChoiceArray){
			JCheckBox checkBox = new JCheckBox(type);
			if(type.equalsIgnoreCase("java")){
				checkBox.setSelected(true);
			}
			typeChoiceCheckboxList.add(checkBox);
		}
		
		
		fileNumLabel=new JLabel("平均分配文件个数");
		fileNumTextField= new JTextField("3");
		Vector<Integer> vector= new Vector<Integer>();
		vector.add(1);vector.add(2);vector.add(3);
		fileNumComboBox = new JComboBox(vector);
		fileNumComboBox.setEditable(true);
		fileNumComboBox.setSelectedIndex(2);
		
		fileOutputTypeLabel=new JLabel("输出文件类型");
		Vector<String> vectorFileType= new Vector();
		vectorFileType.add("");vectorFileType.add(".txt");vectorFileType.add(".doc");vectorFileType.add(".docx");
		fileOutputTypeComboxBox= new JComboBox(vectorFileType);
		fileOutputTypeComboxBox.setEditable(true);
		fileOutputTypeComboxBox.setSelectedIndex(2);
		
		
		fileOptionLabel = new JLabel("文件夹深度级别");
		Vector<String> vectorFileDirLevel= new Vector();
		vectorFileDirLevel.add("0");vectorFileDirLevel.add("1");
		fileDirLevelComboBox= new JComboBox(vectorFileDirLevel);
		fileDirLevelComboBox.setEditable(true);
		fileDirLevelComboBox.setSelectedIndex(0);
		
		
		
		infoTextArea = new JTextArea();
		textAreaScrollPane = new JScrollPane(infoTextArea);
		
		totalFileNumButton = new JButton("计算总的文件数目");
		showInfoButton = new JButton("查看信息");
		printStatButton = new JButton("打印信息");
		printFileButton = new JButton("汇总到文件");
		
		printByAverageLineButton = new JButton("按行数分割(目前正使用的)");
		printStatByAverageLineButton = new JButton("打印行数平均信息");
		
		buttonsPanel = new JPanel();
		
		selectPathButton = new JButton("选择");
		
		fc= new JFileChooser(new File("."));
		
		
		
		linesEachPageLabel = new JLabel("每页行数");
		linesEachPageTextField = new JTextField();
		linesEachPageTextField.setText("40.8");
		linesEachPageTextField.setEditable(false);
		
		
	}
	
	
	private void addComponent(){
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(toolBar,BorderLayout.NORTH);
		getContentPane().add(container,BorderLayout.CENTER);
		
		toolBar.add(clearButton);
		
		//container.add(toolBar);
		
		container.add(infoLabel);
		container.add(fileNameLabel);
		container.add(filePathLabel);
		container.add(filePathTextField);
		
		container.add(fileNameTypeTextField);
		
		container.add(fileSuffexNameLabel);
		for(JCheckBox checkBox:typeChoiceCheckboxList){
			
			fileNameTypeJPanel.add(checkBox);
		}
		container.add(fileNameTypeJPanel);
		
		container.add(fileNumLabel);
		//container.add(fileNumTextField);
		container.add(fileNumComboBox);
		
		container.add(fileOutputTypeLabel);
		container.add(fileOutputTypeComboxBox);
		
		container.add(fileOptionLabel);
		container.add(fileDirLevelComboBox);
		
		//container.add(infoTextArea);
		container.add(textAreaScrollPane);
		
		buttonsPanel.add(totalFileNumButton);
		buttonsPanel.add(printStatByAverageLineButton);
		buttonsPanel.add(printByAverageLineButton);
		buttonsPanel.add(showInfoButton);
		buttonsPanel.add(printStatButton);
		buttonsPanel.add(printFileButton);
		
		container.add(buttonsPanel);
		
		container.add(selectPathButton);
		
		container.add(linesEachPageLabel);
		container.add(linesEachPageTextField);
	}
	
	
	private void setlayout(){
		
		
		//rootLayout = new GridBagLayout();
		
		//rootLayout = new BorderLayout();
		
		//setLayout(rootLayout);
		
		//rootLayout.setConstraints(container, G.n().aGridXY(0, 0).afill(G.BOTH).awx(1).awy(1));
		
		
		GridBagLayout containerLayout=new GridBagLayout();
		container.setLayout(containerLayout);
		containerLayout.setConstraints(infoLabel,G.nG().aGridXY(0, 0).anchor(G.WEST));
		
		containerLayout.setConstraints(filePathLabel,G.nG().aGridXY(0, 1));
		containerLayout.setConstraints(filePathTextField,G.nG().aGridXY(1, 1).weightX(1).agw(2).afill(G.BOTH).anchor(G.WEST));
		containerLayout.setConstraints(selectPathButton,G.nG().aGridXY(3, 1).anchor(G.EAST).afill(G.HORIZONTAL).weightX(0));
		
		containerLayout.setConstraints(fileNameLabel,G.nG().aGridXY(0, 2));
		containerLayout.setConstraints(fileNameTypeTextField,G.nG().aGridXY(1, 2).agw(3).afill(G.HORIZONTAL).anchor(G.WEST));
		
		
		containerLayout.setConstraints(fileSuffexNameLabel,G.nG().aGridXY(0, 3));
		containerLayout.setConstraints(fileNameTypeJPanel,G.nG().aGridXY(1, 3).agw(3).afill(G.HORIZONTAL).anchor(G.WEST));
		
		
		containerLayout.setConstraints(fileNumLabel,G.nG().aGridXY(0, 4));
		//containerLayout.setConstraints(fileNumTextField,G.nG().aGridXY(1, 3).agw(2).afill(G.HORIZONTAL).anchor(G.WEST));
		
		containerLayout.setConstraints(fileNumComboBox,G.nG().aGridXY(1, 4).weightX(1).agw(1).afill(G.HORIZONTAL).anchor(G.WEST));
		
		
		containerLayout.setConstraints(fileOutputTypeLabel, G.nG().aGridXY(2, 4));
		containerLayout.setConstraints(fileOutputTypeComboxBox, G.nG().weightX(0).aGridXY(3, 4).afill(G.HORIZONTAL).anchor(G.WEST));
		
		//container
		containerLayout.setConstraints(fileOptionLabel, G.nG().aGridXY(0, 5));
		containerLayout.setConstraints(fileDirLevelComboBox, G.nG().weightX(0).aGridXY(1, 5).afill(G.HORIZONTAL).anchor(G.WEST));
		
		containerLayout.setConstraints(buttonsPanel,G.nG().aGridXY(0, 6).afill(G.HORIZONTAL).agw(4));
		
		
		containerLayout.setConstraints(linesEachPageLabel,G.nG().aGridXY(0, 7));
		containerLayout.setConstraints(linesEachPageTextField,G.nG().aGridXY(1, 7).agw(3).afill(G.HORIZONTAL).anchor(G.WEST));
		
		
		containerLayout.setConstraints(textAreaScrollPane,G.nG().aGridXY(0, 8).agw(4).afill(G.BOTH).awx(1).awy(1));
		//ontainerLayout.setConstraints(button,G.nG().aGridXY(0, 4));
	}
	
	private void addListener(){
		
		clearButton.addActionListener(this);
		
		totalFileNumButton.addActionListener(this);
		printFileButton.addActionListener(this);
		printStatButton.addActionListener(this);
		showInfoButton.addActionListener(this);
		printByAverageLineButton.addActionListener(this);
		printStatByAverageLineButton.addActionListener(this);
		
		selectPathButton.addActionListener(this);
	
		Document document = filePathTextField.getDocument();  
        document.addDocumentListener(this);  
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		
		getUserInput();
		
		if(o==showInfoButton){	
			if(checkInput()){
				performFileConcat(0);
			}
		}else if(o==printStatButton){
			if(checkInput()){
				performFileConcat(1);
			}
			
		}else if(o==printFileButton){
			if(checkInput()){
				performFileConcat(2);
			}
		}
		else if(o==selectPathButton) {
			openFile();
		}else if(o==clearButton) {
			//openFile();
			clearTextArea();
		}
		else if(o==totalFileNumButton){
			if(checkInput()){
				computerTotalFileNum();
			}
		}else if (o==printStatByAverageLineButton) {
			if(checkInput()){
				performByAverageFileLineConcat(1);
				//computerTotalFileNum();1
			}
			
			//使用最多的是这个
		}else if (o==printByAverageLineButton) {
			if(checkInput()){
				performByAverageFileLineConcat(2);
				//computerTotalFileNum();
			}
		}
	}
	
	private void clearTextArea(){
		infoTextArea.setText("");
	}
	
	private void computerTotalFileNum(){
		DirFileConcat dirFileConcat = new DirFileConcat();
		dirFileConcat.setFilterTypeSet(filterTypeSet);
		dirFileConcat.setPartionNum(particionNums);
		dirFileConcat.setPreName(name);
		dirFileConcat.setSrcDir(srcPath);
		
		String suffixName = (String)fileOutputTypeComboxBox.getSelectedItem();
		
		dirFileConcat.setSuffixName(suffixName);
		
		dirFileConcat.computerTotalFiles();
		
		String msg ="total files :"+ dirFileConcat.getFileList().size();
		appendTextAreaContent(msg);
	}
	
	
	private void getUserInput() {
		srcPath = filePathTextField.getText();
		name = fileNameTypeTextField.getText();
		filterTypeSet = new HashSet<String>();
		
		for(JCheckBox checkBox:typeChoiceCheckboxList){
			if(checkBox.isSelected()){
				String type = checkBox.getText();
				System.out.println(type);
				filterTypeSet.add(type);
			}			
		}
		
		System.out.println("filetypeset:"+filterTypeSet);
		String num = fileNumTextField.getText();
		
		System.out.println("partionNumSSSS:"+fileNumComboBox.getSelectedItem());
		particionNums = (Integer)fileNumComboBox.getSelectedItem();
		
		System.out.println("src path:"+srcPath);
		System.out.println("partition nums:"+particionNums);
		System.out.println("name prefix:"+name);
		
	}
	
	
	
	private boolean checkInput(){
		boolean result=true;
		String msg="";
		if(srcPath==null||srcPath.equalsIgnoreCase("")){
			msg="文件夹不能为空";
			System.out.println(msg);
			appendTextAreaContent(msg);
			result = false;
			
		}
		
		if(name==null||name.equalsIgnoreCase("")){
			msg="文件名不能为空";
			System.out.println(msg);
			appendTextAreaContent(msg);
			result = false;
		}
		return result;	
	}
	
	
	private void appendTextAreaContent(String msg){
		infoTextArea.append(msg);
		infoTextArea.append("\n");
	}
	
	
	private void performFileConcat(int buttonType){
		System.out.println("button,begin");
		
		String msg = "";
		
		
		
//		String filetypes= fileNameTypeTextField.getText();
//		String[] types = filetypes.split("\\,");
//		
//		Set<String> filterTypeSet = new HashSet<String>();
//		
//		int len = types.length;
//		for(String type:types){
//			filterTypeSet.add(type);
//		}
		
		
//		
		
		//String partionNum=fileNumComboBox.getSelectedItem();
		
		
		String suffixName=(String)fileOutputTypeComboxBox.getSelectedItem();
		
		System.out.println("youslect suffixName:"+suffixName);
		DirFileConcat dirFileConcat = new DirFileConcat();
		
		dirFileConcat.setFilterTypeSet(filterTypeSet);
		dirFileConcat.setPartionNum(particionNums);
		dirFileConcat.setPreName(name);
		dirFileConcat.setSrcDir(srcPath);
		dirFileConcat.setSuffixName(suffixName);
		
		dirFileConcat.computerFileItems();
		List<FilePart> fileItemList = dirFileConcat.getFileItemList();
		
		
		for(FilePart fileItem:fileItemList){
			//System.out.println("文件名:"+fileItem.getName());
			//System.out.println("begin:"+fileItem.getPair().beginIndex);
			//System.out.println("begin:"+fileItem.getPair().endIndex);
			//先不处理
			//fileItem.process();
			//查看信息
			System.out.println("------------------------------");
			//fileItem.showInfo();
		
			//showinfo
			if(buttonType==0){
			
				Thread t = new Thread(new FilePartShowRunnable(fileItem));
				t.start();
				try {
					t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//printStat
			}else if(buttonType==1){
				
				Thread t = new Thread(new FilePartPrintInfoRunnable(fileItem));
				t.start();
				try {
					t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//concat	
			}else if(buttonType==2){
				Thread t = new Thread(new FileConcatRunnable(fileItem));
				t.start();
				try {
					t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		//相当于等三个现场曾都执行完之后才执行下面
		appendTextAreaContent(msg);
		
		appendTextAreaContent("------------------------");
		
		StringBuffer sb = new StringBuffer();
		
		
		linesEachPage = Double.parseDouble(linesEachPageTextField.getText().trim());
		int pages = 0;
		
		
		sb.append("文件名:"+"\t\t");
		sb.append("总的文件数目:"+"\t\t");
		sb.append("总的行数"+"\t\t");
		sb.append("页面数目"+"\t\t");
		
		sb.append("\n");
		
		//last stastic info
		for(FilePart fileItem:fileItemList){
			sb.append(fileItem.getName()+"\t\t");
			sb.append(fileItem.getTotalFiles()+"\t\t");
			sb.append(fileItem.getTotalLine()+"\t\t");
			
			double pagesDouble = fileItem.getTotalLine()/linesEachPage;
			pages = (int)Math.ceil(pagesDouble);
			
			sb.append(pages+"\t\t");
			
			sb.append("\n");
		}
		appendTextAreaContent(sb.toString());
		//System.out.println("任务执行完毕");
	}
	
	
	private void performByAverageFileLineConcat(int buttonType){
		System.out.println("button,begin");
		
		String msg = "";
		
		
		
//		String filetypes= fileNameTypeTextField.getText();
//		String[] types = filetypes.split("\\,");
//		
//		Set<String> filterTypeSet = new HashSet<String>();
//		
//		int len = types.length;
//		for(String type:types){
//			filterTypeSet.add(type);
//		}
		
		
//		
		
		//String partionNum=fileNumComboBox.getSelectedItem();
		
		String suffixName = (String)fileOutputTypeComboxBox.getSelectedItem();
		
		String fileDirLevelString = (String)fileDirLevelComboBox.getSelectedItem();
		int fileDirLevel = Integer.parseInt(fileDirLevelString);
		
		System.out.println("youselectsuffixName:"+suffixName);
		System.out.println("select dir level:"+fileDirLevel);
		
		dirFileConcat = new DirFileConcat();
		
		dirFileConcat.setFilterTypeSet(filterTypeSet);
		dirFileConcat.setPartionNum(particionNums);
		dirFileConcat.setPreName(name);
		dirFileConcat.setSrcDir(srcPath);
		dirFileConcat.setSuffixName(suffixName);
		dirFileConcat.setFileDirLevel(fileDirLevel);
		
		//dirFileConcat.computerFileItems();
		dirFileConcat.computerFileScanLineIndex();
		List<FilePart> fileItemList = dirFileConcat.getFileItemList();
		
		
		for(FilePart fileItem:fileItemList){
			//System.out.println("文件名:"+fileItem.getName());
			//System.out.println("begin:"+fileItem.getPair().beginIndex);
			//System.out.println("begin:"+fileItem.getPair().endIndex);
			//先不处理
			//fileItem.process();
			//查看信息
			System.out.println("------------------------------");
			//fileItem.showInfo();
		
			//showinfo
			if(buttonType==0){
			
				Thread t = new Thread(new FilePartShowRunnable(fileItem));
				t.start();
				try {
					t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//printStat
			}else if(buttonType==1){
				
				Thread t = new Thread(new FilePartPrintInfoRunnable(fileItem));
				t.start();
				try {
					t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//concat	
			}else if(buttonType==2){
				Thread t = new Thread(new FileConcatRunnable(fileItem));
				t.start();
				try {
					t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		//相当于等三个现场曾都执行完之后才执行下面
		appendTextAreaContent(msg);
		
		showPrintResultInfo();
		
		//System.out.println("任务执行完毕");
	}
	
	
	private void showPrintResultInfo(){
		
		List<FilePart> fileItemList = dirFileConcat.getFileItemList();
		
		appendTextAreaContent("------------------------");
		
		StringBuffer sb = new StringBuffer();
		
		
		linesEachPage = Double.parseDouble(linesEachPageTextField.getText().trim());
		int pages = 0;
		
		//打印统计信息
		sb.append("文件名:"+"\t\t");
		sb.append("总的文件数目:"+"\t\t");
		sb.append("总的行数"+"\t\t");
		sb.append("页面数目"+"\t\t");
		sb.append("结论"+"\t\t");
		
		sb.append("\n");
		
		//last stastic info
		for(FilePart fileItem:fileItemList){
			sb.append(fileItem.getName()+"\t\t");
			sb.append(fileItem.getTotalFiles()+"\t\t");
			sb.append(fileItem.getTotalLine()+"\t\t");
			
			double pagesDouble = fileItem.getTotalLine()/linesEachPage;
			pages = (int)Math.ceil(pagesDouble);
			
			sb.append(pages+"\t\t");
			
			if(pages>=this.normalPages){
				sb.append("页数太多，请调高分割比例"+"\t\t");
			}else{
				sb.append("页数在正常范围之类，请放心打印"+"\t\t");
			}
			
			
			sb.append("\n");
		}
		
		
		String toDirs = dirFileConcat.getToDirs();
		File toFileDir = new File(toDirs);
		sb.append("生成目标文件所在的目录:"+toFileDir.getAbsolutePath()+"\n");
		
		appendTextAreaContent(sb.toString());
	}
	
	
	private void openFile() // 打开文件
	{
		// 设置打开文件对话框的标题
		fc.setDialogTitle("Open File");
		int flag=-1;
		File f=new File(".");
		
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		// 这里显示打开文件的对话框
		try {
			fc.changeToParentDirectory();
			//fc.setCurrentDirectory(new File("."));
			flag = fc.showOpenDialog(null);
		} catch (HeadlessException head) {

			System.out.println("Open File Dialog ERROR!");
		}

		// 如果按下确定按钮，则获得该文件。
		if (flag == JFileChooser.APPROVE_OPTION) {
			// 获得该文件
			f = fc.getSelectedFile();
			System.out.println("open file----" + f.getName());
			filePathTextField.setText(f.getAbsolutePath());
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		System.out.println("change...");
		Document doc = e.getDocument();  
	    String s="";
		try {
			s = doc.getText(0, doc.getLength());
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		appendTextAreaContent(s);
		//String content = e.getDocument()
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
		System.out.println("insert...");
		Document doc = e.getDocument();  
	    String s="";
	    String lastDirName="";
		try {
			s = doc.getText(0, doc.getLength());
			lastDirName = FileUtil.getLastDirName(s);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		fileNameTypeTextField.setText(lastDirName);
		
		appendTextAreaContent(s);
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		System.out.println("remove...");
		Document doc = e.getDocument();  
	    String s="";
	    String lastDirName="";
		try {
			s = doc.getText(0, doc.getLength());
			lastDirName = FileUtil.getLastDirName(s);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		fileNameTypeTextField.setText(lastDirName);
		
		appendTextAreaContent(s);
		
	} 

}
