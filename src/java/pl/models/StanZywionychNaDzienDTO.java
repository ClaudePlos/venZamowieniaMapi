/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.models;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Transient;
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
    private String szUwagi;
    
    private Boolean sVisible = true; 
    private Boolean s2Visible = true;
    private Boolean oVisible = true;
    private Boolean pVisible = true;
    private Boolean kVisible = true;
    private Boolean pnVisible = true;
    
    private Boolean ksVisible = true; 
    private Boolean ks2Visible = true;
    private Boolean koVisible = true;
    private Boolean kpVisible = true;
    private Boolean kkVisible = true;
    private Boolean kpnVisible = true;
    
    
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
     , BigDecimal lp
     , String szUwagi) {
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
        this.szUwagi = szUwagi;
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

    public String getSzUwagi() {
        return szUwagi;
    }

    public void setSzUwagi(String szUwagi) {
        this.szUwagi = szUwagi;
    }

    public Boolean getsVisible() {
        return sVisible;
    }

    public void setsVisible(Boolean sVisible) {
        this.sVisible = sVisible;
    }

    public Boolean getS2Visible() {
        return s2Visible;
    }

    public void setS2Visible(Boolean s2Visible) {
        this.s2Visible = s2Visible;
    }

    public Boolean getoVisible() {
        return oVisible;
    }

    public void setoVisible(Boolean oVisible) {
        this.oVisible = oVisible;
    }

    public Boolean getpVisible() {
        return pVisible;
    }

    public void setpVisible(Boolean pVisible) {
        this.pVisible = pVisible;
    }

    public Boolean getkVisible() {
        return kVisible;
    }

    public void setkVisible(Boolean kVisible) {
        this.kVisible = kVisible;
    }

    public Boolean getPnVisible() {
        return pnVisible;
    }

    public void setPnVisible(Boolean pnVisible) {
        this.pnVisible = pnVisible;
    }

    public Boolean getKsVisible() {
        return ksVisible;
    }

    public void setKsVisible(Boolean ksVisible) {
        this.ksVisible = ksVisible;
    }

    public Boolean getKs2Visible() {
        return ks2Visible;
    }

    public void setKs2Visible(Boolean ks2Visible) {
        this.ks2Visible = ks2Visible;
    }

    public Boolean getKoVisible() {
        return koVisible;
    }

    public void setKoVisible(Boolean koVisible) {
        this.koVisible = koVisible;
    }

    public Boolean getKpVisible() {
        return kpVisible;
    }

    public void setKpVisible(Boolean kpVisible) {
        this.kpVisible = kpVisible;
    }

    public Boolean getKkVisible() {
        return kkVisible;
    }

    public void setKkVisible(Boolean kkVisible) {
        this.kkVisible = kkVisible;
    }

    public Boolean getKpnVisible() {
        return kpnVisible;
    }

    public void setKpnVisible(Boolean kpnVisible) {
        this.kpnVisible = kpnVisible;
    }
    
    

    

  
    
    
    
    
}
