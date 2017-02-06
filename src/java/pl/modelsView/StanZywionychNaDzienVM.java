/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.modelsView;


import java.io.StringReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
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
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
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
    
    private List<KierunekKosztowVO> kierunkiKosztow;
    
    private KierunekKosztowVO selectedKierunekKosztow = new KierunekKosztowVO();

    private List<GrupaZywionychVO> grupyZywionych = new ArrayList<GrupaZywionychVO>();
    
    private String czyKorekta = "N";
    
    private String statusZamowienia = "STATUS PLANOWANIE";
        
    public List<StanZywionychNaDzienDTO> stanyZywionychNaDzien;
    
    public List<StanZywionychNaDzienDTO> stanyZywionychDoKopiowania;
    
    public List<StanZywionychNaDzienSumaDTO> stanyZywionychNaDzienSuma;
    
    public String width;
    
    private Number ilWierszy = 15;
    
    private Boolean godzDoS_readOnly = false;
    private Boolean godzDoIIS_readOnly = false;
    private Boolean godzDoO_readOnly = false;
    private Boolean godzDoP_readOnly = false;
    private Boolean godzDoK_readOnly = false;
    private Boolean godzDoPN_readOnly = false;
    
    @Wire
    Listbox listBoxSZ;
    
    @Wire
    Button buttScale;
      
    @Wire
    Label godzDoS;
    
    @Wire
    Label godzDoIIS;
    
    @Wire 
    Label godzDoO;
    
    @Wire 
    Label godzDoP;
    
    @Wire 
    Label godzDoK;
    
    @Wire 
    Label godzDoPN;

    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
        Selectors.wireComponents(view, this, false);
    }

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

    public Number getIlWierszy() {
        return ilWierszy;
    }

    public void setIlWierszy(Number ilWierszy) {
        this.ilWierszy = ilWierszy;
    }

    public Boolean getGodzDoS_readOnly() {
        return godzDoS_readOnly;
    }

    public void setGodzDoS_readOnly(Boolean godzDoS_readOnly) {
        this.godzDoS_readOnly = godzDoS_readOnly;
    }

    public Boolean getGodzDoIIS_readOnly() {
        return godzDoIIS_readOnly;
    }

    public void setGodzDoIIS_readOnly(Boolean godzDoIIS_readOnly) {
        this.godzDoIIS_readOnly = godzDoIIS_readOnly;
    }

    public Boolean getGodzDoO_readOnly() {
        return godzDoO_readOnly;
    }

    public void setGodzDoO_readOnly(Boolean godzDoO_readOnly) {
        this.godzDoO_readOnly = godzDoO_readOnly;
    }

    public Boolean getGodzDoP_readOnly() {
        return godzDoP_readOnly;
    }

    public void setGodzDoP_readOnly(Boolean godzDoP_readOnly) {
        this.godzDoP_readOnly = godzDoP_readOnly;
    }

    public Boolean getGodzDoK_readOnly() {
        return godzDoK_readOnly;
    }

    public void setGodzDoK_readOnly(Boolean godzDoK_readOnly) {
        this.godzDoK_readOnly = godzDoK_readOnly;
    }

    public Boolean getGodzDoPN_readOnly() {
        return godzDoPN_readOnly;
    }

    public void setGodzDoPN_readOnly(Boolean godzDoPN_readOnly) {
        this.godzDoPN_readOnly = godzDoPN_readOnly;
    }

    
    
    

    

    
    
    
    
    
  

    public List<StanZywionychNaDzienSumaDTO> getStanyZywionychNaDzienSuma() {
        return stanyZywionychNaDzienSuma;
    }

    public void setStanyZywionychNaDzienSuma(List<StanZywionychNaDzienSumaDTO> stanyZywionychNaDzienSuma) {
        this.stanyZywionychNaDzienSuma = stanyZywionychNaDzienSuma;
    }
    

    public StanZywionychNaDzienVM()
    {  
        width = "1283";
        kierunkiKosztow = new ArrayList<KierunekKosztowVO>( serviceFacade.getKierunkiKosztowUzytkownika() );
        
        if ( stanyZywionychNaDzien != null )
        {
            uzupelnijSumeStanowNaDzien();
        }

    }

    @Command
    @NotifyChange("stanyZywionychNaDzien")
    public void deleteAllMail() {
        Messagebox.show("StanZywionychNaDzienVM-deleteAllMail");
        stanyZywionychNaDzienSuma.clear();
    }
    
    @Command
    @NotifyChange("stanyZywionychNaDzien")
    public void pobInne(@BindingParam("naDzien") Date naDzien, @BindingParam("grupaZywionych") String grupaZywionych, @BindingParam("uwagi") String uwagi) {
      
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = new Date();
        
        if ( !dt1.format(naDzien).equals(dt1.format(d1))  )
        {
            godzDoS_readOnly = false;
            godzDoIIS_readOnly = false;
            godzDoO_readOnly = false;
            godzDoP_readOnly = false;
            godzDoK_readOnly = false;
            godzDoPN_readOnly = false;
            godzDoS.setValue("");
            godzDoIIS.setValue("");
            godzDoO.setValue("");
            godzDoP.setValue("");
            godzDoK.setValue("");
            godzDoPN.setValue("");
        }
        else
        {
            sprawdzGodziny( uwagi );
        }
            
        
        System.out.print(" Pobieram stany dla: " + grupaZywionych + " na dzien: " + naDzien);
        
        if ( grupaZywionych == null )
        {
            Messagebox.show("Brak wybranej Grupy Żywionych.");
            return;
        }
        
        
        stanyZywionychNaDzien = new ArrayList<StanZywionychNaDzienDTO>();
        
        stanyZywionychNaDzien  = serviceFacade.pobierzStanZywionychWdniuDlaGrupyZywionych(formatter.format( naDzien ),grupaZywionych);
        
        if ( stanyZywionychNaDzien.size() == 0 )
        {
            Messagebox.show("Brak wygenrowanych stanów na dzień: " + formatter.format( naDzien ) + "w Mapim.");
            //serviceFacade.uzupelnijZeramiStanWdniu(formatter.format( naDzien ));
            stanyZywionychNaDzien  = serviceFacade.pobierzStanZywionychWdniuDlaGrupyZywionych(formatter.format( naDzien ),grupaZywionych);
            return;
        }
        
        Clients.showNotification("Dane zostały pobrane","info",null, null,300);
        
        //serviceFacade.stanyZywionychNaDzien.clear();
        serviceFacade.naDzienRaport =   naDzien;
        serviceFacade.gzRaprot = grupaZywionych;
        serviceFacade.stanyZywionychNaDzien = stanyZywionychNaDzien;
        
        
        
    }
    
    
    @Command
    @NotifyChange("grupyZywionych")
    public void wybranoKierKosztow( @BindingParam("uwagi") String uwagi, @BindingParam("naDzien") Date naDzien ) {
        //System.out.println( selectedKierunekKosztow.getUwagi() );
        //serviceFacade.kkRaport = selectedKierunekKosztow;
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = new Date();

        grupyZywionych = new ArrayList<GrupaZywionychVO>( serviceFacade.getGrupaZywionych( selectedKierunekKosztow ) );
        
        if ( dt1.format(naDzien).equals(dt1.format(d1))  )
        {
            sprawdzGodziny( uwagi );
        }
        
        /*
        else
        {
            godzDoS_readOnly = false;
            godzDoIIS_readOnly = false;
            godzDoO_readOnly = false;
            godzDoP_readOnly = false;
            godzDoK_readOnly = false;
            godzDoPN_readOnly = false;
            godzDoS.setValue("");
            godzDoIIS.setValue("");
            godzDoO.setValue("");
            godzDoP.setValue("");
            godzDoK.setValue("");
            godzDoPN.setValue("");
        }*/
        
        
        
        
    }
    
    
    
    private void sprawdzGodziny( String uwagi )
    {
        Date d1 = new Date();
        System.out.println( "Uwagi:" + uwagi );
       
            if ( uwagi!= null )
            {
                SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 







                JsonReader jsonReader = Json.createReader(new StringReader( uwagi ));     //JSON - zamwianie godzin
                JsonObject object = jsonReader.readObject();
                jsonReader.close();

                JsonArray dane1 = (JsonArray) object.getJsonArray("dane");

                JsonObject itemDane = dane1.getJsonObject(0);

                JsonString jsonGodzZamDoS = itemDane.getJsonString("S");
                JsonString jsonGodzZamDoIIS = itemDane.getJsonString("IIS");
                JsonString jsonGodzZamDoO = itemDane.getJsonString("O");
                JsonString jsonGodzZamDoP = itemDane.getJsonString("P");
                JsonString jsonGodzZamDoK = itemDane.getJsonString("K");
                JsonString jsonGodzZamDoPN = itemDane.getJsonString("PN");

                godzDoS.setValue( jsonGodzZamDoS.toString() );
                godzDoIIS.setValue( jsonGodzZamDoIIS.toString() );
                godzDoO.setValue( jsonGodzZamDoO.toString() );
                godzDoP.setValue( jsonGodzZamDoP.toString() );
                godzDoK.setValue( jsonGodzZamDoK.toString() );
                godzDoPN.setValue( jsonGodzZamDoPN.toString() );

                String d2s = dt.format(d1);
                String d2IIs = dt.format(d1);
                String d2o = dt.format(d1);
                String d2p = dt.format(d1);
                String d2k = dt.format(d1);
                String d2pn = dt.format(d1);

                d2s = d2s.substring(0, 11) + jsonGodzZamDoS.toString().replace("\"","");
                d2IIs = d2IIs.substring(0, 11) + jsonGodzZamDoIIS.toString().replace("\"","");
                d2o = d2o.substring(0, 11) + jsonGodzZamDoO.toString().replace("\"","");
                d2p = d2p.substring(0, 11) + jsonGodzZamDoP.toString().replace("\"","");
                d2k = d2k.substring(0, 11) + jsonGodzZamDoK.toString().replace("\"","");
                d2pn = d2pn.substring(0, 11) + jsonGodzZamDoPN.toString().replace("\"","");

                Date ds2 = new Date();
                Date dIIs2 = new Date();
                Date do2 = new Date();
                Date dp2 = new Date();
                Date dk2 = new Date();
                Date dpn2 = new Date();

                try {
                    ds2 = dt.parse(d2s);
                    dIIs2 = dt.parse(d2IIs);
                    do2 = dt.parse(d2o);
                    dp2 = dt.parse(d2p);
                    dk2 = dt.parse(d2k);
                    dpn2 = dt.parse(d2pn);
                } catch (ParseException ex) {
                    Logger.getLogger(StanZywionychNaDzienVM.class.getName()).log(Level.SEVERE, null, ex);
                }

                if ( d1.after(ds2) && !jsonGodzZamDoS.toString().equals("\"0:00\"")  )
                {
                    godzDoS.setStyle("color:red;");
                    godzDoS_readOnly = true;
                }
                else 
                {
                    godzDoS.setStyle("color:black;");
                    godzDoS_readOnly = false;
                }

                if ( d1.after(dIIs2) && !jsonGodzZamDoIIS.toString().equals("\"0:00\"")  )
                {
                    godzDoIIS.setStyle("color:red;");
                    godzDoIIS_readOnly = true;
                }
                else 
                {
                    godzDoIIS.setStyle("color:black;");
                    godzDoIIS_readOnly = false;
                }

                if ( d1.after(do2)  )
                {
                    godzDoO.setStyle("color:red;");
                    godzDoO_readOnly = true;
                }
                else 
                {
                    godzDoO.setStyle("color:black;");
                    godzDoO_readOnly = false;
                }

                if ( d1.after(dp2)  )
                {
                    godzDoP.setStyle("color:red;");
                    godzDoP_readOnly = true;
                }
                else 
                {
                    godzDoP.setStyle("color:black;");
                    godzDoP_readOnly = false;
                }

                if ( d1.after(dk2)  )
                {
                    godzDoK.setStyle("color:red;");
                    godzDoK_readOnly = true;
                }
                else 
                {
                    godzDoK.setStyle("color:black;");
                    godzDoK_readOnly = false;
                }

                if ( d1.after(dpn2)  )
                {
                    godzDoPN.setStyle("color:red;");
                    godzDoPN_readOnly = true;
                }
                else 
                {
                    godzDoPN.setStyle("color:black;");
                    godzDoPN_readOnly = false;
                }

            }
        
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
       if ( stanyZywionychNaDzien != null )
       {
           
       
        
        stanyZywionychNaDzienSuma = new ArrayList<StanZywionychNaDzienSumaDTO>();
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
    
    
    @Command
    @NotifyChange("cmd1")
    public void cmd1() {
        
        System.out.println( buttScale.getImage() );
        
        if ( buttScale.getImage().equals("img/scale16x16down.png") )
        {
           buttScale.setImage("img/scale16x16.png");
           listBoxSZ.setRows(7);
           listBoxSZ.setWidth("975px"); 
        }
        else
        {
           buttScale.setImage("img/scale16x16down.png");
           listBoxSZ.setRows(15);
           listBoxSZ.setWidth("1283px");  
        }
            
    }
    
    public void zmienX( String w )
    {
        System.out.println(w + "x");
        width = w;
        System.out.println(width + "x");
        
    }
 
   
   

    }
