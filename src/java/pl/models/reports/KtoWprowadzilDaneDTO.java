/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.models.reports;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author k.skowronski
 */
public class KtoWprowadzilDaneDTO {
    
    private String operator;
    private Date dZmiany;
    private String gz;
    private Date dObrotu;
    private String dieta;
    private String posilek;
    private BigDecimal ilosc;

    public KtoWprowadzilDaneDTO(String operator, Date dZmiany, String gz, Date dObrotu, String dieta, String posilek, BigDecimal ilosc) {
        this.operator = operator;
        this.dZmiany = dZmiany;
        this.gz = gz;
        this.dObrotu = dObrotu;
        this.dieta = dieta;
        this.posilek = posilek;
        this.ilosc = ilosc;
    }
    
    

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getdZmiany() {
        return dZmiany;
    }

    public void setdZmiany(Date dZmiany) {
        this.dZmiany = dZmiany;
    }

    public String getGz() {
        return gz;
    }

    public void setGz(String gz) {
        this.gz = gz;
    }

    public Date getdObrotu() {
        return dObrotu;
    }

    public void setdObrotu(Date dObrotu) {
        this.dObrotu = dObrotu;
    }

    public String getDieta() {
        return dieta;
    }

    public void setDieta(String dieta) {
        this.dieta = dieta;
    }

    public String getPosilek() {
        return posilek;
    }

    public void setPosilek(String posilek) {
        this.posilek = posilek;
    }

    public BigDecimal getIlosc() {
        return ilosc;
    }

    public void setIlosc(BigDecimal ilosc) {
        this.ilosc = ilosc;
    }
    
    
    
}
