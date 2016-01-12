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
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
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
    
    private List<KierunekKosztowVO> kierunkiKosztow = new ArrayList<KierunekKosztowVO>( serviceFacade.getKierunkiKosztowUzytkownika() );
    
    private KierunekKosztowVO selectedKierunekKosztow = new KierunekKosztowVO();

    private List<GrupaZywionychVO> grupyZywionych = new ArrayList<GrupaZywionychVO>();
    
    private String czyKorekta = "N";
    
    private String statusZamowienia = "STATUS PLANOWANIE";
        
    public List<StanZywionychNaDzienDTO> stanyZywionychNaDzien = serviceFacade.stanyZywionychNaDzien;
    
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
        stanyZywionychNaDzien.clear();
        
        stanyZywionychNaDzien  = serviceFacade.pobierzStanZywionychWdniuDlaGrupyZywionych(formatter.format( naDzien ),grupaZywionych);
        
        if ( stanyZywionychNaDzien.size() == 0 )
        {
            serviceFacade.uzupelnijZeramiStanWdniu(formatter.format( naDzien ));
            stanyZywionychNaDzien  = serviceFacade.pobierzStanZywionychWdniuDlaGrupyZywionych(formatter.format( naDzien ),grupaZywionych);
        }
        
        
        
    }
    
    
    @Command
    @NotifyChange("grupyZywionych")
    public void wybranoKierKosztow() {

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
    
    
    
    
    
}
