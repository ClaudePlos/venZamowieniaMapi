/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.reports;

import java.math.BigDecimal;
import javax.ejb.EJB;
import pl.session.ServiceReports;
import java.io.IOException;

/**
 *
 * @author k.skowronski
 */
public class WydPosOddzialyMC {
    
    
    @EJB
    ServiceReports serviceReports = ServiceReports.getInstance();
    
    
    public void IlzywWgOddPodDiety()
    {
        
    }
    
    public void zapiszPDF( String okres, BigDecimal kierunekKosztow, String posilek, String kkNazwa ) throws IOException, Exception{
        
        System.out.print("Pobierma dane w IlzywWgOddPodDiety");
        
    }
    
}
