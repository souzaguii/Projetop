/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Garantia;
import model.Manutencao;

/**
 *
 * @author user
 */
public class ManutencaoDAO {

    public void InserirManutencao(Manutencao manutencao) throws SQLException {

        String SQL = "INSERT INTO cadastros.manutencao (id,descricao, duracao_garantia) values (?, ?, ?)";

        PreparedStatement stmt = Conexao.getConexaoMySQL().prepareStatement(SQL);

        stmt.setInt(1, 0);
        stmt.setString(2, manutencao.getDescricao());
        stmt.setInt(3, manutencao.getDuracao());
        stmt.execute();
        stmt.close();
    }

    public void RemoverManutencao(Manutencao manutencao) throws SQLException {
        String SQL = "Delete from cadastros.manutencao where id=?";

        PreparedStatement stmt = Conexao.getConexaoMySQL().prepareStatement(SQL);
        stmt.setInt(1, manutencao.getId());

        stmt.execute();
        stmt.close();
    }

    public void AlterarManutencao(Manutencao manutencao) throws SQLException {
        String SQL = "update cadastros.manutencao set descricao=?, duracao_garantia=? where id=?";

        PreparedStatement stmt = Conexao.getConexaoMySQL().prepareStatement(SQL);

        stmt.setString(1, manutencao.getDescricao());
        stmt.setInt(2, manutencao.getDuracao());
        stmt.setInt(3, manutencao.getId());

        stmt.execute();
        stmt.close();
    }

    public List<Manutencao> ListaManutencao() throws SQLException {

        List<Manutencao> ListaManutencao;
        ListaManutencao = new ArrayList<>();

        String SQL = "select* from cadastros.manutencao";
        try {

            PreparedStatement stmt = Conexao.getConexaoMySQL().prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ListaManutencao.add(new Manutencao(rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getInt("duracao_garantia"))
                );

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ListaManutencao;
    }

    public List<Manutencao> ListaBuscaManutencao(Manutencao man) throws SQLException {
        List<Manutencao> retorno = new ArrayList<Manutencao>();

        String SQL = "select * from cadastros.manutencao ";

        if (man.getDescricao() != null) {
            SQL += "where descricao like ?";
        }

        PreparedStatement stmt = Conexao.getConexaoMySQL().prepareStatement(SQL);

        if (man.getDescricao() != null) {
            stmt.setString(1, "%" + man.getDescricao() + "%");
        }

        try {
            ResultSet rs = stmt.executeQuery();
            System.out.println(SQL);
            while (rs.next()) {
                retorno.add(new Manutencao(rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getInt("duracao_garantia")));

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return retorno;
    }
}
