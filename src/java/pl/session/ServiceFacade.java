/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.session;


import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;

import javax.json.JsonReader;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import org.zkoss.zul.Messagebox;
import pl.models.GrupaZywionychVO;
import pl.models.KierunekKosztowVO;
import pl.models.NapMapowaniaCenyVO;
import pl.models.OperatorVO;
import pl.models.SprWartDzialalnosciKuchniDTO;
import pl.models.StanZywionychMMRapDTO;
import pl.models.StanZywionychMMRapRozDTO;
import pl.models.StanZywionychNaDzienDTO;
import pl.models.StanZywionychNaDzienSumaDTO;
import java.util.Date;
import javax.ejb.LocalBean;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PersistenceContext;
import pl.models.reports.KtoWprowadzilDaneDTO;
import pl.server.NapBeanBase;


/**
 *
 * @author k.skowronski
 */
public class ServiceFacade extends NapBeanBase {
    
    
//    private static final EntityManagerFactory emfInstance =
//		        Persistence.createEntityManagerFactory("venZamowieniaMapiPU2");
//    private EntityManager em;

    public List<StanZywionychNaDzienDTO> stanyZywionychNaDzien;
    
    public List<StanZywionychNaDzienSumaDTO> stanyZywionychNaDzienSuma;
    
    public static List<KierunekKosztowVO> kierunkiKosztow;
    
    public static List<KierunekKosztowVO> jsonKierunkiKosztow;
    
    public static List<GrupaZywionychVO> grupyZywionych;
    
 

//    
//    public ServiceFacade() {
//        em = emfInstance.createEntityManager();
//    }
//    
    private static volatile ServiceFacade instance = null;
    
