/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.session;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import pl.models.OperatorVO;
import pl.models.User;

/**
 *
 * @author k.skowronski
 */
@Stateless
public class OperatorVOFacade {
    
    
    private static final EntityManagerFactory emfInstance =
		        Persistence.createEntityManagerFactory("venZamowieniaMapiPU2");
    private EntityManager em;

   

    public OperatorVOFacade() {
        em = emfInstance.createEntityManager();
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
        OperatorVO usersOb = (OperatorVO) em.createQuery("SELECT u FROM OperatorVO u where u.login = " + account).getSingleResult();
        return usersOb;
    }
    
}
