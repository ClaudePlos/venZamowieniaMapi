/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.authentication;


import javax.ejb.EJB;
import pl.services.AuthenticationService;
import pl.services.UserCredential;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;



/**
 *
 * @author k.skowronski
 */
public class LoginController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	//wire components
	@Wire
	Textbox account;
	@Wire
	Textbox password;
	@Wire
	Label message;
	
	//services
	AuthenticationService authService = new AuthenticationServiceChapter8Impl();
        
       
	
	@Listen("onClick=#login; onOK=#loginWin")
	public void doLogin(){
		String nm = account.getValue();
		String pd = password.getValue();
		
		if(!authService.login(nm,pd)){
			message.setValue("Login lu hasło są niepoprawne.");
			return;
		}
                
          
                
		UserCredential cre= authService.getUserCredential();
		message.setValue("Welcome, "+cre.getName());
		message.setSclass("");
		
		Executions.sendRedirect("/zamowienia/");
		
	}
}
