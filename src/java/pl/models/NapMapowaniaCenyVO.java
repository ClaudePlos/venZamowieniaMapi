/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.models;

import java.math.BigDecimal;
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
@Table(name = "nap_mapowania_ceny")

public class NapMapowaniaCenyVO {
    
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MAPC_ID")
    private BigDecimal mapcId;
    
    @Column(name = "MAPC_RODZAJ_DIETA")
    private String mapcRodzajDieta;
    
    @Column(name = "MAPC_SNIADANIE")
    private BigDecimal mapcSniadanie;
    
    @Column(name = "MAPC_2_SNIADANIE")
    private BigDecimal mapc2Sniadanie;
    
    @Column(name = "MAPC_OBIAD")
    private BigDecimal mapcObiad;
    
    @Column(name = "MAPC_PODWIECZOREK")
    private BigDecimal mapcPodwieczorek;
    
    @Column(name = "MAPC_KOLACJA")
    private BigDecimal mapcKolacja;
    
    @Column(name = "MAPC_POSILEK_NOCNY")
    private BigDecimal mapcPosilekNocny;
    
    @Column(name = "MAPC_ZUPA_KOMPOT")
    private BigDecimal mapcZupaKompot;
    
    @Column(name = "MAPC_OPIS")
    private String mapcOpis;

    public NapMapowaniaCenyVO(BigDecimal mapcId, String mapcRodzajDieta, BigDecimal mapcSniadanie, BigDecimal mapc2Sniadanie, BigDecimal mapcObiad, BigDecimal mapcPodwieczorek, BigDecimal mapcKolacja, BigDecimal mapcPosilekNocny, BigDecimal mapcZupaKompot, String mapcOpis) {
        this.mapcId = mapcId;
        this.mapcRodzajDieta = mapcRodzajDieta;
        this.mapcSniadanie = mapcSniadanie;
        this.mapc2Sniadanie = mapc2Sniadanie;
        this.mapcObiad = mapcObiad;
        this.mapcPodwieczorek = mapcPodwieczorek;
        this.mapcKolacja = mapcKolacja;
        this.mapcPosilekNocny = mapcPosilekNocny;
        this.mapcZupaKompot = mapcZupaKompot;
        this.mapcOpis = mapcOpis;
    }
    
    
    
    
    

    public BigDecimal getMapcId() {
        return mapcId;
    }

    public void setMapcId(BigDecimal mapcId) {
        this.mapcId = mapcId;
    }

    public String getMapcRodzajDieta() {
        return mapcRodzajDieta;
    }

    public void setMapcRodzajDieta(String mapcRodzajDieta) {
        this.mapcRodzajDieta = mapcRodzajDieta;
    }

    public BigDecimal getMapcSniadanie() {
        return mapcSniadanie;
    }

    public void setMapcSniadanie(BigDecimal mapcSniadanie) {
        this.mapcSniadanie = mapcSniadanie;
    }

    public BigDecimal getMapc2Sniadanie() {
        return mapc2Sniadanie;
    }

    public void setMapc2Sniadanie(BigDecimal mapc2Sniadanie) {
        this.mapc2Sniadanie = mapc2Sniadanie;
    }

    public BigDecimal getMapcObiad() {
        return mapcObiad;
    }

    public void setMapcObiad(BigDecimal mapcObiad) {
        this.mapcObiad = mapcObiad;
    }

    public BigDecimal getMapcPodwieczorek() {
        return mapcPodwieczorek;
    }

    public void setMapcPodwieczorek(BigDecimal mapcPodwieczorek) {
        this.mapcPodwieczorek = mapcPodwieczorek;
    }

    public BigDecimal getMapcKolacja() {
        return mapcKolacja;
    }

    public void setMapcKolacja(BigDecimal mapcKolacja) {
        this.mapcKolacja = mapcKolacja;
    }

    public BigDecimal getMapcPosilekNocny() {
        return mapcPosilekNocny;
    }

    public void setMapcPosilekNocny(BigDecimal mapcPosilekNocny) {
        this.mapcPosilekNocny = mapcPosilekNocny;
    }

    public BigDecimal getMapcZupaKompot() {
        return mapcZupaKompot;
    }

    public void setMapcZupaKompot(BigDecimal mapcZupaKompot) {
        this.mapcZupaKompot = mapcZupaKompot;
    }

    public String getMapcOpis() {
        return mapcOpis;
    }

    public void setMapcOpis(String mapcOpis) {
        this.mapcOpis = mapcOpis;
    }
    
 
    

}
