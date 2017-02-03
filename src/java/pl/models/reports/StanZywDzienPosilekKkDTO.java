/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.models.reports;

import java.math.BigDecimal;

/**
 *
 * @author k.skowronski
 */
public class StanZywDzienPosilekKkDTO {
    
    private String gz;
    private String dieta;
    private BigDecimal ilosc;
    
    
    public StanZywDzienPosilekKkDTO(String gz, String dieta, BigDecimal ilosc) {
        this.gz = gz;
        this.dieta = dieta;
        this.ilosc = ilosc;
    }

    public String getGz() {
        return gz;
    }

    public void setGz(String gz) {
        this.gz = gz;
    }

    public String getDieta() {
        return dieta;
    }

    public void setDieta(String dieta) {
        this.dieta = dieta;
    }

    public BigDecimal getIlosc() {
        return ilosc;
    }

    public void setIlosc(BigDecimal ilosc) {
        this.ilosc = ilosc;
    }
    
    
    
}
