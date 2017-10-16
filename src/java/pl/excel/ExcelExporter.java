/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package pl.excel;
/**
 *
 * @author k.skowronski
 */

import java.io.*;
import javax.swing.*;
import javax.swing.table.TableModel;

public class ExcelExporter {
    
    
    private JFileChooser fc = new JFileChooser(".xls");
    final JPanel panel = new JPanel();
    
    public String ExportTable( TableModel model )
    {
        String msg;
        
        
        File file = fc.getSelectedFile();

        
        
        // 02 - tworzę i zapisuję plik
          try
          {
              Writer out2 = new BufferedWriter(new OutputStreamWriter(
                      
                      new FileOutputStream( file + ".xls"),"UTF-8"));
                      //new FileOutputStream("c:\\vet\\" + "1 - PZ_" + labDzien.getText().substring(0, 7) +".csv"),"UTF-8"));
              
              for( int i = 0; i < model.getRowCount(); i++ )
               {
                if ( i > 0 )   
                out2.write( '\n'  );   
                for( int j = 0; j < model.getColumnCount(); j++ )
                {
                //Create your File Writer
                 if ( model.getValueAt(i, j) != null )
                 {
                     msg = model.getValueAt(i, j).toString() + "\t";  
                 }
                 else
                 {
                     msg = "\t";  
                 }
                 
                 out2.write( msg  );
                }
               }
              //out2.write( "sdfdsf" );
              out2.close();
              
              
          }
          catch ( Exception e) { 
              JOptionPane.showMessageDialog(panel, e, "Error",JOptionPane.INFORMATION_MESSAGE);
          }
        
        return "OK";
    }  
    
    public String ExportTablePanel( TableModel model, JPanel okno)
    {
        String msg;
        
        if(fc.showSaveDialog(okno)!=JFileChooser.APPROVE_OPTION)
        {
           return "Error";
        }
        File file = fc.getSelectedFile();

        
        
        // 02 - tworzę i zapisuję plik
          try
          {
              Writer out2 = new BufferedWriter(new OutputStreamWriter(
                      
                      new FileOutputStream( file + ".csv")));
                      //new FileOutputStream("c:\\vet\\" + "1 - PZ_" + labDzien.getText().substring(0, 7) +".csv"),"UTF-8"));
              
              for( int i = 0; i < model.getRowCount(); i++ )
               {
                if ( i > 0 )   
                out2.write( '\n'  );   
                for( int j = 0; j < model.getColumnCount(); j++ )
                {
                //Create your File Writer
                 if ( model.getValueAt(i, j) != null )
                 {
                     msg = model.getValueAt(i, j).toString() + ";";  
                 }
                 else
                 {
                     msg = ";";  
                 }
                 
                 out2.write( msg  );
                }
               }
              //out2.write( "sdfdsf" );
              out2.close();
              
              
          }
          catch ( Exception e) { 
              JOptionPane.showMessageDialog(panel, e, "Error",JOptionPane.INFORMATION_MESSAGE);
          }
        
        return "OK";
    }  
    
    
}
