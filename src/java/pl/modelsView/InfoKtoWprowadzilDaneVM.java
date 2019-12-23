/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.modelsView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;
import pl.models.reports.KtoWprowadzilDaneDTO;
import pl.session.ServiceFacade;

/**
 *
 * @author k.skowronski
 */
public class InfoKtoWprowadzilDaneVM {
    

    ServiceFacade serviceFacade = ServiceFacade.getInstance();
    
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public List<KtoWprowadzilDaneDTO> ktoWprowadzilDaneList;

  
    
    
    
    @Command
    @NotifyChange("infoKtoWprowadzilKiedy")
    public void infoKtoWprowadzilKiedy(@BindingParam("naDzien") Date naDzien, @BindingParam("kierKosztow") int kierKosztow, @BindingParam("grupaZywionych") String grupaZywionych){
      
        ktoWprowadzilDaneList = serviceFacade.infoKtoWprowadzilKiedy( formatter.format( naDzien ), kierKosztow, grupaZywionych);
        

        Window window = (Window)Executions.createComponents(
                "/info/kto_wprowadzil_dane.zul", null, null);
        window.doModal();
        
        
        
        
        Listbox listBox = new Listbox();
        listBox.setHeight("450px");
        
        Listheader listHeader = new Listheader();
        listHeader.setLabel("Operator");
        listHeader.setWidth("25%");
        
        Listheader listHeader0 = new Listheader();
        listHeader0.setLabel("Data zmiany");
        listHeader0.setWidth("18%");
        
        Listheader listHeader1 = new Listheader();
        listHeader1.setLabel("Grupa Zywionych");
        listHeader1.setWidth("30%");
        
        Listheader listHeader2 = new Listheader();
        listHeader2.setLabel("Data obrotu");
        listHeader2.setWidth("18%");
        
        Listheader listHeader3 = new Listheader();
        listHeader3.setLabel("Dieta");
        listHeader3.setWidth("30%");
        
        Listheader listHeader4 = new Listheader();
        listHeader4.setLabel("Posilek");
        listHeader4.setWidth("30%");
        
        Listheader listHeader5 = new Listheader();
        listHeader5.setLabel("Ilosc");
        listHeader5.setWidth("10%");
        
        
        
        Listhead listHead = new Listhead();
        listHead.appendChild(listHeader);
        listHead.appendChild(listHeader0);
        listHead.appendChild(listHeader1);
        listHead.appendChild(listHeader2);
        listHead.appendChild(listHeader3);
        listHead.appendChild(listHeader4);
        listHead.appendChild(listHeader5);
        
        
        listBox.appendChild(listHead);

       
        	
        window.appendChild(listBox);
        
        
        
        for ( KtoWprowadzilDaneDTO kDTO : ktoWprowadzilDaneList)
        { 
            Listitem listItem1 = new Listitem();
            //Listitem listItem2 = new Listitem();
            //Listitem listItem3 = new Listitem();
            Listcell listCell  = new Listcell();
            Listcell listCell0 = new Listcell();
            Listcell listCell1 = new Listcell();
            Listcell listCell2 = new Listcell();
            Listcell listCell3 = new Listcell();
            Listcell listCell4 = new Listcell();
            Listcell listCell5 = new Listcell();
            
            listCell.setLabel( kDTO.getOperator() );
            listCell0.setLabel( formatter2.format(kDTO.getdZmiany()) );
            listCell1.setLabel( kDTO.getGz() );
            listCell2.setLabel( formatter.format(kDTO.getdObrotu()) );
            listCell3.setLabel( kDTO.getDieta() );
            listCell4.setLabel( kDTO.getPosilek());
            listCell5.setLabel( kDTO.getIlosc().toString() );
            
            listItem1.appendChild(listCell);
            listItem1.appendChild(listCell0);
            listItem1.appendChild(listCell1);
            listItem1.appendChild(listCell2);
            listItem1.appendChild(listCell3);
            listItem1.appendChild(listCell4);
            listItem1.appendChild(listCell5);
            
            listBox.appendChild(listItem1);
            listBox.setVisible(true);
            
        }
        
        
        
        
    }
    
    
    
}
