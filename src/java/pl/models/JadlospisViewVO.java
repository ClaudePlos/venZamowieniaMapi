package pl.models;

import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author k.skowronski
 */

@Entity
@Table(name = "v_jadlospisy")
@XmlRootElement
public class JadlospisViewVO {
    
    
    @Id
    @Column(name = "id_jadlospis")
    private BigDecimal idJadlospis;
    
    @Column(name = "id_dieta")
    private BigDecimal idDieta;
    
    @Column(name = "d_obr")
    private Date dObr;
    
    @Column(name = "dieta_kod")
    private String dietaKod;
    
    @Column(name = "dieta_nazwa")
    private String dietaNazwa;
    
    @Column(name = "w1")
    private BigDecimal w1;
    
    @Column(name = "w2")
    private BigDecimal w2;
    
    @Column(name = "w3")
    private BigDecimal w3;
    
    @Column(name = "w4")
    private BigDecimal w4;
    
    @Column(name = "w5")
    private BigDecimal w5;
    
    @Column(name = "w6")
    private BigDecimal w6;
    
    @Column(name = "w7")
    private BigDecimal w7;
    
    @Column(name = "w8")
    private BigDecimal w8;
    
    @Column(name = "w9")
    private BigDecimal w9;
    
    @Column(name = "w10")
    private BigDecimal w10;
    
    @Column(name = "w11")
    private BigDecimal w11;
    
    @Column(name = "w12")
    private BigDecimal w12;
    
    @Column(name = "w13")
    private BigDecimal w13;
    
    @Column(name = "w14")
    private BigDecimal w14;
    
    @Column(name = "w15")
    private BigDecimal w15;
    
    @Column(name = "w16")
    private BigDecimal w16;

    public BigDecimal getIdJadlospis() {
        return idJadlospis;
    }

    public void setIdJadlospis(BigDecimal idJadlospis) {
        this.idJadlospis = idJadlospis;
    }

    public BigDecimal getIdDieta() {
        return idDieta;
    }

    public void setIdDieta(BigDecimal idDieta) {
        this.idDieta = idDieta;
    }

    public Date getdObr() {
        return dObr;
    }

    public void setdObr(Date dObr) {
        this.dObr = dObr;
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

    public BigDecimal getW1() {
        return w1;
    }

    public void setW1(BigDecimal w1) {
        this.w1 = w1;
    }

    public BigDecimal getW2() {
        return w2;
    }

    public void setW2(BigDecimal w2) {
        this.w2 = w2;
    }

    public BigDecimal getW3() {
        return w3;
    }

    public void setW3(BigDecimal w3) {
        this.w3 = w3;
    }

    public BigDecimal getW4() {
        return w4;
    }

    public void setW4(BigDecimal w4) {
        this.w4 = w4;
    }

    public BigDecimal getW5() {
        return w5;
    }

    public void setW5(BigDecimal w5) {
        this.w5 = w5;
    }

    public BigDecimal getW6() {
        return w6;
    }

    public void setW6(BigDecimal w6) {
        this.w6 = w6;
    }

    public BigDecimal getW7() {
        return w7;
    }

    public void setW7(BigDecimal w7) {
        this.w7 = w7;
    }

    public BigDecimal getW8() {
        return w8;
    }

    public void setW8(BigDecimal w8) {
        this.w8 = w8;
    }

    public BigDecimal getW9() {
        return w9;
    }

    public void setW9(BigDecimal w9) {
        this.w9 = w9;
    }

    public BigDecimal getW10() {
        return w10;
    }

    public void setW10(BigDecimal w10) {
        this.w10 = w10;
    }

    public BigDecimal getW11() {
        return w11;
    }

    public void setW11(BigDecimal w11) {
        this.w11 = w11;
    }

    public BigDecimal getW12() {
        return w12;
    }

    public void setW12(BigDecimal w12) {
        this.w12 = w12;
    }

    public BigDecimal getW13() {
        return w13;
    }

    public void setW13(BigDecimal w13) {
        this.w13 = w13;
    }

    public BigDecimal getW14() {
        return w14;
    }

    public void setW14(BigDecimal w14) {
        this.w14 = w14;
    }

    public BigDecimal getW15() {
        return w15;
    }

    public void setW15(BigDecimal w15) {
        this.w15 = w15;
    }

    public BigDecimal getW16() {
        return w16;
    }

    public void setW16(BigDecimal w16) {
        this.w16 = w16;
    }
    

    
}
