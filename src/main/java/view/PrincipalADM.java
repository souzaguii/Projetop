/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.SetupAutoComplete;
import dao.Conexao;
import dao.GarantiaDAO;
import dao.LoginsDAO;
import dao.ManutencaoDAO;
import dao.clienteDAO;
import dao.RelatorioDAO;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.sql.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.Cliente;
import model.Garantia;
import model.Logins;
import model.Manutencao;

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
        setExtendedState(MAXIMIZED_BOTH);
        PaneMae.setVisible(false);
        SideBoard.setVisible(false);
        PaneTelaInicial.setVisible(true);
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
        TravaCamposCadastrarLogins();
        try {
            clienteDAO clidao = new clienteDAO();
            GarantiaDAO gardao = new GarantiaDAO();
            jLabel10.setText("JÁ FORAM CONSERTADOS " + gardao.TotalConsertos() + " CELULARES");
            jLabel8.setText("JÁ FORAM CADASTRADOS " + clidao.TotalClientes() + " CLIENTES");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao calcular totais de clientes e serviços!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PrincipalADM.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(PrincipalADM.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PrincipalADM.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(PrincipalADM.class.getName()).log(Level.SEVERE, null, ex);
        }
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

        ManutencaoDAO manudao = new ManutencaoDAO();
        Manutencao manu = new Manutencao();
        try {
            manudao.ListaManutencao();
            manu.getDescricao();
            ComboEscolhaConserto.setSelectedItem(manu.getDescricao());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar serviços! " + ex, "Sistema", JOptionPane.INFORMATION_MESSAGE);
        }

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
        jSeparator6.setForeground(new Color(200, 200, 200));
        FieldValorGarantia.setEnabled(false);
        lblConserto3.setVisible(false);

        lblConserto2.setEnabled(false);
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
        FieldUser.setText("");
        FieldSenha.setText("");
        lblNome20.setEnabled(false);
        lblNome21.setEnabled(false);
        lblNome30.setEnabled(false);
        jSeparator34.setForeground(new Color(200, 200, 200));
        ComboTipoConta.setEnabled(false);
        ComboTipoConta.setSelectedItem("Padrão");
        jSeparator33.setForeground(new Color(200, 200, 200));
        jLabel7.setEnabled(false);
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
        jSeparator33.setForeground(new Color(0, 0, 0));
        jSeparator34.setForeground(new Color(0, 0, 0));
        jLabel7.setEnabled(true);

    }

    public void DestravaCamposCadastrarLogins() {
        FielduserCadastrarlogins.setEnabled(true);
        FieldsenhaCadastrarlogins.setEnabled(true);
        lblNome27.setEnabled(true);
        lblNome28.setEnabled(true);
        BotaoBuscarGerenciarServicos2.setEnabled(true);
        BotaoBuscarGerenciarServicos4.setEnabled(true);
        BotaoBuscarGerenciarServicos3.setEnabled(false);

        lblNome29.setEnabled(true);
        ComboEscolhalogins.setEnabled(true);
        jLabel6.setEnabled(true);

        jSeparator37.setForeground(new Color(0, 0, 0));
        jSeparator36.setForeground(new Color(0, 0, 0));

    }

    public void TravaCamposCadastrarLogins() {
        FielduserCadastrarlogins.setEnabled(false);
        FielduserCadastrarlogins.setText("");
        FieldsenhaCadastrarlogins.setText("");

        FieldsenhaCadastrarlogins.setEnabled(false);
        lblNome27.setEnabled(false);
        lblNome28.setEnabled(false);
        BotaoBuscarGerenciarServicos2.setEnabled(false);
        BotaoBuscarGerenciarServicos3.setEnabled(true);

        lblNome29.setEnabled(false);
        ComboEscolhalogins.setEnabled(false);
        ComboEscolhalogins.setSelectedItem("Padrão");
        jLabel6.setEnabled(false);
        BotaoBuscarGerenciarServicos4.setEnabled(false);

        jSeparator37.setForeground(new Color(200, 200, 200));
        jSeparator36.setForeground(new Color(200, 200, 200));

    }

    public void DestravaCamposCadastroGarantia() {
        CampoNome.setEnabled(true);
        CampoDataFormatada.setEnabled(true);
        ComboEscolhaConserto.setEnabled(true);
        BotaoSalvarGarantia.setEnabled(true);
        FieldValorGarantia.setEnabled(true);
        lblConserto2.setEnabled(true);

        lblNome.setEnabled(true);
        lblData.setEnabled(true);
        lblConserto.setEnabled(true);
        jSeparator2.setForeground(new Color(0, 0, 0));
        jSeparator4.setForeground(new Color(0, 0, 0));
        jSeparator6.setForeground(new Color(0, 0, 0));

        BotaoCancelarGarantia.setEnabled(true);
        BotaoNovoGarantia.setEnabled(false);
    }

    public void SalvarCamposCadastroGarantia() {
        CampoNome.setText("");
        CampoDataFormatada.setText("");
        FieldValorGarantia.setText("");
        ComboEscolhaConserto.setSelectedItem(null);
        jSeparator6.setForeground(new Color(200, 200, 200));
        FieldValorGarantia.setEnabled(false);

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

    //-----------------------------------
    public void TravaCamposGarenciarGarantia() {
        CampoGerenciaDataFormatada.setEnabled(false);
        ComboGerenciaEscolhaConserto.setEnabled(false);
        FieldValorGarantiaGer.setEnabled(false);
        lblConserto5.setEnabled(false);
        lblData1.setEnabled(false);
        lblConserto1.setEnabled(false);
        lblConserto4.setVisible(false);

        BotaoAlterarGerenciarGarantia.setEnabled(false);
        BotaoExcluirGerenciarGarantia.setEnabled(false);
        BotaoCancelarGerenciarGarantia.setEnabled(false);
        jSeparator5.setForeground(new Color(200, 200, 200));
        jSeparator8.setForeground(new Color(200, 200, 200));
    }

    public void DestravaCamposGarenciarGarantia() {
        CampoGerenciaDataFormatada.setEnabled(true);
        ComboGerenciaEscolhaConserto.setEnabled(true);
        FieldValorGarantiaGer.setEnabled(true);

        lblData1.setEnabled(true);
        lblConserto1.setEnabled(true);
        lblConserto5.setEnabled(true);
        lblConserto4.setVisible(true);

        BotaoAlterarGerenciarGarantia.setEnabled(true);
        BotaoExcluirGerenciarGarantia.setEnabled(true);
        BotaoCancelarGerenciarGarantia.setEnabled(true);

        jSeparator5.setForeground(new Color(0, 0, 0));
        jSeparator8.setForeground(new Color(0, 0, 0));
    }

    public void SalvarCamposGarenciarGarantia() {
        CampoGerenciaDataFormatada.setText("");
        ComboGerenciaEscolhaConserto.setSelectedItem(null);
        FieldValorGarantiaGer.setText("");

        TravaCamposGarenciarGarantia();
    }

    //-----------------------------------
    public void TravaCamposGerenciarClientes() {
        FieldNomeGerenciarClientes.setEnabled(false);
        FieldCPFGerenciarClientes.setEnabled(false);
        FieldEnderecoGerenciarClientes.setEnabled(false);
        FieldCidadeGerenciarClientes.setEnabled(false);
        FieldEmailGerenciarClientes.setEnabled(false);
        FieldTelefoneGerenciarClientes.setEnabled(false);
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
        FieldCidadeGerenciarClientes.setEnabled(true);
        FieldEnderecoGerenciarClientes.setEnabled(true);
        FieldEmailGerenciarClientes.setEnabled(true);
        FieldTelefoneGerenciarClientes.setEnabled(true);
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
        FieldEnderecoGerenciarClientes.setText("");
        FieldCidadeGerenciarClientes.setText("");
        FieldTelefoneGerenciarClientes.setText("");
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

        JTableHeader titulotabela = TableConsultaCliente.getTableHeader();
        titulotabela.setFont(new java.awt.Font("Century Gothic", 1, 12));
        centralizado = (DefaultTableCellRenderer) titulotabela.getDefaultRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);

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

        JTableHeader titulotabela = TableConsultaCliente.getTableHeader();
        titulotabela.setFont(new java.awt.Font("Century Gothic", 1, 12));
        centralizado = (DefaultTableCellRenderer) titulotabela.getDefaultRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
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

        JTableHeader titulotabela = TableGerenciarCliente.getTableHeader();
        titulotabela.setFont(new java.awt.Font("Century Gothic", 1, 12));
        centralizado = (DefaultTableCellRenderer) titulotabela.getDefaultRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);

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

        JTableHeader titulotabela = TableGerenciarCliente.getTableHeader();
        titulotabela.setFont(new java.awt.Font("Century Gothic", 1, 12));
        centralizado = (DefaultTableCellRenderer) titulotabela.getDefaultRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
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
        String dados[][] = new String[ListaGarantia.size()][6];
        int i = 0;
        for (Garantia gara : ListaGarantia) {
            dados[i][0] = String.valueOf(gara.getId());
            dados[i][1] = gara.getNome();
            dados[i][2] = gara.getDescricao();
            dados[i][3] = String.valueOf(gara.getSaida_concerto().format(formatter));
            dados[i][4] = String.valueOf(gara.getDt_garantia().format(formatter));
            dados[i][5] = gara.getValor();
            i++;
        }
        String tituloColuna[] = {"ID", "Nome do cliente", "Nome do conserto", "Data do conserto", "Prazo de garantia", "Valor"};
        DefaultTableModel tabelaCliente = new DefaultTableModel();
        tabelaCliente.setDataVector(dados, tituloColuna);
        TableConsultaGarantia.setModel(new DefaultTableModel(dados, tituloColuna) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        TableConsultaGarantia.getColumnModel().getColumn(0).setPreferredWidth(10);
        TableConsultaGarantia.getColumnModel().getColumn(1).setPreferredWidth(100);
        TableConsultaGarantia.getColumnModel().getColumn(2).setPreferredWidth(100);
        TableConsultaGarantia.getColumnModel().getColumn(3).setPreferredWidth(100);
        TableConsultaGarantia.getColumnModel().getColumn(4).setPreferredWidth(100);
        TableConsultaGarantia.getColumnModel().getColumn(5).setPreferredWidth(10);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        TableConsultaGarantia.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        TableConsultaGarantia.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        TableConsultaGarantia.getColumnModel().getColumn(2).setCellRenderer(centralizado);
        TableConsultaGarantia.getColumnModel().getColumn(3).setCellRenderer(centralizado);
        TableConsultaGarantia.getColumnModel().getColumn(4).setCellRenderer(centralizado);
        TableConsultaGarantia.getColumnModel().getColumn(5).setCellRenderer(centralizado);
        TableConsultaGarantia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableConsultaGarantia.setRowHeight(25);
        TableConsultaGarantia.updateUI();

        JTableHeader titulotabela = TableConsultaGarantia.getTableHeader();
        titulotabela.setFont(new java.awt.Font("Century Gothic", 1, 12));
        centralizado = (DefaultTableCellRenderer) titulotabela.getDefaultRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);

        ValorTotal();

    }

    public void atualizarTabelaBuscaGarantia() {
        Garantia gar = new Garantia();
        GarantiaDAO gardao = new GarantiaDAO();
        String dados[][] = new String[ListaBuscaGarantia.size()][6];
        int i = 0;
        for (Garantia gara : ListaBuscaGarantia) {

            dados[i][0] = String.valueOf(gara.getId());
            dados[i][1] = gara.getNome();
            dados[i][2] = gara.getDescricao();
            dados[i][3] = String.valueOf(gara.getSaida_concerto().format(formatter));
            dados[i][4] = String.valueOf(gara.getDt_garantia().format(formatter));
            dados[i][5] = gara.getValor();
            i++;
        }
        String tituloColuna[] = {"ID", "Nome do cliente", "Nome do conserto", "Data do conserto", "Prazo de garantia", "Valor"};
        DefaultTableModel tabelaCliente = new DefaultTableModel();
        tabelaCliente.setDataVector(dados, tituloColuna);
        TableConsultaGarantia.setModel(new DefaultTableModel(dados, tituloColuna) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        TableConsultaGarantia.getColumnModel().getColumn(0).setPreferredWidth(10);
        TableConsultaGarantia.getColumnModel().getColumn(1).setPreferredWidth(100);
        TableConsultaGarantia.getColumnModel().getColumn(2).setPreferredWidth(100);
        TableConsultaGarantia.getColumnModel().getColumn(3).setPreferredWidth(100);
        TableConsultaGarantia.getColumnModel().getColumn(4).setPreferredWidth(100);
        TableConsultaGarantia.getColumnModel().getColumn(5).setPreferredWidth(10);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        TableConsultaGarantia.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        TableConsultaGarantia.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        TableConsultaGarantia.getColumnModel().getColumn(2).setCellRenderer(centralizado);
        TableConsultaGarantia.getColumnModel().getColumn(3).setCellRenderer(centralizado);
        TableConsultaGarantia.getColumnModel().getColumn(4).setCellRenderer(centralizado);
        TableConsultaGarantia.getColumnModel().getColumn(5).setCellRenderer(centralizado);
        TableConsultaGarantia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableConsultaGarantia.setRowHeight(25);
        TableConsultaGarantia.updateUI();

        JTableHeader titulotabela = TableConsultaGarantia.getTableHeader();
        titulotabela.setFont(new java.awt.Font("Century Gothic", 1, 12));
        centralizado = (DefaultTableCellRenderer) titulotabela.getDefaultRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);

        ValorTotal();

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
        String dados[][] = new String[ListaGarantia.size()][6];
        int i = 0;
        for (Garantia gara : ListaGarantia) {
            dados[i][0] = String.valueOf(gara.getId());
            dados[i][1] = gara.getNome();
            dados[i][2] = gara.getDescricao();
            dados[i][3] = String.valueOf(gara.getSaida_concerto().format(formatter));
            dados[i][4] = String.valueOf(gara.getDt_garantia().format(formatter));
            dados[i][5] = gara.getValor();
            i++;
        }
        String tituloColuna[] = {"ID", "Nome do cliente", "Nome do conserto", "Data do conserto", "Prazo de garantia", "Valor"};
        DefaultTableModel tabelaCliente = new DefaultTableModel();
        tabelaCliente.setDataVector(dados, tituloColuna);
        TableGerenciarGarantia.setModel(new DefaultTableModel(dados, tituloColuna) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        TableGerenciarGarantia.getColumnModel().getColumn(0).setPreferredWidth(10);
        TableGerenciarGarantia.getColumnModel().getColumn(1).setPreferredWidth(100);
        TableGerenciarGarantia.getColumnModel().getColumn(2).setPreferredWidth(100);
        TableGerenciarGarantia.getColumnModel().getColumn(3).setPreferredWidth(100);
        TableGerenciarGarantia.getColumnModel().getColumn(4).setPreferredWidth(100);
        TableGerenciarGarantia.getColumnModel().getColumn(5).setPreferredWidth(10);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        TableGerenciarGarantia.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        TableGerenciarGarantia.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        TableGerenciarGarantia.getColumnModel().getColumn(2).setCellRenderer(centralizado);
        TableGerenciarGarantia.getColumnModel().getColumn(3).setCellRenderer(centralizado);
        TableGerenciarGarantia.getColumnModel().getColumn(4).setCellRenderer(centralizado);
        TableGerenciarGarantia.getColumnModel().getColumn(5).setCellRenderer(centralizado);
        TableGerenciarGarantia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableGerenciarGarantia.setRowHeight(25);
        TableGerenciarGarantia.updateUI();

        JTableHeader titulotabela = TableGerenciarGarantia.getTableHeader();
        titulotabela.setFont(new java.awt.Font("Century Gothic", 1, 12));
        centralizado = (DefaultTableCellRenderer) titulotabela.getDefaultRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void atualizarTabelaBuscaBGarantia() {
        Garantia gar = new Garantia();
        String dados[][] = new String[ListaBuscaGarantia.size()][6];
        int i = 0;
        for (Garantia gara : ListaBuscaGarantia) {
            dados[i][0] = String.valueOf(gara.getId());
            dados[i][1] = gara.getNome();
            dados[i][2] = gara.getDescricao();
            dados[i][3] = String.valueOf(gara.getSaida_concerto().format(formatter));
            dados[i][4] = String.valueOf(gara.getDt_garantia().format(formatter));
            dados[i][5] = gara.getValor();
            i++;
        }
        String tituloColuna[] = {"ID", "Nome do cliente", "Nome do conserto", "Data do conserto", "Prazo de garantia", "Valor"};
        DefaultTableModel tabelaCliente = new DefaultTableModel();
        tabelaCliente.setDataVector(dados, tituloColuna);
        TableGerenciarGarantia.setModel(new DefaultTableModel(dados, tituloColuna) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        TableGerenciarGarantia.getColumnModel().getColumn(0).setPreferredWidth(10);
        TableGerenciarGarantia.getColumnModel().getColumn(1).setPreferredWidth(100);
        TableGerenciarGarantia.getColumnModel().getColumn(2).setPreferredWidth(100);
        TableGerenciarGarantia.getColumnModel().getColumn(3).setPreferredWidth(100);
        TableGerenciarGarantia.getColumnModel().getColumn(4).setPreferredWidth(100);
        TableGerenciarGarantia.getColumnModel().getColumn(5).setPreferredWidth(10);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        TableGerenciarGarantia.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        TableGerenciarGarantia.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        TableGerenciarGarantia.getColumnModel().getColumn(2).setCellRenderer(centralizado);
        TableGerenciarGarantia.getColumnModel().getColumn(3).setCellRenderer(centralizado);
        TableGerenciarGarantia.getColumnModel().getColumn(4).setCellRenderer(centralizado);
        TableGerenciarGarantia.getColumnModel().getColumn(5).setCellRenderer(centralizado);
        TableGerenciarGarantia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableGerenciarGarantia.setRowHeight(25);
        TableGerenciarGarantia.updateUI();

        JTableHeader titulotabela = TableGerenciarGarantia.getTableHeader();
        titulotabela.setFont(new java.awt.Font("Century Gothic", 1, 12));
        centralizado = (DefaultTableCellRenderer) titulotabela.getDefaultRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
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

        JTableHeader titulotabela = TabelaConsultaManutencao.getTableHeader();
        titulotabela.setFont(new java.awt.Font("Century Gothic", 1, 12));
        centralizado = (DefaultTableCellRenderer) titulotabela.getDefaultRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
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

        JTableHeader titulotabela = TabelaConsultaManutencao.getTableHeader();
        titulotabela.setFont(new java.awt.Font("Century Gothic", 1, 12));
        centralizado = (DefaultTableCellRenderer) titulotabela.getDefaultRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
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

        JTableHeader titulotabela = TableGerenciarServicos.getTableHeader();
        titulotabela.setFont(new java.awt.Font("Century Gothic", 1, 12));
        centralizado = (DefaultTableCellRenderer) titulotabela.getDefaultRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
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

        JTableHeader titulotabela = TableGerenciarServicos.getTableHeader();
        titulotabela.setFont(new java.awt.Font("Century Gothic", 1, 12));
        centralizado = (DefaultTableCellRenderer) titulotabela.getDefaultRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
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
                login.gettipoconta(),});

        }
        TableGerenciarLogins.getColumnModel().getColumn(0).setPreferredWidth(10);
        TableGerenciarLogins.getColumnModel().getColumn(1).setPreferredWidth(190);
        TableGerenciarLogins.getColumnModel().getColumn(2).setPreferredWidth(190);

        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        TableGerenciarLogins.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        TableGerenciarLogins.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        TableGerenciarLogins.getColumnModel().getColumn(2).setCellRenderer(centralizado);
        TableGerenciarLogins.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableGerenciarLogins.setRowHeight(25);
        TableGerenciarLogins.updateUI();

        JTableHeader titulotabela = TableGerenciarLogins.getTableHeader();
        titulotabela.setFont(new java.awt.Font("Century Gothic", 1, 12));
        centralizado = (DefaultTableCellRenderer) titulotabela.getDefaultRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void PesquisarTabela(String desc) {

        DefaultTableModel modelo = (DefaultTableModel) TableGerenciarLogins.getModel();
        modelo.setNumRows(0);
        LoginsDAO logins = new LoginsDAO();

        for (Logins login : logins.PesquisarLogin(desc)) {

            modelo.addRow(new Object[]{
                login.getid(),
                login.getusuario(),
                login.gettipoconta(),});

            DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
            JTableHeader titulotabela = TableGerenciarLogins.getTableHeader();
            titulotabela.setFont(new java.awt.Font("Century Gothic", 1, 12));
            centralizado = (DefaultTableCellRenderer) titulotabela.getDefaultRenderer();
            centralizado.setHorizontalAlignment(SwingConstants.CENTER);

        }

    }

    public void ValorTotal() {

        try {

            if (FieldValorTotal.getText().equals("")) {

                FieldValorTotal.setText("R$ 0,00");

            } else {

                GarantiaDAO gardao = new GarantiaDAO();

                String DiaConvertido = String.valueOf(FieldDia.getText());
                String MesConvertido = String.valueOf(ComboMes.getSelectedItem());
                String AnoConvertido = String.valueOf(FieldAno.getText());

                DiaConvertido = DiaConvertido.replaceAll("Todos", "");

                MesConvertido = MesConvertido.replaceAll("Todos", "");
                MesConvertido = MesConvertido.replaceAll("Janeiro", "01");
                MesConvertido = MesConvertido.replaceAll("Fevereiro", "02");
                MesConvertido = MesConvertido.replaceAll("Março", "03");
                MesConvertido = MesConvertido.replaceAll("Abril", "04");
                MesConvertido = MesConvertido.replaceAll("Maio", "05");
                MesConvertido = MesConvertido.replaceAll("Junho", "06");
                MesConvertido = MesConvertido.replaceAll("Julho", "07");
                MesConvertido = MesConvertido.replaceAll("Agosto", "08");
                MesConvertido = MesConvertido.replaceAll("Setembro", "09");
                MesConvertido = MesConvertido.replaceAll("Outubro", "10");
                MesConvertido = MesConvertido.replaceAll("Novembro", "11");
                MesConvertido = MesConvertido.replaceAll("Dezembro", "12");

                AnoConvertido = AnoConvertido.replaceAll("Todos", "");

                String ValorTotal;
                ValorTotal = gardao.SomaGarantia(DiaConvertido, MesConvertido, AnoConvertido);

                if (ValorTotal.equals("-1.0")) {

                    ValorTotal = gardao.SomaGarantia("", "", "");

                    FieldDia.setText("Todos");
                    ComboMes.setSelectedItem("Todos");
                    FieldAno.setText("Todos");

                    if (ValorTotal.equals("-1.0")) {

                        FieldValorTotal.setText("R$ 0,00");

                    } else {

                        FieldValorTotal.setText(ValorTotal);

                        JOptionPane.showMessageDialog(null, "Não há registros no período selecionado!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {

                    FieldValorTotal.setText(ValorTotal);

                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao somar valor total. Caracteres inválidos!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
        }

    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BotaoAlterarGerLogins1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        SideBoard = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        BotaoGarantias = new javax.swing.JLabel();
        BotaoClientes = new javax.swing.JLabel();
        BotaoConsertos = new javax.swing.JLabel();
        BotaoCadastros = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        PaneTelaInicial = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        Layout = new javax.swing.JLabel();
        PaneMae = new javax.swing.JPanel();
        PaneClientes = new javax.swing.JPanel();
        CadastrarClientes = new javax.swing.JPanel();
        jSeparator14 = new javax.swing.JSeparator();
        jSeparator21 = new javax.swing.JSeparator();
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
        BotaoGerenciarCli3 = new javax.swing.JLabel();
        BotaoNovoCadastroCliente = new javax.swing.JButton();
        FieldCadastroCPFCliente = new javax.swing.JFormattedTextField();
        FieldCadastroTelefoneCliente = new javax.swing.JFormattedTextField();
        ConsultarClientes = new javax.swing.JPanel();
        lblNome2 = new javax.swing.JLabel();
        FieldConsultaNomeCliente = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        lblCPF2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableConsultaCliente = new javax.swing.JTable();
        jSeparator11 = new javax.swing.JSeparator();
        BotaoBuscarConsultaCliente = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        BotaoGerenciarCliConsul = new javax.swing.JLabel();
        BotaoConsultarCliConsul = new javax.swing.JLabel();
        BotaoCadastrarCliConsul = new javax.swing.JLabel();
        BotaoGerenciarCli4 = new javax.swing.JLabel();
        FieldConsultaCPFCliente = new javax.swing.JFormattedTextField();
        GerenciarClientes = new javax.swing.JPanel();
        lblNome10 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableGerenciarCliente = new javax.swing.JTable();
        DadosAlteraClientes = new javax.swing.JPanel();
        BotaoExcluirGerenciarGarantia1 = new javax.swing.JButton();
        BotaoAlterarGerenciarGarantia1 = new javax.swing.JButton();
        lblNome19 = new javax.swing.JLabel();
        FieldNomeGerenciarClientes = new javax.swing.JTextField();
        jSeparator27 = new javax.swing.JSeparator();
        FieldIDGerenciarClientes = new javax.swing.JTextField();
        lblCPF5 = new javax.swing.JLabel();
        jSeparator19 = new javax.swing.JSeparator();
        jSeparator28 = new javax.swing.JSeparator();
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
        BotaoCancelarGerenciarServicos2 = new javax.swing.JButton();
        FieldCPFGerenciarClientes = new javax.swing.JFormattedTextField();
        FieldTelefoneGerenciarClientes = new javax.swing.JFormattedTextField();
        FieldGerenciarNomeCliente = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator32 = new javax.swing.JSeparator();
        lblCPF6 = new javax.swing.JLabel();
        BotaoBuscarConsultaCliente1 = new javax.swing.JButton();
        jPanel24 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        BotaoGerenciarCliGer = new javax.swing.JLabel();
        BotaoConsultarCliGer = new javax.swing.JLabel();
        BotaoCadastrarCliGer = new javax.swing.JLabel();
        BotaoGerenciarCli5 = new javax.swing.JLabel();
        FieldGerenciarCPFCliente = new javax.swing.JFormattedTextField();
        PaneGarantias = new javax.swing.JPanel();
        CadastrarGarantias = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
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
        BotaoGerenciarCli6 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        lblConserto2 = new javax.swing.JLabel();
        lblConserto3 = new javax.swing.JLabel();
        FieldValorGarantia = new javax.swing.JTextField();
        CampoNome = new javax.swing.JTextField();
        ConsultarGarantias = new javax.swing.JPanel();
        lblNome1 = new javax.swing.JLabel();
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
        BotaoGerenciarCli7 = new javax.swing.JLabel();
        Txtid = new javax.swing.JTextField();
        Botao2Via1 = new javax.swing.JButton();
        lblCPF11 = new javax.swing.JLabel();
        FieldValorTotal = new javax.swing.JFormattedTextField();
        ComboMes = new javax.swing.JComboBox<>();
        lblCPF13 = new javax.swing.JLabel();
        lblCPF14 = new javax.swing.JLabel();
        jSeparator18 = new javax.swing.JSeparator();
        FieldDia = new javax.swing.JTextField();
        lblCPF15 = new javax.swing.JLabel();
        lblCPF16 = new javax.swing.JLabel();
        jSeparator39 = new javax.swing.JSeparator();
        FieldAno = new javax.swing.JTextField();
        BotaoBuscarConsultarGarantias4 = new javax.swing.JButton();
        BotaoBuscarConsultarGarantias5 = new javax.swing.JButton();
        FieldConsultaNomeGarantia = new javax.swing.JTextField();
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
        FieldIDGerenciarGarantia = new javax.swing.JTextField();
        lblData1 = new javax.swing.JLabel();
        CampoGerenciaDataFormatada = new javax.swing.JFormattedTextField();
        jSeparator5 = new javax.swing.JSeparator();
        lblConserto1 = new javax.swing.JLabel();
        ComboGerenciaEscolhaConserto = new javax.swing.JComboBox<>();
        FieldValorGarantiaGer = new javax.swing.JTextField();
        lblConserto5 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        lblConserto4 = new javax.swing.JLabel();
        lblCPF3 = new javax.swing.JLabel();
        ComboOrdenaGarantia1 = new javax.swing.JComboBox<>();
        jPanel26 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        BotaoGerenciarCliGer1 = new javax.swing.JLabel();
        BotaoConsultarCliGer1 = new javax.swing.JLabel();
        BotaoCadastrarCliGer1 = new javax.swing.JLabel();
        BotaoGerenciarCli8 = new javax.swing.JLabel();
        ContatarClientes = new javax.swing.JPanel();
        lblCPF7 = new javax.swing.JLabel();
        TxtMsgWpp = new javax.swing.JTextField();
        BotaoBuscarConsultarGarantias2 = new javax.swing.JButton();
        BotaoBuscarConsultarGarantias3 = new javax.swing.JButton();
        jPanel44 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        BotaoGerenciarCliGer2 = new javax.swing.JLabel();
        BotaoConsultarCliGer2 = new javax.swing.JLabel();
        BotaoCadastrarCliGer2 = new javax.swing.JLabel();
        BotaoGerenciarCli9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblCPF10 = new javax.swing.JLabel();
        lblCPF9 = new javax.swing.JLabel();
        TxtNomeCtt = new javax.swing.JTextField();
        lblCPF8 = new javax.swing.JLabel();
        TxtNumCtt = new javax.swing.JFormattedTextField();
        jSeparator38 = new javax.swing.JSeparator();
        PaneServicos = new javax.swing.JPanel();
        CadastrarServicos = new javax.swing.JPanel();
        lblNome5 = new javax.swing.JLabel();
        FieldNomeCadastrarConsertos = new javax.swing.JTextField();
        jSeparator13 = new javax.swing.JSeparator();
        BotaoNovoCadastroConserto = new javax.swing.JButton();
        BotaoSalvarCadastroConserto = new javax.swing.JButton();
        BotaoCancelarCadastroConserto = new javax.swing.JButton();
        jSeparator22 = new javax.swing.JSeparator();
        lblNome13 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        BotaoGerenciarCli2 = new javax.swing.JLabel();
        BotaoConsultarCli2 = new javax.swing.JLabel();
        BotaoClientes4 = new javax.swing.JLabel();
        FieldDuracaoCadastrarConsertos = new javax.swing.JTextField();
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
        BotaoExcluirGerenciarServicos = new javax.swing.JButton();
        BotaoAlterarGerenciarServicos = new javax.swing.JButton();
        lblNome14 = new javax.swing.JLabel();
        FieldNomeGerenciarConsertos = new javax.swing.JTextField();
        jSeparator23 = new javax.swing.JSeparator();
        lblNome15 = new javax.swing.JLabel();
        jSeparator24 = new javax.swing.JSeparator();
        FieldIDGerenciarConsertos = new javax.swing.JTextField();
        BotaoCancelarGerenciarServicos = new javax.swing.JButton();
        FieldDuracaoGerenciarConsertos = new javax.swing.JTextField();
        jPanel32 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        BotaoGerenciarCliConsul3 = new javax.swing.JLabel();
        BotaoConsultarCliConsul3 = new javax.swing.JLabel();
        BotaoCadastrarCliConsul3 = new javax.swing.JLabel();
        PaneCadastros = new javax.swing.JPanel();
        AdicionarLogins = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        BotaoConsultarCliConsul8 = new javax.swing.JLabel();
        BotaoCadastrarCliConsul8 = new javax.swing.JLabel();
        lblNome27 = new javax.swing.JLabel();
        FielduserCadastrarlogins = new javax.swing.JTextField();
        jSeparator36 = new javax.swing.JSeparator();
        jSeparator37 = new javax.swing.JSeparator();
        lblNome28 = new javax.swing.JLabel();
        lblNome29 = new javax.swing.JLabel();
        ComboEscolhalogins = new javax.swing.JComboBox<>();
        FieldsenhaCadastrarlogins = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        BotaoBuscarGerenciarServicos2 = new javax.swing.JButton();
        BotaoBuscarGerenciarServicos3 = new javax.swing.JButton();
        BotaoBuscarGerenciarServicos4 = new javax.swing.JButton();
        GerenciarCadastros = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        BotaoConsultarCliConsul5 = new javax.swing.JLabel();
        BotaoCadastrarCliConsul5 = new javax.swing.JLabel();
        lblNome26 = new javax.swing.JLabel();
        FieldUserGerenciarLogins = new javax.swing.JTextField();
        jSeparator35 = new javax.swing.JSeparator();
        GerenciarLogins = new javax.swing.JPanel();
        jSeparator33 = new javax.swing.JSeparator();
        lblNome20 = new javax.swing.JLabel();
        jSeparator34 = new javax.swing.JSeparator();
        lblNome21 = new javax.swing.JLabel();
        ComboTipoConta = new javax.swing.JComboBox<>();
        lblNome30 = new javax.swing.JLabel();
        FieldUser = new javax.swing.JTextField();
        BotaoAlterarGerLogins = new javax.swing.JButton();
        BotaoExcluirGerLogins = new javax.swing.JButton();
        BotaoCancelarGerLogins = new javax.swing.JButton();
        FieldId = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        FieldSenha = new javax.swing.JPasswordField();
        jScrollPane9 = new javax.swing.JScrollPane();
        TableGerenciarLogins = new javax.swing.JTable();
        BotaoAlterarGerLogins2 = new javax.swing.JButton();

        BotaoAlterarGerLogins1.setBackground(new java.awt.Color(230, 230, 230));
        BotaoAlterarGerLogins1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoAlterarGerLogins1.setText("Alterar");
        BotaoAlterarGerLogins1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoAlterarGerLogins1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoAlterarGerLogins1ActionPerformed(evt);
            }
        });

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gerenciamento de Garantias");
        setIconImages(null);
        setPreferredSize(new java.awt.Dimension(1366, 706));

        SideBoard.setBackground(new java.awt.Color(230, 230, 230));
        SideBoard.setForeground(new java.awt.Color(230, 230, 230));
        SideBoard.setToolTipText("");
        SideBoard.setMaximumSize(new java.awt.Dimension(300, 400));
        SideBoard.setPreferredSize(new java.awt.Dimension(216, 706));

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

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/LogoPrincipal.png"))); // NOI18N
        jLabel4.setPreferredSize(new java.awt.Dimension(176, 55));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
        });

        javax.swing.GroupLayout SideBoardLayout = new javax.swing.GroupLayout(SideBoard);
        SideBoard.setLayout(SideBoardLayout);
        SideBoardLayout.setHorizontalGroup(
            SideBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BotaoClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(BotaoGarantias, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
            .addComponent(BotaoConsertos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(BotaoCadastros, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(SideBoardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
            .addGroup(SideBoardLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        SideBoardLayout.setVerticalGroup(
            SideBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SideBoardLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(BotaoClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoGarantias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoConsertos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoCadastros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(146, 146, 146))
        );

        PaneTelaInicial.setPreferredSize(new java.awt.Dimension(1366, 706));
        PaneTelaInicial.setLayout(null);

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 27)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(25, 25, 112));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("ACEDER AO SISTEMA");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel11MouseExited(evt);
            }
        });
        PaneTelaInicial.add(jLabel11);
        jLabel11.setBounds(10, 490, 300, 28);

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(240, 240, 240));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        PaneTelaInicial.add(jLabel10);
        jLabel10.setBounds(500, 569, 830, 22);

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(240, 240, 240));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        PaneTelaInicial.add(jLabel8);
        jLabel8.setBounds(500, 542, 830, 20);

        Layout.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        Layout.setForeground(new java.awt.Color(240, 240, 240));
        Layout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/LayoutTelaInicial.png"))); // NOI18N
        PaneTelaInicial.add(Layout);
        Layout.setBounds(0, 0, 1366, 706);

        PaneMae.setPreferredSize(new java.awt.Dimension(1150, 706));
        PaneMae.setLayout(new java.awt.CardLayout());

        PaneClientes.setPreferredSize(new java.awt.Dimension(1150, 706));
        PaneClientes.setRequestFocusEnabled(false);
        PaneClientes.setVerifyInputWhenFocusTarget(false);
        PaneClientes.setLayout(new java.awt.CardLayout());

        CadastrarClientes.setForeground(new java.awt.Color(240, 240, 240));
        CadastrarClientes.setDoubleBuffered(false);
        CadastrarClientes.setPreferredSize(new java.awt.Dimension(1150, 706));
        CadastrarClientes.setRequestFocusEnabled(false);
        CadastrarClientes.setVerifyInputWhenFocusTarget(false);

        jSeparator14.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator14.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator21.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator21.setForeground(new java.awt.Color(0, 0, 0));

        BotaoCancelarCadastroCliente.setBackground(new java.awt.Color(230, 230, 230));
        BotaoCancelarCadastroCliente.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoCancelarCadastroCliente.setText("Cancelar");
        BotaoCancelarCadastroCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoCancelarCadastroCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoCancelarCadastroClienteActionPerformed(evt);
            }
        });

        lblNome3.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblNome3.setText("Nome do cliente*");

        lblNome6.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblNome6.setText("Endereço");

        FieldCadastroNomeCliente.setBackground(new java.awt.Color(240, 240, 240));
        FieldCadastroNomeCliente.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldCadastroNomeCliente.setBorder(null);
        FieldCadastroNomeCliente.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldCadastroNomeCliente.setMinimumSize(new java.awt.Dimension(25, 25));
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FieldCadastroNomeClienteKeyTyped(evt);
            }
        });

        FieldCadastroEnderecoCliente.setBackground(new java.awt.Color(240, 240, 240));
        FieldCadastroEnderecoCliente.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldCadastroEnderecoCliente.setBorder(null);
        FieldCadastroEnderecoCliente.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldCadastroEnderecoCliente.setMinimumSize(new java.awt.Dimension(25, 25));
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

        lblNome8.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
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

        lblNome4.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblNome4.setText("Telefone*");

        jSeparator17.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator17.setForeground(new java.awt.Color(0, 0, 0));

        lblNome9.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblNome9.setText("Cidade");

        lblCPF4.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblCPF4.setText("CPF*");

        FieldCadastroCidadeCliente.setBackground(new java.awt.Color(240, 240, 240));
        FieldCadastroCidadeCliente.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldCadastroCidadeCliente.setBorder(null);
        FieldCadastroCidadeCliente.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldCadastroCidadeCliente.setMinimumSize(new java.awt.Dimension(25, 25));
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
        BotaoGerenciarCli.setText("Gerar relatório");
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

        BotaoGerenciarCli3.setBackground(new java.awt.Color(230, 230, 230));
        BotaoGerenciarCli3.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoGerenciarCli3.setForeground(new java.awt.Color(25, 25, 112));
        BotaoGerenciarCli3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoGerenciarCli3.setText("Gerenciar");
        BotaoGerenciarCli3.setOpaque(true);
        BotaoGerenciarCli3.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoGerenciarCli3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli3MouseExited(evt);
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
                .addComponent(BotaoGerenciarCli3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BotaoGerenciarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoConsultarCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoGerenciarCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoGerenciarCli3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(449, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(449, 449, 449))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(77, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
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

        FieldCadastroCPFCliente.setBackground(new java.awt.Color(240, 240, 240));
        FieldCadastroCPFCliente.setBorder(null);
        try {
            FieldCadastroCPFCliente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        FieldCadastroCPFCliente.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldCadastroCPFCliente.setPreferredSize(new java.awt.Dimension(110, 25));
        FieldCadastroCPFCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldCadastroCPFClienteMouseClicked(evt);
            }
        });
        FieldCadastroCPFCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldCadastroCPFClienteKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                FieldCadastroCPFClienteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FieldCadastroCPFClienteKeyTyped(evt);
            }
        });

        FieldCadastroTelefoneCliente.setBackground(new java.awt.Color(240, 240, 240));
        FieldCadastroTelefoneCliente.setBorder(null);
        try {
            FieldCadastroTelefoneCliente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        FieldCadastroTelefoneCliente.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldCadastroTelefoneCliente.setPreferredSize(new java.awt.Dimension(110, 25));
        FieldCadastroTelefoneCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldCadastroTelefoneClienteMouseClicked(evt);
            }
        });
        FieldCadastroTelefoneCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FieldCadastroTelefoneClienteKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout CadastrarClientesLayout = new javax.swing.GroupLayout(CadastrarClientes);
        CadastrarClientes.setLayout(CadastrarClientesLayout);
        CadastrarClientesLayout.setHorizontalGroup(
            CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1150, Short.MAX_VALUE)
            .addGroup(CadastrarClientesLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastrarClientesLayout.createSequentialGroup()
                        .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(FieldCadastroEmailCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                            .addComponent(lblNome9, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BotaoNovoCadastroCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNome3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FieldCadastroCidadeCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator21, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FieldCadastroNomeCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator9))
                        .addGap(167, 167, 167)
                        .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(FieldCadastroEnderecoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblNome6)
                                .addGroup(CadastrarClientesLayout.createSequentialGroup()
                                    .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(FieldCadastroCPFCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblCPF4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jSeparator14, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                                    .addGap(73, 73, 73)
                                    .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblNome4)
                                        .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jSeparator12)
                                            .addComponent(FieldCadastroTelefoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CadastrarClientesLayout.createSequentialGroup()
                                .addComponent(BotaoSalvarCadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(BotaoCancelarCadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNome8))
                .addContainerGap())
        );
        CadastrarClientesLayout.setVerticalGroup(
            CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarClientesLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCPF4)
                    .addComponent(lblNome3)
                    .addComponent(lblNome4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(FieldCadastroNomeCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(FieldCadastroCPFCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(FieldCadastroTelefoneCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(1, 1, 1)
                .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSeparator14)
                    .addComponent(jSeparator9)
                    .addComponent(jSeparator12))
                .addGap(26, 26, 26)
                .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(CadastrarClientesLayout.createSequentialGroup()
                        .addComponent(lblNome9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldCadastroCidadeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(CadastrarClientesLayout.createSequentialGroup()
                        .addComponent(lblNome6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldCadastroEnderecoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addComponent(lblNome8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(FieldCadastroEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addGroup(CadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotaoNovoCadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoSalvarCadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoCancelarCadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        PaneClientes.add(CadastrarClientes, "card3");

        ConsultarClientes.setPreferredSize(new java.awt.Dimension(1150, 706));

        lblNome2.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblNome2.setText("Nome do cliente");

        FieldConsultaNomeCliente.setBackground(new java.awt.Color(240, 240, 240));
        FieldConsultaNomeCliente.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldConsultaNomeCliente.setBorder(null);
        FieldConsultaNomeCliente.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldConsultaNomeCliente.setMinimumSize(new java.awt.Dimension(25, 25));
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

        jSeparator7.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));

        lblCPF2.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
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
        TableConsultaCliente.setGridColor(new java.awt.Color(220, 220, 220));
        TableConsultaCliente.setShowHorizontalLines(false);
        TableConsultaCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableConsultaClienteMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TableConsultaClienteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TableConsultaClienteMouseExited(evt);
            }
        });
        jScrollPane2.setViewportView(TableConsultaCliente);

        jSeparator11.setBackground(new java.awt.Color(240, 240, 240));
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

        BotaoGerenciarCli4.setBackground(new java.awt.Color(230, 230, 230));
        BotaoGerenciarCli4.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoGerenciarCli4.setForeground(new java.awt.Color(25, 25, 112));
        BotaoGerenciarCli4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoGerenciarCli4.setText("Gerar relatório");
        BotaoGerenciarCli4.setOpaque(true);
        BotaoGerenciarCli4.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoGerenciarCli4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli4MouseExited(evt);
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
                .addGap(0, 0, 0)
                .addComponent(BotaoGerenciarCli4, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoGerenciarCliConsul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoConsultarCliConsul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoCadastrarCliConsul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoGerenciarCli4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(449, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addGap(449, 449, 449))
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        FieldConsultaCPFCliente.setBackground(new java.awt.Color(240, 240, 240));
        FieldConsultaCPFCliente.setBorder(null);
        try {
            FieldConsultaCPFCliente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        FieldConsultaCPFCliente.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldConsultaCPFCliente.setPreferredSize(new java.awt.Dimension(205, 25));
        FieldConsultaCPFCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldConsultaCPFClienteMouseClicked(evt);
            }
        });
        FieldConsultaCPFCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldConsultaCPFClienteKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FieldConsultaCPFClienteKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout ConsultarClientesLayout = new javax.swing.GroupLayout(ConsultarClientes);
        ConsultarClientes.setLayout(ConsultarClientesLayout);
        ConsultarClientesLayout.setHorizontalGroup(
            ConsultarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, 1150, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ConsultarClientesLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(ConsultarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(ConsultarClientesLayout.createSequentialGroup()
                        .addGroup(ConsultarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblNome2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FieldConsultaNomeCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                            .addComponent(jSeparator7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(ConsultarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCPF2)
                            .addComponent(jSeparator11)
                            .addComponent(FieldConsultaCPFCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(70, 70, 70)
                        .addComponent(BotaoBuscarConsultaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(57, 57, 57))
        );
        ConsultarClientesLayout.setVerticalGroup(
            ConsultarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConsultarClientesLayout.createSequentialGroup()
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(ConsultarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ConsultarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(ConsultarClientesLayout.createSequentialGroup()
                            .addComponent(lblNome2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(FieldConsultaNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(1, 1, 1)
                            .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ConsultarClientesLayout.createSequentialGroup()
                            .addComponent(lblCPF2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(FieldConsultaCPFCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(1, 1, 1)
                            .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(BotaoBuscarConsultaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        PaneClientes.add(ConsultarClientes, "card4");

        GerenciarClientes.setPreferredSize(new java.awt.Dimension(1150, 706));

        lblNome10.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
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
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableGerenciarCliente.setFocusable(false);
        TableGerenciarCliente.setGridColor(new java.awt.Color(220, 220, 220));
        TableGerenciarCliente.setShowHorizontalLines(false);
        TableGerenciarCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableGerenciarClienteMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TableGerenciarCliente);

        DadosAlteraClientes.setMaximumSize(new java.awt.Dimension(1030, 250));
        DadosAlteraClientes.setPreferredSize(new java.awt.Dimension(1030, 250));
        DadosAlteraClientes.setRequestFocusEnabled(false);

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

        lblNome19.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblNome19.setText("Nome do cliente*");

        FieldNomeGerenciarClientes.setBackground(new java.awt.Color(240, 240, 240));
        FieldNomeGerenciarClientes.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldNomeGerenciarClientes.setBorder(null);
        FieldNomeGerenciarClientes.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldNomeGerenciarClientes.setMinimumSize(new java.awt.Dimension(25, 25));
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

        jSeparator27.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator27.setForeground(new java.awt.Color(0, 0, 0));

        FieldIDGerenciarClientes.setEditable(false);
        FieldIDGerenciarClientes.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldIDGerenciarClientes.setBorder(null);
        FieldIDGerenciarClientes.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldIDGerenciarClientes.setMinimumSize(new java.awt.Dimension(25, 25));
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

        lblCPF5.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblCPF5.setText("CPF*");

        jSeparator19.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator19.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator28.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator28.setForeground(new java.awt.Color(0, 0, 0));

        lblNome11.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblNome11.setText("Telefone*");

        lblNome22.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblNome22.setText("Cidade");

        FieldCidadeGerenciarClientes.setBackground(new java.awt.Color(240, 240, 240));
        FieldCidadeGerenciarClientes.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldCidadeGerenciarClientes.setBorder(null);
        FieldCidadeGerenciarClientes.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldCidadeGerenciarClientes.setMinimumSize(new java.awt.Dimension(25, 25));
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

        jSeparator29.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator29.setForeground(new java.awt.Color(0, 0, 0));

        lblNome23.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblNome23.setText("Endereço");

        FieldEnderecoGerenciarClientes.setBackground(new java.awt.Color(240, 240, 240));
        FieldEnderecoGerenciarClientes.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldEnderecoGerenciarClientes.setBorder(null);
        FieldEnderecoGerenciarClientes.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldEnderecoGerenciarClientes.setMinimumSize(new java.awt.Dimension(25, 25));
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

        jSeparator30.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator30.setForeground(new java.awt.Color(0, 0, 0));

        lblNome24.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblNome24.setText("Email");

        FieldEmailGerenciarClientes.setBackground(new java.awt.Color(240, 240, 240));
        FieldEmailGerenciarClientes.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldEmailGerenciarClientes.setBorder(null);
        FieldEmailGerenciarClientes.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldEmailGerenciarClientes.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldEmailGerenciarClientes.setPreferredSize(new java.awt.Dimension(1, 25));
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

        jSeparator31.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator31.setForeground(new java.awt.Color(0, 0, 0));

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

        FieldCPFGerenciarClientes.setBackground(new java.awt.Color(240, 240, 240));
        FieldCPFGerenciarClientes.setBorder(null);
        try {
            FieldCPFGerenciarClientes.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        FieldCPFGerenciarClientes.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldCPFGerenciarClientes.setPreferredSize(new java.awt.Dimension(110, 25));
        FieldCPFGerenciarClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldCPFGerenciarClientesMouseClicked(evt);
            }
        });
        FieldCPFGerenciarClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldCPFGerenciarClientesKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FieldCPFGerenciarClientesKeyTyped(evt);
            }
        });

        FieldTelefoneGerenciarClientes.setBackground(new java.awt.Color(240, 240, 240));
        FieldTelefoneGerenciarClientes.setBorder(null);
        try {
            FieldTelefoneGerenciarClientes.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        FieldTelefoneGerenciarClientes.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldTelefoneGerenciarClientes.setPreferredSize(new java.awt.Dimension(110, 25));
        FieldTelefoneGerenciarClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldTelefoneGerenciarClientesMouseClicked(evt);
            }
        });
        FieldTelefoneGerenciarClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FieldTelefoneGerenciarClientesKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout DadosAlteraClientesLayout = new javax.swing.GroupLayout(DadosAlteraClientes);
        DadosAlteraClientes.setLayout(DadosAlteraClientesLayout);
        DadosAlteraClientesLayout.setHorizontalGroup(
            DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DadosAlteraClientesLayout.createSequentialGroup()
                .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNome19)
                    .addComponent(lblNome22)
                    .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jSeparator29, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator27, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator31, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DadosAlteraClientesLayout.createSequentialGroup()
                            .addComponent(lblNome24)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 297, Short.MAX_VALUE)
                            .addComponent(FieldIDGerenciarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(15, 15, 15))
                        .addComponent(FieldEmailGerenciarClientes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(FieldNomeGerenciarClientes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(FieldCidadeGerenciarClientes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(135, 135, 135)
                .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DadosAlteraClientesLayout.createSequentialGroup()
                        .addComponent(BotaoAlterarGerenciarGarantia1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(BotaoExcluirGerenciarGarantia1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BotaoCancelarGerenciarServicos2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DadosAlteraClientesLayout.createSequentialGroup()
                        .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCPF5)
                            .addComponent(jSeparator19)
                            .addComponent(lblNome23)
                            .addComponent(FieldCPFGerenciarClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator28, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(FieldTelefoneGerenciarClientes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNome11)))
                    .addGroup(DadosAlteraClientesLayout.createSequentialGroup()
                        .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator30, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FieldEnderecoGerenciarClientes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        DadosAlteraClientesLayout.setVerticalGroup(
            DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DadosAlteraClientesLayout.createSequentialGroup()
                .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(DadosAlteraClientesLayout.createSequentialGroup()
                        .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DadosAlteraClientesLayout.createSequentialGroup()
                                .addComponent(lblCPF5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(FieldCPFGerenciarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(jSeparator19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(DadosAlteraClientesLayout.createSequentialGroup()
                                .addComponent(lblNome11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(FieldTelefoneGerenciarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(jSeparator28, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(16, 16, 16)
                        .addComponent(lblNome23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldEnderecoGerenciarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator30, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BotaoAlterarGerenciarGarantia1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BotaoExcluirGerenciarGarantia1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BotaoCancelarGerenciarServicos2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(DadosAlteraClientesLayout.createSequentialGroup()
                        .addComponent(lblNome19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldNomeGerenciarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(lblNome22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldCidadeGerenciarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(DadosAlteraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNome24)
                            .addComponent(FieldIDGerenciarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldEmailGerenciarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        FieldGerenciarNomeCliente.setBackground(new java.awt.Color(240, 240, 240));
        FieldGerenciarNomeCliente.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldGerenciarNomeCliente.setBorder(null);
        FieldGerenciarNomeCliente.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldGerenciarNomeCliente.setMinimumSize(new java.awt.Dimension(25, 25));
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

        jSeparator10.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator32.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator32.setForeground(new java.awt.Color(0, 0, 0));

        lblCPF6.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
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

        BotaoGerenciarCli5.setBackground(new java.awt.Color(230, 230, 230));
        BotaoGerenciarCli5.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoGerenciarCli5.setForeground(new java.awt.Color(25, 25, 112));
        BotaoGerenciarCli5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoGerenciarCli5.setText("Gerar relatório");
        BotaoGerenciarCli5.setOpaque(true);
        BotaoGerenciarCli5.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoGerenciarCli5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli5MouseExited(evt);
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
                .addGap(0, 0, 0)
                .addComponent(BotaoGerenciarCli5, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(499, 499, 499))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoGerenciarCliGer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoConsultarCliGer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoCadastrarCliGer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoGerenciarCli5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(449, 449, 449)
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        FieldGerenciarCPFCliente.setBackground(new java.awt.Color(240, 240, 240));
        FieldGerenciarCPFCliente.setBorder(null);
        try {
            FieldGerenciarCPFCliente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        FieldGerenciarCPFCliente.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldGerenciarCPFCliente.setPreferredSize(new java.awt.Dimension(110, 25));
        FieldGerenciarCPFCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldGerenciarCPFClienteMouseClicked(evt);
            }
        });
        FieldGerenciarCPFCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldGerenciarCPFClienteKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FieldGerenciarCPFClienteKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout GerenciarClientesLayout = new javax.swing.GroupLayout(GerenciarClientes);
        GerenciarClientes.setLayout(GerenciarClientesLayout);
        GerenciarClientesLayout.setHorizontalGroup(
            GerenciarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GerenciarClientesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(GerenciarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(GerenciarClientesLayout.createSequentialGroup()
                        .addGroup(GerenciarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FieldGerenciarNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNome10))
                        .addGap(135, 135, 135)
                        .addGroup(GerenciarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCPF6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator32, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FieldGerenciarCPFCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(139, 139, 139)
                        .addComponent(BotaoBuscarConsultaCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DadosAlteraClientes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1033, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, 1153, Short.MAX_VALUE)
        );
        GerenciarClientesLayout.setVerticalGroup(
            GerenciarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GerenciarClientesLayout.createSequentialGroup()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addGroup(GerenciarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(GerenciarClientesLayout.createSequentialGroup()
                        .addGroup(GerenciarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNome10, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCPF6, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(GerenciarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FieldGerenciarNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FieldGerenciarCPFCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(GerenciarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator32, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(GerenciarClientesLayout.createSequentialGroup()
                        .addComponent(BotaoBuscarConsultaCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)))
                .addGap(4, 4, 4)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(DadosAlteraClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        PaneClientes.add(GerenciarClientes, "card5");

        PaneMae.add(PaneClientes, "card3");

        PaneGarantias.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        PaneGarantias.setPreferredSize(new java.awt.Dimension(1150, 706));
        PaneGarantias.setLayout(new java.awt.CardLayout());

        CadastrarGarantias.setForeground(new java.awt.Color(255, 255, 255));
        CadastrarGarantias.setDoubleBuffered(false);
        CadastrarGarantias.setPreferredSize(new java.awt.Dimension(1150, 706));

        jSeparator2.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

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

        lblNome.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblNome.setText("Nome do cliente");

        lblConserto.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
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
        CampoDataFormatada.setPreferredSize(new java.awt.Dimension(47, 25));
        CampoDataFormatada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CampoDataFormatadaMouseClicked(evt);
            }
        });
        CampoDataFormatada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CampoDataFormatadaActionPerformed(evt);
            }
        });
        CampoDataFormatada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CampoDataFormatadaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                CampoDataFormatadaKeyTyped(evt);
            }
        });

        jSeparator4.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));

        lblData.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
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

        BotaoGerenciarCli6.setBackground(new java.awt.Color(230, 230, 230));
        BotaoGerenciarCli6.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoGerenciarCli6.setForeground(new java.awt.Color(25, 25, 112));
        BotaoGerenciarCli6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoGerenciarCli6.setText("Gerar relatório");
        BotaoGerenciarCli6.setOpaque(true);
        BotaoGerenciarCli6.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoGerenciarCli6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli6MouseExited(evt);
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
                .addGap(0, 0, 0)
                .addComponent(BotaoGerenciarCli6, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoGerenciarCli1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoConsultarCli1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoClientes3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoGerenciarCli6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(408, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(408, 408, 408))
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(77, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSeparator6.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));

        lblConserto2.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblConserto2.setText("Valor");

        lblConserto3.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        lblConserto3.setText("R$");

        FieldValorGarantia.setBackground(new java.awt.Color(240, 240, 240));
        FieldValorGarantia.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldValorGarantia.setBorder(null);
        FieldValorGarantia.setPreferredSize(new java.awt.Dimension(1, 25));
        FieldValorGarantia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                FieldValorGarantiaFocusLost(evt);
            }
        });
        FieldValorGarantia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldValorGarantiaMouseClicked(evt);
            }
        });
        FieldValorGarantia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FieldValorGarantiaKeyTyped(evt);
            }
        });

        CampoNome.setBackground(new java.awt.Color(240, 240, 240));
        CampoNome.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        CampoNome.setBorder(null);
        CampoNome.setMaximumSize(new java.awt.Dimension(25, 25));
        CampoNome.setMinimumSize(new java.awt.Dimension(25, 25));
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                CampoNomeKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout CadastrarGarantiasLayout = new javax.swing.GroupLayout(CadastrarGarantias);
        CadastrarGarantias.setLayout(CadastrarGarantiasLayout);
        CadastrarGarantiasLayout.setHorizontalGroup(
            CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1150, Short.MAX_VALUE)
            .addGroup(CadastrarGarantiasLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(CadastrarGarantiasLayout.createSequentialGroup()
                        .addGroup(CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNome)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                            .addGroup(CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(CampoDataFormatada, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblData, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(CampoNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(308, 308, 308)
                        .addGroup(CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblConserto, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblConserto2)
                            .addGroup(CadastrarGarantiasLayout.createSequentialGroup()
                                .addComponent(lblConserto3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FieldValorGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboEscolhaConserto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(CadastrarGarantiasLayout.createSequentialGroup()
                        .addGroup(CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CadastrarGarantiasLayout.createSequentialGroup()
                                .addComponent(BotaoNovoGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CadastrarGarantiasLayout.createSequentialGroup()
                                .addComponent(BotaoSalvarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)))
                        .addComponent(BotaoCancelarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(59, 59, 59))
        );
        CadastrarGarantiasLayout.setVerticalGroup(
            CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarGarantiasLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblConserto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ComboEscolhaConserto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(CadastrarGarantiasLayout.createSequentialGroup()
                        .addComponent(CampoNome, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addGroup(CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastrarGarantiasLayout.createSequentialGroup()
                        .addComponent(lblData, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CampoDataFormatada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(CadastrarGarantiasLayout.createSequentialGroup()
                        .addComponent(lblConserto2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblConserto3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FieldValorGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                .addGroup(CadastrarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotaoCancelarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoNovoGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoSalvarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49))
        );

        PaneGarantias.add(CadastrarGarantias, "card3");

        ConsultarGarantias.setPreferredSize(new java.awt.Dimension(1150, 706));

        lblNome1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblNome1.setText("Nome do cliente");

        jSeparator3.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

        lblCPF1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblCPF1.setText("Filtrar por:");
        lblCPF1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCPF1MouseClicked(evt);
            }
        });

        TableConsultaGarantia.setAutoCreateRowSorter(true);
        TableConsultaGarantia.setBackground(new java.awt.Color(240, 240, 240));
        TableConsultaGarantia.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        TableConsultaGarantia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Conserto", "Data do conserto", "Tempo de garantia", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TableConsultaGarantia.setFocusable(false);
        TableConsultaGarantia.setGridColor(new java.awt.Color(220, 220, 220));
        TableConsultaGarantia.setShowHorizontalLines(false);
        TableConsultaGarantia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableConsultaGarantiaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TableConsultaGarantia);
        if (TableConsultaGarantia.getColumnModel().getColumnCount() > 0) {
            TableConsultaGarantia.getColumnModel().getColumn(0).setPreferredWidth(10);
            TableConsultaGarantia.getColumnModel().getColumn(1).setPreferredWidth(100);
            TableConsultaGarantia.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableConsultaGarantia.getColumnModel().getColumn(3).setPreferredWidth(100);
            TableConsultaGarantia.getColumnModel().getColumn(4).setPreferredWidth(100);
            TableConsultaGarantia.getColumnModel().getColumn(5).setPreferredWidth(10);
        }

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
        ComboOrdenaGarantia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todas", "Vigentes", "Vencidas" }));
        ComboOrdenaGarantia.setBorder(null);
        ComboOrdenaGarantia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ComboOrdenaGarantiaMouseClicked(evt);
            }
        });
        ComboOrdenaGarantia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboOrdenaGarantiaActionPerformed(evt);
            }
        });

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

        BotaoGerenciarCli7.setBackground(new java.awt.Color(230, 230, 230));
        BotaoGerenciarCli7.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoGerenciarCli7.setForeground(new java.awt.Color(25, 25, 112));
        BotaoGerenciarCli7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoGerenciarCli7.setText("Gerar relatório");
        BotaoGerenciarCli7.setOpaque(true);
        BotaoGerenciarCli7.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoGerenciarCli7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli7MouseExited(evt);
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
                .addGap(0, 0, 0)
                .addComponent(BotaoGerenciarCli7, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoGerenciarCliConsul1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoConsultarCliConsul1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoCadastrarCliConsul1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoGerenciarCli7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addGap(408, 408, 408))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap(77, Short.MAX_VALUE)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Txtid.setEditable(false);
        Txtid.setForeground(new java.awt.Color(240, 240, 240));
        Txtid.setBorder(null);
        Txtid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtidActionPerformed(evt);
            }
        });

        Botao2Via1.setBackground(new java.awt.Color(230, 230, 230));
        Botao2Via1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        Botao2Via1.setText("Gerar 2ª via");
        Botao2Via1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Botao2Via1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Botao2Via1MouseClicked(evt);
            }
        });
        Botao2Via1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Botao2Via1ActionPerformed(evt);
            }
        });

        lblCPF11.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblCPF11.setText("Valor total");

        FieldValorTotal.setEditable(false);
        FieldValorTotal.setBorder(null);
        FieldValorTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        FieldValorTotal.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldValorTotal.setPreferredSize(new java.awt.Dimension(7, 25));
        FieldValorTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldValorTotalActionPerformed(evt);
            }
        });

        ComboMes.setBackground(new java.awt.Color(240, 240, 240));
        ComboMes.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        ComboMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" }));
        ComboMes.setBorder(null);
        ComboMes.setPreferredSize(new java.awt.Dimension(114, 25));

        lblCPF13.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblCPF13.setText("Filtrar valor total por:");

        lblCPF14.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblCPF14.setText("Dia");

        jSeparator18.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator18.setForeground(new java.awt.Color(0, 0, 0));

        FieldDia.setBackground(new java.awt.Color(240, 240, 240));
        FieldDia.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldDia.setText("Todos");
        FieldDia.setBorder(null);
        FieldDia.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldDia.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldDia.setPreferredSize(new java.awt.Dimension(1, 25));
        FieldDia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                FieldDiaFocusLost(evt);
            }
        });
        FieldDia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldDiaMouseClicked(evt);
            }
        });
        FieldDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldDiaActionPerformed(evt);
            }
        });
        FieldDia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldDiaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                FieldDiaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FieldDiaKeyTyped(evt);
            }
        });

        lblCPF15.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblCPF15.setText("Mês");

        lblCPF16.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblCPF16.setText("Ano");

        jSeparator39.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator39.setForeground(new java.awt.Color(0, 0, 0));

        FieldAno.setBackground(new java.awt.Color(240, 240, 240));
        FieldAno.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldAno.setText("Todos");
        FieldAno.setBorder(null);
        FieldAno.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldAno.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldAno.setPreferredSize(new java.awt.Dimension(1, 25));
        FieldAno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                FieldAnoFocusLost(evt);
            }
        });
        FieldAno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldAnoMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                FieldAnoMouseReleased(evt);
            }
        });
        FieldAno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldAnoActionPerformed(evt);
            }
        });
        FieldAno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldAnoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                FieldAnoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FieldAnoKeyTyped(evt);
            }
        });

        BotaoBuscarConsultarGarantias4.setBackground(new java.awt.Color(230, 230, 230));
        BotaoBuscarConsultarGarantias4.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoBuscarConsultarGarantias4.setText("Redefinir");
        BotaoBuscarConsultarGarantias4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoBuscarConsultarGarantias4.setPreferredSize(new java.awt.Dimension(151, 50));
        BotaoBuscarConsultarGarantias4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoBuscarConsultarGarantias4ActionPerformed(evt);
            }
        });

        BotaoBuscarConsultarGarantias5.setBackground(new java.awt.Color(230, 230, 230));
        BotaoBuscarConsultarGarantias5.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoBuscarConsultarGarantias5.setText("Filtrar");
        BotaoBuscarConsultarGarantias5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoBuscarConsultarGarantias5.setPreferredSize(new java.awt.Dimension(151, 50));
        BotaoBuscarConsultarGarantias5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoBuscarConsultarGarantias5ActionPerformed(evt);
            }
        });

        FieldConsultaNomeGarantia.setBackground(new java.awt.Color(240, 240, 240));
        FieldConsultaNomeGarantia.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldConsultaNomeGarantia.setBorder(null);
        FieldConsultaNomeGarantia.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldConsultaNomeGarantia.setMinimumSize(new java.awt.Dimension(25, 25));
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FieldConsultaNomeGarantiaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout ConsultarGarantiasLayout = new javax.swing.GroupLayout(ConsultarGarantias);
        ConsultarGarantias.setLayout(ConsultarGarantiasLayout);
        ConsultarGarantiasLayout.setHorizontalGroup(
            ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, 1150, Short.MAX_VALUE)
            .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                .addComponent(Txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                        .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Botao2Via1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BotaoBuscarConsultarGarantias1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(60, 60, 60)
                        .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                                .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(FieldDia, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCPF14))
                                .addGap(71, 71, 71)
                                .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ComboMes, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCPF15, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(71, 71, 71)
                                .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCPF16, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(FieldAno, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator39, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblCPF13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                                .addComponent(BotaoBuscarConsultarGarantias4, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(171, 171, 171))
                            .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                                .addComponent(BotaoBuscarConsultarGarantias5, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                                        .addComponent(lblCPF11, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(17, 17, 17))
                                    .addComponent(FieldValorTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1030, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                            .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblNome1)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
                                .addComponent(FieldConsultaNomeGarantia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblCPF1)
                                .addComponent(ComboOrdenaGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BotaoBuscarConsultarGarantias, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        ConsultarGarantiasLayout.setVerticalGroup(
            ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                        .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNome1)
                            .addComponent(lblCPF1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ComboOrdenaGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                                .addComponent(FieldConsultaNomeGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(BotaoBuscarConsultarGarantias, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(Txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ConsultarGarantiasLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ConsultarGarantiasLayout.createSequentialGroup()
                                        .addGap(80, 80, 80)
                                        .addComponent(Botao2Via1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                                        .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(BotaoBuscarConsultarGarantias5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(BotaoBuscarConsultarGarantias1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                                                .addComponent(lblCPF11)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(FieldValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(BotaoBuscarConsultarGarantias4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                                .addComponent(lblCPF13)
                                .addGap(26, 26, 26)
                                .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblCPF14)
                                    .addComponent(lblCPF15)
                                    .addComponent(lblCPF16))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(ConsultarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                                        .addComponent(FieldDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(1, 1, 1)
                                        .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(ConsultarGarantiasLayout.createSequentialGroup()
                                        .addComponent(FieldAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(1, 1, 1)
                                        .addComponent(jSeparator39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(ComboMes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())))
        );

        PaneGarantias.add(ConsultarGarantias, "card4");

        GerenciarGarantias.setPreferredSize(new java.awt.Dimension(1150, 706));

        jSeparator25.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator25.setForeground(new java.awt.Color(0, 0, 0));

        FieldNomeGerenciarServicos1.setBackground(new java.awt.Color(240, 240, 240));
        FieldNomeGerenciarServicos1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldNomeGerenciarServicos1.setBorder(null);
        FieldNomeGerenciarServicos1.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldNomeGerenciarServicos1.setMinimumSize(new java.awt.Dimension(25, 25));
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

        lblNome17.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
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
        TableGerenciarGarantia.setGridColor(new java.awt.Color(220, 220, 220));
        TableGerenciarGarantia.setShowHorizontalLines(false);
        TableGerenciarGarantia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableGerenciarGarantiaMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(TableGerenciarGarantia);

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

        FieldIDGerenciarGarantia.setEditable(false);
        FieldIDGerenciarGarantia.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldIDGerenciarGarantia.setBorder(null);
        FieldIDGerenciarGarantia.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldIDGerenciarGarantia.setMinimumSize(new java.awt.Dimension(25, 25));
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

        lblData1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblData1.setText("Data do conserto");

        CampoGerenciaDataFormatada.setBackground(new java.awt.Color(240, 240, 240));
        CampoGerenciaDataFormatada.setBorder(null);
        try {
            CampoGerenciaDataFormatada.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        CampoGerenciaDataFormatada.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        CampoGerenciaDataFormatada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CampoGerenciaDataFormatadaMouseClicked(evt);
            }
        });
        CampoGerenciaDataFormatada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CampoGerenciaDataFormatadaActionPerformed(evt);
            }
        });
        CampoGerenciaDataFormatada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                CampoGerenciaDataFormatadaKeyTyped(evt);
            }
        });

        jSeparator5.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));

        lblConserto1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblConserto1.setText("Conserto");

        ComboGerenciaEscolhaConserto.setBackground(new java.awt.Color(240, 240, 240));
        ComboGerenciaEscolhaConserto.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        ComboGerenciaEscolhaConserto.setBorder(null);
        ComboGerenciaEscolhaConserto.setPreferredSize(new java.awt.Dimension(36, 25));
        ComboGerenciaEscolhaConserto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboGerenciaEscolhaConsertoActionPerformed(evt);
            }
        });

        FieldValorGarantiaGer.setBackground(new java.awt.Color(240, 240, 240));
        FieldValorGarantiaGer.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldValorGarantiaGer.setBorder(null);
        FieldValorGarantiaGer.setPreferredSize(new java.awt.Dimension(1, 25));
        FieldValorGarantiaGer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                FieldValorGarantiaGerFocusLost(evt);
            }
        });
        FieldValorGarantiaGer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FieldValorGarantiaGerMouseClicked(evt);
            }
        });

        lblConserto5.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblConserto5.setText("Valor");

        jSeparator8.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));

        lblConserto4.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        lblConserto4.setText("R$");

        javax.swing.GroupLayout DadosAlteraGarantiaLayout = new javax.swing.GroupLayout(DadosAlteraGarantia);
        DadosAlteraGarantia.setLayout(DadosAlteraGarantiaLayout);
        DadosAlteraGarantiaLayout.setHorizontalGroup(
            DadosAlteraGarantiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DadosAlteraGarantiaLayout.createSequentialGroup()
                .addGroup(DadosAlteraGarantiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DadosAlteraGarantiaLayout.createSequentialGroup()
                        .addGroup(DadosAlteraGarantiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FieldIDGerenciarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboGerenciaEscolhaConserto, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblConserto1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(DadosAlteraGarantiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(CampoGerenciaDataFormatada)
                            .addComponent(jSeparator5)
                            .addComponent(lblData1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(138, 138, 138))
                    .addGroup(DadosAlteraGarantiaLayout.createSequentialGroup()
                        .addGroup(DadosAlteraGarantiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblConserto5)
                            .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(DadosAlteraGarantiaLayout.createSequentialGroup()
                                .addComponent(lblConserto4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FieldValorGarantiaGer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(DadosAlteraGarantiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BotaoCancelarGerenciarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoExcluirGerenciarGarantia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoAlterarGerenciarGarantia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        DadosAlteraGarantiaLayout.setVerticalGroup(
            DadosAlteraGarantiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DadosAlteraGarantiaLayout.createSequentialGroup()
                .addGroup(DadosAlteraGarantiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(DadosAlteraGarantiaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(BotaoAlterarGerenciarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(BotaoExcluirGerenciarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(BotaoCancelarGerenciarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DadosAlteraGarantiaLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(DadosAlteraGarantiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(DadosAlteraGarantiaLayout.createSequentialGroup()
                                .addGroup(DadosAlteraGarantiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblData1)
                                    .addComponent(lblConserto1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(CampoGerenciaDataFormatada, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(ComboGerenciaEscolhaConserto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addComponent(lblConserto5, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DadosAlteraGarantiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FieldValorGarantiaGer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblConserto4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(FieldIDGerenciarGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11))
        );

        lblCPF3.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblCPF3.setText("Filtrar por:");

        ComboOrdenaGarantia1.setBackground(new java.awt.Color(240, 240, 240));
        ComboOrdenaGarantia1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        ComboOrdenaGarantia1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todas", "Vigentes", "Vencidas" }));
        ComboOrdenaGarantia1.setBorder(null);
        ComboOrdenaGarantia1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboOrdenaGarantia1ActionPerformed(evt);
            }
        });

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

        BotaoGerenciarCli8.setBackground(new java.awt.Color(230, 230, 230));
        BotaoGerenciarCli8.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoGerenciarCli8.setForeground(new java.awt.Color(25, 25, 112));
        BotaoGerenciarCli8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoGerenciarCli8.setText("Gerar relatório");
        BotaoGerenciarCli8.setOpaque(true);
        BotaoGerenciarCli8.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoGerenciarCli8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli8MouseExited(evt);
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
                .addGap(0, 0, 0)
                .addComponent(BotaoGerenciarCli8, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoGerenciarCliGer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoConsultarCliGer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoCadastrarCliGer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoGerenciarCli8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(408, 408, 408)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap(77, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout GerenciarGarantiasLayout = new javax.swing.GroupLayout(GerenciarGarantias);
        GerenciarGarantias.setLayout(GerenciarGarantiasLayout);
        GerenciarGarantiasLayout.setHorizontalGroup(
            GerenciarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, 1150, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GerenciarGarantiasLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(GerenciarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(DadosAlteraGarantia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane8)
                    .addGroup(GerenciarGarantiasLayout.createSequentialGroup()
                        .addGroup(GerenciarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNome17)
                            .addComponent(FieldNomeGerenciarServicos1, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator25, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                        .addGroup(GerenciarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCPF3)
                            .addComponent(ComboOrdenaGarantia1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(58, 58, 58)
                        .addComponent(BotaoBuscarGerenciarServicos1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(57, 57, 57))
        );
        GerenciarGarantiasLayout.setVerticalGroup(
            GerenciarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GerenciarGarantiasLayout.createSequentialGroup()
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(GerenciarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GerenciarGarantiasLayout.createSequentialGroup()
                        .addComponent(lblNome17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldNomeGerenciarServicos1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(GerenciarGarantiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(BotaoBuscarGerenciarServicos1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(GerenciarGarantiasLayout.createSequentialGroup()
                            .addComponent(lblCPF3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(ComboOrdenaGarantia1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(DadosAlteraGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        PaneGarantias.add(GerenciarGarantias, "card5");

        ContatarClientes.setPreferredSize(new java.awt.Dimension(1150, 706));

        lblCPF7.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblCPF7.setText("Mensagem");

        TxtMsgWpp.setBackground(new java.awt.Color(240, 240, 240));
        TxtMsgWpp.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        TxtMsgWpp.setAlignmentY(4.0F);
        TxtMsgWpp.setBorder(null);
        TxtMsgWpp.setPreferredSize(new java.awt.Dimension(3, 25));
        TxtMsgWpp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtMsgWppActionPerformed(evt);
            }
        });
        TxtMsgWpp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtMsgWppKeyPressed(evt);
            }
        });

        BotaoBuscarConsultarGarantias2.setBackground(new java.awt.Color(230, 230, 230));
        BotaoBuscarConsultarGarantias2.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoBuscarConsultarGarantias2.setText("Enviar");
        BotaoBuscarConsultarGarantias2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoBuscarConsultarGarantias2.setPreferredSize(new java.awt.Dimension(151, 50));
        BotaoBuscarConsultarGarantias2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoBuscarConsultarGarantias2ActionPerformed(evt);
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

        BotaoGerenciarCli9.setBackground(new java.awt.Color(230, 230, 230));
        BotaoGerenciarCli9.setFont(new java.awt.Font("Century Gothic", 1, 26)); // NOI18N
        BotaoGerenciarCli9.setForeground(new java.awt.Color(25, 25, 112));
        BotaoGerenciarCli9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BotaoGerenciarCli9.setText("Gerar relatório");
        BotaoGerenciarCli9.setOpaque(true);
        BotaoGerenciarCli9.setPreferredSize(new java.awt.Dimension(150, 50));
        BotaoGerenciarCli9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BotaoGerenciarCli9MouseExited(evt);
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
                .addGap(0, 0, 0)
                .addComponent(BotaoGerenciarCli9, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(BotaoGerenciarCliGer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoConsultarCliGer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoCadastrarCliGer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BotaoGerenciarCli9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGap(405, 405, 405)
                .addComponent(jLabel24)
                .addContainerGap(411, Short.MAX_VALUE))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addContainerGap(77, Short.MAX_VALUE)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 220, 220)));
        jPanel1.setPreferredSize(new java.awt.Dimension(367, 213));

        lblCPF10.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblCPF10.setText("Número:");

        lblCPF9.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblCPF9.setText("Nome:");

        TxtNomeCtt.setEditable(false);
        TxtNomeCtt.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        TxtNomeCtt.setBorder(null);
        TxtNomeCtt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtNomeCttActionPerformed(evt);
            }
        });

        lblCPF8.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblCPF8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCPF8.setText("Dados do contato");

        TxtNumCtt.setEditable(false);
        TxtNumCtt.setBorder(null);
        try {
            TxtNumCtt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        TxtNumCtt.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        TxtNumCtt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtNumCttActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblCPF8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblCPF9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtNomeCtt, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblCPF10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtNumCtt)))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(lblCPF8)
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtNomeCtt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCPF9))
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCPF10)
                    .addComponent(TxtNumCtt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52))
        );

        jSeparator38.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator38.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout ContatarClientesLayout = new javax.swing.GroupLayout(ContatarClientes);
        ContatarClientes.setLayout(ContatarClientesLayout);
        ContatarClientesLayout.setHorizontalGroup(
            ContatarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel44, javax.swing.GroupLayout.DEFAULT_SIZE, 1150, Short.MAX_VALUE)
            .addGroup(ContatarClientesLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(ContatarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(TxtMsgWpp, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                    .addComponent(jSeparator38, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCPF7, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(ContatarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ContatarClientesLayout.createSequentialGroup()
                        .addComponent(BotaoBuscarConsultarGarantias2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BotaoBuscarConsultarGarantias3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(59, 59, 59))
        );
        ContatarClientesLayout.setVerticalGroup(
            ContatarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContatarClientesLayout.createSequentialGroup()
                .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addGroup(ContatarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ContatarClientesLayout.createSequentialGroup()
                        .addComponent(lblCPF7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TxtMsgWpp, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator38, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(ContatarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotaoBuscarConsultarGarantias2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoBuscarConsultarGarantias3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63))
        );

        PaneGarantias.add(ContatarClientes, "card6");

        PaneMae.add(PaneGarantias, "card2");

        PaneServicos.setPreferredSize(new java.awt.Dimension(1150, 706));
        PaneServicos.setLayout(new java.awt.CardLayout());

        CadastrarServicos.setPreferredSize(new java.awt.Dimension(1150, 706));

        lblNome5.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblNome5.setText("Nome do conserto");

        FieldNomeCadastrarConsertos.setBackground(new java.awt.Color(240, 240, 240));
        FieldNomeCadastrarConsertos.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldNomeCadastrarConsertos.setBorder(null);
        FieldNomeCadastrarConsertos.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldNomeCadastrarConsertos.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldNomeCadastrarConsertos.setOpaque(false);
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

        jSeparator13.setBackground(new java.awt.Color(240, 240, 240));
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

        jSeparator22.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator22.setForeground(new java.awt.Color(0, 0, 0));

        lblNome13.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
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
                .addContainerGap(388, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(388, 388, 388))
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(77, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        FieldDuracaoCadastrarConsertos.setBackground(new java.awt.Color(240, 240, 240));
        FieldDuracaoCadastrarConsertos.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldDuracaoCadastrarConsertos.setBorder(null);

        javax.swing.GroupLayout CadastrarServicosLayout = new javax.swing.GroupLayout(CadastrarServicos);
        CadastrarServicos.setLayout(CadastrarServicosLayout);
        CadastrarServicosLayout.setHorizontalGroup(
            CadastrarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarServicosLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(CadastrarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(CadastrarServicosLayout.createSequentialGroup()
                        .addComponent(BotaoNovoCadastroConserto, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BotaoSalvarCadastroConserto, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(BotaoCancelarCadastroConserto, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(CadastrarServicosLayout.createSequentialGroup()
                        .addGroup(CadastrarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNome5)
                            .addComponent(FieldNomeCadastrarConsertos, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                            .addComponent(jSeparator13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(CadastrarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNome13)
                            .addGroup(CadastrarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(FieldDuracaoCadastrarConsertos, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jSeparator22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)))))
                .addGap(59, 59, 59))
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1150, Short.MAX_VALUE)
        );
        CadastrarServicosLayout.setVerticalGroup(
            CadastrarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastrarServicosLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(CadastrarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(CadastrarServicosLayout.createSequentialGroup()
                        .addComponent(lblNome5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldNomeCadastrarConsertos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(CadastrarServicosLayout.createSequentialGroup()
                        .addComponent(lblNome13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldDuracaoCadastrarConsertos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator22)))
                .addGap(241, 241, 241)
                .addGroup(CadastrarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotaoSalvarCadastroConserto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoCancelarCadastroConserto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoNovoCadastroConserto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49))
        );

        PaneServicos.add(CadastrarServicos, "card4");

        ConsultarServicos.setPreferredSize(new java.awt.Dimension(1150, 706));

        FieldConsultaDescricaoGarantias.setBackground(new java.awt.Color(240, 240, 240));
        FieldConsultaDescricaoGarantias.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldConsultaDescricaoGarantias.setBorder(null);
        FieldConsultaDescricaoGarantias.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldConsultaDescricaoGarantias.setMinimumSize(new java.awt.Dimension(25, 25));
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

        lblNome7.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblNome7.setText("Nome do conserto");

        jSeparator15.setBackground(new java.awt.Color(240, 240, 240));
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
        TabelaConsultaManutencao.setGridColor(new java.awt.Color(220, 220, 220));
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
                .addContainerGap(388, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addGap(388, 388, 388))
            .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap(77, Short.MAX_VALUE)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout ConsultarServicosLayout = new javax.swing.GroupLayout(ConsultarServicos);
        ConsultarServicos.setLayout(ConsultarServicosLayout);
        ConsultarServicosLayout.setHorizontalGroup(
            ConsultarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, 1150, Short.MAX_VALUE)
            .addGroup(ConsultarServicosLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(ConsultarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ConsultarServicosLayout.createSequentialGroup()
                        .addGroup(ConsultarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNome7)
                            .addComponent(FieldConsultaDescricaoGarantias, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                            .addComponent(jSeparator15))
                        .addGap(394, 394, 394)
                        .addComponent(BotaoBuscarGarantias, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1030, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59))
        );
        ConsultarServicosLayout.setVerticalGroup(
            ConsultarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConsultarServicosLayout.createSequentialGroup()
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addGroup(ConsultarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ConsultarServicosLayout.createSequentialGroup()
                        .addComponent(lblNome7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldConsultaDescricaoGarantias, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BotaoBuscarGarantias, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        PaneServicos.add(ConsultarServicos, "card5");

        GerenciarServicos.setPreferredSize(new java.awt.Dimension(1150, 706));

        jSeparator20.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator20.setForeground(new java.awt.Color(0, 0, 0));

        FieldNomeGerenciarServicos.setBackground(new java.awt.Color(240, 240, 240));
        FieldNomeGerenciarServicos.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldNomeGerenciarServicos.setBorder(null);
        FieldNomeGerenciarServicos.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldNomeGerenciarServicos.setMinimumSize(new java.awt.Dimension(25, 25));
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

        lblNome12.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
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
        TableGerenciarServicos.setGridColor(new java.awt.Color(220, 220, 220));
        TableGerenciarServicos.setShowHorizontalLines(false);
        TableGerenciarServicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableGerenciarServicosMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(TableGerenciarServicos);

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

        lblNome14.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblNome14.setText("Nome do conserto");

        FieldNomeGerenciarConsertos.setBackground(new java.awt.Color(240, 240, 240));
        FieldNomeGerenciarConsertos.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldNomeGerenciarConsertos.setBorder(null);
        FieldNomeGerenciarConsertos.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldNomeGerenciarConsertos.setMinimumSize(new java.awt.Dimension(25, 25));
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

        jSeparator23.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator23.setForeground(new java.awt.Color(0, 0, 0));

        lblNome15.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblNome15.setText("Prazo de garantia (mês)");

        jSeparator24.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator24.setForeground(new java.awt.Color(0, 0, 0));

        FieldIDGerenciarConsertos.setBackground(new java.awt.Color(240, 240, 240));
        FieldIDGerenciarConsertos.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldIDGerenciarConsertos.setBorder(null);
        FieldIDGerenciarConsertos.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldIDGerenciarConsertos.setMinimumSize(new java.awt.Dimension(25, 25));
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

        FieldDuracaoGerenciarConsertos.setBackground(new java.awt.Color(240, 240, 240));
        FieldDuracaoGerenciarConsertos.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldDuracaoGerenciarConsertos.setBorder(null);
        FieldDuracaoGerenciarConsertos.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldDuracaoGerenciarConsertos.setMinimumSize(new java.awt.Dimension(25, 25));
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

        javax.swing.GroupLayout DadosAlteraServicosLayout = new javax.swing.GroupLayout(DadosAlteraServicos);
        DadosAlteraServicos.setLayout(DadosAlteraServicosLayout);
        DadosAlteraServicosLayout.setHorizontalGroup(
            DadosAlteraServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DadosAlteraServicosLayout.createSequentialGroup()
                .addGroup(DadosAlteraServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DadosAlteraServicosLayout.createSequentialGroup()
                        .addGap(0, 532, Short.MAX_VALUE)
                        .addComponent(FieldIDGerenciarConsertos, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(254, 254, 254))
                    .addGroup(DadosAlteraServicosLayout.createSequentialGroup()
                        .addGroup(DadosAlteraServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DadosAlteraServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblNome15)
                                .addComponent(lblNome14)
                                .addComponent(jSeparator23, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                                .addComponent(FieldNomeGerenciarConsertos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jSeparator24, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FieldDuracaoGerenciarConsertos, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(DadosAlteraServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BotaoAlterarGerenciarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoExcluirGerenciarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoCancelarGerenciarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        DadosAlteraServicosLayout.setVerticalGroup(
            DadosAlteraServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DadosAlteraServicosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DadosAlteraServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(DadosAlteraServicosLayout.createSequentialGroup()
                        .addComponent(lblNome14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldNomeGerenciarConsertos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(lblNome15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldDuracaoGerenciarConsertos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DadosAlteraServicosLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(BotaoAlterarGerenciarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BotaoExcluirGerenciarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BotaoCancelarGerenciarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(FieldIDGerenciarConsertos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
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
                .addContainerGap(388, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addGap(388, 388, 388))
            .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap(77, Short.MAX_VALUE)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout GerenciarServicosLayout = new javax.swing.GroupLayout(GerenciarServicos);
        GerenciarServicos.setLayout(GerenciarServicosLayout);
        GerenciarServicosLayout.setHorizontalGroup(
            GerenciarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, 1150, Short.MAX_VALUE)
            .addGroup(GerenciarServicosLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(GerenciarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(GerenciarServicosLayout.createSequentialGroup()
                        .addGroup(GerenciarServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNome12)
                            .addComponent(jSeparator20, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                            .addComponent(FieldNomeGerenciarServicos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 440, Short.MAX_VALUE)
                        .addComponent(BotaoBuscarGerenciarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane7)
                    .addComponent(DadosAlteraServicos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(57, 57, 57))
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
                        .addComponent(FieldNomeGerenciarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BotaoBuscarGerenciarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(DadosAlteraServicos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );

        PaneServicos.add(GerenciarServicos, "card6");

        PaneMae.add(PaneServicos, "card4");

        PaneCadastros.setPreferredSize(new java.awt.Dimension(1150, 706));
        PaneCadastros.setLayout(new java.awt.CardLayout());

        AdicionarLogins.setPreferredSize(new java.awt.Dimension(1150, 706));

        jPanel42.setMaximumSize(new java.awt.Dimension(32767, 327));
        jPanel42.setPreferredSize(new java.awt.Dimension(1144, 254));

        jLabel23.setFont(new java.awt.Font("Century Gothic", 1, 60)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(25, 25, 112));
        jLabel23.setText("LOGINS");

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
                .addContainerGap(469, Short.MAX_VALUE)
                .addComponent(jLabel23)
                .addGap(463, 463, 463))
            .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        lblNome27.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblNome27.setText("Usuário");

        FielduserCadastrarlogins.setBackground(new java.awt.Color(240, 240, 240));
        FielduserCadastrarlogins.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FielduserCadastrarlogins.setBorder(null);
        FielduserCadastrarlogins.setMaximumSize(new java.awt.Dimension(25, 25));
        FielduserCadastrarlogins.setMinimumSize(new java.awt.Dimension(25, 25));
        FielduserCadastrarlogins.setOpaque(false);
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

        jSeparator37.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator37.setForeground(new java.awt.Color(0, 0, 0));

        lblNome28.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblNome28.setText("Senha");

        lblNome29.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
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

        BotaoBuscarGerenciarServicos2.setBackground(new java.awt.Color(230, 230, 230));
        BotaoBuscarGerenciarServicos2.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoBuscarGerenciarServicos2.setText("Cadastrar");
        BotaoBuscarGerenciarServicos2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoBuscarGerenciarServicos2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoBuscarGerenciarServicos2MouseClicked(evt);
            }
        });
        BotaoBuscarGerenciarServicos2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoBuscarGerenciarServicos2ActionPerformed(evt);
            }
        });

        BotaoBuscarGerenciarServicos3.setBackground(new java.awt.Color(230, 230, 230));
        BotaoBuscarGerenciarServicos3.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoBuscarGerenciarServicos3.setText("Novo");
        BotaoBuscarGerenciarServicos3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoBuscarGerenciarServicos3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoBuscarGerenciarServicos3MouseClicked(evt);
            }
        });
        BotaoBuscarGerenciarServicos3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoBuscarGerenciarServicos3ActionPerformed(evt);
            }
        });

        BotaoBuscarGerenciarServicos4.setBackground(new java.awt.Color(230, 230, 230));
        BotaoBuscarGerenciarServicos4.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoBuscarGerenciarServicos4.setText("Cancelar");
        BotaoBuscarGerenciarServicos4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoBuscarGerenciarServicos4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotaoBuscarGerenciarServicos4MouseClicked(evt);
            }
        });
        BotaoBuscarGerenciarServicos4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoBuscarGerenciarServicos4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AdicionarLoginsLayout = new javax.swing.GroupLayout(AdicionarLogins);
        AdicionarLogins.setLayout(AdicionarLoginsLayout);
        AdicionarLoginsLayout.setHorizontalGroup(
            AdicionarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, 1150, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AdicionarLoginsLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(AdicionarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AdicionarLoginsLayout.createSequentialGroup()
                        .addGroup(AdicionarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator36, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FielduserCadastrarlogins, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                            .addComponent(lblNome27, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNome28, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator37, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                            .addComponent(FieldsenhaCadastrarlogins, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(AdicionarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ComboEscolhalogins, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNome29)))
                    .addGroup(AdicionarLoginsLayout.createSequentialGroup()
                        .addComponent(BotaoBuscarGerenciarServicos3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BotaoBuscarGerenciarServicos2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(BotaoBuscarGerenciarServicos4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(59, 59, 59))
        );
        AdicionarLoginsLayout.setVerticalGroup(
            AdicionarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdicionarLoginsLayout.createSequentialGroup()
                .addGroup(AdicionarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(AdicionarLoginsLayout.createSequentialGroup()
                        .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(lblNome27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FielduserCadastrarlogins, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AdicionarLoginsLayout.createSequentialGroup()
                        .addComponent(lblNome29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ComboEscolhalogins, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addComponent(lblNome28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(FieldsenhaCadastrarlogins, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jSeparator37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                .addGroup(AdicionarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotaoBuscarGerenciarServicos3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoBuscarGerenciarServicos4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotaoBuscarGerenciarServicos2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49))
        );

        PaneCadastros.add(AdicionarLogins, "card3");

        GerenciarCadastros.setPreferredSize(new java.awt.Dimension(1150, 706));

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
            .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addGap(463, 463, 463))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        lblNome26.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblNome26.setText("Usuário");

        FieldUserGerenciarLogins.setBackground(new java.awt.Color(240, 240, 240));
        FieldUserGerenciarLogins.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldUserGerenciarLogins.setBorder(null);
        FieldUserGerenciarLogins.setMaximumSize(new java.awt.Dimension(25, 25));
        FieldUserGerenciarLogins.setMinimumSize(new java.awt.Dimension(25, 25));
        FieldUserGerenciarLogins.setOpaque(false);
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

        GerenciarLogins.setPreferredSize(new java.awt.Dimension(1030, 199));

        jSeparator33.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator33.setForeground(new java.awt.Color(0, 0, 0));

        lblNome20.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblNome20.setText("Usuário");

        jSeparator34.setBackground(new java.awt.Color(240, 240, 240));
        jSeparator34.setForeground(new java.awt.Color(0, 0, 0));

        lblNome21.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblNome21.setText("Senha");

        ComboTipoConta.setBackground(new java.awt.Color(240, 240, 240));
        ComboTipoConta.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        ComboTipoConta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Padrão", "Administrador" }));
        ComboTipoConta.setBorder(null);

        lblNome30.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblNome30.setText("Tipo de conta");

        FieldUser.setBackground(new java.awt.Color(240, 240, 240));
        FieldUser.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldUser.setBorder(null);
        FieldUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                FieldUserMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                FieldUserMouseExited(evt);
            }
        });

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

        FieldId.setEditable(false);
        FieldId.setForeground(new java.awt.Color(240, 240, 240));
        FieldId.setBorder(null);

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel7.setText("Ver senha");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel7MouseExited(evt);
            }
        });

        FieldSenha.setBackground(new java.awt.Color(240, 240, 240));
        FieldSenha.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        FieldSenha.setBorder(null);
        FieldSenha.setPreferredSize(new java.awt.Dimension(1, 25));
        FieldSenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                FieldSenhaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                FieldSenhaMouseExited(evt);
            }
        });
        FieldSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FieldSenhaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout GerenciarLoginsLayout = new javax.swing.GroupLayout(GerenciarLogins);
        GerenciarLogins.setLayout(GerenciarLoginsLayout);
        GerenciarLoginsLayout.setHorizontalGroup(
            GerenciarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GerenciarLoginsLayout.createSequentialGroup()
                .addGroup(GerenciarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GerenciarLoginsLayout.createSequentialGroup()
                        .addGroup(GerenciarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNome20)
                            .addComponent(lblNome21)
                            .addComponent(jSeparator33, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FieldUser, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(GerenciarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(GerenciarLoginsLayout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addComponent(FieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(GerenciarLoginsLayout.createSequentialGroup()
                                .addGap(129, 129, 129)
                                .addComponent(ComboTipoConta, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(GerenciarLoginsLayout.createSequentialGroup()
                        .addGroup(GerenciarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(FieldSenha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                            .addComponent(jLabel7)
                            .addComponent(jSeparator34))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 129, Short.MAX_VALUE)
                        .addComponent(lblNome30)
                        .addGap(291, 291, 291)))
                .addGroup(GerenciarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GerenciarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(BotaoAlterarGerLogins, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BotaoExcluirGerLogins, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BotaoCancelarGerLogins, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        GerenciarLoginsLayout.setVerticalGroup(
            GerenciarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GerenciarLoginsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GerenciarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNome30)
                    .addComponent(lblNome20))
                .addGroup(GerenciarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GerenciarLoginsLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldUser, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(GerenciarLoginsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNome21)
                            .addComponent(FieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jLabel7)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(GerenciarLoginsLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(ComboTipoConta, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(GerenciarLoginsLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(BotaoAlterarGerLogins, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BotaoExcluirGerLogins, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BotaoCancelarGerLogins, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        TableGerenciarLogins.setAutoCreateRowSorter(true);
        TableGerenciarLogins.setBackground(new java.awt.Color(240, 240, 240));
        TableGerenciarLogins.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        TableGerenciarLogins.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "ID", "Usuário", "Tipo de conta"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
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
        TableGerenciarLogins.setGridColor(new java.awt.Color(220, 220, 220));
        TableGerenciarLogins.setRowHeight(21);
        TableGerenciarLogins.setShowHorizontalLines(false);
        TableGerenciarLogins.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableGerenciarLoginsMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(TableGerenciarLogins);

        BotaoAlterarGerLogins2.setBackground(new java.awt.Color(230, 230, 230));
        BotaoAlterarGerLogins2.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoAlterarGerLogins2.setText("Buscar");
        BotaoAlterarGerLogins2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoAlterarGerLogins2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoAlterarGerLogins2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout GerenciarCadastrosLayout = new javax.swing.GroupLayout(GerenciarCadastros);
        GerenciarCadastros.setLayout(GerenciarCadastrosLayout);
        GerenciarCadastrosLayout.setHorizontalGroup(
            GerenciarCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, 1150, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GerenciarCadastrosLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(GerenciarCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(GerenciarCadastrosLayout.createSequentialGroup()
                        .addGroup(GerenciarCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNome26)
                            .addComponent(FieldUserGerenciarLogins, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                            .addComponent(jSeparator35))
                        .addGap(541, 541, 541)
                        .addComponent(BotaoAlterarGerLogins2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GerenciarLogins, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        GerenciarCadastrosLayout.setVerticalGroup(
            GerenciarCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GerenciarCadastrosLayout.createSequentialGroup()
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addGroup(GerenciarCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(GerenciarCadastrosLayout.createSequentialGroup()
                        .addComponent(lblNome26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FieldUserGerenciarLogins, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BotaoAlterarGerLogins2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(GerenciarLogins, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        PaneCadastros.add(GerenciarCadastros, "card2");

        PaneMae.add(PaneCadastros, "card6");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(SideBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(PaneMae, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(PaneTelaInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SideBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(PaneMae, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(PaneTelaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getAccessibleContext().setAccessibleName("Gerenciamento de Garantia");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //----------------------Inicio das ações de revista ------------------------------------
    private void BotaoClientesMouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoClientesMouseClicked
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
//        ResetColor(BotaoRelatorios);
    }//GEN-LAST:event_BotaoClientesMouseClicked

    private void BotaoGarantiasMouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoGarantiasMouseClicked
        PaneClientes.setVisible(false);
        PaneGarantias.setVisible(true);
        PaneServicos.setVisible(false);
        PaneCadastros.setVisible(false);
        ContatarClientes.setVisible(false);
        CadastrarGarantias.setVisible(true);
        ConsultarGarantias.setVisible(false);
        GerenciarGarantias.setVisible(false);
        setLblColor(BotaoGarantias);
        ResetColor(BotaoClientes);
        ResetColor(BotaoConsertos);
//        ResetColor(BotaoRelatorios);
    }//GEN-LAST:event_BotaoGarantiasMouseClicked


    private void BotaoConsertosMouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoConsertosMouseClicked
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
    }//GEN-LAST:event_BotaoConsertosMouseClicked

    private void FieldConsultaNomeClienteMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldConsultaNomeClienteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldConsultaNomeClienteMouseClicked

    private void FieldConsultaNomeClienteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_FieldConsultaNomeClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldConsultaNomeClienteActionPerformed

    private void FieldConsultaNomeClienteKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldConsultaNomeClienteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldConsultaNomeClienteKeyPressed

    private void BotaoBuscarConsultaClienteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoBuscarConsultaClienteActionPerformed
        if (FieldConsultaNomeCliente.getText().isEmpty() && FieldConsultaCPFCliente.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite o nome do cliente ou o CPF antes de buscar!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
        } else {

            ListaBuscaCliente = null;
            Cliente cli = new Cliente();
            clienteDAO clidao = new clienteDAO();

            try {

                if (!FieldConsultaNomeCliente.getText().isEmpty() || !FieldConsultaCPFCliente.getText().isEmpty()) {
                    cli.setNome(FieldConsultaNomeCliente.getText());
                    cli.setCpf(FieldConsultaCPFCliente.getText());

                }
                ListaBuscaCliente = clidao.ListaBuscaCliente(cli);
                atualizarTabelaBuscaCliente();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }//GEN-LAST:event_BotaoBuscarConsultaClienteActionPerformed

    private void FieldCadastroEmailClienteKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldCadastroEmailClienteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroEmailClienteKeyPressed

    private void FieldCadastroEmailClienteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_FieldCadastroEmailClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroEmailClienteActionPerformed

    private void FieldCadastroEmailClienteMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldCadastroEmailClienteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroEmailClienteMouseClicked

    private void FieldCadastroEnderecoClienteKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldCadastroEnderecoClienteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroEnderecoClienteKeyPressed

    private void FieldCadastroEnderecoClienteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_FieldCadastroEnderecoClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroEnderecoClienteActionPerformed

    private void FieldCadastroEnderecoClienteMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldCadastroEnderecoClienteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroEnderecoClienteMouseClicked

    private void BotaoCancelarCadastroClienteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoCancelarCadastroClienteActionPerformed
        if (JOptionPane.showConfirmDialog(null, "Todos os dados inseridos serão perdidos. Deseja continuar?", "Sistema", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            SalvarCamposCadastroClientes();
        }
    }//GEN-LAST:event_BotaoCancelarCadastroClienteActionPerformed

    private void BotaoSalvarCadastroClienteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoSalvarCadastroClienteActionPerformed
        if (FieldCadastroNomeCliente.getText().isEmpty() || FieldCadastroCPFCliente.getText().equals("   .   .   -  ") || FieldCadastroTelefoneCliente.getText().equals("(  )      -    ")) {
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
                JOptionPane.showMessageDialog(null, "Cliente já existe!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_BotaoSalvarCadastroClienteActionPerformed

    private void FieldCadastroNomeClienteKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldCadastroNomeClienteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroNomeClienteKeyPressed

    private void FieldCadastroNomeClienteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_FieldCadastroNomeClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroNomeClienteActionPerformed

    private void FieldCadastroNomeClienteMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldCadastroNomeClienteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroNomeClienteMouseClicked

    private void FieldCadastroCidadeClienteMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldCadastroCidadeClienteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroCidadeClienteMouseClicked

    private void FieldCadastroCidadeClienteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_FieldCadastroCidadeClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroCidadeClienteActionPerformed

    private void FieldCadastroCidadeClienteKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldCadastroCidadeClienteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroCidadeClienteKeyPressed

    private void FieldNomeGerenciarConsertosKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldNomeGerenciarConsertosKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNomeGerenciarConsertosKeyPressed

    private void FieldNomeGerenciarConsertosMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldNomeGerenciarConsertosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNomeGerenciarConsertosMouseClicked

    private void BotaoAlterarGerenciarServicosActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoAlterarGerenciarServicosActionPerformed
        if (FieldIDGerenciarConsertos.getText().isEmpty() || FieldNomeGerenciarConsertos.getText().isEmpty() || FieldDuracaoGerenciarConsertos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Há campos vazios, preencha-os para proceder com a alteração!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
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

    private void BotaoAlterarGerenciarServicosMouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoAlterarGerenciarServicosMouseClicked

    }//GEN-LAST:event_BotaoAlterarGerenciarServicosMouseClicked

    private void BotaoExcluirGerenciarServicosActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoExcluirGerenciarServicosActionPerformed
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

    private void BotaoExcluirGerenciarServicosMouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoExcluirGerenciarServicosMouseClicked

    }//GEN-LAST:event_BotaoExcluirGerenciarServicosMouseClicked

    private void BotaoCancelarGerenciarServicosActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoCancelarGerenciarServicosActionPerformed
        SalvarCamposGerenciarServicos();
    }//GEN-LAST:event_BotaoCancelarGerenciarServicosActionPerformed

    private void BotaoCancelarGerenciarServicosMouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoCancelarGerenciarServicosMouseClicked

    }//GEN-LAST:event_BotaoCancelarGerenciarServicosMouseClicked

    private void TableGerenciarServicosMouseClicked(MouseEvent evt) {//GEN-FIRST:event_TableGerenciarServicosMouseClicked
        TableGerenciarServicos.getTableHeader().setReorderingAllowed(false);
        FieldIDGerenciarConsertos.setText(TableGerenciarServicos.getValueAt(TableGerenciarServicos.getSelectedRow(), 0).toString());
        FieldNomeGerenciarConsertos.setText(TableGerenciarServicos.getValueAt(TableGerenciarServicos.getSelectedRow(), 1).toString());
        FieldDuracaoGerenciarConsertos.setText(TableGerenciarServicos.getValueAt(TableGerenciarServicos.getSelectedRow(), 2).toString());

        DadosAlteraServicos.setVisible(true);
        DestravaCamposGerenciarServicos();
    }//GEN-LAST:event_TableGerenciarServicosMouseClicked

    private void BotaoBuscarGerenciarServicosActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoBuscarGerenciarServicosActionPerformed
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

    private void BotaoBuscarGerenciarServicosMouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoBuscarGerenciarServicosMouseClicked

    }//GEN-LAST:event_BotaoBuscarGerenciarServicosMouseClicked

    private void FieldNomeGerenciarServicosKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldNomeGerenciarServicosKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNomeGerenciarServicosKeyPressed

    private void FieldNomeGerenciarServicosMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldNomeGerenciarServicosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNomeGerenciarServicosMouseClicked

    private void BotaoBuscarGarantiasActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoBuscarGarantiasActionPerformed
        ListaBuscaManutencao = null;
        Manutencao manu = new Manutencao();
        ManutencaoDAO manudao = new ManutencaoDAO();

        try {
            if (!FieldConsultaDescricaoGarantias.getText().isEmpty()) {
                manu.setDescricao(FieldConsultaDescricaoGarantias.getText());
            }

            ListaBuscaManutencao = manudao.ListaBuscaManutencao(manu);
            atualizarTabelaBuscaManutencao();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_BotaoBuscarGarantiasActionPerformed

    private void FieldConsultaDescricaoGarantiasKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldConsultaDescricaoGarantiasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldConsultaDescricaoGarantiasKeyPressed

    private void FieldConsultaDescricaoGarantiasMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldConsultaDescricaoGarantiasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldConsultaDescricaoGarantiasMouseClicked

    private void BotaoCancelarCadastroConsertoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoCancelarCadastroConsertoActionPerformed
        SalvarCamposCadastroServicos();
    }//GEN-LAST:event_BotaoCancelarCadastroConsertoActionPerformed

    private void BotaoSalvarCadastroConsertoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoSalvarCadastroConsertoActionPerformed
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
                JOptionPane.showMessageDialog(null, "Talvez essa categoria já exista!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_BotaoSalvarCadastroConsertoActionPerformed

    private void BotaoNovoCadastroConsertoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoNovoCadastroConsertoActionPerformed
        DestravaCamposCadastroServicos();
    }//GEN-LAST:event_BotaoNovoCadastroConsertoActionPerformed

    private void FieldNomeCadastrarConsertosKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldNomeCadastrarConsertosKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNomeCadastrarConsertosKeyPressed

    private void FieldNomeCadastrarConsertosMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldNomeCadastrarConsertosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNomeCadastrarConsertosMouseClicked

    private void BotaoBuscarConsultarGarantias3ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoBuscarConsultarGarantias3ActionPerformed
        if (TxtMsgWpp.getText().isEmpty()) {

            CadastrarGarantias.setVisible(false);
            ConsultarGarantias.setVisible(true);
            GerenciarGarantias.setVisible(false);
            ContatarClientes.setVisible(false);
            TxtMsgWpp.setText("");
            TxtNumCtt.setText("");
            TxtNomeCtt.setText("");
            atualizarTabelaConsultaGarantia();

        } else {

            if (JOptionPane.showConfirmDialog(null, "A mensagem escrita será apagada. Deseja continuar?", "Sistema", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                CadastrarGarantias.setVisible(false);
                ConsultarGarantias.setVisible(true);
                GerenciarGarantias.setVisible(false);
                ContatarClientes.setVisible(false);
                TxtMsgWpp.setText("");
                TxtNumCtt.setText("");
                TxtNomeCtt.setText("");
                atualizarTabelaConsultaGarantia();
            }
        }
    }//GEN-LAST:event_BotaoBuscarConsultarGarantias3ActionPerformed

    private void TxtNomeCttActionPerformed(ActionEvent evt) {//GEN-FIRST:event_TxtNomeCttActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtNomeCttActionPerformed

    private void BotaoBuscarConsultarGarantias2ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoBuscarConsultarGarantias2ActionPerformed
        String NumConvertido = TxtNumCtt.getText();

        NumConvertido = NumConvertido.replaceAll("\\(", "");
        NumConvertido = NumConvertido.replaceAll("\\)", "");
        NumConvertido = NumConvertido.replaceAll(" ", "");
        NumConvertido = NumConvertido.replaceAll("-", "");

        String telefone = "send?phone=55" + NumConvertido;
        String msg = "&text=" + TxtMsgWpp.getText();
        msg = msg.replaceAll(" ", "%20");

        String url = "https://web.whatsapp.com/" + telefone + msg;

        try {
            Desktop.getDesktop().browse(new URL(url).toURI());
        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        } catch (URISyntaxException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }

    }//GEN-LAST:event_BotaoBuscarConsultarGarantias2ActionPerformed

    private void TxtMsgWppActionPerformed(ActionEvent evt) {//GEN-FIRST:event_TxtMsgWppActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtMsgWppActionPerformed

    private void CampoGerenciaDataFormatadaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_CampoGerenciaDataFormatadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CampoGerenciaDataFormatadaActionPerformed

    private void FieldIDGerenciarGarantiaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldIDGerenciarGarantiaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldIDGerenciarGarantiaKeyPressed

    private void FieldIDGerenciarGarantiaMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldIDGerenciarGarantiaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldIDGerenciarGarantiaMouseClicked

    private void BotaoAlterarGerenciarGarantiaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoAlterarGerenciarGarantiaActionPerformed
        if (FieldIDGerenciarGarantia.getText().isEmpty() || CampoGerenciaDataFormatada.getText().equals("  /  /    ") || FieldValorGarantiaGer.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Há campos vazios, preencha-os para proceder com a alteração!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
        } else if (JOptionPane.showConfirmDialog(null, "Deseja mesmo alterar essa garantia?", "Sistema", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            try {
                Garantia gar = new Garantia();
                GarantiaDAO gardao = new GarantiaDAO();

                gar.setId(Integer.parseInt(FieldIDGerenciarGarantia.getText()));
                gar.setDescricao((String) ComboGerenciaEscolhaConserto.getSelectedItem());
                gar.setSaida_concerto(LocalDate.parse(CampoGerenciaDataFormatada.getText(), formatter));
                gar.setDt_garantia(LocalDate.parse(FuncGarantia(gar.getSaida_concerto(), gar.getDescricao()), formatter));
                gar.setValor(FieldValorGarantiaGer.getText());

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
                JOptionPane.showMessageDialog(null, "Não foi possível alterar essa garantia!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_BotaoAlterarGerenciarGarantiaActionPerformed

    private void BotaoAlterarGerenciarGarantiaMouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoAlterarGerenciarGarantiaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoAlterarGerenciarGarantiaMouseClicked

    private void BotaoExcluirGerenciarGarantiaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoExcluirGerenciarGarantiaActionPerformed
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

    private void BotaoExcluirGerenciarGarantiaMouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoExcluirGerenciarGarantiaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoExcluirGerenciarGarantiaMouseClicked

    private void BotaoCancelarGerenciarGarantiaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoCancelarGerenciarGarantiaActionPerformed
        SalvarCamposGarenciarGarantia();
    }//GEN-LAST:event_BotaoCancelarGerenciarGarantiaActionPerformed

    private void BotaoCancelarGerenciarGarantiaMouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoCancelarGerenciarGarantiaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoCancelarGerenciarGarantiaMouseClicked

    private void TableGerenciarGarantiaMouseClicked(MouseEvent evt) {//GEN-FIRST:event_TableGerenciarGarantiaMouseClicked
        TableGerenciarGarantia.getTableHeader().setReorderingAllowed(false);
        FieldIDGerenciarGarantia.setText(TableGerenciarGarantia.getValueAt(TableGerenciarGarantia.getSelectedRow(), 0).toString());
        ComboGerenciaEscolhaConserto.getModel().setSelectedItem(TableGerenciarGarantia.getValueAt(TableGerenciarGarantia.getSelectedRow(), 2).toString());
        CampoGerenciaDataFormatada.setText(TableGerenciarGarantia.getValueAt(TableGerenciarGarantia.getSelectedRow(), 3).toString());
        String ValorConvertido = TableGerenciarGarantia.getValueAt(TableGerenciarGarantia.getSelectedRow(), 5).toString();

        ValorConvertido = ValorConvertido.replaceAll("\\$", "");

        ValorConvertido = ValorConvertido.replaceAll("R", "");

        int tam = ValorConvertido.length();

        String ValorFinal = ValorConvertido.substring(1, tam);

        FieldValorGarantiaGer.setText(ValorFinal);

        DestravaCamposGarenciarGarantia();
        DadosAlteraGarantia.setVisible(true);

        if (!FieldValorGarantiaGer.getText().isEmpty()) {

            lblConserto4.setVisible(true);

        }
    }//GEN-LAST:event_TableGerenciarGarantiaMouseClicked

    private void BotaoBuscarGerenciarServicos1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoBuscarGerenciarServicos1ActionPerformed
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

    private void BotaoBuscarGerenciarServicos1MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoBuscarGerenciarServicos1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoBuscarGerenciarServicos1MouseClicked

    private void FieldNomeGerenciarServicos1KeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldNomeGerenciarServicos1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNomeGerenciarServicos1KeyPressed

    private void FieldNomeGerenciarServicos1MouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldNomeGerenciarServicos1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNomeGerenciarServicos1MouseClicked

    private void BotaoBuscarConsultarGarantias1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoBuscarConsultarGarantias1ActionPerformed
        if (TxtNomeCtt.getText().isEmpty() || TxtNumCtt.getText().isEmpty() /*|| TxtNumCtt.getText().equals(" ") || TxtNomeCtt.getText().equals(" ")*/) {
            JOptionPane.showMessageDialog(null, "Selecione um cliente na tabela para contatar!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
        } else {
            ContatarClientes.setVisible(true);
            CadastrarGarantias.setVisible(false);
            ConsultarGarantias.setVisible(false);
            GerenciarGarantias.setVisible(false);
        }
    }//GEN-LAST:event_BotaoBuscarConsultarGarantias1ActionPerformed

    private void BotaoNovoGarantiaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoNovoGarantiaActionPerformed
        DestravaCamposCadastroGarantia();
    }//GEN-LAST:event_BotaoNovoGarantiaActionPerformed

    private void BotaoCancelarGarantiaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoCancelarGarantiaActionPerformed
        SalvarCamposCadastroGarantia();
    }//GEN-LAST:event_BotaoCancelarGarantiaActionPerformed

    private void BotaoSalvarGarantiaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoSalvarGarantiaActionPerformed
        if (CampoNome.getText().isEmpty() || FieldValorGarantia.getText().isEmpty() || ComboEscolhaConserto.getSelectedItem() == null || CampoDataFormatada.getText().equals("  /  /    ")) {
            JOptionPane.showMessageDialog(null, "Há campos vazios, preencha-os por favor!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Garantia gar = new Garantia();

            try {
                String Valorconvertido = FieldValorGarantia.getText();
                Valorconvertido = Valorconvertido.replaceAll("\\.", "");
                Valorconvertido = Valorconvertido.replaceAll(",", ".");

                double ValorConvertido = Double.valueOf(Valorconvertido);

                Locale BRAZIL = new Locale("pt", "BR");
                NumberFormat moeda = NumberFormat.getCurrencyInstance(BRAZIL);
                String valor = moeda.format(ValorConvertido);

                gar.getId();
                gar.setNome(CampoNome.getText());
                gar.setDescricao((String) ComboEscolhaConserto.getSelectedItem());
                gar.setSaida_concerto(LocalDate.parse(CampoDataFormatada.getText(), formatter));
                gar.setDt_garantia(LocalDate.parse(FuncGarantia(gar.getSaida_concerto(), gar.getDescricao()), formatter));
                gar.setValor(FieldValorGarantia.getText());
                GarantiaDAO garantiaDAO = new GarantiaDAO();
                garantiaDAO.InserirGarantia(gar);

                try {

                    JOptionPane.showMessageDialog(null, "Garantia cadastrada com sucesso!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                    SalvarCamposCadastroGarantia();
                    atualizarTabelaConsultaGarantia();
                    atualizarTabelaGerenciaCGarantia();

                    if (JOptionPane.showConfirmDialog(null, "Deseja gerar o comprovante de garantia?", "Sistema", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

                        RelatorioDAO relatorio = new RelatorioDAO();
                        relatorio.GeraGarantia(valor);

                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Algo de errado ocorreu! Erro: " + ex.getMessage(), "Sistema", JOptionPane.INFORMATION_MESSAGE);

                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "O nome do cliente inserido não está cadastrado. Cadastre-o para prosseguir!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }//GEN-LAST:event_BotaoSalvarGarantiaActionPerformed

    private void CampoDataFormatadaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_CampoDataFormatadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CampoDataFormatadaActionPerformed

    private void BotaoGerenciarCliMouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliMouseClicked
        RelatorioDAO relatoriocli = new RelatorioDAO();
        relatoriocli.RelatorioCliente();
    }//GEN-LAST:event_BotaoGerenciarCliMouseClicked

    private void BotaoClientes2MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoClientes2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes2MouseClicked

    private void BotaoConsultarCliMouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliMouseClicked
        ConsultarClientes.setVisible(true);
        CadastrarClientes.setVisible(false);
        GerenciarClientes.setVisible(false);
        atualizarTabelaConsultaCliente();
    }//GEN-LAST:event_BotaoConsultarCliMouseClicked

    private void BotaoConsultarCliMouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliMouseEntered
        BotaoConsultarCli.setBackground(new Color(220, 220, 220));
    }//GEN-LAST:event_BotaoConsultarCliMouseEntered

    private void BotaoConsultarCliMouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliMouseExited
        BotaoConsultarCli.setBackground(new Color(230, 230, 230));
    }//GEN-LAST:event_BotaoConsultarCliMouseExited

    private void BotaoGerenciarCliMouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliMouseEntered
        BotaoGerenciarCli.setBackground(new Color(220, 220, 220));
    }//GEN-LAST:event_BotaoGerenciarCliMouseEntered

    private void BotaoGerenciarCliMouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliMouseExited
        BotaoGerenciarCli.setBackground(new Color(230, 230, 230));
    }//GEN-LAST:event_BotaoGerenciarCliMouseExited

    private void BotaoGerenciarCliConsulMouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsulMouseClicked
        ConsultarClientes.setVisible(false);
        CadastrarClientes.setVisible(false);
        GerenciarClientes.setVisible(true);
        atualizarTableGerenciarCCliente();
    }//GEN-LAST:event_BotaoGerenciarCliConsulMouseClicked

    private void BotaoGerenciarCliConsulMouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsulMouseEntered
        BotaoGerenciarCliConsul.setBackground(new Color(220, 220, 220));
    }//GEN-LAST:event_BotaoGerenciarCliConsulMouseEntered

    private void BotaoGerenciarCliConsulMouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsulMouseExited
        BotaoGerenciarCliConsul.setBackground(new Color(230, 230, 230));
    }//GEN-LAST:event_BotaoGerenciarCliConsulMouseExited

    private void BotaoConsultarCliConsulMouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsulMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsulMouseClicked

    private void BotaoConsultarCliConsulMouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsulMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsulMouseEntered

    private void BotaoConsultarCliConsulMouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsulMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsulMouseExited

    private void BotaoCadastrarCliConsulMouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsulMouseClicked
        ConsultarClientes.setVisible(false);
        CadastrarClientes.setVisible(true);
        GerenciarClientes.setVisible(false);
    }//GEN-LAST:event_BotaoCadastrarCliConsulMouseClicked

    private void BotaoCadastrarCliConsulMouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsulMouseEntered
        BotaoCadastrarCliConsul.setBackground(new Color(220, 220, 220));
    }//GEN-LAST:event_BotaoCadastrarCliConsulMouseEntered

    private void BotaoCadastrarCliConsulMouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsulMouseExited
        BotaoCadastrarCliConsul.setBackground(new Color(230, 230, 230));        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliConsulMouseExited

    private void BotaoGerenciarCliGer1MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliGer1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliGer1MouseClicked

    private void BotaoGerenciarCliGer1MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliGer1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliGer1MouseEntered

    private void BotaoGerenciarCliGer1MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliGer1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliGer1MouseExited

    private void BotaoConsultarCliGer1MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliGer1MouseClicked
        ConsultarGarantias.setVisible(true);
        CadastrarGarantias.setVisible(false);
        GerenciarGarantias.setVisible(false);
        ContatarClientes.setVisible(false);
        atualizarTabelaConsultaGarantia();
    }//GEN-LAST:event_BotaoConsultarCliGer1MouseClicked

    private void BotaoConsultarCliGer1MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliGer1MouseEntered
        setLblColor(BotaoConsultarCliGer1);
    }//GEN-LAST:event_BotaoConsultarCliGer1MouseEntered

    private void BotaoConsultarCliGer1MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliGer1MouseExited
        ResetColor(BotaoConsultarCliGer1);
    }//GEN-LAST:event_BotaoConsultarCliGer1MouseExited

    private void BotaoCadastrarCliGer1MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliGer1MouseClicked
        ConsultarGarantias.setVisible(false);
        CadastrarGarantias.setVisible(true);
        GerenciarGarantias.setVisible(false);
        ContatarClientes.setVisible(false); // TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliGer1MouseClicked

    private void BotaoCadastrarCliGer1MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliGer1MouseEntered
        setLblColor(BotaoCadastrarCliGer1);
    }//GEN-LAST:event_BotaoCadastrarCliGer1MouseEntered

    private void BotaoCadastrarCliGer1MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliGer1MouseExited
        ResetColor(BotaoCadastrarCliGer1);// TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliGer1MouseExited

    private void BotaoGerenciarCliConsul1MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul1MouseClicked
        ConsultarGarantias.setVisible(false);
        CadastrarGarantias.setVisible(false);
        GerenciarGarantias.setVisible(true);
        ContatarClientes.setVisible(false);
    }//GEN-LAST:event_BotaoGerenciarCliConsul1MouseClicked

    private void BotaoGerenciarCliConsul1MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul1MouseEntered
        setLblColor(BotaoGerenciarCliConsul1); // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliConsul1MouseEntered

    private void BotaoGerenciarCliConsul1MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul1MouseExited
        ResetColor(BotaoGerenciarCliConsul1);// TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliConsul1MouseExited

    private void BotaoConsultarCliConsul1MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsul1MouseClicked

    private void BotaoConsultarCliConsul1MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul1MouseEntered

    }//GEN-LAST:event_BotaoConsultarCliConsul1MouseEntered

    private void BotaoConsultarCliConsul1MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsul1MouseExited

    private void BotaoCadastrarCliConsul1MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul1MouseClicked
        ConsultarGarantias.setVisible(false);
        CadastrarGarantias.setVisible(true);
        GerenciarGarantias.setVisible(false);
        ContatarClientes.setVisible(false);
    }//GEN-LAST:event_BotaoCadastrarCliConsul1MouseClicked

    private void BotaoCadastrarCliConsul1MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul1MouseEntered
        setLblColor(BotaoCadastrarCliConsul1);// TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliConsul1MouseEntered

    private void BotaoCadastrarCliConsul1MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul1MouseExited
        ResetColor(BotaoCadastrarCliConsul1);// TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliConsul1MouseExited

    private void BotaoGerenciarCli1MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli1MouseClicked
        ConsultarGarantias.setVisible(false);
        CadastrarGarantias.setVisible(false);
        GerenciarGarantias.setVisible(true);
        ContatarClientes.setVisible(false);
    }//GEN-LAST:event_BotaoGerenciarCli1MouseClicked

    private void BotaoGerenciarCli1MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli1MouseEntered
        setLblColor(BotaoGerenciarCli1);
    }//GEN-LAST:event_BotaoGerenciarCli1MouseEntered

    private void BotaoGerenciarCli1MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli1MouseExited
        ResetColor(BotaoGerenciarCli1);
    }//GEN-LAST:event_BotaoGerenciarCli1MouseExited

    private void BotaoConsultarCli1MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCli1MouseClicked
        ConsultarGarantias.setVisible(true);
        CadastrarGarantias.setVisible(false);
        GerenciarGarantias.setVisible(false);
        ContatarClientes.setVisible(false);
        atualizarTabelaConsultaGarantia();
    }//GEN-LAST:event_BotaoConsultarCli1MouseClicked

    private void BotaoConsultarCli1MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCli1MouseEntered
        setLblColor(BotaoConsultarCli1);// TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCli1MouseEntered

    private void BotaoConsultarCli1MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCli1MouseExited
        ResetColor(BotaoConsultarCli1);// TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCli1MouseExited

    private void BotaoClientes3MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoClientes3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes3MouseClicked

    private void BotaoGerenciarCli2MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli2MouseClicked
        ConsultarServicos.setVisible(false);
        CadastrarServicos.setVisible(false);
        GerenciarServicos.setVisible(true);
    }//GEN-LAST:event_BotaoGerenciarCli2MouseClicked

    private void BotaoGerenciarCli2MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli2MouseEntered
        setLblColor(BotaoGerenciarCli2);
    }//GEN-LAST:event_BotaoGerenciarCli2MouseEntered

    private void BotaoGerenciarCli2MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli2MouseExited
        ResetColor(BotaoGerenciarCli2);
    }//GEN-LAST:event_BotaoGerenciarCli2MouseExited

    private void BotaoConsultarCli2MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCli2MouseClicked
        ConsultarServicos.setVisible(true);
        CadastrarServicos.setVisible(false);
        GerenciarServicos.setVisible(false);
    }//GEN-LAST:event_BotaoConsultarCli2MouseClicked

    private void BotaoConsultarCli2MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCli2MouseEntered
        setLblColor(BotaoConsultarCli2);
    }//GEN-LAST:event_BotaoConsultarCli2MouseEntered

    private void BotaoConsultarCli2MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCli2MouseExited
        ResetColor(BotaoConsultarCli2);
    }//GEN-LAST:event_BotaoConsultarCli2MouseExited

    private void BotaoClientes4MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoClientes4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoClientes4MouseClicked

    private void BotaoGerenciarCliConsul2MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul2MouseClicked
        ConsultarServicos.setVisible(false);
        CadastrarServicos.setVisible(false);
        GerenciarServicos.setVisible(true);
    }//GEN-LAST:event_BotaoGerenciarCliConsul2MouseClicked

    private void BotaoGerenciarCliConsul2MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul2MouseEntered
        setLblColor(BotaoGerenciarCliConsul2);
    }//GEN-LAST:event_BotaoGerenciarCliConsul2MouseEntered

    private void BotaoGerenciarCliConsul2MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul2MouseExited
        ResetColor(BotaoGerenciarCliConsul2);// TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliConsul2MouseExited

    private void BotaoConsultarCliConsul2MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsul2MouseClicked

    private void BotaoConsultarCliConsul2MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsul2MouseEntered

    private void BotaoConsultarCliConsul2MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsul2MouseExited

    private void BotaoCadastrarCliConsul2MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul2MouseClicked
        ConsultarServicos.setVisible(false);
        CadastrarServicos.setVisible(true);
        GerenciarServicos.setVisible(false);
    }//GEN-LAST:event_BotaoCadastrarCliConsul2MouseClicked

    private void BotaoCadastrarCliConsul2MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul2MouseEntered
        setLblColor(BotaoCadastrarCliConsul2);
    }//GEN-LAST:event_BotaoCadastrarCliConsul2MouseEntered

    private void BotaoCadastrarCliConsul2MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul2MouseExited
        ResetColor(BotaoCadastrarCliConsul2);
    }//GEN-LAST:event_BotaoCadastrarCliConsul2MouseExited

    private void BotaoGerenciarCliConsul3MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliConsul3MouseClicked

    private void BotaoGerenciarCliConsul3MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliConsul3MouseEntered

    private void BotaoGerenciarCliConsul3MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliConsul3MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliConsul3MouseExited

    private void BotaoConsultarCliConsul3MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul3MouseClicked
        ConsultarServicos.setVisible(true);
        CadastrarServicos.setVisible(false);
        GerenciarServicos.setVisible(false);
    }//GEN-LAST:event_BotaoConsultarCliConsul3MouseClicked

    private void BotaoConsultarCliConsul3MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul3MouseEntered
        setLblColor(BotaoConsultarCliConsul3);
    }//GEN-LAST:event_BotaoConsultarCliConsul3MouseEntered

    private void BotaoConsultarCliConsul3MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul3MouseExited
        ResetColor(BotaoConsultarCliConsul3);
    }//GEN-LAST:event_BotaoConsultarCliConsul3MouseExited

    private void BotaoCadastrarCliConsul3MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul3MouseClicked
        ConsultarServicos.setVisible(false);
        CadastrarServicos.setVisible(true);
        GerenciarServicos.setVisible(false);
    }//GEN-LAST:event_BotaoCadastrarCliConsul3MouseClicked

    private void BotaoCadastrarCliConsul3MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul3MouseEntered
        setLblColor(BotaoCadastrarCliConsul3);
    }//GEN-LAST:event_BotaoCadastrarCliConsul3MouseEntered

    private void BotaoCadastrarCliConsul3MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul3MouseExited
        ResetColor(BotaoCadastrarCliConsul3);
    }//GEN-LAST:event_BotaoCadastrarCliConsul3MouseExited

    private void FieldIDGerenciarConsertosKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldIDGerenciarConsertosKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldIDGerenciarConsertosKeyPressed

    private void FieldIDGerenciarConsertosMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldIDGerenciarConsertosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldIDGerenciarConsertosMouseClicked

    private void BotaoGarantiasMouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoGarantiasMouseEntered
        setLblColor(BotaoGarantias);
    }//GEN-LAST:event_BotaoGarantiasMouseEntered

    private void BotaoGarantiasMouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoGarantiasMouseExited
        ResetColor(BotaoGarantias);
    }//GEN-LAST:event_BotaoGarantiasMouseExited

    private void BotaoClientesMouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoClientesMouseEntered
        setLblColor(BotaoClientes);
    }//GEN-LAST:event_BotaoClientesMouseEntered

    private void BotaoClientesMouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoClientesMouseExited
        ResetColor(BotaoClientes);
    }//GEN-LAST:event_BotaoClientesMouseExited

    private void BotaoConsertosMouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoConsertosMouseEntered
        setLblColor(BotaoConsertos); // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsertosMouseEntered

    private void BotaoConsertosMouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoConsertosMouseExited
        ResetColor(BotaoConsertos); // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsertosMouseExited

    private void BotaoCadastrosMouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrosMouseClicked
        PaneClientes.setVisible(false);
        PaneGarantias.setVisible(false);
        PaneServicos.setVisible(false);
        PaneCadastros.setVisible(true);
        AdicionarLogins.setVisible(true);
        GerenciarCadastros.setVisible(false);
        setLblColor(BotaoCadastros);
        ResetColor(BotaoGarantias);
        ResetColor(BotaoConsertos);
        ResetColor(BotaoClientes);
//        ResetColor(BotaoRelatorios);
    }//GEN-LAST:event_BotaoCadastrosMouseClicked

    private void BotaoCadastrosMouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrosMouseEntered
        setLblColor(BotaoCadastros);
    }//GEN-LAST:event_BotaoCadastrosMouseEntered

    private void BotaoCadastrosMouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrosMouseExited
        ResetColor(BotaoCadastros);
    }//GEN-LAST:event_BotaoCadastrosMouseExited

    private void BotaoConsultarCliConsul5MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsul5MouseClicked

    private void BotaoConsultarCliConsul5MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsul5MouseEntered

    private void BotaoConsultarCliConsul5MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul5MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliConsul5MouseExited

    private void BotaoCadastrarCliConsul5MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul5MouseClicked
        AdicionarLogins.setVisible(true);
        GerenciarCadastros.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliConsul5MouseClicked

    private void BotaoCadastrarCliConsul5MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul5MouseEntered
        setLblColor(BotaoCadastrarCliConsul5);
    }//GEN-LAST:event_BotaoCadastrarCliConsul5MouseEntered

    private void BotaoCadastrarCliConsul5MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul5MouseExited
        ResetColor(BotaoCadastrarCliConsul5);
    }//GEN-LAST:event_BotaoCadastrarCliConsul5MouseExited

    private void FieldUserGerenciarLoginsMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldUserGerenciarLoginsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldUserGerenciarLoginsMouseClicked

    private void FieldUserGerenciarLoginsKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldUserGerenciarLoginsKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldUserGerenciarLoginsKeyPressed

    private void BotaoConsultarCliConsul8MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul8MouseClicked
        AdicionarLogins.setVisible(false);
        GerenciarCadastros.setVisible(true);
        TableGerenciarLogins.updateUI();
        TableGerenciarLoginss();
        GerenciarLogins.setEnabled(false);
    }//GEN-LAST:event_BotaoConsultarCliConsul8MouseClicked

    private void BotaoConsultarCliConsul8MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul8MouseEntered
        setLblColor(BotaoConsultarCliConsul8);
    }//GEN-LAST:event_BotaoConsultarCliConsul8MouseEntered

    private void BotaoConsultarCliConsul8MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliConsul8MouseExited
        ResetColor(BotaoConsultarCliConsul8);
    }//GEN-LAST:event_BotaoConsultarCliConsul8MouseExited

    private void BotaoCadastrarCliConsul8MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliConsul8MouseClicked

    private void BotaoCadastrarCliConsul8MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul8MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliConsul8MouseEntered

    private void BotaoCadastrarCliConsul8MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliConsul8MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoCadastrarCliConsul8MouseExited

    private void FielduserCadastrarloginsMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FielduserCadastrarloginsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FielduserCadastrarloginsMouseClicked

    private void FielduserCadastrarloginsKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FielduserCadastrarloginsKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FielduserCadastrarloginsKeyPressed

    private void BotaoGerenciarCliGer2MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliGer2MouseClicked
        ConsultarGarantias.setVisible(false);
        CadastrarGarantias.setVisible(false);
        GerenciarGarantias.setVisible(true);
        ContatarClientes.setVisible(false);
    }//GEN-LAST:event_BotaoGerenciarCliGer2MouseClicked

    private void BotaoGerenciarCliGer2MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliGer2MouseEntered
        setLblColor(BotaoGerenciarCliGer2);
    }//GEN-LAST:event_BotaoGerenciarCliGer2MouseEntered

    private void BotaoGerenciarCliGer2MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliGer2MouseExited
        ResetColor(BotaoGerenciarCliGer2);
    }//GEN-LAST:event_BotaoGerenciarCliGer2MouseExited

    private void BotaoConsultarCliGer2MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliGer2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliGer2MouseClicked

    private void BotaoConsultarCliGer2MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliGer2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliGer2MouseEntered

    private void BotaoConsultarCliGer2MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliGer2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliGer2MouseExited

    private void BotaoCadastrarCliGer2MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliGer2MouseClicked
        ConsultarGarantias.setVisible(false);
        CadastrarGarantias.setVisible(true);
        GerenciarGarantias.setVisible(false);
        ContatarClientes.setVisible(false);
    }//GEN-LAST:event_BotaoCadastrarCliGer2MouseClicked

    private void BotaoCadastrarCliGer2MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliGer2MouseEntered
        setLblColor(BotaoCadastrarCliGer2);
    }//GEN-LAST:event_BotaoCadastrarCliGer2MouseEntered

    private void BotaoCadastrarCliGer2MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliGer2MouseExited
        ResetColor(BotaoCadastrarCliGer2);
    }//GEN-LAST:event_BotaoCadastrarCliGer2MouseExited

    private void ComboEscolhaConsertoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ComboEscolhaConsertoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboEscolhaConsertoActionPerformed

    private void TableGerenciarLoginsMouseClicked(MouseEvent evt) {//GEN-FIRST:event_TableGerenciarLoginsMouseClicked
        try {
            LoginsDAO loginsdao = new LoginsDAO();
            TableGerenciarLogins.getTableHeader().setReorderingAllowed(false);
            FieldId.setText(TableGerenciarLogins.getValueAt(TableGerenciarLogins.getSelectedRow(), 0).toString());
            FieldUser.setText(TableGerenciarLogins.getValueAt(TableGerenciarLogins.getSelectedRow(), 1).toString());
            ComboTipoConta.setSelectedItem(TableGerenciarLogins.getValueAt(TableGerenciarLogins.getSelectedRow(), 2).toString());
            FieldSenha.setText(loginsdao.PesquisarSenha(FieldUser.getText()));
            DestravaCamposGerenciarLogins();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao selecionar informações!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_TableGerenciarLoginsMouseClicked

    private void BotaoAlterarGerLoginsActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoAlterarGerLoginsActionPerformed
        Logins logins = new Logins();
        LoginsDAO loginsdao = new LoginsDAO();

        if (FieldId.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Selecione um login na tabela para alterar!", "Sistema", JOptionPane.INFORMATION_MESSAGE);

        } else if (FieldUser.getText().isEmpty() || FieldSenha.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Há campos vazios, preencha-os para proceder com a alteração!", "Sistema", JOptionPane.INFORMATION_MESSAGE);

        } else try {
            if (TableGerenciarLogins.getValueAt(TableGerenciarLogins.getSelectedRow(), 2).equals("Administrador") && ComboTipoConta.getSelectedItem().equals("Padrão") && loginsdao.VerificarUltimoADM() == 1) {

                JOptionPane.showMessageDialog(null, "É necessário ter pelo menos um login do tipo Administrador!", "Sistema", JOptionPane.INFORMATION_MESSAGE);

            } else if (JOptionPane.showConfirmDialog(null, "Deseja mesmo alterar o login?", "Sistema", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

                logins.setusuario(FieldUser.getText());
                logins.setsenha(FieldSenha.getText());
                logins.settipoconta((String) ComboTipoConta.getSelectedItem());
                logins.setid((int) TableGerenciarLogins.getValueAt(TableGerenciarLogins.getSelectedRow(), 0));
                loginsdao.AtualizarLogins(logins);
                PesquisarTabela(FieldUserGerenciarLogins.getText());
                TravaCamposGerenciarLogins();

            }
        } catch (SQLException ex) {

        }

    }//GEN-LAST:event_BotaoAlterarGerLoginsActionPerformed

    private void BotaoExcluirGerLoginsActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoExcluirGerLoginsActionPerformed
        Logins logins = new Logins();
        LoginsDAO loginsdao = new LoginsDAO();

        try {

            if (loginsdao.VerificarUltimo() == 1 || loginsdao.VerificarUltimoADM() == 1 && TableGerenciarLogins.getValueAt(TableGerenciarLogins.getSelectedRow(), 2).equals("Administrador")) {

                JOptionPane.showMessageDialog(null, "É necessário ter pelo menos um login do tipo Administrador!", "Sistema", JOptionPane.INFORMATION_MESSAGE);

            } else if (FieldId.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Selecione um login na tabela para excluir!", "Sistema", JOptionPane.INFORMATION_MESSAGE);

            } else if (JOptionPane.showConfirmDialog(null, "Deseja mesmo excluir esse login?", "Sistema", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

                logins.setid(Integer.parseInt(FieldId.getText()));
                loginsdao.Removerlogin(logins);
                TravaCamposGerenciarLogins();
                PesquisarTabela(FieldUserGerenciarLogins.getText());
                JOptionPane.showMessageDialog(null, "Login excluído com sucesso!", "Sistema", JOptionPane.INFORMATION_MESSAGE);

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Algo de errado ocorreu! Erro: " + e.getMessage(), "Sistema", JOptionPane.INFORMATION_MESSAGE);

        }

    }//GEN-LAST:event_BotaoExcluirGerLoginsActionPerformed

    private void BotaoNovoCadastroClienteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoNovoCadastroClienteActionPerformed
        DestravaCamposCadastroClientes();
    }//GEN-LAST:event_BotaoNovoCadastroClienteActionPerformed

    private void FieldsenhaCadastrarloginsKeyTyped(KeyEvent evt) {//GEN-FIRST:event_FieldsenhaCadastrarloginsKeyTyped

    }//GEN-LAST:event_FieldsenhaCadastrarloginsKeyTyped

    private void BotaoAlterarGerLogins1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoAlterarGerLogins1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoAlterarGerLogins1ActionPerformed

    private void BotaoCancelarGerLoginsActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoCancelarGerLoginsActionPerformed
        if (JOptionPane.showConfirmDialog(null, "Todos os dados alterados serão perdidos. Deseja continuar?", "Sistema", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

            TravaCamposGerenciarLogins();
            TableGerenciarLoginss();
        }
    }//GEN-LAST:event_BotaoCancelarGerLoginsActionPerformed

    private void ComboGerenciaEscolhaConsertoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ComboGerenciaEscolhaConsertoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboGerenciaEscolhaConsertoActionPerformed

    private void TableConsultaGarantiaMouseClicked(MouseEvent evt) {//GEN-FIRST:event_TableConsultaGarantiaMouseClicked
        try {
            Cliente cliente = new Cliente();
            clienteDAO clientedao = new clienteDAO();
            Txtid.setText(TableConsultaGarantia.getValueAt(TableConsultaGarantia.getSelectedRow(), 0).toString());
            TxtNomeCtt.setText(TableConsultaGarantia.getValueAt(TableConsultaGarantia.getSelectedRow(), 1).toString());
            cliente.setNome(TableConsultaGarantia.getValueAt(TableConsultaGarantia.getSelectedRow(), 1).toString());
            TxtNumCtt.setText(clientedao.BuscarTel(cliente));
        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Erro: " + ex);

        }
    }//GEN-LAST:event_TableConsultaGarantiaMouseClicked

    private void TxtidActionPerformed(ActionEvent evt) {//GEN-FIRST:event_TxtidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtidActionPerformed

    private void TxtMsgWppKeyPressed(KeyEvent evt) {//GEN-FIRST:event_TxtMsgWppKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            String NumConvertido = TxtNumCtt.getText();

            NumConvertido = NumConvertido.replaceAll("\\(", "");
            NumConvertido = NumConvertido.replaceAll("\\)", "");
            NumConvertido = NumConvertido.replaceAll(" ", "");
            NumConvertido = NumConvertido.replaceAll("-", "");

            String telefone = "send?phone=55" + NumConvertido;
            String msg = "&text=" + TxtMsgWpp.getText();
            msg = msg.replaceAll(" ", "%20");

            String url = "https://web.whatsapp.com/" + telefone + msg;

            try {
                Desktop.getDesktop().browse(new URL(url).toURI());
            } catch (MalformedURLException ex) {
                JOptionPane.showMessageDialog(null, "Erro: " + ex);
            } catch (URISyntaxException | IOException ex) {
                JOptionPane.showMessageDialog(null, "Erro: " + ex);
            }

        }
    }//GEN-LAST:event_TxtMsgWppKeyPressed

    private void BotaoGerenciarCli3MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli3MouseClicked
        ConsultarClientes.setVisible(false);
        CadastrarClientes.setVisible(false);
        GerenciarClientes.setVisible(true);
        atualizarTableGerenciarCCliente();
    }//GEN-LAST:event_BotaoGerenciarCli3MouseClicked

    private void BotaoGerenciarCli3MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli3MouseEntered
        setLblColor(BotaoGerenciarCli3);
    }//GEN-LAST:event_BotaoGerenciarCli3MouseEntered

    private void BotaoGerenciarCli3MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli3MouseExited
        ResetColor(BotaoGerenciarCli3);
    }//GEN-LAST:event_BotaoGerenciarCli3MouseExited

    private void BotaoGerenciarCli4MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli4MouseClicked
        RelatorioDAO relatoriocli = new RelatorioDAO();
        relatoriocli.RelatorioCliente();
    }//GEN-LAST:event_BotaoGerenciarCli4MouseClicked

    private void BotaoGerenciarCli4MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli4MouseEntered
        setLblColor(BotaoGerenciarCli4);
    }//GEN-LAST:event_BotaoGerenciarCli4MouseEntered

    private void BotaoGerenciarCli4MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli4MouseExited
        ResetColor(BotaoGerenciarCli4);
    }//GEN-LAST:event_BotaoGerenciarCli4MouseExited

    private void Botao2Via1MouseClicked(MouseEvent evt) {//GEN-FIRST:event_Botao2Via1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Botao2Via1MouseClicked

    private void Botao2Via1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_Botao2Via1ActionPerformed
        if (Txtid.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Selecione uma garantia na tabela para reimpressão!", "Sistema", JOptionPane.INFORMATION_MESSAGE);

        } else {

            RelatorioDAO rel = new RelatorioDAO();

            int cod = Integer.parseInt(Txtid.getText());
            rel.RelatorioGar(cod, TableConsultaGarantia.getValueAt(TableConsultaGarantia.getSelectedRow(), 5).toString());
        }
    }//GEN-LAST:event_Botao2Via1ActionPerformed

    private void CampoDataFormatadaMouseClicked(MouseEvent evt) {//GEN-FIRST:event_CampoDataFormatadaMouseClicked
        if (CampoDataFormatada.getText().equals("  /  /    ")) {
            CampoDataFormatada.setCaretPosition(0);
        }
    }//GEN-LAST:event_CampoDataFormatadaMouseClicked

    private void CampoDataFormatadaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_CampoDataFormatadaKeyPressed

    }//GEN-LAST:event_CampoDataFormatadaKeyPressed

    private void FieldCadastroCPFClienteKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldCadastroCPFClienteKeyPressed

    }//GEN-LAST:event_FieldCadastroCPFClienteKeyPressed

    private void FieldConsultaCPFClienteKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldConsultaCPFClienteKeyPressed

    }//GEN-LAST:event_FieldConsultaCPFClienteKeyPressed

    private void FieldCadastroCPFClienteMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldCadastroCPFClienteMouseClicked
        if (FieldCadastroCPFCliente.getText().equals("   .   .   -  ")) {
            FieldCadastroCPFCliente.setCaretPosition(0);
        }
    }//GEN-LAST:event_FieldCadastroCPFClienteMouseClicked

    private void FieldCadastroTelefoneClienteMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldCadastroTelefoneClienteMouseClicked
        if (FieldCadastroTelefoneCliente.getText().equals("(  )      -    ")) {
            FieldCadastroTelefoneCliente.setCaretPosition(0);
        }
    }//GEN-LAST:event_FieldCadastroTelefoneClienteMouseClicked

    private void FieldConsultaCPFClienteMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldConsultaCPFClienteMouseClicked
        if (FieldConsultaCPFCliente.getText().equals("   .   .   -  ")) {
            FieldConsultaCPFCliente.setCaretPosition(0);
        }
    }//GEN-LAST:event_FieldConsultaCPFClienteMouseClicked

    private void FieldCadastroCPFClienteKeyTyped(KeyEvent evt) {//GEN-FIRST:event_FieldCadastroCPFClienteKeyTyped
        if (FieldCadastroCPFCliente.getText().equals("   .   .   -  ")) {
            FieldCadastroCPFCliente.setCaretPosition(0);
        }
    }//GEN-LAST:event_FieldCadastroCPFClienteKeyTyped

    private void FieldCadastroCPFClienteKeyReleased(KeyEvent evt) {//GEN-FIRST:event_FieldCadastroCPFClienteKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCadastroCPFClienteKeyReleased

    private void TableConsultaClienteMouseClicked(MouseEvent evt) {//GEN-FIRST:event_TableConsultaClienteMouseClicked

    }//GEN-LAST:event_TableConsultaClienteMouseClicked

    private void TableConsultaClienteMouseEntered(MouseEvent evt) {//GEN-FIRST:event_TableConsultaClienteMouseEntered

    }//GEN-LAST:event_TableConsultaClienteMouseEntered

    private void TableConsultaClienteMouseExited(MouseEvent evt) {//GEN-FIRST:event_TableConsultaClienteMouseExited

    }//GEN-LAST:event_TableConsultaClienteMouseExited

    private void CampoGerenciaDataFormatadaMouseClicked(MouseEvent evt) {//GEN-FIRST:event_CampoGerenciaDataFormatadaMouseClicked
        if (CampoGerenciaDataFormatada.getText().equals("  /  /    ")) {
            CampoGerenciaDataFormatada.setCaretPosition(0);
        }
    }//GEN-LAST:event_CampoGerenciaDataFormatadaMouseClicked

    private void BotaoBuscarGerenciarServicos2MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoBuscarGerenciarServicos2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoBuscarGerenciarServicos2MouseClicked

    private void BotaoBuscarGerenciarServicos2ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoBuscarGerenciarServicos2ActionPerformed
        if (FielduserCadastrarlogins.getText().isEmpty() || FieldsenhaCadastrarlogins.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos, por favor!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
                Logins logins = new Logins();
                LoginsDAO loginsdao = new LoginsDAO();

                if (loginsdao.VerificarUltimo() == 0 && ComboEscolhalogins.getSelectedItem().equals("Padrão")) {

                    JOptionPane.showMessageDialog(null, "O primeiro cadastro deve ser do tipo Administrador!", "Sistema", JOptionPane.INFORMATION_MESSAGE);

                } else {

                    logins.setusuario(FielduserCadastrarlogins.getText());
                    logins.setsenha(FieldsenhaCadastrarlogins.getText());
                    logins.settipoconta((String) ComboEscolhalogins.getSelectedItem());
                    loginsdao.Cadastrarlogin(logins);
                    TravaCamposCadastrarLogins();
                    JOptionPane.showMessageDialog(null, "Login cadastrado com sucesso!", "Sistema", JOptionPane.INFORMATION_MESSAGE);

                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar. Pode ser que o usuário esteja em uso!", "Sistema", JOptionPane.INFORMATION_MESSAGE);

            }
        }
    }//GEN-LAST:event_BotaoBuscarGerenciarServicos2ActionPerformed

    private void BotaoAlterarGerLogins2ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoAlterarGerLogins2ActionPerformed
        if (!FieldUserGerenciarLogins.getText().isEmpty()) {
            PesquisarTabela(FieldUserGerenciarLogins.getText());
        } else {
            TableGerenciarLoginss();
        }
    }//GEN-LAST:event_BotaoAlterarGerLogins2ActionPerformed

    private void FieldDiaMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldDiaMouseClicked
        if (FieldDia.getText().equals("Todos")) {

            FieldDia.setText("");

        }
    }//GEN-LAST:event_FieldDiaMouseClicked

    private void FieldDiaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_FieldDiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldDiaActionPerformed

    private void FieldDiaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldDiaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldDiaKeyPressed

    private void FieldValorTotalActionPerformed(ActionEvent evt) {//GEN-FIRST:event_FieldValorTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldValorTotalActionPerformed

    private void BotaoBuscarConsultarGarantiasActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoBuscarConsultarGarantiasActionPerformed
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
        }
    }//GEN-LAST:event_BotaoBuscarConsultarGarantiasActionPerformed

    private void TxtNumCttActionPerformed(ActionEvent evt) {//GEN-FIRST:event_TxtNumCttActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtNumCttActionPerformed

    private void FieldAnoMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldAnoMouseClicked
        if (FieldAno.getText().equals("Todos")) {

            FieldAno.setText("");

        }
    }//GEN-LAST:event_FieldAnoMouseClicked

    private void FieldAnoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_FieldAnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldAnoActionPerformed

    private void FieldAnoKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldAnoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldAnoKeyPressed

    private void FieldDiaKeyTyped(KeyEvent evt) {//GEN-FIRST:event_FieldDiaKeyTyped

    }//GEN-LAST:event_FieldDiaKeyTyped

    private void FieldAnoKeyTyped(KeyEvent evt) {//GEN-FIRST:event_FieldAnoKeyTyped

    }//GEN-LAST:event_FieldAnoKeyTyped

    private void FieldDiaKeyReleased(KeyEvent evt) {//GEN-FIRST:event_FieldDiaKeyReleased

    }//GEN-LAST:event_FieldDiaKeyReleased


    private void FieldAnoKeyReleased(KeyEvent evt) {//GEN-FIRST:event_FieldAnoKeyReleased

    }//GEN-LAST:event_FieldAnoKeyReleased

    private void BotaoBuscarConsultarGarantias4ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoBuscarConsultarGarantias4ActionPerformed
        FieldDia.setText("Todos");
        ComboMes.setSelectedItem("Todos");
        FieldAno.setText("Todos");

        ValorTotal();
    }//GEN-LAST:event_BotaoBuscarConsultarGarantias4ActionPerformed

    private void FieldAnoMouseReleased(MouseEvent evt) {//GEN-FIRST:event_FieldAnoMouseReleased

    }//GEN-LAST:event_FieldAnoMouseReleased

    private void FieldAnoFocusLost(FocusEvent evt) {//GEN-FIRST:event_FieldAnoFocusLost
        if (FieldAno.getText().equals("")) {

            FieldAno.setText("Todos");

        }
    }//GEN-LAST:event_FieldAnoFocusLost

    private void FieldDiaFocusLost(FocusEvent evt) {//GEN-FIRST:event_FieldDiaFocusLost
        if (FieldDia.getText().equals("")) {

            FieldDia.setText("Todos");

        }
    }//GEN-LAST:event_FieldDiaFocusLost

    private void BotaoBuscarConsultarGarantias5ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoBuscarConsultarGarantias5ActionPerformed
        if (FieldValorTotal.getText().equals("R$ 0,00")) {
            FieldDia.setText("Todos");
            ComboMes.setSelectedItem("Todos");
            FieldAno.setText("Todos");
            JOptionPane.showMessageDialog(null, "É necessário ter pelo menos um conserto cadastrado para filtrar!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
        } else {
            ValorTotal();
        }
    }//GEN-LAST:event_BotaoBuscarConsultarGarantias5ActionPerformed

    private void ComboOrdenaGarantiaMouseClicked(MouseEvent evt) {//GEN-FIRST:event_ComboOrdenaGarantiaMouseClicked

    }//GEN-LAST:event_ComboOrdenaGarantiaMouseClicked

    private void ComboOrdenaGarantiaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ComboOrdenaGarantiaActionPerformed
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
    }//GEN-LAST:event_ComboOrdenaGarantiaActionPerformed

    private void ComboOrdenaGarantia1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ComboOrdenaGarantia1ActionPerformed
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
    }//GEN-LAST:event_ComboOrdenaGarantia1ActionPerformed

    private void FieldValorGarantiaMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldValorGarantiaMouseClicked

    }//GEN-LAST:event_FieldValorGarantiaMouseClicked

    private void FieldValorGarantiaFocusLost(FocusEvent evt) {//GEN-FIRST:event_FieldValorGarantiaFocusLost
        if (FieldValorGarantia.getText().isEmpty()) {

            lblConserto3.setVisible(false);

        }
    }//GEN-LAST:event_FieldValorGarantiaFocusLost

    private void BotaoBuscarGerenciarServicos3MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoBuscarGerenciarServicos3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoBuscarGerenciarServicos3MouseClicked

    private void BotaoBuscarGerenciarServicos3ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoBuscarGerenciarServicos3ActionPerformed
        DestravaCamposCadastrarLogins();
    }//GEN-LAST:event_BotaoBuscarGerenciarServicos3ActionPerformed

    private void BotaoBuscarGerenciarServicos4MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoBuscarGerenciarServicos4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoBuscarGerenciarServicos4MouseClicked

    private void BotaoBuscarGerenciarServicos4ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoBuscarGerenciarServicos4ActionPerformed
        TravaCamposCadastrarLogins();
    }//GEN-LAST:event_BotaoBuscarGerenciarServicos4ActionPerformed

    private void jLabel6MouseExited(MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited

    }//GEN-LAST:event_jLabel6MouseExited

    private void jLabel6MouseEntered(MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseEntered

    }//GEN-LAST:event_jLabel6MouseEntered

    private void jLabel6MouseClicked(MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        if (jLabel6.getText().equals("Ver senha")) {
            FieldsenhaCadastrarlogins.setEchoChar('\u0000');

            jLabel6.setText("Esconder senha");
        } else {
            jLabel6.setText("Ver senha");
            FieldsenhaCadastrarlogins.setEchoChar('●');
        }


    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseClicked(MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        if (jLabel7.getText().equals("Ver senha")) {
            FieldSenha.setEchoChar('\u0000');

            jLabel7.setText("Esconder senha");
        } else {
            jLabel7.setText("Ver senha");
            FieldSenha.setEchoChar('●');;
        }
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel7MouseEntered(MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseEntered

    }//GEN-LAST:event_jLabel7MouseEntered

    private void jLabel7MouseExited(MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited

    }//GEN-LAST:event_jLabel7MouseExited

    private void FieldSenhaKeyTyped(KeyEvent evt) {//GEN-FIRST:event_FieldSenhaKeyTyped

    }//GEN-LAST:event_FieldSenhaKeyTyped

    private void FieldValorGarantiaGerMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldValorGarantiaGerMouseClicked
        lblConserto4.setVisible(true);

        String valor = FieldValorGarantia.getText();

        valor = valor.replaceAll(" ", "");

        FieldValorGarantia.setText(valor);
    }//GEN-LAST:event_FieldValorGarantiaGerMouseClicked

    private void FieldValorGarantiaGerFocusLost(FocusEvent evt) {//GEN-FIRST:event_FieldValorGarantiaGerFocusLost
        if (FieldValorGarantiaGer.getText().isEmpty()) {
            lblConserto4.setVisible(false);
        }
    }//GEN-LAST:event_FieldValorGarantiaGerFocusLost

    private void FieldCadastroTelefoneClienteKeyTyped(KeyEvent evt) {//GEN-FIRST:event_FieldCadastroTelefoneClienteKeyTyped
        if (FieldCadastroTelefoneCliente.getText().equals("(  )      -    ")) {
            FieldCadastroTelefoneCliente.setCaretPosition(0);
        }
    }//GEN-LAST:event_FieldCadastroTelefoneClienteKeyTyped

    private void FieldCadastroNomeClienteKeyTyped(KeyEvent evt) {//GEN-FIRST:event_FieldCadastroNomeClienteKeyTyped

    }//GEN-LAST:event_FieldCadastroNomeClienteKeyTyped

    private void FieldConsultaCPFClienteKeyTyped(KeyEvent evt) {//GEN-FIRST:event_FieldConsultaCPFClienteKeyTyped
        if (FieldConsultaCPFCliente.getText().equals("   .   .   -  ")) {
            FieldConsultaCPFCliente.setCaretPosition(0);
        }
    }//GEN-LAST:event_FieldConsultaCPFClienteKeyTyped

    private void TableGerenciarClienteMouseClicked(MouseEvent evt) {//GEN-FIRST:event_TableGerenciarClienteMouseClicked
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

    private void BotaoExcluirGerenciarGarantia1MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoExcluirGerenciarGarantia1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoExcluirGerenciarGarantia1MouseClicked

    private void BotaoExcluirGerenciarGarantia1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoExcluirGerenciarGarantia1ActionPerformed
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

    private void BotaoAlterarGerenciarGarantia1MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoAlterarGerenciarGarantia1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoAlterarGerenciarGarantia1MouseClicked

    private void BotaoAlterarGerenciarGarantia1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoAlterarGerenciarGarantia1ActionPerformed
        if (FieldIDGerenciarClientes.getText().isEmpty() || FieldNomeGerenciarClientes.getText().isEmpty() || FieldCPFGerenciarClientes.getText().equals("   .   .   -  ") || FieldTelefoneGerenciarClientes.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios (*) para proceder com a alteração!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
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

    private void FieldNomeGerenciarClientesMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldNomeGerenciarClientesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNomeGerenciarClientesMouseClicked

    private void FieldNomeGerenciarClientesKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldNomeGerenciarClientesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNomeGerenciarClientesKeyPressed

    private void FieldIDGerenciarClientesMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldIDGerenciarClientesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldIDGerenciarClientesMouseClicked

    private void FieldIDGerenciarClientesKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldIDGerenciarClientesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldIDGerenciarClientesKeyPressed

    private void FieldCidadeGerenciarClientesMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldCidadeGerenciarClientesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCidadeGerenciarClientesMouseClicked

    private void FieldCidadeGerenciarClientesActionPerformed(ActionEvent evt) {//GEN-FIRST:event_FieldCidadeGerenciarClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCidadeGerenciarClientesActionPerformed

    private void FieldCidadeGerenciarClientesKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldCidadeGerenciarClientesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCidadeGerenciarClientesKeyPressed

    private void FieldEnderecoGerenciarClientesMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldEnderecoGerenciarClientesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldEnderecoGerenciarClientesMouseClicked

    private void FieldEnderecoGerenciarClientesActionPerformed(ActionEvent evt) {//GEN-FIRST:event_FieldEnderecoGerenciarClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldEnderecoGerenciarClientesActionPerformed

    private void FieldEnderecoGerenciarClientesKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldEnderecoGerenciarClientesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldEnderecoGerenciarClientesKeyPressed

    private void FieldEmailGerenciarClientesMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldEmailGerenciarClientesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldEmailGerenciarClientesMouseClicked

    private void FieldEmailGerenciarClientesActionPerformed(ActionEvent evt) {//GEN-FIRST:event_FieldEmailGerenciarClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldEmailGerenciarClientesActionPerformed

    private void FieldEmailGerenciarClientesKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldEmailGerenciarClientesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldEmailGerenciarClientesKeyPressed

    private void BotaoCancelarGerenciarServicos2MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoCancelarGerenciarServicos2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoCancelarGerenciarServicos2MouseClicked

    private void BotaoCancelarGerenciarServicos2ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoCancelarGerenciarServicos2ActionPerformed
        SalvarCamposGerenciarClientes();
    }//GEN-LAST:event_BotaoCancelarGerenciarServicos2ActionPerformed

    private void FieldCPFGerenciarClientesMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldCPFGerenciarClientesMouseClicked
        if (FieldCPFGerenciarClientes.getText().equals("   .   .   -  ")) {
            FieldCPFGerenciarClientes.setCaretPosition(0);
        }
    }//GEN-LAST:event_FieldCPFGerenciarClientesMouseClicked

    private void FieldCPFGerenciarClientesKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldCPFGerenciarClientesKeyPressed

    }//GEN-LAST:event_FieldCPFGerenciarClientesKeyPressed

    private void FieldGerenciarNomeClienteMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldGerenciarNomeClienteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldGerenciarNomeClienteMouseClicked

    private void FieldGerenciarNomeClienteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_FieldGerenciarNomeClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldGerenciarNomeClienteActionPerformed

    private void FieldGerenciarNomeClienteKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldGerenciarNomeClienteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldGerenciarNomeClienteKeyPressed

    private void BotaoBuscarConsultaCliente1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BotaoBuscarConsultaCliente1ActionPerformed
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

    private void BotaoGerenciarCliGerMouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliGerMouseClicked
        ConsultarClientes.setVisible(false);
        CadastrarClientes.setVisible(false);
        GerenciarClientes.setVisible(true);
        atualizarTableGerenciarCCliente();
    }//GEN-LAST:event_BotaoGerenciarCliGerMouseClicked

    private void BotaoGerenciarCliGerMouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliGerMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliGerMouseEntered

    private void BotaoGerenciarCliGerMouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCliGerMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCliGerMouseExited

    private void BotaoConsultarCliGerMouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliGerMouseClicked
        ConsultarClientes.setVisible(true);
        CadastrarClientes.setVisible(false);
        GerenciarClientes.setVisible(false);
        atualizarTabelaConsultaCliente();// TODO add your handling code here:
    }//GEN-LAST:event_BotaoConsultarCliGerMouseClicked

    private void BotaoConsultarCliGerMouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliGerMouseEntered
        BotaoConsultarCliGer.setBackground(new Color(220, 220, 220));
    }//GEN-LAST:event_BotaoConsultarCliGerMouseEntered

    private void BotaoConsultarCliGerMouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoConsultarCliGerMouseExited
        BotaoConsultarCliGer.setBackground(new Color(230, 230, 230));
    }//GEN-LAST:event_BotaoConsultarCliGerMouseExited

    private void BotaoCadastrarCliGerMouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliGerMouseClicked
        ConsultarClientes.setVisible(false);
        CadastrarClientes.setVisible(true);
        GerenciarClientes.setVisible(false);
    }//GEN-LAST:event_BotaoCadastrarCliGerMouseClicked

    private void BotaoCadastrarCliGerMouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliGerMouseEntered
        BotaoCadastrarCliGer.setBackground(new Color(220, 220, 220));
    }//GEN-LAST:event_BotaoCadastrarCliGerMouseEntered

    private void BotaoCadastrarCliGerMouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoCadastrarCliGerMouseExited
        BotaoCadastrarCliGer.setBackground(new Color(230, 230, 230));
    }//GEN-LAST:event_BotaoCadastrarCliGerMouseExited

    private void BotaoGerenciarCli5MouseClicked(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli5MouseClicked
        RelatorioDAO relatoriocli = new RelatorioDAO();
        relatoriocli.RelatorioCliente();// TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCli5MouseClicked

    private void BotaoGerenciarCli5MouseEntered(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli5MouseEntered
        setLblColor(BotaoGerenciarCli5);// TODO add your handling code here:
    }//GEN-LAST:event_BotaoGerenciarCli5MouseEntered

    private void BotaoGerenciarCli5MouseExited(MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli5MouseExited
        ResetColor(BotaoGerenciarCli5);
    }//GEN-LAST:event_BotaoGerenciarCli5MouseExited

    private void FieldGerenciarCPFClienteMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldGerenciarCPFClienteMouseClicked
        if (FieldGerenciarCPFCliente.getText().equals("   .   .   -  ")) {
            FieldGerenciarCPFCliente.setCaretPosition(0);
        }
    }//GEN-LAST:event_FieldGerenciarCPFClienteMouseClicked

    private void FieldGerenciarCPFClienteKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FieldGerenciarCPFClienteKeyPressed

    }//GEN-LAST:event_FieldGerenciarCPFClienteKeyPressed

    private void FieldTelefoneGerenciarClientesMouseClicked(MouseEvent evt) {//GEN-FIRST:event_FieldTelefoneGerenciarClientesMouseClicked
        if (FieldTelefoneGerenciarClientes.getText().equals("(  )      -    ")) {
            FieldTelefoneGerenciarClientes.setCaretPosition(0);
        }
    }//GEN-LAST:event_FieldTelefoneGerenciarClientesMouseClicked

    private void FieldTelefoneGerenciarClientesKeyTyped(KeyEvent evt) {//GEN-FIRST:event_FieldTelefoneGerenciarClientesKeyTyped
        if (FieldTelefoneGerenciarClientes.getText().equals("(  )      -    ")) {
            FieldTelefoneGerenciarClientes.setCaretPosition(0);
        }
    }//GEN-LAST:event_FieldTelefoneGerenciarClientesKeyTyped

    private void FieldGerenciarCPFClienteKeyTyped(KeyEvent evt) {//GEN-FIRST:event_FieldGerenciarCPFClienteKeyTyped
        if (FieldGerenciarCPFCliente.getText().equals("   .   .   -  ")) {
            FieldGerenciarCPFCliente.setCaretPosition(0);
        }
    }//GEN-LAST:event_FieldGerenciarCPFClienteKeyTyped

    private void jLabel4MouseClicked(MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        try {
            clienteDAO clidao = new clienteDAO();
            GarantiaDAO gardao = new GarantiaDAO();
            jLabel10.setText("JÁ FORAM CONSERTADOS " + gardao.TotalConsertos() + " CELULARES");
            jLabel8.setText("JÁ FORAM CADASTRADOS " + clidao.TotalClientes() + " CLIENTES");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao calcular totais de clientes e serviços!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
        }
        SideBoard.setVisible(false);
        PaneMae.setVisible(false);
        PaneTelaInicial.setVisible(true);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void FieldCPFGerenciarClientesKeyTyped(KeyEvent evt) {//GEN-FIRST:event_FieldCPFGerenciarClientesKeyTyped
        if (FieldCPFGerenciarClientes.getText().equals("   .   .   -  ")) {
            FieldCPFGerenciarClientes.setCaretPosition(0);
        }
    }//GEN-LAST:event_FieldCPFGerenciarClientesKeyTyped

    private void FieldValorGarantiaKeyTyped(KeyEvent evt) {//GEN-FIRST:event_FieldValorGarantiaKeyTyped

        lblConserto3.setVisible(true);

    }//GEN-LAST:event_FieldValorGarantiaKeyTyped

    private void CampoDataFormatadaKeyTyped(KeyEvent evt) {//GEN-FIRST:event_CampoDataFormatadaKeyTyped
        if (CampoDataFormatada.getText().equals("  /  /    ")) {
            CampoDataFormatada.setCaretPosition(0);
        }
    }//GEN-LAST:event_CampoDataFormatadaKeyTyped

    private void CampoGerenciaDataFormatadaKeyTyped(KeyEvent evt) {//GEN-FIRST:event_CampoGerenciaDataFormatadaKeyTyped
        if (CampoGerenciaDataFormatada.getText().equals("  /  /    ")) {
            CampoGerenciaDataFormatada.setCaretPosition(0);
        }
    }//GEN-LAST:event_CampoGerenciaDataFormatadaKeyTyped

    private void jLabel11MouseClicked(MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        PaneTelaInicial.setVisible(false);
        PaneMae.setVisible(true);
        SideBoard.setVisible(true);
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel11MouseEntered(MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseEntered
        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 28));
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    }//GEN-LAST:event_jLabel11MouseEntered

    private void jLabel11MouseExited(MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseExited
        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 27));
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_jLabel11MouseExited

    private void jLabel4MouseEntered(MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel4MouseExited(MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_jLabel4MouseExited

    private void CampoNomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CampoNomeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_CampoNomeMouseClicked

    private void CampoNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CampoNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CampoNomeActionPerformed

    private void CampoNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CampoNomeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CampoNomeKeyPressed

    private void CampoNomeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CampoNomeKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_CampoNomeKeyTyped

    private void FieldConsultaNomeGarantiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldConsultaNomeGarantiaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldConsultaNomeGarantiaMouseClicked

    private void FieldConsultaNomeGarantiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldConsultaNomeGarantiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldConsultaNomeGarantiaActionPerformed

    private void FieldConsultaNomeGarantiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldConsultaNomeGarantiaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldConsultaNomeGarantiaKeyPressed

    private void FieldConsultaNomeGarantiaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldConsultaNomeGarantiaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldConsultaNomeGarantiaKeyTyped

    private void lblCPF1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCPF1MouseClicked

    }//GEN-LAST:event_lblCPF1MouseClicked

    private void BotaoGerenciarCli6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli6MouseClicked
        RelatorioDAO reladao = new RelatorioDAO();
        reladao.RelatorioGarantia();
    }//GEN-LAST:event_BotaoGerenciarCli6MouseClicked

    private void BotaoGerenciarCli6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli6MouseEntered
        BotaoGerenciarCli6.setBackground(new Color(220, 220, 220));
    }//GEN-LAST:event_BotaoGerenciarCli6MouseEntered

    private void BotaoGerenciarCli6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli6MouseExited
        BotaoGerenciarCli6.setBackground(new Color(230, 230, 230));
    }//GEN-LAST:event_BotaoGerenciarCli6MouseExited

    private void BotaoGerenciarCli7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli7MouseClicked
        RelatorioDAO reladao = new RelatorioDAO();
        reladao.RelatorioGarantia();
    }//GEN-LAST:event_BotaoGerenciarCli7MouseClicked

    private void BotaoGerenciarCli7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli7MouseEntered
        BotaoGerenciarCli7.setBackground(new Color(220, 220, 220));
    }//GEN-LAST:event_BotaoGerenciarCli7MouseEntered

    private void BotaoGerenciarCli7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli7MouseExited
        BotaoGerenciarCli7.setBackground(new Color(230, 230, 230));
    }//GEN-LAST:event_BotaoGerenciarCli7MouseExited

    private void BotaoGerenciarCli8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli8MouseClicked
        RelatorioDAO reladao = new RelatorioDAO();
        reladao.RelatorioGarantia();
    }//GEN-LAST:event_BotaoGerenciarCli8MouseClicked

    private void BotaoGerenciarCli8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli8MouseEntered
        BotaoGerenciarCli8.setBackground(new Color(220, 220, 220));
    }//GEN-LAST:event_BotaoGerenciarCli8MouseEntered

    private void BotaoGerenciarCli8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli8MouseExited
        BotaoGerenciarCli8.setBackground(new Color(230, 230, 230));
    }//GEN-LAST:event_BotaoGerenciarCli8MouseExited

    private void BotaoGerenciarCli9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli9MouseClicked
        RelatorioDAO reladao = new RelatorioDAO();
        reladao.RelatorioGarantia();
    }//GEN-LAST:event_BotaoGerenciarCli9MouseClicked

    private void BotaoGerenciarCli9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli9MouseEntered
        BotaoGerenciarCli9.setBackground(new Color(220, 220, 220));
    }//GEN-LAST:event_BotaoGerenciarCli9MouseEntered

    private void BotaoGerenciarCli9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotaoGerenciarCli9MouseExited
        BotaoGerenciarCli9.setBackground(new Color(230, 230, 230));
    }//GEN-LAST:event_BotaoGerenciarCli9MouseExited

    private void FieldDuracaoGerenciarConsertosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldDuracaoGerenciarConsertosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldDuracaoGerenciarConsertosMouseClicked

    private void FieldDuracaoGerenciarConsertosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldDuracaoGerenciarConsertosKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldDuracaoGerenciarConsertosKeyPressed

    private void FieldUserMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldUserMouseEntered

    }//GEN-LAST:event_FieldUserMouseEntered

    private void FieldSenhaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldSenhaMouseEntered
   
    }//GEN-LAST:event_FieldSenhaMouseEntered

    private void FieldUserMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldUserMouseExited
   
    }//GEN-LAST:event_FieldUserMouseExited

    private void FieldSenhaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldSenhaMouseExited
       
    }//GEN-LAST:event_FieldSenhaMouseExited

    public void setLblColor(JLabel lbl) {
        lbl.setBackground(new Color(220, 220, 220));
    }

    public void ResetColor(JLabel lbl) {
        lbl.setBackground(new Color(230, 230, 230));
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AdicionarLogins;
    private javax.swing.JButton Botao2Via1;
    private javax.swing.JButton BotaoAlterarGerLogins;
    private javax.swing.JButton BotaoAlterarGerLogins1;
    private javax.swing.JButton BotaoAlterarGerLogins2;
    private javax.swing.JButton BotaoAlterarGerenciarGarantia;
    private javax.swing.JButton BotaoAlterarGerenciarGarantia1;
    private javax.swing.JButton BotaoAlterarGerenciarServicos;
    private javax.swing.JButton BotaoBuscarConsultaCliente;
    private javax.swing.JButton BotaoBuscarConsultaCliente1;
    private javax.swing.JButton BotaoBuscarConsultarGarantias;
    private javax.swing.JButton BotaoBuscarConsultarGarantias1;
    private javax.swing.JButton BotaoBuscarConsultarGarantias2;
    private javax.swing.JButton BotaoBuscarConsultarGarantias3;
    private javax.swing.JButton BotaoBuscarConsultarGarantias4;
    private javax.swing.JButton BotaoBuscarConsultarGarantias5;
    private javax.swing.JButton BotaoBuscarGarantias;
    private javax.swing.JButton BotaoBuscarGerenciarServicos;
    private javax.swing.JButton BotaoBuscarGerenciarServicos1;
    private javax.swing.JButton BotaoBuscarGerenciarServicos2;
    private javax.swing.JButton BotaoBuscarGerenciarServicos3;
    private javax.swing.JButton BotaoBuscarGerenciarServicos4;
    private javax.swing.JLabel BotaoCadastrarCliConsul;
    private javax.swing.JLabel BotaoCadastrarCliConsul1;
    private javax.swing.JLabel BotaoCadastrarCliConsul2;
    private javax.swing.JLabel BotaoCadastrarCliConsul3;
    private javax.swing.JLabel BotaoCadastrarCliConsul5;
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
    private javax.swing.JLabel BotaoConsultarCliConsul5;
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
    private javax.swing.JLabel BotaoGerenciarCli3;
    private javax.swing.JLabel BotaoGerenciarCli4;
    private javax.swing.JLabel BotaoGerenciarCli5;
    private javax.swing.JLabel BotaoGerenciarCli6;
    private javax.swing.JLabel BotaoGerenciarCli7;
    private javax.swing.JLabel BotaoGerenciarCli8;
    private javax.swing.JLabel BotaoGerenciarCli9;
    private javax.swing.JLabel BotaoGerenciarCliConsul;
    private javax.swing.JLabel BotaoGerenciarCliConsul1;
    private javax.swing.JLabel BotaoGerenciarCliConsul2;
    private javax.swing.JLabel BotaoGerenciarCliConsul3;
    private javax.swing.JLabel BotaoGerenciarCliGer;
    private javax.swing.JLabel BotaoGerenciarCliGer1;
    private javax.swing.JLabel BotaoGerenciarCliGer2;
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
    private javax.swing.JComboBox<String> ComboEscolhalogins;
    private javax.swing.JComboBox<String> ComboGerenciaEscolhaConserto;
    public javax.swing.JComboBox<String> ComboMes;
    private javax.swing.JComboBox<String> ComboOrdenaGarantia;
    private javax.swing.JComboBox<String> ComboOrdenaGarantia1;
    private javax.swing.JComboBox<String> ComboTipoConta;
    private javax.swing.JPanel ConsultarClientes;
    private javax.swing.JPanel ConsultarGarantias;
    private javax.swing.JPanel ConsultarServicos;
    private javax.swing.JPanel ContatarClientes;
    private javax.swing.JPanel DadosAlteraClientes;
    private javax.swing.JPanel DadosAlteraGarantia;
    private javax.swing.JPanel DadosAlteraServicos;
    public javax.swing.JTextField FieldAno;
    private javax.swing.JFormattedTextField FieldCPFGerenciarClientes;
    private javax.swing.JFormattedTextField FieldCadastroCPFCliente;
    private javax.swing.JTextField FieldCadastroCidadeCliente;
    private javax.swing.JTextField FieldCadastroEmailCliente;
    private javax.swing.JTextField FieldCadastroEnderecoCliente;
    private javax.swing.JTextField FieldCadastroNomeCliente;
    private javax.swing.JFormattedTextField FieldCadastroTelefoneCliente;
    private javax.swing.JTextField FieldCidadeGerenciarClientes;
    private javax.swing.JFormattedTextField FieldConsultaCPFCliente;
    private javax.swing.JTextField FieldConsultaDescricaoGarantias;
    private javax.swing.JTextField FieldConsultaNomeCliente;
    private javax.swing.JTextField FieldConsultaNomeGarantia;
    public javax.swing.JTextField FieldDia;
    private javax.swing.JTextField FieldDuracaoCadastrarConsertos;
    private javax.swing.JTextField FieldDuracaoGerenciarConsertos;
    private javax.swing.JTextField FieldEmailGerenciarClientes;
    private javax.swing.JTextField FieldEnderecoGerenciarClientes;
    private javax.swing.JFormattedTextField FieldGerenciarCPFCliente;
    private javax.swing.JTextField FieldGerenciarNomeCliente;
    private javax.swing.JTextField FieldIDGerenciarClientes;
    private javax.swing.JTextField FieldIDGerenciarConsertos;
    private javax.swing.JTextField FieldIDGerenciarGarantia;
    private javax.swing.JTextField FieldId;
    private javax.swing.JTextField FieldNomeCadastrarConsertos;
    private javax.swing.JTextField FieldNomeGerenciarClientes;
    private javax.swing.JTextField FieldNomeGerenciarConsertos;
    private javax.swing.JTextField FieldNomeGerenciarServicos;
    private javax.swing.JTextField FieldNomeGerenciarServicos1;
    private javax.swing.JPasswordField FieldSenha;
    private javax.swing.JFormattedTextField FieldTelefoneGerenciarClientes;
    private javax.swing.JTextField FieldUser;
    private javax.swing.JTextField FieldUserGerenciarLogins;
    private javax.swing.JTextField FieldValorGarantia;
    private javax.swing.JTextField FieldValorGarantiaGer;
    public javax.swing.JFormattedTextField FieldValorTotal;
    private javax.swing.JPasswordField FieldsenhaCadastrarlogins;
    private javax.swing.JTextField FielduserCadastrarlogins;
    private javax.swing.JPanel GerenciarCadastros;
    private javax.swing.JPanel GerenciarClientes;
    private javax.swing.JPanel GerenciarGarantias;
    private javax.swing.JPanel GerenciarLogins;
    private javax.swing.JPanel GerenciarServicos;
    private javax.swing.JLabel Layout;
    private javax.swing.JPanel PaneCadastros;
    private javax.swing.JPanel PaneClientes;
    private javax.swing.JPanel PaneGarantias;
    private javax.swing.JPanel PaneMae;
    private javax.swing.JPanel PaneServicos;
    private javax.swing.JPanel PaneTelaInicial;
    private javax.swing.JPanel SideBoard;
    private javax.swing.JTable TabelaConsultaManutencao;
    private javax.swing.JTable TableConsultaCliente;
    private javax.swing.JTable TableConsultaGarantia;
    private javax.swing.JTable TableGerenciarCliente;
    private javax.swing.JTable TableGerenciarGarantia;
    private javax.swing.JTable TableGerenciarLogins;
    private javax.swing.JTable TableGerenciarServicos;
    private javax.swing.JTextField TxtMsgWpp;
    private javax.swing.JTextField TxtNomeCtt;
    private javax.swing.JFormattedTextField TxtNumCtt;
    private javax.swing.JTextField Txtid;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
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
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel4;
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
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator24;
    private javax.swing.JSeparator jSeparator25;
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
    private javax.swing.JSeparator jSeparator38;
    private javax.swing.JSeparator jSeparator39;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel lblCPF1;
    private javax.swing.JLabel lblCPF10;
    private javax.swing.JLabel lblCPF11;
    private javax.swing.JLabel lblCPF13;
    private javax.swing.JLabel lblCPF14;
    private javax.swing.JLabel lblCPF15;
    private javax.swing.JLabel lblCPF16;
    private javax.swing.JLabel lblCPF2;
    private javax.swing.JLabel lblCPF3;
    private javax.swing.JLabel lblCPF4;
    private javax.swing.JLabel lblCPF5;
    private javax.swing.JLabel lblCPF6;
    private javax.swing.JLabel lblCPF7;
    private javax.swing.JLabel lblCPF8;
    private javax.swing.JLabel lblCPF9;
    private javax.swing.JLabel lblConserto;
    private javax.swing.JLabel lblConserto1;
    private javax.swing.JLabel lblConserto2;
    private javax.swing.JLabel lblConserto3;
    private javax.swing.JLabel lblConserto4;
    private javax.swing.JLabel lblConserto5;
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
