/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.reports;

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
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import org.zkoss.zul.Filedownload;
import pl.models.reports.GzDTO;
import pl.models.reports.StanZywDzienPosilekKkDTO;
import pl.session.ServiceReports;

/**
 *
 * @author k.skowronski
 */
public class IlzywWgOddPodDiety {
    
    @EJB
    ServiceReports serviceReports = ServiceReports.getInstance();
    
    
    public void IlzywWgOddPodDiety()
    {
        
    }
    
    public void zapiszPDF( String okres ) throws IOException, Exception{
        
        System.out.print("Pobierma dane w IlzywWgOddPodDiety");
        
        List<StanZywDzienPosilekKkDTO> stanyKK = serviceReports.pobierzStanZywionychDzienPosilekKK(okres, 1231);
        
        List<GzDTO> gzList = new ArrayList<GzDTO>();
        
        for ( StanZywDzienPosilekKkDTO s : stanyKK )
        {
            boolean spr = gzList.stream().anyMatch( rd -> rd.getGzNazwa().equals( s.getGz() ));
            
            if( spr == false )
            {
               GzDTO g = new GzDTO();
               g.setGzNazwa( s.getGz() );
               gzList.add(g);
            }
            
            
        }
        
        
        
        
        
        
        // 02. Generation raport 
            File f = new File("Zestawienie01.pdf");
            
              OutputStream file = new FileOutputStream(f); //
            // OutputStream file = new FileOutputStream(new File("//Users//Claude//Desktop//PDF_Java4s.pdf"));
	          Document document = new Document(PageSize.A4.rotate());
	          PdfWriter.getInstance(document, file);
                  
                  Filedownload.save(f, "application/pdf");
 
			//Inserting Image in PDF
			    // Image image = Image.getInstance ("src/pdf/java4s.png");
			    // image.scaleAbsolute(120f, 60f);//image width,height	
 
			//Inserting Table in PDF
			     PdfPTable table=new PdfPTable( gzList.size() + 1 ); // number of column
                             
                             table.setTotalWidth(790);
                             table.setLockedWidth(true);
                             
                             
                            BaseFont bf = BaseFont.createFont();
                            Font myFont_Naglowek = new Font(bf, 12); //rozmiar czcionki
                            Font myFont_Posilek = new Font(bf, 10);  //rozmiar czcionki
                            Font myFont = new Font(bf, 8);           //rozmiar czcionki
                            
                            
                            Font regular = new Font(Font.FontFamily.HELVETICA, 8);
                            Font bold = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);

                            
                            
                            PdfPCell cell0 = new PdfPCell (new Paragraph ("", myFont_Naglowek));
 
				      cell0.setColspan( 1  ); // connect column to one 
				      cell0.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cell0.setPadding (10.0f);
				     // cell.setBackgroundColor (new BaseColor (140, 221, 8));	
				      table.addCell(cell0); 
                                      
	                     PdfPCell cell = new PdfPCell (new Paragraph ("Oddziały", myFont_Naglowek));
 
				      cell.setColspan( gzList.size()  ); // connect column to one 
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cell.setPadding (10.0f);
				      cell.setBackgroundColor (new BaseColor (140, 221, 8));	

                                      float[] fl = {2f, 1f, };
                                      //table.setWidths(fl);
                                      //Array t1 = "";
                                      int[] size = new int[ gzList.size() + 1 ];
                                      
                                      for (int i = 0; i < size.length; i++) {
                                          if ( i == 0 )
                                          {
                                            size[i] = 200;  
                                          }
                                          else
                                          {
                                             size[i] = 50; 
                                          }    
                                      }
                                      
                                      table.setWidths( size );
				      table.addCell(cell);						               
                                      // name of column
                                      
                                      
                                      PdfPCell cell_1 = new PdfPCell (new Paragraph ( "Diety" , myFont_Posilek));
                                         cell_1.setColspan(1); // connect column to one 
                                         cell_1.setHorizontalAlignment (Element.ALIGN_CENTER);
                                         cell_1.setVerticalAlignment( Element.ALIGN_MIDDLE );
                                         //cell_1.setBorderWidth(5f);
                                         table.addCell(cell_1); 
                                      
                                      // rysujemuy 1 wiersz -> KK   
                                      for ( GzDTO gz : gzList )
                                      {
                                         PdfPCell cell_01 = new PdfPCell (new Paragraph ( gz.getGzNazwa() , myFont_Posilek));
                                         cell_01.setColspan(1); // connect column to one 
                                         cell_01.setHorizontalAlignment (Element.ALIGN_CENTER);
                                         cell_01.setRotation(90);
                                         table.addCell(cell_01); 
                                      }
        
        
        
        
        
        com.itextpdf.text.List list=new com.itextpdf.text.List(true,30);
        
        
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
        
        
        
    }
    
    
}
