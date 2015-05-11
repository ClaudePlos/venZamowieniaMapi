/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author k.skowronski
 */

@Stateless
public class ZamMapi {
    
    @PersistenceUnit(name = "venZamowieniaMapiPU2", unitName = "venZamowieniaMapiPU2")
    EntityManagerFactory emfProd;
    
    
    @Inject
     ZamManager manager;
}
