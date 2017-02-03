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
    
    
    
    
}
