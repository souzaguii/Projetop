/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import model.Garantia;
import view.PrincipalADM;

/**
 *
 * @author Real
 */
public class GarantiaDAO {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void InserirGarantia(Garantia garantia) throws SQLException {

        String SQL = "INSERT INTO cadastros.garantia (id, nome, descricao, saida_concerto, garantia, valor) values (?, ?, ?, ?, ?, ?)";

        PreparedStatement stmt = Conexao.getConexaoMySQL().prepareStatement(SQL);

        stmt.setInt(1, 0);
        stmt.setString(2, garantia.getNome());
        stmt.setString(3, garantia.getDescricao());
        stmt.setDate(4, Date.valueOf(garantia.getSaida_concerto()));
        stmt.setDate(5, Date.valueOf(garantia.getDt_garantia()));
        stmt.setString(6, garantia.getValor());

        stmt.execute();
        stmt.close();
    }

    public void RemoverGarantia(Garantia gar) throws SQLException {
        String SQL = "Delete from cadastros.garantia where id=?";

        PreparedStatement stmt = Conexao.getConexaoMySQL().prepareStatement(SQL);
        stmt.setInt(1, gar.getId());

        stmt.execute();
        stmt.close();
    }

    public void AlterarGarantia(Garantia gar) throws SQLException {
        String SQL = "update cadastros.garantia set nome=?, descricao=?, saida_concerto=?, garantia=?, valor=?  where id=?";

        PreparedStatement stmt = Conexao.getConexaoMySQL().prepareStatement(SQL);
        stmt.setString(1, gar.getNome());
        stmt.setString(2, gar.getDescricao());
        stmt.setDate(3, Date.valueOf(gar.getSaida_concerto()));
        stmt.setDate(4, Date.valueOf(gar.getDt_garantia()));
        stmt.setString(5, gar.getValor());
        stmt.setInt(6, gar.getId());

        stmt.execute();
        stmt.close();
    }

    public List<Garantia> ListaGarantia() throws SQLException {

        List<Garantia> ListaGarantia;
        ListaGarantia = new ArrayList<>();

        String SQL = "select* from cadastros.garantia";
        try {

            PreparedStatement stmt = Conexao.getConexaoMySQL().prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String Valorconvertido = rs.getString("valor");
                Valorconvertido = Valorconvertido.replaceAll("\\.", "");
                Valorconvertido = Valorconvertido.replaceAll(",", ".");

                double ValorConvertido = Double.valueOf(Valorconvertido);

                Locale BRAZIL = new Locale("pt", "BR");
                NumberFormat moeda = NumberFormat.getCurrencyInstance(BRAZIL);

                ListaGarantia.add(new Garantia(rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getObject("saida_concerto", LocalDate.class),
                        rs.getObject("garantia", LocalDate.class),
                        moeda.format(ValorConvertido)
                ));

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ListaGarantia;
    }

    public List<Garantia> ListaBuscaGarantia(Garantia garantia, String string) throws SQLException {
        List<Garantia> retorno = new ArrayList<Garantia>();

        String SQL = "select * from cadastros.garantia ";

        if (garantia.getNome() != null) {
            SQL += "where Nome like ?";
        }
        if (garantia.getNome() != null && string == "Vencidas") {
            SQL += "and garantia < NOW() ORDER BY GARANTIA ASC";
        } else if (garantia.getNome() != null && string == "Vigentes") {
            SQL += "and garantia > NOW() ORDER BY GARANTIA ASC";
        } else if (garantia.getNome() != null && string == "Todas") {
        } else if (garantia.getNome() == null && string == "Vencidas") {
            SQL += "WHERE garantia < NOW() ORDER BY GARANTIA ASC";
        } else if (garantia.getNome() == null && string == "Vigentes") {
            SQL += "WHERE garantia > NOW() ORDER BY GARANTIA ASC";
        } else if (garantia.getNome() == null && string == "Todas") {
        }

        PreparedStatement stmt = Conexao.getConexaoMySQL().prepareStatement(SQL);

        if (garantia.getNome() != null) {
            stmt.setString(1, "%" + garantia.getNome() + "%");
        }
        try {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                String Valorconvertido = rs.getString("valor");
                Valorconvertido = Valorconvertido.replaceAll("\\.", "");
                Valorconvertido = Valorconvertido.replaceAll(",", ".");

                double ValorConvertido = Double.valueOf(Valorconvertido);

                Locale BRAZIL = new Locale("pt", "BR");
                NumberFormat moeda = NumberFormat.getCurrencyInstance(BRAZIL);

                retorno.add(new Garantia(rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getObject("saida_concerto", LocalDate.class),
                        rs.getObject("garantia", LocalDate.class),
                        moeda.format(ValorConvertido)
                ));

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retorno;
    }

    public String SomaGarantia(String D, String M, String A) throws SQLException {

        String SQL = "select* from cadastros.garantia ";

        if (!D.isEmpty() && !M.isEmpty() && !A.isEmpty()) {

            SQL += "WHERE DAY(saida_concerto) =" + D + " and MONTH(saida_concerto) =" + M + " and YEAR(saida_concerto) =" + A;

        } else if (!D.isEmpty()) {

            if (M.isEmpty() && A.isEmpty()) {

                SQL += "WHERE DAY(saida_concerto) =" + D;

            } else if (!M.isEmpty()) {

                SQL += "WHERE DAY(saida_concerto) =" + D + " and MONTH(saida_concerto) =" + M;

            } else if (!A.isEmpty()) {

                SQL += "WHERE DAY(saida_concerto) =" + D + " and YEAR(saida_concerto) =" + A;

            }

        } else if (!M.isEmpty()) {

            if (D.isEmpty() && A.isEmpty()) {

                SQL += "WHERE MONTH(saida_concerto) =" + M;

            } else if (!A.isEmpty()) {

                SQL += "WHERE MONTH(saida_concerto) =" + M + " and YEAR(saida_concerto) =" + A;

            }

        } else if (!A.isEmpty()) {

            if (D.isEmpty() && M.isEmpty()) {

                SQL += "WHERE YEAR(saida_concerto) =" + A;

            }

        }

        PreparedStatement stmt = Conexao.getConexaoMySQL().prepareStatement(SQL);
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {

            String nulo = "-1.0";

            stmt.close();

            return nulo;

        }

        rs.previous();

        double total = 0;

        while (rs.next()) {

            String Valorconvertido = rs.getString("valor");
            Valorconvertido = Valorconvertido.replaceAll("\\.", "");
            Valorconvertido = Valorconvertido.replaceAll(",", ".");

            total = total + Double.valueOf(Valorconvertido);

        }

        stmt.close();

        double ValorConvertido = Double.valueOf(total);

        Locale BRAZIL = new Locale("pt", "BR");
        NumberFormat moeda = NumberFormat.getCurrencyInstance(BRAZIL);

        return moeda.format(ValorConvertido);

    }

}
