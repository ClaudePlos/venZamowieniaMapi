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
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Init;
import org.apache.poi.poifs.property.Child;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.DependsOn;
import org.zkoss.bind.annotation.NotifyChange;
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
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import pl.models.SprWartDzialalnosciKuchniDTO;
import pl.models.StanZywionychNaDzienDTO;
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
    
    /*ListModel<String> grupaZywionychModel = new ListModelList<String>( serviceFacade.getGrupaZywionych(null) );*/
    
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
          SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
          SimpleDateFormat dtf1 = new SimpleDateFormat("yyyy_MM_dd");

          // 02. Generation raport 
            File f = new File("SZ" + serviceFacade.gzRaprot + "_" + dtf1.format( serviceFacade.naDzienRaport ) + ".pdf");
            
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
                            BaseFont bf = BaseFont.createFont();
                            Font myFont = new Font(bf, 8);
                            
                            Font regular = new Font(Font.FontFamily.HELVETICA, 8);
                            Font bold = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);

                            
                             
	                     PdfPCell cell = new PdfPCell (new Paragraph ( "Grupa: " + serviceFacade.gzRaprot + " na dzien: " + dtf.format( serviceFacade.naDzienRaport ), myFont));
 
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
                                      
                                      // row -> wiersze loop 
                                      for ( StanZywionychNaDzienDTO s : serviceFacade.stanyZywionychNaDzien  )
                                      {
    
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
                                         
                                         
                                         
                                         
                                         
                                         
                                      }
                                      
 
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
	                document.add(new Paragraph("SZ w dniu dla GZ. Document Generated On - "+ dtf.format( new Date() ).toString()));	
 
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
    
    
    
}