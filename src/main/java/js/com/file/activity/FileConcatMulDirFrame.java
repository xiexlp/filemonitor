package js.com.file.activity;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import js.com.file.concat.*;
import js.com.file.concat.transform.ToPDFUtils;
import js.com.file.utils.ClipboardUtil;

/***
 * 
 * 文件分割集中打印器
 * 
 * 
 */


import js.com.file.utils.FileJsonKit;
import js.com.file.utils.StringUtil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/***
 * 使用的主类
 */
public class FileConcatMulDirFrame extends JFrame implements ActionListener,DocumentListener{



	private JPanel contentPane;
	
	private JToolBar toolBar;
	private JButton clearButton;
	private JButton saveButton;
	private JButton viewHistoryButton;
	
	private JLabel infoLabel;
	private JLabel defaultPathLabel;
	private JTextField defaultPathTextField;
	private JButton addDefaultPathToListButton;
	private JLabel filePathLabel;
	private JLabel fileNameLabel;
	
	private JLabel fileSuffexNameLabel;
	private JLabel linesEachPageLabel;
	
	private JLabel fileOptionLabel;

	private JLabel beginLineNumberLabel;
	private JTextField beginLineNumberTextField;
	
	private JTextField linesEachPageTextField;

	private JTextField filePathTextField;
	
	//转变为一个list,这样可以多文件夹放到同一个打印;
	private JList filePathJList;
	private JLabel noFilePathLabel;
	private JList noFilePathJList;

	List<String> selectFilePathList;
	JPopupMenu jListPopMenu;
	JPopupMenu nojListPopMenu;

	JMenuItem deletePathMenuItem ;
	JMenuItem addPathMenuItem ;
	JMenuItem copyPathMenuItem ;
	JMenuItem reverseSelectMenuItem ;
	JMenuItem setFileNameTextFieldMenuItem ;

	JMenuItem deletePathMenuItemNo ;
	JMenuItem addPathMenuItemNo ;
	JMenuItem copyPathMenuItemNo ;
	JMenuItem reverseSelectMenuItemNo ;
	JMenuItem setFileNameTextFieldMenuItemNo ;

	private Vector<String> selectDirsVector=new Vector<String>();
	private DefaultListModel selectDirModel = new DefaultListModel();

	private DefaultListModel noSelectDirModel = new DefaultListModel();
	private Vector<String> noSelectDirsVector=new Vector<String>();

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
	private JTextPane textPane;
	
	private JButton selectPathButton;
	private JButton selectNoPathButton;
	
	private JButton totalFileNumButton;
	
	private JButton printByAverageLineButton;
	private JButton printStatByAverageLineButton;
	private JButton fileToPdfButton;
	
	private JButton printFileButton;
	private JButton printStatButton;
	private JButton showInfoButton;
	
	private JPanel buttonsPanel;
	
	//GridBagLayout rootLayout;
	BorderLayout rootLayout;
	JPanel container;
		
	private JFileChooser fc;
	
	//需要打印的文件后缀
	private String[] typeChoiceArray = {"java","bat","xml","h","cpp","c","hpp","sh","txt","php","html","go","json","md","g4","sol","js","css","vue","rb"};
	
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
	List<File> targetFiles;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileConcatMulDirFrame frame = new FileConcatMulDirFrame();
					
