/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.session;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import pl.models.GrupaZywionychVO;
import pl.models.OperatorVO;
import pl.models.StanZywionychNaDzienDTO;
import pl.models.StanZywionychNaDzienSumaDTO;

/**
 *
 * @author k.skowronski
 */
@Stateless
public class ServiceFacade {
    
    
    private static final EntityManagerFactory emfInstance =
		        Persistence.createEntityManagerFactory("venZamowieniaMapiPU2");
    private EntityManager em;

    public List<StanZywionychNaDzienDTO> stanyZywionychNaDzien = new ArrayList<StanZywionychNaDzienDTO>();
    
    public List<StanZywionychNaDzienSumaDTO> stanyZywionychNaDzienSuma = new ArrayList<StanZywionychNaDzienSumaDTO>();
    
    public static List<GrupaZywionychVO> grupyZywionych;
    
    public OperatorVO user;

    public ServiceFacade() {
        em = emfInstance.createEntityManager();
    }
    
    private static volatile ServiceFacade instance = null;
    
    public static ServiceFacade getInstance() {
        if (instance == null) {
          instance = new ServiceFacade();
        }
        return instance;
    }

    public List<OperatorVO> listaOperatorow()
    {
        
      List<OperatorVO> users = new ArrayList<OperatorVO>();
      
      List<Object> usersOb = em.createQuery("SELECT u FROM OperatorVO u").getResultList();
     //  List<Object> usersOb = em.createNativeQuery("SELECT id_operator, kod, haslo FROM operatorzy").getResultList(); 
       return users;
       
    }
    
    
    public synchronized OperatorVO findUser(String account)
    { 
       
        Object[] usersOb = null;
        OperatorVO user = null;
        
        try {
             //user = em.createQuery("SELECT u FROM OperatorVO u where u.kod = '" + account + "'", OperatorVO.class).getSingleResult();
             
             Query query =  em.createNativeQuery("select * from OPERATORZY where KOD = '" + account + "'" );
        
              usersOb = (Object[]) query.getSingleResult();
             
              //user = (OperatorVO) usersOb;
              user = new OperatorVO();
              user.setIdOperator( (BigDecimal) usersOb[0] );
              user.setKod((String) usersOb[3]);
              user.setHaslo((String) usersOb[4]);                                                                                      
        } catch ( Exception e) {
            e.printStackTrace();
            Messagebox.show(e.toString());
        }
        

        return user;
    }
    
    
    
    public void listaGrupyZywionych()
    {
        List<Object[]> grupyOb = null;
        grupyZywionych = new ArrayList<GrupaZywionychVO>();
        
        try {
             //user = em.createQuery("SELECT u FROM OperatorVO u where u.kod = '" + account + "'", OperatorVO.class).getSingleResult();
             
             Query query =  em.createNativeQuery("select * from GRUPY_ZYWIONYCH" );
        
             grupyOb =  query.getResultList();
             
             for ( Object[] g : grupyOb)
             {
               GrupaZywionychVO grupa = new GrupaZywionychVO();
               grupa.setIdGrupaZywionych((BigDecimal) g[0]);
               grupa.setGrupaZywionych((String) g[8]);
               grupyZywionych.add(grupa);
             }
                                                                                  
        } catch ( Exception e) {
            e.printStackTrace();
            Messagebox.show(e.toString());
        }
    
    }
    
    
    
