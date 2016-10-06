/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.modelsView;

import java.util.Date;
import javax.ejb.EJB;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;
import pl.session.ServiceFacade;

/**
 *
 * @author k.skowronski
 */
public class StanZywionychReportsVM {
    
    @EJB 
    ServiceFacade serviceFacade = ServiceFacade.getInstance();
    
    
    @Command
    @NotifyChange("stanyZywionychReprots")
    public void stanZywionychReports(@BindingParam("naDzien") Date naDzien, @BindingParam("kierKosztow") int kierKosztow){
        
        Window window = (Window)Executions.createComponents(
                "/jasperreport.zul", null, null);
        window.doModal();
        
        Listbox listBoxR = new Listbox();
        listBoxR.setHeight("450px");
        
        Label test = new Label();
        test.setValue("Test ks dzis");
        
        window.appendChild(test);
        window.appendChild(listBoxR);
    
    }
    
    
}
