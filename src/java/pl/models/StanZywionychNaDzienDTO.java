/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.models;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author k.skowronski
 */

public class StanZywionychNaDzienDTO {
    
    private Timestamp dObr;
    private BigDecimal idGrupaZywionych;
    private BigDecimal idDieta;
    private String dietaKod;
    private String dietaNazwa;
    private String grupaZywionych;
    private BigDecimal sPil;
    private BigDecimal oPil;
    private BigDecimal kPil;
    private BigDecimal sK1il;
    private BigDecimal oK1il;
    private BigDecimal kK1il;
    

    public StanZywionychNaDzienDTO(Timestamp dObr, BigDecimal idGrupaZywionych, BigDecimal idDieta, String dietaKod, String dietaNazwa, String grupaZywionych, BigDecimal sPil, BigDecimal oPil, BigDecimal kPil, BigDecimal sK1il, BigDecimal oK1il, BigDecimal kK1il) {
        this.dObr = dObr;
        this.idGrupaZywionych = idGrupaZywionych;
        this.idDieta = idDieta;
        this.dietaKod = dietaKod;
        this.dietaNazwa = dietaNazwa;
        this.grupaZywionych = grupaZywionych;
        this.sPil = sPil;
        this.oPil = oPil;
        this.kPil = kPil;
        this.sK1il = sK1il;
        this.oK1il = oK1il;
        this.kK1il = kK1il;
    }

    public Timestamp getdObr() {
        return dObr;
    }

    public void setdObr(Timestamp dObr) {
        this.dObr = dObr;
    }

    public BigDecimal getIdGrupaZywionych() {
        return idGrupaZywionych;
    }

    public void setIdGrupaZywionych(BigDecimal idGrupaZywionych) {
        this.idGrupaZywionych = idGrupaZywionych;
    }

    public BigDecimal getIdDieta() {
        return idDieta;
    }

    public void setIdDieta(BigDecimal idDieta) {
        this.idDieta = idDieta;
    }

    public String getDietaKod() {
        return dietaKod;
    }

    public void setDietaKod(String dietaKod) {
        this.dietaKod = dietaKod;
    }

    public String getDietaNazwa() {
        return dietaNazwa;
    }

    public void setDietaNazwa(String dietaNazwa) {
        this.dietaNazwa = dietaNazwa;
    }

    public String getGrupaZywionych() {
        return grupaZywionych;
    }

    public void setGrupaZywionych(String grupaZywionych) {
        this.grupaZywionych = grupaZywionych;
    }

    public BigDecimal getsPil() {
        return sPil;
    }

    public void setsPil(BigDecimal sPil) {
        this.sPil = sPil;
    }

    public BigDecimal getoPil() {
        return oPil;
    }

    public void setoPil(BigDecimal oPil) {
        this.oPil = oPil;
    }

    public BigDecimal getkPil() {
        return kPil;
    }

    public void setkPil(BigDecimal kPil) {
        this.kPil = kPil;
    }

    public BigDecimal getsK1il() {
        return sK1il;
    }

    public void setsK1il(BigDecimal sK1il) {
        this.sK1il = sK1il;
    }

    public BigDecimal getoK1il() {
        return oK1il;
    }

    public void setoK1il(BigDecimal oK1il) {
        this.oK1il = oK1il;
    }

    public BigDecimal getkK1il() {
        return kK1il;
    }

    public void setkK1il(BigDecimal kK1il) {
        this.kK1il = kK1il;
    }


  
    
    
    
    
}
