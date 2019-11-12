/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Geral
 */
public class Logins {
    
    private int id;
    private String usuario;
    private String senha;
    private String tipoconta;

    public Logins(int id, String usuario, String senha, String tipoconta) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
        this.tipoconta = tipoconta;
    }

    public Logins() {
    }
    
    

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getusuario() {
        return usuario;
    }

    public void setusuario(String usuario) {
        this.usuario = usuario;
    }

    public String getsenha() {
        return senha;
    }

    public void setsenha(String senha) {
        this.senha = senha;
    }

    public String gettipoconta() {
        return tipoconta;
    }

    public void settipoconta(String tipoconta) {
        this.tipoconta = tipoconta;
    }
    
    

}