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
    private String mapcSniadanie;
    
    @Column(name = "MAPC_2_SNIADANIE")
    private String mapc2Sniadanie;
    
    @Column(name = "MAPC_OBIAD")
    private String mapcObiad;
    
    @Column(name = "MAPC_PODWIECZOREK")
    private String mapcPodwieczorek;
    
    @Column(name = "MAPC_KOLACJA")
    private String mapcKolacja;
    
    @Column(name = "MAPC_POSILEK_NOCNY")
    private String mapcPosilekNocny;
    
    @Column(name = "MAPC_ZUPA_KOMPOT")
    private String mapcZupaKompot;
    
    @Column(name = "MAPC_OPIS")
    private String mapcOpis;
    
 

    public NapMapowaniaCenyVO(BigDecimal mapcId, String mapcRodzajDieta, String mapcSniadanie, String mapc2Sniadanie, String mapcObiad, String mapcPodwieczorek, String mapcKolacja, String mapcPosilekNocny, String mapcZupaKompot, String mapcOpis) {
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

    public String getMapcSniadanie() {
        return mapcSniadanie;
    }

    public void setMapcSniadanie(String mapcSniadanie) {
        this.mapcSniadanie = mapcSniadanie;
    }

    public String getMapc2Sniadanie() {
        return mapc2Sniadanie;
    }

    public void setMapc2Sniadanie(String mapc2Sniadanie) {
        this.mapc2Sniadanie = mapc2Sniadanie;
    }

    public String getMapcObiad() {
        return mapcObiad;
    }

    public void setMapcObiad(String mapcObiad) {
        this.mapcObiad = mapcObiad;
    }

    public String getMapcPodwieczorek() {
        return mapcPodwieczorek;
    }

    public void setMapcPodwieczorek(String mapcPodwieczorek) {
        this.mapcPodwieczorek = mapcPodwieczorek;
    }

    public String getMapcKolacja() {
        return mapcKolacja;
    }

    public void setMapcKolacja(String mapcKolacja) {
        this.mapcKolacja = mapcKolacja;
    }

    public String getMapcPosilekNocny() {
        return mapcPosilekNocny;
    }

    public void setMapcPosilekNocny(String mapcPosilekNocny) {
        this.mapcPosilekNocny = mapcPosilekNocny;
    }

    public String getMapcZupaKompot() {
        return mapcZupaKompot;
    }

    public void setMapcZupaKompot(String mapcZupaKompot) {
        this.mapcZupaKompot = mapcZupaKompot;
    }

    public String getMapcOpis() {
        return mapcOpis;
    }

    public void setMapcOpis(String mapcOpis) {
        this.mapcOpis = mapcOpis;
    }
              

}
