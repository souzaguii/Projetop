/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Logins;
import view.PrincipalADM;

/**
 *
 * @author Geral
 */
public class LoginsDAO {

    public void Cadastrarlogin(Logins logins) throws SQLException {

        String SQL = "INSERT INTO cadastros.logins (usuario, senha, tipoconta) values (?, ?, ?)";

        try (PreparedStatement stmt = Conexao.getConexaoMySQL().prepareStatement(SQL)) {
            stmt.setString(1, logins.getusuario());
            stmt.setString(2, logins.getsenha());
            stmt.setString(3, logins.gettipoconta());

            stmt.execute();
        }
    }

    public void Removerlogin(Logins logins) throws SQLException {
        String SQL = "Delete from cadastros.logins where id=?";

        try (PreparedStatement stmt = Conexao.getConexaoMySQL().prepareStatement(SQL)) {
            stmt.setInt(1, logins.getid());
            stmt.execute();
        }
    }

    public void AtualizarLogins(Logins logins) {

        Connection con = Conexao.getConexaoMySQL();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE cadastros.logins SET usuario = ? ,senha = ?,tipoconta = ? WHERE id = ?");
            stmt.setString(1, logins.getusuario());
            stmt.setString(2, logins.getsenha());
            stmt.setString(3, logins.gettipoconta());
            stmt.setInt(4, logins.getid());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Login atualizado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex);
        } finally {
            Conexao.FecharConexao();
        }

    }

    public boolean checkLogin(String login, String senha, String tipo) {

        Connection con = Conexao.getConexaoMySQL();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;

        List<Logins> loginss = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM cadastros.logins WHERE usuario=? and senha=? and tipoconta=?");
            stmt.setString(1, login);
            stmt.setString(2, senha);
            stmt.setString(3, tipo);
            rs = stmt.executeQuery();

            if (rs.next()) {
                check = true;

            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.FecharConexao();
        }

        return check;
    }

    public List<Logins> read() {

        Connection con = Conexao.getConexaoMySQL();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Logins> loginss = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM cadastros.logins");
            rs = stmt.executeQuery();
            int  cont = 0;
            while (rs.next()) {

                Logins logins = new Logins();
                logins.setid(rs.getInt("id"));
                logins.setusuario(rs.getString("usuario"));
                logins.setsenha(rs.getString("senha"));
                logins.settipoconta(rs.getString("tipoconta"));
                loginss.add(logins);
                cont++;
            }                       
//                PrincipalADM prin = new PrincipalADM();
//                
//                prin.cont.setText(String.valueOf(cont));
                
        } catch (SQLException ex) {
            Logger.getLogger(LoginsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.FecharConexao();
        }

        return loginss;
    }

    public List<Logins> PesquisarLogin(String user) {

        Connection con = Conexao.getConexaoMySQL();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Logins> loginss = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM cadastros.logins WHERE usuario LIKE ?");
            stmt.setString(1, "%" + user + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {

                Logins logins = new Logins();
                logins.setid(rs.getInt("id"));
                logins.setusuario(rs.getString("usuario"));
                logins.setsenha(rs.getString("senha"));
                logins.settipoconta(rs.getString("tipoconta"));
                loginss.add(logins);
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.FecharConexao();
        }

        return loginss;

    }

    public String PesquisarSenha(String user) throws SQLException {

        Connection con = Conexao.getConexaoMySQL();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        stmt = con.prepareStatement("SELECT senha FROM cadastros.logins WHERE usuario =?");
        stmt.setString(1, user);
        rs = stmt.executeQuery();

        rs.first();

        String senha = rs.getString("senha");

        return senha;

    }

}
