/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.authentication;

import java.io.Serializable;
import pl.services.AuthenticationService;
import pl.services.UserCredential;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;


/**
 *
 * @author k.skowronski
 */
public class AuthenticationServiceChapter5Impl implements AuthenticationService,Serializable{
    
    public UserCredential getUserCredential(){
        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential)sess.getAttribute("userCredential");
        if(cre==null){
            cre = new UserCredential();//new a anonymous user and set to session
            sess.setAttribute("userCredential",cre);
        }
        return cre;
    }
    
    public boolean login(String nm, String pd) {
		// will be implemented in chapter 8
		return false;
	}


	public void logout() {
		// will be implemented in chapter 8
	}
    
}
