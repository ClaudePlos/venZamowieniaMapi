/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.views.zam;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.DependsOn;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;
import pl.authentication.AuthenticationServiceChapter8Impl;
import pl.models.GrupaZywionychVO;
import pl.services.AuthenticationService;
import pl.session.ServiceFacade;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import pl.models.StanZywionychNaDzienDTO;
import pl.models.reports.GzEventDTO;
import pl.modelsView.StanZywionychNaDzienVM;

/**
 *
 * @author k.skowronski
 */
public class MenuController extends SelectorComposer<Component> {
    
    Session sess = Sessions.getCurrent();
    
    AuthenticationService authService = new AuthenticationServiceChapter8Impl();
    
    @EJB 
    ServiceFacade serviceFacade; // = ServiceFacade.getInstance();

    String mainUrl = "http://localhost:8080"; //TEST 

    List<GrupaZywionychVO> grupyZywionych;
    
    /*ListModel<String> grupaZywionychModel = new ListModelList<String>( serviceFacade.getGrupaZywionych(null) );*/
    
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    
    private EventQueue eventGZnaDzien;
    
    // dal raportow
    public GzEventDTO gzEve;
    
    private SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dtf1 = new SimpleDateFormat("yyyy_MM_dd");

    URL url;
    
    @Wire
    private Window win;
    
    @Wire
    private Radiogroup sv1;
    
    @Wire
    private Vlayout result; //wired to a component called result
    
    @Wire
    private Datebox naDzien;   
    
    @Wire
    private Label selectedDiet; 
    
    @Wire
    private Combobox cmbGZ;   
    
    private String wybranaGrupaZywionych;
    
    Combobox cmbGrupaZywionych;


    public MenuController()
    {
      //  serviceFacade.listaGrupyZywionych();
        
      /*  Label l = new Label(); TO NIE DZIAŁA 
        l.setValue( "Witaj" + serviceFacade.user.getKod() );
        result.appendChild(l);*/ 
    }
    

   /* public ListModel<String> getGrupaZywionychModel() {
        return grupaZywionychModel;
    }*/

    public String getWybranaGrupaZywionych() {
        return wybranaGrupaZywionych;
    }

    public void setWybranaGrupaZywionych(String wybranaGrupaZywionych) {
        this.wybranaGrupaZywionych = wybranaGrupaZywionych;
    }

  
    
    
    
    public List<GrupaZywionychVO> getGrupyZywionych() {
        serviceFacade.listaGrupyZywionych(null);
        
        grupyZywionych = serviceFacade.grupyZywionych;
        return grupyZywionych;
    }
    
    
    
    //event 
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        eventGZnaDzien = EventQueues.lookup("eventGrupaZywionych", EventQueues.DESKTOP, true);
        eventGZnaDzien.subscribe(new EventListener() {
            public void onEvent(Event event) throws Exception {
                GzEventDTO value = (GzEventDTO)event.getData();
               // lbl.setValue(value);
               gzEve = value;
            }
        });
    }
    
    
    
   
    
    
    @DependsOn({"wybranaGrupaZywionych"})
    public void getShirtImage() {
       Messagebox.show(wybranaGrupaZywionych);
    }
    

    
    @Listen("onClick = button#pobierzStan")
    public void pobierzStan(Event event){
          
        Date stanNaDzien = naDzien.getValue();
          
         serviceFacade.stanyZywionychNaDzien 
                 = serviceFacade.pobierzStanZywionychWdniuDlaGrupyZywionych( formatter.format( stanNaDzien ), cmbGZ.getSelectedItem().getValue().toString(), "lp" );
         
         //StanZywionychNaDzienVM.getInstance().pobInne(formatter.format( stanNaDzien ),  cmbGZ.getSelectedItem().getValue().toString());
      //Comboitem zaznaczonaGrupaZywionych =  cmbGZ.getSelectedItem().getValue();
      //Messagebox.show(stanNaDzien.toString() + cmbGZ.getSelectedItem().getValue());
    }
    
    @Listen("onClick = button#logout")
    public void doLogout(){
            authService.logout();		
            Executions.sendRedirect("/");
    }
    

