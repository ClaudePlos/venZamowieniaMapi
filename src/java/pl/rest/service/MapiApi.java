package pl.rest.service;

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
    public List<JadlospisViewVO> getInfAboutJadlospisForDiet(Long dietId , String forDay) throws Exception
    {
         try{
            List<JadlospisViewVO> ret = em.createQuery("select j from JadlospisViewVO j where j.dObr = :dObr and j.idDieta = :dietId")
                    .setParameter("dietId", dietId).setParameter("dObr", dtYYYYMMDD.parse(forDay))
                    .getResultList();
            return ret;
        }
        catch (Exception e){
            throw new Exception("I have problem get list getInfAboutJadlospisForDiet for: dietId: " + dietId + " " + e.getMessage());
        }
    }
    
}
