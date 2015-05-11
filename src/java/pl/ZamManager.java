/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.models.OperatorVO;

/**
 *
 * @author k.skowronski
 */

@Stateless
public class ZamManager implements Serializable {
    
    transient static Logger logger; 
    
    private List<Object> listRegiony;
    
    private static volatile ZamManager instance = null;
	
    //private static final EntityManagerFactory emfInstance =
	//	        Persistence.createEntityManagerFactory("venZamowieniaMapiPU");
    
    //private EntityManager em;
    
    @PersistenceContext(unitName = "venZamowieniaMapiPU2")
    protected EntityManager em;
    

    //@PersistenceContext(name="venZamowieniaMapiPU",unitName="venZamowieniaMapiPU")
    //private EntityManager em;
    
   // EntityManager em = JpaUtil.getEntityManager();
    
    @PostConstruct
    public void init()
    {
    	logger = Logger.getLogger( this.getClass().getCanonicalName() ) ;    	
    	logger.info("Init Start"); 
    }
    
    public static ZamManager getInstance()
    {
        if (instance == null)
        {
            instance = new ZamManager();
        }
        
        return instance;
    }
    
    
    public ZamManager()
    {
        super();  
     //   em = emfInstance.createEntityManager();
    }
    
    
    public List<OperatorVO> listaOperatorow()
    {
        
      List<OperatorVO> users = new ArrayList<OperatorVO>();
      
     // List<Object> usersOb = em.createQuery("SELECT u FROM OperatorVO u").getResultList();
       List<Object> usersOb = em.createNativeQuery("SELECT id_operator, kod, haslo FROM operatorzy").getResultList();
      
      
      for ( Object u : usersOb)
      {
          OperatorVO op = new OperatorVO();
          op.setLogin(u.toString());
          users.add(op);
      }
       
       return users;
    }
    
}
