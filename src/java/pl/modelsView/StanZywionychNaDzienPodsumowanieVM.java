/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.modelsView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Auxhead;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import pl.models.KierunekKosztowVO;
import pl.models.StanZywionychNaDzienDTO;
import pl.reports.IlzywWgOddPodDiety;
import pl.session.ServiceFacade;

/**
 *
 * @author k.skowronski
 */
public class StanZywionychNaDzienPodsumowanieVM  {
   
     
    @EJB 
    ServiceFacade serviceFacade = ServiceFacade.getInstance();
    
    public List<StanZywionychNaDzienDTO> stanyZywionychNaDzien2;

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
        listHeader0.setLabel("DIETA");
        listHeader0.setWidth("30%");
        
        Listheader listHeader1 = new Listheader();
        listHeader1.setLabel("S");
        listHeader1.setWidth("10%");
        
        Listheader listHeader2 = new Listheader();
        listHeader2.setLabel("IIS");
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
        listHeader7.setLabel("K/S");
        listHeader7.setStyle("color: red;");
        listHeader7.setWidth("10%");
        
        Listheader listHeader8 = new Listheader();
        listHeader8.setLabel("K/IIS");
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
        
        Listheader listHeader13 = new Listheader();
        listHeader13.setLabel("UWAGI");
        listHeader13.setWidth("15%");
        
        
        
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
        listHead.appendChild(listHeader13);
        listBox.appendChild(listHead);

       
        	
        window.appendChild(listBox);
        
        
        
        
        //stanyZywionychNaDzien2.clear();
        
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
            Listcell listCell13 = new Listcell();
            
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
            
