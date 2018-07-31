/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.models;

/**
 *
 * @author k.skowronski
 */
public class KontrolaRodzajGodzDTO {
    
    private String czas;
    private String rodzaj;

    public KontrolaRodzajGodzDTO(String czas, String rodzaj) {
        this.czas = czas;
        this.rodzaj = rodzaj;
    }

    public String getCzas() {
        return czas;
    }

    public void setCzas(String czas) {
        this.czas = czas;
    }

    public String getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(String rodzaj) {
        this.rodzaj = rodzaj;
    }

    
    
}