    public static List<String> getGrupaZywionych() {
        
        List<String> gzModules = new ArrayList<String>();
        
        if (grupyZywionych != null)
        {
            for ( GrupaZywionychVO g : grupyZywionych)
            {
                gzModules.add(g.getGrupaZywionych());
            }  
        } else {
        }
        
        
        return gzModules;
    }
    
    
    
    
    public List<StanZywionychNaDzienDTO> pobierzStanZywionychWdniuDlaGrupyZywionych( String naDzien, String grupaZywionych)
    {
        List<Object[]> stanyOb = null;
        List<StanZywionychNaDzienDTO> stanZywionych = new ArrayList<StanZywionychNaDzienDTO>();
        
        try {
             // TODO - zobacz jak Piotrek robi duże zapytania 
             Query query =  em.createNativeQuery("select d_obr, id_grupa_zywionych, id_dieta, \n" +
                    "dieta_kod, dieta_nazwa, grupa_zywionych, SP_il,  OP_il, KP_il, SK1_il, OK1_il, KK1_il  from \n" +
                    "(\n" +
                    "select sz.d_obr, sz.id_grupa_zywionych, d.id_dieta, \n" +
                    "dieta_kod, dieta_nazwa, grupa_zywionych, posilek||' '||typ_stan_zywionych posilek, szp.ilosc \n" +
                    "--*\n" +
                    "from STANY_ZYWIONYCH sz, grupy_zywionych gz, diety d, Stany_zywionych_posilki szp, s_posilki p, s_typy_stanu_zywionych stsz\n" +
                    "where sz.id_grupa_zywionych = gz.id_grupa_zywionych\n" +
                    "and sz.id_dieta = d.id_dieta\n" +
                    "and szp.id_stan_zywionych = sz.ID_STAN_ZYWIONYCH\n" +
                    "and p.id_posilek = szp.id_posilek\n" +
                    "and stsz.id_typ_stan_zywionych = szp.id_typ_stan_zywionych\n" +
                    "and sz.d_obr = '" + naDzien + "'\n" +
                    "and gz.grupa_zywionych = '" + grupaZywionych + "'\n" +
                    ")\n" +
                    "PIVOT( \n" +
                    "        SUM(ilosc) il \n" +
                    "	   FOR posilek \n" +
                    "	   IN ('Obiad korekta I' as OK1,'Obiad planowany' as OP,'Kolacja korekta I' as KK1,'Kolacja planowany' as KP,'Śniadanie korekta I' as SK1,'Śniadanie planowany' as SP )\n" +
                    "	   )" );
        
             stanyOb =  query.getResultList();
             
             for ( Object[] s : stanyOb)
             {
               StanZywionychNaDzienDTO stan 
                       = new StanZywionychNaDzienDTO( (Timestamp) s[0]
                               , (BigDecimal) s[1]
                               , (BigDecimal) s[2]
                               , (String) s[3]
                               , (String) s[4]
                               , (String) s[5]
                               , (BigDecimal) s[6]
                               , (BigDecimal) s[7]
                               , (BigDecimal) s[8]
                               , (BigDecimal) s[9]
                               , (BigDecimal) s[10]
                               , (BigDecimal) s[11] );
               
              /* stan.setdObr((Timestamp) s[0]);
               stan.setIdGrupaZywionych((BigDecimal) s[1]);
               stan.setIdDieta((BigDecimal) s[2]);
               stan.setDietaKod((String) s[3]);
               stan.setDietaNazwa((String) s[4]);
               stan.setGrupaZywionych((String) s[5]);
               stan.setSPil((BigDecimal) s[6]);
               stan.setOPil((BigDecimal) s[7]);
               stan.setKPil((BigDecimal) s[8]);
               stan.setSK1il((BigDecimal) s[9]);
               stan.setOK1il((BigDecimal) s[10]);
               stan.setKK1il((BigDecimal) s[11]);*/
               stanZywionych.add(stan);
             }
                                                                                  
        } catch ( Exception e) {
            e.printStackTrace();
            Messagebox.show(e.toString());
        }
        
        return stanZywionych;
    
    }
    
    
    
    
    
    
    public void uzupelnijZeramiStanWdniu( String naDzien )
    {
        try {
              em.createNativeQuery("begin; "
                      + "nap_hl7_tools.uzup_stan_zyw_zerami_w_dniu('" + naDzien + "')"
                      + "end;");                                                                           
        } catch ( Exception e) {
            e.printStackTrace();
            Messagebox.show(e.toString());
        }
    }
    
    
    public String zapiszStanZywionychWDniu( List<StanZywionychNaDzienDTO> stany )
    {
        for(StanZywionychNaDzienDTO s : stany )
        {
            
             try {
                 
                em.getTransaction().begin();
                 //sniadanie 
                if (  s.getsPil() != null )
                em.createNativeQuery("begin "
                      + "nap_hl7_tools.wgraj_stan_zyw_w_dniu_plan("
                      + "'" + s.getGrupaZywionych() + "'"
                      + ",'" + s.getDietaNazwa() + "'"
                      + ",'" + s.getdObr().toString().substring(0, 10) + "'"
                      + ",'" + 1 + "'"
                      + "," + s.getsPil() + ");"
                      + " end;").executeUpdate(); 
              
                 //obiad
                 
                 em.createNativeQuery("begin "
                      + "nap_hl7_tools.wgraj_stan_zyw_w_dniu_plan("
                      + "'" + s.getGrupaZywionych() + "'"
                      + ",'" + s.getDietaNazwa() + "'"
                      + ",'" + s.getdObr().toString().substring(0, 10) + "'"
                      + ",'" + 3 + "'"
                      + "," + s.getoPil() + ");"
                      + " end;").executeUpdate(); 
                 
                 
                 //kolacja
                 if (  s.getkPil() != null )
                 em.createNativeQuery("begin "
                      + "nap_hl7_tools.wgraj_stan_zyw_w_dniu_plan("
                      + "'" + s.getGrupaZywionych() + "'"
                      + ",'" + s.getDietaNazwa() + "'"
                      + ",'" + s.getdObr().toString().substring(0, 10) + "'"
                      + ",'" + 5 + "'"
                      + "," + s.getkPil() + ");"
                      + " end;").executeUpdate();
                 em.getTransaction().commit();

              
            } catch ( Exception e) {
                e.printStackTrace();
                Messagebox.show(e.toString());
            }
            
        }
        
        
        return "OK";
    }
    
    



}
    
