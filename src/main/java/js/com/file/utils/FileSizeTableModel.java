/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package js.com.file.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Administrator
 */
public class FileSizeTableModel extends  AbstractTableModel{
    String[] names={"文件名","文件大小"};
    File[] files =null;
    List<FileEx> filexs=new ArrayList();

    public FileSizeTableModel(List<FileEx> filexs) {
        this.filexs = filexs;
    }
    
    public int getRowCount(){
        return filexs.size();
    }
    public int getColumnCount(){
        return  names.length;
    } 
    public Object getValueAt(int row, int column){
        if(column==0){
            return filexs.get(row).getName();
        }else if(column==1){
            return filexs.get(row).getFormatSize();
        }
        return "";
    } 
    
    
    
   
    
    
}
