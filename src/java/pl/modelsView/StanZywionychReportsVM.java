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
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.ejb.EJB;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;
import pl.models.SprWartDzialalnosciKuchniDTO;
import pl.session.ServiceFacade;

/**
 *
 * @author k.skowronski
 */
public class StanZywionychReportsVM {
    
    @EJB 
    ServiceFacade serviceFacade = ServiceFacade.getInstance();
    
    
    @Command
    @NotifyChange("stanyZywionychReprots")
    public void stanZywionychReports() throws IOException, Exception{
        
        Window window = (Window)Executions.createComponents(
                "/orderReports/report_for_plock_hospital.zul", null, null);
        window.doModal();
        
        Listbox listBoxR = new Listbox();
        listBoxR.setHeight("450px");
        
        Label test = new Label();
        test.setValue("Test ks dzis");
        
        window.appendChild(test);
        window.appendChild(listBoxR);
 
     }   
    
    
        
    @Command
    @NotifyChange("zapiszPDF")
    public void zapiszPDF( @BindingParam("naDzienRap") Date naDzienRap ) throws IOException, Exception{
       
        try {
            // 01. preparing data 
            java.util.List<SprWartDzialalnosciKuchniDTO> listaStanWartosciowy = serviceFacade.pobierzSprWartoscioweDzialalnoscKuchni(naDzienRap.toString());
            
            
            
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
			     PdfPTable table=new PdfPTable(14); // number of column
                             
                             table.setTotalWidth(790);
                             table.setLockedWidth(true);
                             
                             
                            BaseFont bf = BaseFont.createFont();
                            Font myFont = new Font(bf, 8);
                            
                            Font regular = new Font(FontFamily.HELVETICA, 8);
                            Font bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD);

                            
 
	                     PdfPCell cell = new PdfPCell (new Paragraph ("Sprawozdanie wartosciowe...", myFont));
 
				      cell.setColspan(14); // connect column to one 
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cell.setPadding (10.0f);
				      cell.setBackgroundColor (new BaseColor (140, 221, 8));	
                                      
                                      
 
				      table.addCell(cell);						               
                                      // name of column
                                      
				      table.addCell(new PdfPCell (new Paragraph ("Grupa Zywionch", bold)));
				      table.addCell("Grupa Zywionch Kod");
				      table.addCell("c03");
                                      table.addCell("c04");
                                      table.addCell("c05");
                                      table.addCell("c06");
                                      table.addCell("c07");
                                      table.addCell("c08");
                                      table.addCell("c09");
                                      table.addCell("c10");
                                      table.addCell("c11");
                                      table.addCell("c12");
                                      table.addCell("c13");
                                      table.addCell("c14");

				      table.setSpacingBefore(30.0f);       // Space Before table starts, like margin-top in CSS
				      table.setSpacingAfter(30.0f);        // Space After table starts, like margin-Bottom in CSS	
                                      
                                      // row 
                                      for ( SprWartDzialalnosciKuchniDTO swdK : listaStanWartosciowy )
                                      {
                                         PdfPCell cell01 = new PdfPCell (new Paragraph (swdK.getGrupaZywionych(), myFont)); 
                                         table.addCell( cell01 );
                                         table.addCell( swdK.getGrupaZywionychKod());
                                         table.addCell( "" ); //null
                                         table.addCell( "" ); //null
                                         table.addCell( "" ); //null
                                         table.addCell( "" ); //null
                                         table.addCell( "" );
                                         table.addCell( "" );
                                         table.addCell( "" );
                                         table.addCell( "" );
                                         table.addCell( "" );
                                         table.addCell( "" );
                                         table.addCell( "" );
                                         table.addCell( "" );
                                      }
                                      
 
			 //Inserting List in PDF
				      List list=new List(true,30);
			              list.add(new ListItem("Java4s"));
				      list.add(new ListItem("Php4s"));
				      list.add(new ListItem("Some Thing..."));	
                                      
                                      for ( SprWartDzialalnosciKuchniDTO swdK : listaStanWartosciowy )
                                      {
                                         list.add( swdK.getGrupaZywionychKod());
                                      }
                                      
 
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
 
                    document.add(new Paragraph("Dear Java4s.com"));
                    document.add(new Paragraph("k.skowronski"));
                    document.add(new Paragraph(naDzienRap.toString()));
	                document.add(new Paragraph("Document Generated On - "+new Date().toString()));	
 
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
