/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.views.zam;

import java.util.List;
import javax.ejb.EJB;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;
import pl.models.StanZywionychNaDzienDTO;
import pl.modelsView.StanZywionychNaDzienVM;
import pl.session.ServiceFacade;

/**
 *
 * @author k.skowronski
 */
public class MainController extends SelectorComposer<Window> {
    
    @EJB 
    ServiceFacade serviceFacade = new ServiceFacade();
    
    @Wire
    Vlayout result; 
    
    
     @Listen("onClick=#test")
    public void gzWybrane(Event event) { //register a listener to a component called retrieve

            if (result.getChildren().isEmpty() )
            {
                Grid grid = new Grid();
                
              
                        
                
                result.appendChild(new Label("test") );  
            }
    
    }
    
    
    
}
