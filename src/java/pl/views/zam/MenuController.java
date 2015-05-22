/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.views.zam;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import org.apache.poi.poifs.property.Child;
import org.zkoss.bind.annotation.DependsOn;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;
import pl.authentication.AuthenticationServiceChapter8Impl;
import pl.models.GrupaZywionychVO;
import pl.services.AuthenticationService;
import pl.session.ServiceFacade;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import pl.modelsView.StanZywionychNaDzienVM;

/**
 *
 * @author k.skowronski
 */
public class MenuController extends SelectorComposer<Component> {
    
    AuthenticationService authService = new AuthenticationServiceChapter8Impl();
    
    @EJB 
    ServiceFacade serviceFacade = ServiceFacade.getInstance();
    
    List<GrupaZywionychVO> grupyZywionych;
    
    ListModel<String> grupaZywionychModel = new ListModelList<String>( serviceFacade.getGrupaZywionych() );
    
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    
    @Wire
    private Window win;
    
    @Wire
    private Radiogroup sv1;
    
    @Wire
    private Vlayout result; //wired to a component called result
    
    @Wire
    private Datebox naDzien;     
    
    @Wire
    private Combobox cmbGZ;   
    
    private String wybranaGrupaZywionych;
    
    Combobox cmbGrupaZywionych;
    
    
    public MenuController()
    {
        serviceFacade.listaGrupyZywionych();
        
       /* Label l = new Label();
        l.setValue( "Witaj" + serviceFacade.user.getKod() );
        win.appendChild(l);*/
    }

    public ListModel<String> getGrupaZywionychModel() {
        return grupaZywionychModel;
    }

    public String getWybranaGrupaZywionych() {
        return wybranaGrupaZywionych;
    }

    public void setWybranaGrupaZywionych(String wybranaGrupaZywionych) {
        this.wybranaGrupaZywionych = wybranaGrupaZywionych;
    }
    
    
    
    public List<GrupaZywionychVO> getGrupyZywionych() {
        serviceFacade.listaGrupyZywionych();
        
        grupyZywionych = serviceFacade.grupyZywionych;
        return grupyZywionych;
    }
    
    
    
    @DependsOn({"wybranaGrupaZywionych"})
    public void getShirtImage() {
       Messagebox.show(wybranaGrupaZywionych);
    }
    
    
    
    @Listen("onClick = button#pobierzStan")
    public void pobierzStan(Event event){
          
        Date stanNaDzien = naDzien.getValue();
          
         serviceFacade.stanyZywionychNaDzien 
                 = serviceFacade.pobierzStanZywionychWdniuDlaGrupyZywionych( formatter.format( stanNaDzien ), cmbGZ.getSelectedItem().getValue().toString() );
         
         //StanZywionychNaDzienVM.getInstance().pobInne(formatter.format( stanNaDzien ),  cmbGZ.getSelectedItem().getValue().toString());
      //Comboitem zaznaczonaGrupaZywionych =  cmbGZ.getSelectedItem().getValue();
      //Messagebox.show(stanNaDzien.toString() + cmbGZ.getSelectedItem().getValue());
    }
    
    @Listen("onClick = button#logout")
    public void doLogout(){
            authService.logout();		
            Executions.sendRedirect("/");
    }
    
    
   
    @Listen("onClick=#gzWybrane")
    public void gzWybrane(Event event) { //register a listener to a component called retrieve
          grupaZywionychModel = new ListModelList<String>( serviceFacade.getGrupaZywionych() );
          
            if ( cmbGZ != null )
            {
            
                cmbGrupaZywionych = new Combobox();
                cmbGrupaZywionych.setModel(grupaZywionychModel);  
               // result.appendChild(cmbGrupaZywionych);
                //cmbGZ = new Combobox();
                cmbGZ.setModel(grupaZywionychModel);
            }
    
    }
    
    @Listen("onClick=#gzWszystkie")
    public void gzWszystkie(Event event) {
          if ( cmbGrupaZywionych != null )
            result.removeChild(cmbGrupaZywionych);
    }
    
    
    
}