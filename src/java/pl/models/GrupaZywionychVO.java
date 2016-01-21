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
 * @author k.skowronski
 */
@Entity
@Table(name = "GRUPY_ZYWIONYCH")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupaZywionychVO.findAll", query = "SELECT g FROM GrupaZywionychVO g"),
    @NamedQuery(name = "GrupaZywionychVO.findByIdGrupaZywionych", query = "SELECT g FROM GrupaZywionychVO g WHERE g.idGrupaZywionych = :idGrupaZywionych"),
    @NamedQuery(name = "GrupaZywionychVO.findByIdKuchnia", query = "SELECT g FROM GrupaZywionychVO g WHERE g.idKuchnia = :idKuchnia"),
    @NamedQuery(name = "GrupaZywionychVO.findByGrupaZywionych", query = "SELECT g FROM GrupaZywionychVO g WHERE g.grupaZywionych = :grupaZywionych"),
    @NamedQuery(name = "GrupaZywionychVO.findByGrupaZywionychKod", query = "SELECT g FROM GrupaZywionychVO g WHERE g.grupaZywionychKod = :grupaZywionychKod"),
    @NamedQuery(name = "GrupaZywionychVO.findByLp", query = "SELECT g FROM GrupaZywionychVO g WHERE g.lp = :lp"),
    @NamedQuery(name = "GrupaZywionychVO.findByDomyslne", query = "SELECT g FROM GrupaZywionychVO g WHERE g.domyslne = :domyslne"),
    @NamedQuery(name = "GrupaZywionychVO.findByAktywne", query = "SELECT g FROM GrupaZywionychVO g WHERE g.aktywne = :aktywne"),
    @NamedQuery(name = "GrupaZywionychVO.findByUwagi", query = "SELECT g FROM GrupaZywionychVO g WHERE g.uwagi = :uwagi")})
public class GrupaZywionychVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_GRUPA_ZYWIONYCH")
    private BigDecimal idGrupaZywionych;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_KUCHNIA")
    private long idKuchnia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 125)
    @Column(name = "GRUPA_ZYWIONYCH")
    private String grupaZywionych;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "GRUPA_ZYWIONYCH_KOD")
    private String grupaZywionychKod;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LP")
    private long lp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DOMYSLNE")
    private long domyslne;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AKTYWNE")
    private long aktywne;
    @Size(max = 250)
    @Column(name = "UWAGI")
    private String uwagi;
    @OneToMany(mappedBy = "idOjca")
    private Collection<GrupaZywionychVO> grupaZywionychVOCollection;
    @JoinColumn(name = "ID_OJCA", referencedColumnName = "ID_GRUPA_ZYWIONYCH")
    @ManyToOne
    private GrupaZywionychVO idOjca;
    
    
     

    public GrupaZywionychVO() {
    }

    public GrupaZywionychVO(BigDecimal idGrupaZywionych) {
        this.idGrupaZywionych = idGrupaZywionych;
    }

    public GrupaZywionychVO(BigDecimal idGrupaZywionych, long idKuchnia, String grupaZywionych, String grupaZywionychKod, long lp, long domyslne, long aktywne) {
        this.idGrupaZywionych = idGrupaZywionych;
        this.idKuchnia = idKuchnia;
        this.grupaZywionych = grupaZywionych;
        this.grupaZywionychKod = grupaZywionychKod;
        this.lp = lp;
        this.domyslne = domyslne;
        this.aktywne = aktywne;
    }

    public BigDecimal getIdGrupaZywionych() {
        return idGrupaZywionych;
    }

    public void setIdGrupaZywionych(BigDecimal idGrupaZywionych) {
        this.idGrupaZywionych = idGrupaZywionych;
    }

    public long getIdKuchnia() {
        return idKuchnia;
    }

    public void setIdKuchnia(long idKuchnia) {
        this.idKuchnia = idKuchnia;
    }

    public String getGrupaZywionych() {
        return grupaZywionych;
    }

    public void setGrupaZywionych(String grupaZywionych) {
        this.grupaZywionych = grupaZywionych;
    }

    public String getGrupaZywionychKod() {
        return grupaZywionychKod;
    }

    public void setGrupaZywionychKod(String grupaZywionychKod) {
        this.grupaZywionychKod = grupaZywionychKod;
    }

    public long getLp() {
        return lp;
    }

    public void setLp(long lp) {
        this.lp = lp;
    }

    public long getDomyslne() {
        return domyslne;
    }

    public void setDomyslne(long domyslne) {
        this.domyslne = domyslne;
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

    @XmlTransient
    public Collection<GrupaZywionychVO> getGrupaZywionychVOCollection() {
        return grupaZywionychVOCollection;
    }

    public void setGrupaZywionychVOCollection(Collection<GrupaZywionychVO> grupaZywionychVOCollection) {
        this.grupaZywionychVOCollection = grupaZywionychVOCollection;
    }

    public GrupaZywionychVO getIdOjca() {
        return idOjca;
    }

    public void setIdOjca(GrupaZywionychVO idOjca) {
        this.idOjca = idOjca;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGrupaZywionych != null ? idGrupaZywionych.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupaZywionychVO)) {
            return false;
        }
        GrupaZywionychVO other = (GrupaZywionychVO) object;
        if ((this.idGrupaZywionych == null && other.idGrupaZywionych != null) || (this.idGrupaZywionych != null && !this.idGrupaZywionych.equals(other.idGrupaZywionych))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.authentication.GrupaZywionychVO[ idGrupaZywionych=" + idGrupaZywionych + " ]";
    }

   
    
}
