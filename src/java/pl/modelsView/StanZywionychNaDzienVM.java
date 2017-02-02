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
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import pl.models.GrupaZywionychVO;
import pl.models.KierunekKosztowVO;
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
    
    public static StanZywionychNaDzienVM getInstance() {
        if (instance == null) {
          instance = new StanZywionychNaDzienVM();
        }
        return instance;
    }
    

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    
    private Date dC;
    
    private String gzC;
    
    private List<KierunekKosztowVO> kierunkiKosztow = new ArrayList<KierunekKosztowVO>( serviceFacade.getKierunkiKosztowUzytkownika() );
    
    private KierunekKosztowVO selectedKierunekKosztow = new KierunekKosztowVO();

    private List<GrupaZywionychVO> grupyZywionych = new ArrayList<GrupaZywionychVO>();
    
    private String czyKorekta = "N";
    
    private String statusZamowienia = "STATUS PLANOWANIE";
        
    public List<StanZywionychNaDzienDTO> stanyZywionychNaDzien = serviceFacade.stanyZywionychNaDzien;
    
    public List<StanZywionychNaDzienDTO> stanyZywionychDoKopiowania;
    
    public List<StanZywionychNaDzienSumaDTO> stanyZywionychNaDzienSuma = serviceFacade.stanyZywionychNaDzienSuma;
    
     
    
 

    public List<StanZywionychNaDzienDTO> getStanyZywionychNaDzien() {
		return stanyZywionychNaDzien;
    }

    public void setStanyZywionychNaDzien(List<StanZywionychNaDzienDTO> stanyZywionychNaDzien) {
        this.stanyZywionychNaDzien = stanyZywionychNaDzien;
    }

    public List<GrupaZywionychVO> getGrupyZywionych() {
        return grupyZywionych;
    }

    public void setGrupyZywionych(List<GrupaZywionychVO> grupyZywionych) {
        this.grupyZywionych = grupyZywionych;
    }

    public List<KierunekKosztowVO> getKierunkiKosztow() {
        return kierunkiKosztow;
    }

    public void setKierunkiKosztow(List<KierunekKosztowVO> kierunkiKosztow) {
        this.kierunkiKosztow = kierunkiKosztow;
    }

    public KierunekKosztowVO getSelectedKierunekKosztow() {
        return selectedKierunekKosztow;
    }

    public void setSelectedKierunekKosztow(KierunekKosztowVO selectedKierunekKosztow) {
        this.selectedKierunekKosztow = selectedKierunekKosztow;
    }

    public String getCzyKorekta() {
        return czyKorekta;
    }

    public void setCzyKorekta(String czyKorekta) {
        this.czyKorekta = czyKorekta;
    }

    public String getStatusZamowienia() {
        return statusZamowienia;
    }

    public void setStatusZamowienia(String statusZamowienia) {
        this.statusZamowienia = statusZamowienia;
    }

    

    
    
    
    
    
  

    public List<StanZywionychNaDzienSumaDTO> getStanyZywionychNaDzienSuma() {
        return stanyZywionychNaDzienSuma;
    }

    public void setStanyZywionychNaDzienSuma(List<StanZywionychNaDzienSumaDTO> stanyZywionychNaDzienSuma) {
        this.stanyZywionychNaDzienSuma = stanyZywionychNaDzienSuma;
    }
    
    

    

   
    
    
  
    public StanZywionychNaDzienVM()
    {
        stanyZywionychNaDzien.clear();
        uzupelnijSumeStanowNaDzien();
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
        
        System.out.print(" Pobieram stany dla: " + grupaZywionych + " na dzien: " + naDzien);
        
        if ( grupaZywionych == null )
        {
            Messagebox.show("Brak wybranej Grupy Żywionych.");
            return;
        }
        
        
        stanyZywionychNaDzien.clear();
        
        stanyZywionychNaDzien  = serviceFacade.pobierzStanZywionychWdniuDlaGrupyZywionych(formatter.format( naDzien ),grupaZywionych);
        
        if ( stanyZywionychNaDzien.size() == 0 )
        {
            Messagebox.show("Brak wygenrowanych stanów na dzień: " + formatter.format( naDzien ) + "w Mapim.");
            //serviceFacade.uzupelnijZeramiStanWdniu(formatter.format( naDzien ));
            stanyZywionychNaDzien  = serviceFacade.pobierzStanZywionychWdniuDlaGrupyZywionych(formatter.format( naDzien ),grupaZywionych);
            return;
        }
        
        Clients.showNotification("Dane zostały pobrane","info",null, null,300);
        
        serviceFacade.stanyZywionychNaDzien.clear();
        serviceFacade.naDzienRaport =   naDzien;
        serviceFacade.gzRaprot = grupaZywionych;
        serviceFacade.stanyZywionychNaDzien = stanyZywionychNaDzien;
        
    }
    
    
    @Command
    @NotifyChange("grupyZywionych")
    public void wybranoKierKosztow() {
         System.out.println( selectedKierunekKosztow.getUwagi() );
        serviceFacade.kkRaport = selectedKierunekKosztow;
        grupyZywionych = new ArrayList<GrupaZywionychVO>( serviceFacade.getGrupaZywionych( selectedKierunekKosztow ) );
        
    }
    
    
    
    @Command
    @NotifyChange("stanyZywionychNaDzien")
    public void zapiszStanZyw() {
      //  Messagebox.show("StanZywionychNaDzienVM-pobInne"+naDzien+grupaZywionych);
        String ret = serviceFacade.zapiszStanZywionychWDniu2( stanyZywionychNaDzien, selectedKierunekKosztow, czyKorekta );
        
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
    public void onClickRowGrid(@BindingParam("value") String value)
    {
        Messagebox.show( value );
    }
    
    @Command
    @NotifyChange("stanyZywionychNaDzienSuma")
    public void uzupelnijSumeStanowNaDzien()
    {
        
        //Messagebox.show("Odswiezam !");
        //@Listen("onChange = gridStanZywionych2")
        
        BigDecimal sumSniadanie =  new BigDecimal(0); 
        BigDecimal sumDrugieSniadanie =  new BigDecimal(0); 
        BigDecimal sumObiad =  new BigDecimal(0);
        BigDecimal sumPodwieczorek =  new BigDecimal(0); 
        BigDecimal sumKolacja =  new BigDecimal(0);
        BigDecimal sumPosilekNocny =  new BigDecimal(0); 
        
        //korekta
        BigDecimal sumSniadanieKor       =  new BigDecimal(0); 
        BigDecimal sumDrugieSniadanieKor =  new BigDecimal(0); 
        BigDecimal sumObiadKor           =  new BigDecimal(0);
        BigDecimal sumPodwieczorekKor    =  new BigDecimal(0); 
        BigDecimal sumKolacjaKor         =  new BigDecimal(0);
        BigDecimal sumPosilekNocnyKor    =  new BigDecimal(0);
        
                
        if (stanyZywionychNaDzienSuma.isEmpty())
        {
          StanZywionychNaDzienSumaDTO s = new StanZywionychNaDzienSumaDTO(); 
          stanyZywionychNaDzienSuma.add(s);
        }
             
        
        for ( StanZywionychNaDzienDTO stan : stanyZywionychNaDzien )
        {
            if ( stan.getSniadaniePlanIl() != null )
                sumSniadanie = sumSniadanie.add(stan.getSniadaniePlanIl());
            
            if ( stan.getDrugieSniadaniePlanIl() != null )
                sumDrugieSniadanie = sumDrugieSniadanie.add(stan.getDrugieSniadaniePlanIl());
            
            if ( stan.getObiadPlanIl() != null )
                sumObiad = sumObiad.add(stan.getObiadPlanIl());
            
            if ( stan.getPodwieczorekPlanIl() != null )
                sumPodwieczorek = sumPodwieczorek.add(stan.getPodwieczorekPlanIl());
            
            if ( stan.getKolacjaPlanIl() != null )
                sumKolacja = sumKolacja.add(stan.getKolacjaPlanIl());
            
            if ( stan.getPosilekNocnyPlanIl() != null )
                sumPosilekNocny = sumPosilekNocny.add(stan.getPosilekNocnyPlanIl());
            
            
            //kor
            if ( stan.getSniadanieKorIl()!= null )
                sumSniadanieKor = sumSniadanieKor.add( stan.getSniadaniePlanIl().add( stan.getSniadanieKorIl() ) );
            
            if ( stan.getDrugieSniadanieKorIl()!= null )
                sumDrugieSniadanieKor = sumDrugieSniadanieKor.add( stan.getDrugieSniadaniePlanIl().add( stan.getDrugieSniadanieKorIl()) );
            
            if ( stan.getObiadKorIl()!= null )
                sumObiadKor = sumObiadKor.add( stan.getObiadPlanIl().add( stan.getObiadKorIl()) );
            
            if ( stan.getPodwieczorekKorIl()!= null )
                sumPodwieczorekKor = sumPodwieczorekKor.add( stan.getPodwieczorekPlanIl().add( stan.getPodwieczorekKorIl() ) );
            
            if ( stan.getKolacjaKorIl()!= null )
                sumKolacjaKor = sumKolacjaKor.add( stan.getKolacjaPlanIl().add( stan.getKolacjaKorIl())  );
            
            if ( stan.getPosilekNocnyKorIl()!= null )
                sumPosilekNocnyKor = sumPosilekNocnyKor.add( stan.getPosilekNocnyPlanIl().add( stan.getPosilekNocnyKorIl())  );
            
            
            
            
        }
        
        for ( StanZywionychNaDzienSumaDTO suma : stanyZywionychNaDzienSuma)
        {
            suma.setsPilSum( sumSniadanie ); 
            suma.setDsPilSum( sumDrugieSniadanie ); 
            suma.setoPilSum( sumObiad );
            suma.setPoPilSum( sumPodwieczorek );
            suma.setkPilSum( sumKolacja );
            suma.setPnPilSum( sumPosilekNocny );
            
            suma.setsK1ilSum( sumSniadanieKor );
            suma.setDsK1ilSum(sumDrugieSniadanieKor ); 
            suma.setoK1ilSum( sumObiadKor );
            suma.setPoK1ilSum( sumPodwieczorekKor );
            suma.setkK1ilSum( sumKolacjaKor );
            suma.setPnK1ilSum( sumPosilekNocnyKor );
        }    
    }
    
    
    @Command
    @NotifyChange("stanyZywionychNaDzienSuma")
    public void uzupelnijPozycjePoZmianieSniadania( @BindingParam("row") StanZywionychNaDzienDTO stanZyw )
    {
       System.out.println( stanZyw.getSniadaniePlanIl() );
        
       
            int INDEX = stanyZywionychNaDzien.indexOf(stanZyw);
            stanyZywionychNaDzien.get(INDEX).setObiadPlanIl(stanZyw.getSniadaniePlanIl());
            stanyZywionychNaDzien.get(INDEX).setKolacjaPlanIl(stanZyw.getSniadaniePlanIl());
            
            BindUtils.postNotifyChange(null, null, stanZyw, "obiadPlanIl");
            BindUtils.postNotifyChange(null, null, stanZyw, "kolacjaPlanIl");
            
           /* if ( stanyZywionychNaDzien.get(INDEX).getDrugieSniadaniePlanIl() != null )
            {
                stanyZywionychNaDzien.get(INDEX).setDrugieSniadaniePlanIl(stanZyw.getSniadaniePlanIl());
                BindUtils.postNotifyChange(null, null, stanZyw, "drugieSniadaniePlanIl");
            }
            
            if ( stanyZywionychNaDzien.get(INDEX).getPodwieczorekPlanIl()!= null )
            {
                stanyZywionychNaDzien.get(INDEX).setPodwieczorekPlanIl(stanZyw.getSniadaniePlanIl());
                BindUtils.postNotifyChange(null, null, stanZyw, "podwieczorekPlanIl");
            }
            
            
            if ( stanyZywionychNaDzien.get(INDEX).getPosilekNocnyPlanIl()!= null )
            {
                stanyZywionychNaDzien.get(INDEX).setPosilekNocnyPlanIl(stanZyw.getSniadaniePlanIl());
                BindUtils.postNotifyChange(null, null, stanZyw, "posilekNocnyPlanIl");
            }
            */
            
       
        
       /* 
        
         if  ( stanyZywionychNaDzien.contains(stanZyw) )
        {
            System.out.println("Account found");
        } else {
            System.out.println("Account not found");
        }
       
       
       */
        
        uzupelnijSumeStanowNaDzien();
    }
    
    
    @Command
    @NotifyChange("statusZamowienia")
    public void doCheckedKor(@BindingParam("checked") boolean korZaznaczenie) {
        if (korZaznaczenie){
           czyKorekta = "T"; 
           statusZamowienia = "STATUS KOREKTA - zapisuje tylko korektę";
        }
        else
        {
           czyKorekta = "N"; 
           statusZamowienia = "STATUS PLANOWANIE";
        }
       
       System.out.print("czyKorekta: " + czyKorekta);
    }
    
    
    @Command
    @NotifyChange("stanyZywionychNaDzienSuma")
    public void copyStanZywDlaDnia(@BindingParam("naDzien") Date naDzien,@BindingParam("naDzienCopy") Date naDzienCopy, @BindingParam("grupaZywionych") String grupaZywionych)
    {
        dC = naDzienCopy;
        gzC = grupaZywionych;
        
        
        
        EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            
            public void onEvent(ClickEvent event) throws Exception {
                if(Messagebox.Button.YES.equals(event.getButton())) {
                    
                    copyStanZywDlaDnia2();
                }
            }
        };
        
        Messagebox.show("Czy na pewno chcesz skopiować stany?", "Cancel Order", new Messagebox.Button[]{
                Messagebox.Button.YES, Messagebox.Button.NO }, Messagebox.QUESTION, clickListener);

        
    }
    
    
    
    private void copyStanZywDlaDnia2()
    {
        stanyZywionychDoKopiowania  = serviceFacade.pobierzStanZywionychWdniuDlaGrupyZywionych(formatter.format( dC ), gzC);
        
            for ( StanZywionychNaDzienDTO szCopy : stanyZywionychDoKopiowania )
            {

                for ( StanZywionychNaDzienDTO sz : stanyZywionychNaDzien )
                {
                    if ( sz.getIdGrupaZywionych().equals(szCopy.getIdGrupaZywionych())
                            && sz.getIdDieta().equals(szCopy.getIdDieta()) )
                    {
                       sz.setSniadaniePlanIl(szCopy.getSniadaniePlanIl());
                       sz.setObiadPlanIl(szCopy.getObiadPlanIl()); 
                       sz.setKolacjaPlanIl(szCopy.getKolacjaPlanIl());

                       BindUtils.postNotifyChange(null, null, sz, "sniadaniePlanIl");
                       BindUtils.postNotifyChange(null, null, sz, "obiadPlanIl");
                       BindUtils.postNotifyChange(null, null, sz, "kolacjaPlanIl");
                    }
                }

               /* int INDEX = stanyZywionychNaDzien.indexOf(szCopy);
                stanyZywionychNaDzien.get(INDEX).setObiadPlanIl(szCopy.getSniadaniePlanIl());
                stanyZywionychNaDzien.get(INDEX).setKolacjaPlanIl(szCopy.getSniadaniePlanIl());*/


            }

            uzupelnijSumeStanowNaDzien();
            BindUtils.postNotifyChange(null, null, stanyZywionychNaDzienSuma, "*");
    }
    
    
    
    
}
