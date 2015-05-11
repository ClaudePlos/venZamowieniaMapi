/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.views.zam;

import java.util.List;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Messagebox;
import pl.models.OperatorVO;
import pl.session.OperatorBean;

/**
 *
 * @author k.skowronski
 */
public class MenuController extends SelectorComposer {
    
    
    
    @Listen("onClick = button#logout")
    public void showHi() {
        //
        
        OperatorBean bean = new OperatorBean();
        
        bean.listaOpratorow();
        
        Messagebox.show("OK");
    }
    
}
