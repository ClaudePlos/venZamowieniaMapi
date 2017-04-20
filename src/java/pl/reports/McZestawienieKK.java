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
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import org.zkoss.zul.Filedownload;
import pl.models.reports.DietaDTO;
import pl.models.reports.GzDTO;
import pl.models.reports.RapMcZestawienieKkDTO;
import pl.models.reports.StanZywDzienPosilekKkDTO;
import pl.session.ServiceReports;

/**
 *
 * @author m.marczak
 */
public class McZestawienieKK {
    
    @EJB
    ServiceReports serviceReports = ServiceReports.getInstance();
    
    
    public void McZestawienieKK()
    {
        
    }
    
    
    
    public void zapiszPDF( String dzienOd, String dzienDo, BigDecimal kierunekKosztow, String posilek, String kkNazwa ) throws IOException, Exception{
        
        
        System.out.print("Pobierma dane w McZestawienieKK");
        
        
        List<RapMcZestawienieKkDTO> stanyKK = serviceReports.pobierzMcZestawienieKK(dzienOd, dzienDo, kierunekKosztow, posilek);
        
        
        
        
        
        
        
        
        
        
        
        
        
        // 02. Generation raport 
            File f = new File("McZestawienie " + kkNazwa + ".pdf");
            
              OutputStream file = new FileOutputStream(f); //
            // OutputStream file = new FileOutputStream(new File("//Users//Claude//Desktop//PDF_Java4s.pdf"));
	          Document document = new Document(PageSize.A4.rotate());
	          PdfWriter.getInstance(document, file);
                  
                  Filedownload.save(f, "application/pdf");
 
			//Inserting Image in PDF
			    // Image image = Image.getInstance ("src/pdf/java4s.png");
			    // image.scaleAbsolute(120f, 60f);//image width,height	
 
			//Inserting Table in PDF
			     PdfPTable table=new PdfPTable( 33 ); // number of column
                             
                             table.setTotalWidth(790);
                             table.setLockedWidth(true);
                             
                             //BaseFont.CP1250
                            BaseFont bf = BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED ); // na polskie zniaki
                            
                            //Font fontbold = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
                            