/*
    @Listen("onClick=#gzWybrane")
    public void gzWybrane(Event event) { //register a listener to a component called retrieve
          grupaZywionychModel = new ListModelList<String>( serviceFacade.getGrupaZywionych(null) );
          
            if ( cmbGZ != null )
            {
            
                cmbGrupaZywionych = new Combobox();
                cmbGrupaZywionych.setModel(grupaZywionychModel);  
               // result.appendChild(cmbGrupaZywionych);
                //cmbGZ = new Combobox();
                cmbGZ.setModel(grupaZywionychModel);
            }
    
    }*/
    
    @Listen("onClick=#gzWszystkie")
    public void gzWszystkie(Event event) {
          if ( cmbGrupaZywionych != null )
            result.removeChild(cmbGrupaZywionych);
    }
    
    @Listen("onClick=button#drukujDS")
    public void drukujDS(Event event) throws FileNotFoundException, DocumentException, IOException {
          // Messagebox.show("sdfsdf");
          

          // 02. Generation raport 
          
   
          
            File f = new File("Stan zywionych " + gzEve.getGzRaprot() + " " + 
                    dtf1.format( gzEve.getNaDzienRaport() ) + ".pdf");
            
              OutputStream file = new FileOutputStream(f); //
            // OutputStream file = new FileOutputStream(new File("//Users//Claude//Desktop//PDF_Java4s.pdf"));
	          Document document = new Document(PageSize.A4.rotate());
	          PdfWriter.getInstance(document, file);
                  
                  Filedownload.save(f, "application/pdf");
 
			//Inserting Image in PDF
                                // Image image = Image.getInstance ("src/pdf/java4s.png");
                                // image.scaleAbsolute(120f, 60f);//image width,height	
 
			//Inserting Table in PDF
			     PdfPTable table = new PdfPTable(9); // number of column
                             table.setTotalWidth(new float[]{ 40, 100, 100, 100, 100, 100, 100, 100, 150 });
                             table.setLockedWidth(true);
                             
                             //uklad poziomy
                             table.setTotalWidth(790);
                             table.setLockedWidth(true);
                             
                            //czcionki 
                            BaseFont bf = BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED ); // na polskie zniaki                   
                            Font myFont = new Font(bf, 8);
                            
                            Font regular = new Font(Font.FontFamily.HELVETICA, 8);
                            Font bold = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);

                            
                             
	                     PdfPCell cell = new PdfPCell (new Paragraph ( "Grupa zywionych: " + gzEve.getGzRaprot() + 
                                     " na dzien: " + dtf.format( gzEve.getNaDzienRaport() ), myFont));
 
				      cell.setColspan(9); // connect column to one 
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cell.setPadding (10.0f);
				      cell.setBackgroundColor (new BaseColor (140, 221, 8));	
                                      
                                      
 
				      table.addCell(cell);						               
                                      // name of column
                                      // naglowek tabeli
				      table.addCell( new PdfPCell (new Paragraph ("LP", bold)) );
				      table.addCell( new PdfPCell (new Paragraph ("Dieta", bold)) );
				      table.addCell("S");
                                      table.addCell("II S");
                                      table.addCell("O");
                                      table.addCell("P");
                                      table.addCell("K");
                                      table.addCell("PN");
                                      table.addCell("Uwagi");                                      
                                      
                                      
                                      
				      table.setSpacingBefore(30.0f);       // Space Before table starts, like margin-top in CSS
				      table.setSpacingAfter(30.0f);        // Space After table starts, like margin-Bottom in CSS	
 
                                      
         //deklaracje do sum//
         BigDecimal sumaSniadanie = BigDecimal.ZERO;
         BigDecimal sumaDrugieSniadanie = BigDecimal.ZERO;
         BigDecimal sumaObiad = BigDecimal.ZERO;
         BigDecimal sumaPodwieczorek = BigDecimal.ZERO;
         BigDecimal sumaKolacja = BigDecimal.ZERO;
         BigDecimal sumaPosilekNocny = BigDecimal.ZERO;

         
         
         
         
                                      // row -> wiersze loop 
                                      for ( StanZywionychNaDzienDTO s : gzEve.getStanyZywionychNaDzien()  )
                                      {
                                         
                                        BigDecimal sumRow = BigDecimal.ZERO;
                                         
                                        sumRow = sumRow.add( changeNullOnZero(s.getSniadaniePlanIl()) )
                                                .add( changeNullOnZero(s.getDrugieSniadaniePlanIl()) )
                                                .add( changeNullOnZero(s.getObiadPlanIl()) )
                                                .add( changeNullOnZero(s.getPodwieczorekPlanIl()) )
                                                .add( changeNullOnZero(s.getKolacjaPlanIl()) )
                                                .add( changeNullOnZero(s.getPosilekNocnyPlanIl()) )
                                                .add( changeNullOnZero(s.getSniadanieKorIl()) )
                                                .add( changeNullOnZero(s.getDrugieSniadanieKorIl()) )
                                                .add( changeNullOnZero(s.getObiadKorIl()) )
                                                .add( changeNullOnZero(s.getPodwieczorekKorIl()) )
                                                .add( changeNullOnZero(s.getKolacjaKorIl()) )
                                                .add( changeNullOnZero(s.getPosilekNocnyKorIl()) )
                                                ;
                                        
                                        if ( sumRow == BigDecimal.ZERO )
                                        {
                                           continue; 
                                        }
                                                 
                                          
                                        
                                          
                       
    
                                         table.addCell( new PdfPCell (new Paragraph (s.getLp().toString() , myFont)) );
                                         table.addCell( new PdfPCell (new Paragraph (s.getDietaNazwa() , myFont)) );
                                                                                  
                                         //S
                                         if ( s.getSniadaniePlanIl() != null )
                                         {
                                            table.addCell( new PdfPCell (new Paragraph ( s.getSniadaniePlanIl().add(s.getSniadanieKorIl()).toString() , myFont)) ); //S                                          
                                         }
                                         else 
                                         {
                                             table.addCell( "" );
                                         }
                                         
                                         //IIS
                                         if ( s.getDrugieSniadaniePlanIl() != null )
                                         {
                                            table.addCell( new PdfPCell (new Paragraph (s.getDrugieSniadaniePlanIl().add(s.getDrugieSniadanieKorIl()).toString() , myFont)) ); //S 
                                         }
                                         else 
                                         {
                                             table.addCell( "" );
                                         }
                                         
                                         //O
                                         if ( s.getObiadPlanIl() != null )
                                         {
                                            table.addCell( new PdfPCell (new Paragraph (s.getObiadPlanIl().add(s.getObiadKorIl()).toString() , myFont)) ); //S 
                                         }
                                         else 
                                         {
                                             table.addCell( "" );
                                         }
                                         
                                         //P
                                         if ( s.getPodwieczorekPlanIl() != null )
                                         {
                                            table.addCell( new PdfPCell (new Paragraph (s.getPodwieczorekPlanIl().add(s.getPodwieczorekKorIl()).toString() , myFont)) ); //S 
                                         }
                                         else 
                                         {
                                             table.addCell( "" );
                                         }
                                         
                                         //K
                                         if ( s.getKolacjaPlanIl() != null )
                                         {
                                            table.addCell( new PdfPCell (new Paragraph (s.getKolacjaPlanIl().add(s.getKolacjaKorIl()).toString() , myFont)) ); //S 
                                         }
                                         else 
                                         {
                                             table.addCell( "" );
                                         }
                                         
                                         //PN
                                         if ( s.getPosilekNocnyPlanIl() != null )
                                         {
                                            table.addCell( new PdfPCell (new Paragraph (s.getPosilekNocnyPlanIl().add(s.getPosilekNocnyKorIl()).toString() , myFont)) ); //S 
                                         }
                                         else 
                                         {
                                             table.addCell( "" );
                                         }
                                         
                                         //Uwagi
                                         if ( s.getSzUwagi() != null )
                                         {
                                            table.addCell( new PdfPCell (new Paragraph (s.getSzUwagi().toString() , myFont)) ); //S 
                                         }
                                         else 
                                         {
                                             table.addCell( "" );
                                         }
                                         
                                         
                                         
                                         
                            //do zliczenia sum             
                          
                            // sumaSniadanie = sumaSniadanie.add(s.getSniadaniePlanIl()); //tak nie poniewaz wywali sie na pustych pozycjach i zamiast tego robimy petle if
                            if(s.getSniadaniePlanIl() != null )
                            {
                            sumaSniadanie = sumaSniadanie.add(s.getSniadaniePlanIl().add(s.getSniadanieKorIl()));
                            }                           
                            
                            if(s.getDrugieSniadaniePlanIl() != null )
                            {
                            sumaDrugieSniadanie = sumaDrugieSniadanie.add(s.getDrugieSniadaniePlanIl().add(s.getDrugieSniadanieKorIl()));
                            } 
                            
                            if(s.getObiadPlanIl() != null )
                            {
                            sumaObiad = sumaObiad.add(s.getObiadPlanIl().add(s.getObiadKorIl()));
                            } 
							
                            if(s.getPodwieczorekPlanIl() != null )
                            {
                            sumaPodwieczorek = sumaPodwieczorek.add(s.getPodwieczorekPlanIl().add(s.getPodwieczorekKorIl()));
                            } 

                            if(s.getKolacjaPlanIl() != null )
                            {
                            sumaKolacja = sumaKolacja.add(s.getKolacjaPlanIl().add(s.getKolacjaKorIl()));
                            } 

                            if(s.getPosilekNocnyPlanIl() != null )
                            {
                            sumaPosilekNocny = sumaPosilekNocny.add(s.getPosilekNocnyPlanIl().add(s.getPosilekNocnyKorIl()));
                            }                               
                            
                            
                                      } 
                                      
                                      
                                      
                                      
                                      
                                      
                                      
         ////SUMY////        
              

              
              
              PdfPCell cellSum00 = new PdfPCell (new Paragraph ("SUMA:", bold));
                                             cellSum00.setColspan( 2 ); // connect column to one 
                                             cellSum00.setHorizontalAlignment (Element.ALIGN_CENTER);
                                             cellSum00.setPadding (10.0f);
                                             //cellDane00.setBackgroundColor (new BaseColor (140, 221, 8));	
              table.addCell(cellSum00);                       
                           
                           
               PdfPCell cellSum01 = new PdfPCell (new Paragraph (sumaSniadanie.toString(), bold));
                                      cellSum01.setColspan( 1 ); // connect column to one 
                                     // cellSum01.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      cellSum01.setPadding (10.0f);
                                      //cellDane01.setBackgroundColor (new BaseColor (140, 221, 8));	
               table.addCell(cellSum01); 

               PdfPCell cellSum02 = new PdfPCell (new Paragraph (sumaDrugieSniadanie.toString(), bold));
                                      cellSum02.setColspan( 1 ); // connect column to one 
                                      //cellSum02.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      cellSum02.setPadding (10.0f);
                                      //cellDane02.setBackgroundColor (new BaseColor (140, 221, 8));	
               table.addCell(cellSum02); 

               PdfPCell cellSum03 = new PdfPCell (new Paragraph (sumaObiad.toString(), bold));
                                      cellSum03.setColspan( 1 ); // connect column to one 
                                      //cellSum03.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      cellSum03.setPadding (10.0f);
                                      //cellDane03.setBackgroundColor (new BaseColor (140, 221, 8));	
               table.addCell(cellSum03);

               PdfPCell cellSum04 = new PdfPCell (new Paragraph (sumaPodwieczorek.toString(), bold));
                                      cellSum04.setColspan( 1 ); // connect column to one 
                                      //cellSum04.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      cellSum04.setPadding (10.0f);
                                      //cellDane04.setBackgroundColor (new BaseColor (140, 221, 8));	
               table.addCell(cellSum04);

               PdfPCell cellSum05 = new PdfPCell (new Paragraph (sumaKolacja.toString(), bold));
                                      cellSum05.setColspan( 1 ); // connect column to one 
                                      //cellSum05.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      cellSum05.setPadding (10.0f);
                                      //cellDane05.setBackgroundColor (new BaseColor (140, 221, 8));	
               table.addCell(cellSum05);

               PdfPCell cellSum06 = new PdfPCell (new Paragraph (sumaPosilekNocny.toString(), bold));
                                      cellSum06.setColspan( 1 ); // connect column to one 
                                      //cellSum06.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      cellSum06.setPadding (10.0f);
                                      //cellDane06.setBackgroundColor (new BaseColor (140, 221, 8));	
               table.addCell(cellSum06);  
               
              PdfPCell cellSum07 = new PdfPCell (new Paragraph ("", bold));
                                             cellSum07.setColspan( 1 ); // connect column to one 
                                             cellSum07.setHorizontalAlignment (Element.ALIGN_CENTER);
                                             cellSum07.setPadding (10.0f);
                                             //cellSum07.setBackgroundColor (new BaseColor (140, 221, 8));     
              table.addCell(cellSum07);                                                       
                
                
                
                
                
                
                
 
			 //Inserting List in PDF
				      com.itextpdf.text.List list=new com.itextpdf.text.List(true,30);
			              //list.add(new ListItem("Java4s"));
				      //list.add(new ListItem("Some Thing..."));	
                                      
                                      
                                      
 
			 //Text formating in PDF
	               /* Chunk chunk=new Chunk("Welecome To Java4s Programming Blog...");
					chunk.setUnderline(+1f,-2f);//1st co-ordinate is for line width,2nd is space between
					Chunk chunk1=new Chunk("Php4s.com");
					chunk1.setUnderline(+4f,-8f);
					chunk1.setBackground(new BaseColor (17, 46, 193));  */    
 
			 //Now Insert Every Thing Into PDF Document
		         document.open();//PDF document opened........			       
 
					//document.add(image);
 
					document.add(Chunk.NEWLINE);   //Something like in HTML :-)
 
                        //document.add(new Paragraph("Dear Java4s.com"));
                        //document.add(new Paragraph("k.skowronski"));
                        // document.add(new Paragraph(naDzienRap.toString()));
	                document.add(new Paragraph("Stan zywionych na dzien: "+ dtf.format( gzEve.getNaDzienRaport() ).toString()));	
 
                        
                                   /* int num = document.getPageNumber();
                                    
                                    final PdfPCell pageCountCell = new PdfPCell(new Phrase(
                                        "numer strony: " + num +"/ ", myFont));
                                    pageCountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                    pageCountCell.setBorder(0);
                                    pageCountCell.setBorderWidthTop(1f);
                                    table.addCell(pageCountCell);
                        
                                    document.add(new Paragraph("numer strony:  " + Integer.toString(num) + "/"));
                                    */
                        
					document.add(table);
                                        
                                        
                                        
                                        
 
					//document.add(chunk);
					//document.add(chunk1);
 
					document.add(Chunk.NEWLINE);   //Something like in HTML :-)							    
 
       				document.newPage();            //Opened new page
					document.add(list);            //In the new page we are going to add list
                                        
                                        
                          
                                    
                                        
 
		         document.close();
 
			             file.close();
 
            System.out.println("Pdf created successfully..");
 
        
          
    }
    
    
    
    @Listen("onClick=button#restWartOdzywczaForDietInDay")
    public void restWartOdzywczaForDietInDay(Event event) throws FileNotFoundException, DocumentException, IOException {
         //final Session sess = Sessions.getCurrent();
         System.out.println( gzEve.getGzRaprot() + " " + dtf.format( gzEve.getNaDzienRaport() ).toString() 
                 + " " + Sessions.getCurrent().getAttribute("dietId")
                 + " " + Sessions.getCurrent().getAttribute("gzId"));
         
         String url = mainUrl + "/venZamowieniaMapi/additionalInfo/wartoscOdzywczaInfo/index.html?gzId=" + Sessions.getCurrent().getAttribute("gzId")
                 + "&dietId=" + Sessions.getCurrent().getAttribute("dietId") + "&forDay=" + dtf.format( gzEve.getNaDzienRaport() ).toString();
         Executions.getCurrent().sendRedirect(url, "_blank");
    }
    
    
    @Listen("onClick=button#restJadlospisForDietInDay")
    public void restJadlospisForDietInDay(Event event) throws FileNotFoundException, DocumentException, IOException {
         //final Session sess = Sessions.getCurrent();
         System.out.println( gzEve.getGzRaprot() + " " + dtf.format( gzEve.getNaDzienRaport() ).toString() 
                 + " " + Sessions.getCurrent().getAttribute("dietId")
                 + " " + Sessions.getCurrent().getAttribute("gzId"));
         
         String url = mainUrl + "/venZamowieniaMapi/additionalInfo/jadlospisForDietInDay/index.html?gzId=" + Sessions.getCurrent().getAttribute("gzId")
                 + "&dietId=" + Sessions.getCurrent().getAttribute("dietId") + "&forDay=" + dtf.format( gzEve.getNaDzienRaport() ).toString();
         Executions.getCurrent().sendRedirect(url, "_blank");
    }
    
    private BigDecimal changeNullOnZero( BigDecimal val )
    {
        if ( val == null )
            return BigDecimal.ZERO;
        else
            return val;
    }
    
    
    
}