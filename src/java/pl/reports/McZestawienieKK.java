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
import java.math.BigInteger;
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
                            Font myFont = new Font(bf, 7);           //rozmiar czcionki
                            
                            
                            Font regular = new Font(Font.FontFamily.HELVETICA, 8);
                            Font bold = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);

                            
                            
                            PdfPCell cell_Oddzialy = new PdfPCell (new Paragraph ("Oddziały", myFont_Naglowek));
 
				      cell_Oddzialy.setColspan( 1 ); // connect column to one 
				      cell_Oddzialy.setHorizontalAlignment (Element.ALIGN_CENTER); //wysrodkowanie w poziomie
				      //cell_Oddzialy.setPadding (10.0f); //wysokosc
                                      cell_Oddzialy.setVerticalAlignment( Element.ALIGN_MIDDLE ); //wysrodkowanie w pionie
				      cell_Oddzialy.setBackgroundColor (new BaseColor (140, 221, 8));	
				      table.addCell(cell_Oddzialy); 
                                      

                                      
                                      
	                              PdfPCell cell_Dni = new PdfPCell (new Paragraph ("Dni", myFont_Naglowek));
 
				      cell_Dni.setColspan( 31 ); // connect column to one 
				      cell_Dni.setHorizontalAlignment (Element.ALIGN_CENTER); //wysrodkowanie w poziomie
				      //cell_Dni.setPadding (10.0f); //wysokosc
                                      cell_Dni.setVerticalAlignment( Element.ALIGN_MIDDLE ); //wysrodkowanie w pionie
				      cell_Dni.setBackgroundColor (new BaseColor (140, 221, 8));
                                      
                                      
                                      
                                      float[] fl = {2f, 1f, };
                                      //table.setWidths(fl);
                                      //Array t1 = "";
                                      int[] size = new int[ 33 ];
                                      
                                      for (int i = 0; i < size.length; i++) {
                                          if ( i == 0 )
                                          {
                                            size[i] = 200;  //szerokosc pierwszej kolumny
                                          }
                                          else if(i == size.length-1){ //szerokosc ostatniej kolumny
                                              size[i] = 60;
                                          }
                                          else
                                          {
                                             size[i] = 40; //szerokosc pozostalych kolumn
                                          }    
                                      }
                                      
                                      table.setWidths( size );
				      table.addCell(cell_Dni);						               
                                      // name of column
                                      
                                      
                                      
                                      PdfPCell cell_Suma = new PdfPCell (new Paragraph ("Suma", myFont_Naglowek));

                                      cell_Suma.setColspan( 1 ); // connect column to one 
                                      cell_Suma.setHorizontalAlignment (Element.ALIGN_CENTER); //wysrodkowanie w poziomie
                                      //cell_Suma.setPadding (10.0f); //wysokosc
                                      cell_Suma.setVerticalAlignment( Element.ALIGN_MIDDLE ); //wysrodkowanie w pionie
                                      cell_Suma.setRotation(90); //obrot w kolumnie
                                      cell_Suma.setBackgroundColor (new BaseColor (140, 221, 8));	
                                      table.addCell(cell_Suma);
                                      
                                      
                                      
                                     //drugi wiersz 
                                     //pusta pod Oddzialy
                                    PdfPCell cell001 = new PdfPCell( new Paragraph( "" , myFont));
                                    cell001.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    table.addCell( cell001 );
                                      
                                    
                                    //generowane dni miesiaca
                                    for (int i = 0; i < 31; i++) {
                                        
                                        PdfPCell cell002 = new PdfPCell( new Paragraph( Integer.toString( i + 1 ) , myFont));                                        
                                        cell002.setHorizontalAlignment (Element.ALIGN_CENTER); //wysrodkowanie w poziomie                                     
                                        cell002.setVerticalAlignment (Element.ALIGN_MIDDLE ); //wysrodkowanie w pionie
                                        table.addCell( cell002 );   
                                    }
                                    
                                    
                                    //pusta pod Suma
                                    PdfPCell cell003 = new PdfPCell( new Paragraph( " " , myFont));
                                    cell003.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    table.addCell( cell003 );
                                         
                                     
                                    BigDecimal sumD01 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD02 = new BigDecimal(BigInteger.ZERO);                                   
                                    BigDecimal sumD03 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD04 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD05 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD06 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD07 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD08 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD09 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD10 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD11 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD12 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD13 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD14 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD15 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD16 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD17 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD18 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD19 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD20 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD21 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD22 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD23 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD24 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD25 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD26 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD27 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD28 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD29 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD30 = new BigDecimal(BigInteger.ZERO);
                                    BigDecimal sumD31 = new BigDecimal(BigInteger.ZERO); 
                                    BigDecimal sumSuma = new BigDecimal(BigInteger.ZERO);
                                      
                                    for ( RapMcZestawienieKkDTO s : stanyKK )
                                    {
                                         
                                        PdfPCell cell00 = new PdfPCell( new Paragraph( s.getGz() , myFont));
                                        cell00.setHorizontalAlignment(Element.ALIGN_LEFT);
                                        table.addCell( cell00 );
                                        
                                        
                                        
                                        PdfPCell cell01 = new PdfPCell( new Paragraph( s.getD01().toString() , myFont));
                                        cell01.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell01 );
                                        
                                        sumD01 = sumD01.add( s.getD01() );
                                        
                                        PdfPCell cell02 = new PdfPCell( new Paragraph( s.getD02().toString() , myFont));
                                        cell02.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell02 );
                                        
                                        sumD02 = sumD02.add( s.getD02() );
                                        
                                        PdfPCell cell03 = new PdfPCell( new Paragraph( s.getD03().toString() , myFont));
                                        cell03.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell03 );
                                        
                                        sumD03 = sumD03.add( s.getD03() );
                                        
                                        PdfPCell cell04 = new PdfPCell( new Paragraph( s.getD04().toString() , myFont));
                                        cell04.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell04 );
                                        
                                        sumD04 = sumD04.add( s.getD04() );
                                        
                                        PdfPCell cell05 = new PdfPCell( new Paragraph( s.getD05().toString() , myFont));
                                        cell05.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell05 );
                                        
                                        sumD05 = sumD05.add( s.getD05() );
                                        
                                        PdfPCell cell06 = new PdfPCell( new Paragraph( s.getD06().toString() , myFont));
                                        cell06.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell06 );
                                        
                                        sumD06 = sumD06.add( s.getD06() );
                                        
                                        PdfPCell cell07 = new PdfPCell( new Paragraph( s.getD07().toString() , myFont));
                                        cell07.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell07 );
                                        
                                        sumD07 = sumD07.add( s.getD07() );
                                        
                                        PdfPCell cell08 = new PdfPCell( new Paragraph( s.getD08().toString() , myFont));
                                        cell08.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell08 );
                                        
                                        sumD08 = sumD08.add( s.getD08() );
                                        
                                        PdfPCell cell09 = new PdfPCell( new Paragraph( s.getD09().toString() , myFont));
                                        cell09.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell09 );
                                        
                                        sumD09 = sumD09.add( s.getD09() );
                                        
                                        PdfPCell cell10 = new PdfPCell( new Paragraph( s.getD10().toString() , myFont));
                                        cell10.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell10 );
                                        
                                        sumD10 = sumD10.add( s.getD10() );
                                        
                                        PdfPCell cell11 = new PdfPCell( new Paragraph( s.getD11().toString() , myFont));
                                        cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell11 );
                                        
                                        sumD11 = sumD11.add( s.getD11() );
                                        
                                        PdfPCell cell12 = new PdfPCell( new Paragraph( s.getD12().toString() , myFont));
                                        cell12.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell12 );
                                        
                                        sumD12 = sumD12.add( s.getD12() );
                                        
                                        PdfPCell cell13 = new PdfPCell( new Paragraph( s.getD13().toString() , myFont));
                                        cell13.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell13 );
                                        
                                        sumD13 = sumD13.add( s.getD13() );
                                        
                                        PdfPCell cell14 = new PdfPCell( new Paragraph( s.getD14().toString() , myFont));
                                        cell14.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell14 );
                                        
                                        sumD14 = sumD14.add( s.getD14() );
                                        
                                        PdfPCell cell15 = new PdfPCell( new Paragraph( s.getD15().toString() , myFont));
                                        cell15.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell15 );
                                        
                                        sumD15 = sumD15.add( s.getD15() );
                                        
                                        PdfPCell cell16 = new PdfPCell( new Paragraph( s.getD16().toString() , myFont));
                                        cell16.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell16 );
                                        
                                        sumD16 = sumD16.add( s.getD16() );
                                        
                                        PdfPCell cell17 = new PdfPCell( new Paragraph( s.getD17().toString() , myFont));
                                        cell17.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell17 );
                                        
                                        sumD17 = sumD17.add( s.getD17() );
                                        
                                        PdfPCell cell18 = new PdfPCell( new Paragraph( s.getD18().toString() , myFont));
                                        cell18.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell18 );
                                        
                                        sumD18 = sumD18.add( s.getD18() );
                                        
                                        PdfPCell cell19 = new PdfPCell( new Paragraph( s.getD19().toString() , myFont));
                                        cell19.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell19 );
                                        
                                        sumD19 = sumD19.add( s.getD19() );
                                        
                                        PdfPCell cell20 = new PdfPCell( new Paragraph( s.getD20().toString() , myFont));
                                        cell20.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell20 );
                                        
                                        sumD20 = sumD20.add( s.getD20() );
                                        
                                        PdfPCell cell21 = new PdfPCell( new Paragraph( s.getD21().toString() , myFont));
                                        cell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell21 );
                                        
                                        sumD21 = sumD21.add( s.getD21() );
                                        
                                        PdfPCell cell22 = new PdfPCell( new Paragraph( s.getD22().toString() , myFont));
                                        cell22.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell22 );
                                        
                                        sumD22 = sumD22.add( s.getD22() );
                                        
                                        PdfPCell cell23 = new PdfPCell( new Paragraph( s.getD23().toString() , myFont));
                                        cell23.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell23 );
                                        
                                        sumD23 = sumD23.add( s.getD23() );
                                        
                                        PdfPCell cell24 = new PdfPCell( new Paragraph( s.getD24().toString() , myFont));
                                        cell24.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell24 );
                                        
                                        sumD24 = sumD24.add( s.getD24() );
                                        
                                        PdfPCell cell25 = new PdfPCell( new Paragraph( s.getD25().toString() , myFont));
                                        cell25.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell25 );
                                        
                                        sumD25 = sumD25.add( s.getD25() );
                                        
                                        PdfPCell cell26 = new PdfPCell( new Paragraph( s.getD26().toString() , myFont));
                                        cell26.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell26 );
                                        
                                        sumD26 = sumD26.add( s.getD26() );
                                        
                                        PdfPCell cell27 = new PdfPCell( new Paragraph( s.getD27().toString() , myFont));
                                        cell27.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell27 );
                                        
                                        sumD27 = sumD27.add( s.getD27() );
                                        
                                        PdfPCell cell28 = new PdfPCell( new Paragraph( s.getD28().toString() , myFont));
                                        cell28.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell28 );
                                        
                                        sumD28 = sumD28.add( s.getD28() );
                                        
                                        PdfPCell cell29 = new PdfPCell( new Paragraph( s.getD29().toString() , myFont));
                                        cell29.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell29 );
                                        
                                        sumD29 = sumD29.add( s.getD29() );
                                        
                                        PdfPCell cell30 = new PdfPCell( new Paragraph( s.getD30().toString() , myFont));
                                        cell30.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell30 );
                                        
                                        sumD30 = sumD30.add( s.getD30() );
                                        
                                        PdfPCell cell31 = new PdfPCell( new Paragraph( s.getD31().toString() , myFont));
                                        cell31.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                        table.addCell( cell31 );
                                        
                                        sumD31 = sumD31.add( s.getD31() );
                                        
                                        //do sumy
                                        BigDecimal sum = s.getD01().add(s.getD02().add(s.getD03().add(s.getD04().add(s.getD05().add(s.getD06().add(s.getD07().add(s.getD08().add(s.getD09().add(s.getD10().add(s.getD11().add(s.getD12().add(s.getD13().add(s.getD14().add(s.getD15().add(s.getD16().add(s.getD17().add(s.getD18().add(s.getD19().add(s.getD20().add(s.getD21().add(s.getD22().add(s.getD23().add(s.getD24().add(s.getD25().add(s.getD26().add(s.getD27().add(s.getD28().add(s.getD29().add(s.getD30().add(s.getD31()))))))))))))))))))))))))))))));
                                                
                                        PdfPCell cell32 = new PdfPCell( new Paragraph( sum.toString() , myFont));
                                        cell32.setHorizontalAlignment(Element.ALIGN_LEFT);
                                        table.addCell( cell32 );
                                        
                                        sumSuma = sumSuma.add(sum); //suma wszystkich sum
                                }
                                  
                                PdfPCell d00 = new PdfPCell (new Paragraph ( "Razem", bold ));
                                d00.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                table.addCell( d00 );     
                                                                        
                                PdfPCell d01 = new PdfPCell (new Paragraph ( sumD01.toString(), bold ));
                                d01.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d01 ); 
                                    
                                PdfPCell d02 = new PdfPCell (new Paragraph ( sumD02.toString(), bold ));
                                d02.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d02 ); 
                                    
                                PdfPCell d03 = new PdfPCell (new Paragraph ( sumD03.toString(), bold ));
                                d03.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d03 ); 
                                    
                                PdfPCell d04 = new PdfPCell (new Paragraph ( sumD04.toString(), bold ));
                                d04.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d04 ); 
                                    
                                PdfPCell d05 = new PdfPCell (new Paragraph ( sumD05.toString(), bold ));
                                d05.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d05 ); 
                                    
                                PdfPCell d06 = new PdfPCell (new Paragraph ( sumD06.toString(), bold ));
                                d06.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d06 ); 
                                    
                                PdfPCell d07 = new PdfPCell (new Paragraph ( sumD07.toString(), bold ));
                                d07.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d07 ); 
                                    
                                PdfPCell d08 = new PdfPCell (new Paragraph ( sumD08.toString(), bold ));
                                d08.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d08 ); 
                                    
                                PdfPCell d09 = new PdfPCell (new Paragraph ( sumD09.toString(), bold ));
                                d09.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d09 ); 
                                    
                                PdfPCell d10 = new PdfPCell (new Paragraph ( sumD10.toString(), bold ));
                                d10.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d10 ); 
                                    
                                PdfPCell d11 = new PdfPCell (new Paragraph ( sumD11.toString(), bold ));
                                d11.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d11 ); 
                                    
                                PdfPCell d12 = new PdfPCell (new Paragraph ( sumD12.toString(), bold ));
                                d12.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d12 ); 
                                    
                                PdfPCell d13 = new PdfPCell (new Paragraph ( sumD13.toString(), bold ));
                                d13.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d13 ); 
                                    
                                PdfPCell d14 = new PdfPCell (new Paragraph ( sumD14.toString(), bold ));
                                d14.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d14 ); 
                                    
                                PdfPCell d15 = new PdfPCell (new Paragraph ( sumD15.toString(), bold ));
                                d15.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d15 ); 
                                    
                                PdfPCell d16 = new PdfPCell (new Paragraph ( sumD16.toString(), bold ));
                                d16.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d16 ); 
                                    
                                PdfPCell d17 = new PdfPCell (new Paragraph ( sumD17.toString(), bold ));
                                d17.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d17 ); 
                                    
                                PdfPCell d18 = new PdfPCell (new Paragraph ( sumD18.toString(), bold ));
                                d18.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d18 ); 
                                    
                                PdfPCell d19 = new PdfPCell (new Paragraph ( sumD19.toString(), bold ));
                                d19.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d19 ); 
                                    
                                PdfPCell d20 = new PdfPCell (new Paragraph ( sumD20.toString(), bold ));
                                d20.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d20 ); 
                                    
                                PdfPCell d21 = new PdfPCell (new Paragraph ( sumD21.toString(), bold ));
                                d21.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d21 ); 
                                    
                                PdfPCell d22 = new PdfPCell (new Paragraph ( sumD22.toString(), bold ));
                                d22.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d22 ); 
                                    
                                PdfPCell d23 = new PdfPCell (new Paragraph ( sumD23.toString(), bold ));
                                d23.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d23 ); 
                                    
                                PdfPCell d24 = new PdfPCell (new Paragraph ( sumD24.toString(), bold ));
                                d24.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d24 ); 
                                    
                                PdfPCell d25 = new PdfPCell (new Paragraph ( sumD25.toString(), bold ));
                                d25.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d25 ); 
                                    
                                PdfPCell d26 = new PdfPCell (new Paragraph ( sumD26.toString(), bold ));
                                d26.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d26 ); 
                                    
                                PdfPCell d27 = new PdfPCell (new Paragraph ( sumD27.toString(), bold ));
                                d27.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d27 ); 
                                    
                                PdfPCell d28 = new PdfPCell (new Paragraph ( sumD28.toString(), bold ));
                                d28.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d28 ); 
                                    
                                PdfPCell d29 = new PdfPCell (new Paragraph ( sumD29.toString(), bold ));
                                d29.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d29 ); 
                                    
                                PdfPCell d30 = new PdfPCell (new Paragraph ( sumD30.toString(), bold ));
                                d30.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d30 ); 
                                    
                                PdfPCell d31 = new PdfPCell (new Paragraph ( sumD31.toString(), bold ));
                                d31.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d31 ); 
                                
                                PdfPCell d32 = new PdfPCell (new Paragraph ( sumSuma.toString(), bold ));
                                d32.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table.addCell( d32 ); 
				      
                           
                                         
                                         
                                         
                                         
        
        com.itextpdf.text.List list=new com.itextpdf.text.List(true,30);
        
        
        document.open();//PDF document opened........			       
 
        //document.add(image);

        document.add(Chunk.NEWLINE);   //Something like in HTML :-)
 
                    
        document.add(new Paragraph("Vendi Servis Sp. z o.o. " , myFont_Posilek )); 
        document.add(new Paragraph( kkNazwa + " Na okres od: " + dzienOd.toString() + " do: " + dzienDo.toString() + " Posilek: " + posilek, myFont_Posilek ));

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
