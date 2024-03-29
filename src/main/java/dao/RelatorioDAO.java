/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Garantia;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author user
 */
public class RelatorioDAO {

    public void RelatorioGar(int cod, String valor) {

        try {

            String SQL;
            SQL = "SELECT\n"
                    + "     cliente.`id` AS cliente_id,\n"
                    + "     cliente.`Nome` AS cliente_Nome,\n"
                    + "     cliente.`CPF` AS cliente_CPF,\n"
                    + "     cliente.`telefone` AS cliente_telefone,\n"
                    + "     cliente.`cidade` AS cliente_cidade,\n"
                    + "     cliente.`endereco` AS cliente_endereco,\n"
                    + "     cliente.`Email` AS cliente_Email,\n"
                    + "     garantia.`descricao` AS garantia_descricao,\n"
                    + "     garantia.`saida_concerto` AS garantia_saida_concerto,\n"
                    + "     garantia.`garantia` AS garantia_garantia,\n"
                    + "     manutencao.`duracao_garantia` AS manutencao_duracao_garantia,\n"
                    + "     garantia.`id` AS garantia_id,\n"
                    + "     garantia.`valor` AS garantia_valor\n"
                    + "FROM\n"
                    + "     `cliente` cliente INNER JOIN `garantia` garantia ON cliente.`Nome` = garantia.`Nome`\n"
                    + "     INNER JOIN `manutencao` manutencao ON garantia.`descricao` = manutencao.`descricao`"
                    + "     WHERE garantia.`id`= '" + cod + "'";

            Map parametros = new HashMap();
            parametros.clear();
            parametros.put("logo", this.getClass().getResourceAsStream("/LogoRelatório.png"));
            parametros.put("valor", valor);
            parametros.put("logofundo", this.getClass().getResourceAsStream("/MarcaLogoRelatório.png"));

            PreparedStatement stmt = Conexao.getConexaoMySQL().prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            JRResultSetDataSource relatorioGarantia = new JRResultSetDataSource(rs);
            JasperPrint JpPrint = JasperFillManager.fillReport("relatorios/ComprovanteGarantia.jasper", parametros, relatorioGarantia);
            JasperViewer jc = new JasperViewer(JpPrint, false);
            jc.setVisible(true);
            jc.toFront();

        } catch (SQLException | JRException e) {
            System.out.println(e.getMessage());
        }
    }

    public void RelatorioCliente() {

        try {
            String SQL = "SELECT\n"
                    + "     cliente.`id` AS cliente_id,\n"
                    + "     cliente.`Nome` AS cliente_Nome,\n"
                    + "     cliente.`CPF` AS cliente_CPF,\n"
                    + "     cliente.`telefone` AS cliente_telefone,\n"
                    + "     cliente.`cidade` AS cliente_cidade,\n"
                    + "     cliente.`endereco` AS cliente_endereco,\n"
                    + "     cliente.`Email` AS cliente_Email\n"
                    + "FROM\n"
                    + "     `cliente` cliente";

            Map parametros = new HashMap();
            parametros.clear();
            parametros.put("logo", this.getClass().getResourceAsStream("/LogoRelatório.png"));

            PreparedStatement stmt = Conexao.getConexaoMySQL().prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            JRResultSetDataSource relatorioCli = new JRResultSetDataSource(rs);
            JasperPrint jpPrint = JasperFillManager.fillReport("relatorios/RelatórioCliente.jasper", parametros, relatorioCli);
            JasperViewer jv = new JasperViewer(jpPrint, false);
            jv.setVisible(true);
            jv.toFront();

        } catch (JRException e) {
            System.out.println(e.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(RelatorioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void RelatorioGarantia() {

        try {
            GarantiaDAO gardao = new GarantiaDAO();
            String SQL = "SELECT\n"
                    + "     garantia.`id` AS garantia_id,\n"
                    + "     garantia.`Nome` AS garantia_Nome,\n"
                    + "     garantia.`descricao` AS garantia_descricao,\n"
                    + "     garantia.`saida_concerto` AS garantia_saida_concerto,\n"
                    + "     garantia.`garantia` AS garantia_garantia,\n"
                    + "     garantia.`valor` AS garantia_valor\n"             
                    + "FROM\n"
                    + "     `garantia` garantia";

            Map parametros = new HashMap();
            parametros.clear();
            parametros.put("logo", this.getClass().getResourceAsStream("/LogoRelatório.png"));
            parametros.put("valor", "R$");
            parametros.put("valortotal", gardao.SomaGarantia("", "", ""));
            
            PreparedStatement stmt = Conexao.getConexaoMySQL().prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            JRResultSetDataSource relatorioGar = new JRResultSetDataSource(rs);
            JasperPrint jpPrint = JasperFillManager.fillReport("relatorios/RelatórioGarantia.jasper", parametros, relatorioGar);
            JasperViewer jv = new JasperViewer(jpPrint, false);
            jv.setVisible(true);
            jv.toFront();

        } catch (JRException e) {
            System.out.println(e.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(RelatorioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void GeraGarantia(String valor) {
        try {
            String SQL = "SELECT max(garantia.id) as max_id FROM garantia";
            PreparedStatement stmt = Conexao.getConexaoMySQL().prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int cod = rs.getInt("max_id");
                RelatorioDAO relatorioGarantia = new RelatorioDAO();
                relatorioGarantia.RelatorioGar(cod, valor);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro no acesso" + ex);
        }
    }

}
