/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.models.reports;

import java.util.Date;
import java.util.List;
import pl.models.StanZywionychNaDzienDTO;

/**
 *
 * @author k.skowronski
 */
public class GzEventDTO {
    
    private String gzRaprot;
    private Date naDzienRaport;
    
    
    private List<StanZywionychNaDzienDTO> stanyZywionychNaDzien;

    public String getGzRaprot() {
        return gzRaprot;
    }

    public void setGzRaprot(String gzRaprot) {
        this.gzRaprot = gzRaprot;
    }

    public Date getNaDzienRaport() {
        return naDzienRaport;
    }

    public void setNaDzienRaport(Date naDzienRaport) {
        this.naDzienRaport = naDzienRaport;
    }

    public List<StanZywionychNaDzienDTO> getStanyZywionychNaDzien() {
        return stanyZywionychNaDzien;
    }

    public void setStanyZywionychNaDzien(List<StanZywionychNaDzienDTO> stanyZywionychNaDzien) {
        this.stanyZywionychNaDzien = stanyZywionychNaDzien;
    }
    
    
    
    
    
}
