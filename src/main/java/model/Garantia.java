package model;

import java.time.LocalDate;

public class Garantia {

    private int id;
    private String nome;
    private String descricao;
    private LocalDate saida_concerto;
    private LocalDate dt_garantia;
    private String valor;

    public Garantia() {
    }

    public Garantia(int id, String nome, String descricao, LocalDate saida_concerto, LocalDate dt_garantia, String valor) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.saida_concerto = saida_concerto;
        this.dt_garantia = dt_garantia;
        this.valor = valor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setSaida_concerto(LocalDate saida_concerto) {
        this.saida_concerto = saida_concerto;
    }

    public void setDt_garantia(LocalDate dt_garantia) {
        this.dt_garantia = dt_garantia;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getSaida_concerto() {
        return saida_concerto;
    }

    public LocalDate getDt_garantia() {
        return dt_garantia;
    }

    public String getValor() {
        return valor;
    }

}
