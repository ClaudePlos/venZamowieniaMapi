/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.session;

import java.io.Serializable;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import org.zkoss.zul.Messagebox;

import pl.models.OperatorVO;


/**
 *
 * @author k.skowronski
 */

@ManagedBean @SessionScoped 
public class OperatorBean implements Serializable{ 
    
  
    @EJB 
    OperatorVOFacade operatorVOFacade = new OperatorVOFacade();
    
    
    
    public OperatorBean() {
      // manager = ZamManager.getInstance();        
    }

    
   
    
 
    public void listaOpratorow() {
        //

        List<OperatorVO> users = operatorVOFacade.listaOperatorow();
        
        Messagebox.show(users.toString());
    }
    
 
    
    
}
