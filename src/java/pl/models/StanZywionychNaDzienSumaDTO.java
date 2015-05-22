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
public class StanZywionychNaDzienSumaDTO {
    
    private BigDecimal sPilSum;
    private BigDecimal oPilSum;
    private BigDecimal kPilSum;
    private BigDecimal sK1ilSum;
    private BigDecimal oK1ilSum;
    private BigDecimal kK1ilSum;

    public BigDecimal getsPilSum() {
        return sPilSum;
    }

    public void setsPilSum(BigDecimal sPilSum) {
        this.sPilSum = sPilSum;
    }

    public BigDecimal getoPilSum() {
        return oPilSum;
    }

    public void setoPilSum(BigDecimal oPilSum) {
        this.oPilSum = oPilSum;
    }

    public BigDecimal getkPilSum() {
        return kPilSum;
    }

    public void setkPilSum(BigDecimal kPilSum) {
        this.kPilSum = kPilSum;
    }

    public BigDecimal getsK1ilSum() {
        return sK1ilSum;
    }

    public void setsK1ilSum(BigDecimal sK1ilSum) {
        this.sK1ilSum = sK1ilSum;
    }

    public BigDecimal getoK1ilSum() {
        return oK1ilSum;
    }

    public void setoK1ilSum(BigDecimal oK1ilSum) {
        this.oK1ilSum = oK1ilSum;
    }

    public BigDecimal getkK1ilSum() {
        return kK1ilSum;
    }

    public void setkK1ilSum(BigDecimal kK1ilSum) {
        this.kK1ilSum = kK1ilSum;
    }
    
    
    
}
