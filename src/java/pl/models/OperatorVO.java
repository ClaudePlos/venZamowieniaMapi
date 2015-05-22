/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OperatorVO.findAll", query = "SELECT o FROM OperatorVO o"),
    @NamedQuery(name = "OperatorVO.findByIdOperator", query = "SELECT o FROM OperatorVO o WHERE o.idOperator = :idOperator"),
    @NamedQuery(name = "OperatorVO.findByIdRola", query = "SELECT o FROM OperatorVO o WHERE o.idRola = :idRola"),
    @NamedQuery(name = "OperatorVO.findByKod", query = "SELECT o FROM OperatorVO o WHERE o.kod = :kod"),
    @NamedQuery(name = "OperatorVO.findByHaslo", query = "SELECT o FROM OperatorVO o WHERE o.haslo = :haslo"),
    @NamedQuery(name = "OperatorVO.findByAktywne", query = "SELECT o FROM OperatorVO o WHERE o.aktywne = :aktywne"),
    @NamedQuery(name = "OperatorVO.findByUwagi", query = "SELECT o FROM OperatorVO o WHERE o.uwagi = :uwagi"),
    @NamedQuery(name = "OperatorVO.findBySpid", query = "SELECT o FROM OperatorVO o WHERE o.spid = :spid"),
    @NamedQuery(name = "OperatorVO.findByLokalnyKatalogRoboczy", query = "SELECT o FROM OperatorVO o WHERE o.lokalnyKatalogRoboczy = :lokalnyKatalogRoboczy"),
    @NamedQuery(name = "OperatorVO.findByLokalnyKatalogDlaPobierZal", query = "SELECT o FROM OperatorVO o WHERE o.lokalnyKatalogDlaPobierZal = :lokalnyKatalogDlaPobierZal"),
    @NamedQuery(name = "OperatorVO.findByLokalnyKatalogDlaWyslanZal", query = "SELECT o FROM OperatorVO o WHERE o.lokalnyKatalogDlaWyslanZal = :lokalnyKatalogDlaWyslanZal"),
    @NamedQuery(name = "OperatorVO.findByHasloInt", query = "SELECT o FROM OperatorVO o WHERE o.hasloInt = :hasloInt"),
    @NamedQuery(name = "OperatorVO.findByUzytkownikDomenowy", query = "SELECT o FROM OperatorVO o WHERE o.uzytkownikDomenowy = :uzytkownikDomenowy")})
 * 
 * @author k.skowronski
 */
@Entity
@Table(name = "OPERATORZY")
public class OperatorVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_OPERATOR")
    private BigDecimal idOperator;
    @Column(name = "ID_ROLA")
    private Long idRola;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "KOD")
    private String kod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "HASLO")
    private String haslo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AKTYWNE")
    private long aktywne;
    @Size(max = 250)
    @Column(name = "UWAGI")
    private String uwagi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPID")
    private long spid;
    @Size(max = 125)
    @Column(name = "LOKALNY_KATALOG_ROBOCZY")
    private String lokalnyKatalogRoboczy;
    @Size(max = 125)
    @Column(name = "LOKALNY_KATALOG_DLA_POBIER_ZAL")
    private String lokalnyKatalogDlaPobierZal;
    @Size(max = 125)
    @Column(name = "LOKALNY_KATALOG_DLA_WYSLAN_ZAL")
    private String lokalnyKatalogDlaWyslanZal;
    @Column(name = "HASLO_INT")
    private Long hasloInt;
    @Size(max = 125)
    @Column(name = "UZYTKOWNIK_DOMENOWY")
    private String uzytkownikDomenowy;


   

    public OperatorVO() {
    }

    public OperatorVO(BigDecimal idOperator) {
        this.idOperator = idOperator;
    }

    public OperatorVO(BigDecimal idOperator, String kod, String haslo, long aktywne, long spid) {
        this.idOperator = idOperator;
        this.kod = kod;
        this.haslo = haslo;
        this.aktywne = aktywne;
        this.spid = spid;
    }

    public BigDecimal getIdOperator() {
        return idOperator;
    }

    public void setIdOperator(BigDecimal idOperator) {
        this.idOperator = idOperator;
    }

    public Long getIdRola() {
        return idRola;
    }

    public void setIdRola(Long idRola) {
        this.idRola = idRola;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public long getAktywne() {
        return aktywne;
    }

    public void setAktywne(long aktywne) {
        this.aktywne = aktywne;
    }

    public String getUwagi() {
        return uwagi;
    }

    public void setUwagi(String uwagi) {
        this.uwagi = uwagi;
    }

    public long getSpid() {
        return spid;
    }

    public void setSpid(long spid) {
        this.spid = spid;
    }

    public String getLokalnyKatalogRoboczy() {
        return lokalnyKatalogRoboczy;
    }

    public void setLokalnyKatalogRoboczy(String lokalnyKatalogRoboczy) {
        this.lokalnyKatalogRoboczy = lokalnyKatalogRoboczy;
    }

    public String getLokalnyKatalogDlaPobierZal() {
        return lokalnyKatalogDlaPobierZal;
    }

    public void setLokalnyKatalogDlaPobierZal(String lokalnyKatalogDlaPobierZal) {
        this.lokalnyKatalogDlaPobierZal = lokalnyKatalogDlaPobierZal;
    }

    public String getLokalnyKatalogDlaWyslanZal() {
        return lokalnyKatalogDlaWyslanZal;
    }

    public void setLokalnyKatalogDlaWyslanZal(String lokalnyKatalogDlaWyslanZal) {
        this.lokalnyKatalogDlaWyslanZal = lokalnyKatalogDlaWyslanZal;
    }

    public Long getHasloInt() {
        return hasloInt;
    }

    public void setHasloInt(Long hasloInt) {
        this.hasloInt = hasloInt;
    }

    public String getUzytkownikDomenowy() {
        return uzytkownikDomenowy;
    }

    public void setUzytkownikDomenowy(String uzytkownikDomenowy) {
        this.uzytkownikDomenowy = uzytkownikDomenowy;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOperator != null ? idOperator.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OperatorVO)) {
            return false;
        }
        OperatorVO other = (OperatorVO) object;
        if ((this.idOperator == null && other.idOperator != null) || (this.idOperator != null && !this.idOperator.equals(other.idOperator))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.models.OperatorVO[ idOperator=" + idOperator + " ]";
    }

   
    
}
