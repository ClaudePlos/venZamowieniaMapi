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
import javax.servlet.http.HttpSession;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.Template;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import pl.models.GrupaZywionychVO;
import pl.models.KierunekKosztowVO;
import pl.models.KontrolaRodzajGodzDTO;
import pl.models.OperatorVO;
import pl.models.StanZywionychNaDzienDTO;
import pl.models.StanZywionychNaDzienSumaDTO;
import pl.models.reports.GzEventDTO;
import pl.services.UserCredential;
import pl.session.ServiceFacade;
import pl.views.zam.MenuController;

/**
 *
 * @author k.skowronski
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class StanZywionychNaDzienVM extends SelectorComposer<Component> {
    
    @EJB 
    ServiceFacade serviceFacade = ServiceFacade.getInstance();
    
    private EventQueue eventGZnaDzien;
    
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
    
    public OperatorVO user;
    
    
    
    public String width;
    private String sortType = "lp";
    private Number ilWierszy = 15;
    
    private Boolean godzDoPlan_readOnly = false;
    
    private Boolean godzDoPlanS_readOnly = false;
    private Boolean godzDoPlanIIS_readOnly = false;
    private Boolean godzDoPlanO_readOnly = false;
    private Boolean godzDoPlanP_readOnly = false;
    private Boolean godzDoPlanK_readOnly = false;
    private Boolean godzDoPlanPN_readOnly = false;
    
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
    Label labGodzOpis;
    
    @Wire
    Label godzDoPlan;
    
    @Wire
    Label godzDoPlanS;
    
    @Wire
    Label godzDoPlanIIS;
    
    @Wire 
    Label godzDoPlanO;
    
    @Wire 
    Label godzDoPlanP;
    
    @Wire 
    Label godzDoPlanK;
    
    @Wire 
    Label godzDoPlanPN;
      
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
    
    @Wire
    Grid grid01;
    
    @Wire
    Listheader labS;
    @Wire
    Listheader labS2;
    @Wire
    Listheader labO;
    @Wire
    Listheader labP;
    @Wire
    Listheader labK;
    @Wire
    Listheader labPN;
    @Wire
    Listheader labUW;
    @Wire
    Listheader labSK;
    @Wire
    Listheader labS2K;
    @Wire
    Listheader labOK;
    @Wire
    Listheader labPK;
    @Wire
    Listheader labKK;
    @Wire
    Listheader labPNK;

    

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

    public Boolean getGodzDoPlanIIS_readOnly() {
        return godzDoPlanIIS_readOnly;
    }

    public void setGodzDoPlanIIS_readOnly(Boolean godzDoPlanIIS_readOnly) {
        this.godzDoPlanIIS_readOnly = godzDoPlanIIS_readOnly;
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

    public Boolean getGodzDoPlan_readOnly() {
        return godzDoPlan_readOnly;
    }

    public void setGodzDoPlan_readOnly(Boolean godzDoPlan_readOnly) {
        this.godzDoPlan_readOnly = godzDoPlan_readOnly;
    }

    public Boolean getGodzDoPlanS_readOnly() {
        return godzDoPlanS_readOnly;
    }

    public void setGodzDoPlanS_readOnly(Boolean godzDoPlanS_readOnly) {
        this.godzDoPlanS_readOnly = godzDoPlanS_readOnly;
    }

    public Boolean getGodzDoPlanO_readOnly() {
        return godzDoPlanO_readOnly;
    }

    public void setGodzDoPlanO_readOnly(Boolean godzDoPlanO_readOnly) {
        this.godzDoPlanO_readOnly = godzDoPlanO_readOnly;
    }

    public Boolean getGodzDoPlanP_readOnly() {
        return godzDoPlanP_readOnly;
    }

    public void setGodzDoPlanP_readOnly(Boolean godzDoPlanP_readOnly) {
        this.godzDoPlanP_readOnly = godzDoPlanP_readOnly;
    }

    public Boolean getGodzDoPlanK_readOnly() {
        return godzDoPlanK_readOnly;
    }

    public void setGodzDoPlanK_readOnly(Boolean godzDoPlanK_readOnly) {
        this.godzDoPlanK_readOnly = godzDoPlanK_readOnly;
    }

    public Boolean getGodzDoPlanPN_readOnly() {
        return godzDoPlanPN_readOnly;
    }

    public void setGodzDoPlanPN_readOnly(Boolean godzDoPlanPN_readOnly) {
        this.godzDoPlanPN_readOnly = godzDoPlanPN_readOnly;
    }

    
    
    
    public List<StanZywionychNaDzienSumaDTO> getStanyZywionychNaDzienSuma() {
        return stanyZywionychNaDzienSuma;
    }

    public void setStanyZywionychNaDzienSuma(List<StanZywionychNaDzienSumaDTO> stanyZywionychNaDzienSuma) {
        this.stanyZywionychNaDzienSuma = stanyZywionychNaDzienSuma;
    }
    

    public StanZywionychNaDzienVM()
    { 
        HttpSession session = (HttpSession)(Executions.getCurrent()).getDesktop().getSession().getNativeSession();
        
        UserCredential u =  (UserCredential) session.getAttribute("userCredential");
        
        user = serviceFacade.getUserAndKK( u.getName() );
        
        width = "1283";
        kierunkiKosztow = user.getKierunkiKosztow();
        
        if ( stanyZywionychNaDzien != null )
        {
            uzupelnijSumeStanowNaDzien();
        }

    }
    

    @Command
    @NotifyChange("stanyZywionychNaDzien")
    public void deleteAllMail() {
        Messagebox.show("StanZywionychNaDzienVM-delMesseteAllMail");
        stanyZywionychNaDzienSuma.clear();
    }
    
    @Command
    @NotifyChange("stanyZywionychNaDzien")
    public void pobInne(@BindingParam("naDzien") Date naDzien, @BindingParam("grupaZywionych") String grupaZywionych, @BindingParam("uwagi") String uwagi) throws ParseException {
         
        sprawdzBlokadeGodzinWstawiania( uwagi, naDzien );
 
        System.out.print(" Pobieram stany dla: " + grupaZywionych + " na dzien: " + naDzien);
        
        if ( grupaZywionych == null )
        {
            Messagebox.show("Brak wybranej Grupy Żywionych.");
            return;
        }
        
        
        stanyZywionychNaDzien = new ArrayList<StanZywionychNaDzienDTO>();
        
        stanyZywionychNaDzien  = serviceFacade.pobierzStanZywionychWdniuDlaGrupyZywionych(formatter.format( naDzien ),grupaZywionych, sortType);
        
        if ( stanyZywionychNaDzien.size() == 0 )
        {
            Messagebox.show("Brak wygenrowanych stanów na dzień: " + formatter.format( naDzien ) + "w Mapim.");
            //serviceFacade.uzupelnijZeramiStanWdniu(formatter.format( naDzien ));
            stanyZywionychNaDzien  = serviceFacade.pobierzStanZywionychWdniuDlaGrupyZywionych(formatter.format( naDzien ),grupaZywionych, sortType);
            return;
        }
        
        Clients.showNotification("Dane zostały pobrane","info",null, null,300);
        
        //serviceFacade.stanyZywionychNaDzien.clear();
        //MenuController.getServiceSideBar().naDzienRaport =   naDzien;
        //MenuController.getServiceSideBar().gzRaprot = grupaZywionych;
        
        GzEventDTO g = new GzEventDTO();
        g.setGzRaprot(grupaZywionych );
        g.setNaDzienRaport(naDzien);
        g.setStanyZywionychNaDzien(stanyZywionychNaDzien);
        
        eventGZnaDzien = EventQueues.lookup("eventGrupaZywionych", EventQueues.DESKTOP, true);
        eventGZnaDzien.publish(new Event("onButtonClick", null, g ));
        
        
        //serviceFacade.stanyZywionychNaDzien = stanyZywionychNaDzien;
        
        
        
    }
    
    
    @Command
    @NotifyChange("grupyZywionych")
    public void wybranoKierKosztow( @BindingParam("uwagi") String uwagi, @BindingParam("naDzien") Date naDzien ) throws ParseException {
        //System.out.println( selectedKierunekKosztow.getUwagi() );
        //serviceFacade.kkRaport = selectedKierunekKosztow;
        

        grupyZywionych = new ArrayList<GrupaZywionychVO>( serviceFacade.getGrupaZywionych( selectedKierunekKosztow ) );
        
        sprawdzBlokadeGodzinWstawiania( uwagi, naDzien );
    }
    
    
    
    private void sprawdzBlokadeGodzinWstawiania( String uwagi, Date naDzien ) throws ParseException
    {
        
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = new Date();
        Date d1_tommorow = new Date();

        d1_tommorow.setTime(d1.getTime() + 1 * 24 * 60 * 60 * 1000);
        
        if ( uwagi!= null )
        {
            
            JsonReader jsonReader = Json.createReader(new StringReader( uwagi ));     //JSON - zamwianie godzin
            JsonObject object = jsonReader.readObject();
            jsonReader.close();

            JsonArray dane1 = (JsonArray) object.getJsonArray("dane");

            JsonObject itemDane = dane1.getJsonObject(0);
            
            String PODSTAWOWE = "PODSTAWOWE";
            String rodzaj =  PODSTAWOWE; // ROZWIĄZANIE 
            
            if (itemDane.getJsonString("Rodz") != null){
               rodzaj = itemDane.getJsonString("Rodz").toString();    
            }
            
            if ( rodzaj.equals(PODSTAWOWE) )
            {
                
                if ( dt1.format(naDzien).equals(dt1.format(d1)) ) // daty równe dziś i oznaczona na stronie, sprawdzamy do któreh godz można wprowadzać zamówienia
                {

                System.out.println( "Uwagi:" + uwagi );


                        labGodzOpis.setValue(" Godz. do wprowadzania KOREKTA(S/IIS/O/P/K/PN): ");

                        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 

                        JsonString jsonGodzZamDoS = itemDane.getJsonString("S");
                        JsonString jsonGodzZamDoIIS = itemDane.getJsonString("IIS");
                        JsonString jsonGodzZamDoO = itemDane.getJsonString("O");
                        JsonString jsonGodzZamDoP = itemDane.getJsonString("P");
                        JsonString jsonGodzZamDoK = itemDane.getJsonString("K");
                        JsonString jsonGodzZamDoPN = itemDane.getJsonString("PN");

                        godzDoPlan.setValue("");
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


                        godzDoPlan_readOnly = true; // nie mozna planowac na dzis, tylko na jutro
                        if ( d1.after(ds2) && !jsonGodzZamDoS.toString().equals("\"0:00\"")  ) {
                            godzDoS.setStyle("color:red;");
                            godzDoS_readOnly = true;
                        } else {
                            godzDoS.setStyle("color:black;");
                            godzDoS_readOnly = false;
                        }

                        if ( d1.after(dIIs2) && !jsonGodzZamDoIIS.toString().equals("\"0:00\"")  ) {
                            godzDoIIS.setStyle("color:red;");
                            godzDoIIS_readOnly = true;
                        } else {
                            godzDoIIS.setStyle("color:black;");
                            godzDoIIS_readOnly = false;
                        }

                        if ( d1.after(do2)  ) {
                            godzDoO.setStyle("color:red;");
                            godzDoO_readOnly = true;
                        } else {
                            godzDoO.setStyle("color:black;");
                            godzDoO_readOnly = false;
                        }

                        if ( d1.after(dp2)  ) {
                            godzDoP.setStyle("color:red;");
                            godzDoP_readOnly = true;
                        } else {
                            godzDoP.setStyle("color:black;");
                            godzDoP_readOnly = false;
                        }

                        if ( d1.after(dk2)  ) {
                            godzDoK.setStyle("color:red;");
                            godzDoK_readOnly = true;
                        } else {
                            godzDoK.setStyle("color:black;");
                            godzDoK_readOnly = false;
                        }

                        if ( d1.after(dpn2)  ) {
                            godzDoPN.setStyle("color:red;");
                            godzDoPN_readOnly = true;
                        } else {
                            godzDoPN.setStyle("color:black;");
                            godzDoPN_readOnly = false;
                        }


                } // end sprawdzania godzinowego
                else if ( d1.after( naDzien ) ) // jeżeli sprawdzają daty wcześniejsze niż dziś to blokujemy wprowadzanie
                {
                    labGodzOpis.setValue("");
                    godzDoPlan_readOnly = true;

                    godzDoPlanS_readOnly = true;
                    godzDoPlanIIS_readOnly = true;
                    godzDoPlanO_readOnly = true;
                    godzDoPlanP_readOnly = true;
                    godzDoPlanK_readOnly = true;
                    godzDoPlanPN_readOnly = true;

                    godzDoS_readOnly = true;
                    godzDoIIS_readOnly = true;
                    godzDoO_readOnly = true;
                    godzDoP_readOnly = true;
                    godzDoK_readOnly = true;
                    godzDoPN_readOnly = true;

                    godzDoPlan.setValue("");

                    godzDoPlanS.setValue("");
                    godzDoPlanIIS.setValue("");
                    godzDoPlanO.setValue("");
                    godzDoPlanP.setValue("");
                    godzDoPlanK.setValue("");
                    godzDoPlanPN.setValue("");

                    //korekty
                    godzDoS.setValue("");
                    godzDoIIS.setValue("");
                    godzDoO.setValue("");
                    godzDoP.setValue("");
                    godzDoK.setValue("");
                    godzDoPN.setValue("");
                }
                else if ( dt1.format(naDzien).equals(dt1.format(d1_tommorow)) ) // jezeli jutro to sprawdzam plan do ktorej moga
                {
                        labGodzOpis.setValue(" Godz. do wprowadzania PLAN: ");

                        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 


                        JsonString jsonGodzZamDoPlan = itemDane.getJsonString("Plan");

                        godzDoPlan.setValue( jsonGodzZamDoPlan.toString() );
                        godzDoS.setValue("");
                        godzDoIIS.setValue("");
                        godzDoO.setValue("");
                        godzDoP.setValue("");
                        godzDoK.setValue("");
                        godzDoPN.setValue("");

                        String d2plan = dt.format(d1);
                    
                        d2plan = d2plan.substring(0, 11) + jsonGodzZamDoPlan.toString().replace("\"","");

                        
                        Date dplan2 = new Date();
                    
                        try {
                                dplan2 = dt.parse(d2plan);
                        } catch (ParseException ex) {
                            Logger.getLogger(StanZywionychNaDzienVM.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        if ( d1.after(dplan2) && !jsonGodzZamDoPlan.toString().equals("\"0:00\"")  ) {
                            godzDoPlan.setStyle("color:red;");
                            godzDoPlan_readOnly = true;
                        } else {
                            godzDoPlan.setStyle("color:black;");
                            godzDoPlan_readOnly = false;
                        }

                        godzDoS_readOnly = false;
                        godzDoIIS_readOnly = false;
                        godzDoO_readOnly = false;
                        godzDoP_readOnly = false;
                        godzDoK_readOnly = false;
                        godzDoPN_readOnly = false;


                }
                else if ( d1_tommorow.before( naDzien ) ) //jezeli spr. daty później niz dzis i jutro to wprowadzają wszystkie posilki zamówienia 
                {
                    labGodzOpis.setValue("");
                    godzDoPlan_readOnly = false;
                    godzDoPlanS_readOnly = false;
                    godzDoPlanIIS_readOnly = false;
                    godzDoPlanO_readOnly = false;
                    godzDoPlanP_readOnly = false;
                    godzDoPlanK_readOnly = false;
                    godzDoPlanPN_readOnly = false;

                    godzDoS_readOnly = false;
                    godzDoIIS_readOnly = false;
                    godzDoO_readOnly = false;
                    godzDoP_readOnly = false;
                    godzDoK_readOnly = false;
                    godzDoPN_readOnly = false;

                    godzDoPlan.setValue("");
                    godzDoPlanS.setValue("");
                    godzDoPlanIIS.setValue("");
                    godzDoPlanO.setValue("");
                    godzDoPlanP.setValue("");
                    godzDoPlanK.setValue("");
                    godzDoPlanPN.setValue("");
                    //kor
                    godzDoS.setValue("");
                    godzDoIIS.setValue("");
                    godzDoO.setValue("");
                    godzDoP.setValue("");
                    godzDoK.setValue("");
                    godzDoPN.setValue("");
                }
                else
                {
                        labGodzOpis.setValue("");
                        godzDoPlan_readOnly = false;
                        godzDoPlanS_readOnly = false;
                        godzDoPlanIIS_readOnly = false;
                        godzDoPlanO_readOnly = false;
                        godzDoPlanP_readOnly = false;
                        godzDoPlanK_readOnly = false;
                        godzDoPlanPN_readOnly = false;

                        godzDoS_readOnly = false;
                        godzDoIIS_readOnly = false;
                        godzDoO_readOnly = false;
                        godzDoP_readOnly = false;
                        godzDoK_readOnly = false;
                        godzDoPN_readOnly = false;

                        godzDoPlan.setValue("");
                        godzDoPlanS.setValue("");
                        godzDoPlanIIS.setValue("");
                        godzDoPlanO.setValue("");
                        godzDoPlanP.setValue("");
                        godzDoPlanK.setValue("");
                        godzDoPlanPN.setValue("");

                        godzDoS.setValue("");
                        godzDoIIS.setValue("");
                        godzDoO.setValue("");
                        godzDoP.setValue("");
                        godzDoK.setValue("");
                        godzDoPN.setValue("");
                }
            } else {
//Rodzaj *********************************************************************************************************************************************************
                //Lepsze rozwiązanie 
                //Jeżeli 0:00 to nie ma spradzania 
                // ADD 2018-07-31
                System.out.print("INNE");
                
                
                SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
                labGodzOpis.setValue(" | Czas edycji: Plan/Korekta:");
                
                //PLAN*************
                JsonObject planS   = itemDane.getJsonObject("PlanS");
                JsonObject planIIS = itemDane.getJsonObject("PlanIIS");
                JsonObject planO   = itemDane.getJsonObject("PlanO");
                JsonObject planP   = itemDane.getJsonObject("PlanP");
                JsonObject planK   = itemDane.getJsonObject("PlanK");
                JsonObject planPN  = itemDane.getJsonObject("PlanPN");
                
                KontrolaRodzajGodzDTO godzZamDoPlanS = new KontrolaRodzajGodzDTO( planS.getString("T"), planS.getString("K") );
                KontrolaRodzajGodzDTO godzZamDoPlanIIS = new KontrolaRodzajGodzDTO( planIIS.getString("T"), planIIS.getString("K") );
                KontrolaRodzajGodzDTO godzZamDoPlanO = new KontrolaRodzajGodzDTO( planO.getString("T"), planO.getString("K") );
                KontrolaRodzajGodzDTO godzZamDoPlanP = new KontrolaRodzajGodzDTO( planP.getString("T"), planP.getString("K") );
                KontrolaRodzajGodzDTO godzZamDoPlanK = new KontrolaRodzajGodzDTO( planK.getString("T"), planK.getString("K") );
                KontrolaRodzajGodzDTO godzZamDoPlanPN = new KontrolaRodzajGodzDTO( planPN.getString("T"), planPN.getString("K") );
 
                
                
                // D -- dzis J -- jutro
                if ( dt1.format(naDzien).equals(dt1.format(d1)) ){ // jeżeli dziś
                    
                    godzDoPlanS.setValue( " S(" + godzZamDoPlanS.getRodzaj().replace("J", "W") + "):" + godzZamDoPlanS.getCzas() );
                    godzDoPlanIIS.setValue( " IIS(" + godzZamDoPlanIIS.getRodzaj().replace("J", "W") + "):" + godzZamDoPlanIIS.getCzas() );
                    godzDoPlanO.setValue( " O(" + godzZamDoPlanO.getRodzaj().replace("J", "W") + "):" + godzZamDoPlanO.getCzas() );
                    godzDoPlanP.setValue( " P(" + godzZamDoPlanP.getRodzaj().replace("J", "W") + "):" + godzZamDoPlanP.getCzas() );
                    godzDoPlanK.setValue( " K(" + godzZamDoPlanK.getRodzaj().replace("J", "W") + "):" + godzZamDoPlanK.getCzas() );
                    godzDoPlanPN.setValue( " PN(" + godzZamDoPlanPN.getRodzaj().replace("J", "W") + "):" + godzZamDoPlanPN.getCzas() );
 
                    // jeśli przekroczenie czasu lub ma być planowany tylko jutrzejszy dzień  to czerwone inaczej czarny 
                    if ( (d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoPlanS.getCzas()) ) &&  godzZamDoPlanS.getRodzaj().equals("D") && !godzZamDoPlanS.toString().equals("0:00")) || godzZamDoPlanS.getRodzaj().equals("J") ) {
                        godzDoPlanS.setStyle("color:red;");
                        godzDoPlanS_readOnly = true;
                    } else {
                        godzDoPlanS.setStyle("color:black;");
                        godzDoPlanS_readOnly = false;
                    }
                    
                    if ( (d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoPlanIIS.getCzas()) ) &&  godzZamDoPlanIIS.getRodzaj().equals("D") && !godzZamDoPlanIIS.toString().equals("0:00")) || godzZamDoPlanIIS.getRodzaj().equals("J") ) {
                        godzDoPlanIIS.setStyle("color:red;");
                        godzDoPlanIIS_readOnly = true;
                    } else {
                        godzDoPlanIIS.setStyle("color:black;");
                        godzDoPlanIIS_readOnly = false;
                    } 
                    
                    if ( (d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoPlanO.getCzas()) ) &&  godzZamDoPlanO.getRodzaj().equals("D") && !godzZamDoPlanO.toString().equals("0:00")) || godzZamDoPlanO.getRodzaj().equals("J") ) {
                        godzDoPlanO.setStyle("color:red;");
                        godzDoPlanO_readOnly = true;
                    } else {
                        godzDoPlanO.setStyle("color:black;");
                        godzDoPlanO_readOnly = false;
                    }  
                    
                    if ( (d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoPlanP.getCzas()) ) &&  godzZamDoPlanP.getRodzaj().equals("D") && !godzZamDoPlanP.toString().equals("0:00")) || godzZamDoPlanP.getRodzaj().equals("J") ) {
                        godzDoPlanP.setStyle("color:red;");
                        godzDoPlanP_readOnly = true;
                    } else {
                        godzDoPlanP.setStyle("color:black;");
                        godzDoPlanP_readOnly = false;
                    } 
                    
                    if ( (d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoPlanK.getCzas()) ) &&  godzZamDoPlanK.getRodzaj().equals("D") && !godzZamDoPlanK.toString().equals("0:00")) || godzZamDoPlanK.getRodzaj().equals("J") ) {
                        godzDoPlanK.setStyle("color:red;");
                        godzDoPlanK_readOnly = true;
                    } else {
                        godzDoPlanK.setStyle("color:black;");
                        godzDoPlanK_readOnly = false;
                    } 
                    
                    if ( (d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoPlanPN.getCzas()) ) &&  godzZamDoPlanPN.getRodzaj().equals("D") && !godzZamDoPlanPN.toString().equals("0:00")) || godzZamDoPlanPN.getRodzaj().equals("J") ) {
                        godzDoPlanPN.setStyle("color:red;");
                        godzDoPlanPN_readOnly = true;
                    } else {
                        godzDoPlanPN.setStyle("color:black;");
                        godzDoPlanPN_readOnly = false;
                    } 
    
                } else if ( dt1.format(naDzien).equals(dt1.format(d1_tommorow)) ) { // jezeli jutro to sprawdzam plan do ktorej moga
                    
                    godzDoPlanS.setValue( " S(" + godzZamDoPlanS.getRodzaj().replace("D", "J").replace("J", "D") + "):" + godzZamDoPlanS.getCzas() );
                    godzDoPlanIIS.setValue( " IIS(" + godzZamDoPlanIIS.getRodzaj().replace("D", "J").replace("J", "D") + "):" + godzZamDoPlanIIS.getCzas() );
                    godzDoPlanO.setValue( " O(" + godzZamDoPlanO.getRodzaj().replace("D", "J").replace("J", "D") + "):" + godzZamDoPlanO.getCzas() );
                    godzDoPlanP.setValue( " P(" + godzZamDoPlanP.getRodzaj().replace("D", "J").replace("J", "D") + "):" + godzZamDoPlanP.getCzas() );
                    godzDoPlanK.setValue( " K(" + godzZamDoPlanK.getRodzaj().replace("D", "J").replace("J", "D") + "):" + godzZamDoPlanK.getCzas() );
                    godzDoPlanPN.setValue( " PN(" + godzZamDoPlanPN.getRodzaj().replace("D", "J").replace("J", "D") + "):" + godzZamDoPlanPN.getCzas() );
                    
                    if ( d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoPlanS.getCzas()) ) &&  godzZamDoPlanS.getRodzaj().equals("J") && !godzZamDoPlanS.toString().equals("0:00")){
                        godzDoPlanS.setStyle("color:red;");
                        godzDoPlanS_readOnly = true;
                    } else if ( godzZamDoPlanS.getRodzaj().equals("D") ){
                        godzDoPlanS.setValue("");
                        godzDoPlanS_readOnly = false;
                    } else {
                        godzDoPlanS.setStyle("color:black;");
                        godzDoPlanS_readOnly = false;
                    }
                    
                    if ( d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoPlanIIS.getCzas()) ) &&  godzZamDoPlanIIS.getRodzaj().equals("J") && !godzZamDoPlanIIS.toString().equals("0:00")){
                        godzDoPlanIIS.setStyle("color:red;");
                        godzDoPlanIIS_readOnly = true;
                    } else if ( godzZamDoPlanIIS.getRodzaj().equals("D") ){
                        godzDoPlanIIS.setValue("");
                        godzDoPlanIIS_readOnly = false;
                    } else {
                        godzDoPlanIIS.setStyle("color:black;");
                        godzDoPlanIIS_readOnly = false;
                    } 
                    
                    if ( d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoPlanO.getCzas()) ) &&  godzZamDoPlanO.getRodzaj().equals("J") && !godzZamDoPlanO.toString().equals("0:00")){
                        godzDoPlanO.setStyle("color:red;");
                        godzDoPlanO_readOnly = true;
                    } else if ( godzZamDoPlanO.getRodzaj().equals("D") ){
                        godzDoPlanO.setValue("");
                        godzDoPlanO_readOnly = false;
                    } else {
                        godzDoPlanO.setStyle("color:black;");
                        godzDoPlanO_readOnly = false;
                    }  
                    
                    if ( d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoPlanP.getCzas()) ) &&  godzZamDoPlanP.getRodzaj().equals("J") && !godzZamDoPlanP.toString().equals("0:00")){
                        godzDoPlanP.setStyle("color:red;");
                        godzDoPlanP_readOnly = true;
                    } else if ( godzZamDoPlanP.getRodzaj().equals("D") ){
                        godzDoPlanP.setValue("");
                        godzDoPlanP_readOnly = false;
                    } else {
                        godzDoPlanP.setStyle("color:black;");
                        godzDoPlanP_readOnly = false;
                    } 
                    
                    if ( d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoPlanK.getCzas()) ) &&  godzZamDoPlanK.getRodzaj().equals("J") && !godzZamDoPlanK.toString().equals("0:00")){
                        godzDoPlanK.setStyle("color:red;");
                        godzDoPlanK_readOnly = true;
                    } else if ( godzZamDoPlanK.getRodzaj().equals("D") ){
                        godzDoPlanK.setValue("");
                        godzDoPlanK_readOnly = false;
                    } else {
                        godzDoPlanK.setStyle("color:black;");
                        godzDoPlanK_readOnly = false;
                    } 
                    
                    if ( d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoPlanPN.getCzas()) ) &&  godzZamDoPlanPN.getRodzaj().equals("J") && !godzZamDoPlanPN.toString().equals("0:00")){
                        godzDoPlanPN.setStyle("color:red;");
                        godzDoPlanPN_readOnly = true;
                    } else if ( godzZamDoPlanPN.getRodzaj().equals("D") ){
                        godzDoPlanPN.setValue("");
                        godzDoPlanPN_readOnly = false;
                    } else {
                        godzDoPlanPN.setStyle("color:black;");
                        godzDoPlanPN_readOnly = false;
                    } 
                    
                } else if ( d1.after( naDzien ) ) // jeżeli sprawdzają daty wcześniejsze niż dziś to blokujemy wprowadzanie
                {   //EDYCJA
                    labGodzOpis.setValue("");
                    godzDoPlan_readOnly = true;
                    godzDoPlanS_readOnly = true;
                    godzDoPlanIIS_readOnly = true;
                    godzDoPlanO_readOnly = true;
                    godzDoPlanP_readOnly = true;
                    godzDoPlanK_readOnly = true;
                    godzDoPlanPN_readOnly = true;
                    //korekty
                    godzDoS_readOnly = true;
                    godzDoIIS_readOnly = true;
                    godzDoO_readOnly = true;
                    godzDoP_readOnly = true;
                    godzDoK_readOnly = true;
                    godzDoPN_readOnly = true;
                    //WARTOSCI
                    godzDoPlan.setValue("");
                    godzDoPlanS.setValue("");
                    godzDoPlanIIS.setValue("");
                    godzDoPlanO.setValue("");
                    godzDoPlanP.setValue("");
                    godzDoPlanK.setValue("");
                    godzDoPlanPN.setValue("");
                    //korekty
                    godzDoS.setValue("");
                    godzDoIIS.setValue("");
                    godzDoO.setValue("");
                    godzDoP.setValue("");
                    godzDoK.setValue("");
                    godzDoPN.setValue("");
                } else if ( d1_tommorow.before( naDzien ) ) //jezeli spr. daty później niz dzis i jutro to wprowadzają wszystkie posilki zamówienia 
                {   //EDYCJA
                    labGodzOpis.setValue("");
                    godzDoPlan_readOnly = false;
                    godzDoPlanS_readOnly = false;
                    godzDoPlanIIS_readOnly = false;
                    godzDoPlanO_readOnly = false;
                    godzDoPlanP_readOnly = false;
                    godzDoPlanK_readOnly = false;
                    godzDoPlanPN_readOnly = false;
                    //korekta
                    godzDoS_readOnly = false;
                    godzDoIIS_readOnly = false;
                    godzDoO_readOnly = false;
                    godzDoP_readOnly = false;
                    godzDoK_readOnly = false;
                    godzDoPN_readOnly = false;
                    //WARTOSCI
                    godzDoPlan.setValue("");
                    godzDoPlanS.setValue("");
                    godzDoPlanIIS.setValue("");
                    godzDoPlanO.setValue("");
                    godzDoPlanP.setValue("");
                    godzDoPlanK.setValue("");
                    godzDoPlanPN.setValue("");
                    //kor
                    godzDoS.setValue("");
                    godzDoIIS.setValue("");
                    godzDoO.setValue("");
                    godzDoP.setValue("");
                    godzDoK.setValue("");
                    godzDoPN.setValue("");
                }
                
                
               
                
                //KOREKTA*************
                JsonObject korS   = itemDane.getJsonObject("S");
                JsonObject korIIS = itemDane.getJsonObject("IIS");
                JsonObject korO   = itemDane.getJsonObject("O");
                JsonObject korP   = itemDane.getJsonObject("P");
                JsonObject korK   = itemDane.getJsonObject("K");
                JsonObject korPN  = itemDane.getJsonObject("PN");
                
                KontrolaRodzajGodzDTO godzZamDoKorS = new KontrolaRodzajGodzDTO( korS.getString("T"), korS.getString("K") );
                KontrolaRodzajGodzDTO godzZamDoKorIIS = new KontrolaRodzajGodzDTO( korIIS.getString("T"), korS.getString("K") );
                KontrolaRodzajGodzDTO godzZamDoKorO = new KontrolaRodzajGodzDTO( korO.getString("T"), korS.getString("K") );
                KontrolaRodzajGodzDTO godzZamDoKorP = new KontrolaRodzajGodzDTO( korP.getString("T"), korS.getString("K") );
                KontrolaRodzajGodzDTO godzZamDoKorK = new KontrolaRodzajGodzDTO( korK.getString("T"), korS.getString("K") );
                KontrolaRodzajGodzDTO godzZamDoKorPN = new KontrolaRodzajGodzDTO( korPN.getString("T"), korS.getString("K") );
 
                // D -- dzis J -- jutro
                if ( dt1.format(naDzien).equals(dt1.format(d1)) ){ // jeżeli dziś
                    
                    godzDoS.setValue( " // S(" + godzZamDoKorS.getRodzaj().replace("J", "W") + "):" + godzZamDoKorS.getCzas() );
                    godzDoIIS.setValue( " IIS(" + godzZamDoKorIIS.getRodzaj().replace("J", "W") + "):" + godzZamDoKorIIS.getCzas() );
                    godzDoO.setValue( " O(" + godzZamDoKorO.getRodzaj().replace("J", "W") + "):" + godzZamDoKorO.getCzas() );
                    godzDoP.setValue( " P(" + godzZamDoKorP.getRodzaj().replace("J", "W") + "):" + godzZamDoKorP.getCzas() );
                    godzDoK.setValue( " K(" + godzZamDoKorK.getRodzaj().replace("J", "W") + "):" + godzZamDoKorK.getCzas() );
                    godzDoPN.setValue( " PN(" + godzZamDoKorPN.getRodzaj().replace("J", "W") + "):" + godzZamDoKorPN.getCzas() );
 
                    // jeśli przekroczenie czasu lub ma być planowany tylko jutrzejszy dzień  to czerwone inaczej czarny 
                    if ( (d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoKorS.getCzas()) ) &&  godzZamDoKorS.getRodzaj().equals("D") && !godzZamDoKorS.toString().equals("0:00")) || godzZamDoKorS.getRodzaj().equals("J") ) {
                        godzDoS.setStyle("color:red;");
                        godzDoS_readOnly = true;
                    } else {
                        godzDoS.setStyle("color:black;");
                        godzDoS_readOnly = false;
                    }
                    
                    if ( (d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoKorIIS.getCzas()) ) &&  godzZamDoKorIIS.getRodzaj().equals("D") && !godzZamDoKorIIS.toString().equals("0:00")) || godzZamDoKorIIS.getRodzaj().equals("J") ) {
                        godzDoIIS.setStyle("color:red;");
                        godzDoIIS_readOnly = true;
                    } else {
                        godzDoIIS.setStyle("color:black;");
                        godzDoIIS_readOnly = false;
                    } 
                    
                    if ( (d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoKorO.getCzas()) ) &&  godzZamDoKorO.getRodzaj().equals("D") && !godzZamDoKorO.toString().equals("0:00")) || godzZamDoKorO.getRodzaj().equals("J") ) {
                        godzDoO.setStyle("color:red;");
                        godzDoO_readOnly = true;
                    } else {
                        godzDoO.setStyle("color:black;");
                        godzDoO_readOnly = false;
                    }  
                    
                    if ( (d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoKorP.getCzas()) ) &&  godzZamDoKorP.getRodzaj().equals("D") && !godzZamDoKorP.toString().equals("0:00")) || godzZamDoKorP.getRodzaj().equals("J") ) {
                        godzDoP.setStyle("color:red;");
                        godzDoP_readOnly = true;
                    } else {
                        godzDoP.setStyle("color:black;");
                        godzDoP_readOnly = false;
                    } 
                    
                    if ( (d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoKorK.getCzas()) ) &&  godzZamDoKorK.getRodzaj().equals("D") && !godzZamDoKorK.toString().equals("0:00")) || godzZamDoKorK.getRodzaj().equals("J") ) {
                        godzDoK.setStyle("color:red;");
                        godzDoK_readOnly = true;
                    } else {
                        godzDoK.setStyle("color:black;");
                        godzDoK_readOnly = false;
                    } 
                    
                    if ( (d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoKorPN.getCzas()) ) &&  godzZamDoKorPN.getRodzaj().equals("D") && !godzZamDoKorPN.toString().equals("0:00")) || godzZamDoKorPN.getRodzaj().equals("J") ) {
                        godzDoPN.setStyle("color:red;");
                        godzDoPN_readOnly = true;
                    } else {
                        godzDoPN.setStyle("color:black;");
                        godzDoPN_readOnly = false;
                    } 
    
                } else if ( dt1.format(naDzien).equals(dt1.format(d1_tommorow)) ) { // jezeli jutro to sprawdzam plan do ktorej moga
                    
                    godzDoS.setValue( " // S(" + godzZamDoKorS.getRodzaj().replace("D", "J").replace("J", "D") + "):" + godzZamDoKorS.getCzas() );
                    godzDoIIS.setValue( " IIS(" + godzZamDoKorIIS.getRodzaj().replace("D", "J").replace("J", "D") + "):" + godzZamDoKorIIS.getCzas() );
                    godzDoO.setValue( " O(" + godzZamDoKorO.getRodzaj().replace("D", "J").replace("J", "D") + "):" + godzZamDoKorO.getCzas() );
                    godzDoP.setValue( " P(" + godzZamDoKorP.getRodzaj().replace("D", "J").replace("J", "D") + "):" + godzZamDoKorP.getCzas() );
                    godzDoK.setValue( " K(" + godzZamDoKorK.getRodzaj().replace("D", "J").replace("J", "D") + "):" + godzZamDoKorK.getCzas() );
                    godzDoPN.setValue( " PN(" + godzZamDoKorPN.getRodzaj().replace("D", "J").replace("J", "D") + "):" + godzZamDoKorPN.getCzas() );
                    
                    if ( d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoKorS.getCzas()) ) &&  godzZamDoKorS.getRodzaj().equals("J") && !godzZamDoKorS.toString().equals("0:00")){
                        godzDoS.setStyle("color:red;");
                        godzDoS_readOnly = true;
                    } else if ( godzZamDoKorS.getRodzaj().equals("D") ){
                        godzDoS.setValue("");
                        godzDoS_readOnly = false;
                    } else {
                        godzDoS.setStyle("color:blue;");
                        godzDoS_readOnly = false;
                    }
                    
                    if ( d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoKorIIS.getCzas()) ) &&  godzZamDoKorIIS.getRodzaj().equals("J") && !godzZamDoKorIIS.toString().equals("0:00")){
                        godzDoIIS.setStyle("color:red;");
                        godzDoIIS_readOnly = true;
                    } else if ( godzZamDoKorIIS.getRodzaj().equals("D") ){
                        godzDoIIS.setValue("");
                        godzDoIIS_readOnly = false;
                    } else {
                        godzDoIIS.setStyle("color:blue;");
                        godzDoIIS_readOnly = false;
                    } 
                    
                    if ( d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoKorO.getCzas()) ) &&  godzZamDoKorO.getRodzaj().equals("J") && !godzZamDoKorO.toString().equals("0:00")){
                        godzDoO.setStyle("color:red;");
                        godzDoO_readOnly = true;
                    } else if ( godzZamDoKorO.getRodzaj().equals("D") ){
                        godzDoO.setValue("");
                        godzDoO_readOnly = false;
                    } else {
                        godzDoO.setStyle("color:blue;");
                        godzDoO_readOnly = false;
                    }  
                    
                    if ( d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoKorP.getCzas()) ) &&  godzZamDoKorP.getRodzaj().equals("J") && !godzZamDoKorP.toString().equals("0:00")){
                        godzDoP.setStyle("color:red;");
                        godzDoP_readOnly = true;
                    } else if ( godzZamDoKorP.getRodzaj().equals("D") ){
                        godzDoP.setValue("");
                        godzDoP_readOnly = false;
                    } else {
                        godzDoP.setStyle("color:blue;");
                        godzDoP_readOnly = false;
                    } 
                    
                    if ( d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoKorK.getCzas()) ) &&  godzZamDoKorK.getRodzaj().equals("J") && !godzZamDoKorK.toString().equals("0:00")){
                        godzDoK.setStyle("color:red;");
                        godzDoK_readOnly = true;
                    } else if ( godzZamDoKorK.getRodzaj().equals("D") ){
                        godzDoK.setValue("");
                        godzDoK_readOnly = false;
                    } else {
                        godzDoK.setStyle("color:blue;");
                        godzDoK_readOnly = false;
                    } 
                    
                    if ( d1.after( dt.parse( dt.format(d1).substring(0, 11) + godzZamDoKorPN.getCzas()) ) &&  godzZamDoKorPN.getRodzaj().equals("J") && !godzZamDoKorPN.toString().equals("0:00")){
                        godzDoPN.setStyle("color:red;");
                        godzDoPN_readOnly = true;
                    } else if ( godzZamDoKorPN.getRodzaj().equals("D") ){
                        godzDoPN.setValue("");
                        godzDoPN_readOnly = false;
                    } else {
                        godzDoPN.setStyle("color:blue;");
                        godzDoPN_readOnly = false;
                    } 
                    
                } else if ( d1.after( naDzien ) ) // jeżeli sprawdzają daty wcześniejsze niż dziś to blokujemy wprowadzanie
                {   //EDYCJA
                    labGodzOpis.setValue("");
                    godzDoPlan_readOnly = true;
                    godzDoPlanS_readOnly = true;
                    godzDoPlanIIS_readOnly = true;
                    godzDoPlanO_readOnly = true;
                    godzDoPlanP_readOnly = true;
                    godzDoPlanK_readOnly = true;
                    godzDoPlanPN_readOnly = true;
                    //korekty
                    godzDoS_readOnly = true;
                    godzDoIIS_readOnly = true;
                    godzDoO_readOnly = true;
                    godzDoP_readOnly = true;
                    godzDoK_readOnly = true;
                    godzDoPN_readOnly = true;
                    //WARTOSCI
                    godzDoPlan.setValue("");
                    godzDoPlanS.setValue("");
                    godzDoPlanIIS.setValue("");
                    godzDoPlanO.setValue("");
                    godzDoPlanP.setValue("");
                    godzDoPlanK.setValue("");
                    godzDoPlanPN.setValue("");
                    //korekty
                    godzDoS.setValue("");
                    godzDoIIS.setValue("");
                    godzDoO.setValue("");
                    godzDoP.setValue("");
                    godzDoK.setValue("");
                    godzDoPN.setValue("");
                } else if ( d1_tommorow.before( naDzien ) ) //jezeli spr. daty później niz dzis i jutro to wprowadzają wszystkie posilki zamówienia 
                {   //EDYCJA
                    labGodzOpis.setValue("");
                    godzDoPlan_readOnly = false;
                    godzDoPlanS_readOnly = false;
                    godzDoPlanIIS_readOnly = false;
                    godzDoPlanO_readOnly = false;
                    godzDoPlanP_readOnly = false;
                    godzDoPlanK_readOnly = false;
                    godzDoPlanPN_readOnly = false;
                    //korekta
                    godzDoS_readOnly = false;
                    godzDoIIS_readOnly = false;
                    godzDoO_readOnly = false;
                    godzDoP_readOnly = false;
                    godzDoK_readOnly = false;
                    godzDoPN_readOnly = false;
                    //WARTOSCI
                    godzDoPlan.setValue("");
                    godzDoPlanS.setValue("");
                    godzDoPlanIIS.setValue("");
                    godzDoPlanO.setValue("");
                    godzDoPlanP.setValue("");
                    godzDoPlanK.setValue("");
                    godzDoPlanPN.setValue("");
                    //kor
                    godzDoS.setValue("");
                    godzDoIIS.setValue("");
                    godzDoO.setValue("");
                    godzDoP.setValue("");
                    godzDoK.setValue("");
                    godzDoPN.setValue("");
                }
                
                
            }
        }
            
        
            
        
        

    }
    
    
    
    @Command
    @NotifyChange("stanyZywionychNaDzien")
    public void zapiszStanZyw() {
      //  Messagebox.show("StanZywionychNaDzienVM-pobInne"+naDzien+grupaZywionych);
        String ret = serviceFacade.zapiszStanZywionychWDniu2( stanyZywionychNaDzien, selectedKierunekKosztow, czyKorekta, user );
        
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
        stanyZywionychDoKopiowania  = serviceFacade.pobierzStanZywionychWdniuDlaGrupyZywionych(formatter.format( dC ), gzC, "lp");
        
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
           
       
           grid01.setWidth("975px");
           //cell01.setWidth("500px");
           
         
        
           labS.setWidth("60px"); 
           labS2.setWidth("60px"); 
           labO.setWidth("60px");
           labP.setWidth("60px");
           labK.setWidth("60px");
           labPN.setWidth("60px");
           labUW.setWidth("60px");
           labSK.setWidth("60px");
           labS2K.setWidth("60px");
           labOK.setWidth("60px");
           labPK.setWidth("60px");
           labKK.setWidth("60px");
           labPNK.setWidth("60px");
        }
        else
        {
           buttScale.setImage("img/scale16x16down.png");
           listBoxSZ.setRows(15);
           listBoxSZ.setWidth("1283px"); 
           
           grid01.setWidth("1283px");
       
           labS.setWidth("83px");
           labS2.setWidth("83px");
           labO.setWidth("83px");
           labP.setWidth("83px");
           labK.setWidth("83px");
           labPN.setWidth("83px");
           labUW.setWidth("83px");
           labSK.setWidth("83px");
           labS2K.setWidth("83px");
           labOK.setWidth("83px");
           labPK.setWidth("83px");
           labKK.setWidth("83px");
           labPNK.setWidth("83px");
        }
            
    }
    
    public void zmienX( String w )
    {
        System.out.println(w + "x");
        width = w;
        System.out.println(width + "x");
        
    }
 
    /**
     *
     * @param naDzien
     * @param naDzienCopy
     * @param grupaZywionych
     */
    @Command
    @NotifyChange("stanyZywionychNaDzienSuma")
    public void copyStanZywDlaDniaAll(@BindingParam("naDzien") Date naDzien,@BindingParam("naDzienCopy") Date naDzienCopy, @BindingParam("grupaZywionych") String grupaZywionych)
    {
        dC = naDzienCopy;
        gzC = grupaZywionych;
        
        
        
        EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            
            public void onEvent(ClickEvent event) throws Exception {
                if(Messagebox.Button.YES.equals(event.getButton())) {
                    
                    copyStanZywDlaDniaAll();
                }
            }
        };
        
        Messagebox.show("Czy na pewno chcesz skopiować stany?", "Cancel Order", new Messagebox.Button[]{
                Messagebox.Button.YES, Messagebox.Button.NO }, Messagebox.QUESTION, clickListener);

        
    }
    

    private void copyStanZywDlaDniaAll()
    {
        stanyZywionychDoKopiowania  = serviceFacade.pobierzStanZywionychWdniuDlaGrupyZywionych(formatter.format( dC ), gzC, "lp");
        
            for ( StanZywionychNaDzienDTO szCopy : stanyZywionychDoKopiowania )
            {

                for ( StanZywionychNaDzienDTO sz : stanyZywionychNaDzien )
                {
                    if ( sz.getIdGrupaZywionych().equals(szCopy.getIdGrupaZywionych())
                            && sz.getIdDieta().equals(szCopy.getIdDieta()) )
                    {
                       sz.setSniadaniePlanIl(szCopy.getSniadaniePlanIl());
                       sz.setDrugieSniadaniePlanIl(szCopy.getDrugieSniadaniePlanIl());
                       sz.setObiadPlanIl(szCopy.getObiadPlanIl()); 
                       sz.setPodwieczorekPlanIl(szCopy.getPodwieczorekPlanIl());
                       sz.setKolacjaPlanIl(szCopy.getKolacjaPlanIl());
                       sz.setPosilekNocnyPlanIl(szCopy.getPosilekNocnyPlanIl());
                       sz.setSzUwagi(szCopy.getSzUwagi());
                       
                       //bindowanie odniesienie do pliku main.zul 241 linijka
                       BindUtils.postNotifyChange(null, null, sz, "sniadaniePlanIl");
                       BindUtils.postNotifyChange(null, null, sz, "drugieSniadaniePlanIl");
                       BindUtils.postNotifyChange(null, null, sz, "obiadPlanIl");
                       BindUtils.postNotifyChange(null, null, sz, "podwieczorekPlanIl");
                       BindUtils.postNotifyChange(null, null, sz, "kolacjaPlanIl");
                       BindUtils.postNotifyChange(null, null, sz, "posilekNocnyPlanIl");
                       BindUtils.postNotifyChange(null, null, sz, "szUwagi");
                       
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
    @NotifyChange("statusZamowienia")
    public void doCheckedSort(@BindingParam("checked") boolean sortChange) {
        
        if (sortChange){
            sortType = "dieta_nazwa";
        }
        else
        {
            sortType = "lp";
        }
        
    }
   

    }
