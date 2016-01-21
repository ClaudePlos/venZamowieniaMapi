/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.authentication;

import java.util.Map;

import pl.services.AuthenticationService;
import pl.services.UserCredential;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

/**
 *
 * @author k.skowronski
 */
public class AuthenticationInit implements Initiator {

	//services
	AuthenticationService authService = new AuthenticationServiceChapter8Impl();
	
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		
		UserCredential cre = authService.getUserCredential();
		if(cre==null || cre.isAnonymous()){
			Executions.sendRedirect("zul/login/login.zul");
			return;
		}
	}
}
