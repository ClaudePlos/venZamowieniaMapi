/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.models;

import java.io.Serializable;
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
@Table(name = "OPERATORZY")
public class OperatorVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_OPERATOR")
    private Integer idOperator;

    @Column(name = "KOD")
    private String login;
    
    @Column(name = "HASLO")
    private String haslo;

    public Integer getIdOperator() {
        return idOperator;
    }

    public void setIdOperator(Integer idOperator) {
        this.idOperator = idOperator;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }
    
    

    
}
