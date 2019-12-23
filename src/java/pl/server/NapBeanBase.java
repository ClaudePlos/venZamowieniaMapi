package pl.server;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.persistence.EntityManagerFactory;
import javax.transaction.TransactionSynchronizationRegistry;

/**
 *
 * @author k.skowronski
 */
@Stateful
public class NapBeanBase {
    
//    @PersistenceContext(name = "venZamowieniaMapiPU2",unitName = "venZamowieniaMapiPU2")
//    protected EntityManager em;
    
    EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("venZamowieniaMapiPU2");
    protected EntityManager em = emf.createEntityManager();
    
    public static String CTX_KOMPUTER   = "INAPRZOD_CTX_KOMPUTER";

    @Resource
    protected TransactionSynchronizationRegistry transactionRegistry;
    
  
    
}
