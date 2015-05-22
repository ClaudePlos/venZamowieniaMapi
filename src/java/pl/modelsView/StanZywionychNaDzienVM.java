/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.modelsView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Footer;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import pl.models.StanZywionychNaDzienDTO;
import pl.models.StanZywionychNaDzienSumaDTO;
import pl.session.ServiceFacade;

/**
 *
 * @author k.skowronski
 */

public class StanZywionychNaDzienVM extends SelectorComposer<Component> {
    
    @EJB 
    ServiceFacade serviceFacade = ServiceFacade.getInstance();
    
    private static volatile StanZywionychNaDzienVM instance = null;
    
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    
    ListModel<String> grupaZywionychModel = new ListModelList<String>( serviceFacade.getGrupaZywionych() );

    public static StanZywionychNaDzienVM getInstance() {
        if (instance == null) {
          instance = new StanZywionychNaDzienVM();
        }
        return instance;
    }
    
    public List<StanZywionychNaDzienDTO> stanyZywionychNaDzien = serviceFacade.stanyZywionychNaDzien;
    
    public List<StanZywionychNaDzienSumaDTO> stanyZywionychNaDzienSuma = serviceFacade.stanyZywionychNaDzienSuma;
 

    public List<StanZywionychNaDzienDTO> getStanyZywionychNaDzien() {
		return stanyZywionychNaDzien;
    }

    public void setStanyZywionychNaDzien(List<StanZywionychNaDzienDTO> stanyZywionychNaDzien) {
        this.stanyZywionychNaDzien = stanyZywionychNaDzien;
    }

    public ListModel<String> getGrupaZywionychModel() {
        return grupaZywionychModel;
    }

    public List<StanZywionychNaDzienSumaDTO> getStanyZywionychNaDzienSuma() {
        return stanyZywionychNaDzienSuma;
    }

    public void setStanyZywionychNaDzienSuma(List<StanZywionychNaDzienSumaDTO> stanyZywionychNaDzienSuma) {
        this.stanyZywionychNaDzienSuma = stanyZywionychNaDzienSuma;
    }
    
    

    

   
    
    
  
    public StanZywionychNaDzienVM()
    {
        uzupelnijSumeStanowNaDzie();
       //stanyZywionychNaDzien = serviceFacade.pobierzStanZywionychWdniuDlaGrupyZywionych("2015-04-01","aa");
        
      // Messagebox.show("StanZywionychNaDzienVM-StanZywionychNaDzienVM");
    }

    @Command
    @NotifyChange("stanyZywionychNaDzien")
    public void deleteAllMail() {
        Messagebox.show("StanZywionychNaDzienVM-deleteAllMail");
        stanyZywionychNaDzienSuma.clear();
    }
    
    @Command
    @NotifyChange("stanyZywionychNaDzien")
    public void pobInne(@BindingParam("naDzien") Date naDzien, @BindingParam("grupaZywionych") String grupaZywionych) {
      //  Messagebox.show("StanZywionychNaDzienVM-pobInne"+naDzien+grupaZywionych);
        serviceFacade.uzupelnijZeramiStanWdniu(formatter.format( naDzien ));
        stanyZywionychNaDzien  = serviceFacade.pobierzStanZywionychWdniuDlaGrupyZywionych(formatter.format( naDzien ),grupaZywionych);
    }
    
    
    
   @Listen("onClick=#gzWybrane")
    public void gzWybrane(Event event) { //register a listener to a component called retrieve
          grupaZywionychModel = new ListModelList<String>( serviceFacade.getGrupaZywionych() );
    }   
    
    @Command
    @NotifyChange("stanyZywionychNaDzien")
    public void zapiszStanZyw() {
      //  Messagebox.show("StanZywionychNaDzienVM-pobInne"+naDzien+grupaZywionych);
        String ret = serviceFacade.zapiszStanZywionychWDniu(stanyZywionychNaDzien);
        
        if ( ret.equals("OK") )
        {
           Messagebox.show("Zamówienie zapisane");
        }
        else
        {
            Messagebox.show("Coś poszło nie tak !");
        }
    }
    
    @Command
    @NotifyChange("stanyZywionychNaDzienSuma")
    public void uzupelnijSumeStanowNaDzie()
    {
        BigDecimal sumSniadanie =  new BigDecimal(0); 
        BigDecimal sumObiad =  new BigDecimal(0);
        BigDecimal sumKolacja =  new BigDecimal(0);
                
        if (stanyZywionychNaDzienSuma.isEmpty())
        {
          StanZywionychNaDzienSumaDTO s = new StanZywionychNaDzienSumaDTO(); 
          stanyZywionychNaDzienSuma.add(s);
        }
             
        
        for ( StanZywionychNaDzienDTO stan : stanyZywionychNaDzien )
        {
            if ( stan.getsPil() != null )
                sumSniadanie = sumSniadanie.add(stan.getsPil());
            
            if ( stan.getoPil() != null )
                sumObiad = sumObiad.add(stan.getoPil());
            
            if ( stan.getkPil() != null )
                sumKolacja = sumKolacja.add(stan.getkPil());
        }
        
        for ( StanZywionychNaDzienSumaDTO suma : stanyZywionychNaDzienSuma)
        {
            suma.setsPilSum( sumSniadanie ); 
            suma.setoPilSum( sumObiad );
            suma.setkPilSum( sumKolacja );
        }    
    }
    
}
