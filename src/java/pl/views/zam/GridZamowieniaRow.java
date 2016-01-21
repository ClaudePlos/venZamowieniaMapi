/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.views.zam;


import java.math.BigDecimal;
import javax.ejb.EJB;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import pl.models.StanZywionychNaDzienDTO;
import pl.modelsView.StanZywionychNaDzienVM;

/**
 *
 * @author k.skowronski
 */
public class GridZamowieniaRow implements RowRenderer<StanZywionychNaDzienDTO> {
    
    
    
    @Override
    public void render( Row row,  StanZywionychNaDzienDTO data, int index) throws Exception {

        
        
        row.appendChild(new Label(data.getDietaNazwa()));
        
        if ( data.getSniadaniePlanIl() != null )
        {
            Spinner spin1 = new Spinner();
            spin1.setInplace(true);
            
         // if ( spin1.re )
            
            spin1.setValue(data.getSniadaniePlanIl().intValue() );
            spin1.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
                public void onEvent(Event event) throws Exception {
                 //  data.setSniadaniePlanIl(new BigDecimal( row.getValue().toString()));
                }
            });
                 
            row.appendChild( spin1 );  
        }  
        else
          row.appendChild(new Label()); 
        
        if ( data.getDrugieSniadaniePlanIl() != null )
        {
            Spinner spin1 = new Spinner();
            spin1.setInplace(true);
            spin1.setValue( data.getDrugieSniadaniePlanIl().intValue() );
            spin1.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
                public void onEvent(Event event) throws Exception {
                 
                }
            });
                 
            row.appendChild( spin1 ); 
        }
        else
          row.appendChild(new Label());
	
        
    }
    
    
    
}
