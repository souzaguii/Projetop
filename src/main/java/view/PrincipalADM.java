/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

//import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import controller.JTextFieldLimit;
import controller.SetupAutoComplete;
import dao.Conexao;
import dao.GarantiaDAO;
import dao.LoginsDAO;
import dao.ManutencaoDAO;
import dao.clienteDAO;
import dao.clienteDAO;
import dao.RelatorioDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.sql.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Cliente;
import model.Garantia;
import model.Logins;
import model.Manutencao;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Michel
 */
public class PrincipalADM extends javax.swing.JFrame {

    PreparedStatement stmt = null;
    ResultSet rs = null;

    List<Cliente> ListaCliente;
    List<Cliente> ListaBuscaCliente;

    List<Garantia> ListaGarantia;
    List<Garantia> ListaBuscaGarantia;

    List<Manutencao> ListaManutencao;
    List<Manutencao> ListaBuscaManutencao;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PrincipalADM() {
        initComponents();

        PaneClientes.setVisible(true);
        PaneGarantias.setVisible(false);
        PaneServicos.setVisible(false);
        PaneContatarCli.setVisible(false);
        setLblColor(BotaoClientes);
        ResetColor(BotaoGarantias);
        ResetColor(BotaoConsertos);
        ComboBoxEscolhaConserto();
        atualizarTabelaConsultaCliente();
        atualizarTabelaConsultaGarantia();
        atualizarTabelaConsultaManutencao();
        iniciaCliente();
        iniciaServicos();
        iniciaGarantia();
        TableGerenciarLoginss();
        TravaCamposGerenciarLogins();
    }

    public void iniciaCliente() {
        atualizarTableGerenciarCCliente();
        FieldIDGerenciarClientes.setVisible(false);
        TravaCamposCadastroClientes();
        TravaCamposGerenciarClientes();
    }

    public void iniciaServicos() {
        atualizarTabelaGerenciaCManutencao();
        FieldIDGerenciarConsertos.setVisible(false);
        TravaCamposCadastroServicos();
        TravaCamposGerenciarServicos();

    }

    public void iniciaGarantia() {
        atualizarTabelaGerenciaCGarantia();
        ComboGerenciaEscolhaConserto();
        FieldIDGerenciarGarantia.setVisible(false);
        ComboEscolhaConserto.setSelectedItem(null);
        ComboGerenciaEscolhaConserto.setSelectedItem(null);
        TravaCamposCadastroGarantia();
        TravaCamposGarenciarGarantia();
        AutoComplete();
        AutoComplete1();
        AutoComplete2();
    }

    public void TravaCamposCadastroClientes() {
        FieldCadastroNomeCliente.setEnabled(false);
        FieldCadastroCPFCliente.setEnabled(false);
        FieldCadastroTelefoneCliente.setEnabled(false);
        FieldCadastroCidadeCliente.setEnabled(false);
        FieldCadastroEnderecoCliente.setEnabled(false);
        FieldCadastroEmailCliente.setEnabled(false);
        BotaoSalvarCadastroCliente.setEnabled(false);
        BotaoCancelarCadastroCliente.setEnabled(false);
        jSeparator16.setForeground(new Color(200, 200, 200));
        jSeparator17.setForeground(new Color(200, 200, 200));
        jSeparator12.setForeground(new Color(200, 200, 200));
        jSeparator9.setForeground(new Color(200, 200, 200));
        jSeparator14.setForeground(new Color(200, 200, 200));
        jSeparator21.setForeground(new Color(200, 200, 200));

        lblNome3.setEnabled(false);
        lblNome4.setEnabled(false);
        lblNome6.setEnabled(false);
        lblNome8.setEnabled(false);
        lblNome9.setEnabled(false);
        lblCPF4.setEnabled(false);
    }

    public void DestravaCamposCadastroClientes() {
        FieldCadastroNomeCliente.setEnabled(true);
        FieldCadastroCPFCliente.setEnabled(true);
        FieldCadastroTelefoneCliente.setEnabled(true);
        FieldCadastroCidadeCliente.setEnabled(true);
        FieldCadastroEnderecoCliente.setEnabled(true);
        FieldCadastroEmailCliente.setEnabled(true);
        BotaoSalvarCadastroCliente.setEnabled(true);
        BotaoCancelarCadastroCliente.setEnabled(true);
        jSeparator16.setForeground(new Color(0, 0, 0));
        jSeparator17.setForeground(new Color(0, 0, 0));
        jSeparator12.setForeground(new Color(0, 0, 0));
        jSeparator9.setForeground(new Color(0, 0, 0));
        jSeparator14.setForeground(new Color(0, 0, 0));
        jSeparator21.setForeground(new Color(0, 0, 0));
        lblNome3.setEnabled(true);
        lblNome4.setEnabled(true);
        lblNome6.setEnabled(true);
        lblNome8.setEnabled(true);
        lblNome9.setEnabled(true);
        lblCPF4.setEnabled(true);
        BotaoNovoCadastroCliente.setEnabled(false);

    }

    public void SalvarCamposCadastroClientes() {
        FieldCadastroNomeCliente.setText("");
        FieldCadastroCPFCliente.setText("");
        FieldCadastroTelefoneCliente.setText("");
        FieldCadastroCidadeCliente.setText("");
        FieldCadastroEnderecoCliente.setText("");
        FieldCadastroEmailCliente.setText("");

        TravaCamposCadastroClientes();

        BotaoNovoCadastroCliente.setEnabled(true);
    }

    public void TravaCamposCadastroServicos() {
        FieldNomeCadastrarConsertos.setEnabled(false);
        FieldDuracaoCadastrarConsertos.setEnabled(false);
        lblNome5.setEnabled(false);
        lblNome13.setEnabled(false);
        jSeparator13.setForeground(new Color(200, 200, 200));
        jSeparator22.setForeground(new Color(200, 200, 200));

        BotaoSalvarCadastroConserto.setEnabled(false);
        BotaoCancelarCadastroConserto.setEnabled(false);
    }

    public void DestravaCamposCadastroServicos() {
        FieldNomeCadastrarConsertos.setEnabled(true);
        FieldDuracaoCadastrarConsertos.setEnabled(true);
        lblNome5.setEnabled(true);
        lblNome13.setEnabled(true);
        jSeparator13.setForeground(new Color(0, 0, 0));
        jSeparator22.setForeground(new Color(0, 0, 0));

        BotaoSalvarCadastroConserto.setEnabled(true);
        BotaoCancelarCadastroConserto.setEnabled(true);

        BotaoNovoCadastroConserto.setEnabled(false);

    }

    public void SalvarCamposCadastroServicos() {
        FieldNomeCadastrarConsertos.setText("");
        FieldDuracaoCadastrarConsertos.setText("");

        TravaCamposCadastroServicos();

        BotaoNovoCadastroConserto.setEnabled(true);

    }

    public void TravaCamposCadastroGarantia() {
        CampoNome.setEnabled(false);
        CampoDataFormatada.setEnabled(false);
        ComboEscolhaConserto.setEnabled(false);
        jSeparator2.setForeground(new Color(200, 200, 200));
        jSeparator4.setForeground(new Color(200, 200, 200));

        lblNome.setEnabled(false);
        lblData.setEnabled(false);
        lblConserto.setEnabled(false);

        BotaoSalvarGarantia.setEnabled(false);
        BotaoCancelarGarantia.setEnabled(false);

    }

    public void TravaCamposGerenciarLogins() {
        FieldUser.setEnabled(false);
        FieldSenha.setEnabled(false);
        BotaoAlterarGerLogins.setEnabled(false);
        BotaoExcluirGerLogins.setEnabled(false);
        BotaoCancelarGerLogins.setEnabled(false);
        FieldUser.setText(" ");
        FieldSenha.setText(" ");
        lblNome20.setEnabled(false);
        lblNome21.setEnabled(false);
        lblNome30.setEnabled(false);
        jSeparator34.setForeground(new Color(200, 200, 200));
        ComboTipoConta.setEnabled(false);
        jSeparator33.setForeground(new Color(200, 200, 200));
    }

    public void DestravaCamposGerenciarLogins() {
        FieldUser.setEnabled(true);
        FieldSenha.setEnabled(true);
        BotaoAlterarGerLogins.setEnabled(true);
        BotaoExcluirGerLogins.setEnabled(true);
        BotaoCancelarGerLogins.setEnabled(true);

        lblNome20.setEnabled(true);
        lblNome21.setEnabled(true);
        lblNome30.setEnabled(true);

        ComboTipoConta.setEnabled(true);
        jSeparator34.setEnabled(true);
        jSeparator33.setEnabled(true);

    }

    public void DestravaCamposCadastroGarantia() {
        CampoNome.setEnabled(true);
        CampoDataFormatada.setEnabled(true);
        ComboEscolhaConserto.setEnabled(true);
        BotaoSalvarGarantia.setEnabled(true);

        lblNome.setEnabled(true);
        lblData.setEnabled(true);
        lblConserto.setEnabled(true);
        jSeparator2.setForeground(new Color(0, 0, 0));
        jSeparator4.setForeground(new Color(0, 0, 0));

        BotaoCancelarGarantia.setEnabled(true);
        BotaoNovoGarantia.setEnabled(false);
    }

    public void SalvarCamposCadastroGarantia() {
        CampoNome.setText("");
        CampoDataFormatada.setText("");
        ComboEscolhaConserto.setSelectedItem(null);

        TravaCamposCadastroGarantia();

        BotaoNovoGarantia.setEnabled(true);
    }

    //-----------------------------------
    public void TravaCamposGerenciarServicos() {
        FieldNomeGerenciarConsertos.setEnabled(false);
        FieldDuracaoGerenciarConsertos.setEnabled(false);

        lblNome14.setEnabled(false);
        lblNome15.setEnabled(false);

        BotaoAlterarGerenciarServicos.setEnabled(false);
        BotaoExcluirGerenciarServicos.setEnabled(false);
        BotaoCancelarGerenciarServicos.setEnabled(false);
        jSeparator23.setForeground(new Color(200, 200, 200));
        jSeparator24.setForeground(new Color(200, 200, 200));

    }

    public void DestravaCamposGerenciarServicos() {
        FieldNomeGerenciarConsertos.setEnabled(true);
        FieldDuracaoGerenciarConsertos.setEnabled(true);

        lblNome14.setEnabled(true);
        lblNome15.setEnabled(true);

        BotaoAlterarGerenciarServicos.setEnabled(true);
        BotaoExcluirGerenciarServicos.setEnabled(true);
        BotaoCancelarGerenciarServicos.setEnabled(true);
        jSeparator23.setForeground(new Color(0, 0, 0));
        jSeparator24.setForeground(new Color(0, 0, 0));
    }

    public void SalvarCamposGerenciarServicos() {
        FieldNomeGerenciarConsertos.setText("");
        FieldDuracaoGerenciarConsertos.setText("");
        FieldIDGerenciarConsertos.setText("");

        TravaCamposGerenciarServicos();
    }

    public void SalvarCamposGerenciarLogins() {
        FieldUser.setText("");
        FieldSenha.setText("");
        GerenciarLogins.setEnabled(false);
    }

    //-----------------------------------
    public void TravaCamposGarenciarGarantia() {
        FieldNomeGerenciarGarantia.setEnabled(false);
        CampoGerenciaDataFormatada.setEnabled(false);
        ComboGerenciaEscolhaConserto.setEnabled(false);

        lblNome18.setEnabled(false);
        lblData1.setEnabled(false);
        lblConserto1.setEnabled(false);

        BotaoAlterarGerenciarGarantia.setEnabled(false);
        BotaoExcluirGerenciarGarantia.setEnabled(false);
        BotaoCancelarGerenciarGarantia.setEnabled(false);
        Botao2Via.setEnabled(false);
        jSeparator26.setForeground(new Color(200, 200, 200));
        jSeparator5.setForeground(new Color(200, 200, 200));
    }

    public void DestravaCamposGarenciarGarantia() {
        FieldNomeGerenciarGarantia.setEnabled(true);
        CampoGerenciaDataFormatada.setEnabled(true);
        ComboGerenciaEscolhaConserto.setEnabled(true);

        lblNome18.setEnabled(true);
        lblData1.setEnabled(true);
        lblConserto1.setEnabled(true);

        BotaoAlterarGerenciarGarantia.setEnabled(true);
        BotaoExcluirGerenciarGarantia.setEnabled(true);
        BotaoCancelarGerenciarGarantia.setEnabled(true);
        Botao2Via.setEnabled(true);
        jSeparator26.setForeground(new Color(0, 0, 0));
        jSeparator5.setForeground(new Color(0, 0, 0));
    }

    public void SalvarCamposGarenciarGarantia() {
        FieldNomeGerenciarGarantia.setText("");
        CampoGerenciaDataFormatada.setText("");
        ComboGerenciaEscolhaConserto.setSelectedItem(null);

        TravaCamposGarenciarGarantia();
    }

    public void RedefinirCamposGerenciarLogins() {
        FieldUser.setText("");
        FieldSenha.setText("");
    }

    //-----------------------------------
    public void TravaCamposGerenciarClientes() {
        FieldNomeGerenciarClientes.setEnabled(false);
        FieldCPFGerenciarClientes.setEnabled(false);
        FieldTelefoneGerenciarClientes.setEnabled(false);
        FieldCidadeGerenciarClientes.setEnabled(false);
        FieldEnderecoGerenciarClientes.setEnabled(false);
        FieldEmailGerenciarClientes.setEnabled(false);
        jSeparator19.setForeground(new Color(200, 200, 200));
        jSeparator27.setForeground(new Color(200, 200, 200));
        jSeparator28.setForeground(new Color(200, 200, 200));
        jSeparator29.setForeground(new Color(200, 200, 200));
        jSeparator30.setForeground(new Color(200, 200, 200));
        jSeparator31.setForeground(new Color(200, 200, 200));
        BotaoCancelarGerenciarServicos2.setEnabled(false);
        BotaoExcluirGerenciarGarantia1.setEnabled(false);
        BotaoAlterarGerenciarGarantia1.setEnabled(false);

        lblNome19.setEnabled(false);
        lblNome11.setEnabled(false);
        lblNome22.setEnabled(false);
        lblNome23.setEnabled(false);
        lblNome24.setEnabled(false);
        lblCPF5.setEnabled(false);
    }

    public void DestravaCamposGerenciarClientes() {
        FieldNomeGerenciarClientes.setEnabled(true);
        FieldCPFGerenciarClientes.setEnabled(true);
        FieldTelefoneGerenciarClientes.setEnabled(true);
        FieldCidadeGerenciarClientes.setEnabled(true);
        FieldEnderecoGerenciarClientes.setEnabled(true);
        FieldEmailGerenciarClientes.setEnabled(true);
        jSeparator19.setForeground(new Color(0, 0, 0));
        jSeparator27.setForeground(new Color(0, 0, 0));
        jSeparator28.setForeground(new Color(0, 0, 0));
        jSeparator29.setForeground(new Color(0, 0, 0));
        jSeparator30.setForeground(new Color(0, 0, 0));
        jSeparator31.setForeground(new Color(0, 0, 0));

        BotaoCancelarGerenciarServicos2.setEnabled(true);
        BotaoExcluirGerenciarGarantia1.setEnabled(true);
        BotaoAlterarGerenciarGarantia1.setEnabled(true);

        lblNome19.setEnabled(true);
        lblNome11.setEnabled(true);
        lblNome22.setEnabled(true);
        lblNome23.setEnabled(true);
        lblNome24.setEnabled(true);
        lblCPF5.setEnabled(true);
    }

    public void SalvarCamposGerenciarClientes() {
        FieldNomeGerenciarClientes.setText("");
        FieldCPFGerenciarClientes.setText("");
        FieldTelefoneGerenciarClientes.setText("");
        FieldCidadeGerenciarClientes.setText("");
        FieldEnderecoGerenciarClientes.setText("");
        FieldEmailGerenciarClientes.setText("");

        TravaCamposGerenciarClientes();
    }

    //-----------------------------------
    public String FuncGarantia(LocalDate dataGarantia, String meses) {
        int duracao_garantia = 0;
        try {
            String SQL = "Select duracao_garantia from cadastros.loginsutencao where descricao = ?";
            PreparedStatement stmt = Conexao.getConexaoMySQL().prepareStatement(SQL);
            stmt.setString(1, meses);

            rs = stmt.executeQuery();
            while (rs.next()) {
                duracao_garantia = rs.getInt("duracao_garantia");
            }
            dataGarantia = dataGarantia.plusMonths(duracao_garantia);
            return dataGarantia.format(formatter);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Algo de errado ocorreu! Erro: " + e.getMessage(), "Sistema", JOptionPane.INFORMATION_MESSAGE);
        }
        return dataGarantia.format(formatter);
    }

    public void AutoComplete() {
        clienteDAO clienteDAO = new clienteDAO();
        ArrayList<String> ListaCliente = new ArrayList<>();

        try {

            for (Cliente cli : clienteDAO.ListaCliente()) {
                ListaCliente.add(cli.getNome());
            }

        } catch (SQLException ex) {
            ex.getMessage();
        }

        SetupAutoComplete.setupAutoComplete(CampoNome, ListaCliente);
    }

    public void AutoComplete1() {
        clienteDAO clienteDAO = new clienteDAO();
        ArrayList<String> ListaCliente = new ArrayList<>();

        try {
            for (Cliente cli : clienteDAO.ListaCliente()) {
                ListaCliente.add(cli.getNome());
            }

        } catch (SQLException ex) {
            ex.getMessage();
        }

        SetupAutoComplete.setupAutoComplete(FieldConsultaNomeGarantia, ListaCliente);
    }

    public void AutoComplete2() {
        clienteDAO clienteDAO = new clienteDAO();
        ArrayList<String> ListaCliente = new ArrayList<>();

        try {
            for (Cliente cli : clienteDAO.ListaCliente()) {
                ListaCliente.add(cli.getNome());
            }

        } catch (SQLException ex) {
            ex.getMessage();
        }

        SetupAutoComplete.setupAutoComplete(FieldNomeGerenciarServicos1, ListaCliente);
    }

