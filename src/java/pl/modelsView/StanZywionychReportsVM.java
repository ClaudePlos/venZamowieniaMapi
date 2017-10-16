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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;
import pl.excel.ExcelExporter;
import pl.models.NapMapowaniaCenyVO;
import pl.models.SprWartDzialalnosciKuchniDTO;
import pl.models.StanZywionychMMRapRozDTO;
import pl.models.StanZywionychNaDzienDTO;
import pl.reports.IlzywWgOddPodDiety;
import pl.reports.McZestawienieKK;
import pl.session.ServiceFacade;

/**
 *
 * @author k.skowronski
 */
public class StanZywionychReportsVM {

    private static String rap3 = "Zestawienie finansowe";
    
    final JPanel panel = new JPanel();
    
    @EJB 
    ServiceFacade serviceFacade = ServiceFacade.getInstance();
    
    java.util.List<NapMapowaniaCenyVO> napMapowaniaCenyList = new ArrayList<NapMapowaniaCenyVO>();
    
    
    @Command
    @NotifyChange("stanyZywionychReprots")
    public void stanZywionychReports( @BindingParam("kierKosztow") int kierKosztowId, @BindingParam("kierKosztowNazwa") String kierKosztowNazwa ) throws IOException, Exception{
        
        Window window = (Window)Executions.createComponents(
                "/orderReports/report_for_plock_hospital.zul", null, null);
        window.doModal();
        
        
        Vbox vb01 = new Vbox();
        
        
        ArrayList listZest = new ArrayList();
        listZest.add("Ilość żywionych wg oddziałów z podziałem na diety");
        //listZest.add("Wydanie posiłków wg oddziałów w miesiącu");  //celeowo wylaczone, dla Onkologii zrobi sie drugie, na wzor finansowego
        //zestawienie przestalo dzialac, z windowsa dziala OK, z linuksa wszystkie dane wrzuca w jeden dzien, 
        
        //tymczasowo dla onkologi
        if ( kierKosztowId == 1322 || kierKosztowId == 1321 )
            listZest.add(rap3);
        
        ListModelList lmZest = new ListModelList(listZest);
        
        Hbox hb01 = new Hbox();
        Label l01 = new Label();
        l01.setValue("Zestawienie:");
        Combobox cmbZestawienia = new Combobox();
        cmbZestawienia.setWidth("450px");
        cmbZestawienia.setValue("Wybierz raport: ");
        cmbZestawienia.setModel( lmZest );
        
        
        
        hb01.appendChild(l01);
        hb01.appendChild(cmbZestawienia);
        
        Listbox listBoxR = new Listbox(); 
        listBoxR.setHeight("450px");
        
        Label test = new Label();
        test.setValue(" Raport dla kierunku kosztów: " + kierKosztowNazwa  ); // id mi tutaj nie potrzebne //+ " " + serviceFacade.kkRaport.getIdKierunekKosztow() );
        
        Hbox hb02 = new Hbox();
        Label l02 = new Label();
        
        l02.setValue("Data od:");
        Textbox tbOkres = new Textbox();
        tbOkres.setWidth("100px");
        Date d = new Date();
        tbOkres.setText( serviceFacade.dateToStringYYYMMDD(d) );
        
        Label l022 = new Label();
        l022.setVisible(false);
        l022.setValue("Data do:");
        Textbox tbOkresDo = new Textbox();
        tbOkresDo.setWidth("100px");
        tbOkresDo.setVisible(false);
        tbOkresDo.setText( serviceFacade.dateToStringYYYMMDD(d) );
        
        hb02.appendChild(l02);
        hb02.appendChild(tbOkres);
        hb02.appendChild(l022);
        hb02.appendChild(tbOkresDo);
        
        
        
        
        ArrayList listPosilki = new ArrayList();
        listPosilki.add("Śniadanie");
        listPosilki.add("2. śniadanie");
        listPosilki.add("Obiad");
        listPosilki.add("Podwieczorek");
        listPosilki.add("Kolacja");
        listPosilki.add("Posiłek nocny");
        
        ListModelList lmPosiliki = new ListModelList(listPosilki);
        
        Hbox hb03 = new Hbox();
        Label l03 = new Label();
        l03.setValue("Posiłek:");
        Combobox cmbPosilek = new Combobox();
        cmbPosilek.setWidth("250px");
        cmbPosilek.setId("cmbPosilek");
        cmbPosilek.setModel( lmPosiliki );
        cmbPosilek.setValue("Obiad");
        hb03.appendChild(l03);
        hb03.appendChild(cmbPosilek);
        
        
        
        cmbZestawienia.addEventListener("onChange", new EventListener() {
            public void onEvent(Event event) throws Exception {
                
                if ( cmbZestawienia.getSelectedItem().getValue().toString().equals("Wydanie posiłków wg oddziałów w miesiącu") )
                { 
                   System.out.println("2");
                   l022.setVisible(true);
                   tbOkresDo.setVisible(true);
                   
                   cmbPosilek.setVisible(true);
                   l03.setVisible(true);
                }
                else if ( cmbZestawienia.getSelectedItem().getValue().toString().equals(rap3) )
                { 
                   System.out.println(rap3);
                   cmbPosilek.setVisible(false);
                   l03.setVisible(false);  
                   l022.setVisible(false);
                   tbOkresDo.setVisible(false);
                }
                else
                {
                   l022.setVisible(false);
                   tbOkresDo.setVisible(false);
                   
                   cmbPosilek.setVisible(true);
                   l03.setVisible(true);
                }
            }
        });
        
        
        Button runExcel = new Button();
        runExcel.setLabel("Excel");
        runExcel.addEventListener("onClick", new EventListener() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                
                
                
            }
        });
        
        
        Button runPdf = new Button();
        runPdf.setLabel("Uruchom - PDF");
        runPdf.addEventListener("onClick", new EventListener() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                
         
                
                if ( cmbZestawienia.getSelectedItem().getValue().toString().equals("Ilość żywionych wg oddziałów z podziałem na diety") )
                {
                    l022.setVisible(false);
                    tbOkresDo.setVisible(false);
                    IlzywWgOddPodDiety rap01 = new IlzywWgOddPodDiety();
                    rap01.zapiszPDF( tbOkres.getValue()
                            , BigDecimal.valueOf(kierKosztowId)
                            , cmbPosilek.getSelectedItem().getValue().toString()
                            , kierKosztowNazwa );
                }
//                else if ( cmbZestawienia.getSelectedItem().getValue().toString().equals("Wydanie posiłków wg oddziałów w miesiącu") )
//                {
//                    McZestawienieKK rap02 = new McZestawienieKK();
//                    rap02.zapiszPDF( tbOkres.getValue(), tbOkresDo.getValue()
//                            , BigDecimal.valueOf(kierKosztowId)
//                            , cmbPosilek.getSelectedItem().getValue().toString()
//                            , kierKosztowNazwa);
//
//                }
                else if ( cmbZestawienia.getSelectedItem().getValue().toString().equals(rap3) )
                { 
                    // raport Fin Onkologia
                  try {
                        String okres = tbOkres.getValue().substring(0, 7);
                        System.out.println( okres + " " + kierKosztowId);
                        zapiszPDF( okres, kierKosztowId, kierKosztowNazwa );
                    } catch (Exception e) {
                        //alert();
                    }

                }
                
                
             
                
            }   
        });
        
        
        
        
        
        vb01.appendChild(hb01);
        vb01.appendChild(hb02);
        vb01.appendChild(hb03);
        vb01.appendChild(runPdf);
        vb01.appendChild(runExcel);
        vb01.appendChild(test);
        
        window.appendChild(vb01);

       // window.appendChild(listBoxR);
        
        getNapMapowaniaCeny();
 
     }   
    
    
    public void getNapMapowaniaCeny(){
        
        napMapowaniaCenyList = serviceFacade.getNapMapowaniaCeny();
        
    }
    
    public void zapiszExcel( String okres, int kkId, String kierKosztowNazwa ) throws IOException, Exception{
        java.util.List<StanZywionychMMRapRozDTO> stanZywionych = new ArrayList<StanZywionychMMRapRozDTO>();
        
        stanZywionych = serviceFacade.getDataForFinancialRaport( okres, kkId );
        
//        stanZywionych
//        JTable l1=new JTable();
//        l1.setModel(new CustMod (data));
//        DefaultListModel d1=new DefaultListModel();        
//        
//        d1.addElement(stanZywionych);
//        l1.setModel(d1);
//        
        TableModel model = null; // = l1.getModel();
        
        ExcelExporter xlsExp = new ExcelExporter();
        String ret = xlsExp.ExportTable(model);
        
        if ( ret.equals("OK") )
        {
            JOptionPane.showMessageDialog(panel, "Zapisane", "Info",JOptionPane.INFORMATION_MESSAGE);
        }        
                
    }
    
        
    //@Command
    //@NotifyChange("zapiszPDF")
    public void zapiszPDF( String okres, int kkId, String kierKosztowNazwa ) throws IOException, Exception{
       
         java.util.List<StanZywionychMMRapRozDTO> stanZywionych = new ArrayList<StanZywionychMMRapRozDTO>();
        
        try {
            // 01. preparing data 
            stanZywionych = serviceFacade.getDataForFinancialRaport(okres, kkId );
            
            
            
            // 02. Generation raport 
            File f = new File("Raport finansowy "
                    + kierKosztowNazwa + " "
                    + okres + " "                   
                    + ".pdf");
            
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

                            
 
	                     PdfPCell cell = new PdfPCell (new Paragraph ("Sprawozdanie wartosciowe z dzialalnosci kuchni: "  + kierKosztowNazwa, myFont_Naglowek));
 
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
                                      
                                      PdfPCell cell_7 = new PdfPCell (new Paragraph ("posilek nocny / zupa", myFont_Posilek));
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
                                      table.addCell("Zupa");
                                      table.addCell("Suma");

				      table.setSpacingBefore(10.0f);       // Space Before table starts, like margin-top in CSS
				      table.setSpacingAfter(10.0f);        // Space After table starts, like margin-Bottom in CSS
                                      
                                      
                                      //************************************************************************************************************
                                      
                                      //pobieram ceny
                                      //tabela nap_mapowania_ceny
                                      //sniadanie
                                        BigDecimal cS3 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 3 posiłkowa" ))
                                                                .findFirst().get().getMapcSniadanie() ;
                                        
                                        BigDecimal cS5 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 5 posiłkowa" ))
                                                                .findFirst().get().getMapcSniadanie() ;
                                        
                                        BigDecimal cS6 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 6 posiłkowa" ))
                                                                .findFirst().get().getMapcSniadanie() ;
                                        //2 sniadanie
                                        BigDecimal cDS5 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 5 posiłkowa" ))
                                                                .findFirst().get().getMapc2Sniadanie() ;
                                        
                                        BigDecimal cDS6 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 6 posiłkowa" ))
                                                                .findFirst().get().getMapc2Sniadanie();
                                        
                                        //obiad
                                        BigDecimal cO3 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 3 posiłkowa" ))
                                                                .findFirst().get().getMapcObiad();
                                        
                                        BigDecimal cO5 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 5 posiłkowa" ))
                                                                .findFirst().get().getMapcObiad() ;
                                        
                                        BigDecimal cO6 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 6 posiłkowa" ))
                                                                .findFirst().get().getMapcObiad() ;
                                        
                                        //podwieczorek
                                        BigDecimal cPO5 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 5 posiłkowa" ))
                                                                .findFirst().get().getMapcPodwieczorek() ;
                                        
                                        BigDecimal cPO6 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 6 posiłkowa" ))
                                                                .findFirst().get().getMapcPodwieczorek();
                                        
                                        //kolacja
                                        BigDecimal cK3 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 3 posiłkowa" ))
                                                                .findFirst().get().getMapcKolacja();
                                        
                                        BigDecimal cK5 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 5 posiłkowa" ))
                                                                .findFirst().get().getMapcKolacja() ;
                                        
                                        BigDecimal cK6 = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 6 posiłkowa" ))
                                                                .findFirst().get().getMapcKolacja() ;                                        
                                        
                                        //posilek nocny / zupa
                                        BigDecimal cPN = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 2 posiłkowa  (nocny zupa)" ))
                                                                .findFirst().get().getMapcPosilekNocny() ;
                                        
                                        BigDecimal cKompot = napMapowaniaCenyList.stream()
                                                                .filter((s) -> s.getMapcRodzajDieta().equals("dieta 2 posiłkowa  (nocny zupa)" ))
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
                                      
                                      
                                      //TODO Marcin
                                      // suma razem kk
                                      //deklaracja
                                      BigDecimal sumS3 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal sumS5 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal sumS6 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal sum2S5 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal sum2S6 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal sumO3 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal sumO5 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal sumO6 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal sumP5 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal sumP6 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal sumK3 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal sumK5 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal sumK6 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal sumPN = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal sumD = new BigDecimal(BigInteger.ZERO);
                                      
                                                                            
                                      //TODO Marcin
                                      //suma razem
                                      //deklaracja
                                      BigDecimal allSumS3 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal allSumS5 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal allSumS6 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal allSum2S5 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal allSum2S6 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal allSumO3 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal allSumO5 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal allSumO6 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal allSumP5 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal allSumP6 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal allSumK3 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal allSumK5 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal allSumK6 = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal allSumPN = new BigDecimal(BigInteger.ZERO);
                                      BigDecimal allSumD = new BigDecimal(BigInteger.ZERO);
                                      
                                      
                                      
                                      
                                      //TODO Marcin LOOP                                     
                                      // ROW
                                      for ( StanZywionychMMRapRozDTO stanZywionychOkres : stanZywionych )
                                      {
                                        
                                        
                                        if ( !kkName.equals(stanZywionychOkres.getKk()) )
                                        {
                                            if ( !kkName.equals("Start") )
                                            {
                                                table.addCell( new PdfPCell (new Paragraph ( "Razem " + kkName, bold )) );
                                                
                                                //TODO Marcin
                                                PdfPCell r02 = new PdfPCell (new Paragraph ( sumS3.toString(), bold ));
                                                r02.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r02 ); 
                                                
                                                PdfPCell r03 = new PdfPCell (new Paragraph ( sumS5.toString(), bold ));
                                                r03.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r03 );
                                                
                                                PdfPCell r04 = new PdfPCell (new Paragraph ( sumS6.toString(), bold ));
                                                r04.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r04 );
                                                                                                
                                                PdfPCell r05 = new PdfPCell (new Paragraph ( sum2S5.toString(), bold ));
                                                r05.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r05 );
                                                
                                                PdfPCell r06 = new PdfPCell (new Paragraph ( sum2S6.toString(), bold ));
                                                r06.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r06 );
                                                
                                                PdfPCell r07 = new PdfPCell (new Paragraph ( sumO3.toString(), bold ));
                                                r07.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r07 );
                                                
                                                PdfPCell r08 = new PdfPCell (new Paragraph ( sumO5.toString(), bold ));
                                                r08.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r08 );
                                                
                                                PdfPCell r09 = new PdfPCell (new Paragraph ( sumO6.toString(), bold ));
                                                r09.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r09 );
                                                
                                                PdfPCell r10 = new PdfPCell (new Paragraph ( sumP5.toString(), bold ));
                                                r10.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r10 );
                                                
                                                PdfPCell r11 = new PdfPCell (new Paragraph ( sumP6.toString(), bold ));
                                                r11.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r11 );
                                                
                                                PdfPCell r12 = new PdfPCell (new Paragraph ( sumK3.toString(), bold ));
                                                r12.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r12 );
                                                
                                                PdfPCell r13 = new PdfPCell (new Paragraph ( sumK5.toString(), bold ));
                                                r13.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r13 );
                                                
                                                PdfPCell r14 = new PdfPCell (new Paragraph ( sumK6.toString(), bold ));
                                                r14.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r14 );
                                                
                                                PdfPCell r15 = new PdfPCell (new Paragraph ( sumPN.toString(), bold ));
                                                r15.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r15 );
                                                
                                                PdfPCell r16 = new PdfPCell (new Paragraph ( sumD.toString(), bold ));
                                                r16.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r16 );
                                                
                                                
                                                
                                                PdfPCell r17 = new PdfPCell (new Paragraph ( 
                                                    sumS3.add( sumS5.add( sumS6 ) ).toString()
                                                        , bold ));
                                                r17.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r17 );
                                                
                                                //TODO Marcin
                                                allSumS3 = allSumS3.add(sumS3);
                                                allSumS5 = allSumS5.add(sumS5);
                                                allSumS6 = allSumS6.add(sumS6);
                                                allSum2S5 = allSum2S5.add(sum2S5);
                                                allSum2S6 = allSum2S6.add(sum2S6);                                                
                                                allSumO3 = allSumO3.add(sumO3);                                              
                                                allSumO5 = allSumO5.add(sumO5);
                                                allSumO6 = allSumO6.add(sumO6);
                                                allSumP5 = allSumP5.add(sumP5);
                                                allSumP6 = allSumP6.add(sumP6);
                                                allSumK3 = allSumK3.add(sumK3);
                                                allSumK5 = allSumK5.add(sumK5);
                                                allSumK6 = allSumK6.add(sumK6);
                                                allSumPN = allSumPN.add(sumPN);
                                                allSumD = allSumD.add(sumD);
                                                
                                                //TODO Marcin
                                                sumS3 = new BigDecimal(BigInteger.ZERO);
                                                sumS5 = new BigDecimal(BigInteger.ZERO);
                                                sumS6 = new BigDecimal(BigInteger.ZERO);
                                                sum2S5 = new BigDecimal(BigInteger.ZERO);
                                                sum2S6 = new BigDecimal(BigInteger.ZERO);
                                                sumO3 = new BigDecimal(BigInteger.ZERO);
                                                sumO5 = new BigDecimal(BigInteger.ZERO);
                                                sumO6 = new BigDecimal(BigInteger.ZERO);
                                                sumP5 = new BigDecimal(BigInteger.ZERO);
                                                sumP6 = new BigDecimal(BigInteger.ZERO);
                                                sumK3 = new BigDecimal(BigInteger.ZERO);
                                                sumK5 = new BigDecimal(BigInteger.ZERO);
                                                sumK6 = new BigDecimal(BigInteger.ZERO);
                                                sumPN = new BigDecimal(BigInteger.ZERO);
                                                sumD = new BigDecimal(BigInteger.ZERO);

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
                                         
                                         
                                         
                                         
                                         
                                        //TODO Marcin
                                        //ceny
                                        BigDecimal sn3m = BigDecimal.ZERO;
                                        BigDecimal sn5m  = BigDecimal.ZERO;
                                        BigDecimal sn6m  = BigDecimal.ZERO;
                                        BigDecimal dsn5m  = BigDecimal.ZERO;
                                        BigDecimal dsn6m  = BigDecimal.ZERO;
                                        BigDecimal ob3m = BigDecimal.ZERO;
                                        BigDecimal ob5m = BigDecimal.ZERO;
                                        BigDecimal ob6m = BigDecimal.ZERO;
                                        BigDecimal pod5m = BigDecimal.ZERO;
                                        BigDecimal pod6m = BigDecimal.ZERO;
                                        BigDecimal kol3m = BigDecimal.ZERO;
                                        BigDecimal kol5m = BigDecimal.ZERO;
                                        BigDecimal kol6m = BigDecimal.ZERO;
                                        BigDecimal pnm = BigDecimal.ZERO;
                                        BigDecimal dodm = BigDecimal.ZERO;  
                                        
                                                                                
                                        if ( stanZywionychOkres.getSn3() != null )
                                           sn3m = stanZywionychOkres.getSn3().multiply(cS3);

                                        if ( stanZywionychOkres.getSn5() != null )
                                           sn5m = stanZywionychOkres.getSn5().multiply(cS5);

                                        if ( stanZywionychOkres.getSn6() != null )
                                           sn6m = stanZywionychOkres.getSn6().multiply(cS6);

                                        if ( stanZywionychOkres.getDsn5() != null )
                                           dsn5m = stanZywionychOkres.getDsn5().multiply(cDS5);

                                        if ( stanZywionychOkres.getDsn6() != null )
                                           dsn6m = stanZywionychOkres.getDsn6().multiply(cDS6);
                     
                                        if ( stanZywionychOkres.getOb3() != null )
                                           ob3m = stanZywionychOkres.getOb3().multiply(cO3);

                                        if ( stanZywionychOkres.getOb5() != null )
                                           ob5m = stanZywionychOkres.getOb5().multiply(cO5);

                                        if ( stanZywionychOkres.getOb6() != null && !stanZywionychOkres.getOb6().equals( BigDecimal.ZERO) )
                                           ob6m = stanZywionychOkres.getOb6().multiply(cO6); 
                                         
                                        if ( stanZywionychOkres.getPod5() != null )
                                           pod5m = stanZywionychOkres.getPod5().multiply(cPO5);

                                        if ( stanZywionychOkres.getPod6() != null )
                                           pod6m = stanZywionychOkres.getPod6().multiply(cPO6);
                                        
                                        if ( stanZywionychOkres.getKol3() != null )
                                           kol3m = stanZywionychOkres.getKol3().multiply(cK3);

                                        if ( stanZywionychOkres.getKol5() != null )
                                           kol5m = stanZywionychOkres.getKol5().multiply(cK5);

                                        if ( stanZywionychOkres.getKol6() != null )
                                           kol6m = stanZywionychOkres.getKol6().multiply(cK6); 
                                        
                                        if ( stanZywionychOkres.getPn() != null )
                                           pnm = stanZywionychOkres.getPn().multiply(cPN);

                                        if ( stanZywionychOkres.getKompot() != null )
                                           dodm = stanZywionychOkres.getKompot().multiply(cKompot);
                                        
                                        
                                        
                                        
                                        
                                        
                                        
                                         
                                         //**********************************************
                                         //// wiersze gz
                                         //TODO Marcin
                                         PdfPCell cell02 = new PdfPCell( new Paragraph(sn3m.toString(), myFont)); // pierwotna wersja nie odporna na nulle // PdfPCell cell02 = new PdfPCell( new Paragraph(stanZywionychOkres.getSn3().toString() + "/" + sn3m , myFont));
                                         cell02.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell02 );
                                         
                                         PdfPCell cell03 = new PdfPCell( new Paragraph(sn5m.toString(), myFont));
                                         cell03.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell03 );
                                                                                  
                                         PdfPCell cell04 = new PdfPCell( new Paragraph(sn6m.toString(), myFont));
                                         cell04.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell04 );
                                                                                  
                                         PdfPCell cell05 = new PdfPCell( new Paragraph(dsn5m.toString(), myFont));
                                         cell05.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell05 );
                                         
                                         PdfPCell cell06 = new PdfPCell( new Paragraph(dsn6m.toString(), myFont));
                                         cell06.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell06 );
                                         
                                         //
                                         PdfPCell cell07 = new PdfPCell( new Paragraph(ob3m.toString(), myFont));
                                         cell07.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell07 );
                                         
                                         PdfPCell cell08 = new PdfPCell( new Paragraph(ob5m.toString(), myFont));
                                         cell08.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell08 );
                                         
                                         PdfPCell cell09 = new PdfPCell( new Paragraph(ob6m.toString(), myFont));
                                         cell09.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell09 );
                                         
                                         //
                                         PdfPCell cell10 = new PdfPCell( new Paragraph(pod5m.toString(), myFont));
                                         cell10.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell10 );
                                         
                                         PdfPCell cell11 = new PdfPCell( new Paragraph(pod6m.toString(), myFont));
                                         cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell11 );
                                                                                  
                                         //
                                         PdfPCell cell12 = new PdfPCell( new Paragraph(kol3m.toString(), myFont));
                                         cell12.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell12 );
                                         
                                         PdfPCell cell13 = new PdfPCell( new Paragraph(kol5m.toString(), myFont));
                                         cell13.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell13 );
                                         
                                         PdfPCell cell14 = new PdfPCell( new Paragraph(kol6m.toString(), myFont));
                                         cell14.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell14 );
                                         
                                         //
                                         PdfPCell cell15 = new PdfPCell( new Paragraph(pnm.toString(), myFont));
                                         cell15.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell15 );
                                         
                                         //ZUPA
                                         PdfPCell cell16 = new PdfPCell( new Paragraph(dodm.toString(), myFont));
                                         cell16.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell16 );
                                         
                                         //suma danych z poszczegolnych kolumn w danym wierszu do ostatniej kolumny SUMA (suma z z poszczegolnych oddzialow)
                                         PdfPCell cell17 = new PdfPCell( new Paragraph( sn3m.add(sn5m.add(sn6m.add(dsn5m.add(dsn6m.add(ob3m.add(ob5m.add(ob6m.add(pod5m.add(pod6m.add(kol3m.add(kol5m.add(kol6m.add(pnm.add(dodm)))))))))))))).toString(), bold )); //PdfPCell cell17 = new PdfPCell( new Paragraph( sn3m.add(sn5m.add(sn6m)).toString(), bold ));
                                         cell17.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                         table.addCell( cell17 );
                                        
                                   
                                        //podsumowanie
                                        //TODO Marcin
                                        sumS3 = sumS3.add(sn3m);
                                        sumS5 = sumS5.add(sn5m);
                                        sumS6 = sumS6.add(sn6m);
                                        sum2S5 = sum2S5.add(dsn5m);
                                        sum2S6 = sum2S6.add(dsn6m);
                                        sumO3 = sumO3.add(ob3m);
                                        sumO5 = sumO5.add(ob5m);
                                        sumO6 = sumO6.add(ob6m);
                                        sumP5 = sumP5.add(pod5m);
                                        sumP6 = sumP6.add(pod6m);
                                        sumK3 = sumK3.add(kol3m);
                                        sumK5 = sumK5.add(kol5m);
                                        sumK6 = sumK6.add(kol6m);
                                        sumPN = sumPN.add(pnm);
                                        sumD = sumD.add(dodm);
                                                                                  
                                         
                                         kkName =  stanZywionychOkres.getKk();
                                         
                                         
                                         
                                         //****************************************
                                         
                                         
                                         //TODO Marcin
                                         // ostatni wiersz dla danego kierunku kosztow
                                         // RAZEM - posdumowanie
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
                                                
                                                //
                                                PdfPCell r04 = new PdfPCell (new Paragraph ( sum2S5.toString(), bold ));
                                                r04.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r04 );
                                                
                                                PdfPCell r05 = new PdfPCell (new Paragraph ( sum2S6.toString(), bold ));
                                                r05.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r05 );
                                                
                                                //
                                                PdfPCell r06 = new PdfPCell (new Paragraph ( sumO3.toString(), bold ));
                                                r06.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r06 ); 
                                                
                                                PdfPCell r07 = new PdfPCell (new Paragraph ( sumO5.toString(), bold ));
                                                r07.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r07 ); 
                                                
                                                PdfPCell r08 = new PdfPCell (new Paragraph ( sumO6.toString(), bold ));
                                                r08.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r08 );
                                                
                                                //
                                                PdfPCell r09 = new PdfPCell (new Paragraph ( sumP5.toString(), bold ));
                                                r09.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r09 );
                                                
                                                PdfPCell r10 = new PdfPCell (new Paragraph ( sumP6.toString(), bold ));
                                                r10.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r10 );
                                                
                                                //
                                                PdfPCell r11 = new PdfPCell (new Paragraph ( sumK3.toString(), bold ));
                                                r11.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r11 ); 
                                                
                                                PdfPCell r12 = new PdfPCell (new Paragraph ( sumK5.toString(), bold ));
                                                r12.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r12 ); 
                                                
                                                PdfPCell r13 = new PdfPCell (new Paragraph ( sumK6.toString(), bold ));
                                                r13.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r13 );
                                                
                                                //PNocny
                                                PdfPCell r14 = new PdfPCell (new Paragraph ( sumPN.toString(), bold ));
                                                r14.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r14 );
                                                
                                                //Zupa Suma Pionowa
                                                PdfPCell r15 = new PdfPCell (new Paragraph ( sumD.toString(), bold ));
                                                r15.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r15 );
                                                
                                                
                                                
                                                //suma danych z poszczegolnych kolumn w danym wierszu do ostatniej kolumny SUMA (suma z danego kierunku kosztow)
                                                PdfPCell r17 = new PdfPCell( new Paragraph ( sumS3.add(sumS5.add(sumS6.add(sum2S5.add(sum2S6.add(sumO3.add(sumO5.add(sumO6.add(sumP5.add(sumP6.add(sumK3.add(sumK5.add(sumK6.add(sumPN.add(sumD)))))))))))))).toString(), bold ));                                              
                                                r17.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( r17 );
                                                
                                                
                                                //TODO Marcin
                                                // ostatni wiersz dla wszystkich kierunkow kosztow
                                                // RAZEM - posdumowanie
                                                allSumS3 = allSumS3.add(sumS3);
                                                allSumS5 = allSumS5.add(sumS5);
                                                allSumS6 = allSumS6.add(sumS6);
                                                allSum2S5 = allSum2S5.add(sum2S5);
                                                allSum2S6 = allSum2S6.add(sum2S6);
                                                allSumO3 = allSumO3.add(sumO3);                                              
                                                allSumO5 = allSumO5.add(sumO5);
                                                allSumO6 = allSumO6.add(sumO6);
                                                allSumP5 = allSumP5.add(sumP5);
                                                allSumP6 = allSumP6.add(sumP6);
                                                allSumK3 = allSumK3.add(sumK3);
                                                allSumK5 = allSumK5.add(sumK5);
                                                allSumK6 = allSumK6.add(sumK6);
                                                allSumPN = allSumPN.add(sumPN);
                                                allSumD = allSumD.add(sumD);        
                                                        
                                                        
                                                        
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
                                                
                                                //
                                                PdfPCell ar04 = new PdfPCell (new Paragraph ( allSum2S5.toString(), bold ));
                                                ar04.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( ar04 );
                                                
                                                PdfPCell ar05 = new PdfPCell (new Paragraph ( allSum2S6.toString(), bold ));
                                                ar05.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( ar05 );
                                                
                                                //
                                                PdfPCell ar06 = new PdfPCell (new Paragraph ( allSumO3.toString(), bold ));
                                                ar06.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( ar06 );

                                                PdfPCell ar07 = new PdfPCell (new Paragraph ( allSumO5.toString(), bold ));
                                                ar07.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( ar07 );

                                                PdfPCell ar08 = new PdfPCell (new Paragraph ( allSumO6.toString(), bold ));
                                                ar08.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( ar08 );

                                                //
                                                PdfPCell ar09 = new PdfPCell (new Paragraph ( allSumP5.toString(), bold ));
                                                ar09.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( ar09 );

                                                PdfPCell ar10 = new PdfPCell (new Paragraph ( allSumP6.toString(), bold ));
                                                ar10.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( ar10 );

                                                //
                                                PdfPCell ar11 = new PdfPCell (new Paragraph ( allSumK3.toString(), bold ));
                                                ar11.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( ar11 );

                                                PdfPCell ar12 = new PdfPCell (new Paragraph ( allSumK5.toString(), bold ));
                                                ar12.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( ar12 );

                                                PdfPCell ar13 = new PdfPCell (new Paragraph ( allSumK6.toString(), bold ));
                                                ar13.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( ar13 );

                                                //
                                                PdfPCell ar14 = new PdfPCell (new Paragraph ( allSumPN.toString(), bold ));
                                                ar14.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( ar14 );

                                                PdfPCell ar15 = new PdfPCell (new Paragraph ( allSumD.toString(), bold ));
                                                ar15.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( ar15 );
                                                
                                                
                                                //table.addCell( "" );
                                                
                                                //suma danych z poszczegolnych kolumn w danym wierszu do ostatniej kolumny SUMA (suma wszsytkich kierunkow kosztow)                                               
                                                PdfPCell ar17 = new PdfPCell( new Paragraph ( allSumS3.add(allSumS5.add(allSumS6.add(allSum2S5.add(allSum2S6.add(allSumO3.add(allSumO5.add(allSumO6.add(allSumP5.add(allSumP6.add(allSumK3.add(allSumK5.add(allSumK6.add(allSumPN.add(allSumD)))))))))))))).toString(), bold ));                                              
                                                ar17.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                table.addCell( ar17 );
                                                
                                         }
                                      }
                                      //END LOOP
                                      
 
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
 
            System.out.println("Raport created successfully.....");
 
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