    public static ServiceFacade getInstance() {
        if (instance == null) {
          instance = new ServiceFacade();
        }
        return instance;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<OperatorVO> listaOperatorow()
    {
        
      List<OperatorVO> users = new ArrayList<OperatorVO>();
      
      List<Object> usersOb = em.createQuery("SELECT u FROM OperatorVO u").getResultList();
     //  List<Object> usersOb = em.createNativeQuery("SELECT id_operator, kod, haslo FROM operatorzy").getResultList(); 
       return users;
       
    }
    
    
    public synchronized OperatorVO getUserAndKK(String account)
    { 
        Object[] usersOb = null;  
        OperatorVO user = new OperatorVO();
        try {
             
             Query query =  em.createNativeQuery("select * from OPERATORZY where KOD = '" + account + "'" );
        
              usersOb = (Object[]) query.getSingleResult();
             
              //user = (OperatorVO) usersOb;
              user = new OperatorVO();
              user.setIdOperator( (BigDecimal) usersOb[0] );
              user.setKod((String) usersOb[3]);
              user.setHaslo((String) usersOb[4]);      
              List<KierunekKosztowVO> kierunkiKosztow = pobierzKierunkiKosztowUzytkownikaZBazy( (String) usersOb[6] );
              user.setKierunkiKosztow( kierunkiKosztow );
              
        } catch ( Exception e) {
            e.printStackTrace();
            Messagebox.show(e.toString());
        }
        return user;
    }
    
    
    public synchronized OperatorVO findUserLogowanie(String account)
    {      
        Object[] usersOb = null;
        OperatorVO userLog = new OperatorVO();
        
        try {
             
             Query query =  em.createNativeQuery("select * from OPERATORZY where KOD = '" + account + "'" );
        
              usersOb = (Object[]) query.getSingleResult();

              userLog.setIdOperator( (BigDecimal) usersOb[0] );
              userLog.setKod((String) usersOb[3]);
              userLog.setHaslo((String) usersOb[4]);        
        } catch ( Exception e) {
            e.printStackTrace();
            Messagebox.show(e.toString());
        }
        return userLog;
    }
    
    
    public List<KierunekKosztowVO> pobierzKierunkiKosztowUzytkownikaZBazy(String idKierunkiKosztow) // pobierna jak json
    { 
        
      List<KierunekKosztowVO> ret = new  ArrayList<KierunekKosztowVO>();
      Object[] dane = null;
      
      jsonKierunkiKosztow = new ArrayList<KierunekKosztowVO>();
        
        /*
          {"kierunkiKosztow": ["1234", "3456", "1235"]}
        */
        
      JsonReader jsonReader = Json.createReader(new StringReader( idKierunkiKosztow ));      
      JsonObject object = jsonReader.readObject();
      jsonReader.close();
      
      
      
      JsonArray dane1 = (JsonArray) object.getJsonArray("dane");
      
      for(int i = 0; i < dane1.size(); i++)
      {
         String sqlGrupyZywionych = "(";
         KierunekKosztowVO jsonKK = new KierunekKosztowVO();
         GrupaZywionychVO  jsonGZ = new GrupaZywionychVO();
         
         JsonObject itemDane = dane1.getJsonObject(i);
         
         JsonObject jsonObKK = itemDane.getJsonObject("kierunekKosztow");
         
         JsonString newSam = jsonObKK.getJsonString("id");
                 
         jsonKK.setIdKierunekKosztow( new BigDecimal( newSam.getString() ) );
         
         JsonArray jsonArKK = (JsonArray) jsonObKK.getJsonArray("grupyZywionychId");
         
         List<GrupaZywionychVO> jsonListGZ = new ArrayList<GrupaZywionychVO>();
          
         for (int j = 0; j < jsonArKK.size(); j++) 
         {
             jsonGZ.setIdGrupaZywionych( new BigDecimal( jsonArKK.getString(j) ) );
             jsonListGZ.add(jsonGZ);
             sqlGrupyZywionych = sqlGrupyZywionych + jsonGZ.getIdGrupaZywionych().toString() + ",";
         }
         
          jsonKK.setGrupyZywionych(jsonListGZ);
          
          sqlGrupyZywionych = sqlGrupyZywionych.substring( 0, sqlGrupyZywionych.length() - 1 ) + ")";
          
          jsonKK.setSqlGrupyZywionych(sqlGrupyZywionych);
          
          jsonKierunkiKosztow.add(jsonKK);
      }
      
      //JsonArray jsonKierKosztow = (JsonArray) object.getJsonArray("kierunkiKosztow");
      //JsonArray jsonGrupyZywionych = (JsonArray) object.getJsonArray("grupyZywionych");

      
         
      
      
        for( KierunekKosztowVO kierKosz : jsonKierunkiKosztow ){
  
          try {
                //user = em.createQuery("SELECT u FROM OperatorVO u where u.kod = '" + account + "'", OperatorVO.class).getSingleResult();

                Query query =  em.createNativeQuery("select * from S_KIERUNKI_KOSZTOW where ID_KIERUNEK_KOSZTOW = " + kierKosz.getIdKierunekKosztow()  );

                 dane = (Object[]) query.getSingleResult();

                 //user = (OperatorVO) usersOb;
                 KierunekKosztowVO kk = new KierunekKosztowVO();
                 kk.setIdKierunekKosztow( (BigDecimal) dane[0] );
                 kk.setKierunekKosztowNazwa((String) dane[1]);
                 kk.setKierunekKosztowKod( (String) dane[2]); 
                 kk.setUwagi( (String) dane[6] );
                 kk.setSqlGrupyZywionych( kierKosz.getSqlGrupyZywionych() );
                 
                 ret.add(kk);
                 
           } catch ( Exception e) {
               e.printStackTrace();
               Messagebox.show(e.toString());
           }

          
          
        }
      

        return ret;
    }
    
    
    
    
    
    public void listaGrupyZywionych( KierunekKosztowVO kierunekKosztow )
    {
        List<Object[]> grupyOb = null;
        grupyZywionych = new ArrayList<GrupaZywionychVO>();
        
        
        
        
        System.out.println( "Pobieram GZ dla KK_ID: " + kierunekKosztow.getIdKierunekKosztow() );
        
        try {
             //user = em.createQuery("SELECT u FROM OperatorVO u where u.kod = '" + account + "'", OperatorVO.class).getSingleResult();
             
            String sql = "select * from GRUPY_ZYWIONYCH where AKTYWNE = 1 and id_kierunek_kosztow = " + kierunekKosztow.getIdKierunekKosztow(); 
                   
                     
            if (kierunekKosztow.getSqlGrupyZywionych().length() > 1 )
            {
               sql = sql + " and id_grupa_zywionych in " + kierunekKosztow.getSqlGrupyZywionych();
            }
                     
                     
            sql = sql + " order by lp, grupa_zywionych";
                     
                     
             Query query =  em.createNativeQuery( sql );       
        
             grupyOb =  query.getResultList();
             
             for ( Object[] g : grupyOb)
             {
               GrupaZywionychVO grupa = new GrupaZywionychVO();
               grupa.setIdGrupaZywionych((BigDecimal) g[0]);
               grupa.setGrupaZywionych((String) g[8]);
               grupa.setGrupaZywionychKod((String) g[9]);
               grupyZywionych.add(grupa);
             }
                                                                                  
        } catch ( Exception e) {
            e.printStackTrace();
            Messagebox.show(e.toString());
        }
    
    }
    

    public  List<GrupaZywionychVO> getGrupaZywionych( KierunekKosztowVO selectedKierunekKosztow ) {
     
        if ( selectedKierunekKosztow != null )
        {
            System.out.println( "Pobieram GZ dla KK: " + selectedKierunekKosztow.getKierunekKosztowKod() );
            listaGrupyZywionych( selectedKierunekKosztow );
        }

        return grupyZywionych;
    }
    
    
    
    
    public List<StanZywionychNaDzienDTO> pobierzStanZywionychWdniuDlaGrupyZywionych( String naDzien, String grupaZywionych, String sortType)
    {        
        List<Object[]> stanyOb = null;
        List<StanZywionychNaDzienDTO> stanZywionych = new ArrayList<StanZywionychNaDzienDTO>();
        
        try {
             // TODO - zobacz jak Piotrek robi duĹĽe zapytania 
             Query query =  em.createNativeQuery("select d_obr, id_grupa_zywionych, id_dieta, \n" +
                    "dieta_nazwa, SP_il, DSP_il, OP_il, PP_il, KP_il, PNP_il, \n" +
                    "SK1_il, DSK1_il, OK1_il, PK1_il, KK1_il, PNK1_il, lp, uwagi  from \n" +
                    "(\n" +
                    "select sz.d_obr, sz.id_grupa_zywionych, d.id_dieta, \n" +
                    "dieta_kod, dieta_nazwa, grupa_zywionych, posilek||' '||typ_stan_zywionych posilek, szp.ilosc, d.lp lp, sz.uwagi uwagi  \n" +
                    "--*\n" +
                    "from STANY_ZYWIONYCH sz, grupy_zywionych gz, diety d, Stany_zywionych_posilki szp, s_posilki p, s_typy_stanu_zywionych stsz, diety_grupy_zywionych dgz, diety_kuchnie dk\n" +
                    "where sz.id_grupa_zywionych = gz.id_grupa_zywionych\n" +
                    "and sz.id_dieta = d.id_dieta\n" +
                    "and szp.id_stan_zywionych = sz.ID_STAN_ZYWIONYCH\n" +
                    "and p.id_posilek = szp.id_posilek\n" +
                    "and stsz.id_typ_stan_zywionych = szp.id_typ_stan_zywionych\n" +
                    "and sz.d_obr = to_Date('" + naDzien + "','YYYY-MM-DD')\n" +
                    "and gz.grupa_zywionych = '" + grupaZywionych + "'\n" +
                    "and dgz.ID_GRUPA_ZYWIONYCH = gz.ID_GRUPA_ZYWIONYCH \n" +
                    "and dgz.ID_DIETA = d.ID_DIETA \n" +
                    "and dk.ID_DIETA = d.ID_DIETA \n" +
                    "and dk.AKTYWNE = 1 \n" +
                    //"and dgz.AKTYWNE = 1 \n" +
                    "and dk.ID_KUCHNIA = gz.ID_KUCHNIA \n" +
                    ")\n" +
                    "PIVOT( \n" +
                    "        SUM(ilosc) il \n" +
                    "	   FOR posilek \n" +
                    "	   IN ('Obiad korekta I' as OK1,'Obiad planowany' as OP,'Kolacja korekta I' as KK1,'Kolacja planowany' as KP,'Śniadanie korekta I' as SK1,'Śniadanie planowany' as SP, \n" +
                    "      '2. śniadanie korekta I' as DSK1, '2. śniadanie planowany' as DSP, 'Podwieczorek korekta I' as PK1, 'Podwieczorek planowany' as PP, 'Posiłek nocny korekta I' as PNK1, 'Posiłek nocny planowany' as PNP)\n" +
                    "	   ) order by " + sortType );
        
             stanyOb =  query.getResultList();
             
             int i = 1;
             
             for ( Object[] s : stanyOb)
             {
               StanZywionychNaDzienDTO stan 
                       = new StanZywionychNaDzienDTO( (Timestamp) s[0]
                               , (BigDecimal) s[1]
                               , (BigDecimal) s[2]
                               , (String) s[3]
                               , (BigDecimal) s[4]
                               , (BigDecimal) s[5]
                               , (BigDecimal) s[6]
                               , (BigDecimal) s[7]
                               , (BigDecimal) s[8]
                               , (BigDecimal) s[9]
                               , (BigDecimal) s[10]
                               , (BigDecimal) s[11]
                               , (BigDecimal) s[12]
                               , (BigDecimal) s[13]
                               , (BigDecimal) s[14]
                               , (BigDecimal) s[15]
                               , new BigDecimal( String.valueOf(i) ) 
                               , (String) s[17]  // uwagi 
                            );
               
               if ( stan.getSniadaniePlanIl() == null ) stan.setsVisible(Boolean.FALSE);
               if ( stan.getDrugieSniadaniePlanIl() == null ) stan.setS2Visible(Boolean.FALSE);
               if ( stan.getObiadPlanIl() == null ) stan.setoVisible(Boolean.FALSE);
               if ( stan.getPodwieczorekPlanIl() == null ) stan.setpVisible(Boolean.FALSE);
               if ( stan.getKolacjaPlanIl() == null ) stan.setkVisible(Boolean.FALSE);
               if ( stan.getPosilekNocnyPlanIl() == null ) stan.setPnVisible(Boolean.FALSE);
               
               if ( stan.getSniadanieKorIl() == null ) stan.setKsVisible(Boolean.FALSE);
               if ( stan.getDrugieSniadanieKorIl() == null ) stan.setKs2Visible(Boolean.FALSE);
               if ( stan.getObiadKorIl() == null ) stan.setKoVisible(Boolean.FALSE);
               if ( stan.getPodwieczorekKorIl() == null ) stan.setKpVisible(Boolean.FALSE);
               if ( stan.getKolacjaKorIl() == null ) stan.setKkVisible(Boolean.FALSE);
               if ( stan.getPosilekNocnyKorIl() == null ) stan.setKpnVisible(Boolean.FALSE);
               
               
               stanZywionych.add(stan);
               
               i++;
             }
                                                                                  
        } catch ( Exception e) {
            e.printStackTrace();
            Messagebox.show(e.toString());
        }
        
        return stanZywionych;
    
    }
    
    
    
    
    
    
    public void uzupelnijZeramiStanWdniu( String naDzien )
    {
        EntityTransaction tx = em.getTransaction();
        
        String q  = "begin "
                      + "nap_hl7_tools.uzup_stan_zyw_zerami_w_dniu( to_date('" + naDzien + "','YYYY-MM-DD') );"
                      + "end;";
        try {
            tx.begin();
              em.createNativeQuery(q).executeUpdate();
            tx.commit();
        } catch ( Exception e) {
            tx.rollback();
            e.printStackTrace();
            Messagebox.show(e.toString());
        }
    }
    
    
    


    
    public String zapiszStanZywionychWDniu2( List<StanZywionychNaDzienDTO> stany, KierunekKosztowVO kierKosztow, String czyKorekta, OperatorVO user )
    {
        int ileWierszy = stany.size();
        int i = 1;
        
        System.out.print("Start wgranie, StZy kk: " + kierKosztow + " Czy korekta?: " + czyKorekta + " Na dzien: TODO******" );
        
        EntityTransaction tx = em.getTransaction();
        
        for(StanZywionychNaDzienDTO s : stany )
        {
            
            
             try {

                 
                tx.begin();
                
                 
                
                    
                        em.createNativeQuery("begin "
                              + "nap_hl7_tools.wgraj_stan_zyw_w_dniu_plan2("
                              + "'" + kierKosztow.getIdKierunekKosztow() + "'"
                              + ",'" + s.getIdGrupaZywionych() + "'"
                              + ",'" + s.getDietaNazwa() + "'"
                              + ",to_date('" + s.getdObr().toString().substring(0, 10) + "','YYYY-MM-DD')"
                                
                              + "," + s.getSniadaniePlanIl()
                              + "," + s.getDrugieSniadaniePlanIl()
                              + "," + s.getObiadPlanIl()
                              + "," + s.getPodwieczorekPlanIl()
                              + "," + s.getKolacjaPlanIl()
                              + "," + s.getPosilekNocnyPlanIl()
                                
                              + "," + s.getSniadanieKorIl()
                              + "," + s.getDrugieSniadanieKorIl()
                              + "," + s.getObiadKorIl()
                              + "," + s.getPodwieczorekKorIl()
                              + "," + s.getKolacjaKorIl()
                              + "," + s.getPosilekNocnyKorIl()
                                
                              + ",'" + s.getSzUwagi() + "'"
                                
                              + "," + user.getIdOperator() 
                              + ",'" + czyKorekta + "'"
                              + ");"
                              + " end;").executeUpdate(); 
                    
                
                
                  tx.commit();
                  
                  if ( i == ileWierszy )
                  {
                      aktualizujStanZywionychPoWgraniu( kierKosztow, s.getIdGrupaZywionych(), s.getdObr().toString().substring(0, 10), user );
                  }
                 

              
            } catch ( Exception e) {
                tx.rollback();
                e.printStackTrace();
                Messagebox.show(e.toString());
            }
            i++;
        }
        
        System.out.print("End wgranie StZy, kk: " + kierKosztow + " Czy korekta?: " + czyKorekta + " Na dzien: TODO" );
        return "OK";
    }
    
    
    public String aktualizujStanZywionychPoWgraniu(KierunekKosztowVO kierKosztow, BigDecimal idGrupaZywionch, String data, OperatorVO user)
    {
        EntityTransaction tx = em.getTransaction();
        
        try {
                 
                System.out.print("Aktualizuje stany zywionych na koniec"); 
                 
                tx.begin();
                
                    
                        em.createNativeQuery("begin "
                              + "nap_hl7_tools.AKTUALIZUJ_STANY_ZYWIONYCH("
                              + "'" + kierKosztow.getIdKierunekKosztow() + "'"
                              + "," + idGrupaZywionch 
                              + ",to_date('" + data + "','YYYY-MM-DD')"
                              + "," + user.getIdOperator() 
                              + ");"
                              + " end;").executeUpdate(); 
                    
                
                
                  tx.commit();
                 

              
            } catch ( Exception e) {
                tx.rollback();
                e.printStackTrace();
                Messagebox.show(e.toString());
            }
        
        return "OK";
    }
    
    
    
    
    public List<StanZywionychNaDzienDTO> pobierzStanZywionychWdniuDlaKierunkuKosztow( String naDzien, int kierunekKosztow)
    {        
        List<Object[]> stanyOb = null;
        List<StanZywionychNaDzienDTO> stanZywionych = new ArrayList<StanZywionychNaDzienDTO>();
        
        try {
             // TODO - zobacz jak Piotrek robi duĹĽe zapytania 
             Query query =  em.createNativeQuery("select d_obr, id_dieta, dieta_nazwa\n" +
"                  , SP_il, DSP_il, OP_il, PP_il, KP_il, PNP_il,\n" +
"                    SK1_il, DSK1_il, OK1_il, PK1_il, KK1_il, PNK1_il, lp, uwagi  from \n" +
"					(\n" +
"                    select sz.d_obr, d.id_dieta, \n" +
"                    dieta_kod, dieta_nazwa, posilek||' '||typ_stan_zywionych posilek, sum(szp.ilosc) ilosc, d.lp lp, sz.uwagi uwagi \n" +
"                    from STANY_ZYWIONYCH sz, grupy_zywionych gz, diety d, Stany_zywionych_posilki szp, s_posilki p, s_typy_stanu_zywionych stsz, diety_grupy_zywionych dgz, diety_kuchnie dk\n" +
"                    where sz.id_grupa_zywionych = gz.id_grupa_zywionych\n" +
"                    and sz.id_dieta = d.id_dieta\n" +
"                    and szp.id_stan_zywionych = sz.ID_STAN_ZYWIONYCH\n" +
"                    and p.id_posilek = szp.id_posilek\n" +
"                    and stsz.id_typ_stan_zywionych = szp.id_typ_stan_zywionych\n" +
                    "and sz.d_obr = to_Date('" + naDzien + "','YYYY-MM-DD')\n" +
"					and id_kierunek_kosztow = " + kierunekKosztow + "\n" +
"                    and dgz.ID_GRUPA_ZYWIONYCH = gz.ID_GRUPA_ZYWIONYCH \n" +
"                    and dgz.ID_DIETA = d.ID_DIETA \n" +
"                    and dk.ID_DIETA = d.ID_DIETA \n" +
"                    and dk.AKTYWNE = 1 \n" +
"                    and dgz.AKTYWNE = 1 \n" +
"                    and dk.ID_KUCHNIA = gz.ID_KUCHNIA \n" +
"					group by sz.d_obr, d.id_dieta, dieta_kod, dieta_nazwa, posilek||' '||typ_stan_zywionych, d.lp, sz.uwagi\n" +
"					)\n" +
"                    PIVOT( \n" +
"                            SUM(ilosc) il \n" +
"                    	   FOR posilek \n" +
"                    	   IN ('Obiad korekta I' as OK1,'Obiad planowany' as OP,'Kolacja korekta I' as KK1,'Kolacja planowany' as KP,'Śniadanie korekta I' as SK1,'Śniadanie planowany' as SP, \n" +
"                          '2. śniadanie korekta I' as DSK1, '2. śniadanie planowany' as DSP, 'Podwieczorek korekta I' as PK1, 'Podwieczorek planowany' as PP, 'Posiłek nocny korekta I' as PNK1, 'Posiłek nocny planowany' as PNP)\n" +
"                    	   ) order by lp" );

        
             stanyOb =  query.getResultList();
             
             int i = 1;
             
             for ( Object[] s : stanyOb)
             {
               StanZywionychNaDzienDTO stan 
                       = new StanZywionychNaDzienDTO( (Timestamp) s[0]
                               , null
                               , (BigDecimal) s[1]
                               , (String) s[2]
                               , (BigDecimal) s[3]
                               , (BigDecimal) s[4]
                               , (BigDecimal) s[5]
                               , (BigDecimal) s[6]
                               , (BigDecimal) s[7]
                               , (BigDecimal) s[8]
                               , (BigDecimal) s[9]
                               , (BigDecimal) s[10]
                               , (BigDecimal) s[11]
                               , (BigDecimal) s[12]
                               , (BigDecimal) s[13]
                               , (BigDecimal) s[14]
                               , new BigDecimal( String.valueOf(i) ) 
                               , (String) s[16]);
               
               stanZywionych.add(stan);
               
               i++;
             }
                                                                                  
        } catch ( Exception e) {
            e.printStackTrace();
            Messagebox.show(e.toString());
        }
        
        return stanZywionych;
    
    }
    
    
    public List<StanZywionychNaDzienDTO> pobierzStanZywionychWdniuDlaKierunkuKosztowGlobal( String naDzien, int kierunekKosztow)
    {        
        List<Object[]> stanyOb = null;
        List<StanZywionychNaDzienDTO> stanZywionych = new ArrayList<StanZywionychNaDzienDTO>();
        
        try {
             // TODO - zobacz jak Piotrek robi duĹĽe zapytania 
             Query query =  em.createNativeQuery(
"                     select d_obr, id_dieta, dieta_nazwa \n" +
"                    , SP_il, DSP_il, OP_il, PP_il, KP_il, PNP_il, \n" +
"                    SK1_il, DSK1_il, OK1_il, PK1_il, KK1_il, PNK1_il, lp, uwagi from \n" +
"                    ( \n" +
"                    select sz.d_obr, d.id_dieta, \n" +
"                    dieta_kod, dieta_nazwa, posilek||' '||typ_stan_zywionych posilek, sum(szp.ilosc) ilosc, d.lp lp \n" +
"                    --,  uwagi \n" +
"                    , LISTAGG( case when sz.uwagi is not null then sz.uwagi||' '|| gz.grupa_zywionych_kod ||' || ' else null end  ) WITHIN  GROUP (ORDER BY sz.uwagi) uwagi \n" +
"                    from STANY_ZYWIONYCH sz, grupy_zywionych gz, diety d, Stany_zywionych_posilki szp, s_posilki p, s_typy_stanu_zywionych stsz, diety_grupy_zywionych dgz, diety_kuchnie dk \n" +
"                    where sz.id_grupa_zywionych = gz.id_grupa_zywionych \n" +
"                    and sz.id_dieta = d.id_dieta \n" +
"                    and szp.id_stan_zywionych = sz.ID_STAN_ZYWIONYCH \n" +
"                    and p.id_posilek = szp.id_posilek \n" +
"                    and stsz.id_typ_stan_zywionych = szp.id_typ_stan_zywionych \n" +
"                    and sz.d_obr = to_Date('" + naDzien + "','YYYY-MM-DD')\n" +
"                    and id_kierunek_kosztow = " + kierunekKosztow + "\n" +
"                    and dgz.ID_GRUPA_ZYWIONYCH = gz.ID_GRUPA_ZYWIONYCH \n" +
"                    and dgz.ID_DIETA = d.ID_DIETA \n" +
"                    and dk.ID_DIETA = d.ID_DIETA \n" +
"                    and dk.AKTYWNE = 1 \n" +
"                    and dgz.AKTYWNE = 1 \n" +
"                    and dk.ID_KUCHNIA = gz.ID_KUCHNIA \n" +
"                    group by sz.d_obr, d.id_dieta, dieta_kod, dieta_nazwa, posilek||' '||typ_stan_zywionych, d.lp \n" +
"                    ) \n" +
"                    PIVOT( \n" +
"                    SUM(ilosc) il \n" +
"                    FOR posilek \n" +
"                    IN ('Obiad korekta I' as OK1,'Obiad planowany' as OP,'Kolacja korekta I' as KK1,'Kolacja planowany' as KP,'Śniadanie korekta I' as SK1,'Śniadanie planowany' as SP, \n" +
"                    '2. śniadanie korekta I' as DSK1, '2. śniadanie planowany' as DSP, 'Podwieczorek korekta I' as PK1, 'Podwieczorek planowany' as PP, 'Posiłek nocny korekta I' as PNK1, 'Posiłek nocny planowany' as PNP) \n" +
"                    ) order by lp" );
        
             stanyOb =  query.getResultList();
             
             int i = 1;
             
             for ( Object[] s : stanyOb)
             {
               StanZywionychNaDzienDTO stan 
                       = new StanZywionychNaDzienDTO( (Timestamp) s[0]
                               , null
                               , (BigDecimal) s[1]
                               , (String) s[2]
                               , (BigDecimal) s[3]
                               , (BigDecimal) s[4]
                               , (BigDecimal) s[5]
                               , (BigDecimal) s[6]
                               , (BigDecimal) s[7]
                               , (BigDecimal) s[8]
                               , (BigDecimal) s[9]
                               , (BigDecimal) s[10]
                               , (BigDecimal) s[11]
                               , (BigDecimal) s[12]
                               , (BigDecimal) s[13]
                               , (BigDecimal) s[14]
                               , new BigDecimal( String.valueOf(i) ) 
                               , (String) s[16]);
               
               stanZywionych.add(stan);
               
               i++;
             }
                                                                                  
        } catch ( Exception e) {
            e.printStackTrace();
            Messagebox.show(e.toString());
        }
        
        return stanZywionych;
    
    }
    
    
    
    
    public List<SprWartDzialalnosciKuchniDTO> pobierzSprWartoscioweDzialalnoscKuchni( String naDzien )
    {  
        //SprWartDzialalnosciKuchniDTO k = new SprWartDzialalnosciKuchniDTO();
        List<Object[]> stany = null;
        List<SprWartDzialalnosciKuchniDTO> listaStanWartosciowy = new ArrayList<SprWartDzialalnosciKuchniDTO>();
        
        try {

             Query query =  em.createNativeQuery("select grupa_zywionych, grupa_zywionych_kod from grupy_zywionych" );
             
            //k =  (SprWartDzialalnosciKuchniDTO) query.getSingleResult();
             
             stany =  query.getResultList();
             
             int i = 1;
             
             for ( Object[] s : stany)
             {
               /*SprWartDzialalnosciKuchniDTO stan = new SprWartDzialalnosciKuchniDTO( 
                               (String) s[0]
                               );*/
                 
                 SprWartDzialalnosciKuchniDTO rowSprWart = new SprWartDzialalnosciKuchniDTO( 
                           (String) s[0]
                         , (String) s[1]    
                 );
     
                 //rowSprWart.setGrupaZywionych((String) s[0]);
                 //rowSprWart.setGrupaZywionychKod((String) s[1]);
                 
               listaStanWartosciowy.add(rowSprWart);
               
               i++;
             }
                                                                                  
        } catch ( Exception e) {
            e.printStackTrace();
            Messagebox.show(e.toString());
        }
        
        return listaStanWartosciowy;
    
    }
    
    
    
    
    public List<NapMapowaniaCenyVO> getNapMapowaniaCeny()
    {        
        List<Object[]> napMapCeny = null;
        List<NapMapowaniaCenyVO> napMapowaniaCenyList = new ArrayList<NapMapowaniaCenyVO>();
        
        try {
             // TODO - zobacz jak Piotrek robi duĹĽe zapytania 
             Query query =  em.createNativeQuery("select * from nap_mapowania_ceny" );

        
             napMapCeny =  query.getResultList();
             
             int i = 1;
             
             for ( Object[] s : napMapCeny)
             {
               NapMapowaniaCenyVO stan 
                       = new NapMapowaniaCenyVO( 
                                 (BigDecimal) s[9]
                               , (String) s[0]
                               , (BigDecimal) s[1]
                               , (BigDecimal) s[2]
                               , (BigDecimal) s[3]
                               , (BigDecimal) s[4]
                               , (BigDecimal) s[5]
                               , (BigDecimal) s[6]
                               , (BigDecimal) s[7]
                               , (String) s[8]
                               );
               
               napMapowaniaCenyList.add(stan);
               
               i++;
             }
                                                                                  
        } catch ( Exception e) {
            e.printStackTrace();
            Messagebox.show(e.toString());
        }
        
        return napMapowaniaCenyList;
    
    }
    
    
    
    public List<StanZywionychMMRapRozDTO> getDataForFinancialRaport( String okres, int kierunekKosztow )
    {  
        //SprWartDzialalnosciKuchniDTO k = new SprWartDzialalnosciKuchniDTO();
        List<Object[]> stanyOb = null;
        List<StanZywionychMMRapRozDTO> stanZywionychRapRoz = new ArrayList<StanZywionychMMRapRozDTO>();
        List<StanZywionychMMRapRozDTO> stanZywionychRapRoz2 = new ArrayList<StanZywionychMMRapRozDTO>();
        
        try {

             Query query =  em.createNativeQuery("SELECT KIERUNEK_KOSZTOW, trim(substr( GRUPA_ZYWIONYCH, 5, INSTR(GRUPA_ZYWIONYCH, ',')-6 )) \n" + //trim czyli to cale okrojenie zrobione pod COI Onkologia
",SUM(SN) SN\n" +
",SUM(DSN) DSN\n" +
",SUM(OB) OB\n" +
",SUM(POD) POD\n" +
",SUM(KOL) KOL\n" +
",SUM(PN) PN\n" +
",ilo_posilkowa FROM (select KIERUNEK_KOSZTOW, grupa_zywionych\n" +
"                  , SP_il + SK1_il SN\n" +
"				  , DSP_il + DSK1_il DSN\n" +
"				  , OP_il + OK1_il OB\n" +
"				  , PP_il + PK1_il POD\n" +
"				  , KP_il + KK1_il KOL\n" +
"				  , PNP_il + PNK1_il PN\n" +
", case when SP_il is null then 0 else 1 end \n" +
"+ case when DSP_il is null then 0 else 1 end \n" +
"+ case when OP_il is null then 0 else 1 end\n" +
"+ case when PP_il is null then 0 else 1 end\n" +
"+ case when KP_il is null then 0 else 1 end\n" +
"+ case when PNP_il is null then 0 else 1 end ilo_posilkowa	 			 \n" +
"from \n" +
"					(\n" +
"                    select kk.KIERUNEK_KOSZTOW, gz.grupa_zywionych, dieta_nazwa, posilek||' '||typ_stan_zywionych posilek, sum(szp.ilosc) ilosc\n" +
"                    from STANY_ZYWIONYCH sz, grupy_zywionych gz, diety d, Stany_zywionych_posilki szp, s_posilki p, s_typy_stanu_zywionych stsz, diety_grupy_zywionych dgz, diety_kuchnie dk, S_KIERUNKI_KOSZTOW kk\n" +
"                    where sz.id_grupa_zywionych = gz.id_grupa_zywionych\n" +
"                    and sz.id_dieta = d.id_dieta\n" +
"                    and szp.id_stan_zywionych = sz.ID_STAN_ZYWIONYCH\n" +
"                    and p.id_posilek = szp.id_posilek\n" +
"                    and stsz.id_typ_stan_zywionych = szp.id_typ_stan_zywionych\n" +
"                    and to_char(sz.d_obr,'YYYY-MM') = '" + okres + "'\n" +
"					and gz.id_kierunek_kosztow = kk.ID_KIERUNEK_KOSZTOW\n" +
"					and gz.id_kierunek_kosztow = " + kierunekKosztow + "\n" +
"                    and dgz.ID_GRUPA_ZYWIONYCH = gz.ID_GRUPA_ZYWIONYCH \n" +
"                    and dgz.ID_DIETA = d.ID_DIETA \n" +
"                    and dk.ID_DIETA = d.ID_DIETA \n" +
"                    and dk.AKTYWNE = 1 \n" +
"                    and dgz.AKTYWNE = 1 \n" +
"                    and gz.AKTYWNE = 1 \n" +
"                    and GRUPA_ZYWIONYCH != 'Centrum Onkologii - Instytut' \n" +   //wywalone na sztywno z raportu dla COI Onkologia
"                    and GRUPA_ZYWIONYCH != 'Wawelska - Centrum Onkologii' \n" +   //wywalone na sztywno z raportu dla COI Onkologia       
"                    and dk.ID_KUCHNIA = gz.ID_KUCHNIA \n" +
"					group by kk.KIERUNEK_KOSZTOW, gz.grupa_zywionych, dieta_nazwa,  posilek||' '||typ_stan_zywionych\n" +
"					)\n" +
"                    PIVOT( \n" +
"                            SUM(ilosc) il \n" +
"                    	   FOR posilek \n" +
"                    	   IN ('Obiad korekta I' as OK1\n" +
"						   ,'Obiad planowany' as OP\n" +
"						   ,'Kolacja korekta I' as KK1\n" +
"						   ,'Kolacja planowany' as KP\n" +
"						   ,'Śniadanie korekta I' as SK1\n" +
"						   ,'Śniadanie planowany' as SP\n" +
"						   ,'2. śniadanie korekta I' as DSK1\n" +
"						   ,'2. śniadanie planowany' as DSP\n" +
"						   , 'Podwieczorek korekta I' as PK1\n" +
"						   , 'Podwieczorek planowany' as PP\n" +
"						   , 'Posiłek nocny korekta I' as PNK1\n" +
"						   , 'Posiłek nocny planowany' as PNP)\n" +
"                    	   )) GROUP BY KIERUNEK_KOSZTOW, grupa_zywionych, ilo_posilkowa order by KIERUNEK_KOSZTOW, grupa_zywionych		" );
             
             stanyOb =  query.getResultList();
             
           
             
             for ( Object[] s : stanyOb)
             {
               StanZywionychMMRapRozDTO stan = null;
                    
                 if ( ((BigDecimal) s[8]).intValue() == 1 )//dieta 1o posilkowa kiedys byly to zupy
                 {
                     stan = new StanZywionychMMRapRozDTO();
                     stan.setKk(   (String) s[0] );
                     stan.setGz(   (String) s[1] );
                     stan.setSn3(   new BigDecimal(BigInteger.ZERO) );
                     stan.setSn5(   new BigDecimal(BigInteger.ZERO) );
                     stan.setSn6(   new BigDecimal(BigInteger.ZERO) );
                     
                     stan.setDsn5(  new BigDecimal(BigInteger.ZERO) );
                     stan.setDsn6(  new BigDecimal(BigInteger.ZERO) );
                     
                     stan.setOb3(   new BigDecimal(BigInteger.ZERO));
                     stan.setOb5(   new BigDecimal(BigInteger.ZERO) );
                     stan.setOb6(   new BigDecimal(BigInteger.ZERO) );
                     
                     stan.setPod5(  new BigDecimal(BigInteger.ZERO)  );
                     stan.setPod6(  new BigDecimal(BigInteger.ZERO)  );
                     
                     stan.setKol3(  new BigDecimal(BigInteger.ZERO)  );
                     stan.setKol5(  new BigDecimal(BigInteger.ZERO)  );
                     stan.setKol6(  new BigDecimal(BigInteger.ZERO)  );
                     
                     if ( s[7] != null )
                        stan.setPn(  (BigDecimal) s[7]  );
                     else 
                        stan.setPn(  new BigDecimal(BigInteger.ZERO) );
                     
                    stan.setKompot(  new BigDecimal(BigInteger.ZERO) );                       
                 }
               
                 if ( ((BigDecimal) s[8]).intValue() == 3 ) //dieta 3y posilkowa
                 {
                     stan = new StanZywionychMMRapRozDTO();
                     stan.setKk(   (String) s[0] );
                     stan.setGz(   (String) s[1] );
                     stan.setSn3(  (BigDecimal) s[2] );
                     stan.setSn5(   new BigDecimal(BigInteger.ZERO) );
                     stan.setSn6(   new BigDecimal(BigInteger.ZERO) );
                     
                     stan.setDsn5(  new BigDecimal(BigInteger.ZERO) );
                     stan.setDsn6(  new BigDecimal(BigInteger.ZERO) );
                     
                     stan.setOb3(  (BigDecimal) s[4] );
                     stan.setOb5(   new BigDecimal(BigInteger.ZERO) );
                     stan.setOb6(   new BigDecimal(BigInteger.ZERO) );
                     
                     stan.setPod5(  new BigDecimal(BigInteger.ZERO)  );
                     stan.setPod6(  new BigDecimal(BigInteger.ZERO)  );
                     
                     stan.setKol3( (BigDecimal) s[6] );
                     stan.setKol5( new BigDecimal(BigInteger.ZERO)  );
                     stan.setKol6( new BigDecimal(BigInteger.ZERO)  );
                     
                     if ( s[7] != null )
                        stan.setPn(  (BigDecimal) s[7]  );
                     else 
                        stan.setPn(  new BigDecimal(BigInteger.ZERO) );

                    stan.setKompot(  new BigDecimal(BigInteger.ZERO) ); 
                    
                 }
                 
                 if ( ((BigDecimal) s[8]).intValue() == 5 )//dieta 5o posilkowa
                 {
                     stan = new StanZywionychMMRapRozDTO();
                     stan.setKk(   (String) s[0] );
                     stan.setGz(   (String) s[1] );
                     stan.setSn3(   new BigDecimal(BigInteger.ZERO));
                     stan.setSn5(  (BigDecimal) s[2] );
                     stan.setSn6(   new BigDecimal(BigInteger.ZERO) );
                     
                     stan.setDsn5( (BigDecimal) s[3] );
                     stan.setDsn6(  new BigDecimal(BigInteger.ZERO) );
                     
                     stan.setOb3(   new BigDecimal(BigInteger.ZERO));
                     stan.setOb5(  (BigDecimal) s[4] );
                     stan.setOb6(   new BigDecimal(BigInteger.ZERO) );
                     
                     stan.setPod5( (BigDecimal) s[5]  );
                     stan.setPod6(  new BigDecimal(BigInteger.ZERO)  );
                     
                     stan.setKol3(  new BigDecimal(BigInteger.ZERO) );
                     stan.setKol5( (BigDecimal) s[6]  );
                     stan.setKol6(  new BigDecimal(BigInteger.ZERO)  );
                     
                     if ( s[7] != null )
                        stan.setPn(  (BigDecimal) s[7]  );
                     else 
                        stan.setPn(  new BigDecimal(BigInteger.ZERO) );
                     
                    stan.setKompot(  new BigDecimal(BigInteger.ZERO) );                        
                     
                     
                 }
                 
                 if ( ((BigDecimal) s[8]).intValue() == 6 )//dieta 6o posilkowa
                 {
                     stan = new StanZywionychMMRapRozDTO();
                     stan.setKk(   (String) s[0] );
                     stan.setGz(   (String) s[1] );
                     stan.setSn3(   new BigDecimal(BigInteger.ZERO));
                     stan.setSn5(   new BigDecimal(BigInteger.ZERO) );
                     stan.setSn6(  (BigDecimal) s[2] );

                     stan.setDsn5(  new BigDecimal(BigInteger.ZERO) );
                     stan.setDsn6( (BigDecimal) s[3] );
                     
                     stan.setOb3(   new BigDecimal(BigInteger.ZERO));  
                     stan.setOb5(   new BigDecimal(BigInteger.ZERO) );
                     stan.setOb6(  (BigDecimal) s[4] );

                     stan.setPod5(  new BigDecimal(BigInteger.ZERO)  );
                     stan.setPod6( (BigDecimal) s[5]  );
                     
                     stan.setKol3(  new BigDecimal(BigInteger.ZERO) );
                     stan.setKol5(  new BigDecimal(BigInteger.ZERO)  );
                     stan.setKol6( (BigDecimal) s[6]  );
                     
                     if ( s[7] != null )
                        stan.setPn(  (BigDecimal) s[7]  );
                     else 
                        stan.setPn(  new BigDecimal(BigInteger.ZERO) ); 
                     
                    
                     stan.setKompot(  new BigDecimal(BigInteger.ZERO) );                        
                 }
                 
                 
                 
                 if ( ((BigDecimal) s[8]).intValue() == 2 ) //dieta 2u posilkowa
                 {
                     stan = new StanZywionychMMRapRozDTO();
                     stan.setKk(   (String) s[0] );
                     stan.setGz(   (String) s[1] );
                     stan.setSn3(   new BigDecimal(BigInteger.ZERO) );
                     stan.setSn5(   new BigDecimal(BigInteger.ZERO) );
                     stan.setSn6(   new BigDecimal(BigInteger.ZERO) );

                     stan.setDsn5(  new BigDecimal(BigInteger.ZERO) );
                     stan.setDsn6(  new BigDecimal(BigInteger.ZERO) );
                     
                     stan.setOb3(   new BigDecimal(BigInteger.ZERO) );  
                     stan.setOb5(   new BigDecimal(BigInteger.ZERO) );
                     stan.setOb6(   new BigDecimal(BigInteger.ZERO) );

                     stan.setPod5(  new BigDecimal(BigInteger.ZERO)  );
                     stan.setPod6(  new BigDecimal(BigInteger.ZERO)  );
                     
                     stan.setKol3(  new BigDecimal(BigInteger.ZERO) );
                     stan.setKol5(  new BigDecimal(BigInteger.ZERO) );
                     stan.setKol6(  new BigDecimal(BigInteger.ZERO) );
                     
                    
                     stan.setPn(  (BigDecimal) s[7] );                      
                     //stan.setKompot( (BigDecimal) s[8] );
                     
                     if ( s[7] != null )
                        stan.setPn(  new BigDecimal(BigInteger.ZERO)   );
                     else 
                        stan.setPn(  new BigDecimal(BigInteger.ZERO) ); 
                     
                     //System.out.print( s[0] + " " + s[1] + " " + s[8] );
                     
                     if ( s[8] != null ){
                        
                        BigDecimal sniadanie; 
                        BigDecimal obiad;
                        BigDecimal kolacja;
                         
                        if ( s[2] != null ){
                           String sniadString = s[2].toString();
                           sniadanie = new BigDecimal( sniadString );  
                        }                              
                        else 
                            sniadanie = new BigDecimal(BigInteger.ZERO);
                        
                        if ( s[4] != null ){
                           String obiadString = s[4].toString();
                           obiad= new BigDecimal( obiadString );  
                        }
                        else 
                            obiad = new BigDecimal(BigInteger.ZERO); 
                        
                        if ( s[6] != null ){
                           String kolacjaString = s[6].toString();
                           kolacja= new BigDecimal( kolacjaString );  
                        }
                        else 
                            kolacja = new BigDecimal(BigInteger.ZERO);   
                         
                        BigDecimal kompot = sniadanie.add(obiad.add(kolacja)); //w diecie 2u posilkowej mamy tylko sniadanie, obiad, kolacje i to tu dodajemy
                        stan.setKompot(  kompot  ); 
                     }
                     else 
                        stan.setKompot(  new BigDecimal(BigInteger.ZERO) );                      
                     
                 }
                 
                 
                 
               stanZywionychRapRoz.add(stan);
                         

             }
             
             for ( StanZywionychMMRapRozDTO rr : stanZywionychRapRoz )
               {
                  if ( rr != null ) 
                  {
                        if( stanZywionychRapRoz2.stream().anyMatch( p -> p.getGz().equals(rr.getGz())) )
                        {
                            StanZywionychMMRapRozDTO firstMatchedName = stanZywionychRapRoz2.stream()
                                  .filter((s) -> s.getGz().equals(rr.getGz() ))
                                  .findFirst().get();

                            //System.out.print(rr);

                            if ( rr.getSn3() != null )
                            firstMatchedName.setSn3( firstMatchedName.getSn3().add(rr.getSn3()) ) ;

                            if ( rr.getSn5() != null )
                            firstMatchedName.setSn5( firstMatchedName.getSn5().add(rr.getSn5()) ) ;

                            if ( rr.getSn6() != null )
                            firstMatchedName.setSn6( firstMatchedName.getSn6().add(rr.getSn6()) ) ;



                            if ( rr.getDsn5() != null )
                            firstMatchedName.setDsn5( firstMatchedName.getDsn5().add(rr.getDsn5()) ) ;

                            if ( rr.getDsn6() != null )
                            firstMatchedName.setDsn6( firstMatchedName.getDsn6().add(rr.getDsn6()) ) ;
                            
                            
                            
                            if ( rr.getOb3() != null )
                            firstMatchedName.setOb3( firstMatchedName.getOb3().add(rr.getOb3()) ) ;
                            
                            if ( rr.getOb5() != null )
                            firstMatchedName.setOb5( firstMatchedName.getOb5().add(rr.getOb5()) ) ;
                            
                            if ( rr.getOb6() != null )
                            firstMatchedName.setOb6( firstMatchedName.getOb6().add(rr.getOb6()) ) ;
                            
                            
                            if ( rr.getPod5() != null )
                            firstMatchedName.setPod5( firstMatchedName.getPod5().add(rr.getPod5()) ) ;
                            
                            if ( rr.getPod6() != null )
                            firstMatchedName.setPod6( firstMatchedName.getPod6().add(rr.getPod6()) ) ;
                            
                            
                            if ( rr.getKol3() != null )
                            firstMatchedName.setKol3( firstMatchedName.getKol3().add(rr.getKol3()) ) ;
                            
                            if ( rr.getKol5() != null )
                            firstMatchedName.setKol5( firstMatchedName.getKol5().add(rr.getKol5()) ) ;
                            
                            if ( rr.getKol6() != null )
                            firstMatchedName.setKol6( firstMatchedName.getKol6().add(rr.getKol6()) ) ;
                            
                            
                            if ( rr.getPn() != null )
                            firstMatchedName.setPn( firstMatchedName.getPn().add(rr.getPn()) ) ;
                            
                            if ( rr.getKompot() != null )
                            firstMatchedName.setKompot( firstMatchedName.getKompot().add(rr.getKompot()) ) ;

                        }
                        else
                        {
                           stanZywionychRapRoz2.add(rr);
                        }
                  }
                  
               }
                                                                                  
        } catch ( Exception e) {
            e.printStackTrace();
            Messagebox.show(e.toString());
        }
        
        return stanZywionychRapRoz2;
    
    }
    
    
    
    
    
            
    public List<KtoWprowadzilDaneDTO> infoKtoWprowadzilKiedy( String naDzien, int kierunekKosztow, String grupaZywkonych  )
    {  
        //SprWartDzialalnosciKuchniDTO k = new SprWartDzialalnosciKuchniDTO();
        List<Object[]> listKto = null;
        List<KtoWprowadzilDaneDTO> listaDane = new ArrayList<KtoWprowadzilDaneDTO>();
        
        try {

             Query query =  em.createNativeQuery("select operator, d_zmiany, GRUPA_ZYWIONYCH, d_obr, dieta_nazwa, posilek, ilosc  from (   \n" +
"	   select (select kod from operatorzy o where o.id_operator = sz.id_operator) operator\n" +
"	   ,  szp.d_zmiany\n" +
"	   , gz.GRUPA_ZYWIONYCH\n" +
"	   , sz.d_obr, \n" +
"   dieta_nazwa, posilek||' '||typ_stan_zywionych posilek, sum(szp.ilosc) ilosc, d.lp lp, p.lp lp_posilku\n" +
"  from STANY_ZYWIONYCH sz, grupy_zywionych gz, diety d, Stany_zywionych_posilki szp, s_posilki p, s_typy_stanu_zywionych stsz, diety_grupy_zywionych dgz, diety_kuchnie dk\n" +
"  where sz.id_grupa_zywionych = gz.id_grupa_zywionych\n" +
"  and sz.id_dieta = d.id_dieta\n" +
"  and szp.id_stan_zywionych = sz.ID_STAN_ZYWIONYCH\n" +
"  and p.id_posilek = szp.id_posilek\n" +
"  and stsz.id_typ_stan_zywionych = szp.id_typ_stan_zywionych\n" +
"                    and sz.d_obr = to_Date('" + naDzien + "','YYYY-MM-DD')\n" +
"					and id_kierunek_kosztow = " + kierunekKosztow + "\n" +
"					and gz.GRUPA_ZYWIONYCH = '" + grupaZywkonych + "'\n" +
"  and dgz.ID_GRUPA_ZYWIONYCH = gz.ID_GRUPA_ZYWIONYCH \n" +
"  and dgz.ID_DIETA = d.ID_DIETA \n" +
"  and dk.ID_DIETA = d.ID_DIETA \n" +
"  and dk.AKTYWNE = 1 \n" +
"  and dgz.AKTYWNE = 1 \n" +
"  and dk.ID_KUCHNIA = gz.ID_KUCHNIA \n" +
"group by sz.d_obr, d.id_dieta, dieta_kod, dieta_nazwa, posilek||' '||typ_stan_zywionych, d.lp, sz.id_operator,  szp.d_zmiany, gz.GRUPA_ZYWIONYCH, p.lp\n" +
")\n" +
"where ilosc != 0\n" +
"order by lp, lp_posilku" );
             
            //k =  (SprWartDzialalnosciKuchniDTO) query.getSingleResult();
             
             listKto =  query.getResultList();
 
             for ( Object[] s : listKto)
             {

                 
                 KtoWprowadzilDaneDTO kto = new KtoWprowadzilDaneDTO( 
                           (String) s[0]
                         , (Date) s[1] 
                         , (String) s[2] 
                         , (Date) s[3] 
                         , (String) s[4]
                         , (String) s[5]
                         , (BigDecimal) s[6]
                 );

                 
               listaDane.add(kto);

             }
                                                                                  
        } catch ( Exception e) {
            e.printStackTrace();
            Messagebox.show(e.toString());
        }
        
        return listaDane;
    
    }
    
    
    // OTHERS ******************************
    public String dateToStringYYYMMDD( Date d )
    {
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
        return dt1.format(d);
    }
    
    // add KS 2017-04-19
    public KierunekKosztowVO getKierunekKosztow( int idKierunkiKosztow) // pobierna jak json
    { 
        KierunekKosztowVO kk = new KierunekKosztowVO();

          try {
              //
                Query query =  em.createQuery("select k.idKierunekKosztow, k.kierunekKosztowNazwa, k.kierunekKosztowKod, k.uwagi "
                        + "from KierunekKosztowVO k where k.idKierunekKosztow = :kk ")
                        .setParameter("kk", idKierunkiKosztow);

                  Object[] dane  =  (Object[]) query.getSingleResult();

 
                 
                 kk.setIdKierunekKosztow( (BigDecimal) dane[0] );
                 kk.setKierunekKosztowNazwa((String) dane[1]);
                 kk.setKierunekKosztowKod( (String) dane[2]); 
                 kk.setUwagi( (String) dane[3] );
                 
                 //ret.add(kk);
                 
           } catch ( Exception e) {
               e.printStackTrace();
               Messagebox.show(e.toString());
           }

        return kk;
    }
    
    
    
    //do raportu ilosciowego
    public List<StanZywionychMMRapDTO> pobierzStanZywionychWokresieDlaKierunkuKosztow( String okres, int kierunekKosztow)
    {        
        List<Object[]> stanyOb = null;
        List<StanZywionychMMRapDTO> stanZywionych = new ArrayList<StanZywionychMMRapDTO>();
        
        try {
             Query query =  em.createNativeQuery("SELECT KIERUNEK_KOSZTOW, GRUPA_ZYWIONYCH  \n" +
",SUM(SN) SN\n" +
",SUM(DSN) DSN\n" +
",SUM(OB) OB\n" +
",SUM(POD) POD\n" +
",SUM(KOL) KOL\n" +
",SUM(PN) PN\n" +
"FROM (select KIERUNEK_KOSZTOW, grupa_zywionych\n" +
"                  , SP_il + SK1_il SN\n" +
"				  , DSP_il + DSK1_il DSN\n" +
"				  , OP_il + OK1_il OB\n" +
"				  , PP_il + PK1_il POD\n" +
"				  , KP_il + KK1_il KOL\n" +
"				  , PNP_il + PNK1_il PN\n" +
", case when SP_il is null then 0 else 1 end \n" +
"+ case when DSP_il is null then 0 else 1 end \n" +
"+ case when OP_il is null then 0 else 1 end\n" +
"+ case when PP_il is null then 0 else 1 end\n" +
"+ case when KP_il is null then 0 else 1 end\n" +
"+ case when PNP_il is null then 0 else 1 end ilo_posilkowa			 \n" +
"from \n" +
"					(\n" +
"                    select kk.KIERUNEK_KOSZTOW, gz.grupa_zywionych, dieta_nazwa, posilek||' '||typ_stan_zywionych posilek, sum(szp.ilosc) ilosc\n" +
"                    from STANY_ZYWIONYCH sz, grupy_zywionych gz, diety d, Stany_zywionych_posilki szp, s_posilki p, s_typy_stanu_zywionych stsz, diety_grupy_zywionych dgz, diety_kuchnie dk, S_KIERUNKI_KOSZTOW kk\n" +
"                    where sz.id_grupa_zywionych = gz.id_grupa_zywionych\n" +
"                    and sz.id_dieta = d.id_dieta\n" +
"                    and szp.id_stan_zywionych = sz.ID_STAN_ZYWIONYCH\n" +
"                    and p.id_posilek = szp.id_posilek\n" +
"                    and stsz.id_typ_stan_zywionych = szp.id_typ_stan_zywionych\n" +
"                    and to_char(sz.d_obr,'YYYY-MM') = '" + okres + "'\n" +
"					and gz.id_kierunek_kosztow = kk.ID_KIERUNEK_KOSZTOW\n" +
"					and gz.id_kierunek_kosztow = " + kierunekKosztow + "\n" +
"                    and dgz.ID_GRUPA_ZYWIONYCH = gz.ID_GRUPA_ZYWIONYCH \n" +
"                    and dgz.ID_DIETA = d.ID_DIETA \n" +
"                    and dk.ID_DIETA = d.ID_DIETA \n" +
"                    and dk.AKTYWNE = 1 \n" +
"                    and dgz.AKTYWNE = 1 \n" +
"                    and gz.AKTYWNE = 1       \n" +
"                    and dk.ID_KUCHNIA = gz.ID_KUCHNIA \n" +
"					group by kk.KIERUNEK_KOSZTOW, gz.grupa_zywionych, dieta_nazwa,  posilek||' '||typ_stan_zywionych\n" +
"					)\n" +
"                    PIVOT( \n" +
"                            SUM(ilosc) il \n" +
"                    	   FOR posilek \n" +
"                    	   IN ('Obiad korekta I' as OK1\n" +
"						   ,'Obiad planowany' as OP\n" +
"						   ,'Kolacja korekta I' as KK1\n" +
"						   ,'Kolacja planowany' as KP\n" +
"						   ,'Śniadanie korekta I' as SK1\n" +
"						   ,'Śniadanie planowany' as SP\n" +
"						   ,'2. śniadanie korekta I' as DSK1\n" +
"						   ,'2. śniadanie planowany' as DSP\n" +
"						   , 'Podwieczorek korekta I' as PK1\n" +
"						   , 'Podwieczorek planowany' as PP\n" +
"						   , 'Posiłek nocny korekta I' as PNK1\n" +
"						   , 'Posiłek nocny planowany' as PNP)\n" +
"                    	   )) GROUP BY KIERUNEK_KOSZTOW, grupa_zywionych order by KIERUNEK_KOSZTOW, grupa_zywionych");

        
             stanyOb =  query.getResultList();
             
             int i = 1;
             
             for ( Object[] s : stanyOb)
             {
               StanZywionychMMRapDTO stan =     new StanZywionychMMRapDTO();
               
               //stan = new stanZywionych();
               if ( s[0] != null )
                stan.setKk(   (String) s[0] );               
               if ( s[1] != null )
                 stan.setGz(   (String) s[1] );                 
               if ( s[2] != null )
               stan.setSn(   (BigDecimal) s[2]  );
               if ( s[3] != null )
               stan.setDsn(  (BigDecimal) s[3]  );
               if ( s[4] != null )
               stan.setOb(   (BigDecimal) s[4]  );
               if ( s[5] != null )
               stan.setPod(  (BigDecimal) s[5]  );
               if ( s[6] != null )
               stan.setKol(  (BigDecimal) s[6]  );
               if ( s[7] != null )
               stan.setPn(   (BigDecimal) s[7]  );
               stanZywionych.add(stan);
               
             }
             
             
                                 
                            
                            
                                                                                  
        } catch ( Exception e) {
            e.printStackTrace();
            Messagebox.show(e.toString());
        }
        
        return stanZywionych;
    
    }
    

}
    