    private void ComboBoxEscolhaConserto() { //ok
        try {
            String SQL = "Select * from cadastros.loginsutencao order by id asc";
            PreparedStatement stmt = Conexao.getConexaoMySQL().prepareStatement(SQL);

            rs = stmt.executeQuery();
            while (rs.next()) {
                String Nome = rs.getString("descricao");
                ComboEscolhaConserto.addItem(Nome);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void ComboGerenciaEscolhaConserto() { //ok
        try {
            String SQL = "Select * from cadastros.loginsutencao order by id asc";
            PreparedStatement stmt = Conexao.getConexaoMySQL().prepareStatement(SQL);

            rs = stmt.executeQuery();
            while (rs.next()) {
                String Nome = rs.getString("descricao");
                ComboGerenciaEscolhaConserto.addItem(Nome);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void ResetaCombos() {
        ComboEscolhaConserto.removeAllItems();
        ComboGerenciaEscolhaConserto.removeAllItems();
        try {
            ComboEscolhaConserto.setSelectedItem(null);
            ComboGerenciaEscolhaConserto.setSelectedItem(null);
            ComboBoxEscolhaConserto();
            ComboGerenciaEscolhaConserto();
        } catch (Exception e) {

        }
    }

    public void atualizarTabelaConsultaCliente() {
        Cliente cli = new Cliente();
        clienteDAO clidao = new clienteDAO();
        try {
            ListaCliente = clidao.ListaCliente();
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalADM.class.getName()).log(Level.SEVERE, null, ex);
        }
        String dados[][] = new String[ListaCliente.size()][7];
        int i = 0;
        for (Cliente clien : ListaCliente) {
            dados[i][0] = String.valueOf(clien.getId());
            dados[i][1] = clien.getNome();
            dados[i][2] = clien.getCpf();
            dados[i][3] = clien.getTelefone();
            dados[i][4] = clien.getCidade();
            dados[i][5] = clien.getEndereco();
            dados[i][6] = clien.getEmail();
            i++;
        }
        String tituloColuna[] = {"ID", "Nome do cliente", "CPF", "Telefone", "Cidade", "Endereço", "Email"};
        DefaultTableModel tabelaCliente = new DefaultTableModel();
        tabelaCliente.setDataVector(dados, tituloColuna);
        TableConsultaCliente.setModel(new DefaultTableModel(dados, tituloColuna) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        TableConsultaCliente.getColumnModel().getColumn(0).setPreferredWidth(10);
        TableConsultaCliente.getColumnModel().getColumn(1).setPreferredWidth(100);
        TableConsultaCliente.getColumnModel().getColumn(2).setPreferredWidth(100);
        TableConsultaCliente.getColumnModel().getColumn(3).setPreferredWidth(100);
        TableConsultaCliente.getColumnModel().getColumn(4).setPreferredWidth(100);
        TableConsultaCliente.getColumnModel().getColumn(5).setPreferredWidth(100);
        TableConsultaCliente.getColumnModel().getColumn(6).setPreferredWidth(100);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        TableConsultaCliente.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        TableConsultaCliente.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        TableConsultaCliente.getColumnModel().getColumn(2).setCellRenderer(centralizado);
        TableConsultaCliente.getColumnModel().getColumn(3).setCellRenderer(centralizado);
        TableConsultaCliente.getColumnModel().getColumn(4).setCellRenderer(centralizado);
        TableConsultaCliente.getColumnModel().getColumn(5).setCellRenderer(centralizado);
        TableConsultaCliente.getColumnModel().getColumn(6).setCellRenderer(centralizado);
        TableConsultaCliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableConsultaCliente.setRowHeight(25);
        TableConsultaCliente.updateUI();

    }

    public void atualizarTabelaBuscaCliente() {
        Cliente cli = new Cliente();
        String dados[][] = new String[ListaBuscaCliente.size()][7];
        int i = 0;
        for (Cliente clien : ListaBuscaCliente) {
            dados[i][0] = String.valueOf(clien.getId());
            dados[i][1] = clien.getNome();
            dados[i][2] = String.valueOf(clien.getCpf());
            dados[i][3] = clien.getTelefone();
            dados[i][4] = clien.getCidade();
            dados[i][5] = clien.getEndereco();
            dados[i][6] = clien.getEmail();
            i++;
        }
        String tituloColuna[] = {"ID", "Nome do cliente", "CPF", "Telefone", "Cidade", "Endereço", "Email"};
        DefaultTableModel tabelaCliente = new DefaultTableModel();
        tabelaCliente.setDataVector(dados, tituloColuna);
        TableConsultaCliente.setModel(new DefaultTableModel(dados, tituloColuna) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        TableConsultaCliente.getColumnModel().getColumn(0).setPreferredWidth(20);
        TableConsultaCliente.getColumnModel().getColumn(1).setPreferredWidth(100);
        TableConsultaCliente.getColumnModel().getColumn(2).setPreferredWidth(100);
        TableConsultaCliente.getColumnModel().getColumn(3).setPreferredWidth(100);
        TableConsultaCliente.getColumnModel().getColumn(4).setPreferredWidth(100);
        TableConsultaCliente.getColumnModel().getColumn(5).setPreferredWidth(100);
        TableConsultaCliente.getColumnModel().getColumn(6).setPreferredWidth(100);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        TableConsultaCliente.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        TableConsultaCliente.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        TableConsultaCliente.getColumnModel().getColumn(2).setCellRenderer(centralizado);
        TableConsultaCliente.getColumnModel().getColumn(3).setCellRenderer(centralizado);
        TableConsultaCliente.getColumnModel().getColumn(4).setCellRenderer(centralizado);
        TableConsultaCliente.getColumnModel().getColumn(5).setCellRenderer(centralizado);
        TableConsultaCliente.getColumnModel().getColumn(6).setCellRenderer(centralizado);
        TableConsultaCliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableConsultaCliente.setRowHeight(25);
        TableConsultaCliente.updateUI();
    }
//--------------------------------

    public void atualizarTableGerenciarCCliente() {
        Cliente cli = new Cliente();
        clienteDAO clidao = new clienteDAO();
        try {
            ListaCliente = clidao.ListaCliente();
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalADM.class.getName()).log(Level.SEVERE, null, ex);
        }
        String dados[][] = new String[ListaCliente.size()][7];
        int i = 0;
        for (Cliente clien : ListaCliente) {
            dados[i][0] = String.valueOf(clien.getId());
            dados[i][1] = clien.getNome();
            dados[i][2] = clien.getCpf();
            dados[i][3] = clien.getTelefone();
            dados[i][4] = clien.getCidade();
            dados[i][5] = clien.getEndereco();
            dados[i][6] = clien.getEmail();
            i++;
        }
        String tituloColuna[] = {"ID", "Nome do cliente", "CPF", "Telefone", "Cidade", "Endereço", "Email"};
        DefaultTableModel tabelaCliente = new DefaultTableModel();
        tabelaCliente.setDataVector(dados, tituloColuna);
        TableGerenciarCliente.setModel(new DefaultTableModel(dados, tituloColuna) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        TableGerenciarCliente.getColumnModel().getColumn(0).setPreferredWidth(10);
        TableGerenciarCliente.getColumnModel().getColumn(1).setPreferredWidth(100);
        TableGerenciarCliente.getColumnModel().getColumn(2).setPreferredWidth(100);
        TableGerenciarCliente.getColumnModel().getColumn(3).setPreferredWidth(100);
        TableGerenciarCliente.getColumnModel().getColumn(4).setPreferredWidth(100);
        TableGerenciarCliente.getColumnModel().getColumn(5).setPreferredWidth(100);
        TableGerenciarCliente.getColumnModel().getColumn(6).setPreferredWidth(100);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        TableGerenciarCliente.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        TableGerenciarCliente.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        TableGerenciarCliente.getColumnModel().getColumn(2).setCellRenderer(centralizado);
        TableGerenciarCliente.getColumnModel().getColumn(3).setCellRenderer(centralizado);
        TableGerenciarCliente.getColumnModel().getColumn(4).setCellRenderer(centralizado);
        TableGerenciarCliente.getColumnModel().getColumn(5).setCellRenderer(centralizado);
        TableGerenciarCliente.getColumnModel().getColumn(6).setCellRenderer(centralizado);
        TableGerenciarCliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableGerenciarCliente.setRowHeight(25);
        TableGerenciarCliente.updateUI();

    }

    public void atualizarTableGerenciarBCliente() {
        Cliente cli = new Cliente();
        String dados[][] = new String[ListaBuscaCliente.size()][7];
        int i = 0;
        for (Cliente clien : ListaBuscaCliente) {
            dados[i][0] = String.valueOf(clien.getId());
            dados[i][1] = clien.getNome();
            dados[i][2] = String.valueOf(clien.getCpf());
            dados[i][3] = clien.getTelefone();
            dados[i][4] = clien.getCidade();
            dados[i][5] = clien.getEndereco();
            dados[i][6] = clien.getEmail();
            i++;
        }
        String tituloColuna[] = {"ID", "Nome do cliente", "CPF", "Telefone", "Cidade", "Endereço", "Email"};
        DefaultTableModel tabelaCliente = new DefaultTableModel();
        tabelaCliente.setDataVector(dados, tituloColuna);
        TableGerenciarCliente.setModel(new DefaultTableModel(dados, tituloColuna) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        TableGerenciarCliente.getColumnModel().getColumn(0).setPreferredWidth(20);
        TableGerenciarCliente.getColumnModel().getColumn(1).setPreferredWidth(100);
        TableGerenciarCliente.getColumnModel().getColumn(2).setPreferredWidth(100);
        TableGerenciarCliente.getColumnModel().getColumn(3).setPreferredWidth(100);
        TableGerenciarCliente.getColumnModel().getColumn(4).setPreferredWidth(100);
        TableGerenciarCliente.getColumnModel().getColumn(5).setPreferredWidth(100);
        TableGerenciarCliente.getColumnModel().getColumn(6).setPreferredWidth(100);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        TableGerenciarCliente.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        TableGerenciarCliente.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        TableGerenciarCliente.getColumnModel().getColumn(2).setCellRenderer(centralizado);
        TableGerenciarCliente.getColumnModel().getColumn(3).setCellRenderer(centralizado);
        TableGerenciarCliente.getColumnModel().getColumn(4).setCellRenderer(centralizado);
        TableGerenciarCliente.getColumnModel().getColumn(5).setCellRenderer(centralizado);
        TableGerenciarCliente.getColumnModel().getColumn(6).setCellRenderer(centralizado);
        TableGerenciarCliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableGerenciarCliente.setRowHeight(25);
        TableGerenciarCliente.updateUI();
    }
//--------------------------------

    public void atualizarTabelaConsultaGarantia() {
        Garantia gar = new Garantia();
        GarantiaDAO gardao = new GarantiaDAO();
        try {
            ListaGarantia = gardao.ListaGarantia();
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalADM.class.getName()).log(Level.SEVERE, null, ex);
        }
        String dados[][] = new String[ListaGarantia.size()][5];
        int i = 0;
        for (Garantia gara : ListaGarantia) {
            dados[i][0] = String.valueOf(gara.getId());
            dados[i][1] = gara.getNome();
            dados[i][2] = gara.getDescricao();
            dados[i][3] = String.valueOf(gara.getSaida_concerto().format(formatter));
            dados[i][4] = String.valueOf(gara.getDt_garantia().format(formatter));
            i++;
        }
        String tituloColuna[] = {"ID", "Nome do cliente", "Nome do conserto", "Data do conserto", "Prazo de garantia (mês)"};
        DefaultTableModel tabelaCliente = new DefaultTableModel();
        tabelaCliente.setDataVector(dados, tituloColuna);
        TableConsultaGarantia.setModel(new DefaultTableModel(dados, tituloColuna) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        TableConsultaGarantia.getColumnModel().getColumn(0).setPreferredWidth(20);
        TableConsultaGarantia.getColumnModel().getColumn(1).setPreferredWidth(180);
        TableConsultaGarantia.getColumnModel().getColumn(2).setPreferredWidth(100);
        TableConsultaGarantia.getColumnModel().getColumn(3).setPreferredWidth(100);
        TableConsultaGarantia.getColumnModel().getColumn(4).setPreferredWidth(100);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        TableConsultaGarantia.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        TableConsultaGarantia.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        TableConsultaGarantia.getColumnModel().getColumn(2).setCellRenderer(centralizado);
        TableConsultaGarantia.getColumnModel().getColumn(3).setCellRenderer(centralizado);
        TableConsultaGarantia.getColumnModel().getColumn(4).setCellRenderer(centralizado);
        TableConsultaGarantia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableConsultaGarantia.setRowHeight(25);
        TableConsultaGarantia.updateUI();
    }

    public void atualizarTabelaBuscaGarantia() {
        Garantia gar = new Garantia();
        String dados[][] = new String[ListaBuscaGarantia.size()][5];
        int i = 0;
        for (Garantia gara : ListaBuscaGarantia) {
            dados[i][0] = String.valueOf(gara.getId());
            dados[i][1] = gara.getNome();
            dados[i][2] = gara.getDescricao();
            dados[i][3] = String.valueOf(gara.getSaida_concerto().format(formatter));
            dados[i][4] = String.valueOf(gara.getDt_garantia().format(formatter));
            i++;
        }
        String tituloColuna[] = {"ID", "Nome do cliente", "Nome do conserto", "Data do conserto", "Prazo de garantia (mês)"};
        DefaultTableModel tabelaCliente = new DefaultTableModel();
        tabelaCliente.setDataVector(dados, tituloColuna);
        TableConsultaGarantia.setModel(new DefaultTableModel(dados, tituloColuna) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        TableConsultaGarantia.getColumnModel().getColumn(0).setPreferredWidth(20);
        TableConsultaGarantia.getColumnModel().getColumn(1).setPreferredWidth(180);
        TableConsultaGarantia.getColumnModel().getColumn(2).setPreferredWidth(100);
        TableConsultaGarantia.getColumnModel().getColumn(3).setPreferredWidth(100);
        TableConsultaGarantia.getColumnModel().getColumn(4).setPreferredWidth(100);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        TableConsultaGarantia.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        TableConsultaGarantia.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        TableConsultaGarantia.getColumnModel().getColumn(2).setCellRenderer(centralizado);
        TableConsultaGarantia.getColumnModel().getColumn(3).setCellRenderer(centralizado);
        TableConsultaGarantia.getColumnModel().getColumn(4).setCellRenderer(centralizado);
        TableConsultaGarantia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableConsultaGarantia.setRowHeight(25);
        TableConsultaGarantia.updateUI();
    }

//----------------------------------------
    public void atualizarTabelaGerenciaCGarantia() {
        Garantia gar = new Garantia();
        GarantiaDAO gardao = new GarantiaDAO();
        try {
            ListaGarantia = gardao.ListaGarantia();
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalADM.class.getName()).log(Level.SEVERE, null, ex);
        }
        String dados[][] = new String[ListaGarantia.size()][5];
        int i = 0;
        for (Garantia gara : ListaGarantia) {
            dados[i][0] = String.valueOf(gara.getId());
            dados[i][1] = gara.getNome();
            dados[i][2] = gara.getDescricao();
            dados[i][3] = String.valueOf(gara.getSaida_concerto().format(formatter));
            dados[i][4] = String.valueOf(gara.getDt_garantia().format(formatter));
            i++;
        }
        String tituloColuna[] = {"ID", "Nome do cliente", "Nome do conserto", "Data do conserto", "Prazo de garantia (mês)"};
        DefaultTableModel tabelaCliente = new DefaultTableModel();
        tabelaCliente.setDataVector(dados, tituloColuna);
        TableGerenciarGarantia.setModel(new DefaultTableModel(dados, tituloColuna) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        TableGerenciarGarantia.getColumnModel().getColumn(0).setPreferredWidth(20);
        TableGerenciarGarantia.getColumnModel().getColumn(1).setPreferredWidth(180);
        TableGerenciarGarantia.getColumnModel().getColumn(2).setPreferredWidth(100);
        TableGerenciarGarantia.getColumnModel().getColumn(3).setPreferredWidth(100);
        TableGerenciarGarantia.getColumnModel().getColumn(4).setPreferredWidth(100);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        TableGerenciarGarantia.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        TableGerenciarGarantia.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        TableGerenciarGarantia.getColumnModel().getColumn(2).setCellRenderer(centralizado);
        TableGerenciarGarantia.getColumnModel().getColumn(3).setCellRenderer(centralizado);
        TableGerenciarGarantia.getColumnModel().getColumn(4).setCellRenderer(centralizado);
        TableGerenciarGarantia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableGerenciarGarantia.setRowHeight(25);
        TableGerenciarGarantia.updateUI();
    }

    public void atualizarTabelaBuscaBGarantia() {
        Garantia gar = new Garantia();
        String dados[][] = new String[ListaBuscaGarantia.size()][5];
        int i = 0;
        for (Garantia gara : ListaBuscaGarantia) {
            dados[i][0] = String.valueOf(gara.getId());
            dados[i][1] = gara.getNome();
            dados[i][2] = gara.getDescricao();
            dados[i][3] = String.valueOf(gara.getSaida_concerto().format(formatter));
            dados[i][4] = String.valueOf(gara.getDt_garantia().format(formatter));
            i++;
        }
        String tituloColuna[] = {"ID", "Nome do cliente", "Nome do conserto", "Data do conserto", "Prazo de garantia (mês)"};
        DefaultTableModel tabelaCliente = new DefaultTableModel();
        tabelaCliente.setDataVector(dados, tituloColuna);
        TableGerenciarGarantia.setModel(new DefaultTableModel(dados, tituloColuna) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        TableGerenciarGarantia.getColumnModel().getColumn(0).setPreferredWidth(20);
        TableGerenciarGarantia.getColumnModel().getColumn(1).setPreferredWidth(180);
        TableGerenciarGarantia.getColumnModel().getColumn(2).setPreferredWidth(100);
        TableGerenciarGarantia.getColumnModel().getColumn(3).setPreferredWidth(100);
        TableGerenciarGarantia.getColumnModel().getColumn(4).setPreferredWidth(100);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        TableGerenciarGarantia.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        TableGerenciarGarantia.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        TableGerenciarGarantia.getColumnModel().getColumn(2).setCellRenderer(centralizado);
        TableGerenciarGarantia.getColumnModel().getColumn(3).setCellRenderer(centralizado);
        TableGerenciarGarantia.getColumnModel().getColumn(4).setCellRenderer(centralizado);
        TableGerenciarGarantia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableGerenciarGarantia.setRowHeight(25);
        TableGerenciarGarantia.updateUI();
    }
//----------------------------------------

    public void atualizarTabelaConsultaManutencao() {
        Manutencao logins = new Manutencao();
        ManutencaoDAO loginsdao = new ManutencaoDAO();
        try {
            ListaManutencao = loginsdao.ListaManutencao();
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalADM.class.getName()).log(Level.SEVERE, null, ex);
        }
        String dados[][] = new String[ListaManutencao.size()][3];
        int i = 0;
        for (Manutencao loginsu : ListaManutencao) {
            dados[i][0] = String.valueOf(loginsu.getId());
            dados[i][1] = loginsu.getDescricao();
            dados[i][2] = String.valueOf(loginsu.getDuracao());
            i++;
        }
        String tituloColuna[] = {"ID", "Nome do conserto", "Prazo de garantia (mês)"};
        DefaultTableModel tabelaCliente = new DefaultTableModel();
        tabelaCliente.setDataVector(dados, tituloColuna);
        TabelaConsultaManutencao.setModel(new DefaultTableModel(dados, tituloColuna) {
            boolean[] canEdit = new boolean[]{
                false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        TabelaConsultaManutencao.getColumnModel().getColumn(0).setPreferredWidth(10);
        TabelaConsultaManutencao.getColumnModel().getColumn(1).setPreferredWidth(190);
        TabelaConsultaManutencao.getColumnModel().getColumn(2).setPreferredWidth(190);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        TabelaConsultaManutencao.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        TabelaConsultaManutencao.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        TabelaConsultaManutencao.getColumnModel().getColumn(2).setCellRenderer(centralizado);
        TabelaConsultaManutencao.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TabelaConsultaManutencao.setRowHeight(25);
        TabelaConsultaManutencao.updateUI();
    }

    public void atualizarTabelaBuscaManutencao() {
        Manutencao logins = new Manutencao();
        String dados[][] = new String[ListaBuscaManutencao.size()][3];
        int i = 0;
        for (Manutencao loginsu : ListaBuscaManutencao) {
            dados[i][0] = String.valueOf(loginsu.getId());
            dados[i][1] = loginsu.getDescricao();
            dados[i][2] = String.valueOf(loginsu.getDuracao());
            i++;
        }
        String tituloColuna[] = {"ID", "Nome do conserto", "Prazo de garantia (mês)"};
        DefaultTableModel tabelaCliente = new DefaultTableModel();
        tabelaCliente.setDataVector(dados, tituloColuna);
        TabelaConsultaManutencao.setModel(new DefaultTableModel(dados, tituloColuna) {
            boolean[] canEdit = new boolean[]{
                false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        TabelaConsultaManutencao.getColumnModel().getColumn(0).setPreferredWidth(10);
        TabelaConsultaManutencao.getColumnModel().getColumn(1).setPreferredWidth(190);
        TabelaConsultaManutencao.getColumnModel().getColumn(2).setPreferredWidth(190);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        TabelaConsultaManutencao.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        TabelaConsultaManutencao.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        TabelaConsultaManutencao.getColumnModel().getColumn(2).setCellRenderer(centralizado);
        TabelaConsultaManutencao.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TabelaConsultaManutencao.setRowHeight(25);
        TabelaConsultaManutencao.updateUI();
    }
//--------------------------

    public void atualizarTabelaGerenciaCManutencao() {
        Manutencao logins = new Manutencao();
        ManutencaoDAO loginsdao = new ManutencaoDAO();
        try {
            ListaManutencao = loginsdao.ListaManutencao();
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalADM.class.getName()).log(Level.SEVERE, null, ex);
        }
        String dados[][] = new String[ListaManutencao.size()][3];
        int i = 0;
        for (Manutencao loginsu : ListaManutencao) {
            dados[i][0] = String.valueOf(loginsu.getId());
            dados[i][1] = loginsu.getDescricao();
            dados[i][2] = String.valueOf(loginsu.getDuracao());
            i++;
        }
        String tituloColuna[] = {"ID", "Nome do conserto", "Prazo de garantia (mês)"};
        DefaultTableModel tabelaCliente = new DefaultTableModel();
        tabelaCliente.setDataVector(dados, tituloColuna);
        TableGerenciarServicos.setModel(new DefaultTableModel(dados, tituloColuna) {
            boolean[] canEdit = new boolean[]{
                false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        TableGerenciarServicos.getColumnModel().getColumn(0).setPreferredWidth(10);
        TableGerenciarServicos.getColumnModel().getColumn(1).setPreferredWidth(190);
        TableGerenciarServicos.getColumnModel().getColumn(2).setPreferredWidth(190);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        TableGerenciarServicos.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        TableGerenciarServicos.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        TableGerenciarServicos.getColumnModel().getColumn(2).setCellRenderer(centralizado);
        TableGerenciarServicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableGerenciarServicos.setRowHeight(25);
        TableGerenciarServicos.updateUI();
    }

    public void atualizarTabelaGerenciaBManutencao() {
        Manutencao logins = new Manutencao();
        String dados[][] = new String[ListaBuscaManutencao.size()][3];
        int i = 0;
        for (Manutencao loginsu : ListaBuscaManutencao) {
            dados[i][0] = String.valueOf(loginsu.getId());
            dados[i][1] = loginsu.getDescricao();
            dados[i][2] = String.valueOf(loginsu.getDuracao());
            i++;
        }
        String tituloColuna[] = {"ID", "Nome do conserto", "Prazo de garantia (mês)"};
        DefaultTableModel tabelaCliente = new DefaultTableModel();
        tabelaCliente.setDataVector(dados, tituloColuna);
        TableGerenciarServicos.setModel(new DefaultTableModel(dados, tituloColuna) {
            boolean[] canEdit = new boolean[]{
                false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        TableGerenciarServicos.getColumnModel().getColumn(0).setPreferredWidth(10);
        TableGerenciarServicos.getColumnModel().getColumn(1).setPreferredWidth(190);
        TableGerenciarServicos.getColumnModel().getColumn(2).setPreferredWidth(190);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        TableGerenciarServicos.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        TableGerenciarServicos.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        TableGerenciarServicos.getColumnModel().getColumn(2).setCellRenderer(centralizado);
        TableGerenciarServicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableGerenciarServicos.setRowHeight(25);
        TableGerenciarServicos.updateUI();
    }
//---------------------------------

    public void TableGerenciarLoginss() {

        DefaultTableModel modelo = (DefaultTableModel) TableGerenciarLogins.getModel();
        modelo.setNumRows(0);
        LoginsDAO logins = new LoginsDAO();

        for (Logins login : logins.read()) {

            modelo.addRow(new Object[]{
                login.getid(),
                login.getusuario(),
                login.getsenha(),
                login.gettipoconta(),});

        }
        TableGerenciarLogins.getColumnModel().getColumn(0).setPreferredWidth(10);
        TableGerenciarLogins.getColumnModel().getColumn(1).setPreferredWidth(190);
        TableGerenciarLogins.getColumnModel().getColumn(2).setPreferredWidth(190);
        TableGerenciarLogins.getColumnModel().getColumn(3).setPreferredWidth(190);
        
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        TableGerenciarLogins.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        TableGerenciarLogins.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        TableGerenciarLogins.getColumnModel().getColumn(2).setCellRenderer(centralizado);
         TableGerenciarLogins.getColumnModel().getColumn(3).setCellRenderer(centralizado);
        TableGerenciarLogins.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableGerenciarLogins.setRowHeight(25);
        TableGerenciarLogins.updateUI();
    }

    public void PesquisarTabela(String desc) {

        DefaultTableModel modelo = (DefaultTableModel) TableGerenciarLogins.getModel();
        modelo.setNumRows(0);
        LoginsDAO logins = new LoginsDAO();

        for (Logins login : logins.PesquisarLogin(desc)) {

            modelo.addRow(new Object[]{
                login.getid(),
                login.getusuario(),
                login.getsenha(),
                login.gettipoconta(),});

        }

    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BotaoAlterarGerLogins1 = new javax.swing.JButton();
        SideBoard = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        BotaoGarantias = new javax.swing.JLabel();
        BotaoClientes = new javax.swing.JLabel();
        BotaoRelatorios = new javax.swing.JLabel();
        BotaoConsertos = new javax.swing.JLabel();
        BotaoCadastros = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        PaneMae = new javax.swing.JPanel();
        PaneClientes = new javax.swing.JPanel();
        CadastrarClientes = new javax.swing.JPanel();
        jSeparator14 = new javax.swing.JSeparator();
        jSeparator21 = new javax.swing.JSeparator();
        FieldCadastroTelefoneCliente = new javax.swing.JFormattedTextField();
        FieldCadastroCPFCliente = new javax.swing.JTextField();
        BotaoCancelarCadastroCliente = new javax.swing.JButton();
        lblNome3 = new javax.swing.JLabel();
        lblNome6 = new javax.swing.JLabel();
        FieldCadastroNomeCliente = new javax.swing.JTextField();
        FieldCadastroEnderecoCliente = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator16 = new javax.swing.JSeparator();
        BotaoSalvarCadastroCliente = new javax.swing.JButton();
        lblNome8 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        FieldCadastroEmailCliente = new javax.swing.JTextField();
        lblNome4 = new javax.swing.JLabel();
        jSeparator17 = new javax.swing.JSeparator();
        lblNome9 = new javax.swing.JLabel();
        lblCPF4 = new javax.swing.JLabel();
        FieldCadastroCidadeCliente = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        BotaoGerenciarCli = new javax.swing.JLabel();
        BotaoConsultarCli = new javax.swing.JLabel();
        BotaoClientes2 = new javax.swing.JLabel();
        BotaoNovoCadastroCliente = new javax.swing.JButton();
        ConsultarClientes = new javax.swing.JPanel();
        lblNome2 = new javax.swing.JLabel();
        FieldConsultaNomeCliente = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        lblCPF2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableConsultaCliente = new javax.swing.JTable();
        jSeparator11 = new javax.swing.JSeparator();
        BotaoBuscarConsultaCliente = new javax.swing.JButton();
        FieldConsultaCPFCliente = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        BotaoGerenciarCliConsul = new javax.swing.JLabel();
        BotaoConsultarCliConsul = new javax.swing.JLabel();
        BotaoCadastrarCliConsul = new javax.swing.JLabel();
        GerenciarClientes = new javax.swing.JPanel();
        lblNome10 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableGerenciarCliente = new javax.swing.JTable();
        DadosAlteraClientes = new javax.swing.JPanel();
        BotaoCancelarGerenciarServicos2 = new javax.swing.JButton();
        BotaoExcluirGerenciarGarantia1 = new javax.swing.JButton();
        BotaoAlterarGerenciarGarantia1 = new javax.swing.JButton();
        lblNome19 = new javax.swing.JLabel();
        FieldNomeGerenciarClientes = new javax.swing.JTextField();
        jSeparator27 = new javax.swing.JSeparator();
        FieldIDGerenciarClientes = new javax.swing.JTextField();
        lblCPF5 = new javax.swing.JLabel();
        FieldCPFGerenciarClientes = new javax.swing.JTextField();
        jSeparator19 = new javax.swing.JSeparator();
        jSeparator28 = new javax.swing.JSeparator();
        FieldTelefoneGerenciarClientes = new javax.swing.JFormattedTextField();
        lblNome11 = new javax.swing.JLabel();
        lblNome22 = new javax.swing.JLabel();
        FieldCidadeGerenciarClientes = new javax.swing.JTextField();
        jSeparator29 = new javax.swing.JSeparator();
        lblNome23 = new javax.swing.JLabel();
        FieldEnderecoGerenciarClientes = new javax.swing.JTextField();
        jSeparator30 = new javax.swing.JSeparator();
        lblNome24 = new javax.swing.JLabel();
        FieldEmailGerenciarClientes = new javax.swing.JTextField();
        jSeparator31 = new javax.swing.JSeparator();
        FieldGerenciarNomeCliente = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator32 = new javax.swing.JSeparator();
        FieldGerenciarCPFCliente = new javax.swing.JTextField();
        lblCPF6 = new javax.swing.JLabel();
        BotaoBuscarConsultaCliente1 = new javax.swing.JButton();
        jPanel24 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        BotaoGerenciarCliGer = new javax.swing.JLabel();
        BotaoConsultarCliGer = new javax.swing.JLabel();
        BotaoCadastrarCliGer = new javax.swing.JLabel();
        PaneGarantias = new javax.swing.JPanel();
        CadastrarGarantias = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        CampoNome = new javax.swing.JTextField();
        BotaoSalvarGarantia = new javax.swing.JButton();
        lblNome = new javax.swing.JLabel();
        lblConserto = new javax.swing.JLabel();
        BotaoCancelarGarantia = new javax.swing.JButton();
        ComboEscolhaConserto = new javax.swing.JComboBox<>();
        BotaoNovoGarantia = new javax.swing.JButton();
        CampoDataFormatada = new javax.swing.JFormattedTextField();
        jSeparator4 = new javax.swing.JSeparator();
        lblData = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        BotaoGerenciarCli1 = new javax.swing.JLabel();
        BotaoConsultarCli1 = new javax.swing.JLabel();
        BotaoClientes3 = new javax.swing.JLabel();
        ConsultarGarantias = new javax.swing.JPanel();
        lblNome1 = new javax.swing.JLabel();
        FieldConsultaNomeGarantia = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        lblCPF1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableConsultaGarantia = new javax.swing.JTable();
        BotaoBuscarConsultarGarantias = new javax.swing.JButton();
        ComboOrdenaGarantia = new javax.swing.JComboBox<>();
        BotaoBuscarConsultarGarantias1 = new javax.swing.JButton();
        jPanel28 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        BotaoGerenciarCliConsul1 = new javax.swing.JLabel();
        BotaoConsultarCliConsul1 = new javax.swing.JLabel();
        BotaoCadastrarCliConsul1 = new javax.swing.JLabel();
        GerenciarGarantias = new javax.swing.JPanel();
        jSeparator25 = new javax.swing.JSeparator();
        FieldNomeGerenciarServicos1 = new javax.swing.JTextField();
        lblNome17 = new javax.swing.JLabel();
        BotaoBuscarGerenciarServicos1 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        TableGerenciarGarantia = new javax.swing.JTable();
        DadosAlteraGarantia = new javax.swing.JPanel();
        BotaoCancelarGerenciarGarantia = new javax.swing.JButton();
        BotaoExcluirGerenciarGarantia = new javax.swing.JButton();
        BotaoAlterarGerenciarGarantia = new javax.swing.JButton();
        lblNome18 = new javax.swing.JLabel();
        FieldNomeGerenciarGarantia = new javax.swing.JTextField();
        jSeparator26 = new javax.swing.JSeparator();
        FieldIDGerenciarGarantia = new javax.swing.JTextField();
        lblData1 = new javax.swing.JLabel();
        CampoGerenciaDataFormatada = new javax.swing.JFormattedTextField();
        jSeparator5 = new javax.swing.JSeparator();
        lblConserto1 = new javax.swing.JLabel();
        ComboGerenciaEscolhaConserto = new javax.swing.JComboBox<>();
        Botao2Via = new javax.swing.JButton();
        lblCPF3 = new javax.swing.JLabel();
        ComboOrdenaGarantia1 = new javax.swing.JComboBox<>();
        jPanel26 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        BotaoGerenciarCliGer1 = new javax.swing.JLabel();
        BotaoConsultarCliGer1 = new javax.swing.JLabel();
        BotaoCadastrarCliGer1 = new javax.swing.JLabel();
        PaneContatarCli = new javax.swing.JPanel();
        lblCPF7 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        BotaoBuscarConsultarGarantias2 = new javax.swing.JButton();
        lblCPF8 = new javax.swing.JLabel();
        TxtNomeCtt = new javax.swing.JTextField();
        TxtTelCtt = new javax.swing.JTextField();
        BotaoBuscarConsultarGarantias3 = new javax.swing.JButton();
        jPanel44 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        BotaoGerenciarCliGer2 = new javax.swing.JLabel();
        BotaoConsultarCliGer2 = new javax.swing.JLabel();
        BotaoCadastrarCliGer2 = new javax.swing.JLabel();
        PaneServicos = new javax.swing.JPanel();
        CadastrarServicos = new javax.swing.JPanel();
        lblNome5 = new javax.swing.JLabel();
        FieldNomeCadastrarConsertos = new javax.swing.JTextField();
        jSeparator13 = new javax.swing.JSeparator();
        BotaoNovoCadastroConserto = new javax.swing.JButton();
        BotaoSalvarCadastroConserto = new javax.swing.JButton();
        BotaoCancelarCadastroConserto = new javax.swing.JButton();
        FieldDuracaoCadastrarConsertos = new javax.swing.JTextField();
        jSeparator22 = new javax.swing.JSeparator();
        lblNome13 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        BotaoGerenciarCli2 = new javax.swing.JLabel();
        BotaoConsultarCli2 = new javax.swing.JLabel();
        BotaoClientes4 = new javax.swing.JLabel();
        ConsultarServicos = new javax.swing.JPanel();
        FieldConsultaDescricaoGarantias = new javax.swing.JTextField();
        lblNome7 = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        TabelaConsultaManutencao = new javax.swing.JTable();
        BotaoBuscarGarantias = new javax.swing.JButton();
        jPanel30 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        BotaoGerenciarCliConsul2 = new javax.swing.JLabel();
        BotaoConsultarCliConsul2 = new javax.swing.JLabel();
        BotaoCadastrarCliConsul2 = new javax.swing.JLabel();
        GerenciarServicos = new javax.swing.JPanel();
        jSeparator20 = new javax.swing.JSeparator();
        FieldNomeGerenciarServicos = new javax.swing.JTextField();
        lblNome12 = new javax.swing.JLabel();
        BotaoBuscarGerenciarServicos = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        TableGerenciarServicos = new javax.swing.JTable();
        DadosAlteraServicos = new javax.swing.JPanel();
        BotaoCancelarGerenciarServicos = new javax.swing.JButton();
        BotaoExcluirGerenciarServicos = new javax.swing.JButton();
        BotaoAlterarGerenciarServicos = new javax.swing.JButton();
        lblNome14 = new javax.swing.JLabel();
        FieldNomeGerenciarConsertos = new javax.swing.JTextField();
        jSeparator23 = new javax.swing.JSeparator();
        lblNome15 = new javax.swing.JLabel();
        FieldDuracaoGerenciarConsertos = new javax.swing.JTextField();
        jSeparator24 = new javax.swing.JSeparator();
        FieldIDGerenciarConsertos = new javax.swing.JTextField();
        jPanel32 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        BotaoGerenciarCliConsul3 = new javax.swing.JLabel();
        BotaoConsultarCliConsul3 = new javax.swing.JLabel();
        BotaoCadastrarCliConsul3 = new javax.swing.JLabel();
        PaneRelatorios = new javax.swing.JPanel();
        RelatorioClientes = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        BotaoGerenciarCliConsul4 = new javax.swing.JLabel();
        BotaoConsultarCliConsul4 = new javax.swing.JLabel();
        BotaoCadastrarCliConsul4 = new javax.swing.JLabel();
        BotaoRelatorioCliente = new javax.swing.JButton();
        RelatorioServicos = new javax.swing.JPanel();
        jPanel38 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        BotaoGerenciarCliConsul6 = new javax.swing.JLabel();
        BotaoConsultarCliConsul6 = new javax.swing.JLabel();
        BotaoCadastrarCliConsul6 = new javax.swing.JLabel();
        RelatorioGerenciar = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        BotaoGerenciarCliConsul7 = new javax.swing.JLabel();
        BotaoConsultarCliConsul7 = new javax.swing.JLabel();
        BotaoCadastrarCliConsul7 = new javax.swing.JLabel();
        PaneCadastros = new javax.swing.JPanel();
        AdicionarCadastros = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        BotaoConsultarCliConsul8 = new javax.swing.JLabel();
        BotaoCadastrarCliConsul8 = new javax.swing.JLabel();
        lblNome27 = new javax.swing.JLabel();
        FielduserCadastrarlogins = new javax.swing.JTextField();
        jSeparator36 = new javax.swing.JSeparator();
        BotaoNovoCadastroCliente5 = new javax.swing.JLabel();
        jSeparator37 = new javax.swing.JSeparator();
        lblNome28 = new javax.swing.JLabel();
        lblNome29 = new javax.swing.JLabel();
        ComboEscolhalogins = new javax.swing.JComboBox<>();
        FieldsenhaCadastrarlogins = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        Fieldversenha = new javax.swing.JTextField();
        GerenciarCadastros = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        BotaoConsultarCliConsul5 = new javax.swing.JLabel();
        BotaoCadastrarCliConsul5 = new javax.swing.JLabel();
        lblNome26 = new javax.swing.JLabel();
        FieldUserGerenciarLogins = new javax.swing.JTextField();
        jSeparator35 = new javax.swing.JSeparator();
        BotaoNovoCadastroCliente4 = new javax.swing.JLabel();
        GerenciarLogins = new javax.swing.JPanel();
        jSeparator33 = new javax.swing.JSeparator();
        lblNome20 = new javax.swing.JLabel();
        jSeparator34 = new javax.swing.JSeparator();
        lblNome21 = new javax.swing.JLabel();
        ComboTipoConta = new javax.swing.JComboBox<>();
        lblNome30 = new javax.swing.JLabel();
        FieldUser = new javax.swing.JTextField();
        FieldSenha = new javax.swing.JTextField();
        BotaoAlterarGerLogins = new javax.swing.JButton();
        BotaoExcluirGerLogins = new javax.swing.JButton();
        BotaoCancelarGerLogins = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        TableGerenciarLogins = new javax.swing.JTable();
        FieldId = new javax.swing.JTextField();

        BotaoAlterarGerLogins1.setBackground(new java.awt.Color(230, 230, 230));
        BotaoAlterarGerLogins1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoAlterarGerLogins1.setText("Alterar");
        BotaoAlterarGerLogins1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoAlterarGerLogins1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoAlterarGerLogins1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gerenciamento de Garantias");
        setIconImages(null);
        setPreferredSize(new java.awt.Dimension(1364, 750));

        SideBoard.setBackground(new java.awt.Color(230, 230, 230));
        SideBoard.setForeground(new java.awt.Color(230, 230, 230));
        SideBoard.setToolTipText("");
        SideBoard.setMaximumSize(new java.awt.Dimension(300, 400));

        jSeparator1.setBackground(new java.awt.Color(25, 25, 112));
        jSeparator1.setForeground(new java.awt.Color(230, 230, 230));

        BotaoGarantias.setBackground(new java.awt.Color(230, 230, 230));
        BotaoGarantias.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoGarantias.setForeground(new java.awt.Color(25, 25, 112));
        BotaoGarantias.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoGarantias.setText("Garantias");
        BotaoGarantias.setMaximumSize(new java.awt.Dimension(139, 25));
        BotaoGarantias.setMinimumSize(new java.awt.Dimension(139, 25));
        BotaoGarantias.setOpaque(true);
        BotaoGarantias.setPreferredSize(new java.awt.Dimension(139, 75));
        BotaoGarantias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoGarantiasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoGarantiasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoGarantiasMouseExited(evt);
            }
        });

        BotaoClientes.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoClientes.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes.setText("Clientes");
        BotaoClientes.setOpaque(true);
        BotaoClientes.setPreferredSize(new java.awt.Dimension(101, 75));
        BotaoClientes.setVerifyInputWhenFocusTarget(false);
        BotaoClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoClientesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoClientesMouseExited(evt);
            }
        });

        BotaoRelatorios.setBackground(new java.awt.Color(230, 230, 230));
        BotaoRelatorios.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoRelatorios.setForeground(new java.awt.Color(23, 23, 112));
        BotaoRelatorios.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoRelatorios.setText("Relatórios");
        BotaoRelatorios.setMaximumSize(new java.awt.Dimension(139, 25));
        BotaoRelatorios.setMinimumSize(new java.awt.Dimension(139, 25));
        BotaoRelatorios.setOpaque(true);
        BotaoRelatorios.setPreferredSize(new java.awt.Dimension(139, 75));
        BotaoRelatorios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoRelatoriosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoRelatoriosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoRelatoriosMouseExited(evt);
            }
        });

        BotaoConsertos.setBackground(new java.awt.Color(230, 230, 230));
        BotaoConsertos.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoConsertos.setForeground(new java.awt.Color(23, 23, 112));
        BotaoConsertos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoConsertos.setText("Categorias");
        BotaoConsertos.setMaximumSize(new java.awt.Dimension(139, 25));
        BotaoConsertos.setMinimumSize(new java.awt.Dimension(139, 25));
        BotaoConsertos.setOpaque(true);
        BotaoConsertos.setPreferredSize(new java.awt.Dimension(139, 75));
        BotaoConsertos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoConsertosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoConsertosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoConsertosMouseExited(evt);
            }
        });

        BotaoCadastros.setBackground(new java.awt.Color(230, 230, 230));
        BotaoCadastros.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoCadastros.setForeground(new java.awt.Color(23, 23, 112));
        BotaoCadastros.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoCadastros.setText("Logins");
        BotaoCadastros.setMaximumSize(new java.awt.Dimension(139, 25));
        BotaoCadastros.setMinimumSize(new java.awt.Dimension(139, 25));
        BotaoCadastros.setOpaque(true);
        BotaoCadastros.setPreferredSize(new java.awt.Dimension(139, 75));
        BotaoCadastros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoCadastrosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoCadastrosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoCadastrosMouseExited(evt);
            }
        });

        javax.swing.GroupLayout SideBoardLayout = new javax.swing.GroupLayout(SideBoard);
        SideBoard.setLayout(SideBoardLayout);
        SideBoardLayout.setHorizontalGroup(
            SideBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BotaoClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(BotaoGarantias, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
            .addComponent(BotaoConsertos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(BotaoRelatorios, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(BotaoCadastros, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(SideBoardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
            .addGroup(SideBoardLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SideBoardLayout.setVerticalGroup(
            SideBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SideBoardLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(jLabel4)
                .addGap(68, 68, 68)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(BotaoClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoGarantias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoConsertos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoRelatorios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoCadastros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PaneMae.setPreferredSize(new java.awt.Dimension(1144, 750));
        PaneMae.setLayout(new java.awt.CardLayout());

        PaneClientes.setPreferredSize(new java.awt.Dimension(1144, 429));
        PaneClientes.setLayout(new java.awt.CardLayout());

        CadastrarClientes.setForeground(new java.awt.Color(240, 240, 240));
        CadastrarClientes.setDoubleBuffered(false);
        CadastrarClientes.setPreferredSize(new java.awt.Dimension(1144, 677));
        CadastrarClientes.setRequestFocusEnabled(false);
        CadastrarClientes.setVerifyInputWhenFocusTarget(false);

        jSeparator14.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator14.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator21.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator21.setForeground(new java.awt.Color(0, 0, 0));

        FieldCadastroTelefoneCliente.setBackground(new java.awt.Color(240, 240, 240));
        FieldCadastroTelefoneCliente.setBorder(null);
        try {
            FieldCadastroTelefoneCliente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        FieldCadastroTelefoneCliente.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldCadastroTelefoneCliente.setPreferredSize(new java.awt.Dimension(49, 25));

        FieldCadastroCPFCliente.setBackground(new java.awt.Color(240, 240, 240));
        FieldCadastroCPFCliente.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldCadastroCPFCliente.setBorder(null);
        FieldCadastroCPFCliente.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldCadastroCPFCliente.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldCadastroCPFCliente.setDocument(new JTextFieldLimit(11, true, true));
        FieldCadastroCPFCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldCadastroCPFClienteMouseClicked(evt);
            }
        });
        FieldCadastroCPFCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldCadastroCPFClienteActionPerformed(evt);
            }
        });
        FieldCadastroCPFCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldCadastroCPFClienteKeyPressed(evt);
            }
        });

        BotaoCancelarCadastroCliente.setBackground(new java.awt.Color(230, 230, 230));
        BotaoCancelarCadastroCliente.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoCancelarCadastroCliente.setText("Cancelar");
        BotaoCancelarCadastroCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoCancelarCadastroCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoCancelarCadastroClienteActionPerformed(evt);
            }
        });

        lblNome3.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome3.setText("Nome do cliente*");

        lblNome6.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome6.setText("Endereço");

        FieldCadastroNomeCliente.setBackground(new java.awt.Color(240, 240, 240));
        FieldCadastroNomeCliente.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldCadastroNomeCliente.setBorder(null);
        FieldCadastroNomeCliente.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldCadastroNomeCliente.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldCadastroNomeCliente.setDocument(new JTextFieldLimit(255, true, false));
        FieldCadastroNomeCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldCadastroNomeClienteMouseClicked(evt);
            }
        });
        FieldCadastroNomeCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldCadastroNomeClienteActionPerformed(evt);
            }
        });
        FieldCadastroNomeCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldCadastroNomeClienteKeyPressed(evt);
            }
        });

        FieldCadastroEnderecoCliente.setBackground(new java.awt.Color(240, 240, 240));
        FieldCadastroEnderecoCliente.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldCadastroEnderecoCliente.setBorder(null);
        FieldCadastroEnderecoCliente.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldCadastroEnderecoCliente.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldCadastroEnderecoCliente.setDocument(new JTextFieldLimit(255, true, false));
        FieldCadastroEnderecoCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldCadastroEnderecoClienteMouseClicked(evt);
            }
        });
        FieldCadastroEnderecoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldCadastroEnderecoClienteActionPerformed(evt);
            }
        });
        FieldCadastroEnderecoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldCadastroEnderecoClienteKeyPressed(evt);
            }
        });

        jSeparator9.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator16.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator16.setForeground(new java.awt.Color(0, 0, 0));

        BotaoSalvarCadastroCliente.setBackground(new java.awt.Color(230, 230, 230));
        BotaoSalvarCadastroCliente.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoSalvarCadastroCliente.setText("Salvar");
        BotaoSalvarCadastroCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoSalvarCadastroCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoSalvarCadastroClienteActionPerformed(evt);
            }
        });

        lblNome8.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome8.setText("Email");

        jSeparator12.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator12.setForeground(new java.awt.Color(0, 0, 0));

        FieldCadastroEmailCliente.setBackground(new java.awt.Color(240, 240, 240));
        FieldCadastroEmailCliente.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldCadastroEmailCliente.setBorder(null);
        FieldCadastroEmailCliente.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldCadastroEmailCliente.setMinimumSize(new java.awt.Dimension(25, 25));
        //FieldCadastroEmailCliente.setDocument(new JTextFieldLimit(255, true, false));
        FieldCadastroEmailCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldCadastroEmailClienteMouseClicked(evt);
            }
        });
        FieldCadastroEmailCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldCadastroEmailClienteActionPerformed(evt);
            }
        });
        FieldCadastroEmailCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldCadastroEmailClienteKeyPressed(evt);
            }
        });

        lblNome4.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome4.setText("Celular*");

        jSeparator17.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator17.setForeground(new java.awt.Color(0, 0, 0));

        lblNome9.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome9.setText("Cidade");

        lblCPF4.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblCPF4.setText("CPF*");

        FieldCadastroCidadeCliente.setBackground(new java.awt.Color(240, 240, 240));
        FieldCadastroCidadeCliente.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldCadastroCidadeCliente.setBorder(null);
        FieldCadastroCidadeCliente.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldCadastroCidadeCliente.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldCadastroCidadeCliente.setDocument(new JTextFieldLimit(255, true, false));
        FieldCadastroCidadeCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldCadastroCidadeClienteMouseClicked(evt);
            }
        });
        FieldCadastroCidadeCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldCadastroCidadeClienteActionPerformed(evt);
            }
        });
        FieldCadastroCidadeCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldCadastroCidadeClienteKeyPressed(evt);
            }
        });

        jPanel2.setPreferredSize(new java.awt.Dimension(1144, 254));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(25, 25, 112));
        jLabel1.setText("CLIENTES");

        jPanel4.setBackground(new java.awt.Color(230, 230, 230));

        BotaoGerenciarCli.setBackground(new java.awt.Color(230, 230, 230));
        BotaoGerenciarCli.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoGerenciarCli.setForeground(new java.awt.Color(25, 25, 112));
        BotaoGerenciarCli.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoGerenciarCli.setText("Gerenciar");
        BotaoGerenciarCli.setOpaque(true);
        BotaoGerenciarCli.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoGerenciarCli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliMouseExited(evt);
            }
        });

        BotaoConsultarCli.setBackground(new java.awt.Color(230, 230, 230));
        BotaoConsultarCli.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoConsultarCli.setForeground(new java.awt.Color(25, 25, 112));
        BotaoConsultarCli.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoConsultarCli.setText("Consultar");
        BotaoConsultarCli.setOpaque(true);
        BotaoConsultarCli.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoConsultarCli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliMouseExited(evt);
            }
        });

        BotaoClientes2.setBackground(new java.awt.Color(220, 220, 220));
        BotaoClientes2.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoClientes2.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes2.setText("Cadastrar");
        BotaoClientes2.setOpaque(true);
        BotaoClientes2.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoClientes2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(BotaoClientes2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoConsultarCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoGerenciarCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoGerenciarCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoConsultarCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(443, 443, 443))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BotaoNovoCadastroCliente.setBackground(new java.awt.Color(230, 230, 230));
        BotaoNovoCadastroCliente.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoNovoCadastroCliente.setText("Novo");
        BotaoNovoCadastroCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoNovoCadastroCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoNovoCadastroClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CadastrarClientesLayout = new javax.swing.GroupLayout(CadastrarClientes);
        CadastrarClientes.setLayout(CadastrarClientesLayout);
        CadastrarClientesLayout.setHorizontalGroup(
            CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(CadastrarClientesLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastrarClientesLayout.createSequentialGroup()
                        .addComponent(BotaoNovoCadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BotaoSalvarCadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BotaoCancelarCadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CadastrarClientesLayout.createSequentialGroup()
                        .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNome9)
                            .addComponent(FieldCadastroCidadeCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator21, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNome6)
                            .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(CadastrarClientesLayout.createSequentialGroup()
                                    .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jSeparator14)
                                        .addComponent(FieldCadastroCPFCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblCPF4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(26, 26, 26)
                                    .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(FieldCadastroTelefoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNome4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(FieldCadastroEnderecoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(CadastrarClientesLayout.createSequentialGroup()
                        .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblNome8)
                                .addComponent(FieldCadastroEmailCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblNome3)
                                .addComponent(FieldCadastroNomeCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 647, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        CadastrarClientesLayout.setVerticalGroup(
            CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarClientesLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CadastrarClientesLayout.createSequentialGroup()
                        .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCPF4)
                            .addComponent(lblNome4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FieldCadastroCPFCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FieldCadastroTelefoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CadastrarClientesLayout.createSequentialGroup()
                        .addComponent(lblNome3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldCadastroNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastrarClientesLayout.createSequentialGroup()
                        .addComponent(lblNome6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldCadastroEnderecoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(CadastrarClientesLayout.createSequentialGroup()
                        .addComponent(lblNome9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldCadastroCidadeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator21, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addComponent(lblNome8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(FieldCadastroEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastrarClientesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BotaoSalvarCadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BotaoCancelarCadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(CadastrarClientesLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(BotaoNovoCadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(84, Short.MAX_VALUE))
        );

        PaneClientes.add(CadastrarClientes, "card3");

        ConsultarClientes.setPreferredSize(new java.awt.Dimension(1144, 429));

        lblNome2.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome2.setText("Nome do cliente");

        FieldConsultaNomeCliente.setBackground(new java.awt.Color(240, 240, 240));
        FieldConsultaNomeCliente.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldConsultaNomeCliente.setBorder(null);
        FieldConsultaNomeCliente.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldConsultaNomeCliente.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldConsultaNomeCliente.setDocument(new JTextFieldLimit(255, true, false));
        FieldConsultaNomeCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldConsultaNomeClienteMouseClicked(evt);
            }
        });
        FieldConsultaNomeCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldConsultaNomeClienteActionPerformed(evt);
            }
        });
        FieldConsultaNomeCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldConsultaNomeClienteKeyPressed(evt);
            }
        });

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));

        lblCPF2.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblCPF2.setText("CPF");

        TableConsultaCliente.setAutoCreateRowSorter(true);
        TableConsultaCliente.setBackground(new java.awt.Color(240, 240, 240));
        TableConsultaCliente.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        TableConsultaCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Nome", "CPF", "Telefone"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TableConsultaCliente.setFocusable(false);
        TableConsultaCliente.setGridColor(new java.awt.Color(204, 204, 204));
        TableConsultaCliente.setSelectionBackground(new java.awt.Color(230, 230, 230));
        TableConsultaCliente.setShowHorizontalLines(false);
        jScrollPane2.setViewportView(TableConsultaCliente);

        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));

        BotaoBuscarConsultaCliente.setBackground(new java.awt.Color(230, 230, 230));
        BotaoBuscarConsultaCliente.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoBuscarConsultaCliente.setText("Buscar");
        BotaoBuscarConsultaCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoBuscarConsultaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoBuscarConsultaClienteActionPerformed(evt);
            }
        });

        FieldConsultaCPFCliente.setBackground(new java.awt.Color(240, 240, 240));
        FieldConsultaCPFCliente.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldConsultaCPFCliente.setBorder(null);
        FieldConsultaCPFCliente.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldConsultaCPFCliente.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldConsultaCPFCliente.setDocument(new JTextFieldLimit(11, true, true));
        FieldConsultaCPFCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldConsultaCPFClienteMouseClicked(evt);
            }
        });
        FieldConsultaCPFCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldConsultaCPFClienteActionPerformed(evt);
            }
        });
        FieldConsultaCPFCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldConsultaCPFClienteKeyPressed(evt);
            }
        });

        jPanel22.setPreferredSize(new java.awt.Dimension(1144, 254));

        jLabel13.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(25, 25, 112));
        jLabel13.setText("CLIENTES");

        jPanel23.setBackground(new java.awt.Color(230, 230, 230));

        BotaoGerenciarCliConsul.setBackground(new java.awt.Color(230, 230, 230));
        BotaoGerenciarCliConsul.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoGerenciarCliConsul.setForeground(new java.awt.Color(25, 25, 112));
        BotaoGerenciarCliConsul.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoGerenciarCliConsul.setText("Gerenciar");
        BotaoGerenciarCliConsul.setOpaque(true);
        BotaoGerenciarCliConsul.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoGerenciarCliConsul.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliConsulMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliConsulMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliConsulMouseExited(evt);
            }
        });

        BotaoConsultarCliConsul.setBackground(new java.awt.Color(220, 220, 220));
        BotaoConsultarCliConsul.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoConsultarCliConsul.setForeground(new java.awt.Color(25, 25, 112));
        BotaoConsultarCliConsul.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoConsultarCliConsul.setText("Consultar");
        BotaoConsultarCliConsul.setOpaque(true);
        BotaoConsultarCliConsul.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoConsultarCliConsul.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsulMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsulMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsulMouseExited(evt);
            }
        });

        BotaoCadastrarCliConsul.setBackground(new java.awt.Color(230, 230, 230));
        BotaoCadastrarCliConsul.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoCadastrarCliConsul.setForeground(new java.awt.Color(25, 25, 112));
        BotaoCadastrarCliConsul.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoCadastrarCliConsul.setText("Cadastrar");
        BotaoCadastrarCliConsul.setOpaque(true);
        BotaoCadastrarCliConsul.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoCadastrarCliConsul.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsulMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsulMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsulMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(BotaoCadastrarCliConsul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoConsultarCliConsul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoGerenciarCliConsul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoGerenciarCliConsul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoConsultarCliConsul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoCadastrarCliConsul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addGap(443, 443, 443))
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout ConsultarClientesLayout = new javax.swing.GroupLayout(ConsultarClientes);
        ConsultarClientes.setLayout(ConsultarClientesLayout);
        ConsultarClientesLayout.setHorizontalGroup(
            ConsultarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConsultarClientesLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(ConsultarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1027, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ConsultarClientesLayout.createSequentialGroup()
                        .addGroup(ConsultarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNome2)
                            .addComponent(FieldConsultaNomeCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                            .addComponent(jSeparator7))
                        .addGap(18, 18, 18)
                        .addGroup(ConsultarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCPF2)
                            .addComponent(FieldConsultaCPFCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BotaoBuscarConsultaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(67, Short.MAX_VALUE))
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ConsultarClientesLayout.setVerticalGroup(
            ConsultarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConsultarClientesLayout.createSequentialGroup()
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(ConsultarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(ConsultarClientesLayout.createSequentialGroup()
                        .addComponent(lblNome2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldConsultaNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ConsultarClientesLayout.createSequentialGroup()
                        .addGroup(ConsultarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(ConsultarClientesLayout.createSequentialGroup()
                                .addComponent(lblCPF2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(FieldConsultaCPFCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(BotaoBuscarConsultaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 36, Short.MAX_VALUE))
        );

        PaneClientes.add(ConsultarClientes, "card4");

        lblNome10.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome10.setText("Nome do cliente");

        TableGerenciarCliente.setAutoCreateRowSorter(true);
        TableGerenciarCliente.setBackground(new java.awt.Color(240, 240, 240));
        TableGerenciarCliente.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        TableGerenciarCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Nome", "CPF", "Telefone"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TableGerenciarCliente.setFocusable(false);
        TableGerenciarCliente.setGridColor(new java.awt.Color(204, 204, 204));
        TableGerenciarCliente.setSelectionBackground(new java.awt.Color(230, 230, 230));
        TableGerenciarCliente.setShowHorizontalLines(false);
        TableGerenciarCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableGerenciarClienteMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TableGerenciarCliente);

        DadosAlteraClientes.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do cliente"));

        BotaoCancelarGerenciarServicos2.setBackground(new java.awt.Color(230, 230, 230));
        BotaoCancelarGerenciarServicos2.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoCancelarGerenciarServicos2.setText("Cancelar");
        BotaoCancelarGerenciarServicos2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoCancelarGerenciarServicos2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoCancelarGerenciarServicos2MouseClicked(evt);
            }
        });
        BotaoCancelarGerenciarServicos2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoCancelarGerenciarServicos2ActionPerformed(evt);
            }
        });

        BotaoExcluirGerenciarGarantia1.setBackground(new java.awt.Color(230, 230, 230));
        BotaoExcluirGerenciarGarantia1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoExcluirGerenciarGarantia1.setText("Excluir");
        BotaoExcluirGerenciarGarantia1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoExcluirGerenciarGarantia1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoExcluirGerenciarGarantia1MouseClicked(evt);
            }
        });
        BotaoExcluirGerenciarGarantia1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoExcluirGerenciarGarantia1ActionPerformed(evt);
            }
        });

        BotaoAlterarGerenciarGarantia1.setBackground(new java.awt.Color(230, 230, 230));
        BotaoAlterarGerenciarGarantia1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoAlterarGerenciarGarantia1.setText("Alterar");
        BotaoAlterarGerenciarGarantia1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoAlterarGerenciarGarantia1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoAlterarGerenciarGarantia1MouseClicked(evt);
            }
        });
        BotaoAlterarGerenciarGarantia1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoAlterarGerenciarGarantia1ActionPerformed(evt);
            }
        });

        lblNome19.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome19.setText("Nome do cliente*");

        FieldNomeGerenciarClientes.setBackground(new java.awt.Color(240, 240, 240));
        FieldNomeGerenciarClientes.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldNomeGerenciarClientes.setBorder(null);
        FieldNomeGerenciarClientes.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldNomeGerenciarClientes.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldNomeGerenciarClientes.setDocument(new JTextFieldLimit(255, true, false));
        FieldNomeGerenciarClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldNomeGerenciarClientesMouseClicked(evt);
            }
        });
        FieldNomeGerenciarClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldNomeGerenciarClientesKeyPressed(evt);
            }
        });

        jSeparator27.setForeground(new java.awt.Color(0, 0, 0));

        FieldIDGerenciarClientes.setEditable(false);
        FieldIDGerenciarClientes.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldIDGerenciarClientes.setBorder(null);
        FieldIDGerenciarClientes.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldIDGerenciarClientes.setMinimumSize(new java.awt.Dimension(25, 25));
        CampoNome.setDocument(new JTextFieldLimit(40, true));
        FieldIDGerenciarClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldIDGerenciarClientesMouseClicked(evt);
            }
        });
        FieldIDGerenciarClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldIDGerenciarClientesKeyPressed(evt);
            }
        });

        lblCPF5.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblCPF5.setText("CPF*");

        FieldCPFGerenciarClientes.setBackground(new java.awt.Color(240, 240, 240));
        FieldCPFGerenciarClientes.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldCPFGerenciarClientes.setBorder(null);
        FieldCPFGerenciarClientes.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldCPFGerenciarClientes.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldCPFGerenciarClientes.setDocument(new JTextFieldLimit(11, true, true));
        FieldCPFGerenciarClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldCPFGerenciarClientesMouseClicked(evt);
            }
        });
        FieldCPFGerenciarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldCPFGerenciarClientesActionPerformed(evt);
            }
        });
        FieldCPFGerenciarClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldCPFGerenciarClientesKeyPressed(evt);
            }
        });

        jSeparator19.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator28.setForeground(new java.awt.Color(0, 0, 0));

        FieldTelefoneGerenciarClientes.setBackground(new java.awt.Color(240, 240, 240));
        FieldTelefoneGerenciarClientes.setBorder(null);
        try {
            FieldTelefoneGerenciarClientes.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        FieldTelefoneGerenciarClientes.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldTelefoneGerenciarClientes.setPreferredSize(new java.awt.Dimension(49, 25));

        lblNome11.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome11.setText("Celular*");

        lblNome22.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome22.setText("Cidade");

        FieldCidadeGerenciarClientes.setBackground(new java.awt.Color(240, 240, 240));
        FieldCidadeGerenciarClientes.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldCidadeGerenciarClientes.setBorder(null);
        FieldCidadeGerenciarClientes.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldCidadeGerenciarClientes.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldCidadeGerenciarClientes.setDocument(new JTextFieldLimit(255, true, false));
        FieldCidadeGerenciarClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldCidadeGerenciarClientesMouseClicked(evt);
            }
        });
        FieldCidadeGerenciarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldCidadeGerenciarClientesActionPerformed(evt);
            }
        });
        FieldCidadeGerenciarClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldCidadeGerenciarClientesKeyPressed(evt);
            }
        });

        jSeparator29.setForeground(new java.awt.Color(0, 0, 0));

        lblNome23.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome23.setText("Endereço");

        FieldEnderecoGerenciarClientes.setBackground(new java.awt.Color(240, 240, 240));
        FieldEnderecoGerenciarClientes.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldEnderecoGerenciarClientes.setBorder(null);
        FieldEnderecoGerenciarClientes.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldEnderecoGerenciarClientes.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldEnderecoGerenciarClientes.setDocument(new JTextFieldLimit(255, true, false));
        FieldEnderecoGerenciarClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldEnderecoGerenciarClientesMouseClicked(evt);
            }
        });
        FieldEnderecoGerenciarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldEnderecoGerenciarClientesActionPerformed(evt);
            }
        });
        FieldEnderecoGerenciarClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldEnderecoGerenciarClientesKeyPressed(evt);
            }
        });

        jSeparator30.setForeground(new java.awt.Color(0, 0, 0));

        lblNome24.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome24.setText("Email");

        FieldEmailGerenciarClientes.setBackground(new java.awt.Color(240, 240, 240));
        FieldEmailGerenciarClientes.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldEmailGerenciarClientes.setBorder(null);
        FieldEmailGerenciarClientes.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldEmailGerenciarClientes.setMinimumSize(new java.awt.Dimension(25, 25));
        //FieldEmailGerenciarClientes.setDocument(new JTextFieldLimit(255, false));
        FieldEmailGerenciarClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldEmailGerenciarClientesMouseClicked(evt);
            }
        });
        FieldEmailGerenciarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldEmailGerenciarClientesActionPerformed(evt);
            }
        });
        FieldEmailGerenciarClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldEmailGerenciarClientesKeyPressed(evt);
            }
        });

        jSeparator31.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout DadosAlteraClientesLayout = new javax.swing.GroupLayout(DadosAlteraClientes);
        DadosAlteraClientes.setLayout(DadosAlteraClientesLayout);
        DadosAlteraClientesLayout.setHorizontalGroup(
            DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DadosAlteraClientesLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(DadosAlteraClientesLayout.createSequentialGroup()
                        .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(FieldEmailGerenciarClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                            .addComponent(jSeparator31, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                            .addComponent(FieldCidadeGerenciarClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(DadosAlteraClientesLayout.createSequentialGroup()
                                .addComponent(BotaoAlterarGerenciarGarantia1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BotaoExcluirGerenciarGarantia1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BotaoCancelarGerenciarServicos2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblNome23, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator30, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblNome24)
                        .addGroup(DadosAlteraClientesLayout.createSequentialGroup()
                            .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(DadosAlteraClientesLayout.createSequentialGroup()
                                        .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jSeparator27, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(FieldNomeGerenciarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(lblNome19))
                                        .addGap(18, 18, 18)
                                        .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jSeparator19)
                                                .addComponent(FieldCPFGerenciarClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                                            .addComponent(lblCPF5)))
                                    .addComponent(FieldEnderecoGerenciarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(lblNome22))
                            .addGap(18, 18, 18)
                            .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblNome11)
                                .addComponent(jSeparator28, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(DadosAlteraClientesLayout.createSequentialGroup()
                                    .addGap(28, 28, 28)
                                    .addComponent(FieldIDGerenciarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(FieldTelefoneGerenciarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(55, 55, 55))
        );
        DadosAlteraClientesLayout.setVerticalGroup(
            DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DadosAlteraClientesLayout.createSequentialGroup()
                .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNome19, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblNome11)
                        .addComponent(lblCPF5)))
                .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(DadosAlteraClientesLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jSeparator28, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DadosAlteraClientesLayout.createSequentialGroup()
                        .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FieldCPFGerenciarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FieldNomeGerenciarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FieldTelefoneGerenciarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator19, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator27, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(11, 11, 11)
                .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DadosAlteraClientesLayout.createSequentialGroup()
                        .addComponent(lblNome22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldCidadeGerenciarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator29, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNome24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldEmailGerenciarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator31, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DadosAlteraClientesLayout.createSequentialGroup()
                        .addComponent(lblNome23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FieldEnderecoGerenciarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FieldIDGerenciarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator30, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BotaoCancelarGerenciarServicos2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BotaoExcluirGerenciarGarantia1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BotaoAlterarGerenciarGarantia1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        FieldGerenciarNomeCliente.setBackground(new java.awt.Color(240, 240, 240));
        FieldGerenciarNomeCliente.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldGerenciarNomeCliente.setBorder(null);
        FieldGerenciarNomeCliente.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldGerenciarNomeCliente.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldGerenciarNomeCliente.setDocument(new JTextFieldLimit(255, true, false));
        FieldGerenciarNomeCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldGerenciarNomeClienteMouseClicked(evt);
            }
        });
        FieldGerenciarNomeCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldGerenciarNomeClienteActionPerformed(evt);
            }
        });
        FieldGerenciarNomeCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldGerenciarNomeClienteKeyPressed(evt);
            }
        });

        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator32.setForeground(new java.awt.Color(0, 0, 0));

        FieldGerenciarCPFCliente.setBackground(new java.awt.Color(240, 240, 240));
        FieldGerenciarCPFCliente.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldGerenciarCPFCliente.setBorder(null);
        FieldGerenciarCPFCliente.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldGerenciarCPFCliente.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldGerenciarCPFCliente.setDocument(new JTextFieldLimit(11, true, true));
        FieldGerenciarCPFCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldGerenciarCPFClienteMouseClicked(evt);
            }
        });
        FieldGerenciarCPFCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldGerenciarCPFClienteActionPerformed(evt);
            }
        });
        FieldGerenciarCPFCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldGerenciarCPFClienteKeyPressed(evt);
            }
        });

        lblCPF6.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblCPF6.setText("CPF");

        BotaoBuscarConsultaCliente1.setBackground(new java.awt.Color(230, 230, 230));
        BotaoBuscarConsultaCliente1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoBuscarConsultaCliente1.setText("Buscar");
        BotaoBuscarConsultaCliente1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoBuscarConsultaCliente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoBuscarConsultaCliente1ActionPerformed(evt);
            }
        });

        jPanel24.setPreferredSize(new java.awt.Dimension(1144, 254));

        jLabel14.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(25, 25, 112));
        jLabel14.setText("CLIENTES");

        jPanel25.setBackground(new java.awt.Color(230, 230, 230));

        BotaoGerenciarCliGer.setBackground(new java.awt.Color(220, 220, 220));
        BotaoGerenciarCliGer.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoGerenciarCliGer.setForeground(new java.awt.Color(25, 25, 112));
        BotaoGerenciarCliGer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoGerenciarCliGer.setText("Gerenciar");
        BotaoGerenciarCliGer.setOpaque(true);
        BotaoGerenciarCliGer.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoGerenciarCliGer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliGerMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliGerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliGerMouseExited(evt);
            }
        });

        BotaoConsultarCliGer.setBackground(new java.awt.Color(230, 230, 230));
        BotaoConsultarCliGer.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoConsultarCliGer.setForeground(new java.awt.Color(25, 25, 112));
        BotaoConsultarCliGer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoConsultarCliGer.setText("Consultar");
        BotaoConsultarCliGer.setOpaque(true);
        BotaoConsultarCliGer.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoConsultarCliGer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliGerMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliGerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliGerMouseExited(evt);
            }
        });

        BotaoCadastrarCliGer.setBackground(new java.awt.Color(230, 230, 230));
        BotaoCadastrarCliGer.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoCadastrarCliGer.setForeground(new java.awt.Color(25, 25, 112));
        BotaoCadastrarCliGer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoCadastrarCliGer.setText("Cadastrar");
        BotaoCadastrarCliGer.setOpaque(true);
        BotaoCadastrarCliGer.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoCadastrarCliGer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliGerMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliGerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliGerMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(BotaoCadastrarCliGer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoConsultarCliGer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoGerenciarCliGer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoGerenciarCliGer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoConsultarCliGer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoCadastrarCliGer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(443, 443, 443))
            .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout GerenciarClientesLayout = new javax.swing.GroupLayout(GerenciarClientes);
        GerenciarClientes.setLayout(GerenciarClientesLayout);
        GerenciarClientesLayout.setHorizontalGroup(
            GerenciarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GerenciarClientesLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(GerenciarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5)
                    .addComponent(DadosAlteraClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(GerenciarClientesLayout.createSequentialGroup()
                        .addGroup(GerenciarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(GerenciarClientesLayout.createSequentialGroup()
                                .addGroup(GerenciarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(FieldGerenciarNomeCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(GerenciarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(FieldGerenciarCPFCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator32, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCPF6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblNome10))
                        .addGap(18, 18, 18)
                        .addComponent(BotaoBuscarConsultaCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(65, Short.MAX_VALUE))
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        GerenciarClientesLayout.setVerticalGroup(
            GerenciarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GerenciarClientesLayout.createSequentialGroup()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(GerenciarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(GerenciarClientesLayout.createSequentialGroup()
                        .addGroup(GerenciarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNome10, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCPF6, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(GerenciarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FieldGerenciarNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FieldGerenciarCPFCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(GerenciarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(GerenciarClientesLayout.createSequentialGroup()
                        .addComponent(BotaoBuscarConsultaCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)))
                .addGap(0, 0, 0)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DadosAlteraClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        DadosAlteraClientes.getAccessibleContext().setAccessibleName("\"Dados do cliente\"");

        PaneClientes.add(GerenciarClientes, "card5");

        PaneMae.add(PaneClientes, "card3");

        PaneGarantias.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        PaneGarantias.setPreferredSize(new java.awt.Dimension(1144, 429));
        PaneGarantias.setLayout(new java.awt.CardLayout());

        CadastrarGarantias.setForeground(new java.awt.Color(255, 255, 255));
        CadastrarGarantias.setDoubleBuffered(false);
        CadastrarGarantias.setPreferredSize(new java.awt.Dimension(1144, 429));
        CadastrarGarantias.setRequestFocusEnabled(false);
        CadastrarGarantias.setVerifyInputWhenFocusTarget(false);

        jSeparator2.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        CampoNome.setBackground(new java.awt.Color(240, 240, 240));
        CampoNome.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        CampoNome.setBorder(null);
        CampoNome.setMaximumSize(new java.awt.Dimension(25, 25));
        CampoNome.setMinimumSize(new java.awt.Dimension(25, 25));
        CampoNome.setDocument(new JTextFieldLimit(255, true, false));
        CampoNome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CampoNomeMouseClicked(evt);
            }
        });
        CampoNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CampoNomeActionPerformed(evt);
            }
        });
        CampoNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CampoNomeKeyPressed(evt);
            }
        });

        BotaoSalvarGarantia.setBackground(new java.awt.Color(230, 230, 230));
        BotaoSalvarGarantia.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoSalvarGarantia.setText("Salvar");
        BotaoSalvarGarantia.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoSalvarGarantia.setPreferredSize(new java.awt.Dimension(151, 50));
        BotaoSalvarGarantia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoSalvarGarantiaActionPerformed(evt);
            }
        });

        lblNome.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome.setText("Nome do cliente");

        lblConserto.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblConserto.setText("Conserto");

        BotaoCancelarGarantia.setBackground(new java.awt.Color(230, 230, 230));
        BotaoCancelarGarantia.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoCancelarGarantia.setText("Cancelar");
        BotaoCancelarGarantia.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoCancelarGarantia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoCancelarGarantiaActionPerformed(evt);
            }
        });

        ComboEscolhaConserto.setBackground(new java.awt.Color(240, 240, 240));
        ComboEscolhaConserto.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        ComboEscolhaConserto.setBorder(null);
        ComboEscolhaConserto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboEscolhaConsertoActionPerformed(evt);
            }
        });

        BotaoNovoGarantia.setBackground(new java.awt.Color(230, 230, 230));
        BotaoNovoGarantia.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoNovoGarantia.setText("Novo");
        BotaoNovoGarantia.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoNovoGarantia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoNovoGarantiaActionPerformed(evt);
            }
        });

        CampoDataFormatada.setBackground(new java.awt.Color(240, 240, 240));
        CampoDataFormatada.setBorder(null);
        try {
            CampoDataFormatada.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        CampoDataFormatada.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        CampoDataFormatada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CampoDataFormatadaActionPerformed(evt);
            }
        });

        jSeparator4.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));

        lblData.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblData.setText("Data do conserto");

        jPanel3.setPreferredSize(new java.awt.Dimension(1144, 254));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(25, 25, 112));
        jLabel2.setText("GARANTIAS");

        jPanel5.setBackground(new java.awt.Color(230, 230, 230));

        BotaoGerenciarCli1.setBackground(new java.awt.Color(230, 230, 230));
        BotaoGerenciarCli1.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoGerenciarCli1.setForeground(new java.awt.Color(25, 25, 112));
        BotaoGerenciarCli1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoGerenciarCli1.setText("Gerenciar");
        BotaoGerenciarCli1.setOpaque(true);
        BotaoGerenciarCli1.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoGerenciarCli1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli1MouseExited(evt);
            }
        });

        BotaoConsultarCli1.setBackground(new java.awt.Color(230, 230, 230));
        BotaoConsultarCli1.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoConsultarCli1.setForeground(new java.awt.Color(25, 25, 112));
        BotaoConsultarCli1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoConsultarCli1.setText("Consultar");
        BotaoConsultarCli1.setOpaque(true);
        BotaoConsultarCli1.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoConsultarCli1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoConsultarCli1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoConsultarCli1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoConsultarCli1MouseExited(evt);
            }
        });

        BotaoClientes3.setBackground(new java.awt.Color(220, 220, 220));
        BotaoClientes3.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoClientes3.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes3.setText("Cadastrar");
        BotaoClientes3.setOpaque(true);
        BotaoClientes3.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoClientes3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(BotaoClientes3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoConsultarCli1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoGerenciarCli1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoGerenciarCli1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoConsultarCli1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(443, 443, 443))
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout CadastrarGarantiasLayout = new javax.swing.GroupLayout(CadastrarGarantias);
        CadastrarGarantias.setLayout(CadastrarGarantiasLayout);
        CadastrarGarantiasLayout.setHorizontalGroup(
            CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(CadastrarGarantiasLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(CadastrarGarantiasLayout.createSequentialGroup()
                        .addComponent(BotaoNovoGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BotaoSalvarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BotaoCancelarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(CadastrarGarantiasLayout.createSequentialGroup()
                        .addGroup(CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator2)
                            .addComponent(CampoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNome))
                        .addGap(49, 49, 49)
                        .addGroup(CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CampoDataFormatada)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(61, 61, 61)
                        .addGroup(CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ComboEscolhaConserto, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblConserto, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        CadastrarGarantiasLayout.setVerticalGroup(
            CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarGarantiasLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(CadastrarGarantiasLayout.createSequentialGroup()
                        .addGroup(CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblData, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblConserto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CadastrarGarantiasLayout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(CadastrarGarantiasLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(ComboEscolhaConserto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(CadastrarGarantiasLayout.createSequentialGroup()
                        .addComponent(lblNome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CampoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CampoDataFormatada, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotaoSalvarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoCancelarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoNovoGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46))
        );

        PaneGarantias.add(CadastrarGarantias, "card3");

        ConsultarGarantias.setPreferredSize(new java.awt.Dimension(1144, 429));

        lblNome1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome1.setText("Nome do cliente");

        FieldConsultaNomeGarantia.setBackground(new java.awt.Color(240, 240, 240));
        FieldConsultaNomeGarantia.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldConsultaNomeGarantia.setBorder(null);
        FieldConsultaNomeGarantia.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldConsultaNomeGarantia.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldConsultaNomeGarantia.setPreferredSize(new java.awt.Dimension(1, 25));
        FieldConsultaNomeGarantia.setDocument(new JTextFieldLimit(255, true, false));
        FieldConsultaNomeGarantia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldConsultaNomeGarantiaMouseClicked(evt);
            }
        });
        FieldConsultaNomeGarantia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldConsultaNomeGarantiaActionPerformed(evt);
            }
        });
        FieldConsultaNomeGarantia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldConsultaNomeGarantiaKeyPressed(evt);
            }
        });

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

        lblCPF1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblCPF1.setText("Filtrar por:");

        TableConsultaGarantia.setAutoCreateRowSorter(true);
        TableConsultaGarantia.setBackground(new java.awt.Color(240, 240, 240));
        TableConsultaGarantia.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        TableConsultaGarantia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Nome", "CPF", "Telefone", "Conserto", "Data do conserto", "Tempo de garantia"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TableConsultaGarantia.setFocusable(false);
        TableConsultaGarantia.setGridColor(new java.awt.Color(204, 204, 204));
        TableConsultaGarantia.setSelectionBackground(new java.awt.Color(230, 230, 230));
        TableConsultaGarantia.setShowHorizontalLines(false);
        jScrollPane1.setViewportView(TableConsultaGarantia);

        BotaoBuscarConsultarGarantias.setBackground(new java.awt.Color(230, 230, 230));
        BotaoBuscarConsultarGarantias.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoBuscarConsultarGarantias.setText("Buscar");
        BotaoBuscarConsultarGarantias.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoBuscarConsultarGarantias.setPreferredSize(new java.awt.Dimension(151, 50));
        BotaoBuscarConsultarGarantias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoBuscarConsultarGarantiasActionPerformed(evt);
            }
        });

        ComboOrdenaGarantia.setBackground(new java.awt.Color(240, 240, 240));
        ComboOrdenaGarantia.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        ComboOrdenaGarantia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODAS", "VIGENTES", "VENCIDAS" }));
        ComboOrdenaGarantia.setBorder(null);

        BotaoBuscarConsultarGarantias1.setBackground(new java.awt.Color(230, 230, 230));
        BotaoBuscarConsultarGarantias1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoBuscarConsultarGarantias1.setText("Contatar cliente");
        BotaoBuscarConsultarGarantias1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoBuscarConsultarGarantias1.setPreferredSize(new java.awt.Dimension(151, 50));
        BotaoBuscarConsultarGarantias1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoBuscarConsultarGarantias1ActionPerformed(evt);
            }
        });

        jPanel28.setPreferredSize(new java.awt.Dimension(1144, 254));

        jLabel16.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(25, 25, 112));
        jLabel16.setText("GARANTIAS");

        jPanel29.setBackground(new java.awt.Color(230, 230, 230));

        BotaoGerenciarCliConsul1.setBackground(new java.awt.Color(230, 230, 230));
        BotaoGerenciarCliConsul1.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoGerenciarCliConsul1.setForeground(new java.awt.Color(25, 25, 112));
        BotaoGerenciarCliConsul1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoGerenciarCliConsul1.setText("Gerenciar");
        BotaoGerenciarCliConsul1.setOpaque(true);
        BotaoGerenciarCliConsul1.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoGerenciarCliConsul1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliConsul1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliConsul1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliConsul1MouseExited(evt);
            }
        });

        BotaoConsultarCliConsul1.setBackground(new java.awt.Color(220, 220, 220));
        BotaoConsultarCliConsul1.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoConsultarCliConsul1.setForeground(new java.awt.Color(25, 25, 112));
        BotaoConsultarCliConsul1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoConsultarCliConsul1.setText("Consultar");
        BotaoConsultarCliConsul1.setOpaque(true);
        BotaoConsultarCliConsul1.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoConsultarCliConsul1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul1MouseExited(evt);
            }
        });

        BotaoCadastrarCliConsul1.setBackground(new java.awt.Color(230, 230, 230));
        BotaoCadastrarCliConsul1.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoCadastrarCliConsul1.setForeground(new java.awt.Color(25, 25, 112));
        BotaoCadastrarCliConsul1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoCadastrarCliConsul1.setText("Cadastrar");
        BotaoCadastrarCliConsul1.setOpaque(true);
        BotaoCadastrarCliConsul1.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoCadastrarCliConsul1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul1MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(BotaoCadastrarCliConsul1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoConsultarCliConsul1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoGerenciarCliConsul1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoGerenciarCliConsul1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoConsultarCliConsul1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoCadastrarCliConsul1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addGap(443, 443, 443))
            .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout ConsultarGarantiasLayout = new javax.swing.GroupLayout(ConsultarGarantias);
        ConsultarGarantias.setLayout(ConsultarGarantiasLayout);
        ConsultarGarantiasLayout.setHorizontalGroup(
            ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                        .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FieldConsultaNomeGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNome1))
                        .addGap(53, 53, 53)
                        .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ComboOrdenaGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCPF1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(53, 53, 53)
                        .addComponent(BotaoBuscarConsultarGarantias, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1061, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoBuscarConsultarGarantias1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
            .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ConsultarGarantiasLayout.setVerticalGroup(
            ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BotaoBuscarConsultarGarantias, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                        .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNome1)
                            .addComponent(lblCPF1))
                        .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(ComboOrdenaGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ConsultarGarantiasLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FieldConsultaNomeGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(1, 1, 1)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BotaoBuscarConsultarGarantias1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );

        PaneGarantias.add(ConsultarGarantias, "card4");

        GerenciarGarantias.setPreferredSize(new java.awt.Dimension(1190, 451));

        jSeparator25.setForeground(new java.awt.Color(0, 0, 0));

        FieldNomeGerenciarServicos1.setBackground(new java.awt.Color(240, 240, 240));
        FieldNomeGerenciarServicos1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldNomeGerenciarServicos1.setBorder(null);
        FieldNomeGerenciarServicos1.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldNomeGerenciarServicos1.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldNomeGerenciarServicos1.setDocument(new JTextFieldLimit(255, true, false));
        FieldNomeGerenciarServicos1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldNomeGerenciarServicos1MouseClicked(evt);
            }
        });
        FieldNomeGerenciarServicos1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldNomeGerenciarServicos1KeyPressed(evt);
            }
        });

        lblNome17.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome17.setText("Nome do cliente");

        BotaoBuscarGerenciarServicos1.setBackground(new java.awt.Color(230, 230, 230));
        BotaoBuscarGerenciarServicos1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoBuscarGerenciarServicos1.setText("Buscar");
        BotaoBuscarGerenciarServicos1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoBuscarGerenciarServicos1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoBuscarGerenciarServicos1MouseClicked(evt);
            }
        });
        BotaoBuscarGerenciarServicos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoBuscarGerenciarServicos1ActionPerformed(evt);
            }
        });

        TableGerenciarGarantia.setAutoCreateRowSorter(true);
        TableGerenciarGarantia.setBackground(new java.awt.Color(240, 240, 240));
        TableGerenciarGarantia.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        TableGerenciarGarantia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Conserto", "Tempo de garantia"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TableGerenciarGarantia.setFocusable(false);
        TableGerenciarGarantia.setGridColor(new java.awt.Color(204, 204, 204));
        TableGerenciarGarantia.setSelectionBackground(new java.awt.Color(230, 230, 230));
        TableGerenciarGarantia.setShowHorizontalLines(false);
        TableGerenciarGarantia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableGerenciarGarantiaMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(TableGerenciarGarantia);

        DadosAlteraGarantia.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do conserto"));

        BotaoCancelarGerenciarGarantia.setBackground(new java.awt.Color(230, 230, 230));
        BotaoCancelarGerenciarGarantia.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoCancelarGerenciarGarantia.setText("Cancelar");
        BotaoCancelarGerenciarGarantia.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoCancelarGerenciarGarantia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoCancelarGerenciarGarantiaMouseClicked(evt);
            }
        });
        BotaoCancelarGerenciarGarantia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoCancelarGerenciarGarantiaActionPerformed(evt);
            }
        });

        BotaoExcluirGerenciarGarantia.setBackground(new java.awt.Color(230, 230, 230));
        BotaoExcluirGerenciarGarantia.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoExcluirGerenciarGarantia.setText("Excluir");
        BotaoExcluirGerenciarGarantia.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoExcluirGerenciarGarantia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoExcluirGerenciarGarantiaMouseClicked(evt);
            }
        });
        BotaoExcluirGerenciarGarantia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoExcluirGerenciarGarantiaActionPerformed(evt);
            }
        });

        BotaoAlterarGerenciarGarantia.setBackground(new java.awt.Color(230, 230, 230));
        BotaoAlterarGerenciarGarantia.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoAlterarGerenciarGarantia.setText("Alterar");
        BotaoAlterarGerenciarGarantia.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoAlterarGerenciarGarantia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoAlterarGerenciarGarantiaMouseClicked(evt);
            }
        });
        BotaoAlterarGerenciarGarantia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoAlterarGerenciarGarantiaActionPerformed(evt);
            }
        });

        lblNome18.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome18.setText("Nome do cliente");

        FieldNomeGerenciarGarantia.setBackground(new java.awt.Color(240, 240, 240));
        FieldNomeGerenciarGarantia.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldNomeGerenciarGarantia.setBorder(null);
        FieldNomeGerenciarGarantia.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldNomeGerenciarGarantia.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldNomeGerenciarGarantia.setDocument(new JTextFieldLimit(255, true, false));
        FieldNomeGerenciarGarantia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldNomeGerenciarGarantiaMouseClicked(evt);
            }
        });
        FieldNomeGerenciarGarantia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldNomeGerenciarGarantiaKeyPressed(evt);
            }
        });

        jSeparator26.setForeground(new java.awt.Color(0, 0, 0));

        FieldIDGerenciarGarantia.setBackground(new java.awt.Color(255, 0, 255));
        FieldIDGerenciarGarantia.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldIDGerenciarGarantia.setBorder(null);
        FieldIDGerenciarGarantia.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldIDGerenciarGarantia.setMinimumSize(new java.awt.Dimension(25, 25));
        CampoNome.setDocument(new JTextFieldLimit(40, true));
        FieldIDGerenciarGarantia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldIDGerenciarGarantiaMouseClicked(evt);
            }
        });
        FieldIDGerenciarGarantia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldIDGerenciarGarantiaKeyPressed(evt);
            }
        });

        lblData1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblData1.setText("Data do conserto");

        CampoGerenciaDataFormatada.setBackground(new java.awt.Color(240, 240, 240));
        CampoGerenciaDataFormatada.setBorder(null);
        try {
            CampoGerenciaDataFormatada.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        CampoGerenciaDataFormatada.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        CampoGerenciaDataFormatada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CampoGerenciaDataFormatadaActionPerformed(evt);
            }
        });

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));

        lblConserto1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblConserto1.setText("Conserto");

        ComboGerenciaEscolhaConserto.setBackground(new java.awt.Color(240, 240, 240));
        ComboGerenciaEscolhaConserto.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        ComboGerenciaEscolhaConserto.setBorder(null);
        ComboGerenciaEscolhaConserto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboGerenciaEscolhaConsertoActionPerformed(evt);
            }
        });

        Botao2Via.setBackground(new java.awt.Color(230, 230, 230));
        Botao2Via.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        Botao2Via.setText("2° Via");
        Botao2Via.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Botao2Via.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Botao2ViaMouseClicked(evt);
            }
        });
        Botao2Via.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Botao2ViaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DadosAlteraGarantiaLayout = new javax.swing.GroupLayout(DadosAlteraGarantia);
        DadosAlteraGarantia.setLayout(DadosAlteraGarantiaLayout);
        DadosAlteraGarantiaLayout.setHorizontalGroup(
            DadosAlteraGarantiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DadosAlteraGarantiaLayout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addGroup(DadosAlteraGarantiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(DadosAlteraGarantiaLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(FieldIDGerenciarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 184, Short.MAX_VALUE)
                        .addComponent(Botao2Via, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BotaoAlterarGerenciarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BotaoExcluirGerenciarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BotaoCancelarGerenciarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblConserto1)
                    .addComponent(ComboGerenciaEscolhaConserto, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(DadosAlteraGarantiaLayout.createSequentialGroup()
                        .addGroup(DadosAlteraGarantiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNome18)
                            .addComponent(FieldNomeGerenciarGarantia, javax.swing.GroupLayout.DEFAULT_SIZE, 736, Short.MAX_VALUE)
                            .addComponent(jSeparator26))
                        .addGap(34, 34, 34)
                        .addGroup(DadosAlteraGarantiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblData1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CampoGerenciaDataFormatada, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.LEADING))))
                .addGap(55, 55, 55))
        );
        DadosAlteraGarantiaLayout.setVerticalGroup(
            DadosAlteraGarantiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DadosAlteraGarantiaLayout.createSequentialGroup()
                .addGroup(DadosAlteraGarantiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(DadosAlteraGarantiaLayout.createSequentialGroup()
                        .addComponent(lblNome18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldNomeGerenciarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator26, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DadosAlteraGarantiaLayout.createSequentialGroup()
                        .addComponent(lblData1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CampoGerenciaDataFormatada, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addComponent(lblConserto1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ComboGerenciaEscolhaConserto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(DadosAlteraGarantiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FieldIDGerenciarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoCancelarGerenciarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoExcluirGerenciarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoAlterarGerenciarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Botao2Via, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblCPF3.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblCPF3.setText("Filtrar por:");

        ComboOrdenaGarantia1.setBackground(new java.awt.Color(240, 240, 240));
        ComboOrdenaGarantia1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        ComboOrdenaGarantia1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODAS", "VIGENTES", "VENCIDAS" }));
        ComboOrdenaGarantia1.setBorder(null);

        jPanel26.setPreferredSize(new java.awt.Dimension(1144, 254));

        jLabel15.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(25, 25, 112));
        jLabel15.setText("GARANTIAS");

        jPanel27.setBackground(new java.awt.Color(230, 230, 230));

        BotaoGerenciarCliGer1.setBackground(new java.awt.Color(220, 220, 220));
        BotaoGerenciarCliGer1.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoGerenciarCliGer1.setForeground(new java.awt.Color(25, 25, 112));
        BotaoGerenciarCliGer1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoGerenciarCliGer1.setText("Gerenciar");
        BotaoGerenciarCliGer1.setOpaque(true);
        BotaoGerenciarCliGer1.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoGerenciarCliGer1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliGer1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliGer1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliGer1MouseExited(evt);
            }
        });

        BotaoConsultarCliGer1.setBackground(new java.awt.Color(230, 230, 230));
        BotaoConsultarCliGer1.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoConsultarCliGer1.setForeground(new java.awt.Color(25, 25, 112));
        BotaoConsultarCliGer1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoConsultarCliGer1.setText("Consultar");
        BotaoConsultarCliGer1.setOpaque(true);
        BotaoConsultarCliGer1.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoConsultarCliGer1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliGer1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliGer1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliGer1MouseExited(evt);
            }
        });

        BotaoCadastrarCliGer1.setBackground(new java.awt.Color(230, 230, 230));
        BotaoCadastrarCliGer1.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoCadastrarCliGer1.setForeground(new java.awt.Color(25, 25, 112));
        BotaoCadastrarCliGer1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoCadastrarCliGer1.setText("Cadastrar");
        BotaoCadastrarCliGer1.setOpaque(true);
        BotaoCadastrarCliGer1.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoCadastrarCliGer1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliGer1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliGer1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliGer1MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(BotaoCadastrarCliGer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoConsultarCliGer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoGerenciarCliGer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(740, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoGerenciarCliGer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoConsultarCliGer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoCadastrarCliGer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(379, 379, 379)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout GerenciarGarantiasLayout = new javax.swing.GroupLayout(GerenciarGarantias);
        GerenciarGarantias.setLayout(GerenciarGarantiasLayout);
        GerenciarGarantiasLayout.setHorizontalGroup(
            GerenciarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GerenciarGarantiasLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(GerenciarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(GerenciarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(DadosAlteraGarantia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(GerenciarGarantiasLayout.createSequentialGroup()
                        .addGroup(GerenciarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNome17)
                            .addComponent(FieldNomeGerenciarServicos1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator25, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(GerenciarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCPF3)
                            .addComponent(ComboOrdenaGarantia1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addComponent(BotaoBuscarGerenciarServicos1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(105, Short.MAX_VALUE))
            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, 1190, Short.MAX_VALUE)
        );
        GerenciarGarantiasLayout.setVerticalGroup(
            GerenciarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GerenciarGarantiasLayout.createSequentialGroup()
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(GerenciarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(GerenciarGarantiasLayout.createSequentialGroup()
                        .addGroup(GerenciarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNome17)
                            .addComponent(lblCPF3))
                        .addGroup(GerenciarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(GerenciarGarantiasLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(ComboOrdenaGarantia1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GerenciarGarantiasLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(FieldNomeGerenciarServicos1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator25, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BotaoBuscarGerenciarServicos1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DadosAlteraGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PaneGarantias.add(GerenciarGarantias, "card5");

        PaneContatarCli.setPreferredSize(new java.awt.Dimension(1144, 429));

        lblCPF7.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblCPF7.setText("Mensagem:");

        jTextField1.setBackground(new java.awt.Color(240, 240, 240));
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        BotaoBuscarConsultarGarantias2.setBackground(new java.awt.Color(230, 230, 230));
        BotaoBuscarConsultarGarantias2.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoBuscarConsultarGarantias2.setText("Enviar mensagem");
        BotaoBuscarConsultarGarantias2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoBuscarConsultarGarantias2.setPreferredSize(new java.awt.Dimension(151, 50));
        BotaoBuscarConsultarGarantias2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoBuscarConsultarGarantias2ActionPerformed(evt);
            }
        });

        lblCPF8.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblCPF8.setText("Dados do contato:");

        TxtNomeCtt.setBackground(new java.awt.Color(240, 240, 240));
        TxtNomeCtt.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        TxtNomeCtt.setText("Nome: Lucas");
        TxtNomeCtt.setBorder(null);
        TxtNomeCtt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtNomeCttActionPerformed(evt);
            }
        });

        TxtTelCtt.setBackground(new java.awt.Color(240, 240, 240));
        TxtTelCtt.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        TxtTelCtt.setText("Telefone: 34999999999");
        TxtTelCtt.setBorder(null);
        TxtTelCtt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtTelCttActionPerformed(evt);
            }
        });

        BotaoBuscarConsultarGarantias3.setBackground(new java.awt.Color(230, 230, 230));
        BotaoBuscarConsultarGarantias3.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoBuscarConsultarGarantias3.setText("Cancelar");
        BotaoBuscarConsultarGarantias3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoBuscarConsultarGarantias3.setPreferredSize(new java.awt.Dimension(151, 50));
        BotaoBuscarConsultarGarantias3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoBuscarConsultarGarantias3ActionPerformed(evt);
            }
        });

        jPanel44.setPreferredSize(new java.awt.Dimension(1144, 254));

        jLabel24.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(25, 25, 112));
        jLabel24.setText("GARANTIAS");

        jPanel45.setBackground(new java.awt.Color(230, 230, 230));

        BotaoGerenciarCliGer2.setBackground(new java.awt.Color(230, 230, 230));
        BotaoGerenciarCliGer2.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoGerenciarCliGer2.setForeground(new java.awt.Color(25, 25, 112));
        BotaoGerenciarCliGer2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoGerenciarCliGer2.setText("Gerenciar");
        BotaoGerenciarCliGer2.setOpaque(true);
        BotaoGerenciarCliGer2.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoGerenciarCliGer2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliGer2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliGer2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliGer2MouseExited(evt);
            }
        });

        BotaoConsultarCliGer2.setBackground(new java.awt.Color(220, 220, 220));
        BotaoConsultarCliGer2.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoConsultarCliGer2.setForeground(new java.awt.Color(25, 25, 112));
        BotaoConsultarCliGer2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoConsultarCliGer2.setText("Consultar");
        BotaoConsultarCliGer2.setOpaque(true);
        BotaoConsultarCliGer2.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoConsultarCliGer2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliGer2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliGer2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliGer2MouseExited(evt);
            }
        });

        BotaoCadastrarCliGer2.setBackground(new java.awt.Color(230, 230, 230));
        BotaoCadastrarCliGer2.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoCadastrarCliGer2.setForeground(new java.awt.Color(25, 25, 112));
        BotaoCadastrarCliGer2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoCadastrarCliGer2.setText("Cadastrar");
        BotaoCadastrarCliGer2.setOpaque(true);
        BotaoCadastrarCliGer2.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoCadastrarCliGer2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliGer2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliGer2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliGer2MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(BotaoCadastrarCliGer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoConsultarCliGer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoGerenciarCliGer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(694, Short.MAX_VALUE))
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoGerenciarCliGer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoConsultarCliGer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoCadastrarCliGer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGap(379, 379, 379)
                .addComponent(jLabel24)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout PaneContatarCliLayout = new javax.swing.GroupLayout(PaneContatarCli);
        PaneContatarCli.setLayout(PaneContatarCliLayout);
        PaneContatarCliLayout.setHorizontalGroup(
            PaneContatarCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PaneContatarCliLayout.createSequentialGroup()
                .addContainerGap(71, Short.MAX_VALUE)
                .addComponent(lblCPF7)
                .addGap(18, 18, 18)
                .addGroup(PaneContatarCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PaneContatarCliLayout.createSequentialGroup()
                        .addComponent(BotaoBuscarConsultarGarantias2, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(481, 481, 481)
                        .addComponent(BotaoBuscarConsultarGarantias3, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PaneContatarCliLayout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(135, 135, 135)
                        .addGroup(PaneContatarCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCPF8)
                            .addGroup(PaneContatarCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(TxtTelCtt)
                                .addComponent(TxtNomeCtt, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(87, 87, 87))
            .addComponent(jPanel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PaneContatarCliLayout.setVerticalGroup(
            PaneContatarCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PaneContatarCliLayout.createSequentialGroup()
                .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addGroup(PaneContatarCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCPF7)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PaneContatarCliLayout.createSequentialGroup()
                        .addComponent(lblCPF8)
                        .addGap(39, 39, 39)
                        .addGroup(PaneContatarCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TxtNomeCtt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PaneContatarCliLayout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(TxtTelCtt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(78, 78, 78)
                .addGroup(PaneContatarCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotaoBuscarConsultarGarantias2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoBuscarConsultarGarantias3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );

        PaneGarantias.add(PaneContatarCli, "card6");

        PaneMae.add(PaneGarantias, "card2");

        PaneServicos.setPreferredSize(new java.awt.Dimension(1144, 429));
        PaneServicos.setLayout(new java.awt.CardLayout());

        lblNome5.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome5.setText("Nome do conserto");

        FieldNomeCadastrarConsertos.setBackground(new java.awt.Color(240, 240, 240));
        FieldNomeCadastrarConsertos.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldNomeCadastrarConsertos.setBorder(null);
        FieldNomeCadastrarConsertos.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldNomeCadastrarConsertos.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldNomeCadastrarConsertos.setOpaque(false);
        FieldNomeCadastrarConsertos.setDocument(new JTextFieldLimit(255, true));
        FieldNomeCadastrarConsertos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldNomeCadastrarConsertosMouseClicked(evt);
            }
        });
        FieldNomeCadastrarConsertos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldNomeCadastrarConsertosKeyPressed(evt);
            }
        });

        jSeparator13.setForeground(new java.awt.Color(0, 0, 0));

        BotaoNovoCadastroConserto.setBackground(new java.awt.Color(230, 230, 230));
        BotaoNovoCadastroConserto.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoNovoCadastroConserto.setText("Novo");
        BotaoNovoCadastroConserto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoNovoCadastroConserto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoNovoCadastroConsertoActionPerformed(evt);
            }
        });

        BotaoSalvarCadastroConserto.setBackground(new java.awt.Color(230, 230, 230));
        BotaoSalvarCadastroConserto.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoSalvarCadastroConserto.setText("Salvar");
        BotaoSalvarCadastroConserto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoSalvarCadastroConserto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoSalvarCadastroConsertoActionPerformed(evt);
            }
        });

        BotaoCancelarCadastroConserto.setBackground(new java.awt.Color(230, 230, 230));
        BotaoCancelarCadastroConserto.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoCancelarCadastroConserto.setText("Cancelar");
        BotaoCancelarCadastroConserto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoCancelarCadastroConserto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoCancelarCadastroConsertoActionPerformed(evt);
            }
        });

        FieldDuracaoCadastrarConsertos.setBackground(new java.awt.Color(240, 240, 240));
        FieldDuracaoCadastrarConsertos.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldDuracaoCadastrarConsertos.setBorder(null);
        FieldDuracaoCadastrarConsertos.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldDuracaoCadastrarConsertos.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldDuracaoCadastrarConsertos.setOpaque(false);
        FieldDuracaoCadastrarConsertos.setDocument(new JTextFieldLimit(2, false, true));
        FieldDuracaoCadastrarConsertos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldDuracaoCadastrarConsertosMouseClicked(evt);
            }
        });
        FieldDuracaoCadastrarConsertos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldDuracaoCadastrarConsertosKeyPressed(evt);
            }
        });

        jSeparator22.setForeground(new java.awt.Color(0, 0, 0));

        lblNome13.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome13.setText("Prazo de garantia (mês)");

        jPanel6.setPreferredSize(new java.awt.Dimension(1144, 254));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(25, 25, 112));
        jLabel3.setText("CATEGORIAS");

        jPanel7.setBackground(new java.awt.Color(230, 230, 230));

        BotaoGerenciarCli2.setBackground(new java.awt.Color(230, 230, 230));
        BotaoGerenciarCli2.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoGerenciarCli2.setForeground(new java.awt.Color(25, 25, 112));
        BotaoGerenciarCli2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoGerenciarCli2.setText("Gerenciar");
        BotaoGerenciarCli2.setOpaque(true);
        BotaoGerenciarCli2.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoGerenciarCli2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli2MouseExited(evt);
            }
        });

        BotaoConsultarCli2.setBackground(new java.awt.Color(230, 230, 230));
        BotaoConsultarCli2.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoConsultarCli2.setForeground(new java.awt.Color(25, 25, 112));
        BotaoConsultarCli2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoConsultarCli2.setText("Consultar");
        BotaoConsultarCli2.setOpaque(true);
        BotaoConsultarCli2.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoConsultarCli2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoConsultarCli2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoConsultarCli2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoConsultarCli2MouseExited(evt);
            }
        });

        BotaoClientes4.setBackground(new java.awt.Color(220, 220, 220));
        BotaoClientes4.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoClientes4.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes4.setText("Cadastrar");
        BotaoClientes4.setOpaque(true);
        BotaoClientes4.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoClientes4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(BotaoClientes4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoConsultarCli2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoGerenciarCli2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoGerenciarCli2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoConsultarCli2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(443, 443, 443))
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout CadastrarServicosLayout = new javax.swing.GroupLayout(CadastrarServicos);
        CadastrarServicos.setLayout(CadastrarServicosLayout);
        CadastrarServicosLayout.setHorizontalGroup(
            CadastrarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarServicosLayout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addGroup(CadastrarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(CadastrarServicosLayout.createSequentialGroup()
                        .addGroup(CadastrarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CadastrarServicosLayout.createSequentialGroup()
                                .addComponent(lblNome5)
                                .addGap(0, 413, Short.MAX_VALUE))
                            .addComponent(jSeparator13)
                            .addComponent(FieldNomeCadastrarConsertos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(198, 198, 198)
                        .addGroup(CadastrarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CadastrarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jSeparator22, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(FieldDuracaoCadastrarConsertos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))
                            .addComponent(lblNome13, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(CadastrarServicosLayout.createSequentialGroup()
                        .addComponent(BotaoNovoCadastroConserto, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BotaoSalvarCadastroConserto, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(BotaoCancelarCadastroConserto, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(79, 79, 79))
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        CadastrarServicosLayout.setVerticalGroup(
            CadastrarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarServicosLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(CadastrarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(CadastrarServicosLayout.createSequentialGroup()
                        .addComponent(lblNome5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldNomeCadastrarConsertos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(CadastrarServicosLayout.createSequentialGroup()
                        .addComponent(lblNome13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldDuracaoCadastrarConsertos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator22, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(CadastrarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotaoSalvarCadastroConserto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoCancelarCadastroConserto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoNovoCadastroConserto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(83, 83, 83))
        );

        PaneServicos.add(CadastrarServicos, "card4");

        FieldConsultaDescricaoGarantias.setBackground(new java.awt.Color(240, 240, 240));
        FieldConsultaDescricaoGarantias.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldConsultaDescricaoGarantias.setBorder(null);
        FieldConsultaDescricaoGarantias.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldConsultaDescricaoGarantias.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldConsultaDescricaoGarantias.setDocument(new JTextFieldLimit(255, true));
        FieldConsultaDescricaoGarantias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldConsultaDescricaoGarantiasMouseClicked(evt);
            }
        });
        FieldConsultaDescricaoGarantias.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldConsultaDescricaoGarantiasKeyPressed(evt);
            }
        });

        lblNome7.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome7.setText("Nome do conserto");

        jSeparator15.setForeground(new java.awt.Color(0, 0, 0));

        TabelaConsultaManutencao.setAutoCreateRowSorter(true);
        TabelaConsultaManutencao.setBackground(new java.awt.Color(240, 240, 240));
        TabelaConsultaManutencao.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        TabelaConsultaManutencao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Conserto", "Tempo de garantia"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TabelaConsultaManutencao.setFocusable(false);
        TabelaConsultaManutencao.setGridColor(new java.awt.Color(204, 204, 204));
        TabelaConsultaManutencao.setSelectionBackground(new java.awt.Color(230, 230, 230));
        TabelaConsultaManutencao.setShowHorizontalLines(false);
        jScrollPane3.setViewportView(TabelaConsultaManutencao);

        BotaoBuscarGarantias.setBackground(new java.awt.Color(230, 230, 230));
        BotaoBuscarGarantias.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoBuscarGarantias.setText("Buscar");
        BotaoBuscarGarantias.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoBuscarGarantias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoBuscarGarantiasActionPerformed(evt);
            }
        });

        jPanel30.setPreferredSize(new java.awt.Dimension(1144, 254));

        jLabel17.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(25, 25, 112));
        jLabel17.setText("CATEGORIAS");

        jPanel31.setBackground(new java.awt.Color(230, 230, 230));

        BotaoGerenciarCliConsul2.setBackground(new java.awt.Color(230, 230, 230));
        BotaoGerenciarCliConsul2.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoGerenciarCliConsul2.setForeground(new java.awt.Color(25, 25, 112));
        BotaoGerenciarCliConsul2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoGerenciarCliConsul2.setText("Gerenciar");
        BotaoGerenciarCliConsul2.setOpaque(true);
        BotaoGerenciarCliConsul2.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoGerenciarCliConsul2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliConsul2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliConsul2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliConsul2MouseExited(evt);
            }
        });

        BotaoConsultarCliConsul2.setBackground(new java.awt.Color(220, 220, 220));
        BotaoConsultarCliConsul2.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoConsultarCliConsul2.setForeground(new java.awt.Color(25, 25, 112));
        BotaoConsultarCliConsul2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoConsultarCliConsul2.setText("Consultar");
        BotaoConsultarCliConsul2.setOpaque(true);
        BotaoConsultarCliConsul2.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoConsultarCliConsul2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul2MouseExited(evt);
            }
        });

        BotaoCadastrarCliConsul2.setBackground(new java.awt.Color(230, 230, 230));
        BotaoCadastrarCliConsul2.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoCadastrarCliConsul2.setForeground(new java.awt.Color(25, 25, 112));
        BotaoCadastrarCliConsul2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoCadastrarCliConsul2.setText("Cadastrar");
        BotaoCadastrarCliConsul2.setOpaque(true);
        BotaoCadastrarCliConsul2.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoCadastrarCliConsul2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul2MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(BotaoCadastrarCliConsul2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoConsultarCliConsul2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoGerenciarCliConsul2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoGerenciarCliConsul2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoConsultarCliConsul2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoCadastrarCliConsul2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addGap(443, 443, 443))
            .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout ConsultarServicosLayout = new javax.swing.GroupLayout(ConsultarServicos);
        ConsultarServicos.setLayout(ConsultarServicosLayout);
        ConsultarServicosLayout.setHorizontalGroup(
            ConsultarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ConsultarServicosLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(ConsultarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1031, Short.MAX_VALUE)
                    .addGroup(ConsultarServicosLayout.createSequentialGroup()
                        .addGroup(ConsultarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNome7)
                            .addComponent(FieldConsultaDescricaoGarantias, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                            .addComponent(jSeparator15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BotaoBuscarGarantias, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(55, 55, 55))
            .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ConsultarServicosLayout.setVerticalGroup(
            ConsultarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConsultarServicosLayout.createSequentialGroup()
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(ConsultarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ConsultarServicosLayout.createSequentialGroup()
                        .addComponent(lblNome7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldConsultaDescricaoGarantias, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BotaoBuscarGarantias, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PaneServicos.add(ConsultarServicos, "card5");

        GerenciarServicos.setPreferredSize(new java.awt.Dimension(1190, 451));

        jSeparator20.setForeground(new java.awt.Color(0, 0, 0));

        FieldNomeGerenciarServicos.setBackground(new java.awt.Color(240, 240, 240));
        FieldNomeGerenciarServicos.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldNomeGerenciarServicos.setBorder(null);
        FieldNomeGerenciarServicos.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldNomeGerenciarServicos.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldNomeGerenciarServicos.setDocument(new JTextFieldLimit(255, true));
        FieldNomeGerenciarServicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldNomeGerenciarServicosMouseClicked(evt);
            }
        });
        FieldNomeGerenciarServicos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldNomeGerenciarServicosKeyPressed(evt);
            }
        });

        lblNome12.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome12.setText("Nome do conserto");

        BotaoBuscarGerenciarServicos.setBackground(new java.awt.Color(230, 230, 230));
        BotaoBuscarGerenciarServicos.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoBuscarGerenciarServicos.setText("Buscar");
        BotaoBuscarGerenciarServicos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoBuscarGerenciarServicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoBuscarGerenciarServicosMouseClicked(evt);
            }
        });
        BotaoBuscarGerenciarServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoBuscarGerenciarServicosActionPerformed(evt);
            }
        });

        TableGerenciarServicos.setAutoCreateRowSorter(true);
        TableGerenciarServicos.setBackground(new java.awt.Color(240, 240, 240));
        TableGerenciarServicos.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        TableGerenciarServicos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Conserto", "Tempo de garantia"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TableGerenciarServicos.setFocusable(false);
        TableGerenciarServicos.setGridColor(new java.awt.Color(204, 204, 204));
        TableGerenciarServicos.setSelectionBackground(new java.awt.Color(230, 230, 230));
        TableGerenciarServicos.setShowHorizontalLines(false);
        TableGerenciarServicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableGerenciarServicosMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(TableGerenciarServicos);

        DadosAlteraServicos.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados da categoria"));

        BotaoCancelarGerenciarServicos.setBackground(new java.awt.Color(230, 230, 230));
        BotaoCancelarGerenciarServicos.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoCancelarGerenciarServicos.setText("Cancelar");
        BotaoCancelarGerenciarServicos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoCancelarGerenciarServicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoCancelarGerenciarServicosMouseClicked(evt);
            }
        });
        BotaoCancelarGerenciarServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoCancelarGerenciarServicosActionPerformed(evt);
            }
        });

        BotaoExcluirGerenciarServicos.setBackground(new java.awt.Color(230, 230, 230));
        BotaoExcluirGerenciarServicos.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoExcluirGerenciarServicos.setText("Excluir");
        BotaoExcluirGerenciarServicos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoExcluirGerenciarServicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoExcluirGerenciarServicosMouseClicked(evt);
            }
        });
        BotaoExcluirGerenciarServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoExcluirGerenciarServicosActionPerformed(evt);
            }
        });

        BotaoAlterarGerenciarServicos.setBackground(new java.awt.Color(230, 230, 230));
        BotaoAlterarGerenciarServicos.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoAlterarGerenciarServicos.setText("Alterar");
        BotaoAlterarGerenciarServicos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoAlterarGerenciarServicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoAlterarGerenciarServicosMouseClicked(evt);
            }
        });
        BotaoAlterarGerenciarServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoAlterarGerenciarServicosActionPerformed(evt);
            }
        });

        lblNome14.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome14.setText("Nome do conserto");

        FieldNomeGerenciarConsertos.setBackground(new java.awt.Color(240, 240, 240));
        FieldNomeGerenciarConsertos.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldNomeGerenciarConsertos.setBorder(null);
        FieldNomeGerenciarConsertos.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldNomeGerenciarConsertos.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldNomeGerenciarConsertos.setDocument(new JTextFieldLimit(255, true));
        FieldNomeGerenciarConsertos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldNomeGerenciarConsertosMouseClicked(evt);
            }
        });
        FieldNomeGerenciarConsertos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldNomeGerenciarConsertosKeyPressed(evt);
            }
        });

        jSeparator23.setForeground(new java.awt.Color(0, 0, 0));

        lblNome15.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome15.setText("Prazo de garantia (mês)");

        FieldDuracaoGerenciarConsertos.setBackground(new java.awt.Color(240, 240, 240));
        FieldDuracaoGerenciarConsertos.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldDuracaoGerenciarConsertos.setBorder(null);
        FieldDuracaoGerenciarConsertos.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldDuracaoGerenciarConsertos.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldDuracaoGerenciarConsertos.setDocument(new JTextFieldLimit(2, false, true));
        FieldDuracaoGerenciarConsertos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldDuracaoGerenciarConsertosMouseClicked(evt);
            }
        });
        FieldDuracaoGerenciarConsertos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldDuracaoGerenciarConsertosKeyPressed(evt);
            }
        });

        jSeparator24.setForeground(new java.awt.Color(0, 0, 0));

        FieldIDGerenciarConsertos.setBackground(new java.awt.Color(240, 240, 240));
        FieldIDGerenciarConsertos.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldIDGerenciarConsertos.setBorder(null);
        FieldIDGerenciarConsertos.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldIDGerenciarConsertos.setMinimumSize(new java.awt.Dimension(25, 25));
        CampoNome.setDocument(new JTextFieldLimit(40, true));
        FieldIDGerenciarConsertos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldIDGerenciarConsertosMouseClicked(evt);
            }
        });
        FieldIDGerenciarConsertos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldIDGerenciarConsertosKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout DadosAlteraServicosLayout = new javax.swing.GroupLayout(DadosAlteraServicos);
        DadosAlteraServicos.setLayout(DadosAlteraServicosLayout);
        DadosAlteraServicosLayout.setHorizontalGroup(
            DadosAlteraServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DadosAlteraServicosLayout.createSequentialGroup()
                .addContainerGap(67, Short.MAX_VALUE)
                .addGroup(DadosAlteraServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(DadosAlteraServicosLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(FieldIDGerenciarConsertos, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BotaoAlterarGerenciarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BotaoExcluirGerenciarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BotaoCancelarGerenciarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DadosAlteraServicosLayout.createSequentialGroup()
                        .addGroup(DadosAlteraServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNome14)
                            .addComponent(FieldNomeGerenciarConsertos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator23, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(79, 79, 79)
                        .addGroup(DadosAlteraServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNome15)
                            .addGroup(DadosAlteraServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(FieldDuracaoGerenciarConsertos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSeparator24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(68, 68, 68))
        );
        DadosAlteraServicosLayout.setVerticalGroup(
            DadosAlteraServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DadosAlteraServicosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DadosAlteraServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DadosAlteraServicosLayout.createSequentialGroup()
                        .addComponent(lblNome14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldNomeGerenciarConsertos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator23, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DadosAlteraServicosLayout.createSequentialGroup()
                        .addComponent(lblNome15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldDuracaoGerenciarConsertos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator24, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(DadosAlteraServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DadosAlteraServicosLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(FieldIDGerenciarConsertos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(51, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DadosAlteraServicosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(DadosAlteraServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BotaoExcluirGerenciarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BotaoAlterarGerenciarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BotaoCancelarGerenciarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12))))
        );

        jPanel32.setPreferredSize(new java.awt.Dimension(1144, 254));

        jLabel18.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(25, 25, 112));
        jLabel18.setText("CATEGORIAS");

        jPanel33.setBackground(new java.awt.Color(230, 230, 230));

        BotaoGerenciarCliConsul3.setBackground(new java.awt.Color(220, 220, 220));
        BotaoGerenciarCliConsul3.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoGerenciarCliConsul3.setForeground(new java.awt.Color(25, 25, 112));
        BotaoGerenciarCliConsul3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoGerenciarCliConsul3.setText("Gerenciar");
        BotaoGerenciarCliConsul3.setOpaque(true);
        BotaoGerenciarCliConsul3.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoGerenciarCliConsul3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliConsul3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliConsul3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliConsul3MouseExited(evt);
            }
        });

        BotaoConsultarCliConsul3.setBackground(new java.awt.Color(230, 230, 230));
        BotaoConsultarCliConsul3.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoConsultarCliConsul3.setForeground(new java.awt.Color(25, 25, 112));
        BotaoConsultarCliConsul3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoConsultarCliConsul3.setText("Consultar");
        BotaoConsultarCliConsul3.setOpaque(true);
        BotaoConsultarCliConsul3.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoConsultarCliConsul3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul3MouseExited(evt);
            }
        });

        BotaoCadastrarCliConsul3.setBackground(new java.awt.Color(230, 230, 230));
        BotaoCadastrarCliConsul3.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoCadastrarCliConsul3.setForeground(new java.awt.Color(25, 25, 112));
        BotaoCadastrarCliConsul3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoCadastrarCliConsul3.setText("Cadastrar");
        BotaoCadastrarCliConsul3.setOpaque(true);
        BotaoCadastrarCliConsul3.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoCadastrarCliConsul3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul3MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(BotaoCadastrarCliConsul3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoConsultarCliConsul3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoGerenciarCliConsul3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoGerenciarCliConsul3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoConsultarCliConsul3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoCadastrarCliConsul3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addGap(443, 443, 443))
            .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout GerenciarServicosLayout = new javax.swing.GroupLayout(GerenciarServicos);
        GerenciarServicos.setLayout(GerenciarServicosLayout);
        GerenciarServicosLayout.setHorizontalGroup(
            GerenciarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GerenciarServicosLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(GerenciarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(GerenciarServicosLayout.createSequentialGroup()
                        .addGroup(GerenciarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNome12)
                            .addComponent(FieldNomeGerenciarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 825, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator20, javax.swing.GroupLayout.PREFERRED_SIZE, 825, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BotaoBuscarGerenciarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane7)
                    .addComponent(DadosAlteraServicos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(114, Short.MAX_VALUE))
            .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        GerenciarServicosLayout.setVerticalGroup(
            GerenciarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GerenciarServicosLayout.createSequentialGroup()
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(GerenciarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(GerenciarServicosLayout.createSequentialGroup()
                        .addComponent(lblNome12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldNomeGerenciarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BotaoBuscarGerenciarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jSeparator20, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(DadosAlteraServicos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PaneServicos.add(GerenciarServicos, "card6");

        PaneMae.add(PaneServicos, "card4");

        PaneRelatorios.setLayout(new java.awt.CardLayout());

        RelatorioClientes.setPreferredSize(new java.awt.Dimension(1190, 451));

        jPanel34.setMaximumSize(new java.awt.Dimension(32767, 327));
        jPanel34.setPreferredSize(new java.awt.Dimension(1144, 254));

        jLabel19.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(25, 25, 112));
        jLabel19.setText("RELATÓRIOS");

        jPanel35.setBackground(new java.awt.Color(230, 230, 230));

        BotaoGerenciarCliConsul4.setBackground(new java.awt.Color(230, 230, 230));
        BotaoGerenciarCliConsul4.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoGerenciarCliConsul4.setForeground(new java.awt.Color(25, 25, 112));
        BotaoGerenciarCliConsul4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoGerenciarCliConsul4.setText("Gerenciar");
        BotaoGerenciarCliConsul4.setOpaque(true);
        BotaoGerenciarCliConsul4.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoGerenciarCliConsul4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliConsul4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliConsul4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliConsul4MouseExited(evt);
            }
        });

        BotaoConsultarCliConsul4.setBackground(new java.awt.Color(230, 230, 230));
        BotaoConsultarCliConsul4.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoConsultarCliConsul4.setForeground(new java.awt.Color(25, 25, 112));
        BotaoConsultarCliConsul4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoConsultarCliConsul4.setText("Serviços");
        BotaoConsultarCliConsul4.setOpaque(true);
        BotaoConsultarCliConsul4.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoConsultarCliConsul4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul4MouseExited(evt);
            }
        });

        BotaoCadastrarCliConsul4.setBackground(new java.awt.Color(220, 220, 220));
        BotaoCadastrarCliConsul4.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoCadastrarCliConsul4.setForeground(new java.awt.Color(25, 25, 112));
        BotaoCadastrarCliConsul4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoCadastrarCliConsul4.setText("Clientes");
        BotaoCadastrarCliConsul4.setOpaque(true);
        BotaoCadastrarCliConsul4.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoCadastrarCliConsul4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul4MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(BotaoCadastrarCliConsul4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoConsultarCliConsul4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoGerenciarCliConsul4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoGerenciarCliConsul4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoConsultarCliConsul4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoCadastrarCliConsul4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addContainerGap(357, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addGap(443, 443, 443))
            .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BotaoRelatorioCliente.setBackground(new java.awt.Color(230, 230, 230));
        BotaoRelatorioCliente.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoRelatorioCliente.setText("Gerar relatório");
        BotaoRelatorioCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoRelatorioCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoRelatorioClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout RelatorioClientesLayout = new javax.swing.GroupLayout(RelatorioClientes);
        RelatorioClientes.setLayout(RelatorioClientesLayout);
        RelatorioClientesLayout.setHorizontalGroup(
            RelatorioClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(RelatorioClientesLayout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(BotaoRelatorioCliente)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        RelatorioClientesLayout.setVerticalGroup(
            RelatorioClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RelatorioClientesLayout.createSequentialGroup()
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(203, 203, 203)
                .addComponent(BotaoRelatorioCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(243, Short.MAX_VALUE))
        );

        PaneRelatorios.add(RelatorioClientes, "card6");

        RelatorioServicos.setPreferredSize(new java.awt.Dimension(1190, 451));

        jPanel38.setPreferredSize(new java.awt.Dimension(1144, 254));

        jLabel21.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(25, 25, 112));
        jLabel21.setText("RELATÓRIOS");

        jPanel39.setBackground(new java.awt.Color(230, 230, 230));

        BotaoGerenciarCliConsul6.setBackground(new java.awt.Color(230, 230, 230));
        BotaoGerenciarCliConsul6.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoGerenciarCliConsul6.setForeground(new java.awt.Color(25, 25, 112));
        BotaoGerenciarCliConsul6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoGerenciarCliConsul6.setText("Gerenciar");
        BotaoGerenciarCliConsul6.setOpaque(true);
        BotaoGerenciarCliConsul6.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoGerenciarCliConsul6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliConsul6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliConsul6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliConsul6MouseExited(evt);
            }
        });

        BotaoConsultarCliConsul6.setBackground(new java.awt.Color(220, 220, 220));
        BotaoConsultarCliConsul6.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoConsultarCliConsul6.setForeground(new java.awt.Color(25, 25, 112));
        BotaoConsultarCliConsul6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoConsultarCliConsul6.setText("Serviços");
        BotaoConsultarCliConsul6.setOpaque(true);
        BotaoConsultarCliConsul6.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoConsultarCliConsul6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul6MouseExited(evt);
            }
        });

        BotaoCadastrarCliConsul6.setBackground(new java.awt.Color(230, 230, 230));
        BotaoCadastrarCliConsul6.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoCadastrarCliConsul6.setForeground(new java.awt.Color(25, 25, 112));
        BotaoCadastrarCliConsul6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoCadastrarCliConsul6.setText("Clientes");
        BotaoCadastrarCliConsul6.setOpaque(true);
        BotaoCadastrarCliConsul6.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoCadastrarCliConsul6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul6MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(BotaoCadastrarCliConsul6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoConsultarCliConsul6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoGerenciarCliConsul6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoGerenciarCliConsul6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoConsultarCliConsul6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoCadastrarCliConsul6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                .addContainerGap(357, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addGap(443, 443, 443))
            .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout RelatorioServicosLayout = new javax.swing.GroupLayout(RelatorioServicos);
        RelatorioServicos.setLayout(RelatorioServicosLayout);
        RelatorioServicosLayout.setHorizontalGroup(
            RelatorioServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        RelatorioServicosLayout.setVerticalGroup(
            RelatorioServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RelatorioServicosLayout.createSequentialGroup()
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(496, Short.MAX_VALUE))
        );

        PaneRelatorios.add(RelatorioServicos, "card6");

        RelatorioGerenciar.setPreferredSize(new java.awt.Dimension(1190, 451));

        jPanel40.setPreferredSize(new java.awt.Dimension(1144, 254));

        jLabel22.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(25, 25, 112));
        jLabel22.setText("RELATÓRIOS");

        jPanel41.setBackground(new java.awt.Color(230, 230, 230));

        BotaoGerenciarCliConsul7.setBackground(new java.awt.Color(220, 220, 220));
        BotaoGerenciarCliConsul7.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoGerenciarCliConsul7.setForeground(new java.awt.Color(25, 25, 112));
        BotaoGerenciarCliConsul7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoGerenciarCliConsul7.setText("Gerenciar");
        BotaoGerenciarCliConsul7.setOpaque(true);
        BotaoGerenciarCliConsul7.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoGerenciarCliConsul7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliConsul7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliConsul7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCliConsul7MouseExited(evt);
            }
        });

        BotaoConsultarCliConsul7.setBackground(new java.awt.Color(230, 230, 230));
        BotaoConsultarCliConsul7.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoConsultarCliConsul7.setForeground(new java.awt.Color(25, 25, 112));
        BotaoConsultarCliConsul7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoConsultarCliConsul7.setText("Serviços");
        BotaoConsultarCliConsul7.setOpaque(true);
        BotaoConsultarCliConsul7.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoConsultarCliConsul7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul7MouseExited(evt);
            }
        });

        BotaoCadastrarCliConsul7.setBackground(new java.awt.Color(230, 230, 230));
        BotaoCadastrarCliConsul7.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoCadastrarCliConsul7.setForeground(new java.awt.Color(25, 25, 112));
        BotaoCadastrarCliConsul7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoCadastrarCliConsul7.setText("Clientes");
        BotaoCadastrarCliConsul7.setOpaque(true);
        BotaoCadastrarCliConsul7.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoCadastrarCliConsul7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul7MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(BotaoCadastrarCliConsul7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoConsultarCliConsul7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoGerenciarCliConsul7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoGerenciarCliConsul7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoConsultarCliConsul7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoCadastrarCliConsul7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel40Layout.createSequentialGroup()
                .addGap(21, 357, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addGap(443, 443, 443))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout RelatorioGerenciarLayout = new javax.swing.GroupLayout(RelatorioGerenciar);
        RelatorioGerenciar.setLayout(RelatorioGerenciarLayout);
        RelatorioGerenciarLayout.setHorizontalGroup(
            RelatorioGerenciarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        RelatorioGerenciarLayout.setVerticalGroup(
            RelatorioGerenciarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RelatorioGerenciarLayout.createSequentialGroup()
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(496, Short.MAX_VALUE))
        );

        PaneRelatorios.add(RelatorioGerenciar, "card6");

        PaneMae.add(PaneRelatorios, "card5");

        PaneCadastros.setPreferredSize(new java.awt.Dimension(1144, 429));
        PaneCadastros.setLayout(new java.awt.CardLayout());

        jPanel42.setMaximumSize(new java.awt.Dimension(32767, 327));
        jPanel42.setPreferredSize(new java.awt.Dimension(1144, 254));

        jLabel23.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(25, 25, 112));
        jLabel23.setText("CADASTROS");

        jPanel43.setBackground(new java.awt.Color(230, 230, 230));

        BotaoConsultarCliConsul8.setBackground(new java.awt.Color(230, 230, 230));
        BotaoConsultarCliConsul8.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoConsultarCliConsul8.setForeground(new java.awt.Color(25, 25, 112));
        BotaoConsultarCliConsul8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoConsultarCliConsul8.setText("Gerenciar");
        BotaoConsultarCliConsul8.setOpaque(true);
        BotaoConsultarCliConsul8.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoConsultarCliConsul8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul8MouseExited(evt);
            }
        });

        BotaoCadastrarCliConsul8.setBackground(new java.awt.Color(220, 220, 220));
        BotaoCadastrarCliConsul8.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoCadastrarCliConsul8.setForeground(new java.awt.Color(25, 25, 112));
        BotaoCadastrarCliConsul8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoCadastrarCliConsul8.setText("Cadastrar");
        BotaoCadastrarCliConsul8.setOpaque(true);
        BotaoCadastrarCliConsul8.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoCadastrarCliConsul8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul8MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(BotaoCadastrarCliConsul8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoConsultarCliConsul8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoConsultarCliConsul8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoCadastrarCliConsul8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel42Layout.createSequentialGroup()
                .addContainerGap(397, Short.MAX_VALUE)
                .addComponent(jLabel23)
                .addGap(398, 398, 398))
            .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        lblNome27.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome27.setText("Usuário");

        FielduserCadastrarlogins.setBackground(new java.awt.Color(240, 240, 240));
        FielduserCadastrarlogins.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FielduserCadastrarlogins.setBorder(null);
        FielduserCadastrarlogins.setMaximumSize(new java.awt.Dimension(25, 25));
        FielduserCadastrarlogins.setMinimumSize(new java.awt.Dimension(25, 25));
        FielduserCadastrarlogins.setOpaque(false);
        FieldNomeCadastrarConsertos.setDocument(new JTextFieldLimit(255, true));
        FielduserCadastrarlogins.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FielduserCadastrarloginsMouseClicked(evt);
            }
        });
        FielduserCadastrarlogins.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FielduserCadastrarloginsKeyPressed(evt);
            }
        });

        jSeparator36.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator36.setForeground(new java.awt.Color(0, 0, 0));

        BotaoNovoCadastroCliente5.setBackground(new java.awt.Color(230, 230, 230));
        BotaoNovoCadastroCliente5.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoNovoCadastroCliente5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoNovoCadastroCliente5.setText("Cadastrar");
        BotaoNovoCadastroCliente5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210)));
        BotaoNovoCadastroCliente5.setOpaque(true);
        BotaoNovoCadastroCliente5.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoNovoCadastroCliente5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoNovoCadastroCliente5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoNovoCadastroCliente5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoNovoCadastroCliente5MouseExited(evt);
            }
        });

        jSeparator37.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator37.setForeground(new java.awt.Color(0, 0, 0));

        lblNome28.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome28.setText("Senha");

        lblNome29.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome29.setText("Tipo de conta");

        ComboEscolhalogins.setBackground(new java.awt.Color(240, 240, 240));
        ComboEscolhalogins.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        ComboEscolhalogins.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Padrão", "Administrador" }));
        ComboEscolhalogins.setBorder(null);

        FieldsenhaCadastrarlogins.setBackground(new java.awt.Color(240, 240, 240));
        FieldsenhaCadastrarlogins.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldsenhaCadastrarlogins.setBorder(null);
        FieldsenhaCadastrarlogins.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FieldsenhaCadastrarloginsKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel6.setText("Ver senha");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel6MouseExited(evt);
            }
        });

        Fieldversenha.setEditable(false);
        Fieldversenha.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        Fieldversenha.setForeground(new java.awt.Color(240, 240, 240));
        Fieldversenha.setBorder(null);
        Fieldversenha.setMaximumSize(new java.awt.Dimension(25, 25));
        Fieldversenha.setMinimumSize(new java.awt.Dimension(25, 25));
        Fieldversenha.setOpaque(false);
        FieldNomeCadastrarConsertos.setDocument(new JTextFieldLimit(255, true));
        Fieldversenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldversenhaMouseClicked(evt);
            }
        });
        Fieldversenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldversenhaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout AdicionarCadastrosLayout = new javax.swing.GroupLayout(AdicionarCadastros);
        AdicionarCadastros.setLayout(AdicionarCadastrosLayout);
        AdicionarCadastrosLayout.setHorizontalGroup(
            AdicionarCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(AdicionarCadastrosLayout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addGroup(AdicionarCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSeparator36, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FielduserCadastrarlogins, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                    .addComponent(lblNome27, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNome28, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator37, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                    .addComponent(FieldsenhaCadastrarlogins, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AdicionarCadastrosLayout.createSequentialGroup()
                        .addComponent(Fieldversenha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(AdicionarCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ComboEscolhalogins, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNome29)
                    .addComponent(BotaoNovoCadastroCliente5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(283, 283, 283))
        );
        AdicionarCadastrosLayout.setVerticalGroup(
            AdicionarCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdicionarCadastrosLayout.createSequentialGroup()
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addGroup(AdicionarCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(AdicionarCadastrosLayout.createSequentialGroup()
                        .addComponent(lblNome27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FielduserCadastrarlogins, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AdicionarCadastrosLayout.createSequentialGroup()
                        .addComponent(lblNome29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ComboEscolhalogins, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(1, 1, 1)
                .addComponent(jSeparator36, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addGroup(AdicionarCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(AdicionarCadastrosLayout.createSequentialGroup()
                        .addComponent(lblNome28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldsenhaCadastrarlogins, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BotaoNovoCadastroCliente5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jSeparator37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(AdicionarCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AdicionarCadastrosLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel6))
                    .addComponent(Fieldversenha, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(222, Short.MAX_VALUE))
        );

        PaneCadastros.add(AdicionarCadastros, "card3");

        GerenciarCadastros.setPreferredSize(new java.awt.Dimension(1144, 737));

        jPanel36.setMaximumSize(new java.awt.Dimension(32767, 327));
        jPanel36.setPreferredSize(new java.awt.Dimension(1144, 254));

        jLabel20.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(25, 25, 112));
        jLabel20.setText("LOGINS");

        jPanel37.setBackground(new java.awt.Color(230, 230, 230));

        BotaoConsultarCliConsul5.setBackground(new java.awt.Color(220, 220, 220));
        BotaoConsultarCliConsul5.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoConsultarCliConsul5.setForeground(new java.awt.Color(25, 25, 112));
        BotaoConsultarCliConsul5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoConsultarCliConsul5.setText("Gerenciar");
        BotaoConsultarCliConsul5.setOpaque(true);
        BotaoConsultarCliConsul5.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoConsultarCliConsul5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoConsultarCliConsul5MouseExited(evt);
            }
        });

        BotaoCadastrarCliConsul5.setBackground(new java.awt.Color(230, 230, 230));
        BotaoCadastrarCliConsul5.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoCadastrarCliConsul5.setForeground(new java.awt.Color(25, 25, 112));
        BotaoCadastrarCliConsul5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoCadastrarCliConsul5.setText("Cadastrar");
        BotaoCadastrarCliConsul5.setOpaque(true);
        BotaoCadastrarCliConsul5.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoCadastrarCliConsul5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoCadastrarCliConsul5MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(BotaoCadastrarCliConsul5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoConsultarCliConsul5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoConsultarCliConsul5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoCadastrarCliConsul5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                .addContainerGap(480, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addGap(446, 446, 446))
            .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        lblNome26.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome26.setText("Usuário");

        FieldUserGerenciarLogins.setBackground(new java.awt.Color(240, 240, 240));
        FieldUserGerenciarLogins.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldUserGerenciarLogins.setBorder(null);
        FieldUserGerenciarLogins.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldUserGerenciarLogins.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldUserGerenciarLogins.setOpaque(false);
        FieldNomeCadastrarConsertos.setDocument(new JTextFieldLimit(255, true));
        FieldUserGerenciarLogins.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldUserGerenciarLoginsMouseClicked(evt);
            }
        });
        FieldUserGerenciarLogins.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldUserGerenciarLoginsKeyPressed(evt);
            }
        });

        jSeparator35.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator35.setForeground(new java.awt.Color(0, 0, 0));

        BotaoNovoCadastroCliente4.setBackground(new java.awt.Color(230, 230, 230));
        BotaoNovoCadastroCliente4.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoNovoCadastroCliente4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoNovoCadastroCliente4.setText("Buscar");
        BotaoNovoCadastroCliente4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210)));
        BotaoNovoCadastroCliente4.setOpaque(true);
        BotaoNovoCadastroCliente4.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoNovoCadastroCliente4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoNovoCadastroCliente4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoNovoCadastroCliente4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoNovoCadastroCliente4MouseExited(evt);
            }
        });

        jSeparator33.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator33.setForeground(new java.awt.Color(0, 0, 0));

        lblNome20.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome20.setText("Usuário");

        jSeparator34.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator34.setForeground(new java.awt.Color(0, 0, 0));

        lblNome21.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome21.setText("Senha");

        ComboTipoConta.setBackground(new java.awt.Color(240, 240, 240));
        ComboTipoConta.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        ComboTipoConta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Padrão", "Administrador" }));
        ComboTipoConta.setBorder(null);

        lblNome30.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome30.setText("Tipo de conta");

        FieldUser.setBackground(new java.awt.Color(240, 240, 240));
        FieldUser.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldUser.setBorder(null);

        FieldSenha.setBackground(new java.awt.Color(240, 240, 240));
        FieldSenha.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldSenha.setBorder(null);

        BotaoAlterarGerLogins.setBackground(new java.awt.Color(230, 230, 230));
        BotaoAlterarGerLogins.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoAlterarGerLogins.setText("Alterar");
        BotaoAlterarGerLogins.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoAlterarGerLogins.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoAlterarGerLoginsActionPerformed(evt);
            }
        });

        BotaoExcluirGerLogins.setBackground(new java.awt.Color(230, 230, 230));
        BotaoExcluirGerLogins.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoExcluirGerLogins.setText("Excluir");
        BotaoExcluirGerLogins.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoExcluirGerLogins.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoExcluirGerLoginsActionPerformed(evt);
            }
        });

        BotaoCancelarGerLogins.setBackground(new java.awt.Color(230, 230, 230));
        BotaoCancelarGerLogins.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoCancelarGerLogins.setText("Cancelar");
        BotaoCancelarGerLogins.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoCancelarGerLogins.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoCancelarGerLoginsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout GerenciarLoginsLayout = new javax.swing.GroupLayout(GerenciarLogins);
        GerenciarLogins.setLayout(GerenciarLoginsLayout);
        GerenciarLoginsLayout.setHorizontalGroup(
            GerenciarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GerenciarLoginsLayout.createSequentialGroup()
                .addComponent(jSeparator34, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(GerenciarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNome30)
                    .addComponent(ComboTipoConta, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(104, 104, 104)
                .addGroup(GerenciarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BotaoAlterarGerLogins, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoExcluirGerLogins, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(GerenciarLoginsLayout.createSequentialGroup()
                .addGroup(GerenciarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GerenciarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblNome20)
                        .addComponent(lblNome21)
                        .addComponent(jSeparator33)
                        .addComponent(FieldUser, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(FieldSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BotaoCancelarGerLogins, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        GerenciarLoginsLayout.setVerticalGroup(
            GerenciarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GerenciarLoginsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GerenciarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GerenciarLoginsLayout.createSequentialGroup()
                        .addGroup(GerenciarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNome30)
                            .addComponent(lblNome20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(GerenciarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ComboTipoConta, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(GerenciarLoginsLayout.createSequentialGroup()
                                .addComponent(FieldUser, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(jSeparator33, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(29, 29, 29)
                        .addComponent(lblNome21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(FieldSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator34, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(GerenciarLoginsLayout.createSequentialGroup()
                        .addComponent(BotaoAlterarGerLogins, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BotaoExcluirGerLogins, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BotaoCancelarGerLogins, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        TableGerenciarLogins.setAutoCreateRowSorter(true);
        TableGerenciarLogins.setBackground(new java.awt.Color(240, 240, 240));
        TableGerenciarLogins.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        TableGerenciarLogins.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "ID", "Usuário", "Senha", "Tipo de conta"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableGerenciarLogins.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        TableGerenciarLogins.setFocusable(false);
        TableGerenciarLogins.setGridColor(new java.awt.Color(204, 204, 204));
        TableGerenciarLogins.setRowHeight(21);
        TableGerenciarLogins.setSelectionBackground(new java.awt.Color(230, 230, 230));
        TableGerenciarLogins.setShowHorizontalLines(false);
        TableGerenciarLogins.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableGerenciarLoginsMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(TableGerenciarLogins);

        FieldId.setEditable(false);
        FieldId.setForeground(new java.awt.Color(240, 240, 240));
        FieldId.setBorder(null);

        javax.swing.GroupLayout GerenciarCadastrosLayout = new javax.swing.GroupLayout(GerenciarCadastros);
        GerenciarCadastros.setLayout(GerenciarCadastrosLayout);
        GerenciarCadastrosLayout.setHorizontalGroup(
            GerenciarCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GerenciarCadastrosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GerenciarCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, GerenciarCadastrosLayout.createSequentialGroup()
                        .addComponent(FieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addGroup(GerenciarCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(GerenciarCadastrosLayout.createSequentialGroup()
                                .addGroup(GerenciarCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblNome26)
                                    .addComponent(jSeparator35, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                                    .addComponent(FieldUserGerenciarLogins, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(64, 64, 64)
                                .addComponent(BotaoNovoCadastroCliente4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(GerenciarLogins, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(GerenciarCadastrosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 1022, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(59, 59, 59))
        );
        GerenciarCadastrosLayout.setVerticalGroup(
            GerenciarCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GerenciarCadastrosLayout.createSequentialGroup()
                .addGroup(GerenciarCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(GerenciarCadastrosLayout.createSequentialGroup()
                        .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(lblNome26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldUserGerenciarLogins, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BotaoNovoCadastroCliente4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jSeparator35, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(GerenciarCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GerenciarCadastrosLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(GerenciarLogins, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(GerenciarCadastrosLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(FieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        PaneCadastros.add(GerenciarCadastros, "card2");

        PaneMae.add(PaneCadastros, "card6");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(SideBoard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(PaneMae, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SideBoard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(PaneMae, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleName("Gerenciamento de Garantia");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //----------------------Inicio das ações de revista ------------------------------------
    private void BotaoClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientesMouseClicked
        CadastrarClientes.setVisible(true);
        ConsultarClientes.setVisible(false);
        GerenciarClientes.setVisible(false);
        PaneCadastros.setVisible(false);
        PaneClientes.setVisible(true);
        PaneGarantias.setVisible(false);
        PaneServicos.setVisible(false);
        setLblColor(BotaoClientes);
        ResetColor(BotaoGarantias);
        ResetColor(BotaoConsertos);
        ResetColor(BotaoRelatorios);
    }//GEN-LAST:event_BotaoClientesMouseClicked

    private void BotaoGarantiasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGarantiasMouseClicked
        PaneClientes.setVisible(false);
        PaneGarantias.setVisible(true);
        PaneServicos.setVisible(false);
        PaneCadastros.setVisible(false);
        PaneContatarCli.setVisible(false);
        CadastrarGarantias.setVisible(true);
        ConsultarGarantias.setVisible(false);
        GerenciarGarantias.setVisible(false);
        setLblColor(BotaoGarantias);
        ResetColor(BotaoClientes);
        ResetColor(BotaoConsertos);
        ResetColor(BotaoRelatorios);
    }//GEN-LAST:event_BotaoGarantiasMouseClicked


    private void BotaoConsertosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsertosMouseClicked
        PaneClientes.setVisible(false);
        PaneGarantias.setVisible(false);
        PaneServicos.setVisible(true);
        PaneCadastros.setVisible(false);
        CadastrarServicos.setVisible(true);
        ConsultarServicos.setVisible(false);
        GerenciarServicos.setVisible(false);
        ResetColor(BotaoClientes);
        ResetColor(BotaoGarantias);
        setLblColor(BotaoConsertos);
        ResetColor(BotaoRelatorios);
    }//GEN-LAST:event_BotaoConsertosMouseClicked

    private void FieldConsultaNomeClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldConsultaNomeClienteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldConsultaNomeClienteMouseClicked

    private void FieldConsultaNomeClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldConsultaNomeClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldConsultaNomeClienteActionPerformed

    private void FieldConsultaNomeClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldConsultaNomeClienteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldConsultaNomeClienteKeyPressed

    private void BotaoBuscarConsultaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoBuscarConsultaClienteActionPerformed
        ListaBuscaCliente = null;
        Cliente cli = new Cliente();
        clienteDAO clidao = new clienteDAO();

        try {
            if (!FieldConsultaNomeCliente.getText().isEmpty()) {
                cli.setNome(FieldConsultaNomeCliente.getText());
            }
            if (!FieldConsultaCPFCliente.getText().isEmpty()) {
                cli.setCpf(FieldConsultaCPFCliente.getText());
            }
            ListaBuscaCliente = clidao.ListaBuscaCliente(cli);
            atualizarTabelaBuscaCliente();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_BotaoBuscarConsultaClienteActionPerformed

    private void FieldCadastroEmailClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldCadastroEmailClienteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroEmailClienteKeyPressed

    private void FieldCadastroEmailClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldCadastroEmailClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroEmailClienteActionPerformed

    private void FieldCadastroEmailClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldCadastroEmailClienteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroEmailClienteMouseClicked

    private void FieldCadastroEnderecoClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldCadastroEnderecoClienteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroEnderecoClienteKeyPressed

    private void FieldCadastroEnderecoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldCadastroEnderecoClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroEnderecoClienteActionPerformed

    private void FieldCadastroEnderecoClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldCadastroEnderecoClienteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroEnderecoClienteMouseClicked

    private void BotaoCancelarCadastroClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoCancelarCadastroClienteActionPerformed
        if (JOptionPane.showConfirmDialog(null, "Todos os dados inseridos serão perdidos. Deseja continuar?", "Sistema", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            SalvarCamposCadastroClientes();
        }
    }//GEN-LAST:event_BotaoCancelarCadastroClienteActionPerformed

    private void BotaoSalvarCadastroClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoSalvarCadastroClienteActionPerformed
        if (FieldCadastroNomeCliente.getText().isEmpty() || FieldCadastroCPFCliente.getText().isEmpty() || FieldCadastroTelefoneCliente.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios (*), por favor!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
                Cliente cli = new Cliente();
                clienteDAO clidao = new clienteDAO();

                cli.getId();
                cli.setNome(FieldCadastroNomeCliente.getText());
                cli.setCpf(FieldCadastroCPFCliente.getText());
                cli.setTelefone(FieldCadastroTelefoneCliente.getText());
                cli.setCidade(FieldCadastroCidadeCliente.getText());
                cli.setEndereco(FieldCadastroEnderecoCliente.getText());
                cli.setEmail(FieldCadastroEmailCliente.getText());
                clidao.InserirCliente(cli);

                try {
                    AutoComplete();
                    AutoComplete1();
                    AutoComplete2();
                    atualizarTabelaConsultaCliente();
                    JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                    SalvarCamposCadastroClientes();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Algo de errado ocorreu! Erro: " + ex.getMessage(), "Sistema", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println(ex.getMessage());
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Esse cliente já existe! Erro:" + ex.getMessage(), "Sistema", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_BotaoSalvarCadastroClienteActionPerformed

    private void FieldCadastroNomeClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldCadastroNomeClienteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroNomeClienteKeyPressed

    private void FieldCadastroNomeClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldCadastroNomeClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroNomeClienteActionPerformed

    private void FieldCadastroNomeClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldCadastroNomeClienteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroNomeClienteMouseClicked

    private void FieldCadastroCidadeClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldCadastroCidadeClienteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroCidadeClienteMouseClicked

    private void FieldCadastroCidadeClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldCadastroCidadeClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroCidadeClienteActionPerformed

    private void FieldCadastroCidadeClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldCadastroCidadeClienteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroCidadeClienteKeyPressed

    private void FieldConsultaCPFClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldConsultaCPFClienteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldConsultaCPFClienteMouseClicked

    private void FieldConsultaCPFClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldConsultaCPFClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldConsultaCPFClienteActionPerformed

    private void FieldConsultaCPFClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldConsultaCPFClienteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldConsultaCPFClienteKeyPressed

    private void FieldCadastroCPFClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldCadastroCPFClienteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroCPFClienteMouseClicked

    private void FieldCadastroCPFClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldCadastroCPFClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroCPFClienteActionPerformed

    private void FieldCadastroCPFClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldCadastroCPFClienteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroCPFClienteKeyPressed

    private void BotaoCancelarGerenciarServicos2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCancelarGerenciarServicos2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoCancelarGerenciarServicos2MouseClicked

    private void BotaoCancelarGerenciarServicos2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoCancelarGerenciarServicos2ActionPerformed
        SalvarCamposGerenciarClientes();
    }//GEN-LAST:event_BotaoCancelarGerenciarServicos2ActionPerformed

    private void BotaoExcluirGerenciarGarantia1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoExcluirGerenciarGarantia1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoExcluirGerenciarGarantia1MouseClicked

    private void BotaoExcluirGerenciarGarantia1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoExcluirGerenciarGarantia1ActionPerformed
        if (FieldIDGerenciarClientes.getText().isEmpty()
                || FieldNomeGerenciarClientes.getText().isEmpty()
                || FieldCPFGerenciarClientes.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione um cliente para remoção, caso não queira remover, cancele a ação!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
        } else if (JOptionPane.showConfirmDialog(null, "Deseja mesmo excluir esse cliente?", "Sistema", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            try {
                Cliente cli = new Cliente();
                clienteDAO clidao = new clienteDAO();

                cli.setId(Integer.parseInt(FieldIDGerenciarClientes.getText()));

                clidao.RemoverCliente(cli);

                try {
                    atualizarTableGerenciarCCliente();
                    atualizarTabelaConsultaCliente();

                    AutoComplete();
                    AutoComplete1();
                    AutoComplete2();

                    SalvarCamposGerenciarClientes();

                    JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Algo de errado ocorreu! Erro: " + ex.getMessage(), "Sistema", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println(ex.getMessage());
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Não foi possível excluir esse cliente, talvez ele já esteja vinculado à alguma garantia!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_BotaoExcluirGerenciarGarantia1ActionPerformed

    private void BotaoAlterarGerenciarGarantia1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoAlterarGerenciarGarantia1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoAlterarGerenciarGarantia1MouseClicked

    private void BotaoAlterarGerenciarGarantia1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoAlterarGerenciarGarantia1ActionPerformed
        if (FieldIDGerenciarClientes.getText().isEmpty() || FieldNomeGerenciarClientes.getText().isEmpty() || FieldCPFGerenciarClientes.getText().isEmpty() || FieldTelefoneGerenciarClientes.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Há campos vazios, preencha os campos obrigatórios (*) para proceder com a alteração!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
        } else if (JOptionPane.showConfirmDialog(null, "Deseja mesmo alterar esse cliente?", "Sistema", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            try {
                Cliente cli = new Cliente();
                clienteDAO clidao = new clienteDAO();

                cli.setId(Integer.parseInt(FieldIDGerenciarClientes.getText()));
                cli.setNome(FieldNomeGerenciarClientes.getText());
                cli.setCpf(FieldCPFGerenciarClientes.getText());
                cli.setTelefone(FieldTelefoneGerenciarClientes.getText());
                cli.setCidade(FieldCidadeGerenciarClientes.getText());
                cli.setEndereco(FieldEnderecoGerenciarClientes.getText());
                cli.setEmail(FieldEmailGerenciarClientes.getText());

                clidao.AlterarCliente(cli);

                try {
                    atualizarTableGerenciarCCliente();
                    atualizarTabelaConsultaCliente();

                    AutoComplete();
                    AutoComplete1();
                    AutoComplete2();

                    SalvarCamposGerenciarClientes();

                    JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Algo de errado ocorreu! Erro: " + ex.getMessage(), "Sistema", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println(ex.getMessage());
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Não foi possível alterar esse cliente, talvez ele já esteja vinculado à alguma garantia!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_BotaoAlterarGerenciarGarantia1ActionPerformed

    private void FieldNomeGerenciarClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldNomeGerenciarClientesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNomeGerenciarClientesMouseClicked

    private void FieldNomeGerenciarClientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldNomeGerenciarClientesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNomeGerenciarClientesKeyPressed

    private void FieldIDGerenciarClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldIDGerenciarClientesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldIDGerenciarClientesMouseClicked

    private void FieldIDGerenciarClientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldIDGerenciarClientesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldIDGerenciarClientesKeyPressed

    private void FieldCPFGerenciarClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldCPFGerenciarClientesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCPFGerenciarClientesMouseClicked

    private void FieldCPFGerenciarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldCPFGerenciarClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCPFGerenciarClientesActionPerformed

    private void FieldCPFGerenciarClientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldCPFGerenciarClientesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCPFGerenciarClientesKeyPressed

    private void FieldCidadeGerenciarClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldCidadeGerenciarClientesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCidadeGerenciarClientesMouseClicked

    private void FieldCidadeGerenciarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldCidadeGerenciarClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCidadeGerenciarClientesActionPerformed

    private void FieldCidadeGerenciarClientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldCidadeGerenciarClientesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCidadeGerenciarClientesKeyPressed

    private void FieldEnderecoGerenciarClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldEnderecoGerenciarClientesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldEnderecoGerenciarClientesMouseClicked

    private void FieldEnderecoGerenciarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldEnderecoGerenciarClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldEnderecoGerenciarClientesActionPerformed

    private void FieldEnderecoGerenciarClientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldEnderecoGerenciarClientesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldEnderecoGerenciarClientesKeyPressed

    private void FieldEmailGerenciarClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldEmailGerenciarClientesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldEmailGerenciarClientesMouseClicked

    private void FieldEmailGerenciarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldEmailGerenciarClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldEmailGerenciarClientesActionPerformed

    private void FieldEmailGerenciarClientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldEmailGerenciarClientesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldEmailGerenciarClientesKeyPressed

    private void TableGerenciarClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableGerenciarClienteMouseClicked
        TableGerenciarCliente.getTableHeader().setReorderingAllowed(false);

        FieldIDGerenciarClientes.setText(TableGerenciarCliente.getValueAt(TableGerenciarCliente.getSelectedRow(), 0).toString());
        FieldNomeGerenciarClientes.setText(TableGerenciarCliente.getValueAt(TableGerenciarCliente.getSelectedRow(), 1).toString());
        FieldCPFGerenciarClientes.setText(TableGerenciarCliente.getValueAt(TableGerenciarCliente.getSelectedRow(), 2).toString());
        FieldTelefoneGerenciarClientes.setText(TableGerenciarCliente.getValueAt(TableGerenciarCliente.getSelectedRow(), 3).toString());
        if (TableGerenciarCliente.getValueAt(TableGerenciarCliente.getSelectedRow(), 4).toString() != null) {
            FieldCidadeGerenciarClientes.setText(TableGerenciarCliente.getValueAt(TableGerenciarCliente.getSelectedRow(), 4).toString());
        }
        if (TableGerenciarCliente.getValueAt(TableGerenciarCliente.getSelectedRow(), 5).toString() != null) {
            FieldEnderecoGerenciarClientes.setText(TableGerenciarCliente.getValueAt(TableGerenciarCliente.getSelectedRow(), 5).toString());
        }
        if (TableGerenciarCliente.getValueAt(TableGerenciarCliente.getSelectedRow(), 6).toString() != null) {
            FieldEmailGerenciarClientes.setText(TableGerenciarCliente.getValueAt(TableGerenciarCliente.getSelectedRow(), 6).toString());
        }

        DestravaCamposGerenciarClientes();

        DadosAlteraClientes.setVisible(true);
    }//GEN-LAST:event_TableGerenciarClienteMouseClicked

    private void FieldGerenciarNomeClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldGerenciarNomeClienteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldGerenciarNomeClienteMouseClicked

    private void FieldGerenciarNomeClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldGerenciarNomeClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldGerenciarNomeClienteActionPerformed

    private void FieldGerenciarNomeClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldGerenciarNomeClienteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldGerenciarNomeClienteKeyPressed

    private void FieldGerenciarCPFClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldGerenciarCPFClienteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldGerenciarCPFClienteMouseClicked

    private void FieldGerenciarCPFClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldGerenciarCPFClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldGerenciarCPFClienteActionPerformed

    private void FieldGerenciarCPFClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldGerenciarCPFClienteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldGerenciarCPFClienteKeyPressed

    private void BotaoBuscarConsultaCliente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoBuscarConsultaCliente1ActionPerformed
        ListaBuscaCliente = null;
        Cliente cli = new Cliente();
        clienteDAO clidao = new clienteDAO();

        try {
            if (!FieldGerenciarNomeCliente.getText().isEmpty()) {
                cli.setNome(FieldGerenciarNomeCliente.getText());
            }
            if (!FieldGerenciarCPFCliente.getText().isEmpty()) {
                cli.setCpf(FieldGerenciarCPFCliente.getText());
            }
            ListaBuscaCliente = clidao.ListaBuscaCliente(cli);
            atualizarTableGerenciarBCliente();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_BotaoBuscarConsultaCliente1ActionPerformed

    private void FieldDuracaoGerenciarConsertosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldDuracaoGerenciarConsertosKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldDuracaoGerenciarConsertosKeyPressed

    private void FieldDuracaoGerenciarConsertosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldDuracaoGerenciarConsertosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldDuracaoGerenciarConsertosMouseClicked

    private void FieldNomeGerenciarConsertosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldNomeGerenciarConsertosKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNomeGerenciarConsertosKeyPressed

    private void FieldNomeGerenciarConsertosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldNomeGerenciarConsertosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNomeGerenciarConsertosMouseClicked

    private void BotaoAlterarGerenciarServicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoAlterarGerenciarServicosActionPerformed
        if (FieldIDGerenciarConsertos.getText().isEmpty() || FieldNomeGerenciarConsertos.getText().isEmpty() || FieldDuracaoGerenciarConsertos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Há campos vazios, preencha esses campos para proceder com a alteração!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
        } else if (JOptionPane.showConfirmDialog(null, "Deseja mesmo alterar essa categoria?", "Sistema", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            try {
                Manutencao logins = new Manutencao();
                ManutencaoDAO loginsdao = new ManutencaoDAO();

                logins.setId(Integer.parseInt(FieldIDGerenciarConsertos.getText()));
                logins.setDescricao(FieldNomeGerenciarConsertos.getText());
                logins.setDuracao(Integer.parseInt(FieldDuracaoGerenciarConsertos.getText()));

                loginsdao.AlterarManutencao(logins);

                try {
                    ResetaCombos();
                    atualizarTabelaGerenciaCManutencao();
                    atualizarTabelaConsultaManutencao();
                    SalvarCamposGerenciarServicos();

                    JOptionPane.showMessageDialog(null, "Categoria alterada com sucesso!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Algo de errado ocorreu! Erro: " + ex.getMessage(), "Sistema", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println(ex.getMessage());
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Não foi possível alterar essa categoria, talvez ela já esteja vinculada à alguma garantia! " /*+
                    "\n" + "Erro:" + ex.getMessage()*/, "Sistema", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_BotaoAlterarGerenciarServicosActionPerformed

    private void BotaoAlterarGerenciarServicosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoAlterarGerenciarServicosMouseClicked

    }//GEN-LAST:event_BotaoAlterarGerenciarServicosMouseClicked

    private void BotaoExcluirGerenciarServicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoExcluirGerenciarServicosActionPerformed
        if (FieldIDGerenciarConsertos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione uma categoria para remoção, caso não queira remover, cancele a ação!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
        } else if (JOptionPane.showConfirmDialog(null, "Deseja mesmo excluir essa categoria?", "Sistema", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            try {
                Manutencao logins = new Manutencao();
                ManutencaoDAO loginsDAO = new ManutencaoDAO();

                logins.setId(Integer.parseInt(FieldIDGerenciarConsertos.getText()));
                loginsDAO.RemoverManutencao(logins);
                try {

                    ResetaCombos();
                    atualizarTabelaGerenciaCManutencao();
                    atualizarTabelaConsultaManutencao();
                    SalvarCamposGerenciarServicos();

                    JOptionPane.showMessageDialog(null, "Categoria excluída com sucesso!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Algo de errado ocorreu! Erro: " + e.getMessage(), "Sistema", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println(e.getMessage());
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Não foi possível excluir essa categoria, talvez ela já esteja vinculada à alguma garantia!" /*+
                    "\n" + "Erro:" + ex.getMessage()*/, "Sistema", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_BotaoExcluirGerenciarServicosActionPerformed

    private void BotaoExcluirGerenciarServicosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoExcluirGerenciarServicosMouseClicked

    }//GEN-LAST:event_BotaoExcluirGerenciarServicosMouseClicked

    private void BotaoCancelarGerenciarServicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoCancelarGerenciarServicosActionPerformed
        SalvarCamposGerenciarServicos();
    }//GEN-LAST:event_BotaoCancelarGerenciarServicosActionPerformed

    private void BotaoCancelarGerenciarServicosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCancelarGerenciarServicosMouseClicked

    }//GEN-LAST:event_BotaoCancelarGerenciarServicosMouseClicked

    private void TableGerenciarServicosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableGerenciarServicosMouseClicked
        TableGerenciarServicos.getTableHeader().setReorderingAllowed(false);
        FieldIDGerenciarConsertos.setText(TableGerenciarServicos.getValueAt(TableGerenciarServicos.getSelectedRow(), 0).toString());
        FieldNomeGerenciarConsertos.setText(TableGerenciarServicos.getValueAt(TableGerenciarServicos.getSelectedRow(), 1).toString());
        FieldDuracaoGerenciarConsertos.setText(TableGerenciarServicos.getValueAt(TableGerenciarServicos.getSelectedRow(), 2).toString());

        DadosAlteraServicos.setVisible(true);
        DestravaCamposGerenciarServicos();
    }//GEN-LAST:event_TableGerenciarServicosMouseClicked

    private void BotaoBuscarGerenciarServicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoBuscarGerenciarServicosActionPerformed
        ListaBuscaManutencao = null;
        Manutencao logins = new Manutencao();
        ManutencaoDAO loginsdao = new ManutencaoDAO();

        try {
            if (!FieldNomeGerenciarServicos.getText().isEmpty()) {
                logins.setDescricao(FieldNomeGerenciarServicos.getText());
            }

            ListaBuscaManutencao = loginsdao.ListaBuscaManutencao(logins);
            atualizarTabelaGerenciaBManutencao();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_BotaoBuscarGerenciarServicosActionPerformed

    private void BotaoBuscarGerenciarServicosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoBuscarGerenciarServicosMouseClicked

    }//GEN-LAST:event_BotaoBuscarGerenciarServicosMouseClicked

    private void FieldNomeGerenciarServicosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldNomeGerenciarServicosKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNomeGerenciarServicosKeyPressed

    private void FieldNomeGerenciarServicosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldNomeGerenciarServicosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNomeGerenciarServicosMouseClicked

    private void BotaoBuscarGarantiasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoBuscarGarantiasActionPerformed
        ListaBuscaManutencao = null;
        Manutencao logins = new Manutencao();
        ManutencaoDAO loginsdao = new ManutencaoDAO();

        try {
            if (!FieldConsultaDescricaoGarantias.getText().isEmpty()) {
                logins.setDescricao(FieldConsultaDescricaoGarantias.getText());
            }

            ListaBuscaManutencao = loginsdao.ListaBuscaManutencao(logins);
            atualizarTabelaBuscaManutencao();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_BotaoBuscarGarantiasActionPerformed

    private void FieldConsultaDescricaoGarantiasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldConsultaDescricaoGarantiasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldConsultaDescricaoGarantiasKeyPressed

    private void FieldConsultaDescricaoGarantiasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldConsultaDescricaoGarantiasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldConsultaDescricaoGarantiasMouseClicked

    private void FieldDuracaoCadastrarConsertosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldDuracaoCadastrarConsertosKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldDuracaoCadastrarConsertosKeyPressed

    private void FieldDuracaoCadastrarConsertosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldDuracaoCadastrarConsertosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldDuracaoCadastrarConsertosMouseClicked

    private void BotaoCancelarCadastroConsertoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoCancelarCadastroConsertoActionPerformed
        SalvarCamposCadastroServicos();
    }//GEN-LAST:event_BotaoCancelarCadastroConsertoActionPerformed

    private void BotaoSalvarCadastroConsertoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoSalvarCadastroConsertoActionPerformed
        if (FieldNomeCadastrarConsertos.getText().isEmpty() || FieldDuracaoCadastrarConsertos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Há campos vazios, preencha-os por favor!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
                Manutencao logins = new Manutencao();
                ManutencaoDAO loginsdao = new ManutencaoDAO();

                logins.getId();
                logins.setDescricao(FieldNomeCadastrarConsertos.getText());
                logins.setDuracao(Integer.parseInt(FieldDuracaoCadastrarConsertos.getText()));

                loginsdao.InserirManutencao(logins);

                try {
                    ResetaCombos();
                    atualizarTabelaGerenciaCManutencao();
                    atualizarTabelaConsultaManutencao();
                    JOptionPane.showMessageDialog(null, "Categoria cadastrada com sucesso!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                    SalvarCamposCadastroServicos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Algo de errado ocorreu! Erro: " + ex.getMessage(), "Sistema", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println(ex.getMessage());
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Talvez essa categoria já exista! "/*Erro:" + ex.getMessage()*/, "Sistema", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_BotaoSalvarCadastroConsertoActionPerformed

    private void BotaoNovoCadastroConsertoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoNovoCadastroConsertoActionPerformed
        DestravaCamposCadastroServicos();
    }//GEN-LAST:event_BotaoNovoCadastroConsertoActionPerformed

    private void FieldNomeCadastrarConsertosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldNomeCadastrarConsertosKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNomeCadastrarConsertosKeyPressed

    private void FieldNomeCadastrarConsertosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldNomeCadastrarConsertosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNomeCadastrarConsertosMouseClicked

    private void BotaoBuscarConsultarGarantias3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoBuscarConsultarGarantias3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoBuscarConsultarGarantias3ActionPerformed

    private void TxtTelCttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtTelCttActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtTelCttActionPerformed

    private void TxtNomeCttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtNomeCttActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtNomeCttActionPerformed

    private void BotaoBuscarConsultarGarantias2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoBuscarConsultarGarantias2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoBuscarConsultarGarantias2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void CampoGerenciaDataFormatadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CampoGerenciaDataFormatadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CampoGerenciaDataFormatadaActionPerformed

    private void FieldIDGerenciarGarantiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldIDGerenciarGarantiaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldIDGerenciarGarantiaKeyPressed

    private void FieldIDGerenciarGarantiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldIDGerenciarGarantiaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldIDGerenciarGarantiaMouseClicked

    private void FieldNomeGerenciarGarantiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldNomeGerenciarGarantiaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNomeGerenciarGarantiaKeyPressed

    private void FieldNomeGerenciarGarantiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldNomeGerenciarGarantiaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNomeGerenciarGarantiaMouseClicked

    private void BotaoAlterarGerenciarGarantiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoAlterarGerenciarGarantiaActionPerformed
        if (FieldIDGerenciarGarantia.getText().isEmpty() || FieldNomeGerenciarGarantia.getText().isEmpty() || CampoGerenciaDataFormatada.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Há campos vazios, preencha esses campos para proceder com a alteração!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
        } else if (JOptionPane.showConfirmDialog(null, "Deseja mesmo alterar essa garantia?", "Sistema", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            try {
                Garantia gar = new Garantia();
                GarantiaDAO gardao = new GarantiaDAO();

                gar.setId(Integer.parseInt(FieldIDGerenciarGarantia.getText()));
                gar.setNome(FieldNomeGerenciarGarantia.getText());
                gar.setDescricao((String) ComboGerenciaEscolhaConserto.getSelectedItem());
                gar.setSaida_concerto(LocalDate.parse(CampoGerenciaDataFormatada.getText(), formatter));
                gar.setDt_garantia(LocalDate.parse(FuncGarantia(gar.getSaida_concerto(), gar.getDescricao()), formatter));//atenção redobrada nessa linha

                gardao.AlterarGarantia(gar);

                try {
                    atualizarTabelaGerenciaCGarantia();
                    atualizarTabelaConsultaGarantia();
                    SalvarCamposGarenciarGarantia();
                    JOptionPane.showMessageDialog(null, "Garantia alterada com sucesso!", "Sistema", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Algo de errado ocorreu! Contate o suporte técnico. Erro: " + ex.getMessage(), "Sistema", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println(ex.getMessage());
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Não foi possível alterar essa garantia, contate o suporte técnico!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_BotaoAlterarGerenciarGarantiaActionPerformed

    private void BotaoAlterarGerenciarGarantiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoAlterarGerenciarGarantiaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoAlterarGerenciarGarantiaMouseClicked

    private void BotaoExcluirGerenciarGarantiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoExcluirGerenciarGarantiaActionPerformed
        if (FieldIDGerenciarGarantia.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione uma garantia para remoção, caso não queira remover, cancele a ação!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
        } else if (JOptionPane.showConfirmDialog(null, "Deseja mesmo excluir essa garantia?", "Sistema", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            try {
                Garantia gar = new Garantia();
                GarantiaDAO gardao = new GarantiaDAO();

                gar.setId(Integer.parseInt(FieldIDGerenciarGarantia.getText()));
                gardao.RemoverGarantia(gar);
                try {
                    atualizarTabelaGerenciaCGarantia();
                    atualizarTabelaConsultaGarantia();
                    SalvarCamposGarenciarGarantia();
                    JOptionPane.showMessageDialog(null, "Garantia excluída com sucesso!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Algo de errado ocorreu! Erro: " + e.getMessage(), "Sistema", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println(e.getMessage());
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Não foi possível excluir essa garantia, contate o suporte técnico!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_BotaoExcluirGerenciarGarantiaActionPerformed

    private void BotaoExcluirGerenciarGarantiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoExcluirGerenciarGarantiaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoExcluirGerenciarGarantiaMouseClicked

    private void BotaoCancelarGerenciarGarantiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoCancelarGerenciarGarantiaActionPerformed
        SalvarCamposGarenciarGarantia();
    }//GEN-LAST:event_BotaoCancelarGerenciarGarantiaActionPerformed

    private void BotaoCancelarGerenciarGarantiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCancelarGerenciarGarantiaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoCancelarGerenciarGarantiaMouseClicked

    private void TableGerenciarGarantiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableGerenciarGarantiaMouseClicked
        TableGerenciarGarantia.getTableHeader().setReorderingAllowed(false);
        FieldIDGerenciarGarantia.setText(TableGerenciarGarantia.getValueAt(TableGerenciarGarantia.getSelectedRow(), 0).toString());
        FieldNomeGerenciarGarantia.setText(TableGerenciarGarantia.getValueAt(TableGerenciarGarantia.getSelectedRow(), 1).toString());
        ComboGerenciaEscolhaConserto.getModel().setSelectedItem(TableGerenciarGarantia.getValueAt(TableGerenciarGarantia.getSelectedRow(), 2).toString());
        CampoGerenciaDataFormatada.setText(TableGerenciarGarantia.getValueAt(TableGerenciarGarantia.getSelectedRow(), 3).toString());
        DestravaCamposGarenciarGarantia();
        DadosAlteraGarantia.setVisible(true);
    }//GEN-LAST:event_TableGerenciarGarantiaMouseClicked

    private void BotaoBuscarGerenciarServicos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoBuscarGerenciarServicos1ActionPerformed
        ListaBuscaGarantia = null;
        Garantia gar = new Garantia();
        GarantiaDAO gardao = new GarantiaDAO();

        String auxiliar;

        try {
            if (!FieldNomeGerenciarServicos1.getText().isEmpty()) {
                gar.setNome(FieldNomeGerenciarServicos1.getText());
            }

            ListaBuscaGarantia = gardao.ListaBuscaGarantia(gar, auxiliar = auxiliar = (String) ComboOrdenaGarantia1.getSelectedItem());
            atualizarTabelaBuscaBGarantia();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_BotaoBuscarGerenciarServicos1ActionPerformed

    private void BotaoBuscarGerenciarServicos1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoBuscarGerenciarServicos1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoBuscarGerenciarServicos1MouseClicked

    private void FieldNomeGerenciarServicos1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldNomeGerenciarServicos1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNomeGerenciarServicos1KeyPressed

    private void FieldNomeGerenciarServicos1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldNomeGerenciarServicos1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNomeGerenciarServicos1MouseClicked

    private void BotaoBuscarConsultarGarantias1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoBuscarConsultarGarantias1ActionPerformed
        PaneContatarCli.setVisible(true);
        CadastrarGarantias.setVisible(false);
        ConsultarGarantias.setVisible(false);
        GerenciarGarantias.setVisible(false);
    }//GEN-LAST:event_BotaoBuscarConsultarGarantias1ActionPerformed

    private void BotaoBuscarConsultarGarantiasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoBuscarConsultarGarantiasActionPerformed
        ListaBuscaGarantia = null;
        Garantia gar = new Garantia();
        GarantiaDAO gardao = new GarantiaDAO();

        String auxiliar;

        try {
            if (!FieldConsultaNomeGarantia.getText().isEmpty()) {
                gar.setNome(FieldConsultaNomeGarantia.getText());
            }

            ListaBuscaGarantia = gardao.ListaBuscaGarantia(gar, auxiliar = (String) ComboOrdenaGarantia.getSelectedItem());
            atualizarTabelaBuscaGarantia();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_BotaoBuscarConsultarGarantiasActionPerformed

    private void FieldConsultaNomeGarantiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldConsultaNomeGarantiaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldConsultaNomeGarantiaKeyPressed

    private void FieldConsultaNomeGarantiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldConsultaNomeGarantiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldConsultaNomeGarantiaActionPerformed

    private void FieldConsultaNomeGarantiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldConsultaNomeGarantiaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldConsultaNomeGarantiaMouseClicked

    private void BotaoNovoGarantiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoNovoGarantiaActionPerformed
        DestravaCamposCadastroGarantia();
    }//GEN-LAST:event_BotaoNovoGarantiaActionPerformed

    private void BotaoCancelarGarantiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoCancelarGarantiaActionPerformed
        SalvarCamposCadastroGarantia();
    }//GEN-LAST:event_BotaoCancelarGarantiaActionPerformed

    private void BotaoSalvarGarantiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoSalvarGarantiaActionPerformed
        if (CampoNome.getText().isEmpty() || ComboEscolhaConserto.getSelectedItem().equals(null) || CampoDataFormatada.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Há campos vazios, preencha-os por favor!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Garantia gar = new Garantia();

            try {
                gar.getId();
                gar.setNome(CampoNome.getText());
                gar.setDescricao((String) ComboEscolhaConserto.getSelectedItem());
                gar.setSaida_concerto(LocalDate.parse(CampoDataFormatada.getText(), formatter));
                gar.setDt_garantia(LocalDate.parse(FuncGarantia(gar.getSaida_concerto(), gar.getDescricao()), formatter));//atenção redobrada nessa linha

                GarantiaDAO garantiaDAO = new GarantiaDAO();
                garantiaDAO.InserirGarantia(gar);

                RelatorioDAO relatorio = new RelatorioDAO();
                relatorio.GeraGarantia();

                try {

                    JOptionPane.showMessageDialog(null, "Garantia cadastrada com sucesso!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                    SalvarCamposCadastroGarantia();
                    atualizarTabelaConsultaGarantia();
                    atualizarTabelaGerenciaCGarantia();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Algo de errado ocorreu! Erro: " + ex.getMessage(), "Sistema", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println(ex.getMessage());
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "O nome do cliente inserido não está cadastrado. Cadastre-o para prosseguir!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(ex.getMessage());
            }

        }
    }//GEN-LAST:event_BotaoSalvarGarantiaActionPerformed

    private void CampoDataFormatadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CampoDataFormatadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CampoDataFormatadaActionPerformed

    private void CampoNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CampoNomeKeyPressed

    }//GEN-LAST:event_CampoNomeKeyPressed

    private void CampoNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CampoNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CampoNomeActionPerformed

    private void CampoNomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CampoNomeMouseClicked

    }//GEN-LAST:event_CampoNomeMouseClicked

    private void BotaoGerenciarCliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliMouseClicked
        ConsultarClientes.setVisible(false);
        CadastrarClientes.setVisible(false);
        GerenciarClientes.setVisible(true);
    }//GEN-LAST:event_BotaoGerenciarCliMouseClicked

    private void BotaoClientes2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes2MouseClicked

    private void BotaoConsultarCliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliMouseClicked
        ConsultarClientes.setVisible(true);
        CadastrarClientes.setVisible(false);
        GerenciarClientes.setVisible(false);
    }//GEN-LAST:event_BotaoConsultarCliMouseClicked

    private void BotaoConsultarCliMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliMouseEntered
        BotaoConsultarCli.setBackground(new Color(220, 220, 220));
    }//GEN-LAST:event_BotaoConsultarCliMouseEntered

    private void BotaoConsultarCliMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliMouseExited
        BotaoConsultarCli.setBackground(new Color(230, 230, 230));
    }//GEN-LAST:event_BotaoConsultarCliMouseExited

    private void BotaoGerenciarCliMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliMouseEntered
        BotaoGerenciarCli.setBackground(new Color(220, 220, 220));
    }//GEN-LAST:event_BotaoGerenciarCliMouseEntered

    private void BotaoGerenciarCliMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliMouseExited
        BotaoGerenciarCli.setBackground(new Color(230, 230, 230));
    }//GEN-LAST:event_BotaoGerenciarCliMouseExited

    private void BotaoGerenciarCliConsulMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsulMouseClicked
        ConsultarClientes.setVisible(false);
        CadastrarClientes.setVisible(false);
        GerenciarClientes.setVisible(true);
    }//GEN-LAST:event_BotaoGerenciarCliConsulMouseClicked

    private void BotaoGerenciarCliConsulMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsulMouseEntered
        BotaoGerenciarCliConsul.setBackground(new Color(220, 220, 220));
    }//GEN-LAST:event_BotaoGerenciarCliConsulMouseEntered

    private void BotaoGerenciarCliConsulMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsulMouseExited
        BotaoGerenciarCliConsul.setBackground(new Color(230, 230, 230));
    }//GEN-LAST:event_BotaoGerenciarCliConsulMouseExited

    private void BotaoConsultarCliConsulMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsulMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsulMouseClicked

    private void BotaoConsultarCliConsulMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsulMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsulMouseEntered

    private void BotaoConsultarCliConsulMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsulMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsulMouseExited

    private void BotaoCadastrarCliConsulMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsulMouseClicked
        ConsultarClientes.setVisible(false);
        CadastrarClientes.setVisible(true);
        GerenciarClientes.setVisible(false);
    }//GEN-LAST:event_BotaoCadastrarCliConsulMouseClicked

    private void BotaoCadastrarCliConsulMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsulMouseEntered
        BotaoCadastrarCliConsul.setBackground(new Color(220, 220, 220));
    }//GEN-LAST:event_BotaoCadastrarCliConsulMouseEntered

    private void BotaoCadastrarCliConsulMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsulMouseExited
        BotaoCadastrarCliConsul.setBackground(new Color(230, 230, 230));        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliConsulMouseExited

    private void BotaoGerenciarCliGerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliGerMouseClicked
        ConsultarClientes.setVisible(false);
        CadastrarClientes.setVisible(false);
        GerenciarClientes.setVisible(true);
    }//GEN-LAST:event_BotaoGerenciarCliGerMouseClicked

    private void BotaoGerenciarCliGerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliGerMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliGerMouseEntered

    private void BotaoGerenciarCliGerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliGerMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliGerMouseExited

    private void BotaoConsultarCliGerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliGerMouseClicked
        ConsultarClientes.setVisible(true);
        CadastrarClientes.setVisible(false);
        GerenciarClientes.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliGerMouseClicked

    private void BotaoConsultarCliGerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliGerMouseEntered
        BotaoConsultarCliGer.setBackground(new Color(220, 220, 220));
    }//GEN-LAST:event_BotaoConsultarCliGerMouseEntered

    private void BotaoConsultarCliGerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliGerMouseExited
        BotaoConsultarCliGer.setBackground(new Color(230, 230, 230));
    }//GEN-LAST:event_BotaoConsultarCliGerMouseExited

    private void BotaoCadastrarCliGerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliGerMouseClicked
        ConsultarClientes.setVisible(false);
        CadastrarClientes.setVisible(true);
        GerenciarClientes.setVisible(false);
    }//GEN-LAST:event_BotaoCadastrarCliGerMouseClicked

    private void BotaoCadastrarCliGerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliGerMouseEntered
        BotaoCadastrarCliGer.setBackground(new Color(220, 220, 220));
    }//GEN-LAST:event_BotaoCadastrarCliGerMouseEntered

    private void BotaoCadastrarCliGerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliGerMouseExited
        BotaoCadastrarCliGer.setBackground(new Color(230, 230, 230));
    }//GEN-LAST:event_BotaoCadastrarCliGerMouseExited

    private void BotaoGerenciarCliGer1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliGer1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliGer1MouseClicked

    private void BotaoGerenciarCliGer1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliGer1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliGer1MouseEntered

    private void BotaoGerenciarCliGer1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliGer1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliGer1MouseExited

    private void BotaoConsultarCliGer1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliGer1MouseClicked
        ConsultarGarantias.setVisible(true);
        CadastrarGarantias.setVisible(false);
        GerenciarGarantias.setVisible(false);
        PaneContatarCli.setVisible(false);
    }//GEN-LAST:event_BotaoConsultarCliGer1MouseClicked

    private void BotaoConsultarCliGer1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliGer1MouseEntered
        setLblColor(BotaoConsultarCliGer1);
    }//GEN-LAST:event_BotaoConsultarCliGer1MouseEntered

    private void BotaoConsultarCliGer1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliGer1MouseExited
        ResetColor(BotaoConsultarCliGer1);
    }//GEN-LAST:event_BotaoConsultarCliGer1MouseExited

    private void BotaoCadastrarCliGer1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliGer1MouseClicked
        ConsultarGarantias.setVisible(false);
        CadastrarGarantias.setVisible(true);
        GerenciarGarantias.setVisible(false);
        PaneContatarCli.setVisible(false); // TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliGer1MouseClicked

    private void BotaoCadastrarCliGer1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliGer1MouseEntered
        setLblColor(BotaoCadastrarCliGer1);
    }//GEN-LAST:event_BotaoCadastrarCliGer1MouseEntered

    private void BotaoCadastrarCliGer1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliGer1MouseExited
        ResetColor(BotaoCadastrarCliGer1);// TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliGer1MouseExited

    private void BotaoGerenciarCliConsul1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul1MouseClicked
        ConsultarGarantias.setVisible(false);
        CadastrarGarantias.setVisible(false);
        GerenciarGarantias.setVisible(true);
        PaneContatarCli.setVisible(false);
    }//GEN-LAST:event_BotaoGerenciarCliConsul1MouseClicked

    private void BotaoGerenciarCliConsul1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul1MouseEntered
        setLblColor(BotaoGerenciarCliConsul1); // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliConsul1MouseEntered

    private void BotaoGerenciarCliConsul1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul1MouseExited
        ResetColor(BotaoGerenciarCliConsul1);// TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliConsul1MouseExited

    private void BotaoConsultarCliConsul1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsul1MouseClicked

    private void BotaoConsultarCliConsul1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul1MouseEntered

    }//GEN-LAST:event_BotaoConsultarCliConsul1MouseEntered

    private void BotaoConsultarCliConsul1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsul1MouseExited

    private void BotaoCadastrarCliConsul1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul1MouseClicked
        ConsultarGarantias.setVisible(false);
        CadastrarGarantias.setVisible(true);
        GerenciarGarantias.setVisible(false);
        PaneContatarCli.setVisible(false);
    }//GEN-LAST:event_BotaoCadastrarCliConsul1MouseClicked

    private void BotaoCadastrarCliConsul1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul1MouseEntered
        setLblColor(BotaoCadastrarCliConsul1);// TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliConsul1MouseEntered

    private void BotaoCadastrarCliConsul1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul1MouseExited
        ResetColor(BotaoCadastrarCliConsul1);// TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliConsul1MouseExited

    private void BotaoGerenciarCli1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli1MouseClicked
        ConsultarGarantias.setVisible(false);
        CadastrarGarantias.setVisible(false);
        GerenciarGarantias.setVisible(true);
        PaneContatarCli.setVisible(false);
    }//GEN-LAST:event_BotaoGerenciarCli1MouseClicked

    private void BotaoGerenciarCli1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli1MouseEntered
        setLblColor(BotaoGerenciarCli1);
    }//GEN-LAST:event_BotaoGerenciarCli1MouseEntered

    private void BotaoGerenciarCli1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli1MouseExited
        ResetColor(BotaoGerenciarCli1);
    }//GEN-LAST:event_BotaoGerenciarCli1MouseExited

    private void BotaoConsultarCli1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCli1MouseClicked
        ConsultarGarantias.setVisible(true);
        CadastrarGarantias.setVisible(false);
        GerenciarGarantias.setVisible(false);
        PaneContatarCli.setVisible(false);
    }//GEN-LAST:event_BotaoConsultarCli1MouseClicked

    private void BotaoConsultarCli1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCli1MouseEntered
        setLblColor(BotaoConsultarCli1);// TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCli1MouseEntered

    private void BotaoConsultarCli1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCli1MouseExited
        ResetColor(BotaoConsultarCli1);// TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCli1MouseExited

    private void BotaoClientes3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes3MouseClicked

    private void BotaoGerenciarCli2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli2MouseClicked
        ConsultarServicos.setVisible(false);
        CadastrarServicos.setVisible(false);
        GerenciarServicos.setVisible(true);
    }//GEN-LAST:event_BotaoGerenciarCli2MouseClicked

    private void BotaoGerenciarCli2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli2MouseEntered
        setLblColor(BotaoGerenciarCli2);
    }//GEN-LAST:event_BotaoGerenciarCli2MouseEntered

    private void BotaoGerenciarCli2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli2MouseExited
        ResetColor(BotaoGerenciarCli2);
    }//GEN-LAST:event_BotaoGerenciarCli2MouseExited

    private void BotaoConsultarCli2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCli2MouseClicked
        ConsultarServicos.setVisible(true);
        CadastrarServicos.setVisible(false);
        GerenciarServicos.setVisible(false);
    }//GEN-LAST:event_BotaoConsultarCli2MouseClicked

    private void BotaoConsultarCli2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCli2MouseEntered
        setLblColor(BotaoConsultarCli2);
    }//GEN-LAST:event_BotaoConsultarCli2MouseEntered

    private void BotaoConsultarCli2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCli2MouseExited
        ResetColor(BotaoConsultarCli2);
    }//GEN-LAST:event_BotaoConsultarCli2MouseExited

    private void BotaoClientes4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes4MouseClicked

    private void BotaoGerenciarCliConsul2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul2MouseClicked
        ConsultarServicos.setVisible(false);
        CadastrarServicos.setVisible(false);
        GerenciarServicos.setVisible(true);
    }//GEN-LAST:event_BotaoGerenciarCliConsul2MouseClicked

    private void BotaoGerenciarCliConsul2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul2MouseEntered
        setLblColor(BotaoGerenciarCliConsul2);
    }//GEN-LAST:event_BotaoGerenciarCliConsul2MouseEntered

    private void BotaoGerenciarCliConsul2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul2MouseExited
        ResetColor(BotaoGerenciarCliConsul2);// TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliConsul2MouseExited

    private void BotaoConsultarCliConsul2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsul2MouseClicked

    private void BotaoConsultarCliConsul2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsul2MouseEntered

    private void BotaoConsultarCliConsul2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsul2MouseExited

    private void BotaoCadastrarCliConsul2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul2MouseClicked
        ConsultarServicos.setVisible(false);
        CadastrarServicos.setVisible(true);
        GerenciarServicos.setVisible(false);
    }//GEN-LAST:event_BotaoCadastrarCliConsul2MouseClicked

    private void BotaoCadastrarCliConsul2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul2MouseEntered
        setLblColor(BotaoCadastrarCliConsul2);
    }//GEN-LAST:event_BotaoCadastrarCliConsul2MouseEntered

    private void BotaoCadastrarCliConsul2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul2MouseExited
        ResetColor(BotaoCadastrarCliConsul2);
    }//GEN-LAST:event_BotaoCadastrarCliConsul2MouseExited

    private void BotaoGerenciarCliConsul3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliConsul3MouseClicked

    private void BotaoGerenciarCliConsul3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliConsul3MouseEntered

    private void BotaoGerenciarCliConsul3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul3MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliConsul3MouseExited

    private void BotaoConsultarCliConsul3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul3MouseClicked
        ConsultarServicos.setVisible(true);
        CadastrarServicos.setVisible(false);
        GerenciarServicos.setVisible(false);
    }//GEN-LAST:event_BotaoConsultarCliConsul3MouseClicked

    private void BotaoConsultarCliConsul3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul3MouseEntered
        setLblColor(BotaoConsultarCliConsul3);
    }//GEN-LAST:event_BotaoConsultarCliConsul3MouseEntered

    private void BotaoConsultarCliConsul3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul3MouseExited
        ResetColor(BotaoConsultarCliConsul3);
    }//GEN-LAST:event_BotaoConsultarCliConsul3MouseExited

    private void BotaoCadastrarCliConsul3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul3MouseClicked
        ConsultarServicos.setVisible(false);
        CadastrarServicos.setVisible(true);
        GerenciarServicos.setVisible(false);
    }//GEN-LAST:event_BotaoCadastrarCliConsul3MouseClicked

    private void BotaoCadastrarCliConsul3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul3MouseEntered
        setLblColor(BotaoCadastrarCliConsul3);
    }//GEN-LAST:event_BotaoCadastrarCliConsul3MouseEntered

    private void BotaoCadastrarCliConsul3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul3MouseExited
        ResetColor(BotaoCadastrarCliConsul3);
    }//GEN-LAST:event_BotaoCadastrarCliConsul3MouseExited

    private void FieldIDGerenciarConsertosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldIDGerenciarConsertosKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldIDGerenciarConsertosKeyPressed

    private void FieldIDGerenciarConsertosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldIDGerenciarConsertosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldIDGerenciarConsertosMouseClicked

    private void BotaoRelatoriosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoRelatoriosMouseClicked
        PaneClientes.setVisible(false);
        PaneGarantias.setVisible(false);
        PaneServicos.setVisible(false);
        PaneRelatorios.setVisible(true);
        setLblColor(BotaoRelatorios);
        ResetColor(BotaoGarantias);
        ResetColor(BotaoConsertos);
        ResetColor(BotaoClientes);
        ResetColor(BotaoCadastros);
    }//GEN-LAST:event_BotaoRelatoriosMouseClicked

    private void BotaoGerenciarCliConsul4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul4MouseClicked
        RelatorioClientes.setVisible(false);
        RelatorioServicos.setVisible(false);
        RelatorioGerenciar.setVisible(true);
    }//GEN-LAST:event_BotaoGerenciarCliConsul4MouseClicked

    private void BotaoGerenciarCliConsul4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul4MouseEntered
        setLblColor(BotaoGerenciarCliConsul4); // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliConsul4MouseEntered

    private void BotaoGerenciarCliConsul4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul4MouseExited
        ResetColor(BotaoGerenciarCliConsul4);// TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliConsul4MouseExited

    private void BotaoConsultarCliConsul4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul4MouseClicked
        RelatorioClientes.setVisible(false);
        RelatorioServicos.setVisible(true);
        RelatorioGerenciar.setVisible(false);
    }//GEN-LAST:event_BotaoConsultarCliConsul4MouseClicked

    private void BotaoConsultarCliConsul4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul4MouseEntered
        setLblColor(BotaoConsultarCliConsul4);
    }//GEN-LAST:event_BotaoConsultarCliConsul4MouseEntered

    private void BotaoConsultarCliConsul4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul4MouseExited
        ResetColor(BotaoConsultarCliConsul4); // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsul4MouseExited

    private void BotaoCadastrarCliConsul4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliConsul4MouseClicked

    private void BotaoCadastrarCliConsul4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliConsul4MouseEntered

    private void BotaoCadastrarCliConsul4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul4MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliConsul4MouseExited

    private void BotaoGerenciarCliConsul6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul6MouseClicked
        RelatorioClientes.setVisible(false);
        RelatorioServicos.setVisible(false);
        RelatorioGerenciar.setVisible(true);
    }//GEN-LAST:event_BotaoGerenciarCliConsul6MouseClicked

    private void BotaoGerenciarCliConsul6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul6MouseEntered
        setLblColor(BotaoGerenciarCliConsul6);
    }//GEN-LAST:event_BotaoGerenciarCliConsul6MouseEntered

    private void BotaoGerenciarCliConsul6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul6MouseExited
        ResetColor(BotaoGerenciarCliConsul6);
    }//GEN-LAST:event_BotaoGerenciarCliConsul6MouseExited

    private void BotaoConsultarCliConsul6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsul6MouseClicked

    private void BotaoConsultarCliConsul6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul6MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsul6MouseEntered

    private void BotaoConsultarCliConsul6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul6MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsul6MouseExited

    private void BotaoCadastrarCliConsul6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul6MouseClicked
        RelatorioClientes.setVisible(true);
        RelatorioServicos.setVisible(false);
        RelatorioGerenciar.setVisible(false);
    }//GEN-LAST:event_BotaoCadastrarCliConsul6MouseClicked

    private void BotaoCadastrarCliConsul6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul6MouseEntered
        setLblColor(BotaoCadastrarCliConsul6);
    }//GEN-LAST:event_BotaoCadastrarCliConsul6MouseEntered

    private void BotaoCadastrarCliConsul6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul6MouseExited
        ResetColor(BotaoCadastrarCliConsul6);
    }//GEN-LAST:event_BotaoCadastrarCliConsul6MouseExited

    private void BotaoGerenciarCliConsul7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliConsul7MouseClicked

    private void BotaoGerenciarCliConsul7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul7MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliConsul7MouseEntered

    private void BotaoGerenciarCliConsul7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul7MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliConsul7MouseExited

    private void BotaoConsultarCliConsul7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul7MouseClicked
        RelatorioClientes.setVisible(false);
        RelatorioServicos.setVisible(true);
        RelatorioGerenciar.setVisible(false);
    }//GEN-LAST:event_BotaoConsultarCliConsul7MouseClicked

    private void BotaoConsultarCliConsul7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul7MouseEntered
        setLblColor(BotaoConsultarCliConsul7);
    }//GEN-LAST:event_BotaoConsultarCliConsul7MouseEntered

    private void BotaoConsultarCliConsul7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul7MouseExited
        ResetColor(BotaoConsultarCliConsul7);
    }//GEN-LAST:event_BotaoConsultarCliConsul7MouseExited

    private void BotaoCadastrarCliConsul7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul7MouseClicked
        RelatorioClientes.setVisible(true);
        RelatorioServicos.setVisible(false);
        RelatorioGerenciar.setVisible(false);
    }//GEN-LAST:event_BotaoCadastrarCliConsul7MouseClicked

    private void BotaoCadastrarCliConsul7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul7MouseEntered
        setLblColor(BotaoCadastrarCliConsul7);
    }//GEN-LAST:event_BotaoCadastrarCliConsul7MouseEntered

    private void BotaoCadastrarCliConsul7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul7MouseExited
        ResetColor(BotaoCadastrarCliConsul7);
    }//GEN-LAST:event_BotaoCadastrarCliConsul7MouseExited

    private void BotaoGarantiasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGarantiasMouseEntered
        setLblColor(BotaoGarantias);
    }//GEN-LAST:event_BotaoGarantiasMouseEntered

    private void BotaoGarantiasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGarantiasMouseExited
        ResetColor(BotaoGarantias);
    }//GEN-LAST:event_BotaoGarantiasMouseExited

    private void BotaoClientesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientesMouseEntered
        setLblColor(BotaoClientes);
    }//GEN-LAST:event_BotaoClientesMouseEntered

    private void BotaoClientesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientesMouseExited
        ResetColor(BotaoClientes);
    }//GEN-LAST:event_BotaoClientesMouseExited

    private void BotaoConsertosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsertosMouseEntered
        setLblColor(BotaoConsertos); // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsertosMouseEntered

    private void BotaoConsertosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsertosMouseExited
        ResetColor(BotaoConsertos); // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsertosMouseExited

    private void BotaoRelatoriosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoRelatoriosMouseEntered
        setLblColor(BotaoRelatorios); // TODO add your handling code here:
    }//GEN-LAST:event_BotaoRelatoriosMouseEntered

    private void BotaoRelatoriosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoRelatoriosMouseExited
        ResetColor(BotaoRelatorios);// TODO add your handling code here:
    }//GEN-LAST:event_BotaoRelatoriosMouseExited

    private void BotaoRelatorioClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoRelatorioClienteActionPerformed
        // TODO add your handling code here:
        RelatorioDAO relatoriocli = new RelatorioDAO();
        relatoriocli.RelatorioCliente();
    }//GEN-LAST:event_BotaoRelatorioClienteActionPerformed

    private void BotaoCadastrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrosMouseClicked
        PaneClientes.setVisible(false);
        PaneGarantias.setVisible(false);
        PaneServicos.setVisible(false);
        PaneRelatorios.setVisible(false);
        PaneCadastros.setVisible(true);
        AdicionarCadastros.setVisible(true);
        GerenciarCadastros.setVisible(false);
        setLblColor(BotaoCadastros);
        ResetColor(BotaoGarantias);
        ResetColor(BotaoConsertos);
        ResetColor(BotaoClientes);
        ResetColor(BotaoRelatorios);
    }//GEN-LAST:event_BotaoCadastrosMouseClicked

    private void BotaoCadastrosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrosMouseEntered
        setLblColor(BotaoCadastros);
    }//GEN-LAST:event_BotaoCadastrosMouseEntered

    private void BotaoCadastrosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrosMouseExited
        ResetColor(BotaoCadastros);
    }//GEN-LAST:event_BotaoCadastrosMouseExited

    private void BotaoConsultarCliConsul5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsul5MouseClicked

    private void BotaoConsultarCliConsul5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsul5MouseEntered

    private void BotaoConsultarCliConsul5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul5MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsul5MouseExited

    private void BotaoCadastrarCliConsul5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul5MouseClicked
        AdicionarCadastros.setVisible(true);
        GerenciarCadastros.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliConsul5MouseClicked

    private void BotaoCadastrarCliConsul5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul5MouseEntered
        setLblColor(BotaoCadastrarCliConsul5);
    }//GEN-LAST:event_BotaoCadastrarCliConsul5MouseEntered

    private void BotaoCadastrarCliConsul5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul5MouseExited
        ResetColor(BotaoCadastrarCliConsul5);
    }//GEN-LAST:event_BotaoCadastrarCliConsul5MouseExited

    private void FieldUserGerenciarLoginsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldUserGerenciarLoginsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldUserGerenciarLoginsMouseClicked

    private void FieldUserGerenciarLoginsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldUserGerenciarLoginsKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldUserGerenciarLoginsKeyPressed

    private void BotaoNovoCadastroCliente4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoNovoCadastroCliente4MouseClicked
        PesquisarTabela(FieldUserGerenciarLogins.getText());
    }//GEN-LAST:event_BotaoNovoCadastroCliente4MouseClicked

    private void BotaoNovoCadastroCliente4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoNovoCadastroCliente4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoNovoCadastroCliente4MouseEntered

    private void BotaoNovoCadastroCliente4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoNovoCadastroCliente4MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoNovoCadastroCliente4MouseExited

    private void BotaoConsultarCliConsul8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul8MouseClicked
        AdicionarCadastros.setVisible(false);
        GerenciarCadastros.setVisible(true);
        TableGerenciarLogins.updateUI();
        TableGerenciarLoginss();
        GerenciarLogins.setEnabled(false);
        TravaCamposGerenciarLogins();
    }//GEN-LAST:event_BotaoConsultarCliConsul8MouseClicked

    private void BotaoConsultarCliConsul8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul8MouseEntered
        setLblColor(BotaoConsultarCliConsul8);
    }//GEN-LAST:event_BotaoConsultarCliConsul8MouseEntered

    private void BotaoConsultarCliConsul8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul8MouseExited
        ResetColor(BotaoConsultarCliConsul8);
    }//GEN-LAST:event_BotaoConsultarCliConsul8MouseExited

    private void BotaoCadastrarCliConsul8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliConsul8MouseClicked

    private void BotaoCadastrarCliConsul8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul8MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliConsul8MouseEntered

    private void BotaoCadastrarCliConsul8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul8MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliConsul8MouseExited

    private void FielduserCadastrarloginsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FielduserCadastrarloginsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FielduserCadastrarloginsMouseClicked

    private void FielduserCadastrarloginsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FielduserCadastrarloginsKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FielduserCadastrarloginsKeyPressed

    private void BotaoNovoCadastroCliente5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoNovoCadastroCliente5MouseClicked
        if (FielduserCadastrarlogins.getText().isEmpty() || FieldsenhaCadastrarlogins.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos, por favor!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
                Logins logins = new Logins();
                LoginsDAO loginsdao = new LoginsDAO();

                logins.setusuario(FielduserCadastrarlogins.getText());
                logins.setsenha(FieldsenhaCadastrarlogins.getText());
                logins.settipoconta((String) ComboEscolhalogins.getSelectedItem());
                loginsdao.Cadastrarlogin(logins);

                try {
                    JOptionPane.showMessageDialog(null, "Login cadastrado com sucesso!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                    SalvarCamposCadastroClientes();

                } catch (HeadlessException ex) {
                    JOptionPane.showMessageDialog(null, "Algo de errado ocorreu! Erro: " + ex.getMessage(), "Sistema", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println(ex.getMessage());
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Nome de usuário em uso!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_BotaoNovoCadastroCliente5MouseClicked

    private void BotaoNovoCadastroCliente5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoNovoCadastroCliente5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoNovoCadastroCliente5MouseEntered

    private void BotaoNovoCadastroCliente5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoNovoCadastroCliente5MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoNovoCadastroCliente5MouseExited

    private void BotaoGerenciarCliGer2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliGer2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliGer2MouseClicked

    private void BotaoGerenciarCliGer2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliGer2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliGer2MouseEntered

    private void BotaoGerenciarCliGer2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliGer2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliGer2MouseExited

    private void BotaoConsultarCliGer2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliGer2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliGer2MouseClicked

    private void BotaoConsultarCliGer2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliGer2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliGer2MouseEntered

    private void BotaoConsultarCliGer2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliGer2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliGer2MouseExited

    private void BotaoCadastrarCliGer2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliGer2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliGer2MouseClicked

    private void BotaoCadastrarCliGer2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliGer2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliGer2MouseEntered

    private void BotaoCadastrarCliGer2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliGer2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliGer2MouseExited

    private void ComboEscolhaConsertoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboEscolhaConsertoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboEscolhaConsertoActionPerformed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel6MouseClicked

    private void FieldversenhaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldversenhaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldversenhaMouseClicked

    private void FieldversenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldversenhaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldversenhaKeyPressed

    private void jLabel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseEntered
        Fieldversenha.setText(FieldsenhaCadastrarlogins.getText());
        Fieldversenha.setForeground(new Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 12));
    }//GEN-LAST:event_jLabel6MouseEntered

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited
        Fieldversenha.setForeground(new Color(240, 240, 240));
        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 11));
    }//GEN-LAST:event_jLabel6MouseExited

    private void TableGerenciarLoginsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableGerenciarLoginsMouseClicked
        TableGerenciarLogins.getTableHeader().setReorderingAllowed(false);
        FieldId.setText(TableGerenciarLogins.getValueAt(TableGerenciarLogins.getSelectedRow(), 0).toString());
        FieldUser.setText(TableGerenciarLogins.getValueAt(TableGerenciarLogins.getSelectedRow(), 1).toString());
        FieldSenha.setText(TableGerenciarLogins.getValueAt(TableGerenciarLogins.getSelectedRow(), 2).toString());
        DestravaCamposGerenciarLogins();
    }//GEN-LAST:event_TableGerenciarLoginsMouseClicked

    private void BotaoAlterarGerLoginsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoAlterarGerLoginsActionPerformed
        if (FieldUser.getText().isEmpty() || FieldSenha.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Há campos vazios, preencha esses campos para proceder com a alteração!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
        } else if (JOptionPane.showConfirmDialog(null, "Deseja mesmo alterar o login?", "Sistema", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            Logins logins = new Logins();
            LoginsDAO loginsdao = new LoginsDAO();
            logins.setusuario(FieldUser.getText());
            logins.setsenha(FieldSenha.getText());
            logins.settipoconta((String) ComboTipoConta.getSelectedItem());
            logins.setid((int) TableGerenciarLogins.getValueAt(TableGerenciarLogins.getSelectedRow(), 0));
            loginsdao.AtualizarLogins(logins);
            PesquisarTabela(FieldUserGerenciarLogins.getText());
            TravaCamposGerenciarLogins();
            RedefinirCamposGerenciarLogins();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoAlterarGerLoginsActionPerformed

    private void BotaoExcluirGerLoginsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoExcluirGerLoginsActionPerformed
        if (FieldId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione um login para remoção, caso não queira remover, cancele a ação!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
        } else if (JOptionPane.showConfirmDialog(null, "Deseja mesmo excluir esse login?", "Sistema", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            try {
                Logins logins = new Logins();
                LoginsDAO loginsdao = new LoginsDAO();
                logins.setid(Integer.parseInt(FieldId.getText()));
                loginsdao.Removerlogin(logins);
                try {
                    TravaCamposGerenciarLogins();
                    RedefinirCamposGerenciarLogins();
                    PesquisarTabela(FieldUserGerenciarLogins.getText());
                    JOptionPane.showMessageDialog(null, "Login excluído com sucesso! Verifique se há logins cadastrados antes de sair do sistema.", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Algo de errado ocorreu! Erro: " + e.getMessage(), "Sistema", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println(e.getMessage());
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Não foi possível excluir esse login.");

            }
        }

    }//GEN-LAST:event_BotaoExcluirGerLoginsActionPerformed

    private void BotaoNovoCadastroClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoNovoCadastroClienteActionPerformed
        DestravaCamposCadastroClientes(); // TODO add your handling code here:
    }//GEN-LAST:event_BotaoNovoCadastroClienteActionPerformed

    private void FieldsenhaCadastrarloginsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldsenhaCadastrarloginsKeyTyped
        Fieldversenha.setText(FieldsenhaCadastrarlogins.getText());
        Fieldversenha.setForeground(new Color(240, 240, 240));
        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 11));
    }//GEN-LAST:event_FieldsenhaCadastrarloginsKeyTyped

    private void BotaoAlterarGerLogins1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoAlterarGerLogins1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoAlterarGerLogins1ActionPerformed

    private void BotaoCancelarGerLoginsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoCancelarGerLoginsActionPerformed
        TravaCamposGerenciarLogins();
    }//GEN-LAST:event_BotaoCancelarGerLoginsActionPerformed

    private void Botao2ViaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Botao2ViaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Botao2ViaMouseClicked

    private void Botao2ViaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Botao2ViaActionPerformed
        // TODO add your handling code here:
        if (FieldIDGerenciarGarantia.getText().isEmpty()) {
         
            JOptionPane.showMessageDialog(null, "Selecione uma garantia para a reimpresão, caso não queira reimprimir, cancele a ação!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
       
        }
             
        RelatorioDAO rel = new RelatorioDAO();
     
        int cod =  Integer.parseInt(FieldIDGerenciarGarantia.getText());
        rel.RelatorioGar(cod);
        
    }//GEN-LAST:event_Botao2ViaActionPerformed

    private void ComboGerenciaEscolhaConsertoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboGerenciaEscolhaConsertoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboGerenciaEscolhaConsertoActionPerformed

    public void setLblColor(JLabel lbl) {
        lbl.setBackground(new Color(220, 220, 220));
    }

    public void ResetColor(JLabel lbl) {
        lbl.setBackground(new Color(230, 230, 230));
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AdicionarCadastros;
    private javax.swing.JButton Botao2Via;
    private javax.swing.JButton BotaoAlterarGerLogins;
    private javax.swing.JButton BotaoAlterarGerLogins1;
    private javax.swing.JButton BotaoAlterarGerenciarGarantia;
    private javax.swing.JButton BotaoAlterarGerenciarGarantia1;
    private javax.swing.JButton BotaoAlterarGerenciarServicos;
    private javax.swing.JButton BotaoBuscarConsultaCliente;
    private javax.swing.JButton BotaoBuscarConsultaCliente1;
    private javax.swing.JButton BotaoBuscarConsultarGarantias;
    private javax.swing.JButton BotaoBuscarConsultarGarantias1;
    private javax.swing.JButton BotaoBuscarConsultarGarantias2;
    private javax.swing.JButton BotaoBuscarConsultarGarantias3;
    private javax.swing.JButton BotaoBuscarGarantias;
    private javax.swing.JButton BotaoBuscarGerenciarServicos;
    private javax.swing.JButton BotaoBuscarGerenciarServicos1;
    private javax.swing.JLabel BotaoCadastrarCliConsul;
    private javax.swing.JLabel BotaoCadastrarCliConsul1;
    private javax.swing.JLabel BotaoCadastrarCliConsul2;
    private javax.swing.JLabel BotaoCadastrarCliConsul3;
    private javax.swing.JLabel BotaoCadastrarCliConsul4;
    private javax.swing.JLabel BotaoCadastrarCliConsul5;
    private javax.swing.JLabel BotaoCadastrarCliConsul6;
    private javax.swing.JLabel BotaoCadastrarCliConsul7;
    private javax.swing.JLabel BotaoCadastrarCliConsul8;
    private javax.swing.JLabel BotaoCadastrarCliGer;
    private javax.swing.JLabel BotaoCadastrarCliGer1;
    private javax.swing.JLabel BotaoCadastrarCliGer2;
    private javax.swing.JLabel BotaoCadastros;
    private javax.swing.JButton BotaoCancelarCadastroCliente;
    private javax.swing.JButton BotaoCancelarCadastroConserto;
    private javax.swing.JButton BotaoCancelarGarantia;
    private javax.swing.JButton BotaoCancelarGerLogins;
    private javax.swing.JButton BotaoCancelarGerenciarGarantia;
    private javax.swing.JButton BotaoCancelarGerenciarServicos;
    private javax.swing.JButton BotaoCancelarGerenciarServicos2;
    private javax.swing.JLabel BotaoClientes;
    private javax.swing.JLabel BotaoClientes2;
    private javax.swing.JLabel BotaoClientes3;
    private javax.swing.JLabel BotaoClientes4;
    private javax.swing.JLabel BotaoConsertos;
    private javax.swing.JLabel BotaoConsultarCli;
    private javax.swing.JLabel BotaoConsultarCli1;
    private javax.swing.JLabel BotaoConsultarCli2;
    private javax.swing.JLabel BotaoConsultarCliConsul;
    private javax.swing.JLabel BotaoConsultarCliConsul1;
    private javax.swing.JLabel BotaoConsultarCliConsul2;
    private javax.swing.JLabel BotaoConsultarCliConsul3;
    private javax.swing.JLabel BotaoConsultarCliConsul4;
    private javax.swing.JLabel BotaoConsultarCliConsul5;
    private javax.swing.JLabel BotaoConsultarCliConsul6;
    private javax.swing.JLabel BotaoConsultarCliConsul7;
    private javax.swing.JLabel BotaoConsultarCliConsul8;
    private javax.swing.JLabel BotaoConsultarCliGer;
    private javax.swing.JLabel BotaoConsultarCliGer1;
    private javax.swing.JLabel BotaoConsultarCliGer2;
    private javax.swing.JButton BotaoExcluirGerLogins;
    private javax.swing.JButton BotaoExcluirGerenciarGarantia;
    private javax.swing.JButton BotaoExcluirGerenciarGarantia1;
    private javax.swing.JButton BotaoExcluirGerenciarServicos;
    private javax.swing.JLabel BotaoGarantias;
    private javax.swing.JLabel BotaoGerenciarCli;
    private javax.swing.JLabel BotaoGerenciarCli1;
    private javax.swing.JLabel BotaoGerenciarCli2;
    private javax.swing.JLabel BotaoGerenciarCliConsul;
    private javax.swing.JLabel BotaoGerenciarCliConsul1;
    private javax.swing.JLabel BotaoGerenciarCliConsul2;
    private javax.swing.JLabel BotaoGerenciarCliConsul3;
    private javax.swing.JLabel BotaoGerenciarCliConsul4;
    private javax.swing.JLabel BotaoGerenciarCliConsul6;
    private javax.swing.JLabel BotaoGerenciarCliConsul7;
    private javax.swing.JLabel BotaoGerenciarCliGer;
    private javax.swing.JLabel BotaoGerenciarCliGer1;
    private javax.swing.JLabel BotaoGerenciarCliGer2;
    private javax.swing.JButton BotaoNovoCadastroCliente;
    private javax.swing.JLabel BotaoNovoCadastroCliente4;
    private javax.swing.JLabel BotaoNovoCadastroCliente5;
    private javax.swing.JButton BotaoNovoCadastroConserto;
    private javax.swing.JButton BotaoNovoGarantia;
    private javax.swing.JButton BotaoRelatorioCliente;
    private javax.swing.JLabel BotaoRelatorios;
    private javax.swing.JButton BotaoSalvarCadastroCliente;
    private javax.swing.JButton BotaoSalvarCadastroConserto;
    private javax.swing.JButton BotaoSalvarGarantia;
    private javax.swing.JPanel CadastrarClientes;
    private javax.swing.JPanel CadastrarGarantias;
    private javax.swing.JPanel CadastrarServicos;
    private javax.swing.JFormattedTextField CampoDataFormatada;
    private javax.swing.JFormattedTextField CampoGerenciaDataFormatada;
    private javax.swing.JTextField CampoNome;
    private javax.swing.JComboBox<String> ComboEscolhaConserto;
    private javax.swing.JComboBox<String> ComboEscolhalogins;
    private javax.swing.JComboBox<String> ComboGerenciaEscolhaConserto;
    private javax.swing.JComboBox<String> ComboOrdenaGarantia;
    private javax.swing.JComboBox<String> ComboOrdenaGarantia1;
    private javax.swing.JComboBox<String> ComboTipoConta;
    private javax.swing.JPanel ConsultarClientes;
    private javax.swing.JPanel ConsultarGarantias;
    private javax.swing.JPanel ConsultarServicos;
    private javax.swing.JPanel DadosAlteraClientes;
    private javax.swing.JPanel DadosAlteraGarantia;
    private javax.swing.JPanel DadosAlteraServicos;
    private javax.swing.JTextField FieldCPFGerenciarClientes;
    private javax.swing.JTextField FieldCadastroCPFCliente;
    private javax.swing.JTextField FieldCadastroCidadeCliente;
    private javax.swing.JTextField FieldCadastroEmailCliente;
    private javax.swing.JTextField FieldCadastroEnderecoCliente;
    private javax.swing.JTextField FieldCadastroNomeCliente;
    private javax.swing.JFormattedTextField FieldCadastroTelefoneCliente;
    private javax.swing.JTextField FieldCidadeGerenciarClientes;
    private javax.swing.JTextField FieldConsultaCPFCliente;
    private javax.swing.JTextField FieldConsultaDescricaoGarantias;
    private javax.swing.JTextField FieldConsultaNomeCliente;
    private javax.swing.JTextField FieldConsultaNomeGarantia;
    private javax.swing.JTextField FieldDuracaoCadastrarConsertos;
    private javax.swing.JTextField FieldDuracaoGerenciarConsertos;
    private javax.swing.JTextField FieldEmailGerenciarClientes;
    private javax.swing.JTextField FieldEnderecoGerenciarClientes;
    private javax.swing.JTextField FieldGerenciarCPFCliente;
    private javax.swing.JTextField FieldGerenciarNomeCliente;
    private javax.swing.JTextField FieldIDGerenciarClientes;
    private javax.swing.JTextField FieldIDGerenciarConsertos;
    private javax.swing.JTextField FieldIDGerenciarGarantia;
    private javax.swing.JTextField FieldId;
    private javax.swing.JTextField FieldNomeCadastrarConsertos;
    private javax.swing.JTextField FieldNomeGerenciarClientes;
    private javax.swing.JTextField FieldNomeGerenciarConsertos;
    private javax.swing.JTextField FieldNomeGerenciarGarantia;
    private javax.swing.JTextField FieldNomeGerenciarServicos;
    private javax.swing.JTextField FieldNomeGerenciarServicos1;
    private javax.swing.JTextField FieldSenha;
    private javax.swing.JFormattedTextField FieldTelefoneGerenciarClientes;
    private javax.swing.JTextField FieldUser;
    private javax.swing.JTextField FieldUserGerenciarLogins;
    private javax.swing.JPasswordField FieldsenhaCadastrarlogins;
    private javax.swing.JTextField FielduserCadastrarlogins;
    private javax.swing.JTextField Fieldversenha;
    private javax.swing.JPanel GerenciarCadastros;
    private javax.swing.JPanel GerenciarClientes;
    private javax.swing.JPanel GerenciarGarantias;
    private javax.swing.JPanel GerenciarLogins;
    private javax.swing.JPanel GerenciarServicos;
    private javax.swing.JPanel PaneCadastros;
    private javax.swing.JPanel PaneClientes;
    private javax.swing.JPanel PaneContatarCli;
    private javax.swing.JPanel PaneGarantias;
    private javax.swing.JPanel PaneMae;
    private javax.swing.JPanel PaneRelatorios;
    private javax.swing.JPanel PaneServicos;
    private javax.swing.JPanel RelatorioClientes;
    private javax.swing.JPanel RelatorioGerenciar;
    private javax.swing.JPanel RelatorioServicos;
    private javax.swing.JPanel SideBoard;
    private javax.swing.JTable TabelaConsultaManutencao;
    private javax.swing.JTable TableConsultaCliente;
    private javax.swing.JTable TableConsultaGarantia;
    private javax.swing.JTable TableGerenciarCliente;
    private javax.swing.JTable TableGerenciarGarantia;
    private javax.swing.JTable TableGerenciarLogins;
    private javax.swing.JTable TableGerenciarServicos;
    private javax.swing.JTextField TxtNomeCtt;
    private javax.swing.JTextField TxtTelCtt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator24;
    private javax.swing.JSeparator jSeparator25;
    private javax.swing.JSeparator jSeparator26;
    private javax.swing.JSeparator jSeparator27;
    private javax.swing.JSeparator jSeparator28;
    private javax.swing.JSeparator jSeparator29;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator30;
    private javax.swing.JSeparator jSeparator31;
    private javax.swing.JSeparator jSeparator32;
    private javax.swing.JSeparator jSeparator33;
    private javax.swing.JSeparator jSeparator34;
    private javax.swing.JSeparator jSeparator35;
    private javax.swing.JSeparator jSeparator36;
    private javax.swing.JSeparator jSeparator37;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblCPF1;
    private javax.swing.JLabel lblCPF2;
    private javax.swing.JLabel lblCPF3;
    private javax.swing.JLabel lblCPF4;
    private javax.swing.JLabel lblCPF5;
    private javax.swing.JLabel lblCPF6;
    private javax.swing.JLabel lblCPF7;
    private javax.swing.JLabel lblCPF8;
    private javax.swing.JLabel lblConserto;
    private javax.swing.JLabel lblConserto1;
    private javax.swing.JLabel lblData;
    private javax.swing.JLabel lblData1;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblNome1;
    private javax.swing.JLabel lblNome10;
    private javax.swing.JLabel lblNome11;
    private javax.swing.JLabel lblNome12;
    private javax.swing.JLabel lblNome13;
    private javax.swing.JLabel lblNome14;
    private javax.swing.JLabel lblNome15;
    private javax.swing.JLabel lblNome17;
    private javax.swing.JLabel lblNome18;
    private javax.swing.JLabel lblNome19;
    private javax.swing.JLabel lblNome2;
    private javax.swing.JLabel lblNome20;
    private javax.swing.JLabel lblNome21;
    private javax.swing.JLabel lblNome22;
    private javax.swing.JLabel lblNome23;
    private javax.swing.JLabel lblNome24;
    private javax.swing.JLabel lblNome26;
    private javax.swing.JLabel lblNome27;
    private javax.swing.JLabel lblNome28;
    private javax.swing.JLabel lblNome29;
    private javax.swing.JLabel lblNome3;
    private javax.swing.JLabel lblNome30;
    private javax.swing.JLabel lblNome4;
    private javax.swing.JLabel lblNome5;
    private javax.swing.JLabel lblNome6;
    private javax.swing.JLabel lblNome7;
    private javax.swing.JLabel lblNome8;
    private javax.swing.JLabel lblNome9;
    // End of variables declaration//GEN-END:variables
}
