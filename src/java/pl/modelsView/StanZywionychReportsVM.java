/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.modelsView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
 
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import javax.ejb.EJB;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import pl.models.NapMapowaniaCenyVO;
import pl.models.SprWartDzialalnosciKuchniDTO;
import pl.models.StanZywionychMMRapRozDTO;
import pl.models.StanZywionychNaDzienDTO;
import pl.session.ServiceFacade;

/**
 *
 * @author k.skowronski
 */
public class StanZywionychReportsVM {

   
    
    @EJB 
    ServiceFacade serviceFacade = ServiceFacade.getInstance();
    
    java.util.List<NapMapowaniaCenyVO> napMapowaniaCenyList = new ArrayList<NapMapowaniaCenyVO>();
    
    
    @Command
    @NotifyChange("stanyZywionychReprots")
    public void stanZywionychReports() throws IOException, Exception{
        
        Window window = (Window)Executions.createComponents(
                "/orderReports/report_for_plock_hospital.zul", null, null);
        window.doModal();
        
        Listbox listBoxR = new Listbox();
        listBoxR.setHeight("450px");
        
        Label test = new Label();
        test.setValue(" Raport dla kierunku kosztów: " + serviceFacade.kkRaport.getKierunekKosztowNazwa() ); // id mi tutaj nie potrzebne //+ " " + serviceFacade.kkRaport.getIdKierunekKosztow() );
        
        Textbox tbOkres = new Textbox();
        tbOkres.setWidth("100px");
        tbOkres.setText("2017-01");
        
        Button run = new Button();
        run.setLabel("Uruchom");
        run.addEventListener("onClick", new EventListener() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                
                try {
                    zapiszPDF( tbOkres.getText() );
                } catch (Exception e) {
                    //alert();
                }
    
                
            }   
        });
        
        
       
        window.appendChild(tbOkres);
        window.appendChild(run);
        window.appendChild(test);
       // window.appendChild(listBoxR);
        
        getNapMapowaniaCeny();
 
     }   
    
    
    public void getNapMapowaniaCeny(){
        
        napMapowaniaCenyList = serviceFacade.getNapMapowaniaCeny();
        
    }
    
        
    //@Command
    //@NotifyChange("zapiszPDF")
    public void zapiszPDF( String okres ) throws IOException, Exception{
       
         java.util.List<StanZywionychMMRapRozDTO> stanZywionych = new ArrayList<StanZywionychMMRapRozDTO>();
        
        try {
            // 01. preparing data 
            stanZywionych = serviceFacade.getDataForFinancialRaport(okres, serviceFacade.kkRaport.getIdKierunekKosztow().intValue() );
            
            
            
            // 02. Generation raport 
            File f = new File("raport_finansowy.pdf");
            
              OutputStream file = new FileOutputStream(f); //
            // OutputStream file = new FileOutputStream(new File("//Users//Claude//Desktop//PDF_Java4s.pdf"));
	          Document document = new Document(PageSize.A4.rotate());
	          PdfWriter.getInstance(document, file);
                  
                  Filedownload.save(f, "application/pdf");
 
			//Inserting Image in PDF
			    // Image image = Image.getInstance ("src/pdf/java4s.png");
			    // image.scaleAbsolute(120f, 60f);//image width,height	
 
			//Inserting Table in PDF
			     PdfPTable table=new PdfPTable(17); // number of column
                             
                             table.setTotalWidth(790);
                             table.setLockedWidth(true);
                             
                             
                            BaseFont bf = BaseFont.createFont();
                            Font myFont_Naglowek = new Font(bf, 12); //rozmiar czcionki
                            Font myFont_Posilek = new Font(bf, 10);  //rozmiar czcionki
                            Font myFont = new Font(bf, 8);           //rozmiar czcionki
                            
                            
                            Font regular = new Font(FontFamily.HELVETICA, 8);
                            Font bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD);

                            
 
	                     PdfPCell cell = new PdfPCell (new Paragraph ("Sprawozdanie wartosciowe z dzialalnosci kuchni", myFont_Naglowek));
 
				      cell.setColspan(17); // connect column to one 
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cell.setPadding (10.0f);
				      cell.setBackgroundColor (new BaseColor (140, 221, 8));	

                                      
                                      table.setWidths(new int[]{200,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50});
				      table.addCell(cell);						               
                                      // name of column
                                      
                                      
                                      PdfPCell cell_1 = new PdfPCell (new Paragraph ("posilek", myFont_Posilek));
				      cell_1.setColspan(1); // connect column to one 
                                      cell_1.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      table.addCell(cell_1);
                                      
                                      PdfPCell cell_2 = new PdfPCell (new Paragraph ("sniadanie", myFont_Posilek));
				      cell_2.setColspan(3); // connect column to one 
                                      cell_2.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      table.addCell(cell_2);
                                      
                                      PdfPCell cell_3 = new PdfPCell (new Paragraph ("2 sniadanie", myFont_Posilek));
				      cell_3.setColspan(2); // connect column to one 
                                      cell_3.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      table.addCell(cell_3);
                                      
                                      PdfPCell cell_4 = new PdfPCell (new Paragraph ("obiad", myFont_Posilek));
				      cell_4.setColspan(3); // connect column to one 
                                      cell_4.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      table.addCell(cell_4);
                                      
                                      PdfPCell cell_5 = new PdfPCell (new Paragraph ("podwieczorek", myFont_Posilek));
				      cell_5.setColspan(2); // connect column to one 
                                      cell_5.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      table.addCell(cell_5);
                                      
                                      PdfPCell cell_6 = new PdfPCell (new Paragraph ("kolacja", myFont_Posilek));
				      cell_6.setColspan(3); // connect column to one 
                                      cell_6.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      table.addCell(cell_6);
                                      
                                      PdfPCell cell_7 = new PdfPCell (new Paragraph ("posilek nocny / dodatek", myFont_Posilek));
				      cell_7.setColspan(2); // connect column to one 
                                      cell_7.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      table.addCell(cell_7);
                                                                                                                 
                                      PdfPCell cell_9 = new PdfPCell (new Paragraph (" ", myFont_Posilek));
				      cell_9.setColspan(1); // connect column to one 
                                      cell_9.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      table.addCell(cell_9);
                                      
                                      
				      table.addCell(new PdfPCell (new Paragraph (" ", bold)));
				      table.addCell("SN3");
				      table.addCell("SN5");
                                      table.addCell("SN6");
                                      table.addCell("II SN5");
                                      table.addCell("II SN6");
                                      table.addCell("OB3");
                                      table.addCell("OB5");
                                      table.addCell("OB6");
                                      table.addCell("POD5");
                                      table.addCell("POD6");
                                      table.addCell("KOL3");
                                      table.addCell("KOL5");
                                      table.addCell("KOL6");
                                      table.addCell("PNoc");
                                      table.addCell("Dod");
                                      table.addCell("Suma");

				      table.setSpacingBefore(10.0f);       // Space Before table starts, like margin-top in CSS
				      table.setSpacingAfter(10.0f);        // Space After table starts, like margin-Bottom in CSS
                                      
                                      
                                      //************************************************************************************************************
                                      
                                      //pobieram ceny
                                        BigDecimal cS3 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 3 posiłkowa" ))
                                                                .findFirst().get().getMapcSniadanie() ;
                                        
                                        BigDecimal cS5 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 5 posiłkowa" ))
                                                                .findFirst().get().getMapcSniadanie() ;
                                        
                                        BigDecimal cS6 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 6 posiłkowa" ))
                                                                .findFirst().get().getMapcSniadanie() ;
                                        //
                                        BigDecimal cDS5 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 5 posiłkowa" ))
                                                                .findFirst().get().getMapc2Sniadanie() ;
                                        
                                        BigDecimal cDS6 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 6 posiłkowa" ))
                                                                .findFirst().get().getMapc2Sniadanie();
                                        
                                        //
                                        BigDecimal cO3 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 3 posiłkowa" ))
                                                                .findFirst().get().getMapcObiad();
                                        
                                        BigDecimal cO5 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 5 posiłkowa" ))
                                                                .findFirst().get().getMapcObiad() ;
                                        
                                        BigDecimal cO6 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 6 posiłkowa" ))
                                                                .findFirst().get().getMapcObiad() ;
                                        
                                        //
                                        BigDecimal cPO5 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 5 posiłkowa" ))
                                                                .findFirst().get().getMapcPodwieczorek() ;
                                        
                                        BigDecimal cPO6 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 6 posiłkowa" ))
                                                                .findFirst().get().getMapcPodwieczorek();
                                        
                                        //
                                        BigDecimal cK3 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 3 posiłkowa" ))
                                                                .findFirst().get().getMapcKolacja();
                                        
                                        BigDecimal cK5 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 5 posiłkowa" ))
                                                                .findFirst().get().getMapcKolacja() ;
                                        
                                        BigDecimal cK6 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 6 posiłkowa" ))
                                                                .findFirst().get().getMapcKolacja() ;
                                        
                                        
                                        //dieta 3 posiłkowa + nocny
                                        BigDecimal cPN = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 3 posiłkowa + nocny" ))
                                                                .findFirst().get().getMapcPosilekNocny() ;
                                        
                                        //dieta 3 posiłkowa + nocny
                                        BigDecimal cKompot = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 3 posiłkowa + nocny" ))
                                                                .findFirst().get().getMapcZupaKompot() ;
                                        
                                       
                                      
                                         table.addCell("Cena diety");
                                         table.addCell( cS3.toString() );
                                         table.addCell( cS5.toString() );
                                         table.addCell( cS6.toString() );
                                         table.addCell( cDS5.toString() );
                                         table.addCell( cDS6.toString() );
                                         table.addCell( cO3.toString() );
                                         table.addCell( cO5.toString() );
                                         table.addCell( cO6.toString() );
                                         table.addCell( cPO5.toString() );
                                         table.addCell( cPO6.toString() );
                                         table.addCell( cK3.toString() );
                                         table.addCell( cK5.toString() );
                                         table.addCell( cK6.toString() );
                                         table.addCell( cPN.toString() );
                                         table.addCell( cKompot.toString() );
                                         table.addCell("");
                                         
                                         
                                    //***********************************************************************
                                          
                                      int i = 0;
                                      int numberOfRow = stanZywionych.size();
                                      String kkName = "Start";
                                      
                                      // suma razem kk
                                      //deklaracja
                                      BigDecimal sumS3 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal sumS5 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal sumS6 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal sum2S5 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal sum2S6 = new BigDecimal(BigInteger.ZERO);
                                      //TODO Marcin
                                     
                                      
                                      //suma razem
                                      //deklaracja
                                      BigDecimal allSumS3 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal allSumS5 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal allSumS6 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal allSum2S5 = new BigDecimal(BigInteger.ZERO);
                                      //TODO Marcin
                                      
                                      // ROW
                                      for ( StanZywionychMMRapRozDTO stanZywionychOkres : stanZywionych )
                                      {
                                        
                                        
                                        if ( !kkName.equals(stanZywionychOkres.getKk()) )
                                        {
                                            if ( !kkName.equals("Start") )
                                            {
                                                table.addCell( new PdfPCell (new Paragraph ( "Razem " + kkName, bold )) );
                                                
                                                PdfPCell r02 = new PdfPCell (new Paragraph ( sumS3.toString(), bold ));
                                                r02.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r02 ); 
                                                
                                                PdfPCell r03 = new PdfPCell (new Paragraph ( sumS5.toString(), bold ));
                                                r03.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r03 );
                                                
                                                PdfPCell r04 = new PdfPCell (new Paragraph ( sumS6.toString(), bold ));
                                                r04.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r04 );
                                                
                                                //TODO Marcin
                                                PdfPCell r05 = new PdfPCell (new Paragraph ( sumS6.toString(), bold ));
                                                r05.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r05 );
                                                
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                
                                                PdfPCell r17 = new PdfPCell (new Paragraph ( 
                                                    sumS3.add( sumS5.add( sumS6 ) ).toString()
                                                        , bold ));
                                                r17.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r17 );
                                                
                                                allSumS3 = allSumS3.add(sumS3);
                                                allSumS5 = allSumS5.add(sumS5);
                                                allSumS6 = allSumS6.add(sumS6);
                                                allSum2S5 = allSum2S5.add(sum2S5);
                                                //TODO Marcin
                                                
                                                sumS3 = new BigDecimal(BigInteger.ZERO);
                                                sumS5 = new BigDecimal(BigInteger.ZERO);
                                                sumS6 = new BigDecimal(BigInteger.ZERO);
                                                sum2S5 = new BigDecimal(BigInteger.ZERO);
                                                //TODO Marcin

                                                PdfPCell cellNewLine = new PdfPCell (new Paragraph ("", bold ));
                                                cellNewLine.setColspan(17);
                                                table.addCell(cellNewLine);
                                                
                                            }
                                            
                                            PdfPCell cellKK = new PdfPCell (new Paragraph (stanZywionychOkres.getKk(), myFont_Naglowek )); //generowana nazwa kierunku kosztow
                                            cellKK.setColspan(17); // connect column to one 
                                            cellKK.setHorizontalAlignment (Element.ALIGN_CENTER);
                                            cellKK.setPadding (10.0f);
                                            cellKK.setBackgroundColor (new BaseColor (222, 222, 222));
                                            table.addCell(cellKK);
                                        }
                                          
                                         System.out.print(stanZywionychOkres.getGz());
                                          
                                         PdfPCell cell01 = new PdfPCell( new Paragraph( stanZywionychOkres.getGz(), myFont ));
                                         table.addCell( cell01 );
                                         
                                         BigDecimal sn3m = stanZywionychOkres.getSn3().multiply(cS3);
                                         BigDecimal sn5m = stanZywionychOkres.getSn5().multiply(cS5);
                                         BigDecimal sn6m = stanZywionychOkres.getSn6().multiply(cS6);
                                         BigDecimal dsn5m = stanZywionychOkres.getDsn5().multiply(cDS5);
                                         //TODO Marcin
                                         
                                         
                                         
                                         
                                         
                                         
                                         //**********************************************
                                         //// wiersze gz
                                         PdfPCell cell02 = new PdfPCell( new Paragraph(stanZywionychOkres.getSn3().toString() + "/" + sn3m , myFont));
                                         cell02.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell02 );
                                         
                                         PdfPCell cell03 = new PdfPCell( new Paragraph(stanZywionychOkres.getSn5().toString() + "/" + sn5m , myFont));
                                         cell03.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell03 );
                                         
                                         
                                         PdfPCell cell04 = new PdfPCell( new Paragraph(stanZywionychOkres.getSn6().toString() + "/" + sn6m , myFont));
                                         cell04.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell04 );
                                         
                                         //TODO Marcin
                                         PdfPCell cell05 = new PdfPCell( new Paragraph(dsn5m.toString(), myFont));
                                         cell05.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell05 );
                                         
                                         PdfPCell cell06 = new PdfPCell( new Paragraph(stanZywionychOkres.getDsn6().toString(), myFont));
                                         cell06.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell06 );
                                         
                                         //
                                         PdfPCell cell07 = new PdfPCell( new Paragraph(stanZywionychOkres.getOb3().toString(), myFont));
                                         cell07.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell07 );
                                         
                                         PdfPCell cell08 = new PdfPCell( new Paragraph(stanZywionychOkres.getOb5().toString(), myFont));
                                         cell08.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell08 );
                                         
                                         PdfPCell cell09 = new PdfPCell( new Paragraph(stanZywionychOkres.getOb6().toString(), myFont));
                                         cell09.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell09 );
                                         
                                         //
                                         PdfPCell cell10 = new PdfPCell( new Paragraph(stanZywionychOkres.getPod5().toString(), myFont));
                                         cell10.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell10 );
                                         
                                         PdfPCell cell11 = new PdfPCell( new Paragraph(stanZywionychOkres.getPod6().toString(), myFont));
                                         cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell11 );
                                         
                                         
                                         //
                                         PdfPCell cell12 = new PdfPCell( new Paragraph(stanZywionychOkres.getKol3().toString(), myFont));
                                         cell12.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell12 );
                                         
                                         PdfPCell cell13 = new PdfPCell( new Paragraph(stanZywionychOkres.getKol5().toString(), myFont));
                                         cell13.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell13 );
                                         
                                         PdfPCell cell14 = new PdfPCell( new Paragraph(stanZywionychOkres.getKol6().toString(), myFont));
                                         cell14.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell14 );
                                         
                                        //
                                         PdfPCell cell15 = new PdfPCell( new Paragraph(stanZywionychOkres.getPn().toString(), myFont));
                                         cell15.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell15 );
                                         
                                         PdfPCell cell16 = new PdfPCell( new Paragraph("", myFont));
                                         cell16.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell16 );
                                         
                                         PdfPCell cell17 = new PdfPCell( new Paragraph( sn3m.add(sn5m.add(sn6m)).toString(), bold ));
                                         cell17.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell17 );
                                        
                                   
                                         
                                         sumS3 = sumS3.add(sn3m);
                                         sumS5 = sumS5.add(sn5m);
                                         sumS6 = sumS6.add(sn6m);
                                         kkName =  stanZywionychOkres.getKk();
                                         
                                         //****************************************8
                                         
                                         
                                         
                                         // LAST ROW
                                         i++;
                                         
                                         if ( i == numberOfRow)
                                         {
                                                table.addCell( new PdfPCell (new Paragraph ( "Razem " + kkName, bold )) );
                                                
                                                PdfPCell r01 = new PdfPCell (new Paragraph ( sumS3.toString(), bold ));
                                                r01.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r01 ); 
                                                
                                                PdfPCell r02 = new PdfPCell (new Paragraph ( sumS5.toString(), bold ));
                                                r02.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r02 ); 
                                                
                                                PdfPCell r03 = new PdfPCell (new Paragraph ( sumS6.toString(), bold ));
                                                r03.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r03 ); 
                                                
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                
                                                
                                                PdfPCell r17 = new PdfPCell (new Paragraph ( 
                                                    sumS3.add( sumS5.add( sumS6 ) ).toString()
                                                        , bold )); 
                                                r17.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r17 );
                                                
                                                allSumS3 = allSumS3.add(sumS3);
                                                allSumS5 = allSumS5.add(sumS5);
                                                allSumS6 = allSumS6.add(sumS6);
                                                
                                                table.addCell( new PdfPCell (new Paragraph ( "Razem ", bold )) );
                                                
                                                PdfPCell ar01 = new PdfPCell (new Paragraph ( allSumS3.toString(), bold ));
                                                ar01.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( ar01 ); 
                                                
                                                PdfPCell ar02 = new PdfPCell (new Paragraph ( allSumS5.toString(), bold ));
                                                ar02.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( ar02 ); 
                                                
                                                PdfPCell ar03 = new PdfPCell (new Paragraph ( allSumS6.toString(), bold ));
                                                ar03.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( ar03 ); 
                                                
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                table.addCell( "" );
                                                
                                                PdfPCell ar17 = new PdfPCell (new Paragraph ( 
                                                        allSumS3.add( allSumS5.add( allSumS6 ) ).toString()
                                                        , bold ));
                                                ar17.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( ar17 );
                                                
                                                
                                         }
                                      }
                                      
 
			 //Inserting List in PDF
				      List list=new List(true,30);
			             /* list.add(new ListItem("Java4s"));
				      list.add(new ListItem("Php4s"));
				      list.add(new ListItem("Some Thing..."));	*/
                                      
                                     /* for ( SprWartDzialalnosciKuchniDTO swdK : listaStanWartosciowy )
                                      {
                                         list.add( swdK.getGrupaZywionychKod());
                                      }*/
                                      
 
			 //Text formating in PDF
	                /*Chunk chunk=new Chunk("Welecome To Java4s Programming Blog...");
					chunk.setUnderline(+1f,-2f);//1st co-ordinate is for line width,2nd is space between
					Chunk chunk1=new Chunk("Php4s.com");
					chunk1.setUnderline(+4f,-8f);
					chunk1.setBackground(new BaseColor (17, 46, 193));    */  
 
			 //Now Insert Every Thing Into PDF Document
		         document.open();//PDF document opened........			       
 
					//document.add(image);
 
					document.add(Chunk.NEWLINE);   //Something like in HTML :-)
 
                    
                    
                    document.add(new Paragraph("Okres: " + okres.toString()));
	              //  document.add(new Paragraph("Document Generated On - "+new Date().toString()));	
 
					document.add(table);
 
					//document.add(chunk);
					//document.add(chunk1);
 
					document.add(Chunk.NEWLINE);   //Something like in HTML :-)							    
 
       				document.newPage();            //Opened new page
					document.add(list);            //In the new page we are going to add list
 
		         document.close();
 
			             file.close();
 
            System.out.println("Raport created successfully...");
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
        
        
        
    
        
             
        /*
        ByteArrayOutputStream out = new ByteArrayOutputStream();
     
            Grid tree = new Grid();
            
            PdfExporter exporter = new PdfExporter();
            
            
           // exporter.export(tree, out);

            AMedia amedia = new AMedia("FirstReport.pdf", "pdf", "application/pdf", out.toByteArray());
            Filedownload.save(amedia);      
            out.close();
        */
        
    
    
    
    
}
