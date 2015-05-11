/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.authentication;

/**
 *
 * @author k.skowronski
 */
import javax.ejb.EJB;
import pl.authentication.AuthenticationServiceChapter5Impl;
import pl.other.UserInfoServiceChapter5Impl;
import pl.models.User;
import pl.services.UserCredential;
import pl.services.UserInfoService;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import pl.models.OperatorVO;
import pl.session.OperatorVOFacade;

public class AuthenticationServiceChapter8Impl extends AuthenticationServiceChapter5Impl{
	private static final long serialVersionUID = 1L;
	
	UserInfoService userInfoService = new UserInfoServiceChapter5Impl();
        
        
        @EJB 
        OperatorVOFacade operatorVOFacade = new OperatorVOFacade();
	
	@Override
	public boolean login(String nm, String pd) {
		OperatorVO user = operatorVOFacade.findUser(nm);
		//a simple plan text password verification
		if(user==null || !user.getHaslo().equals(pd)){
			return false;
		}
		
		Session sess = Sessions.getCurrent();
		UserCredential cre = new UserCredential(user.getLogin(),user.getLogin());
		//just in case for this demo.
		if(cre.isAnonymous()){
			return false;
		}
		sess.setAttribute("userCredential",cre);
		
		//TODO handle the role here for authorization
		return true;
	}
	
	@Override
	public void logout() {
		Session sess = Sessions.getCurrent();
		sess.removeAttribute("userCredential");
	}
}
