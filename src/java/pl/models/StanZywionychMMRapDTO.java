/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.models;

import java.math.BigDecimal;

/**
 *
 * @author k.skowronski
 */
public class StanZywionychMMRapDTO {
    
    private String kk;
    private String gz;
    
    private BigDecimal sn;
    private BigDecimal dsn;
    private BigDecimal ob;
    private BigDecimal pod;
    private BigDecimal kol;
    private BigDecimal pn;
    private BigDecimal il_posilkowa;

 
    

    public String getKk() {
        return kk;
    }

    public void setKk(String kk) {
        this.kk = kk;
    }

    public String getGz() {
        return gz;
    }

    public void setGz(String gz) {
        this.gz = gz;
    }

    public BigDecimal getSn() {
        return sn;
    }

    public void setSn(BigDecimal sn) {
        this.sn = sn;
    }

    public BigDecimal getDsn() {
        return dsn;
    }

    public void setDsn(BigDecimal dsn) {
        this.dsn = dsn;
    }

    public BigDecimal getOb() {
        return ob;
    }

    public void setOb(BigDecimal ob) {
        this.ob = ob;
    }

    public BigDecimal getPod() {
        return pod;
    }

    public void setPod(BigDecimal pod) {
        this.pod = pod;
    }

    public BigDecimal getKol() {
        return kol;
    }

    public void setKol(BigDecimal kol) {
        this.kol = kol;
    }

    public BigDecimal getPn() {
        return pn;
    }

    public void setPn(BigDecimal pn) {
        this.pn = pn;
    }

    public BigDecimal getIl_posilkowa() {
        return il_posilkowa;
    }

    public void setIl_posilkowa(BigDecimal il_posilkowa) {
        this.il_posilkowa = il_posilkowa;
    }
    
    
    
    
}
