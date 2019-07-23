/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package js.com.file.utils;

import java.io.File;
import java.util.Date;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Administrator
 */
public class FileTableModel extends  AbstractTableModel{
    
     String[] names={"文件名","文件大小"};
    File[] files =null;
    //List<FileEx> filexs=new ArrayList();

    public FileTableModel(File[] files) {
        this.files = files;
    }
    
    public int getRowCount(){
        return files.length;
    }
    public int getColumnCount(){
        return  names.length;
    } 
    public Object getValueAt(int row, int column){
        if(column==0){
            return this.files[row].getName();
        }else if(column==1){
            return new Date(this.files[row].lastModified()).toString();
        }
        return "";
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public File[] getFiles() {
        return files;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }
    
    
    
}
