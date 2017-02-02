/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.reports;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import pl.session.ServiceReports;

/**
 *
 * @author k.skowronski
 */
public class IlzywWgOddPodDiety {
    
    @EJB
    ServiceReports serviceReports = ServiceReports.getInstance();
    
    
    public void IlzywWgOddPodDiety()
    {
        
    }
    
    public void zapiszPDF( String okres ) throws IOException, Exception{
        
        System.out.print("Pobierma dane w IlzywWgOddPodDiety");
        
        List<Object[]> stanyKK = serviceReports.pobierzStanZywionychDzienPozilekKK(okres, 1231);
        
        
        for ( Object[] s : stanyKK)
        {
          System.out.println( (String) s[0] );
        }
        
        
    }
    
    
}
