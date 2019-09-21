/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;


/**
 *
 * @author Real
 */
public class Garantia {
    private int id;
    private String nome;
    private String descricao;
    private LocalDate saida_concerto;
    private LocalDate dt_garantia;

    //private LocalDate detea = LocalDate.now();
    
    public Garantia() {
    }

    public Garantia(int id, String nome, String descricao, LocalDate saida_concerto, LocalDate dt_garantia) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.saida_concerto = saida_concerto;
        this.dt_garantia = dt_garantia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getSaida_concerto() {
        return saida_concerto;
    }

    public void setSaida_concerto(LocalDate saida_concerto) {
        this.saida_concerto = saida_concerto;
    }

    public LocalDate getDt_garantia() {
        return dt_garantia;
    }

    public void setDt_garantia(LocalDate dt_garantia) {
        this.dt_garantia = dt_garantia;
    }
        
}