            if ( szDTO.getSzUwagi()!= null )
            {
                listCell13.setLabel( szDTO.getSzUwagi() );
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
            listItem1.appendChild(listCell13);
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
        
        
        
        
        Button buttPDF = new Button();
        buttPDF.setLabel("Drukuj PDF");
        window.appendChild(buttPDF);
        
        
        buttPDF.addEventListener("onClick", new EventListener() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                
                    zapiszPDF( naDzien, kierKosztow );  
            }   
        });
        
        
    }
    
    private void zapiszPDF( Date naDzien, int kierKosztow  ) throws IOException, Exception
    {
        SimpleDateFormat dtf1 = new SimpleDateFormat("yyyy-MM-dd");
        KierunekKosztowVO kk = serviceFacade.getKierunekKosztow(kierKosztow);
        
         System.out.print("Tworze pdf podsumowanie KK");
          File f = new File("Podsumowanie " 
                  + kk.getKierunekKosztowNazwa() + " "
                  + dtf1.format(naDzien)
                  + ".pdf");
          
          OutputStream file = new FileOutputStream(f); //
            // OutputStream file = new FileOutputStream(new File("//Users//Claude//Desktop//PDF_Java4s.pdf"));
	          Document document = new Document(PageSize.A4.rotate());
	          PdfWriter.getInstance(document, file);
                  
                  Filedownload.save(f, "application/pdf");
         
          PdfPTable table=new PdfPTable( 15 ); // number of column
          
          table.setWidths(new int[]{40,200,50,50,50,50,50,50,50,50,50,50,50,50,100});
				     
          
           table.setTotalWidth(790);
           table.setLockedWidth(true);
           
           //BaseFont.CP1250
                    BaseFont bf = BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED ); // na polskie zniaki

                    //Font fontbold = FontFactory.getFont("Times-Roman", 12, Font.BOLD);

                    Font myFont_Naglowek = new Font(bf, 10, Font.BOLD); //rozmiar czcionki
                    Font myFont_Tresc = new Font(bf, 8);  //rozmiar czcionki
                    Font myFont = new Font(bf, 8);           //rozmiar czcionki


                    Font regular = new Font(Font.FontFamily.HELVETICA, 8);
                    Font bold = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
                    
                    
                    PdfPCell cell0 = new PdfPCell (new Paragraph ("Podsumowanie Kierunku Kosztów: " + kk.getKierunekKosztowNazwa(), myFont_Naglowek));
                    
                    cell0.setColspan( 15 ); // connect column to one 
                    cell0.setHorizontalAlignment (Element.ALIGN_CENTER);
                    cell0.setPadding (10.0f);
                    cell0.setBackgroundColor (new BaseColor (140, 221, 8));	
                    table.addCell(cell0); 
                    
                    
                    
                    PdfPCell cell = new PdfPCell (new Paragraph ("LP", bold));  

                                      cell.setColspan( 1 ); // connect column to one 
                                      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      cell.setPadding (10.0f);
                                      //cell.setBackgroundColor (new BaseColor (140, 221, 8));
                                     

                    table.addCell(cell); 
                    
                    
                    PdfPCell cel2 = new PdfPCell (new Paragraph ("DIETA", bold));  
                    
				      cel2.setColspan( 1 ); // connect column to one 
				      cel2.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cel2.setPadding (10.0f);
				      //cel2.setBackgroundColor (new BaseColor (140, 221, 8));
                                      
                    table.addCell(cel2);
                    
                    
                    PdfPCell cel3 = new PdfPCell (new Paragraph ("S", bold));  
                    
				      cel3.setColspan( 1 ); // connect column to one 
				      cel3.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cel3.setPadding (10.0f);
				      //cel3.setBackgroundColor (new BaseColor (140, 221, 8));
                                      
                    table.addCell(cel3);
                    
                    
                    PdfPCell cel4 = new PdfPCell (new Paragraph ("IIS", bold));  
                    
				      cel4.setColspan( 1 ); // connect column to one 
				      cel4.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cel4.setPadding (10.0f);
				      //cel4.setBackgroundColor (new BaseColor (140, 221, 8));
                                      
                    table.addCell(cel4);
                    
                    
                    PdfPCell cel5 = new PdfPCell (new Paragraph ("O", bold));  
                    
				      cel5.setColspan( 1 ); // connect column to one 
				      cel5.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cel5.setPadding (10.0f);
				      //cel5.setBackgroundColor (new BaseColor (140, 221, 8));
                                      
                    table.addCell(cel5);
                    
                    
                    PdfPCell cel6 = new PdfPCell (new Paragraph ("P", bold));  
                    
				      cel6.setColspan( 1 ); // connect column to one 
				      cel6.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cel6.setPadding (10.0f);
				      //cel6.setBackgroundColor (new BaseColor (140, 221, 8));
                                      
                    table.addCell(cel6);
                    
                    
                    PdfPCell cel7 = new PdfPCell (new Paragraph ("K", bold));  
                    
				      cel7.setColspan( 1 ); // connect column to one 
				      cel7.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cel7.setPadding (10.0f);
				      //cel7.setBackgroundColor (new BaseColor (140, 221, 8));
                                      
                    table.addCell(cel7);
                    
                    
                    PdfPCell cel8 = new PdfPCell (new Paragraph ("PN", bold));  
                    
				      cel8.setColspan( 1 ); // connect column to one 
				      cel8.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cel8.setPadding (10.0f);
				      //cel8.setBackgroundColor (new BaseColor (140, 221, 8));
                                      
                    table.addCell(cel8);
                    
                    
                    ////KOREKTY////
                    
                    PdfPCell cel9 = new PdfPCell (new Paragraph ("K/S", bold));  
                    
				      cel9.setColspan( 1 ); // connect column to one 
				      cel9.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cel9.setPadding (10.0f);
				      //cel9.setBackgroundColor (new BaseColor (140, 221, 8));
                                      
                    table.addCell(cel9);
                    
                    
                    PdfPCell cel10 = new PdfPCell (new Paragraph ("K/IIS", bold));  
                    
				      cel10.setColspan( 1 ); // connect column to one 
				      cel10.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cel10.setPadding (10.0f);
				      //cel10.setBackgroundColor (new BaseColor (140, 221, 8));
                                      
                    table.addCell(cel10);
                    
                    
                    PdfPCell cel11 = new PdfPCell (new Paragraph ("K/O", bold));  
                    
				      cel11.setColspan( 1 ); // connect column to one 
				      cel11.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cel11.setPadding (10.0f);
				      //cel11.setBackgroundColor (new BaseColor (140, 221, 8));
                                      
                    table.addCell(cel11);
                    
                    
                    PdfPCell cel12 = new PdfPCell (new Paragraph ("K/P", bold));  
                    
				      cel12.setColspan( 1 ); // connect column to one 
				      cel12.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cel12.setPadding (10.0f);
				      //cel12.setBackgroundColor (new BaseColor (140, 221, 8));
                                      
                    table.addCell(cel12);
                    
                    
                    PdfPCell cel13 = new PdfPCell (new Paragraph ("K/K", bold));  
                    
				      cel13.setColspan( 1 ); // connect column to one 
				      cel13.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cel13.setPadding (10.0f);
				      //cel13.setBackgroundColor (new BaseColor (140, 221, 8));
                                      
                    table.addCell(cel13);
                    
                    
                    PdfPCell cel14 = new PdfPCell (new Paragraph ("K/PN", bold));  
                    
				      cel14.setColspan( 1 ); // connect column to one 
				      cel14.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cel14.setPadding (10.0f);
				      //cel14.setBackgroundColor (new BaseColor (140, 221, 8));
                                      
                    table.addCell(cel14);
                    
                    
                    PdfPCell cel15 = new PdfPCell (new Paragraph ("UWAGI", bold));  
                    
				      cel15.setColspan( 1 ); // connect column to one 
				      cel15.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cel15.setPadding (10.0f);
				      //cel14.setBackgroundColor (new BaseColor (140, 221, 8));
                                      
                    table.addCell(cel15);
                    
                    
                    
                    
         ////ZLICZANIE DANYCH DO PLANU////
         
         //deklaracje do sum//
         BigDecimal sumaS = BigDecimal.ZERO;
         BigDecimal sumaS2 = BigDecimal.ZERO;
         BigDecimal sumaO = BigDecimal.ZERO;
         BigDecimal sumaP = BigDecimal.ZERO;
         BigDecimal sumaK = BigDecimal.ZERO;
         BigDecimal sumaPN = BigDecimal.ZERO;
         
         BigDecimal sumaKorS = BigDecimal.ZERO;
         BigDecimal sumaKorS2 = BigDecimal.ZERO;
         BigDecimal sumaKorO = BigDecimal.ZERO;
         BigDecimal sumaKorP = BigDecimal.ZERO;
         BigDecimal sumaKorK = BigDecimal.ZERO;
         BigDecimal sumaKorPN = BigDecimal.ZERO;
         
         
         
         
         
         
         for ( StanZywionychNaDzienDTO szDTO : stanyZywionychNaDzien2)
        {
                PdfPCell cellDane01 = new PdfPCell (new Paragraph (szDTO.getLp().toString(), myFont_Tresc));

                                          cellDane01.setColspan( 1 ); // connect column to one 
                                          cellDane01.setHorizontalAlignment (Element.ALIGN_CENTER);
                                          cellDane01.setPadding (10.0f);
                                          //cellDane01.setBackgroundColor (new BaseColor (140, 221, 8));	

                        table.addCell(cellDane01); 
                    
                    
                PdfPCell cellDane02 = new PdfPCell (new Paragraph (szDTO.getDietaNazwa(), myFont_Tresc));

                                             cellDane02.setColspan( 1 ); // connect column to one 
                                             cellDane02.setHorizontalAlignment (Element.ALIGN_CENTER);
                                             cellDane02.setPadding (10.0f);
                                             //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                           table.addCell(cellDane02);

                       
                String SNPlanIl="";           
                    if(szDTO.getSniadaniePlanIl() != null )
                    {
                        SNPlanIl = szDTO.getSniadaniePlanIl().toString();
                        sumaS = sumaS.add(szDTO.getSniadaniePlanIl());
                    }
                PdfPCell cellDane03 = new PdfPCell (new Paragraph (SNPlanIl, myFont_Tresc));

                                         cellDane03.setColspan( 1 ); // connect column to one 
                                         cellDane03.setHorizontalAlignment (Element.ALIGN_CENTER);
                                         cellDane03.setPadding (10.0f);
                                         //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                       table.addCell(cellDane03); 
                      
                       
                String IISNPlanIl="";           
                    if(szDTO.getDrugieSniadaniePlanIl() != null )
                    {
                        IISNPlanIl = szDTO.getDrugieSniadaniePlanIl().toString();
                        sumaS2 = sumaS2.add(szDTO.getDrugieSniadaniePlanIl());
                    }           
                PdfPCell cellDane04 = new PdfPCell (new Paragraph (IISNPlanIl, myFont_Tresc));

                                             cellDane04.setColspan( 1 ); // connect column to one 
                                             cellDane04.setHorizontalAlignment (Element.ALIGN_CENTER);
                                             cellDane04.setPadding (10.0f);
                                             //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                           table.addCell(cellDane04); 
               
                       
                String OPlanIl="";           
                    if(szDTO.getObiadPlanIl() != null )
                    {
                        OPlanIl = szDTO.getObiadPlanIl().toString();
                        sumaO = sumaO.add(szDTO.getObiadPlanIl());
                    }           
                PdfPCell cellDane05 = new PdfPCell (new Paragraph (OPlanIl, myFont_Tresc));

                                             cellDane05.setColspan( 1 ); // connect column to one 
                                             cellDane05.setHorizontalAlignment (Element.ALIGN_CENTER);
                                             cellDane05.setPadding (10.0f);
                                             //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                           table.addCell(cellDane05); 
              
                           
                String PPlanIl="";           
                    if(szDTO.getPodwieczorekPlanIl() != null )
                    {
                        PPlanIl = szDTO.getPodwieczorekPlanIl().toString();
                        sumaP = sumaP.add(szDTO.getPodwieczorekPlanIl());
                    }        
                PdfPCell cellDane06 = new PdfPCell (new Paragraph (PPlanIl, myFont_Tresc));

                                             cellDane06.setColspan( 1 ); // connect column to one 
                                             cellDane06.setHorizontalAlignment (Element.ALIGN_CENTER);
                                             cellDane06.setPadding (10.0f);
                                             //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                           table.addCell(cellDane06); 
               
                           
                String KPlanIl="";           
                    if(szDTO.getKolacjaPlanIl() != null )
                    {
                        KPlanIl = szDTO.getKolacjaPlanIl().toString();
                        sumaK = sumaK.add(szDTO.getKolacjaPlanIl());
                    }       
                PdfPCell cellDane07 = new PdfPCell (new Paragraph (KPlanIl, myFont_Tresc));

                                             cellDane07.setColspan( 1 ); // connect column to one 
                                             cellDane07.setHorizontalAlignment (Element.ALIGN_CENTER);
                                             cellDane07.setPadding (10.0f);
                                             //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                           table.addCell(cellDane07); 
              
                           
                String PNPlanIl="";           
                    if(szDTO.getPosilekNocnyPlanIl() != null )
                    {
                        PNPlanIl = szDTO.getPosilekNocnyPlanIl().toString();
                        sumaPN = sumaPN.add(szDTO.getPosilekNocnyPlanIl());
                    }       
                PdfPCell cellDane08 = new PdfPCell (new Paragraph (PNPlanIl, myFont_Tresc));

                                             cellDane08.setColspan( 1 ); // connect column to one 
                                             cellDane08.setHorizontalAlignment (Element.ALIGN_CENTER);
                                             cellDane08.setPadding (10.0f);
                                             //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                           table.addCell(cellDane08); 

               
        
         ////ZLICZANIE DANYCH DO KOREKT////                       
         
                      
                String KorSNPlanIl="";           
                    if(szDTO.getSniadanieKorIl() != null )
                    {
                        KorSNPlanIl = szDTO.getSniadanieKorIl().toString();
                        sumaKorS = sumaKorS.add(szDTO.getSniadanieKorIl());
                    }
                PdfPCell cellDane09 = new PdfPCell (new Paragraph (KorSNPlanIl, myFont_Tresc));

                                         cellDane09.setColspan( 1 ); // connect column to one 
                                         cellDane09.setHorizontalAlignment (Element.ALIGN_CENTER);
                                         cellDane09.setPadding (10.0f);
                                         //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                       table.addCell(cellDane09); 
                      
                       
                String KorIISNPlanIl="";           
                    if(szDTO.getDrugieSniadanieKorIl() != null )
                    {
                        KorIISNPlanIl = szDTO.getDrugieSniadanieKorIl().toString();
                        sumaKorS2 = sumaKorS2.add(szDTO.getDrugieSniadanieKorIl());
                    }           
                PdfPCell cellDane10 = new PdfPCell (new Paragraph (KorIISNPlanIl, myFont_Tresc));

                                             cellDane10.setColspan( 1 ); // connect column to one 
                                             cellDane10.setHorizontalAlignment (Element.ALIGN_CENTER);
                                             cellDane10.setPadding (10.0f);
                                             //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                           table.addCell(cellDane10); 
               
                       
                String KorOPlanIl="";           
                    if(szDTO.getObiadKorIl() != null )
                    {
                        KorOPlanIl = szDTO.getObiadKorIl().toString();
                        sumaKorO = sumaKorO.add(szDTO.getObiadKorIl());
                    }           
                PdfPCell cellDane11 = new PdfPCell (new Paragraph (KorOPlanIl, myFont_Tresc));

                                             cellDane11.setColspan( 1 ); // connect column to one 
                                             cellDane11.setHorizontalAlignment (Element.ALIGN_CENTER);
                                             cellDane11.setPadding (10.0f);
                                             //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                           table.addCell(cellDane11); 
              
                           
                String KorPPlanIl="";           
                    if(szDTO.getPodwieczorekKorIl() != null )
                    {
                        KorPPlanIl = szDTO.getPodwieczorekKorIl().toString();
                        sumaKorP = sumaKorP.add(szDTO.getPodwieczorekKorIl());
                    }        
                PdfPCell cellDane12 = new PdfPCell (new Paragraph (KorPPlanIl, myFont_Tresc));

                                             cellDane12.setColspan( 1 ); // connect column to one 
                                             cellDane12.setHorizontalAlignment (Element.ALIGN_CENTER);
                                             cellDane12.setPadding (10.0f);
                                             //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                           table.addCell(cellDane12); 
               
                           
                String KorKPlanIl="";           
                    if(szDTO.getKolacjaKorIl() != null )
                    {
                        KorKPlanIl = szDTO.getKolacjaKorIl().toString();
                        sumaKorK = sumaKorK.add(szDTO.getKolacjaKorIl());
                    }       
                PdfPCell cellDane13 = new PdfPCell (new Paragraph (KorKPlanIl, myFont_Tresc));

                                             cellDane13.setColspan( 1 ); // connect column to one 
                                             cellDane13.setHorizontalAlignment (Element.ALIGN_CENTER);
                                             cellDane13.setPadding (10.0f);
                                             //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                           table.addCell(cellDane13); 
              
                           
                String KorPNPlanIl="";           
                    if(szDTO.getPosilekNocnyKorIl() != null )
                    {
                        KorPNPlanIl = szDTO.getPosilekNocnyKorIl().toString();
                        sumaKorPN = sumaKorPN.add(szDTO.getPosilekNocnyKorIl());
                    }       
                PdfPCell cellDane14 = new PdfPCell (new Paragraph (KorPNPlanIl, myFont_Tresc));

                                             cellDane14.setColspan( 1 ); // connect column to one 
                                             cellDane14.setHorizontalAlignment (Element.ALIGN_CENTER);
                                             cellDane14.setPadding (10.0f);
                                             //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                           table.addCell(cellDane14); 
                           
                           
                PdfPCell cellDane15 = new PdfPCell (new Paragraph (szDTO.getSzUwagi() , myFont_Tresc));

                                             cellDane15.setColspan( 1 ); // connect column to one 
                                             cellDane15.setHorizontalAlignment (Element.ALIGN_CENTER);
                                             cellDane15.setPadding (10.0f);
                                             //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                           table.addCell(cellDane15);           

          
                       
        }     
         
        ////SUMY////
                
                PdfPCell cellSum00 = new PdfPCell (new Paragraph ("SUMA:", bold));

                                             cellSum00.setColspan( 2 ); // connect column to one 
                                             cellSum00.setHorizontalAlignment (Element.ALIGN_CENTER);
                                             cellSum00.setPadding (10.0f);
                                             //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                           table.addCell(cellSum00); 
                           
                           
                PdfPCell cellSum01 = new PdfPCell (new Paragraph (sumaS.toString(), bold));

                                      cellSum01.setColspan( 1 ); // connect column to one 
                                      cellSum01.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      cellSum01.setPadding (10.0f);
                                      //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                    table.addCell(cellSum01);       
                                            
                    
                PdfPCell cellSum02 = new PdfPCell (new Paragraph (sumaS2.toString(), bold));

                                      cellSum02.setColspan( 1 ); // connect column to one 
                                      cellSum02.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      cellSum02.setPadding (10.0f);
                                      //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                    table.addCell(cellSum02);          
                    
                                              
                PdfPCell cellSum03 = new PdfPCell (new Paragraph (sumaO.toString(), bold));

                                      cellSum03.setColspan( 1 ); // connect column to one 
                                      cellSum03.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      cellSum03.setPadding (10.0f);
                                      //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                    table.addCell(cellSum03);          
                    
                                              
                PdfPCell cellSum04 = new PdfPCell (new Paragraph (sumaP.toString(), bold));

                                      cellSum04.setColspan( 1 ); // connect column to one 
                                      cellSum04.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      cellSum04.setPadding (10.0f);
                                      //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                    table.addCell(cellSum04);          
                    
                                              
                PdfPCell cellSum05 = new PdfPCell (new Paragraph (sumaK.toString(), bold));

                                      cellSum05.setColspan( 1 ); // connect column to one 
                                      cellSum05.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      cellSum05.setPadding (10.0f);
                                      //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                    table.addCell(cellSum05);          
                    
                                              
                PdfPCell cellSum06 = new PdfPCell (new Paragraph (sumaPN.toString(), bold));

                                      cellSum06.setColspan( 1 ); // connect column to one 
                                      cellSum06.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      cellSum06.setPadding (10.0f);
                                      //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                    table.addCell(cellSum06);          
                    
                                              
                PdfPCell cellSum07 = new PdfPCell (new Paragraph (sumaKorS.toString(), bold));

                                      cellSum07.setColspan( 1 ); // connect column to one 
                                      cellSum07.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      cellSum07.setPadding (10.0f);
                                      //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                    table.addCell(cellSum07);          
                    
                                              
                PdfPCell cellSum08 = new PdfPCell (new Paragraph (sumaKorS2.toString(), bold));

                                      cellSum08.setColspan( 1 ); // connect column to one 
                                      cellSum08.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      cellSum08.setPadding (10.0f);
                                      //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                    table.addCell(cellSum08);          
                    
                                              
                PdfPCell cellSum09 = new PdfPCell (new Paragraph (sumaKorO.toString(), bold));

                                      cellSum09.setColspan( 1 ); // connect column to one 
                                      cellSum09.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      cellSum09.setPadding (10.0f);
                                      //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                    table.addCell(cellSum09);          
                    
                                              
                PdfPCell cellSum10 = new PdfPCell (new Paragraph (sumaKorP.toString(), bold));

                                      cellSum10.setColspan( 1 ); // connect column to one 
                                      cellSum10.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      cellSum10.setPadding (10.0f);
                                      //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                    table.addCell(cellSum10);          
                    
                                              
                PdfPCell cellSum11 = new PdfPCell (new Paragraph (sumaKorK.toString(), bold));

                                      cellSum11.setColspan( 1 ); // connect column to one 
                                      cellSum11.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      cellSum11.setPadding (10.0f);
                                      //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                    table.addCell(cellSum11);          
                    
                                              
                PdfPCell cellSum12 = new PdfPCell (new Paragraph (sumaKorPN.toString(), bold));

                                      cellSum12.setColspan( 1 ); // connect column to one 
                                      cellSum12.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      cellSum12.setPadding (10.0f);
                                      //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                    table.addCell(cellSum12);          
                   
                    
                PdfPCell cellSum13 = new PdfPCell (new Paragraph (" ", bold));

                                             cellSum13.setColspan( 1 ); // connect column to one 
                                             cellSum13.setHorizontalAlignment (Element.ALIGN_CENTER);
                                             cellSum13.setPadding (10.0f);
                                             //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	

                           table.addCell(cellSum13);                                               
                         
                    
                    
                    
                    
                    
                     document.open();//PDF document opened........			       
 
        //document.add(image);

        document.add(Chunk.NEWLINE);   //Something like in HTML :-)
 
                    
        document.add(new Paragraph("Vendi Servis Sp. z o.o." )); 
        document.add(new Paragraph("Zestawienie na dzien: " + dtf1.format(naDzien) )); 

          //  document.add(new Paragraph("Document Generated On - "+new Date().toString()));	
          
        document.add(Chunk.NEWLINE);  
 
        document.add(table);
        
        
       
        

        //document.add(chunk);
        //document.add(chunk1);
 
        document.add(Chunk.NEWLINE);   //Something like in HTML :-)							    
 
        document.newPage();            //Opened new page
      
 
        document.close();
 
        file.close();
 
        System.out.println("Raport created successfully...");
         
    }
    
    
    
    public void cc( Date naDzien, int kierKosztow ){
        
        
        
        //System.out.print(kierKosztow + naDzien);

        BindUtils.postNotifyChange(null, null, stanyZywionychNaDzien2, "*");
    }
    
    
}