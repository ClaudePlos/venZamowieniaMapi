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
public class SprWartDzialalnosciKuchniDTO {
    
    private String grupaZywionych;
    private String grupaZywionychKod;  
    
    
    
    public SprWartDzialalnosciKuchniDTO(String grupaZywionych, String grupaZywionychKod) {
        this.grupaZywionych = grupaZywionych;
        this.grupaZywionychKod = grupaZywionychKod;
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
    
    
    
    
    
}
