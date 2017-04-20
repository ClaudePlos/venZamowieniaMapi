/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.session;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.zkoss.zul.Messagebox;
import pl.models.StanZywionychNaDzienDTO;
import pl.models.reports.RapMcZestawienieKkDTO;
import pl.models.reports.StanZywDzienPosilekKkDTO;

/**
 *
 * @author k.skowronski
 */
@Stateless
public class ServiceReports {
    
    private static final EntityManagerFactory emfInstance =
		        Persistence.createEntityManagerFactory("venZamowieniaMapiPU2");
    private EntityManager em;
    
    
    public ServiceReports() {
        em = emfInstance.createEntityManager();
    }
    
    
    private static volatile ServiceReports instance = null;
    
    public static ServiceReports getInstance() {
        if (instance == null) {
          instance = new ServiceReports();
        }
        return instance;
    }
    
    
    
    
    public List<StanZywDzienPosilekKkDTO> pobierzStanZywionychDzienPosilekKK( String naDzien, BigDecimal kierunekKosztow, String posilek)
    {        
        List<Object[]> stanyKK = null;
        List<StanZywDzienPosilekKkDTO> stanZywionych = new ArrayList<StanZywDzienPosilekKkDTO>();
    System.out.print( naDzien + " kkId: " + kierunekKosztow + " " + posilek );
        try {
         
             Query query =  em.createNativeQuery("select  grupa_zywionych\n" +
"  , dieta_nazwa, sum(szp.ilosc) ilosc\n" +
"  from STANY_ZYWIONYCH sz, grupy_zywionych gz, diety d, Stany_zywionych_posilki szp, s_posilki p, s_typy_stanu_zywionych stsz, diety_grupy_zywionych dgz, diety_kuchnie dk\n" +
"  where sz.id_grupa_zywionych = gz.id_grupa_zywionych\n" +
"  and sz.id_dieta = d.id_dieta\n" +
"  and szp.id_stan_zywionych = sz.ID_STAN_ZYWIONYCH\n" +
"  and p.id_posilek = szp.id_posilek\n" +
"  and stsz.id_typ_stan_zywionych = szp.id_typ_stan_zywionych\n" +
"  and sz.d_obr = to_Date('" + naDzien + "','YYYY-MM-DD')\n" +
"  and id_kierunek_kosztow = " + kierunekKosztow + "\n" +
"  and p.posilek = '" + posilek + "'\n" +
"  and dgz.ID_GRUPA_ZYWIONYCH = gz.ID_GRUPA_ZYWIONYCH \n" +
"  and dgz.ID_DIETA = d.ID_DIETA \n" +
"  and dk.ID_DIETA = d.ID_DIETA \n" +
"  and dk.AKTYWNE = 1 \n" +
"  and dgz.AKTYWNE = 1 \n" +
"  and szp.ilosc != 0 \n" +
"  and dk.ID_KUCHNIA = gz.ID_KUCHNIA \n" +
"group by sz.d_obr, d.id_dieta, dieta_kod, dieta_nazwa, d.lp, sz.uwagi, grupa_zywionych\n" +
"order by 1,2" );

        
             stanyKK =  query.getResultList();
             
             
             
       for ( Object[] s : stanyKK)
             {
               StanZywDzienPosilekKkDTO stan 
                       = new StanZywDzienPosilekKkDTO( 
                                 (String) s[0]
                               , (String) s[1]
                               , (BigDecimal) s[2]
                               );
               
               stanZywionych.add(stan);
  
             }      
             
           
                                                                                  
        } catch ( Exception e) {
            e.printStackTrace();
            Messagebox.show(e.toString());
        }
        
        return stanZywionych;
    
    }
    
    
    
    
    public List<RapMcZestawienieKkDTO> pobierzMcZestawienieKK( String naDzienOd, String naDzienDo, BigDecimal kierunekKosztow, String posilek)
    {        
        List<Object[]> stanyKK = null;
        List<RapMcZestawienieKkDTO> stanZywionychMcKK = new ArrayList<RapMcZestawienieKkDTO>();
        System.out.print( naDzienOd + " kkId: " + kierunekKosztow + " " + posilek ); // log
        try {
         
             Query query =  em.createNativeQuery("select * from \n" +
"		(					\n" +
"                    select  gz.grupa_zywionych,  sum(szp.ilosc) ilosc\n" +
"					, substr(sz.D_OBR,-2) dzien\n" +
"                    from STANY_ZYWIONYCH sz, grupy_zywionych gz, diety d, Stany_zywionych_posilki szp, s_posilki p, s_typy_stanu_zywionych stsz, diety_grupy_zywionych dgz, diety_kuchnie dk, S_KIERUNKI_KOSZTOW kk\n" +
"                    where sz.id_grupa_zywionych = gz.id_grupa_zywionych\n" +
"                    and sz.id_dieta = d.id_dieta\n" +
"                    and szp.id_stan_zywionych = sz.ID_STAN_ZYWIONYCH\n" +
"                    and p.id_posilek = szp.id_posilek\n" +
"                    and stsz.id_typ_stan_zywionych = szp.id_typ_stan_zywionych\n" +
"                    --and to_char(sz.d_obr,'YYYY-MM') = '2017-04' --' + okres + '\n" +
"					AND D_OBR BETWEEN '" + naDzienOd + "' AND '" + naDzienDo + "'\n" +
"					--AND GRUPA_ZYWIONYCH IN ('Rehabilitacja Oddz. I parter')--, 'CHIRURGIA A III PIĘTRO')\n" +
"					and posilek = '" + posilek + "'\n" +
"					and gz.id_kierunek_kosztow = kk.ID_KIERUNEK_KOSZTOW\n" +
"					and gz.id_kierunek_kosztow = " + kierunekKosztow + 
"                    and dgz.ID_GRUPA_ZYWIONYCH = gz.ID_GRUPA_ZYWIONYCH \n" +
"                    and dgz.ID_DIETA = d.ID_DIETA \n" +
"                    and dk.ID_DIETA = d.ID_DIETA \n" +
"                    and dk.AKTYWNE = 1 \n" +
"                    and dgz.AKTYWNE = 1 \n" +
"                    and dk.ID_KUCHNIA = gz.ID_KUCHNIA \n" +
"					group by kk.KIERUNEK_KOSZTOW, gz.grupa_zywionych, /*dieta_nazwa, */ posilek /*||' '||typ_stan_zywionych */ , D_OBR	\n" +
"					order by dzien	\n" +
"					)\n" +
"                    PIVOT( \n" +
"                            SUM(ilosc)\n" +
"                    	   FOR dzien \n" +
"                    	   IN (\n" +
"							    '01' as D01,\n" +
"								'02' as D02,\n" +
"								'03' as D03,\n" +
"								'04' as D04,\n" +
"								'05' as D05,\n" +
"								'06' as D06,\n" +
"								'07' as D07,\n" +
"								'08' as D08,\n" +
"								'09' as D09,\n" +
"								'10' as D10,\n" +
"								'11' as D11,\n" +
"								'12' as D12,\n" +
"								'13' as D13,\n" +
"								'14' as D14,\n" +
"								'15' as D15,\n" +
"								'16' as D16,\n" +
"								'17' as D17,\n" +
"								'18' as D18,\n" +
"								'19' as D19,\n" +
"								'20' as D20,\n" +
"								'21' as D21,\n" +
"								'22' as D22,\n" +
"								'23' as D23,\n" +
"								'24' as D24,\n" +
"								'25' as D25,\n" +
"								'26' as D26,\n" +
"								'27' as D27,\n" +
"								'28' as D28,\n" +
"								'29' as D29,\n" +
"								'30' as D30,\n" +
"								'31' as D31))" );

        
             stanyKK =  query.getResultList();
             
             
             
             for ( Object[] s : stanyKK)
             {
               RapMcZestawienieKkDTO stan = new RapMcZestawienieKkDTO();
               stan.setGz( (String) s[0] );
               
               if ( s[1] != null )
                stan.setD01( (BigDecimal) s[1] );
               else
                stan.setD01( BigDecimal.ZERO );
               
               stan.setD02( (BigDecimal) s[2] );
               stan.setD03( (BigDecimal) s[3] );
               stan.setD04( (BigDecimal) s[4] );
               stan.setD05( (BigDecimal) s[5] );
               stan.setD06( (BigDecimal) s[6] );
               stan.setD07( (BigDecimal) s[7] );
               stan.setD08( (BigDecimal) s[8] );
               stan.setD09( (BigDecimal) s[9] );
               stan.setD10( (BigDecimal) s[10] );
               stan.setD11( (BigDecimal) s[11] );
               stan.setD12( (BigDecimal) s[12] );
               stan.setD13( (BigDecimal) s[13] );
               stan.setD14( (BigDecimal) s[14] );
               stan.setD15( (BigDecimal) s[15] );
               stan.setD16( (BigDecimal) s[16] );
               stan.setD17( (BigDecimal) s[17] );
               stan.setD18( (BigDecimal) s[18] );
               stan.setD19( (BigDecimal) s[19] );
               stan.setD20( (BigDecimal) s[20] );
               
               if ( s[21] != null )
                stan.setD21( (BigDecimal) s[21] );
               else
                stan.setD21( BigDecimal.ZERO );
               
               stan.setD22( (BigDecimal) s[22] );
               stan.setD23( (BigDecimal) s[23] );
               stan.setD24( (BigDecimal) s[24] );
               stan.setD25( (BigDecimal) s[25] );
               stan.setD26( (BigDecimal) s[26] );
               stan.setD27( (BigDecimal) s[27] );
               stan.setD28( (BigDecimal) s[28] );
               stan.setD29( (BigDecimal) s[29] );
               stan.setD30( (BigDecimal) s[30] );
               stan.setD31( (BigDecimal) s[31] );

                    
               
               stanZywionychMcKK.add(stan);
  
             }      
             
           
                                                                                  
        } catch ( Exception e) {
            e.printStackTrace();
            Messagebox.show(e.toString());
        }
        
        return stanZywionychMcKK;
    
    }
    
    
    
    
}
