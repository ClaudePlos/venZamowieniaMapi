/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.modelsView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Auxhead;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;
import pl.models.StanZywionychNaDzienDTO;
import pl.session.ServiceFacade;

/**
 *
 * @author k.skowronski
 */
public class StanZywionychNaDzienPodsumowanieVM  {
   
     
    @EJB 
    ServiceFacade serviceFacade = ServiceFacade.getInstance();
    
    public List<StanZywionychNaDzienDTO> stanyZywionychNaDzien2 = serviceFacade.pobierzStanZywionychWdniuDlaKierunkuKosztow( null, 0 );

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public List<StanZywionychNaDzienDTO> getStanyZywionychNaDzien2() {
        return stanyZywionychNaDzien2;
    }

    public void setStanyZywionychNaDzien2(List<StanZywionychNaDzienDTO> stanyZywionychNaDzien2) {
        this.stanyZywionychNaDzien2 = stanyZywionychNaDzien2;
    }

    
    
    
    
    
    
    @Command
    @NotifyChange("stanyZywionychNaDzien")
    public void stanZywionychGlobal(@BindingParam("naDzien") Date naDzien, @BindingParam("kierKosztow") int kierKosztow){

        //stanyZywionychNaDzien = new ArrayList<StanZywionychNaDzienDTO>();
        
        
        Window window = (Window)Executions.createComponents(
                "/zamowieniaPodsumowanie/podsumowanie_dialog.zul", null, null);
        window.doModal();
        
        int s = 0;
        int dS = 0;
        int o = 0;
        int p = 0;
        int k = 0;
        int pn = 0;
        int Ks = 0;
        int KdS = 0;
        int Ko = 0;
        int Kp = 0;
        int Kk = 0;
        int Kpn = 0;
        
        Listbox listBox = new Listbox();
        listBox.setHeight("450px");
        
        Listheader listHeader = new Listheader();
        listHeader.setLabel("LP");
        listHeader.setWidth("7%");
        
        Listheader listHeader0 = new Listheader();
        listHeader0.setLabel("Dieta");
        listHeader0.setWidth("30%");
        
        Listheader listHeader1 = new Listheader();
        listHeader1.setLabel("Ś");
        listHeader1.setWidth("10%");
        
        Listheader listHeader2 = new Listheader();
        listHeader2.setLabel("IIŚ");
        listHeader2.setWidth("10%");
        
        Listheader listHeader3 = new Listheader();
        listHeader3.setLabel("O");
        listHeader3.setWidth("10%");
        
        Listheader listHeader4 = new Listheader();
        listHeader4.setLabel("P");
        listHeader4.setWidth("10%");
        
        Listheader listHeader5 = new Listheader();
        listHeader5.setLabel("K");
        listHeader5.setWidth("10%");
        
        Listheader listHeader6 = new Listheader();
        listHeader6.setLabel("PN");
        listHeader6.setWidth("10%");
        
        Listheader listHeader7 = new Listheader();
        listHeader7.setLabel("K/Ś");
        listHeader7.setStyle("color: red;");
        listHeader7.setWidth("10%");
        
        Listheader listHeader8 = new Listheader();
        listHeader8.setLabel("K/IIŚ");
        listHeader8.setStyle("color: red;");
        listHeader8.setWidth("10%");
        
        Listheader listHeader9 = new Listheader();
        listHeader9.setLabel("K/O");
        listHeader9.setStyle("color: red;");
        listHeader9.setWidth("10%");
        
        Listheader listHeader10 = new Listheader();
        listHeader10.setLabel("K/P");
        listHeader10.setStyle("color: red;");
        listHeader10.setWidth("10%");
        
        Listheader listHeader11 = new Listheader();
        listHeader11.setLabel("K/K");
        listHeader11.setStyle("color: red;");
        listHeader11.setWidth("10%");
        
        Listheader listHeader12 = new Listheader();
        listHeader12.setLabel("K/PN");
        listHeader12.setStyle("color: red;");
        listHeader12.setWidth("10%");
        
        
        
        Listhead listHead = new Listhead();
        listHead.appendChild(listHeader);
        listHead.appendChild(listHeader0);
        listHead.appendChild(listHeader1);
        listHead.appendChild(listHeader2);
        listHead.appendChild(listHeader3);
        listHead.appendChild(listHeader4);
        listHead.appendChild(listHeader5);
        listHead.appendChild(listHeader6);
        listHead.appendChild(listHeader7);
        listHead.appendChild(listHeader8);
        listHead.appendChild(listHeader9);
        listHead.appendChild(listHeader10);
        listHead.appendChild(listHeader11);
        listHead.appendChild(listHeader12);
        listBox.appendChild(listHead);

       
        	
        window.appendChild(listBox);
        
        
        
        
        stanyZywionychNaDzien2.clear();
        
        stanyZywionychNaDzien2 = serviceFacade.pobierzStanZywionychWdniuDlaKierunkuKosztowGlobal(formatter.format(naDzien), kierKosztow );
        
        for ( StanZywionychNaDzienDTO szDTO : stanyZywionychNaDzien2)
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
            Listcell listCell6 = new Listcell();
            Listcell listCell7 = new Listcell();
            listCell7.setStyle("background-color: #BBC2DB;color: red;");
            Listcell listCell8 = new Listcell();
            listCell8.setStyle("background-color: #BBC2DB;color: red;");
            Listcell listCell9 = new Listcell();
            listCell9.setStyle("background-color: #BBC2DB;color: red;");
            Listcell listCell10 = new Listcell();
            listCell10.setStyle("background-color: #BBC2DB;color: red;");
            Listcell listCell11 = new Listcell();
            listCell11.setStyle("background-color: #BBC2DB;color: red;");
            Listcell listCell12 = new Listcell();
            listCell12.setStyle("background-color: #BBC2DB;color: red;");
            
            listCell.setLabel( szDTO.getLp().toString() );
            listCell0.setLabel( szDTO.getDietaNazwa() );
            
            if ( szDTO.getSniadaniePlanIl() != null )
            {
                 listCell1.setLabel( szDTO.getSniadaniePlanIl().toString() );
                 s = s + szDTO.getSniadaniePlanIl().intValue();
            }
               
            
            if ( szDTO.getDrugieSniadaniePlanIl() != null )
            {
                listCell2.setLabel( szDTO.getDrugieSniadaniePlanIl().toString() );
                dS = dS + szDTO.getDrugieSniadaniePlanIl().intValue();
            }
                
            
            if ( szDTO.getObiadPlanIl()!= null )
            {
               listCell3.setLabel( szDTO.getObiadPlanIl().toString() );
               o = o + szDTO.getObiadPlanIl().intValue();
            }
                
            
            if ( szDTO.getPodwieczorekPlanIl()!= null )
            {
               listCell4.setLabel( szDTO.getPodwieczorekPlanIl().toString() );
               p = p + szDTO.getPodwieczorekPlanIl().intValue();
            }
                
            
            if ( szDTO.getKolacjaPlanIl() != null )
            {
                listCell5.setLabel( szDTO.getKolacjaPlanIl().toString() );
                k = k + szDTO.getKolacjaPlanIl().intValue();
            }
                
            
            if ( szDTO.getPosilekNocnyPlanIl()!= null )
            {
                listCell6.setLabel( szDTO.getPosilekNocnyPlanIl().toString() );
                pn = pn + szDTO.getPosilekNocnyPlanIl().intValue();
            }
                
            
            if ( szDTO.getSniadanieKorIl()!= null )
            {
                listCell7.setLabel( szDTO.getSniadanieKorIl().toString() );
                Ks = Ks + szDTO.getSniadanieKorIl().intValue();
            }
                
            
            if ( szDTO.getDrugieSniadanieKorIl() != null )
            {
                listCell8.setLabel( szDTO.getDrugieSniadanieKorIl().toString() );
                KdS = KdS + szDTO.getDrugieSniadanieKorIl().intValue();
            }
                
            
            if ( szDTO.getObiadKorIl()!= null )
            {
                listCell9.setLabel( szDTO.getObiadKorIl().toString() );
                Ko = Ko + szDTO.getObiadKorIl().intValue();
            }
                
            
            if ( szDTO.getPodwieczorekKorIl()!= null )
            {
                listCell10.setLabel( szDTO.getPodwieczorekKorIl().toString() );
                Kp = Kp + szDTO.getPodwieczorekKorIl().intValue();
            }
                
            
            if ( szDTO.getKolacjaKorIl() != null )
            {
                listCell11.setLabel( szDTO.getKolacjaKorIl().toString() );
                Kk = Kk + szDTO.getKolacjaKorIl().intValue();
            }
                
            
            if ( szDTO.getPosilekNocnyKorIl()!= null )
            {
                listCell12.setLabel( szDTO.getPosilekNocnyKorIl().toString() );
                Kpn = Kpn + szDTO.getPosilekNocnyKorIl().intValue();
            }
                
            
            listItem1.appendChild(listCell);
            listItem1.appendChild(listCell0);
            listItem1.appendChild(listCell1);
            listItem1.appendChild(listCell2);
            listItem1.appendChild(listCell3);
            listItem1.appendChild(listCell4);
            listItem1.appendChild(listCell5);
            listItem1.appendChild(listCell6);
            listItem1.appendChild(listCell7);
            listItem1.appendChild(listCell8);
            listItem1.appendChild(listCell9);
            listItem1.appendChild(listCell10);
            listItem1.appendChild(listCell11);
            listItem1.appendChild(listCell12);
            listBox.appendChild(listItem1);
            listBox.setVisible(true);

            
        }
        
