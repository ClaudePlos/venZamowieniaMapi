package pl.rest.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import pl.models.JadlospisSkladnikiViewVO;
import pl.models.JadlospisViewVO;

/**
 *
 * @author k.skowronski
 */
@Stateless
@LocalBean
public class MapiApi {
    
    private static final EntityManagerFactory emfInstance =
		        Persistence.createEntityManagerFactory("venZamowieniaMapiPU2");
    private EntityManager em;
    
    
    public MapiApi() {
        em = emfInstance.createEntityManager();
    }
    
    private SimpleDateFormat dtYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
    
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<JadlospisViewVO> getInfWartoscOdzywczaForDiet(Long dietId , String forDay) throws Exception
    {
         try{
            List<JadlospisViewVO> ret = em.createQuery("select j from JadlospisViewVO j where j.dObr = :dObr and j.idDieta = :dietId")
                    .setParameter("dietId", dietId).setParameter("dObr", dtYYYYMMDD.parse(forDay))
                    .getResultList();
            return ret;
        }
        catch (Exception e){
            throw new Exception("I have problem get list getInfWartoscOdzywczaForDiet for: dietId: " + dietId + " " + e.getMessage());
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<JadlospisSkladnikiViewVO> getInfAboutJadlospisForDiet(BigDecimal jadlospisId ) throws Exception
    {        
         try{
        
                List<JadlospisSkladnikiViewVO> jsL = em.createQuery("select js from JadlospisSkladnikiViewVO js where js.idJadlospis = :idJadlospis")
                    .setParameter("idJadlospis", jadlospisId)
                    .getResultList();
            return jsL;
        }
        catch (Exception e){
            throw new Exception("I have problem get list getInfAboutJadlospisForDiet for: jadlospisId: " + jadlospisId + " " + e.getMessage());
        }
    }
    
}
