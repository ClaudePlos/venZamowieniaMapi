/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.views.zam;

import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;




/**
 *
 * @author k.skowronski
 */
public class GridZamowieniaRow extends GenericForwardComposer{

  private Grid inboxGrid;
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    
    //inboxGrid.setModel(new ListModelList(getData()));

  }
  
  

  /* simply return a small model here , you could read data from database for your own implementation.*/
  private List<String[]> getData(){
    ArrayList<String[]> list= new ArrayList<String[]>();
    
    list.add(new String[]{"Test Mail1","TonyQ", "10k"});
    list.add(new String[]{"Test Mail12","Ryan", "100k"});
    list.add(new String[]{"Test Mail13","Simon", "15k"});
    list.add(new String[]{"Test Mail14","Jimmy", "5k"});    
    return list;
  }
  
  
  
}