        Auxhead ah = new Auxhead();
        Auxheader ah00 = new Auxheader("");
        ah00.setParent(ah);
        
        Auxheader ah01 = new Auxheader("Suma:");
        ah01.setAlign("right");
        ah01.setParent(ah);
        
        Auxheader ah1 = new Auxheader( String.valueOf(s) );
        ah1.setParent(ah);
        
        Auxheader ah2 = new Auxheader( String.valueOf(dS) );
        ah2.setParent(ah);
        
        Auxheader ah3 = new Auxheader( String.valueOf(o) );
        ah3.setParent(ah);
        
        Auxheader ah4 = new Auxheader( String.valueOf(p) );
        ah4.setParent(ah);
        
        Auxheader ah5 = new Auxheader( String.valueOf(k) );
        ah5.setParent(ah);
        
        Auxheader ah6 = new Auxheader( String.valueOf(pn) );
        ah6.setParent(ah);
        
        Auxheader ah7 = new Auxheader( String.valueOf(Ks) );
        ah7.setParent(ah);
        
        Auxheader ah8 = new Auxheader( String.valueOf(KdS) );
        ah8.setParent(ah);
        
        Auxheader ah9 = new Auxheader( String.valueOf(Ko) );
        ah9.setParent(ah);
        
        Auxheader ah10 = new Auxheader( String.valueOf(Kp) );
        ah10.setParent(ah);
        
        Auxheader ah11 = new Auxheader( String.valueOf(Kk) );
        ah11.setParent(ah);
        
        Auxheader ah12 = new Auxheader( String.valueOf(Kpn) );
        ah12.setParent(ah);
        
        listBox.appendChild(ah);
        
        
    }
    
    public void cc( Date naDzien, int kierKosztow ){
        
        
        
        //System.out.print(kierKosztow + naDzien);

        BindUtils.postNotifyChange(null, null, stanyZywionychNaDzien2, "*");
    }
    
    
}