					Dimension dimension = new Dimension(1400, 600);
					Point point = LocationUtils.getLocationPointTopAjust(dimension, 50);
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
	public FileConcatMulDirFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		add1Init();
		add2Component();
		add3Layout();
		add4Listener();
	}
	
	private void add1Init(){
		//contentPane = new JPanel();
		//GridBagLayout gridBagLayout = new GridBagLayout();
		//contentPane.setLayout(gridBagLayout);
		
		container = new JPanel();
		
		toolBar = new JToolBar();
		clearButton = new JButton("清除");
		saveButton = new JButton("保存项目内容");
		viewHistoryButton = new JButton("查看历史内容");

		defaultPathLabel = new JLabel("默认文件路径");
		defaultPathTextField =new JTextField();
		defaultPathTextField.setText("F:\\git\\java\\mycat\\Mycat-Server");
		addDefaultPathToListButton = new JButton("添加");
		infoLabel = new JLabel("组合信息");
		filePathLabel=new JLabel("文件路径");
		fileNameLabel=new JLabel("生成文件名前缀");
		
		fileSuffexNameLabel=new JLabel("文件名后缀过滤选择");
		
		filePathTextField=new JTextField();

		filePathJList = new JList();
		Border greenLineBorder = BorderFactory.createLineBorder(Color.green);
		filePathJList.setBorder(greenLineBorder);

		filePathJList.setModel(selectDirModel);
		noFilePathJList = new JList();
		noFilePathJList.setModel(noSelectDirModel);
		Border llineBorder = BorderFactory.createLineBorder(Color.red);
		noFilePathJList.setBorder(llineBorder);

		noFilePathLabel = new JLabel("排除的文件夹");

		//允许选择多个项目
		filePathJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		noFilePathJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		jListPopMenu = new JPopupMenu();
		deletePathMenuItem = new JMenuItem("删除");
		addPathMenuItem = new JMenuItem("增加");
		reverseSelectMenuItem = new JMenuItem("反选");
		copyPathMenuItem = new JMenuItem("复制当前选择");
		setFileNameTextFieldMenuItem = new JMenuItem("生成文件名");
		jListPopMenu.add(addPathMenuItem);
		jListPopMenu.add(deletePathMenuItem);
		jListPopMenu.add(reverseSelectMenuItem);
		jListPopMenu.add(copyPathMenuItem);
		jListPopMenu.add(setFileNameTextFieldMenuItem);

		deletePathMenuItem.addActionListener(jlistMenuListener);
		addPathMenuItem.addActionListener(jlistMenuListener);
		reverseSelectMenuItem.addActionListener(jlistMenuListener);
		copyPathMenuItem.addActionListener(jlistMenuListener);
		setFileNameTextFieldMenuItem.addActionListener(jlistMenuListener);

		nojListPopMenu = new JPopupMenu();
		deletePathMenuItemNo = new JMenuItem("删除");
		addPathMenuItemNo = new JMenuItem("增加");
		reverseSelectMenuItemNo = new JMenuItem("反选");
		copyPathMenuItemNo = new JMenuItem("复制当前选择");
		setFileNameTextFieldMenuItemNo = new JMenuItem("生成文件名");
		nojListPopMenu.add(addPathMenuItemNo);
		nojListPopMenu.add(deletePathMenuItemNo);
		nojListPopMenu.add(reverseSelectMenuItemNo);
		nojListPopMenu.add(copyPathMenuItemNo);
		nojListPopMenu.add(setFileNameTextFieldMenuItemNo);

		deletePathMenuItemNo.addActionListener(jlistNoDirMenuListener);
		addPathMenuItemNo.addActionListener(jlistNoDirMenuListener);
		reverseSelectMenuItemNo.addActionListener(jlistNoDirMenuListener);
		copyPathMenuItemNo.addActionListener(jlistNoDirMenuListener);
		setFileNameTextFieldMenuItemNo.addActionListener(jlistNoDirMenuListener);

		filePathJList.add(jListPopMenu);
		filePathJList.addMouseListener(new myJListListener());
		noFilePathJList.add(nojListPopMenu);
		noFilePathJList.addMouseListener(new noDirJListListener());


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

		beginLineNumberLabel = new JLabel("开始行数");
		beginLineNumberTextField = new JTextField();
		beginLineNumberTextField.setText("0");

		infoTextArea = new JTextArea();
		textPane = new JTextPane();
		textAreaScrollPane = new JScrollPane(textPane);


		buttonsPanel = new JPanel();
		totalFileNumButton = new JButton("计算总的文件数目");
		totalFileNumButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		//buttonsPanel.add(totalFileNumButton);
		showInfoButton = new JButton("查看信息");
		showInfoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("i am clicked:"+showInfoButton.getText());
				if(checkInput()){
					performFileConcat(0);
				}
			}
		});
		//buttonsPanel.add(showInfoButton);
		printStatButton = new JButton("打印信息");
		printStatButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("i am clicked:"+printStatButton.getText());
				if(checkInput()){
					performFileConcat(1);
				}
			}
		});
		//buttonsPanel.add(printStatButton);
		printFileButton = new JButton("汇总到文件");
		printStatButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("i am clicked:"+printFileButton.getText());
				if(checkInput()){
					performFileConcat(2);
				}
			}
		});
		//buttonsPanel.add(printFileButton);
		
		printByAverageLineButton = new JButton("按行数分割(目前正使用的)");
		printStatByAverageLineButton = new JButton("打印行数平均信息");

		fileToPdfButton = new JButton("file转pdf");


		selectPathButton = new JButton("选择文件夹");
		selectNoPathButton = new JButton("选择文件夹");
		
		fc= new JFileChooser(new File("."));

		linesEachPageLabel = new JLabel("每页行数");
		linesEachPageTextField = new JTextField();
		linesEachPageTextField.setText("40.8");
		linesEachPageTextField.setEditable(false);
	}

	public class myJListListener extends MouseAdapter {
		//e.getButton() 返回值有 1，2，3。1代表鼠标左键，3代表鼠标右键
		//jList.getSelected() 返回的是选中的JList中的项数。
		//if语句的意思也就是，在JList 中点击了右键而且JList选中了某项，显示右键菜单
		//e.getX() , e.getY() 返回的是鼠标目前的位置！也就是在目前鼠标的位置上弹出右键
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == 3 && filePathJList.getSelectedIndex()>=0)
				jListPopMenu.show(filePathJList,e.getX(),e.getY());
		}
	}

	public class noDirJListListener extends MouseAdapter {
		//e.getButton() 返回值有 1，2，3。1代表鼠标左键，3代表鼠标右键
		//jList.getSelected() 返回的是选中的JList中的项数。
		//if语句的意思也就是，在JList 中点击了右键而且JList选中了某项，显示右键菜单
		//e.getX() , e.getY() 返回的是鼠标目前的位置！也就是在目前鼠标的位置上弹出右键
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == 3 && noFilePathJList.getSelectedIndex()>=0)
				nojListPopMenu.show(noFilePathJList,e.getX(),e.getY());
		}
	}

	ActionListener jlistMenuListener = e -> {
		String cmd = e.getActionCommand();
		Object source = e.getSource();
		int[] indices = filePathJList.getSelectedIndices();
		int len = indices.length;
		if(source==deletePathMenuItem){
			System.out.println("delete menu item");
			for(int i=0;i<len;i++){
				selectDirModel.remove(indices[i]);
			}
		}else if(source==addPathMenuItem){
			System.out.println("add menu item");
			openFile();
		}else if(source==reverseSelectMenuItem){
			System.out.println("reverse menu item");
		}else if(source==copyPathMenuItem){
			System.out.println("copy menu item");
			String selectPath = (String)selectDirModel.firstElement();
			ClipboardUtil.setToClipboardText(selectPath);;
		}else if(source==setFileNameTextFieldMenuItem){
			System.out.println("set name menu item");
			String selectPath = (String)selectDirModel.firstElement();
			String name = StringUtil.getNameFromPath(selectPath);
			fileNameTypeTextField.setText(name);
		}
		//ta.append("Click '" + cmd + "' menu item.\n");
		if (cmd.equals("Exit")) {
			System.exit(0);
		}
	};

	ActionListener jlistNoDirMenuListener = e -> {
		String cmd = e.getActionCommand();
		Object source = e.getSource();
		int[] indices = noFilePathJList.getSelectedIndices();
		int len = indices.length;
		if(source==deletePathMenuItemNo){
			System.out.println("delete menu item");
			for(int i=0;i<len;i++){
				noSelectDirModel.remove(indices[i]);
			}
		}else if(source==addPathMenuItemNo){
			System.out.println("add menu item");
			openFile();
		}else if(source==reverseSelectMenuItemNo){
			System.out.println("reverse menu item");
		}else if(source==copyPathMenuItemNo){
			System.out.println("copy menu item");
			String selectPath = (String)noSelectDirModel.firstElement();
			ClipboardUtil.setToClipboardText(selectPath);
		}else if(source==setFileNameTextFieldMenuItemNo){
			System.out.println("set name menu item");
			String selectPath = (String)noSelectDirModel.firstElement();
			String name = StringUtil.getNameFromPath(selectPath);
			//fileNameTypeTextField.setText(name);
		}
		//ta.append("Click '" + cmd + "' menu item.\n");
		if (cmd.equals("Exit")) {
			System.exit(0);
		}
	};



	private void add2Component(){
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(toolBar,BorderLayout.NORTH);
		getContentPane().add(container,BorderLayout.CENTER);
		
		toolBar.add(clearButton);
		toolBar.add(saveButton);
		toolBar.add(viewHistoryButton);
		
		//container.add(toolBar);


		container.add(defaultPathLabel);
		container.add(infoLabel);
		container.add(fileNameLabel);
		container.add(filePathLabel);
		
		//container.add(filePathTextField);
		container.add(filePathJList);

		container.add(noFilePathLabel);
		container.add(defaultPathTextField);
		//container.add(filePathTextField);
		container.add(noFilePathJList);
		
		container.add(fileNameTypeTextField);
		container.add(addDefaultPathToListButton);
		
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

		container.add(beginLineNumberLabel);
		container.add(beginLineNumberTextField);
		
		//container.add(infoTextArea);
		container.add(textAreaScrollPane);


		buttonsPanel.add(printStatByAverageLineButton);
		buttonsPanel.add(printByAverageLineButton);
		buttonsPanel.add(fileToPdfButton);


		container.add(buttonsPanel);
		
		container.add(selectPathButton);
		container.add(selectNoPathButton);
		
		container.add(linesEachPageLabel);
		container.add(linesEachPageTextField);
	}
	
	
	private void add3Layout(){
		//rootLayout = new GridBagLayout();
		//rootLayout = new BorderLayout();
		//setLayout(rootLayout);
		//rootLayout.setConstraints(container, G.n().aGridXY(0, 0).afill(G.BOTH).awx(1).awy(1));
		GridBagLayout containerLayout=new GridBagLayout();
		container.setLayout(containerLayout);
		containerLayout.setConstraints(infoLabel, G.nG().aGridXY(0, 0).anchor(G.WEST));
		int level=1;
		containerLayout.setConstraints(defaultPathLabel,G.nG().aGridXY(0, level));
		containerLayout.setConstraints(defaultPathTextField,G.nG().aGridXY(1, level).weightX(1).agw(2).afill(G.BOTH).anchor(G.WEST));
		containerLayout.setConstraints(addDefaultPathToListButton,G.nG().aGridXY(3, level).anchor(G.EAST).afill(G.HORIZONTAL).weightX(0));

		level = level+1;
		containerLayout.setConstraints(filePathLabel,G.nG().aGridXY(0, level));
		containerLayout.setConstraints(filePathJList,G.nG().aGridXY(1, level).weightX(1).agw(2).afill(G.BOTH).anchor(G.WEST));
		containerLayout.setConstraints(selectPathButton,G.nG().aGridXY(3, level).anchor(G.EAST).afill(G.HORIZONTAL).weightX(0));

		level = level+1;
		containerLayout.setConstraints(noFilePathLabel,G.nG().aGridXY(0, level));
		containerLayout.setConstraints(noFilePathJList,G.nG().aGridXY(1, level).weightX(1).agw(2).afill(G.BOTH).anchor(G.WEST));
		containerLayout.setConstraints(selectNoPathButton,G.nG().aGridXY(3, level).anchor(G.EAST).afill(G.HORIZONTAL).weightX(0));

		level = level+1;
		containerLayout.setConstraints(fileNameLabel,G.nG().aGridXY(0, level));
		containerLayout.setConstraints(fileNameTypeTextField,G.nG().aGridXY(1, level).agw(3).afill(G.HORIZONTAL).anchor(G.WEST));

		level = level+1;
		containerLayout.setConstraints(fileSuffexNameLabel,G.nG().aGridXY(0, level));
		containerLayout.setConstraints(fileNameTypeJPanel,G.nG().aGridXY(1, level).agw(3).afill(G.HORIZONTAL).anchor(G.WEST));

		level= level+1;
		containerLayout.setConstraints(fileNumLabel,G.nG().aGridXY(0, level));
		//containerLayout.setConstraints(fileNumTextField,G.nG().aGridXY(1, 3).agw(2).afill(G.HORIZONTAL).anchor(G.WEST));
		containerLayout.setConstraints(fileNumComboBox,G.nG().aGridXY(1, level).weightX(1).agw(1).afill(G.HORIZONTAL).anchor(G.WEST));

		level = level+1;
		containerLayout.setConstraints(fileOutputTypeLabel, G.nG().aGridXY(2, level));
		containerLayout.setConstraints(fileOutputTypeComboxBox, G.nG().weightX(0).aGridXY(3, level).afill(G.HORIZONTAL).anchor(G.WEST));
		
		//container
		level= level+1;
		containerLayout.setConstraints(fileOptionLabel, G.nG().aGridXY(0, level));
		containerLayout.setConstraints(fileDirLevelComboBox, G.nG().weightX(0).aGridXY(1, level).afill(G.HORIZONTAL).anchor(G.WEST));

		containerLayout.setConstraints(beginLineNumberLabel, G.nG().aGridXY(2, level));
		containerLayout.setConstraints(beginLineNumberTextField, G.nG().weightX(0).aGridXY(3, level).afill(G.HORIZONTAL).anchor(G.WEST));


		level = level+1;
		containerLayout.setConstraints(buttonsPanel,G.nG().aGridXY(0, level).afill(G.HORIZONTAL).agw(4));

		level= level+1;
		containerLayout.setConstraints(linesEachPageLabel,G.nG().aGridXY(0, level));
		containerLayout.setConstraints(linesEachPageTextField,G.nG().aGridXY(1, level).agw(3).afill(G.HORIZONTAL).anchor(G.WEST));

		level= level+1;
		containerLayout.setConstraints(textAreaScrollPane,G.nG().aGridXY(0, level).agw(4).afill(G.BOTH).awx(1).awy(1));
		//ontainerLayout.setConstraints(button,G.nG().aGridXY(0, 4));
	}
	
	private void add4Listener(){
		clearButton.addActionListener(this);
		saveButton.addActionListener(this);
		viewHistoryButton.addActionListener(this);
		//totalFileNumButton.addActionListener(this);
		//printFileButton.addActionListener(this);
		//printStatButton.addActionListener(this);
		//showInfoButton.addActionListener(this);
		printByAverageLineButton.addActionListener(this);
		printStatByAverageLineButton.addActionListener(this);
		addDefaultPathToListButton.addActionListener(this);
		fileToPdfButton.addActionListener(this);
		
		selectPathButton.addActionListener(this);
		selectNoPathButton.addActionListener(this);
	
		Document document = filePathTextField.getDocument();  
        document.addDocumentListener(this);  
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		String text = e.getActionCommand();
		getUserInput();
		//查看信息
		 if(o==addDefaultPathToListButton){
			System.out.println("i am clicked:"+addDefaultPathToListButton.getText());
			String path = defaultPathTextField.getText();
			selectDirModel.addElement(path);
			//选择
			int size = selectDirModel.getSize();
			int[] selectIntArray= new int[size];
			for(int i=0;i<size;i++){
				selectIntArray[i]=i;
				//filePathJList.setS
			}
			//默认文件夹全选
			filePathJList.setSelectedIndices(selectIntArray);
			//选择第一个作文文件名
			String fileOrDirName=(String) selectDirModel.get(0);
			fileNameTypeTextField.setText(StringUtil.getNameFromPath(fileOrDirName));

		}
		//选择要打印的文件路径
		else if(o==selectPathButton) {
			System.out.println("i am clicked:"+selectPathButton.getText());
			openFile();
		}else if(o==selectNoPathButton){
			System.out.println("i am clicked:"+selectNoPathButton.getText());
			openNoFile();
		}
		else if(o==clearButton) {
			System.out.println("i am clicked:"+clearButton.getText());
			//openFile();
			clearTextArea();
		}else if(o==saveButton){
			 System.out.println("保存当前项目");
			 doSaveCurrentProject();
		 }else if(o==viewHistoryButton){
			 System.out.println("查询历史项目");
			 doViewHistoryProjects();
		 }


		if(text.equals("按行数分割(目前正使用的)")){
			System.out.println("i am clicked:"+printByAverageLineButton.getText());
			if(checkInput()){
				performByAverageFileLineConcat(Constant.FILE_CONCAT);
				//computerTotalFileNum();
			}
		}else if(text.equals("打印行数平均信息")){
			System.out.println("i am clicked:"+printStatByAverageLineButton.getText());
			if(checkInput()){
				performByAverageFileLineConcat(Constant.PRINT_INFO);
				//computerTotalFileNum();1
			}
		}else if(text.equals("file转pdf")){
			System.out.println("file转pdf");
			if(checkInput()){
				//performByAverageFileLineConcat(1);
				//computerTotalFileNum();1
				fileToPdf();
			}
		}
	}

	private void doSaveCurrentProject(){
		System.out.println("do save current project");

		List<String> pathList = filePathJList.getSelectedValuesList();
		List<String> excludePathList = noFilePathJList.getSelectedValuesList();
		String preFileName = fileNameTypeTextField.getText().trim();
		List<String>  fileTypeList = new ArrayList<>();

		//filterTypeSet = new HashSet<String>();

		for(JCheckBox checkBox:typeChoiceCheckboxList){
			if(checkBox.isSelected()){
				String type = checkBox.getText();
				System.out.println(type);
				fileTypeList.add(type);
			}
		}

		String averageFileNum = fileNumTextField.getText().trim();
		String outputFileType = (String)fileOutputTypeComboxBox.getSelectedItem();
		String deepFileLevel = (String)fileDirLevelComboBox.getModel().getSelectedItem();
		Integer beginLineNumber = Integer.parseInt(beginLineNumberTextField.getText().trim());

		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setPrefixName(preFileName);
		projectInfo.setAverageFileNum(averageFileNum);
		projectInfo.setFilePathList(pathList);
		projectInfo.setExcludePathList(excludePathList);
		projectInfo.setBeginLineNumber(beginLineNumber);
		projectInfo.setBeginLineNumber(beginLineNumber);
		projectInfo.setFileDeepLevel(deepFileLevel);
		projectInfo.setOutputFileType(outputFileType);
		projectInfo.setSuffexTypeList(fileTypeList);

		String projectInfoJson = JSON.toJSONString(projectInfo);
        //use gson
        Gson gson = new Gson();
        String jsonStr = gson.toJson(projectInfo, ProjectInfo.class);

		appendTextPaneContent(projectInfoJson);

		FileJsonKit.saveToProperties(projectInfo);
		//fileNameTypeCheckBox.getSelectedObjects();
	}

	private void doViewHistoryProjects(){
		System.out.println("do view history projects");

		String content =FileJsonKit.loadFromProperties();
		appendTextPaneContent(content);
	}
	
	private void clearTextArea(){
		textPane.setText("");
	}

	private void fileToPdf(){
		try {
		System.out.println("file to pdf");
		for(File f:targetFiles){
			String fcontent = FileUtil.readFileByLines(f.getAbsolutePath()+".doc");
			String targetPath = f.getPath();
			String targetName = f.getName()+".pdf";
			System.out.println("target path:"+f.getPath());
			System.out.println("target Name:"+f.getName());
			String destAbsPath = targetPath+".pdf";
			File destFile = new File(destAbsPath);

			if(!destFile.exists()) destFile.createNewFile();
			System.out.println("dest file:"+destFile.getAbsolutePath());
			ToPDFUtils.toPdf(fcontent,destFile);
		}}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void computerTotalFileNum(){
		DirFileConcat dirFileConcat = new DirFileConcat();
		dirFileConcat.setFilterTypeSet(filterTypeSet);
		dirFileConcat.setPartionNum(particionNums);
		dirFileConcat.setPreName(name);
		dirFileConcat.setSrcDir(srcPath);
		
		//使用文件列表
		List<String> selectFilePathList=filePathJList.getSelectedValuesList();
		//这一句很重要，必须要有
		dirFileConcat.setSrcDirList(selectFilePathList);

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
//		if(srcPath==null||srcPath.equalsIgnoreCase("")){
//			msg="文件夹不能为空";
//			System.out.println(msg);
//			appendTextAreaContent(msg);
//			result = false;
//			
//		}
		
		if(name==null||name.equalsIgnoreCase("")){
			msg="文件名不能为空";
			System.out.println(msg);
			appendTextAreaContent(msg);
			result = false;
		}


		selectFilePathList =filePathJList.getSelectedValuesList();
		int selectFilePathSize = selectFilePathList.size();
		if(selectFilePathSize<=0){
			msg="请选择打印的文件路劲";
			System.out.println(msg);
			appendTextAreaContent(msg);
			return false;
		}
		return result;	
	}
	
	
	private void appendTextAreaContent(String msg){
		infoTextArea.append(msg);
		infoTextArea.append("\n");
	}

	private void appendTextPaneContent(String msg){
		textPane.setText(textPane.getText()+msg+"\n");
	}
	
	
	private void performFileConcat(int buttonType){
		System.out.println("button,begin1111");
		
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
		appendTextPaneContent(msg);

		JButton openButton= new JButton("open");
		//appendTextAreaContent(openButton);
		infoTextArea.add(openButton);
		openButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("open file");
			}
		});

		appendTextPaneContent("------------------------");
		
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
		appendTextPaneContent(sb.toString());
		appendTextPaneContent("haha");
		//System.out.println("任务执行完毕");
	}
	
	
	private void performByAverageFileLineConcat(int buttonType){
		System.out.println("button,begin");
		String msg = "";
		//后缀名
		String suffixName = (String)fileOutputTypeComboxBox.getSelectedItem();
		//文件夹深度，这个参数暂时还没使用，这个必须选择才行，有没有自动选择的
		String fileDirLevelString = (String)fileDirLevelComboBox.getSelectedItem();
		int fileDirLevel = Integer.parseInt(fileDirLevelString);
		
		System.out.println("you selec tsuffix Name:"+suffixName);
		System.out.println("select dir level:"+fileDirLevel);

		//选择的文件列表
		List<String> selectFilePathList =filePathJList.getSelectedValuesList();
		List<String> selectNoFilePathList = noFilePathJList.getSelectedValuesList();

		//这一句很重要，必须要有
		dirFileConcat = new DirFileConcat();
		dirFileConcat.setSrcDirList(selectFilePathList);
		dirFileConcat.setNoSrcDirList(selectNoFilePathList);
		dirFileConcat.setFilterTypeSet(filterTypeSet);
		dirFileConcat.setBeginLineNumber(Integer.parseInt(beginLineNumberTextField.getText().trim()));
		//文件份数
		dirFileConcat.setPartionNum(particionNums);
		//文件名前缀
		dirFileConcat.setPreName(name);
		//这个是单独目录的，现在不用
		dirFileConcat.setSrcDir(srcPath);
		//后缀
		dirFileConcat.setSuffixName(suffixName);
		//深度
		dirFileConcat.setFileDirLevel(fileDirLevel);
		
		//dirFileConcat.computerFileItems();
		//这个方法确定名称
		dirFileConcat.computerFileScanLineIndex();
		List<FilePart> fileItemList = dirFileConcat.getFileItemList();
		for(FilePart fileItem:fileItemList){
			System.out.println("------------------------------");
			//showinfo只是显示信息
			if(buttonType==Constant.FILE_INFO){
				Thread t = new Thread(new FilePartShowRunnable(fileItem));
				t.start();
				try {
					t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//printStat打印统计
			}else if(buttonType==Constant.PRINT_INFO){
				Thread t = new Thread(new FilePartPrintInfoRunnable(fileItem));
				t.start();
				try {
					t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//concat需要聚合到一个word文件中去
			}else if(buttonType==Constant.FILE_CONCAT){
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
		appendTextPaneContent(msg);
		//appendTextPaneContent("222222222222222222222222222222222");
		
		showPrintResultInfo();
		
		//System.out.println("任务执行完毕");
	}
	
	
	private void showPrintResultInfo(){
		
		List<FilePart> fileItemList = dirFileConcat.getFileItemList();

		appendTextPaneContent("------------------------");
		
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
		String toDirs = dirFileConcat.getToDirs();
		targetFiles =new ArrayList<>();
		File toFileDir = new File(toDirs);
		//last stastic info
		for(FilePart fileItem:fileItemList){
			sb.append(fileItem.getName()+"\t\t");
			sb.append(fileItem.getTotalFiles()+"\t\t");
			sb.append(fileItem.getTotalLine()+"\t\t");
			String filePath = toDirs+"\\"+fileItem.getName();
			File targetFile = new File(filePath);
			//生成的目标文件
			targetFiles.add(targetFile);
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

		sb.append("生成目标文件所在的目录:"+toFileDir.getAbsolutePath()+"\n");
		JButton fileButton = new JButton(toFileDir.getAbsolutePath());
		fileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("打开文件");
				//或者直接调用资源管理器
				try {
					Desktop.getDesktop().open(new File(e.getActionCommand()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				//openDir(e.getActionCommand());
			}
		});
		appendTextPaneContent(sb.toString());
		textPane.insertComponent(fileButton);
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
			
			//selectDirsVector.add(f.getAbsolutePath());
			selectDirModel.addElement(f.getAbsolutePath());
			int size = selectDirModel.getSize();
			int[] selectIntArray= new int[size];
			for(int i=0;i<size;i++){
				selectIntArray[i]=i;
				//filePathJList.setS
			}
			//默认文件夹全选
			filePathJList.setSelectedIndices(selectIntArray);
			String fileOrDirName=(String) selectDirModel.get(0);
			fileNameTypeTextField.setText(StringUtil.getNameFromPath(fileOrDirName));

			//filePathJList.setModel(model);
			//filePathJList = new JList(selectDirsVector);
			//filePathTextField.setText(f.getAbsolutePath());
		}
	}

	private void openNoFile() // 打开文件夹，不需要打印的
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

			//selectDirsVector.add(f.getAbsolutePath());
			//selectDirModel.addElement(f.getAbsolutePath());
			noSelectDirModel.addElement(f.getAbsolutePath());
			int size = noSelectDirModel.getSize();
			int[] selectIntArray= new int[size];
			for(int i=0;i<size;i++){
				selectIntArray[i]=i;
				//filePathJList.setS
			}
			//filePathJList.setSelectedIndices(selectIntArray);
			noFilePathJList.setSelectedIndices(selectIntArray);
			//filePathJList.setModel(model);
			//filePathJList = new JList(selectDirsVector);

			//filePathTextField.setText(f.getAbsolutePath());
		}
	}

	private void openDir(String path) // 打开文件夹，不需要打印的
	{
		// 设置打开文件对话框的标题
		fc.setDialogTitle("Open File");
		int flag=-1;
		File f=new File(path);

		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		// 这里显示打开文件的对话框
		try {
			//fc.changeToParentDirectory();
			fc.setCurrentDirectory(f);
			flag = fc.showOpenDialog(null);
		} catch (HeadlessException head) {
			System.out.println("Open File Dialog ERROR!");
		}
		if (flag == JFileChooser.APPROVE_OPTION) {
			// 获得该文件
			f = fc.getSelectedFile();
			System.out.println("open file----" + f.getName());
			try {
				Desktop.getDesktop().open(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
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

		appendTextPaneContent(s);
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

		appendTextPaneContent(s);
		
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

		appendTextPaneContent(s);
		
	} 

}
