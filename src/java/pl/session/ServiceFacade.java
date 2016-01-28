/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.session;


import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
    
    public static List<KierunekKosztowVO> kierunkiKosztow;
    
    public static List<KierunekKosztowVO> jsonKierunkiKosztow;
    
    public static List<GrupaZywionychVO> grupyZywionych;
    
    public static OperatorVO user;
    
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
        
        try {
             //user = em.createQuery("SELECT u FROM OperatorVO u where u.kod = '" + account + "'", OperatorVO.class).getSingleResult();
             
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
             
            String sql = "select * from GRUPY_ZYWIONYCH where id_kierunek_kosztow = " + kierunekKosztow.getIdKierunekKosztow(); 
                   
                     
            if (kierunekKosztow.getSqlGrupyZywionych().length() > 1 )
            {
               sql = sql + " and id_grupa_zywionych in " + kierunekKosztow.getSqlGrupyZywionych();
            }
                     
                     
            sql = sql + " order by grupa_zywionych";
                     
                     
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
    
    
    public static List<KierunekKosztowVO> getKierunkiKosztowUzytkownika() {
        return user.getKierunkiKosztow();
    }
    
    
    
    public  List<GrupaZywionychVO> getGrupaZywionych( KierunekKosztowVO selectedKierunekKosztow ) {
     
        if ( selectedKierunekKosztow != null )
        {
            System.out.println( "Pobieram GZ dla KK: " + selectedKierunekKosztow.getKierunekKosztowKod() );
            listaGrupyZywionych( selectedKierunekKosztow );
        }

        return grupyZywionych;
    }
    
    
    
    
    public List<StanZywionychNaDzienDTO> pobierzStanZywionychWdniuDlaGrupyZywionych( String naDzien, String grupaZywionych)
    {        
        List<Object[]> stanyOb = null;
        List<StanZywionychNaDzienDTO> stanZywionych = new ArrayList<StanZywionychNaDzienDTO>();
        
        try {
             // TODO - zobacz jak Piotrek robi duże zapytania 
             Query query =  em.createNativeQuery("select d_obr, id_grupa_zywionych, id_dieta, \n" +
                    "dieta_nazwa, SP_il, DSP_il, OP_il, PP_il, KP_il, PNP_il, \n" +
                    "SK1_il, DSK1_il, OK1_il, PK1_il, KK1_il, PNK1_il, lp  from \n" +
                    "(\n" +
                    "select sz.d_obr, sz.id_grupa_zywionych, d.id_dieta, \n" +
                    "dieta_kod, dieta_nazwa, grupa_zywionych, posilek||' '||typ_stan_zywionych posilek, szp.ilosc, d.lp lp  \n" +
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
                    "	   ) order by lp" );
        
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
                               , new BigDecimal( String.valueOf(i) ) );
               
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
    
    /*
    public String zapiszStanZywionychWDniu( List<StanZywionychNaDzienDTO> stany, KierunekKosztowVO kierKosztow )
    {
        BigDecimal zero =  new BigDecimal(0);
        
        for(StanZywionychNaDzienDTO s : stany )
        {
            EntityTransaction tx = em.getTransaction();
            
             try {
                 
                 
                tx.begin();
                
                 //sniadanie
                if ( s.getsPil() != null )
                {
                    if (  !s.getsPil().equals(zero)  )
                    {
                        em.createNativeQuery("begin "
                              + "nap_hl7_tools.wgraj_stan_zyw_w_dniu_plan("
                              + "'" + kierKosztow.getIdKierunekKosztow() + "'"
                              + ",'" + s.getGrupaZywionych() + "'"
                              + ",'" + s.getDietaNazwa() + "'"
                              + ",to_date('" + s.getdObr().toString().substring(0, 10) + "','YYYY-MM-DD')"
                              + ",'" + 1 + "'"
                              + "," + s.getsPil() + "," + user.getIdOperator() + ");"
                              + " end;").executeUpdate(); 
                    }
                }
                
              
                 //obiad
                if ( s.getoPil() != null )
                {
                    if (  !s.getoPil().equals(zero) )
                    {
                     em.createNativeQuery("begin "
                          + "nap_hl7_tools.wgraj_stan_zyw_w_dniu_plan("
                          + "'" + kierKosztow.getIdKierunekKosztow() + "'"
                          + ",'" + s.getGrupaZywionych() + "'"
                          + ",'" + s.getDietaNazwa() + "'"
                          + ",to_date('" + s.getdObr().toString().substring(0, 10) + "','YYYY-MM-DD')"
                          + ",'" + 3 + "'"
                          + "," + s.getoPil() + "," + user.getIdOperator() + ");"
                          + " end;").executeUpdate(); 
                    }
                }
                 
                 
                 //kolacja
                if ( s.getkPil() != null )
                {
                    if (  !s.getkPil().equals(zero) )
                    {
                       em.createNativeQuery("begin "
                            + "nap_hl7_tools.wgraj_stan_zyw_w_dniu_plan("
                            + "'" + kierKosztow.getIdKierunekKosztow() + "'"
                            + ",'" + s.getGrupaZywionych() + "'"
                            + ",'" + s.getDietaNazwa() + "'"
                            + ",to_date('" + s.getdObr().toString().substring(0, 10) + "','YYYY-MM-DD')"
                            + ",'" + 5 + "'"
                            + "," + s.getkPil() + "," + user.getIdOperator() + ");"
                            + " end;").executeUpdate(); 
                    }
                }
                 
                // wyzerowanie 
                //sniadanie 
                if ( s.getsPil() != null )
                {
                    if (  s.getsPil().signum() < 0 )
                    {
                        em.createNativeQuery("begin "
                              + "nap_hl7_tools.wgraj_stan_zyw_w_dniu_plan("
                              + "'" + kierKosztow.getIdKierunekKosztow() + "'"
                              + ",'" + s.getGrupaZywionych() + "'"
                              + ",'" + s.getDietaNazwa() + "'"
                              + ",to_date('" + s.getdObr().toString().substring(0, 10) + "','YYYY-MM-DD')"
                              + ",'" + 1 + "'"
                              + "," + zero + "," + user.getIdOperator() + ");"
                              + " end;").executeUpdate(); 
                    }
                }
                
              
                 //obiad
                if ( s.getoPil() != null )
                {
                    if (  s.getoPil().signum() < 0  )
                    {
                     em.createNativeQuery("begin "
                          + "nap_hl7_tools.wgraj_stan_zyw_w_dniu_plan("
                          + "'" + kierKosztow.getIdKierunekKosztow() + "'"
                          + ",'" + s.getGrupaZywionych() + "'"
                          + ",'" + s.getDietaNazwa() + "'"
                          + ",to_date('" + s.getdObr().toString().substring(0, 10) + "','YYYY-MM-DD')"
                          + ",'" + 3 + "'"
                          + "," + zero + "," + user.getIdOperator() + ");"
                          + " end;").executeUpdate(); 
                    }
                }
                 
                 
                 //kolacja
                if ( s.getkPil() != null )
                {
                    if (  s.getkPil().signum() < 0 )
                    {
                       em.createNativeQuery("begin "
                            + "nap_hl7_tools.wgraj_stan_zyw_w_dniu_plan("
                            + "'" + kierKosztow.getIdKierunekKosztow() + "'"
                            + ",'" + s.getGrupaZywionych() + "'"
                            + ",'" + s.getDietaNazwa() + "'"
                            + ",to_date('" + s.getdObr().toString().substring(0, 10) + "','YYYY-MM-DD')"
                            + ",'" + 5 + "'"
                            + "," + zero + "," + user.getIdOperator() + ");"
                            + " end;").executeUpdate(); 
                    }
                }
                 
                 
                 
                 tx.commit();
                 

              
            } catch ( Exception e) {
                tx.rollback();
                e.printStackTrace();
                Messagebox.show(e.toString());
            }
            
        }
        
        
        return "OK";
    }
    */
    


    
    public String zapiszStanZywionychWDniu2( List<StanZywionychNaDzienDTO> stany, KierunekKosztowVO kierKosztow, String czyKorekta )
    {
        int ileWierszy = stany.size();
        int i = 1;
        
        EntityTransaction tx = em.getTransaction();
        
        for(StanZywionychNaDzienDTO s : stany )
        {
            
            
             try {
                 
                System.out.print("Zapisuje dane, wgrałem już " + i * 100 / ileWierszy); 
                 
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
                                
                              + "," + user.getIdOperator() 
                              + ",'" + czyKorekta + "'"
                              + ");"
                              + " end;").executeUpdate(); 
                    
                
                
                  tx.commit();
                  
                  if ( i == ileWierszy )
                  {
                      aktualizujStanZywionychPoWgraniu( kierKosztow, s.getIdGrupaZywionych(), s.getdObr().toString().substring(0, 10) );
                  }
                 

              
            } catch ( Exception e) {
                tx.rollback();
                e.printStackTrace();
                Messagebox.show(e.toString());
            }
            i++;
        }
        

        return "OK";
    }
    
    
    public String aktualizujStanZywionychPoWgraniu(KierunekKosztowVO kierKosztow, BigDecimal idGrupaZywionch, String data)
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
             // TODO - zobacz jak Piotrek robi duże zapytania 
             Query query =  em.createNativeQuery("select d_obr, id_dieta, dieta_nazwa\n" +
"                  , SP_il, DSP_il, OP_il, PP_il, KP_il, PNP_il,\n" +
"                    SK1_il, DSK1_il, OK1_il, PK1_il, KK1_il, PNK1_il, lp  from \n" +
"					(\n" +
"                    select sz.d_obr, d.id_dieta, \n" +
"                    dieta_kod, dieta_nazwa, posilek||' '||typ_stan_zywionych posilek, sum(szp.ilosc) ilosc, d.lp lp \n" +
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
"					group by sz.d_obr, d.id_dieta, dieta_kod, dieta_nazwa, posilek||' '||typ_stan_zywionych, d.lp\n" +
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
                               , new BigDecimal( String.valueOf(i) ) );
               
               stanZywionych.add(stan);
               
               i++;
             }
                                                                                  
        } catch ( Exception e) {
            e.printStackTrace();
            Messagebox.show(e.toString());
        }
        
        return stanZywionych;
    
    }
    
    

}
    