                            Font myFont_Naglowek = new Font(bf, 10); //rozmiar czcionki
                            Font myFont_Posilek = new Font(bf, 8);  //rozmiar czcionki
                            Font myFont = new Font(bf, 8);           //rozmiar czcionki
                            
                            
                            Font regular = new Font(Font.FontFamily.HELVETICA, 8);
                            Font bold = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);

                            
                            
                            PdfPCell cell0 = new PdfPCell (new Paragraph ("Oddziały", myFont_Naglowek));
 
				      cell0.setColspan( 1 ); // connect column to one 
				      cell0.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cell0.setPadding (10.0f);
				     // cell.setBackgroundColor (new BaseColor (140, 221, 8));	
				      table.addCell(cell0); 
                                      
                                      
                                      
                                      
	                              PdfPCell cell = new PdfPCell (new Paragraph ("Dni", myFont_Naglowek));
 
				      cell.setColspan( 31 ); // connect column to one 
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cell.setPadding (10.0f);
				      cell.setBackgroundColor (new BaseColor (140, 221, 8));
                                      
                                      
                                      
                                      PdfPCell cell2 = new PdfPCell (new Paragraph ("Suma", myFont_Naglowek));
 
				      cell2.setColspan( 1 ); // connect column to one 
				      cell2.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cell2.setPadding (10.0f);
				      cell2.setBackgroundColor (new BaseColor (140, 221, 8));	
                                      
                            

                                      
                                      
                                      
                                      
                                      float[] fl = {2f, 1f, };
                                      //table.setWidths(fl);
                                      //Array t1 = "";
                                      int[] size = new int[ 33 ];
                                      
                                      for (int i = 0; i < size.length; i++) {
                                          if ( i == 0 )
                                          {
                                            size[i] = 250;  
                                          }
                                          else
                                          {
                                             size[i] = 30; 
                                          }    
                                      }
                                      
                                      table.setWidths( size );
				      table.addCell(cell);						               
                                      // name of column
                                      
                                      
                                      
                            
                                         
                                      
                                      
                                    for ( RapMcZestawienieKkDTO s : stanyKK )
                                    {
                                         
                                        PdfPCell cell00 = new PdfPCell( new Paragraph( s.getGz() , myFont)); // pierwotna wersja nie odporna na nulle // PdfPCell cell02 = new PdfPCell( new Paragraph(stanZywionychOkres.getSn3().toString() + "/" + sn3m , myFont));
                                        cell00.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell00 );
                                        
                                        PdfPCell cell01 = new PdfPCell( new Paragraph( s.getD01().toString() , myFont));
                                        cell01.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell01 );
                                        
                                        PdfPCell cell02 = new PdfPCell( new Paragraph( s.getD02().toString() , myFont));
                                        cell02.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell02 );
                                        
                                        PdfPCell cell03 = new PdfPCell( new Paragraph( s.getD03().toString() , myFont));
                                        cell03.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell03 );
                                        
                                        PdfPCell cell04 = new PdfPCell( new Paragraph( s.getD04().toString() , myFont));
                                        cell04.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell04 );
                                        
                                        PdfPCell cell05 = new PdfPCell( new Paragraph( s.getD05().toString() , myFont));
                                        cell05.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell05 );
                                        
                                        PdfPCell cell06 = new PdfPCell( new Paragraph( s.getD06().toString() , myFont));
                                        cell06.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell06 );
                                        
                                        PdfPCell cell07 = new PdfPCell( new Paragraph( s.getD07().toString() , myFont));
                                        cell07.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell07 );
                                        
                                        PdfPCell cell08 = new PdfPCell( new Paragraph( s.getD08().toString() , myFont));
                                        cell08.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell08 );
                                        
                                        PdfPCell cell09 = new PdfPCell( new Paragraph( s.getD09().toString() , myFont));
                                        cell09.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell09 );
                                        
                                        PdfPCell cell10 = new PdfPCell( new Paragraph( s.getD10().toString() , myFont));
                                        cell10.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell10 );
                                        
                                        PdfPCell cell11 = new PdfPCell( new Paragraph( s.getD11().toString() , myFont));
                                        cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell11 );
                                        
                                        PdfPCell cell12 = new PdfPCell( new Paragraph( s.getD12().toString() , myFont));
                                        cell12.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell12 );
                                        
                                        PdfPCell cell13 = new PdfPCell( new Paragraph( s.getD13().toString() , myFont));
                                        cell13.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell13 );
                                        
                                        PdfPCell cell14 = new PdfPCell( new Paragraph( s.getD14().toString() , myFont));
                                        cell14.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell14 );
                                        
                                        PdfPCell cell15 = new PdfPCell( new Paragraph( s.getD15().toString() , myFont));
                                        cell15.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell15 );
                                        
                                        PdfPCell cell16 = new PdfPCell( new Paragraph( s.getD16().toString() , myFont));
                                        cell16.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell16 );
                                        
                                        PdfPCell cell17 = new PdfPCell( new Paragraph( s.getD17().toString() , myFont));
                                        cell17.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell17 );
                                        
                                        PdfPCell cell18 = new PdfPCell( new Paragraph( s.getD18().toString() , myFont));
                                        cell18.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell18 );
                                        
                                        PdfPCell cell19 = new PdfPCell( new Paragraph( s.getD19().toString() , myFont));
                                        cell19.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell19 );
                                        
                                        PdfPCell cell20 = new PdfPCell( new Paragraph( s.getD20().toString() , myFont));
                                        cell20.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell20 );
                                        
                                        PdfPCell cell21 = new PdfPCell( new Paragraph( s.getD21().toString() , myFont));
                                        cell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell21 );
                                        
                                        PdfPCell cell22 = new PdfPCell( new Paragraph( s.getD22().toString() , myFont));
                                        cell22.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell22 );
                                        
                                        PdfPCell cell23 = new PdfPCell( new Paragraph( s.getD23().toString() , myFont));
                                        cell23.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell23 );
                                        
                                        PdfPCell cell24 = new PdfPCell( new Paragraph( s.getD24().toString() , myFont));
                                        cell24.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell24 );
                                        
                                        PdfPCell cell25 = new PdfPCell( new Paragraph( s.getD25().toString() , myFont));
                                        cell25.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell25 );
                                        
                                        PdfPCell cell26 = new PdfPCell( new Paragraph( s.getD26().toString() , myFont));
                                        cell26.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell26 );
                                        
                                        PdfPCell cell27 = new PdfPCell( new Paragraph( s.getD27().toString() , myFont));
                                        cell27.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell27 );
                                        
                                        PdfPCell cell28 = new PdfPCell( new Paragraph( s.getD28().toString() , myFont));
                                        cell28.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell28 );
                                        
                                        PdfPCell cell29 = new PdfPCell( new Paragraph( s.getD29().toString() , myFont));
                                        cell29.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell29 );
                                        
                                        PdfPCell cell30 = new PdfPCell( new Paragraph( s.getD30().toString() , myFont));
                                        cell30.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell30 );
                                        
                                        PdfPCell cell31 = new PdfPCell( new Paragraph( s.getD31().toString() , myFont));
                                        cell31.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell31 );
                                        
                                        
                                    }
                                      
                                      
                                      
                                  
                                      
                                      
                                      
                                      
                                      

                         
                                  
        
        
        com.itextpdf.text.List list=new com.itextpdf.text.List(true,30);
        
        
        document.open();//PDF document opened........			       
 
        //document.add(image);

        document.add(Chunk.NEWLINE);   //Something like in HTML :-)
 
                    
        document.add(new Paragraph("Vendi Servis Sp. z o.o. " + dzienOd.toString(), myFont_Posilek )); 
        document.add(new Paragraph( kkNazwa + " Na okres od " + dzienOd.toString() + " do " + dzienDo.toString() + " " + posilek, myFont_Posilek ));

          //  document.add(new Paragraph("Document Generated On - "+new Date().toString()));	
          
        document.add(Chunk.NEWLINE);  
 
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
