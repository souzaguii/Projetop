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
import javax.swing.JOptionPane;

/**
 *
 * @author Geral
 */
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
                SeparadorUser.setForeground(new Color(153, 0, 0));
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
//        Principal pri = new Principal();
//        pri.setVisible(true);
    }

    public boolean checkLogin(String login) {
        return login.equals("Administrador");
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
        SeparadorUser = new javax.swing.JSeparator();
        TxtSenha = new javax.swing.JPasswordField();
        ComboEscolhatipoconta = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Fieldversenha = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Fazer Login");
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(550, 555));
        setMinimumSize(new java.awt.Dimension(550, 555));
        setPreferredSize(new java.awt.Dimension(530, 720));
        setResizable(false);
        getContentPane().setLayout(new java.awt.CardLayout());

        PaneInicial.setMaximumSize(new java.awt.Dimension(500, 500));
        PaneInicial.setMinimumSize(new java.awt.Dimension(500, 500));
        PaneInicial.setPreferredSize(new java.awt.Dimension(550, 555));

        BotaoSalvarCadastroCliente.setBackground(new java.awt.Color(230, 230, 230));
        BotaoSalvarCadastroCliente.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BotaoSalvarCadastroCliente.setText("Entrar");
        BotaoSalvarCadastroCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BotaoSalvarCadastroCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoSalvarCadastroClienteActionPerformed(evt);
            }
        });

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

        SeparadorUser.setBackground(new java.awt.Color(240, 240, 240));
        SeparadorUser.setForeground(new java.awt.Color(0, 0, 0));

        TxtSenha.setBackground(new java.awt.Color(240, 240, 240));
        TxtSenha.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        TxtSenha.setBorder(null);
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

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel2.setText("Usuário");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel4.setText("Senha");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
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

        Fieldversenha.setEditable(false);
        Fieldversenha.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        Fieldversenha.setForeground(new java.awt.Color(240, 240, 240));
        Fieldversenha.setBorder(null);
        Fieldversenha.setMaximumSize(new java.awt.Dimension(25, 25));
        Fieldversenha.setMinimumSize(new java.awt.Dimension(25, 25));
        Fieldversenha.setOpaque(false);
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

        javax.swing.GroupLayout PaneInicialLayout = new javax.swing.GroupLayout(PaneInicial);
        PaneInicial.setLayout(PaneInicialLayout);
        PaneInicialLayout.setHorizontalGroup(
            PaneInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaneInicialLayout.createSequentialGroup()
                .addGroup(PaneInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PaneInicialLayout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jLabel3))
                    .addGroup(PaneInicialLayout.createSequentialGroup()
                        .addComponent(SeparadorUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(137, 137, 137)
                        .addGroup(PaneInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5)
                            .addComponent(ComboEscolhatipoconta, 0, 253, Short.MAX_VALUE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(TxtSenha)
                            .addGroup(PaneInicialLayout.createSequentialGroup()
                                .addComponent(Fieldversenha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6))
                            .addComponent(SeparadorSenha)
                            .addComponent(Separadoruser)
                            .addComponent(TxtUsuario)))
                    .addGroup(PaneInicialLayout.createSequentialGroup()
                        .addGap(188, 188, 188)
                        .addGroup(PaneInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PaneInicialLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(lblDados))
                            .addComponent(BotaoSalvarCadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        PaneInicialLayout.setVerticalGroup(
            PaneInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaneInicialLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(50, 50, 50)
                .addComponent(jLabel2)
                .addGroup(PaneInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PaneInicialLayout.createSequentialGroup()
                        .addGap(296, 296, 296)
                        .addComponent(SeparadorUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PaneInicialLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TxtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(Separadoruser, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TxtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(PaneInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PaneInicialLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(PaneInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(Fieldversenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(PaneInicialLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(SeparadorSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(31, 31, 31)
                        .addComponent(jLabel5)
                        .addGap(13, 13, 13)
                        .addComponent(ComboEscolhatipoconta, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36)
                .addComponent(BotaoSalvarCadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(lblDados)
                .addGap(40, 40, 40))
        );

        getContentPane().add(PaneInicial, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TxtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtUsuarioActionPerformed

    }//GEN-LAST:event_TxtUsuarioActionPerformed

    private void TxtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtUsuarioKeyPressed
//Descomentar e apagar em baixo para voltar com validação no banco de dados.
        PrincipalADM prin = new PrincipalADM();
        prin.setVisible(true);
//        SeparadorSenha.setBackground(new Color(0, 0, 0));
//        Separadoruser.setBackground(new Color(25, 25, 112));
//        lblDados.setForeground(new Color(240, 240, 240));
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            LoginsDAO loginsdao = new LoginsDAO();
//            if (loginsdao.checkLogin(TxtUsuario.getText(), TxtSenha.getText(), (String) ComboEscolhatipoconta.getSelectedItem())) {
//                if (checkLogin((String) ComboEscolhatipoconta.getSelectedItem())) {
//                    PrincipalADM prin = new PrincipalADM();
//                    prin.setVisible(true);
//                    this.dispose();
//                } else {
//                    Principal prin = new Principal();
//                    prin.setVisible(true);
//                    this.dispose();
//                }
//            } else {
//                lblDados.setForeground(new Color(153, 0, 0));
//                Separadoruser.setBackground(new Color(153, 0, 0));
//                SeparadorSenha.setBackground(new Color(153, 0, 0));
//            }
//        }
    }//GEN-LAST:event_TxtUsuarioKeyPressed

    private void TxtSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtSenhaKeyPressed
        SeparadorSenha.setBackground(new Color(25, 25, 112));
        Separadoruser.setBackground(new Color(0, 0, 0));
        lblDados.setForeground(new Color(240, 240, 240));
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            LoginsDAO loginsdao = new LoginsDAO();
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
            }
        }
    }//GEN-LAST:event_TxtSenhaKeyPressed

    private void TxtUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtUsuarioKeyTyped
//        Separadoruser.setBackground(new Color(25, 25, 112));
//        SeparadorSenha.setBackground(new Color(0, 0, 0));
//        lblDados.setForeground(new Color(240, 240, 240));
    }//GEN-LAST:event_TxtUsuarioKeyTyped

    private void TxtUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtUsuarioMouseClicked
        Separadoruser.setBackground(new Color(25, 25, 112));
        SeparadorSenha.setBackground(new Color(0, 0, 0));
        lblDados.setForeground(new Color(240, 240, 240));
    }//GEN-LAST:event_TxtUsuarioMouseClicked

    private void TxtSenhaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtSenhaMouseClicked
        SeparadorSenha.setBackground(new Color(25, 25, 112));
        Separadoruser.setBackground(new Color(0, 0, 0));
        lblDados.setForeground(new Color(240, 240, 240));// TODO add your handling code here:
    }//GEN-LAST:event_TxtSenhaMouseClicked

    private void BotaoSalvarCadastroClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoSalvarCadastroClienteActionPerformed
        LoginsDAO loginsdao = new LoginsDAO();
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
        }
    }//GEN-LAST:event_BotaoSalvarCadastroClienteActionPerformed

    private void TxtUsuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtUsuarioKeyReleased

    }//GEN-LAST:event_TxtUsuarioKeyReleased

    private void TxtUsuarioMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtUsuarioMouseReleased

    }//GEN-LAST:event_TxtUsuarioMouseReleased

    private void TxtSenhaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtSenhaKeyTyped
        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 11));
        Fieldversenha.setForeground(new Color(240, 240, 240));
    }//GEN-LAST:event_TxtSenhaKeyTyped

    private void ComboEscolhatipocontaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ComboEscolhatipocontaMouseClicked
        SeparadorSenha.setBackground(new Color(0, 0, 0));
        Separadoruser.setBackground(new Color(0, 0, 0));
        lblDados.setForeground(new Color(240, 240, 240));
    }//GEN-LAST:event_ComboEscolhatipocontaMouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseEntered
        Fieldversenha.setText(TxtSenha.getText());
        Fieldversenha.setForeground(new Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 12));
    }//GEN-LAST:event_jLabel6MouseEntered

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited
        Fieldversenha.setForeground(new Color(240, 240, 240));
        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 11));
    }//GEN-LAST:event_jLabel6MouseExited

    private void FieldversenhaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FieldversenhaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldversenhaMouseClicked

    private void FieldversenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldversenhaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldversenhaKeyPressed

    private void ComboEscolhatipocontaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ComboEscolhatipocontaKeyPressed
        SeparadorSenha.setBackground(new Color(0, 0, 0));
        Separadoruser.setBackground(new Color(0, 0, 0));
        lblDados.setForeground(new Color(240, 240, 240));
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            LoginsDAO loginsdao = new LoginsDAO();
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
            }
        }
    }//GEN-LAST:event_ComboEscolhatipocontaKeyPressed

    private void ComboEscolhatipocontaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboEscolhatipocontaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboEscolhatipocontaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotaoSalvarCadastroCliente;
    private javax.swing.JComboBox<String> ComboEscolhatipoconta;
    private javax.swing.JTextField Fieldversenha;
    private javax.swing.JPanel PaneInicial;
    private javax.swing.JSeparator SeparadorSenha;
    private javax.swing.JSeparator SeparadorUser;
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
