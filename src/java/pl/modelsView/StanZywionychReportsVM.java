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
        test.setValue("Raport dla kk: " + serviceFacade.kkRaport.getKierunekKosztowNazwa() + " " + serviceFacade.kkRaport.getIdKierunekKosztow() );
        
        Textbox tbOkres = new Textbox();
        tbOkres.setWidth("100px");
        tbOkres.setText("2016-10");
        
        Button run = new Button();
        run.setLabel("Run");
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
            File f = new File("PDF_s_test.pdf");
            
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
                            Font myFont = new Font(bf, 8);
                            
                            Font regular = new Font(FontFamily.HELVETICA, 8);
                            Font bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD);

                            
 
	                     PdfPCell cell = new PdfPCell (new Paragraph ("Sprawozdanie wartosciowe z dzialalnosci kuchni", myFont));
 
				      cell.setColspan(17); // connect column to one 
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cell.setPadding (10.0f);
				      cell.setBackgroundColor (new BaseColor (140, 221, 8));	
                                      
                                      
                                      table.setWidths(new int[]{200,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50});
				      table.addCell(cell);						               
                                      // name of column
                                      
				      table.addCell(new PdfPCell (new Paragraph ("-", bold)));
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

				      table.setSpacingBefore(30.0f);       // Space Before table starts, like margin-top in CSS
				      table.setSpacingAfter(30.0f);        // Space After table starts, like margin-Bottom in CSS
                                      
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
                                         
                                         
                                      
                                          
                                      
                                      
                                      String kkName = "Start";
                                      // row 
                                      for ( StanZywionychMMRapRozDTO stanZywionychOkres : stanZywionych )
                                      {
                                        
                                        
                                        if ( !kkName.equals(stanZywionychOkres.getKk()) )
                                        {
                                            PdfPCell cellKK = new PdfPCell (new Paragraph (stanZywionychOkres.getKk(), bold));
                                            cellKK.setColspan(17); // connect column to one 
                                            cellKK.setHorizontalAlignment (Element.ALIGN_LEFT);
                                            cellKK.setPadding (3.0f);
                                            cellKK.setBackgroundColor (new BaseColor (222, 222, 222));
                                            table.addCell(cellKK);
                                        }
                                          
                                         System.out.print(stanZywionychOkres.getGz());
                                          
                                         PdfPCell cell01 = new PdfPCell( new Paragraph( stanZywionychOkres.getGz(), myFont )); 
                                         table.addCell( cell01 );
                                         
                                         table.addCell( new PdfPCell( new Paragraph(stanZywionychOkres.getSn3().toString() + "/" + stanZywionychOkres.getSn3().multiply(cS3) , myFont)) );
                                         table.addCell( new PdfPCell( new Paragraph(stanZywionychOkres.getSn5().toString() + "/" + stanZywionychOkres.getSn5().multiply(cS5) , myFont)) ); //null
                                         table.addCell( new PdfPCell( new Paragraph(stanZywionychOkres.getSn6().toString() + "/" + stanZywionychOkres.getSn6().multiply(cS6) , myFont)) ); 
                                         table.addCell( new PdfPCell( new Paragraph(stanZywionychOkres.getDsn5().toString(), myFont)) );
                                         table.addCell( new PdfPCell( new Paragraph(stanZywionychOkres.getDsn6().toString(), myFont)) );
                                         
                                         table.addCell( new PdfPCell( new Paragraph(stanZywionychOkres.getOb3().toString(), myFont)) );
                                         table.addCell( new PdfPCell( new Paragraph(stanZywionychOkres.getOb5().toString(), myFont)) );
                                         table.addCell( new PdfPCell( new Paragraph(stanZywionychOkres.getOb6().toString(), myFont)) );
                                         table.addCell( new PdfPCell( new Paragraph(stanZywionychOkres.getPod5().toString(), myFont)) );
                                         table.addCell( new PdfPCell( new Paragraph(stanZywionychOkres.getPod6().toString(), myFont)));
                                         table.addCell( new PdfPCell( new Paragraph(stanZywionychOkres.getKol3().toString(), myFont)) );
                                         table.addCell( new PdfPCell( new Paragraph(stanZywionychOkres.getKol5().toString(), myFont)) );
                                         table.addCell( new PdfPCell( new Paragraph(stanZywionychOkres.getKol6().toString(), myFont)) );
                                         table.addCell( new PdfPCell( new Paragraph(stanZywionychOkres.getPn().toString(), myFont)));
                                         table.addCell( "" );
                                         table.addCell( "-" );
                                         
                                         kkName =  stanZywionychOkres.getKk();
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
	                Chunk chunk=new Chunk("Welecome To Java4s Programming Blog...");
					chunk.setUnderline(+1f,-2f);//1st co-ordinate is for line width,2nd is space between
					Chunk chunk1=new Chunk("Php4s.com");
					chunk1.setUnderline(+4f,-8f);
					chunk1.setBackground(new BaseColor (17, 46, 193));      
 
			 //Now Insert Every Thing Into PDF Document
		         document.open();//PDF document opened........			       
 
					//document.add(image);
 
					document.add(Chunk.NEWLINE);   //Something like in HTML :-)
 
                    
                    
                    document.add(new Paragraph("Miesiąc: " + okres.toString()));
	              //  document.add(new Paragraph("Document Generated On - "+new Date().toString()));	
 
					document.add(table);
 
					document.add(chunk);
					document.add(chunk1);
 
					document.add(Chunk.NEWLINE);   //Something like in HTML :-)							    
 
       				document.newPage();            //Opened new page
					document.add(list);            //In the new page we are going to add list
 
		         document.close();
 
			             file.close();
 
            System.out.println("Pdf created successfully..");
 
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
