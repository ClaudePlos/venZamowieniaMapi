/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.models;

import java.math.BigDecimal;
import java.sql.Timestamp;
/**
 *
 * @author k.skowronski
 */

public class StanZywionychNaDzienDTO {
    
    private Timestamp dObr;
    private BigDecimal idGrupaZywionych;
    private BigDecimal idDieta;
    private String dietaNazwa;
    //private String grupaZywionych;
    
    private BigDecimal sniadaniePlanIl;
    private BigDecimal drugieSniadaniePlanIl;
    private BigDecimal obiadPlanIl;
    private BigDecimal podwieczorekPlanIl;
    private BigDecimal kolacjaPlanIl;
    private BigDecimal posilekNocnyPlanIl;
    
    private BigDecimal sniadanieKorIl;
    private BigDecimal drugieSniadanieKorIl;
    private BigDecimal obiadKorIl;
    private BigDecimal podwieczorekKorIl;
    private BigDecimal kolacjaKorIl;
    private BigDecimal posilekNocnyKorIl;
    
    private BigDecimal lp;

    public StanZywionychNaDzienDTO(Timestamp dObr, BigDecimal idGrupaZywionych, BigDecimal idDieta, String dietaNazwa
     , BigDecimal sniadaniePlanIl
     , BigDecimal drugieSniadaniePlanIl
     , BigDecimal obiadPlanIl
     , BigDecimal podwieczorekPlanIl
     , BigDecimal kolacjaPlanIl
     , BigDecimal posilekNocnyPlanIl
     , BigDecimal sniadanieKorIl
     , BigDecimal drugieSniadanieKorIl
     , BigDecimal obiadKorIl
     , BigDecimal podwieczorekKorIl
     , BigDecimal kolacjaKorIl
     , BigDecimal posilekNocnyKorIl
     , BigDecimal lp) {
        this.dObr = dObr;
        this.idGrupaZywionych = idGrupaZywionych;
        this.idDieta = idDieta;
        this.dietaNazwa = dietaNazwa;
        this.sniadaniePlanIl = sniadaniePlanIl;
        this.drugieSniadaniePlanIl = drugieSniadaniePlanIl;
        this.obiadPlanIl = obiadPlanIl;
        this.podwieczorekPlanIl = podwieczorekPlanIl;
        this.kolacjaPlanIl = kolacjaPlanIl;
        this.posilekNocnyPlanIl = posilekNocnyPlanIl;
        this.sniadanieKorIl = sniadanieKorIl;
        this.drugieSniadanieKorIl = drugieSniadanieKorIl;
        this.obiadKorIl = obiadKorIl;
        this.podwieczorekKorIl = podwieczorekKorIl;
        this.kolacjaKorIl = kolacjaKorIl;
        this.posilekNocnyKorIl = posilekNocnyKorIl;
        this.lp = lp;
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

    public String getDietaNazwa() {
        return dietaNazwa;
    }

    public void setDietaNazwa(String dietaNazwa) {
        this.dietaNazwa = dietaNazwa;
    }

    public BigDecimal getSniadaniePlanIl() {
        return sniadaniePlanIl;
    }

    public void setSniadaniePlanIl(BigDecimal sniadaniePlanIl) {
        this.sniadaniePlanIl = sniadaniePlanIl;
    }

    public BigDecimal getDrugieSniadaniePlanIl() {
        return drugieSniadaniePlanIl;
    }

    public void setDrugieSniadaniePlanIl(BigDecimal drugieSniadaniePlanIl) {
        this.drugieSniadaniePlanIl = drugieSniadaniePlanIl;
    }

    public BigDecimal getObiadPlanIl() {
        return obiadPlanIl;
    }

    public void setObiadPlanIl(BigDecimal obiadPlanIl) {
        this.obiadPlanIl = obiadPlanIl;
    }

    public BigDecimal getPodwieczorekPlanIl() {
        return podwieczorekPlanIl;
    }

    public void setPodwieczorekPlanIl(BigDecimal podwieczorekPlanIl) {
        this.podwieczorekPlanIl = podwieczorekPlanIl;
    }

    public BigDecimal getKolacjaPlanIl() {
        return kolacjaPlanIl;
    }

    public void setKolacjaPlanIl(BigDecimal kolacjaPlanIl) {
        this.kolacjaPlanIl = kolacjaPlanIl;
    }

    public BigDecimal getPosilekNocnyPlanIl() {
        return posilekNocnyPlanIl;
    }

    public void setPosilekNocnyPlanIl(BigDecimal posilekNocnyPlanIl) {
        this.posilekNocnyPlanIl = posilekNocnyPlanIl;
    }

    public BigDecimal getSniadanieKorIl() {
        return sniadanieKorIl;
    }

    public void setSniadanieKorIl(BigDecimal sniadanieKorIl) {
        this.sniadanieKorIl = sniadanieKorIl;
    }

    public BigDecimal getDrugieSniadanieKorIl() {
        return drugieSniadanieKorIl;
    }

    public void setDrugieSniadanieKorIl(BigDecimal drugieSniadanieKorIl) {
        this.drugieSniadanieKorIl = drugieSniadanieKorIl;
    }

    public BigDecimal getObiadKorIl() {
        return obiadKorIl;
    }

    public void setObiadKorIl(BigDecimal obiadKorIl) {
        this.obiadKorIl = obiadKorIl;
    }

    public BigDecimal getPodwieczorekKorIl() {
        return podwieczorekKorIl;
    }

    public void setPodwieczorekKorIl(BigDecimal podwieczorekKorIl) {
        this.podwieczorekKorIl = podwieczorekKorIl;
    }

    public BigDecimal getKolacjaKorIl() {
        return kolacjaKorIl;
    }

    public void setKolacjaKorIl(BigDecimal kolacjaKorIl) {
        this.kolacjaKorIl = kolacjaKorIl;
    }

    public BigDecimal getPosilekNocnyKorIl() {
        return posilekNocnyKorIl;
    }

    public void setPosilekNocnyKorIl(BigDecimal posilekNocnyKorIl) {
        this.posilekNocnyKorIl = posilekNocnyKorIl;
    }

    public BigDecimal getLp() {
        return lp;
    }

    public void setLp(BigDecimal lp) {
        this.lp = lp;
    }

    

  
    
    
    
    
}
