/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.LoginsDAO;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TelaLogin extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public void login() {

        String sql = "select * from cadastros.logins where usuario = ? and senha =?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, TxtUsuario.getText());
            pst.setString(2, TxtSenha.getText());
            rs = pst.executeQuery();

            if (rs.next()) {

                Principal pri = new Principal();
                pri.setVisible(true);

            } else {
                lblDados.setForeground(new Color(153, 0, 0));
                Separadoruser.setForeground(new Color(153, 0, 0));
                SeparadorSenha.setForeground(new Color(153, 0, 0));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public TelaLogin() {
        initComponents();
        lblDados.setForeground(new Color(240, 240, 240));

        PaneInicial.setVisible(true);
    }

    public boolean checkLogin(String login) {
        return login.equals("Administrador");
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

    public boolean firstuser(String login) {
        return login.equals("admin");
    }

    public boolean firstsenha(String login) {
        return login.equals("admin");
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PaneInicial = new javax.swing.JPanel();
        BotaoSalvarCadastroCliente = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lblDados = new javax.swing.JLabel();
        Separadoruser = new javax.swing.JSeparator();
        SeparadorSenha = new javax.swing.JSeparator();
        TxtUsuario = new javax.swing.JTextField();
        TxtSenha = new javax.swing.JPasswordField();
        ComboEscolhatipoconta = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(514, 681));
        setMinimumSize(new java.awt.Dimension(514, 681));
        setPreferredSize(null);
        setResizable(false);

        PaneInicial.setMaximumSize(null);
        PaneInicial.setName(""); // NOI18N
        PaneInicial.setPreferredSize(new java.awt.Dimension(514, 681));

        BotaoSalvarCadastroCliente.setBackground(new java.awt.Color(230, 230, 230));
        BotaoSalvarCadastroCliente.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoSalvarCadastroCliente.setText("Entrar");
        BotaoSalvarCadastroCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoSalvarCadastroCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoSalvarCadastroClienteActionPerformed(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/LogoLogin.png"))); // NOI18N
        jLabel3.setToolTipText("");

        lblDados.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        lblDados.setForeground(new java.awt.Color(153, 0, 0));
        lblDados.setText("Dados inválidos!");

        Separadoruser.setBackground(new java.awt.Color(0, 0, 0));
        Separadoruser.setForeground(new java.awt.Color(240, 240, 240));

        SeparadorSenha.setBackground(new java.awt.Color(0, 0, 0));
        SeparadorSenha.setForeground(new java.awt.Color(240, 240, 240));

        TxtUsuario.setBackground(new java.awt.Color(240, 240, 240));
        TxtUsuario.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        TxtUsuario.setBorder(null);
        TxtUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TxtUsuarioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                TxtUsuarioFocusLost(evt);
            }
        });
        TxtUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TxtUsuarioMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                TxtUsuarioMouseReleased(evt);
            }
        });
        TxtUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtUsuarioActionPerformed(evt);
            }
        });
        TxtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtUsuarioKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TxtUsuarioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtUsuarioKeyTyped(evt);
            }
        });

        TxtSenha.setBackground(new java.awt.Color(240, 240, 240));
        TxtSenha.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        TxtSenha.setBorder(null);
        TxtSenha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TxtSenhaFocusGained(evt);
            }
        });
        TxtSenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TxtSenhaMouseClicked(evt);
            }
        });
        TxtSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtSenhaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtSenhaKeyTyped(evt);
            }
        });

        ComboEscolhatipoconta.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        ComboEscolhatipoconta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Padrão", "Administrador" }));
        ComboEscolhatipoconta.setBorder(null);
        ComboEscolhatipoconta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ComboEscolhatipocontaFocusGained(evt);
            }
        });
        ComboEscolhatipoconta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ComboEscolhatipocontaMouseClicked(evt);
            }
        });
        ComboEscolhatipoconta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboEscolhatipocontaActionPerformed(evt);
            }
        });
        ComboEscolhatipoconta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ComboEscolhatipocontaKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel2.setText("Usuário");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel4.setText("Senha");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel5.setText("Tipo de conta");

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

        javax.swing.GroupLayout PaneInicialLayout = new javax.swing.GroupLayout(PaneInicial);
        PaneInicial.setLayout(PaneInicialLayout);
        PaneInicialLayout.setHorizontalGroup(
            PaneInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PaneInicialLayout.createSequentialGroup()
                .addGap(130, 131, Short.MAX_VALUE)
                .addGroup(PaneInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(TxtSenha)
                    .addComponent(Separadoruser)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(TxtUsuario)
                    .addComponent(SeparadorSenha)
                    .addComponent(ComboEscolhatipoconta, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(130, 130, 130))
            .addGroup(PaneInicialLayout.createSequentialGroup()
                .addGroup(PaneInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PaneInicialLayout.createSequentialGroup()
                        .addGap(182, 182, 182)
                        .addGroup(PaneInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BotaoSalvarCadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PaneInicialLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(lblDados))))
                    .addGroup(PaneInicialLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PaneInicialLayout.setVerticalGroup(
            PaneInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaneInicialLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel3)
                .addGap(56, 56, 56)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TxtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Separadoruser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TxtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(SeparadorSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLabel6)
                .addGap(6, 6, 6)
                .addComponent(jLabel5)
                .addGap(13, 13, 13)
                .addComponent(ComboEscolhatipoconta, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82)
                .addComponent(BotaoSalvarCadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(lblDados)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PaneInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PaneInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited

    }//GEN-LAST:event_jLabel6MouseExited

    private void jLabel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseEntered

    }//GEN-LAST:event_jLabel6MouseEntered

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        if (jLabel6.getText().equals("Ver senha")) {
            TxtSenha.setEchoChar('\u0000');

            jLabel6.setText("Esconder senha");
        } else {
            jLabel6.setText("Ver senha");
            TxtSenha.setEchoChar('●');;
        }
    }//GEN-LAST:event_jLabel6MouseClicked

    private void ComboEscolhatipocontaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ComboEscolhatipocontaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            LoginsDAO loginsdao = new LoginsDAO();

            try {
                if (loginsdao.VerificarUltimo() == 0) {

                    if (firstuser(TxtUsuario.getText()) && firstsenha(TxtSenha.getText()) && checkLogin((String) ComboEscolhatipoconta.getSelectedItem())) {

                        PrincipalADM prin = new PrincipalADM();
                        prin.setVisible(true);
                        this.dispose();

                        JOptionPane.showMessageDialog(null, "No momento, há somente o login padrão do sistema: \"Usuário = admin | Senha = admin | Tipo de conta = Administrador\". Cadastre um login seguro para desativar automaticamente o login padrão!", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                                               
                    } else {
                        lblDados.setForeground(new Color(153, 0, 0));
                        Separadoruser.setBackground(new Color(153, 0, 0));
                        SeparadorSenha.setBackground(new Color(153, 0, 0));
                        TxtUsuario.setText("");
                        TxtSenha.setText("");
                        ComboEscolhatipoconta.setSelectedItem("Padrão");
                        JOptionPane.showMessageDialog(null, "Tente: \"Usuário = admin | Senha = admin | Tipo de conta = Administrador\"!", "Sistema", JOptionPane.INFORMATION_MESSAGE);

                    }

                } else {

                    if (loginsdao.checkLogin(TxtUsuario.getText(), TxtSenha.getText(), (String) ComboEscolhatipoconta.getSelectedItem())) {
                        if (checkLogin((String) ComboEscolhatipoconta.getSelectedItem())) {
                            PrincipalADM prin = new PrincipalADM();
                            prin.setVisible(true);
                            this.dispose();
                        } else {
                            Principal prin = new Principal();
                            prin.setVisible(true);
                            this.dispose();
                        }
                    } else {
                        lblDados.setForeground(new Color(153, 0, 0));
                        Separadoruser.setBackground(new Color(153, 0, 0));
                        SeparadorSenha.setBackground(new Color(153, 0, 0));
                        TxtUsuario.setText("");
                        TxtSenha.setText("");
                        ComboEscolhatipoconta.setSelectedItem("Padrão");
                    }

                }
            } catch (SQLException ex) {

            }
        }
    }//GEN-LAST:event_ComboEscolhatipocontaKeyPressed

    private void ComboEscolhatipocontaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboEscolhatipocontaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboEscolhatipocontaActionPerformed

    private void ComboEscolhatipocontaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ComboEscolhatipocontaMouseClicked

    }//GEN-LAST:event_ComboEscolhatipocontaMouseClicked

    private void ComboEscolhatipocontaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ComboEscolhatipocontaFocusGained
        Separadoruser.setBackground(new Color(0, 0, 0));
        SeparadorSenha.setBackground(new Color(0, 0, 0));
        lblDados.setForeground(new Color(240, 240, 240));
    }//GEN-LAST:event_ComboEscolhatipocontaFocusGained

    private void TxtSenhaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtSenhaKeyTyped

    }//GEN-LAST:event_TxtSenhaKeyTyped

    private void TxtSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtSenhaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            LoginsDAO loginsdao = new LoginsDAO();

            try {
                if (loginsdao.VerificarUltimo() == 0) {

                    if (firstuser(TxtUsuario.getText()) && firstsenha(TxtSenha.getText()) && checkLogin((String) ComboEscolhatipoconta.getSelectedItem())) {

                        PrincipalADM prin = new PrincipalADM();
                        prin.setVisible(true);
                        this.dispose();

                        JOptionPane.showMessageDialog(null, "No momento, há somente o login padrão do sistema: \"Usuário = admin | Senha = admin | Tipo de conta = Administrador\". Cadastre um login seguro para desativar automaticamente o login padrão!", "Sistema", JOptionPane.INFORMATION_MESSAGE);

                    } else {
                        lblDados.setForeground(new Color(153, 0, 0));
                        Separadoruser.setBackground(new Color(153, 0, 0));
                        SeparadorSenha.setBackground(new Color(153, 0, 0));
                        TxtUsuario.setText("");
                        TxtSenha.setText("");
                        ComboEscolhatipoconta.setSelectedItem("Padrão");
                        JOptionPane.showMessageDialog(null, "Tente: \"Usuário = admin | Senha = admin | Tipo de conta = Administrador\"!", "Sistema", JOptionPane.INFORMATION_MESSAGE);

                    }

                } else {

                    if (loginsdao.checkLogin(TxtUsuario.getText(), TxtSenha.getText(), (String) ComboEscolhatipoconta.getSelectedItem())) {
                        if (checkLogin((String) ComboEscolhatipoconta.getSelectedItem())) {
                            PrincipalADM prin = new PrincipalADM();
                            prin.setVisible(true);
                            this.dispose();
                        } else {
                            Principal prin = new Principal();
                            prin.setVisible(true);
                            this.dispose();
                        }
                    } else {
                        lblDados.setForeground(new Color(153, 0, 0));
                        Separadoruser.setBackground(new Color(153, 0, 0));
                        SeparadorSenha.setBackground(new Color(153, 0, 0));
                        TxtUsuario.setText("");
                        TxtSenha.setText("");
                        ComboEscolhatipoconta.setSelectedItem("Padrão");
                    }

                }
            } catch (SQLException ex) {

            }
        }
    }//GEN-LAST:event_TxtSenhaKeyPressed

    private void TxtSenhaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtSenhaMouseClicked

    }//GEN-LAST:event_TxtSenhaMouseClicked

    private void TxtSenhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TxtSenhaFocusGained
        Separadoruser.setBackground(new Color(0, 0, 0));
        SeparadorSenha.setBackground(new Color(0, 0, 0));
        lblDados.setForeground(new Color(240, 240, 240));
    }//GEN-LAST:event_TxtSenhaFocusGained

    private void TxtUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtUsuarioKeyTyped

    }//GEN-LAST:event_TxtUsuarioKeyTyped

    private void TxtUsuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtUsuarioKeyReleased

    }//GEN-LAST:event_TxtUsuarioKeyReleased

    private void TxtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtUsuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            LoginsDAO loginsdao = new LoginsDAO();

            try {
                if (loginsdao.VerificarUltimo() == 0) {

                    if (firstuser(TxtUsuario.getText()) && firstsenha(TxtSenha.getText()) && checkLogin((String) ComboEscolhatipoconta.getSelectedItem())) {

                        PrincipalADM prin = new PrincipalADM();
                        prin.setVisible(true);
                        this.dispose();

                        JOptionPane.showMessageDialog(null, "No momento, há somente o login padrão do sistema: \"Usuário = admin | Senha = admin | Tipo de conta = Administrador\". Cadastre um login seguro para desativar automaticamente o login padrão!", "Sistema", JOptionPane.INFORMATION_MESSAGE);

                    } else {
                        lblDados.setForeground(new Color(153, 0, 0));
                        Separadoruser.setBackground(new Color(153, 0, 0));
                        SeparadorSenha.setBackground(new Color(153, 0, 0));
                        TxtUsuario.setText("");
                        TxtSenha.setText("");
                        ComboEscolhatipoconta.setSelectedItem("Padrão");
                        JOptionPane.showMessageDialog(null, "Tente: \"Usuário = admin | Senha = admin | Tipo de conta = Administrador\"!", "Sistema", JOptionPane.INFORMATION_MESSAGE);

                    }

                } else {

                    if (loginsdao.checkLogin(TxtUsuario.getText(), TxtSenha.getText(), (String) ComboEscolhatipoconta.getSelectedItem())) {
                        if (checkLogin((String) ComboEscolhatipoconta.getSelectedItem())) {
                            PrincipalADM prin = new PrincipalADM();
                            prin.setVisible(true);
                            this.dispose();
                        } else {
                            Principal prin = new Principal();
                            prin.setVisible(true);
                            this.dispose();
                        }
                    } else {
                        lblDados.setForeground(new Color(153, 0, 0));
                        Separadoruser.setBackground(new Color(153, 0, 0));
                        SeparadorSenha.setBackground(new Color(153, 0, 0));
                        TxtUsuario.setText("");
                        TxtSenha.setText("");
                        ComboEscolhatipoconta.setSelectedItem("Padrão");
                    }

                }
            } catch (SQLException ex) {

            }
        }
    }//GEN-LAST:event_TxtUsuarioKeyPressed

    private void TxtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtUsuarioActionPerformed

    }//GEN-LAST:event_TxtUsuarioActionPerformed

    private void TxtUsuarioMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtUsuarioMouseReleased

    }//GEN-LAST:event_TxtUsuarioMouseReleased

    private void TxtUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtUsuarioMouseClicked

    }//GEN-LAST:event_TxtUsuarioMouseClicked

    private void TxtUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TxtUsuarioFocusLost

    }//GEN-LAST:event_TxtUsuarioFocusLost

    private void TxtUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TxtUsuarioFocusGained
        Separadoruser.setBackground(new Color(0, 0, 0));
        SeparadorSenha.setBackground(new Color(0, 0, 0));
        lblDados.setForeground(new Color(240, 240, 240));
    }//GEN-LAST:event_TxtUsuarioFocusGained

    private void BotaoSalvarCadastroClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoSalvarCadastroClienteActionPerformed
        LoginsDAO loginsdao = new LoginsDAO();

        try {
            if (loginsdao.VerificarUltimo() == 0) {

                if (firstuser(TxtUsuario.getText()) && firstsenha(TxtSenha.getText()) && checkLogin((String) ComboEscolhatipoconta.getSelectedItem())) {

                    PrincipalADM prin = new PrincipalADM();
                    prin.setVisible(true);
                    this.dispose();

                    JOptionPane.showMessageDialog(null, "No momento, há somente o login padrão do sistema: \"Usuário = admin | Senha = admin | Tipo de conta = Administrador\". Cadastre um login seguro para desativar automaticamente o login padrão!", "Sistema", JOptionPane.INFORMATION_MESSAGE);

                } else {
                    lblDados.setForeground(new Color(153, 0, 0));
                    Separadoruser.setBackground(new Color(153, 0, 0));
                    SeparadorSenha.setBackground(new Color(153, 0, 0));
                    TxtUsuario.setText("");
                    TxtSenha.setText("");
                    ComboEscolhatipoconta.setSelectedItem("Padrão");
                    JOptionPane.showMessageDialog(null, "Tente: \"Usuário = admin | Senha = admin | Tipo de conta = Administrador\"!", "Sistema", JOptionPane.INFORMATION_MESSAGE);

                }

            } else {

                if (loginsdao.checkLogin(TxtUsuario.getText(), TxtSenha.getText(), (String) ComboEscolhatipoconta.getSelectedItem())) {
                    if (checkLogin((String) ComboEscolhatipoconta.getSelectedItem())) {
                        PrincipalADM prin = new PrincipalADM();
                        prin.setVisible(true);
                        this.dispose();
                    } else {
                        Principal prin = new Principal();
                        prin.setVisible(true);
                        this.dispose();
                    }
                } else {
                    lblDados.setForeground(new Color(153, 0, 0));
                    Separadoruser.setBackground(new Color(153, 0, 0));
                    SeparadorSenha.setBackground(new Color(153, 0, 0));
                    TxtUsuario.setText("");
                    TxtSenha.setText("");
                    ComboEscolhatipoconta.setSelectedItem("Padrão");
                }

            }
        } catch (SQLException ex) {

        }
    }//GEN-LAST:event_BotaoSalvarCadastroClienteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotaoSalvarCadastroCliente;
    private javax.swing.JComboBox<String> ComboEscolhatipoconta;
    private javax.swing.JPanel PaneInicial;
    private javax.swing.JSeparator SeparadorSenha;
    private javax.swing.JSeparator Separadoruser;
    private javax.swing.JPasswordField TxtSenha;
    private javax.swing.JTextField TxtUsuario;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblDados;
    // End of variables declaration//GEN-END:variables
}
