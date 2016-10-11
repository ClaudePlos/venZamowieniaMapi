/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author k.skowronski
 */

@Entity
@Table(name = "s_kierunki_kosztow")

public class KierunekKosztowVO implements Serializable {
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_KIERUNEK_KOSZTOW")
    private BigDecimal idKierunekKosztow;

    @Column(name = "KIERUNEK_KOSZTOW")
    private String kierunekKosztowNazwa;
    
    @Column(name = "KIERUNEK_KOSZTOW_KOD")
    private String kierunekKosztowKod;
    
    
    private List<GrupaZywionychVO> grupyZywionych;
    
    private String sqlGrupyZywionych;
    
    

    public BigDecimal getIdKierunekKosztow() {
        return idKierunekKosztow;
    }

    public void setIdKierunekKosztow(BigDecimal idKierunekKosztow) {
        this.idKierunekKosztow = idKierunekKosztow;
    }

    public String getKierunekKosztowNazwa() {
        return kierunekKosztowNazwa;
    }

    public void setKierunekKosztowNazwa(String kierunekKosztowNazwa) {
        this.kierunekKosztowNazwa = kierunekKosztowNazwa;
    }

   

    public String getKierunekKosztowKod() {
        return kierunekKosztowKod;
    }

    public void setKierunekKosztowKod(String kierunekKosztowKod) {
        this.kierunekKosztowKod = kierunekKosztowKod;
    }

    public List<GrupaZywionychVO> getGrupyZywionych() {
        return grupyZywionych;
    }

    public void setGrupyZywionych(List<GrupaZywionychVO> grupyZywionych) {
        this.grupyZywionych = grupyZywionych;
    }

    public String getSqlGrupyZywionych() {
        return sqlGrupyZywionych;
    }

    public void setSqlGrupyZywionych(String sqlGrupyZywionych) {
        this.sqlGrupyZywionych = sqlGrupyZywionych;
    }
    
    
    
    
    
}
