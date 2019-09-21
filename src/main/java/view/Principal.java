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
import dao.ManutencaoDAO;
import dao.clienteDAO;
import dao.clienteDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.sql.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Cliente;
import model.Garantia;
import model.Manutencao;

/**
 *
 * @author Michel
 */
public class Principal extends javax.swing.JFrame {

    PreparedStatement stmt = null;
    ResultSet rs = null;

    List<Cliente> ListaCliente;
    List<Cliente> ListaBuscaCliente;

    List<Garantia> ListaGarantia;
    List<Garantia> ListaBuscaGarantia;

    List<Manutencao> ListaManutencao;
    List<Manutencao> ListaBuscaManutencao;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Principal() {
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

        BotaoSalvarCadastroConserto.setEnabled(false);
        BotaoCancelarCadastroConserto.setEnabled(false);
    }

    public void DestravaCamposCadastroServicos() {
        FieldNomeCadastrarConsertos.setEnabled(true);
        FieldDuracaoCadastrarConsertos.setEnabled(true);
        lblNome5.setEnabled(true);
        lblNome13.setEnabled(true);

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

        lblNome.setEnabled(false);
        lblData.setEnabled(false);
        lblConserto.setEnabled(false);

        BotaoSalvarGarantia.setEnabled(false);
        BotaoCancelarGarantia.setEnabled(false);

    }

    public void DestravaCamposCadastroGarantia() {
        CampoNome.setEnabled(true);
        CampoDataFormatada.setEnabled(true);
        ComboEscolhaConserto.setEnabled(true);
        BotaoSalvarGarantia.setEnabled(true);

        lblNome.setEnabled(true);
        lblData.setEnabled(true);
        lblConserto.setEnabled(true);

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

    }

    public void DestravaCamposGerenciarServicos() {
        FieldNomeGerenciarConsertos.setEnabled(true);
        FieldDuracaoGerenciarConsertos.setEnabled(true);

        lblNome14.setEnabled(true);
        lblNome15.setEnabled(true);

        BotaoAlterarGerenciarServicos.setEnabled(true);
        BotaoExcluirGerenciarServicos.setEnabled(true);
        BotaoCancelarGerenciarServicos.setEnabled(true);
    }

    public void SalvarCamposGerenciarServicos() {
        FieldNomeGerenciarConsertos.setText("");
        FieldDuracaoGerenciarConsertos.setText("");
        FieldIDGerenciarConsertos.setText("");

        TravaCamposGerenciarServicos();
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
    }

    public void SalvarCamposGarenciarGarantia() {
        FieldNomeGerenciarGarantia.setText("");
        CampoGerenciaDataFormatada.setText("");
        ComboGerenciaEscolhaConserto.setSelectedItem(null);

        TravaCamposGarenciarGarantia();
    }

