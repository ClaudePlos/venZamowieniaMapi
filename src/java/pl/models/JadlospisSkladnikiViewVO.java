package pl.models;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author k.skowronski
 */
@Entity
@Table(name = "v_jadlospis_skladniki")
public class JadlospisSkladnikiViewVO implements Serializable {
    
    @Id
    @Column(name = "id_jadlospis_skladnik")
    private BigDecimal idJadlospisSkladnik;
    
    @Column(name = "id_jadlospis")
    private BigDecimal idJadlospis;
    
    @Column(name = "lp")
    private BigDecimal lp;
    
    @Column(name = "posilek_kod")
    private String posilekKod;
    
    @Column(name = "rodzaj")
    private String rodzaj;
    
    @Column(name = "nazwa_skladnik")
    private String nazwaSkladnik;
    
    @Column(name = "gramatura")
    private String gramatura;
    
    @Column(name = "jm_gramatura_dania")
    private String jmGramaturaDania;
    
    @Column(name = "ilosc")
    private BigDecimal ilosc;
    
    @Column(name = "jm_kod")
    private String jmKod;

    public BigDecimal getIdJadlospisSkladnik() {
        return idJadlospisSkladnik;
    }

    public void setIdJadlospisSkladnik(BigDecimal idJadlospisSkladnik) {
        this.idJadlospisSkladnik = idJadlospisSkladnik;
    }

    public BigDecimal getIdJadlospis() {
        return idJadlospis;
    }

    public void setIdJadlospis(BigDecimal idJadlospis) {
        this.idJadlospis = idJadlospis;
    }

    public BigDecimal getLp() {
        return lp;
    }

    public void setLp(BigDecimal lp) {
        this.lp = lp;
    }

    public String getPosilekKod() {
        return posilekKod;
    }

    public void setPosilekKod(String posilekKod) {
        this.posilekKod = posilekKod;
    }

    public String getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(String rodzaj) {
        this.rodzaj = rodzaj;
    }

    public String getNazwaSkladnik() {
        return nazwaSkladnik;
    }

    public void setNazwaSkladnik(String nazwaSkladnik) {
        this.nazwaSkladnik = nazwaSkladnik;
    }

    public String getGramatura() {
        return gramatura;
    }

    public void setGramatura(String gramatura) {
        this.gramatura = gramatura;
    }

    public String getJmGramaturaDania() {
        return jmGramaturaDania;
    }

    public void setJmGramaturaDania(String jmGramaturaDania) {
        this.jmGramaturaDania = jmGramaturaDania;
    }

    public BigDecimal getIlosc() {
        return ilosc;
    }

    public void setIlosc(BigDecimal ilosc) {
        this.ilosc = ilosc;
    }

    public String getJmKod() {
        return jmKod;
    }

    public void setJmKod(String jmKod) {
        this.jmKod = jmKod;
    }
  
    
    
    
    
}