    //-----------------------------------
    public void TravaCamposGerenciarClientes() {
        FieldNomeGerenciarClientes.setEnabled(false);
        FieldCPFGerenciarClientes.setEnabled(false);
        FieldTelefoneGerenciarClientes.setEnabled(false);
        FieldCidadeGerenciarClientes.setEnabled(false);
        FieldEnderecoGerenciarClientes.setEnabled(false);
        FieldEmailGerenciarClientes.setEnabled(false);

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
            String SQL = "Select duracao_garantia from cadastros.manutencao where descricao = ?";
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
            String SQL = "Select * from cadastros.manutencao order by id asc";
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
            String SQL = "Select * from cadastros.manutencao order by id asc";
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
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
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
        Manutencao man = new Manutencao();
        ManutencaoDAO mandao = new ManutencaoDAO();
        try {
            ListaManutencao = mandao.ListaManutencao();
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        String dados[][] = new String[ListaManutencao.size()][3];
        int i = 0;
        for (Manutencao manu : ListaManutencao) {
            dados[i][0] = String.valueOf(manu.getId());
            dados[i][1] = manu.getDescricao();
            dados[i][2] = String.valueOf(manu.getDuracao());
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
        Manutencao man = new Manutencao();
        String dados[][] = new String[ListaBuscaManutencao.size()][3];
        int i = 0;
        for (Manutencao manu : ListaBuscaManutencao) {
            dados[i][0] = String.valueOf(manu.getId());
            dados[i][1] = manu.getDescricao();
            dados[i][2] = String.valueOf(manu.getDuracao());
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
        Manutencao man = new Manutencao();
        ManutencaoDAO mandao = new ManutencaoDAO();
        try {
            ListaManutencao = mandao.ListaManutencao();
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        String dados[][] = new String[ListaManutencao.size()][3];
        int i = 0;
        for (Manutencao manu : ListaManutencao) {
            dados[i][0] = String.valueOf(manu.getId());
            dados[i][1] = manu.getDescricao();
            dados[i][2] = String.valueOf(manu.getDuracao());
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
        Manutencao man = new Manutencao();
        String dados[][] = new String[ListaBuscaManutencao.size()][3];
        int i = 0;
        for (Manutencao manu : ListaBuscaManutencao) {
            dados[i][0] = String.valueOf(manu.getId());
            dados[i][1] = manu.getDescricao();
            dados[i][2] = String.valueOf(manu.getDuracao());
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SideBoard = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        BotaoGarantias = new javax.swing.JLabel();
        BotaoClientes = new javax.swing.JLabel();
        BotaoConsertos = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
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
        BotaoNovoCadastroCliente = new javax.swing.JButton();
        lblNome9 = new javax.swing.JLabel();
        lblCPF4 = new javax.swing.JLabel();
        FieldCadastroCidadeCliente = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        BotaoClientes1 = new javax.swing.JLabel();
        BotaoClientes3 = new javax.swing.JLabel();
        BotaoClientes2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
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
        jPanel5 = new javax.swing.JPanel();
        BotaoClientes4 = new javax.swing.JLabel();
        BotaoClientes5 = new javax.swing.JLabel();
        BotaoClientes6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
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
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        BotaoClientes7 = new javax.swing.JLabel();
        BotaoClientes8 = new javax.swing.JLabel();
        BotaoClientes9 = new javax.swing.JLabel();
        PaneGarantias = new javax.swing.JPanel();
        CadastrarGarantias = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        CampoNome = new javax.swing.JTextField();
        BotaoSalvarGarantia = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblNome = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        BotaoClientes10 = new javax.swing.JLabel();
        BotaoClientes11 = new javax.swing.JLabel();
        BotaoClientes12 = new javax.swing.JLabel();
        lblConserto = new javax.swing.JLabel();
        BotaoCancelarGarantia = new javax.swing.JButton();
        ComboEscolhaConserto = new javax.swing.JComboBox<>();
        BotaoNovoGarantia = new javax.swing.JButton();
        CampoDataFormatada = new javax.swing.JFormattedTextField();
        jSeparator4 = new javax.swing.JSeparator();
        lblData = new javax.swing.JLabel();
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
        jPanel10 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        BotaoClientes13 = new javax.swing.JLabel();
        BotaoClientes14 = new javax.swing.JLabel();
        BotaoClientes15 = new javax.swing.JLabel();
        GerenciarServicos1 = new javax.swing.JPanel();
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
        lblCPF3 = new javax.swing.JLabel();
        ComboOrdenaGarantia1 = new javax.swing.JComboBox<>();
        jPanel12 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        BotaoClientes16 = new javax.swing.JLabel();
        BotaoClientes17 = new javax.swing.JLabel();
        BotaoClientes18 = new javax.swing.JLabel();
        PaneContatarCli = new javax.swing.JPanel();
        lblCPF7 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        BotaoBuscarConsultarGarantias2 = new javax.swing.JButton();
        lblCPF8 = new javax.swing.JLabel();
        TxtNomeCtt = new javax.swing.JTextField();
        TxtTelCtt = new javax.swing.JTextField();
        BotaoBuscarConsultarGarantias3 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        BotaoClientes19 = new javax.swing.JLabel();
        BotaoClientes20 = new javax.swing.JLabel();
        BotaoClientes21 = new javax.swing.JLabel();
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
        jPanel16 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        BotaoClientes22 = new javax.swing.JLabel();
        BotaoClientes23 = new javax.swing.JLabel();
        BotaoClientes24 = new javax.swing.JLabel();
        ConsultarServicos = new javax.swing.JPanel();
        FieldConsultaDescricaoGarantias = new javax.swing.JTextField();
        lblNome7 = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        TabelaConsultaManutencao = new javax.swing.JTable();
        BotaoBuscarGarantias = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        BotaoClientes25 = new javax.swing.JLabel();
        BotaoClientes26 = new javax.swing.JLabel();
        BotaoClientes27 = new javax.swing.JLabel();
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
        jPanel20 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        BotaoClientes28 = new javax.swing.JLabel();
        BotaoClientes29 = new javax.swing.JLabel();
        BotaoClientes30 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PROJETOP Garantias");
        setBackground(new java.awt.Color(230, 230, 230));
        setIconImages(null);
        setPreferredSize(new java.awt.Dimension(1364, 750));

        SideBoard.setBackground(new java.awt.Color(230, 230, 230));
        SideBoard.setForeground(new java.awt.Color(230, 230, 230));
        SideBoard.setToolTipText("");
        SideBoard.setMaximumSize(new java.awt.Dimension(300, 400));

        jSeparator1.setBackground(new java.awt.Color(25, 25, 112));
        jSeparator1.setForeground(new java.awt.Color(230, 230, 230));

        BotaoGarantias.setBackground(new java.awt.Color(230, 230, 230));
        BotaoGarantias.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoGarantias.setForeground(new java.awt.Color(25, 25, 112));
        BotaoGarantias.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoGarantias.setText("Garantias");
        BotaoGarantias.setMaximumSize(new java.awt.Dimension(139, 25));
        BotaoGarantias.setMinimumSize(new java.awt.Dimension(139, 25));
        BotaoGarantias.setOpaque(true);
        BotaoGarantias.setPreferredSize(new java.awt.Dimension(139, 25));
        BotaoGarantias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoGarantiasMouseClicked(evt);
            }
        });

        BotaoClientes.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes.setText("Clientes");
        BotaoClientes.setOpaque(true);
        BotaoClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientesMouseClicked(evt);
            }
        });

        BotaoConsertos.setBackground(new java.awt.Color(230, 230, 230));
        BotaoConsertos.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoConsertos.setForeground(new java.awt.Color(23, 23, 112));
        BotaoConsertos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoConsertos.setText("Categoria de Garantias");
        BotaoConsertos.setMaximumSize(new java.awt.Dimension(139, 25));
        BotaoConsertos.setMinimumSize(new java.awt.Dimension(139, 25));
        BotaoConsertos.setOpaque(true);
        BotaoConsertos.setPreferredSize(new java.awt.Dimension(139, 25));
        BotaoConsertos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoConsertosMouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 30)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(25, 25, 112));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("GARANTIAS");

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 30)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(25, 25, 112));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("PROTECH");

        javax.swing.GroupLayout SideBoardLayout = new javax.swing.GroupLayout(SideBoard);
        SideBoard.setLayout(SideBoardLayout);
        SideBoardLayout.setHorizontalGroup(
            SideBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BotaoClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(BotaoGarantias, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
            .addComponent(BotaoConsertos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(SideBoardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
            .addGroup(SideBoardLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(SideBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SideBoardLayout.setVerticalGroup(
            SideBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SideBoardLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(BotaoClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoGarantias, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoConsertos, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jSeparator14.setForeground(new java.awt.Color(0, 0, 0));

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

        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));

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

        jSeparator17.setForeground(new java.awt.Color(0, 0, 0));

        BotaoNovoCadastroCliente.setBackground(new java.awt.Color(230, 230, 230));
        BotaoNovoCadastroCliente.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoNovoCadastroCliente.setText("Novo");
        BotaoNovoCadastroCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoNovoCadastroCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoNovoCadastroClienteActionPerformed(evt);
            }
        });

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

        jPanel4.setBackground(new java.awt.Color(230, 230, 230));

        BotaoClientes1.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes1.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes1.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes1.setText("Gerenciar");
        BotaoClientes1.setOpaque(true);
        BotaoClientes1.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes1MouseClicked(evt);
            }
        });

        BotaoClientes3.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes3.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes3.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes3.setText("Consultar");
        BotaoClientes3.setOpaque(true);
        BotaoClientes3.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes3MouseClicked(evt);
            }
        });

        BotaoClientes2.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes2.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes2.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes2.setText("Cadastrar");
        BotaoClientes2.setOpaque(true);
        BotaoClientes2.setPreferredSize(new java.awt.Dimension(135, 50));
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
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoClientes1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(25, 25, 112));
        jLabel1.setText("CLIENTES");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(443, 443, 443))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout CadastrarClientesLayout = new javax.swing.GroupLayout(CadastrarClientes);
        CadastrarClientes.setLayout(CadastrarClientesLayout);
        CadastrarClientesLayout.setHorizontalGroup(
            CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarClientesLayout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastrarClientesLayout.createSequentialGroup()
                        .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNome8)
                            .addComponent(FieldCadastroEmailCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(535, 535, 535))
                    .addGroup(CadastrarClientesLayout.createSequentialGroup()
                        .addComponent(BotaoNovoCadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BotaoSalvarCadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BotaoCancelarCadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblNome3)
                        .addComponent(FieldCadastroNomeCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 647, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CadastrarClientesLayout.createSequentialGroup()
                        .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNome9)
                            .addComponent(FieldCadastroCidadeCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator21, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
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
                                    .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(32, Short.MAX_VALUE))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        CadastrarClientesLayout.setVerticalGroup(
            CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarClientesLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotaoSalvarCadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoNovoCadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoCancelarCadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(90, 90, 90))
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

        jPanel5.setBackground(new java.awt.Color(230, 230, 230));

        BotaoClientes4.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes4.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes4.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes4.setText("Gerenciar");
        BotaoClientes4.setOpaque(true);
        BotaoClientes4.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes4MouseClicked(evt);
            }
        });

        BotaoClientes5.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes5.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes5.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes5.setText("Consultar");
        BotaoClientes5.setOpaque(true);
        BotaoClientes5.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes5MouseClicked(evt);
            }
        });

        BotaoClientes6.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes6.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes6.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes6.setText("Cadastrar");
        BotaoClientes6.setOpaque(true);
        BotaoClientes6.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoClientes4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(25, 25, 112));
        jLabel2.setText("CLIENTES");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(443, 443, 443))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(90, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ConsultarClientesLayout.setVerticalGroup(
            ConsultarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConsultarClientesLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
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
                .addGap(0, 12, Short.MAX_VALUE))
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

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(25, 25, 112));
        jLabel3.setText("CLIENTES");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(443, 443, 443))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(230, 230, 230));

        BotaoClientes7.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes7.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes7.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes7.setText("Gerenciar");
        BotaoClientes7.setOpaque(true);
        BotaoClientes7.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes7MouseClicked(evt);
            }
        });

        BotaoClientes8.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes8.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes8.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes8.setText("Consultar");
        BotaoClientes8.setOpaque(true);
        BotaoClientes8.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes8MouseClicked(evt);
            }
        });

        BotaoClientes9.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes9.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes9.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes9.setText("Cadastrar");
        BotaoClientes9.setOpaque(true);
        BotaoClientes9.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes9MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoClientes7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        GerenciarClientesLayout.setVerticalGroup(
            GerenciarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GerenciarClientesLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(25, 25, 112));
        jLabel4.setText("GARANTIAS");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(443, 443, 443))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(81, Short.MAX_VALUE))
        );

        lblNome.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblNome.setText("Nome do cliente");

        jPanel9.setBackground(new java.awt.Color(230, 230, 230));

        BotaoClientes10.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes10.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes10.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes10.setText("Gerenciar");
        BotaoClientes10.setOpaque(true);
        BotaoClientes10.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes10MouseClicked(evt);
            }
        });

        BotaoClientes11.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes11.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes11.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes11.setText("Consultar");
        BotaoClientes11.setOpaque(true);
        BotaoClientes11.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes11MouseClicked(evt);
            }
        });

        BotaoClientes12.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes12.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes12.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes12.setText("Cadastrar");
        BotaoClientes12.setOpaque(true);
        BotaoClientes12.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes12MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoClientes10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

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

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));

        lblData.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblData.setText("Data do conserto");

        javax.swing.GroupLayout CadastrarGarantiasLayout = new javax.swing.GroupLayout(CadastrarGarantias);
        CadastrarGarantias.setLayout(CadastrarGarantiasLayout);
        CadastrarGarantiasLayout.setHorizontalGroup(
            CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarGarantiasLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addGap(93, 93, 93))
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        CadastrarGarantiasLayout.setVerticalGroup(
            CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarGarantiasLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 273, Short.MAX_VALUE)
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

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(25, 25, 112));
        jLabel5.setText("GARANTIAS");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(395, 395, 395))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        jPanel11.setBackground(new java.awt.Color(230, 230, 230));

        BotaoClientes13.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes13.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes13.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes13.setText("Gerenciar");
        BotaoClientes13.setOpaque(true);
        BotaoClientes13.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes13MouseClicked(evt);
            }
        });

        BotaoClientes14.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes14.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes14.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes14.setText("Consultar");
        BotaoClientes14.setOpaque(true);
        BotaoClientes14.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes14MouseClicked(evt);
            }
        });

        BotaoClientes15.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes15.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes15.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes15.setText("Cadastrar");
        BotaoClientes15.setOpaque(true);
        BotaoClientes15.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes15MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoClientes13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout ConsultarGarantiasLayout = new javax.swing.GroupLayout(ConsultarGarantias);
        ConsultarGarantias.setLayout(ConsultarGarantiasLayout);
        ConsultarGarantiasLayout.setHorizontalGroup(
            ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
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
                        .addContainerGap(42, Short.MAX_VALUE))))
        );
        ConsultarGarantiasLayout.setVerticalGroup(
            ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(BotaoBuscarConsultarGarantias1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );

        PaneGarantias.add(ConsultarGarantias, "card4");

        GerenciarServicos1.setPreferredSize(new java.awt.Dimension(1190, 451));

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 343, Short.MAX_VALUE)
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
                    .addComponent(BotaoAlterarGerenciarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblCPF3.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblCPF3.setText("Filtrar por:");

        ComboOrdenaGarantia1.setBackground(new java.awt.Color(240, 240, 240));
        ComboOrdenaGarantia1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        ComboOrdenaGarantia1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODAS", "VIGENTES", "VENCIDAS" }));
        ComboOrdenaGarantia1.setBorder(null);

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(25, 25, 112));
        jLabel6.setText("GARANTIAS");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(395, 395, 395))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(230, 230, 230));

        BotaoClientes16.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes16.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes16.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes16.setText("Gerenciar");
        BotaoClientes16.setOpaque(true);
        BotaoClientes16.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes16MouseClicked(evt);
            }
        });

        BotaoClientes17.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes17.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes17.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes17.setText("Consultar");
        BotaoClientes17.setOpaque(true);
        BotaoClientes17.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes17MouseClicked(evt);
            }
        });

        BotaoClientes18.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes18.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes18.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes18.setText("Cadastrar");
        BotaoClientes18.setOpaque(true);
        BotaoClientes18.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes18MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoClientes16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout GerenciarServicos1Layout = new javax.swing.GroupLayout(GerenciarServicos1);
        GerenciarServicos1.setLayout(GerenciarServicos1Layout);
        GerenciarServicos1Layout.setHorizontalGroup(
            GerenciarServicos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GerenciarServicos1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(GerenciarServicos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(GerenciarServicos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(DadosAlteraGarantia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(GerenciarServicos1Layout.createSequentialGroup()
                        .addGroup(GerenciarServicos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNome17)
                            .addComponent(FieldNomeGerenciarServicos1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator25, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(GerenciarServicos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCPF3)
                            .addComponent(ComboOrdenaGarantia1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addComponent(BotaoBuscarGerenciarServicos1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(59, Short.MAX_VALUE))
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        GerenciarServicos1Layout.setVerticalGroup(
            GerenciarServicos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GerenciarServicos1Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(GerenciarServicos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(GerenciarServicos1Layout.createSequentialGroup()
                        .addGroup(GerenciarServicos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNome17)
                            .addComponent(lblCPF3))
                        .addGroup(GerenciarServicos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(GerenciarServicos1Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(ComboOrdenaGarantia1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GerenciarServicos1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(FieldNomeGerenciarServicos1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator25, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BotaoBuscarGerenciarServicos1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DadosAlteraGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        PaneGarantias.add(GerenciarServicos1, "card5");

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
        TxtNomeCtt.setText("Nome");
        TxtNomeCtt.setBorder(null);
        TxtNomeCtt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtNomeCttActionPerformed(evt);
            }
        });

        TxtTelCtt.setBackground(new java.awt.Color(240, 240, 240));
        TxtTelCtt.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        TxtTelCtt.setText("Telefone");
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

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(25, 25, 112));
        jLabel7.setText("GARANTIAS");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(395, 395, 395))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        jPanel15.setBackground(new java.awt.Color(230, 230, 230));

        BotaoClientes19.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes19.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes19.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes19.setText("Gerenciar");
        BotaoClientes19.setOpaque(true);
        BotaoClientes19.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes19MouseClicked(evt);
            }
        });

        BotaoClientes20.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes20.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes20.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes20.setText("Consultar");
        BotaoClientes20.setOpaque(true);
        BotaoClientes20.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes20MouseClicked(evt);
            }
        });

        BotaoClientes21.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes21.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes21.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes21.setText("Cadastrar");
        BotaoClientes21.setOpaque(true);
        BotaoClientes21.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes21MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoClientes19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout PaneContatarCliLayout = new javax.swing.GroupLayout(PaneContatarCli);
        PaneContatarCli.setLayout(PaneContatarCliLayout);
        PaneContatarCliLayout.setHorizontalGroup(
            PaneContatarCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PaneContatarCliLayout.createSequentialGroup()
                .addContainerGap(71, Short.MAX_VALUE)
                .addComponent(lblCPF7)
                .addGap(18, 18, 18)
                .addGroup(PaneContatarCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PaneContatarCliLayout.createSequentialGroup()
                        .addComponent(BotaoBuscarConsultarGarantias2, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BotaoBuscarConsultarGarantias3, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PaneContatarCliLayout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(lblCPF8)
                        .addGap(18, 18, 18)
                        .addGroup(PaneContatarCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TxtTelCtt)
                            .addComponent(TxtNomeCtt, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(87, 87, 87))
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PaneContatarCliLayout.setVerticalGroup(
            PaneContatarCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PaneContatarCliLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PaneContatarCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCPF7)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PaneContatarCliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblCPF8)
                        .addComponent(TxtNomeCtt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PaneContatarCliLayout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(TxtTelCtt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(90, 90, 90)
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

        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(25, 25, 112));
        jLabel9.setText("CATEGORIAS");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(395, 395, 395))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        jPanel17.setBackground(new java.awt.Color(230, 230, 230));

        BotaoClientes22.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes22.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes22.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes22.setText("Gerenciar");
        BotaoClientes22.setOpaque(true);
        BotaoClientes22.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes22MouseClicked(evt);
            }
        });

        BotaoClientes23.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes23.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes23.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes23.setText("Consultar");
        BotaoClientes23.setOpaque(true);
        BotaoClientes23.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes23MouseClicked(evt);
            }
        });

        BotaoClientes24.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes24.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes24.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes24.setText("Cadastrar");
        BotaoClientes24.setOpaque(true);
        BotaoClientes24.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes24MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoClientes22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        CadastrarServicosLayout.setVerticalGroup(
            CadastrarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarServicosLayout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 224, Short.MAX_VALUE)
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

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(25, 25, 112));
        jLabel10.setText("CATEGORIAS");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(395, 395, 395))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        jPanel19.setBackground(new java.awt.Color(230, 230, 230));

        BotaoClientes25.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes25.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes25.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes25.setText("Gerenciar");
        BotaoClientes25.setOpaque(true);
        BotaoClientes25.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes25MouseClicked(evt);
            }
        });

        BotaoClientes26.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes26.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes26.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes26.setText("Consultar");
        BotaoClientes26.setOpaque(true);
        BotaoClientes26.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes26MouseClicked(evt);
            }
        });

        BotaoClientes27.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes27.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes27.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes27.setText("Cadastrar");
        BotaoClientes27.setOpaque(true);
        BotaoClientes27.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes27MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoClientes25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ConsultarServicosLayout.setVerticalGroup(
            ConsultarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConsultarServicosLayout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap(39, Short.MAX_VALUE))
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

        FieldIDGerenciarConsertos.setBackground(new java.awt.Color(255, 0, 255));
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

        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(25, 25, 112));
        jLabel12.setText("CATEGORIAS");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(395, 395, 395))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        jPanel21.setBackground(new java.awt.Color(230, 230, 230));

        BotaoClientes28.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes28.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes28.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes28.setText("Gerenciar");
        BotaoClientes28.setOpaque(true);
        BotaoClientes28.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes28MouseClicked(evt);
            }
        });

        BotaoClientes29.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes29.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes29.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes29.setText("Consultar");
        BotaoClientes29.setOpaque(true);
        BotaoClientes29.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes29MouseClicked(evt);
            }
        });

        BotaoClientes30.setBackground(new java.awt.Color(230, 230, 230));
        BotaoClientes30.setFont(new java.awt.Font("Century Gothic", 0, 26)); // NOI18N
        BotaoClientes30.setForeground(new java.awt.Color(25, 25, 112));
        BotaoClientes30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoClientes30.setText("Cadastrar");
        BotaoClientes30.setOpaque(true);
        BotaoClientes30.setPreferredSize(new java.awt.Dimension(135, 50));
        BotaoClientes30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoClientes30MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BotaoClientes28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoClientes28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addContainerGap(68, Short.MAX_VALUE))
            .addGroup(GerenciarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(GerenciarServicosLayout.createSequentialGroup()
                    .addGap(23, 23, 23)
                    .addGroup(GerenciarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(23, 23, 23)))
        );
        GerenciarServicosLayout.setVerticalGroup(
            GerenciarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GerenciarServicosLayout.createSequentialGroup()
                .addContainerGap()
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
                .addContainerGap(297, Short.MAX_VALUE))
            .addGroup(GerenciarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(GerenciarServicosLayout.createSequentialGroup()
                    .addGap(98, 98, 98)
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)
                    .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(369, Short.MAX_VALUE)))
        );

        PaneServicos.add(GerenciarServicos, "card6");

        PaneMae.add(PaneServicos, "card4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(SideBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(PaneMae, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SideBoard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(PaneMae, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleName("Gerenciamento de Garantia");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //----------------------Inicio das ações de revista ------------------------------------
    private void BotaoClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientesMouseClicked
        PaneClientes.setVisible(true);
        PaneGarantias.setVisible(false);
        PaneServicos.setVisible(false);
        setLblColor(BotaoClientes);
        ResetColor(BotaoGarantias);
        ResetColor(BotaoConsertos);
    }//GEN-LAST:event_BotaoClientesMouseClicked

    private void BotaoGarantiasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGarantiasMouseClicked
        PaneClientes.setVisible(false);
        PaneGarantias.setVisible(true);
        PaneServicos.setVisible(false);
        setLblColor(BotaoGarantias);
        ResetColor(BotaoClientes);
        ResetColor(BotaoConsertos);
    }//GEN-LAST:event_BotaoGarantiasMouseClicked


    private void BotaoConsertosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoConsertosMouseClicked
        PaneClientes.setVisible(false);
        PaneGarantias.setVisible(false);
        PaneServicos.setVisible(true);
        ResetColor(BotaoClientes);
        ResetColor(BotaoGarantias);
        setLblColor(BotaoConsertos);
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

    private void BotaoNovoCadastroClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoNovoCadastroClienteActionPerformed
        DestravaCamposCadastroClientes();
    }//GEN-LAST:event_BotaoNovoCadastroClienteActionPerformed

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
        if(TableGerenciarCliente.getValueAt(TableGerenciarCliente.getSelectedRow(), 5).toString() != null){
            FieldEnderecoGerenciarClientes.setText(TableGerenciarCliente.getValueAt(TableGerenciarCliente.getSelectedRow(), 5).toString());
        }
        if(TableGerenciarCliente.getValueAt(TableGerenciarCliente.getSelectedRow(), 6).toString() != null){
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

    private void FieldIDGerenciarConsertosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldIDGerenciarConsertosKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldIDGerenciarConsertosKeyPressed

    private void FieldIDGerenciarConsertosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldIDGerenciarConsertosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldIDGerenciarConsertosMouseClicked

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
                Manutencao man = new Manutencao();
                ManutencaoDAO mandao = new ManutencaoDAO();

                man.setId(Integer.parseInt(FieldIDGerenciarConsertos.getText()));
                man.setDescricao(FieldNomeGerenciarConsertos.getText());
                man.setDuracao(Integer.parseInt(FieldDuracaoGerenciarConsertos.getText()));

                mandao.AlterarManutencao(man);

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
                Manutencao man = new Manutencao();
                ManutencaoDAO manDAO = new ManutencaoDAO();

                man.setId(Integer.parseInt(FieldIDGerenciarConsertos.getText()));
                manDAO.RemoverManutencao(man);
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
        Manutencao man = new Manutencao();
        ManutencaoDAO mandao = new ManutencaoDAO();

        try {
            if (!FieldNomeGerenciarServicos.getText().isEmpty()) {
                man.setDescricao(FieldNomeGerenciarServicos.getText());
            }

            ListaBuscaManutencao = mandao.ListaBuscaManutencao(man);
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
        Manutencao man = new Manutencao();
        ManutencaoDAO mandao = new ManutencaoDAO();

        try {
            if (!FieldConsultaDescricaoGarantias.getText().isEmpty()) {
                man.setDescricao(FieldConsultaDescricaoGarantias.getText());
            }

            ListaBuscaManutencao = mandao.ListaBuscaManutencao(man);
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
                Manutencao man = new Manutencao();
                ManutencaoDAO mandao = new ManutencaoDAO();

                man.getId();
                man.setDescricao(FieldNomeCadastrarConsertos.getText());
                man.setDuracao(Integer.parseInt(FieldDuracaoCadastrarConsertos.getText()));

                mandao.InserirManutencao(man);

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

    private void BotaoClientes1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes1MouseClicked

    private void BotaoClientes2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes2MouseClicked

    private void BotaoClientes3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes3MouseClicked

    private void BotaoClientes4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes4MouseClicked

    private void BotaoClientes5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes5MouseClicked

    private void BotaoClientes6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes6MouseClicked

    private void BotaoClientes7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes7MouseClicked

    private void BotaoClientes8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes8MouseClicked

    private void BotaoClientes9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes9MouseClicked

    private void BotaoClientes10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes10MouseClicked

    private void BotaoClientes11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes11MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes11MouseClicked

    private void BotaoClientes12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes12MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes12MouseClicked

    private void BotaoClientes13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes13MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes13MouseClicked

    private void BotaoClientes14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes14MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes14MouseClicked

    private void BotaoClientes15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes15MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes15MouseClicked

    private void BotaoClientes16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes16MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes16MouseClicked

    private void BotaoClientes17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes17MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes17MouseClicked

    private void BotaoClientes18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes18MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes18MouseClicked

    private void BotaoClientes19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes19MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes19MouseClicked

    private void BotaoClientes20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes20MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes20MouseClicked

    private void BotaoClientes21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes21MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes21MouseClicked

    private void BotaoClientes22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes22MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes22MouseClicked

    private void BotaoClientes23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes23MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes23MouseClicked

    private void BotaoClientes24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes24MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes24MouseClicked

    private void BotaoClientes25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes25MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes25MouseClicked

    private void BotaoClientes26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes26MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes26MouseClicked

    private void BotaoClientes27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes27MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes27MouseClicked

    private void BotaoClientes28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes28MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes28MouseClicked

    private void BotaoClientes29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes29MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes29MouseClicked

    private void BotaoClientes30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoClientes30MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes30MouseClicked

    public void setLblColor(JLabel lbl) {
        lbl.setBackground(new Color(220, 220, 220));
    }

    public void ResetColor(JLabel lbl) {
        lbl.setBackground(new Color(230, 230, 230));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private javax.swing.JButton BotaoCancelarCadastroCliente;
    private javax.swing.JButton BotaoCancelarCadastroConserto;
    private javax.swing.JButton BotaoCancelarGarantia;
    private javax.swing.JButton BotaoCancelarGerenciarGarantia;
    private javax.swing.JButton BotaoCancelarGerenciarServicos;
    private javax.swing.JButton BotaoCancelarGerenciarServicos2;
    private javax.swing.JLabel BotaoClientes;
    private javax.swing.JLabel BotaoClientes1;
    private javax.swing.JLabel BotaoClientes10;
    private javax.swing.JLabel BotaoClientes11;
    private javax.swing.JLabel BotaoClientes12;
    private javax.swing.JLabel BotaoClientes13;
    private javax.swing.JLabel BotaoClientes14;
    private javax.swing.JLabel BotaoClientes15;
    private javax.swing.JLabel BotaoClientes16;
    private javax.swing.JLabel BotaoClientes17;
    private javax.swing.JLabel BotaoClientes18;
    private javax.swing.JLabel BotaoClientes19;
    private javax.swing.JLabel BotaoClientes2;
    private javax.swing.JLabel BotaoClientes20;
    private javax.swing.JLabel BotaoClientes21;
    private javax.swing.JLabel BotaoClientes22;
    private javax.swing.JLabel BotaoClientes23;
    private javax.swing.JLabel BotaoClientes24;
    private javax.swing.JLabel BotaoClientes25;
    private javax.swing.JLabel BotaoClientes26;
    private javax.swing.JLabel BotaoClientes27;
    private javax.swing.JLabel BotaoClientes28;
    private javax.swing.JLabel BotaoClientes29;
    private javax.swing.JLabel BotaoClientes3;
    private javax.swing.JLabel BotaoClientes30;
    private javax.swing.JLabel BotaoClientes4;
    private javax.swing.JLabel BotaoClientes5;
    private javax.swing.JLabel BotaoClientes6;
    private javax.swing.JLabel BotaoClientes7;
    private javax.swing.JLabel BotaoClientes8;
    private javax.swing.JLabel BotaoClientes9;
    private javax.swing.JLabel BotaoConsertos;
    private javax.swing.JButton BotaoExcluirGerenciarGarantia;
    private javax.swing.JButton BotaoExcluirGerenciarGarantia1;
    private javax.swing.JButton BotaoExcluirGerenciarServicos;
    private javax.swing.JLabel BotaoGarantias;
    private javax.swing.JButton BotaoNovoCadastroCliente;
    private javax.swing.JButton BotaoNovoCadastroConserto;
    private javax.swing.JButton BotaoNovoGarantia;
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
    private javax.swing.JComboBox<String> ComboGerenciaEscolhaConserto;
    private javax.swing.JComboBox<String> ComboOrdenaGarantia;
    private javax.swing.JComboBox<String> ComboOrdenaGarantia1;
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
    private javax.swing.JTextField FieldNomeCadastrarConsertos;
    private javax.swing.JTextField FieldNomeGerenciarClientes;
    private javax.swing.JTextField FieldNomeGerenciarConsertos;
    private javax.swing.JTextField FieldNomeGerenciarGarantia;
    private javax.swing.JTextField FieldNomeGerenciarServicos;
    private javax.swing.JTextField FieldNomeGerenciarServicos1;
    private javax.swing.JFormattedTextField FieldTelefoneGerenciarClientes;
    private javax.swing.JPanel GerenciarClientes;
    private javax.swing.JPanel GerenciarServicos;
    private javax.swing.JPanel GerenciarServicos1;
    private javax.swing.JPanel PaneClientes;
    private javax.swing.JPanel PaneContatarCli;
    private javax.swing.JPanel PaneGarantias;
    private javax.swing.JPanel PaneMae;
    private javax.swing.JPanel PaneServicos;
    private javax.swing.JPanel SideBoard;
    private javax.swing.JTable TabelaConsultaManutencao;
    private javax.swing.JTable TableConsultaCliente;
    private javax.swing.JTable TableConsultaGarantia;
    private javax.swing.JTable TableGerenciarCliente;
    private javax.swing.JTable TableGerenciarGarantia;
    private javax.swing.JTable TableGerenciarServicos;
    private javax.swing.JTextField TxtNomeCtt;
    private javax.swing.JTextField TxtTelCtt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
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
    private javax.swing.JLabel lblNome22;
    private javax.swing.JLabel lblNome23;
    private javax.swing.JLabel lblNome24;
    private javax.swing.JLabel lblNome3;
    private javax.swing.JLabel lblNome4;
    private javax.swing.JLabel lblNome5;
    private javax.swing.JLabel lblNome6;
    private javax.swing.JLabel lblNome7;
    private javax.swing.JLabel lblNome8;
    private javax.swing.JLabel lblNome9;
    // End of variables declaration//GEN-END:variables
}
