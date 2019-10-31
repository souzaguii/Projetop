/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 *
 * @author Geral
 */
public class TelaLogin extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public TelaLogin() {
        initComponents();
        lblDados.setForeground(new Color(240,240,240));
        PaneCadastrar.setVisible(false);
        PaneModificar.setVisible(false);
        PaneInicial.setVisible(true);
    }
   public boolean checkLogin(String login, String senha) {
        return login.equals("admin") && senha.equals("admin");
    }
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        PaneInicial = new javax.swing.JPanel();
        BtnEntrar = new javax.swing.JLabel();
        lblDados = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        TxtUsuario = new javax.swing.JTextField();
        SeparadorUser = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        TxtSenha = new javax.swing.JPasswordField();
        SeparadorSenha = new javax.swing.JSeparator();
        PaneCadastrar = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        BtnEntrar1 = new javax.swing.JLabel();
        PaneModificar = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        BtnEntrar2 = new javax.swing.JLabel();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.CardLayout());

        PaneInicial.setPreferredSize(new java.awt.Dimension(500, 500));

        BtnEntrar.setBackground(new java.awt.Color(230, 230, 230));
        BtnEntrar.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BtnEntrar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BtnEntrar.setText("Entrar");
        BtnEntrar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210)));
        BtnEntrar.setOpaque(true);
        BtnEntrar.setPreferredSize(new java.awt.Dimension(150, 50));
        BtnEntrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnEntrarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BtnEntrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BtnEntrarMouseExited(evt);
            }
        });

        lblDados.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        lblDados.setForeground(new java.awt.Color(153, 0, 0));
        lblDados.setText("Dados inválidos!");

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel1.setText("Usuário");

        TxtUsuario.setBackground(new java.awt.Color(240, 240, 240));
        TxtUsuario.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        TxtUsuario.setBorder(null);
        TxtUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TxtUsuarioMouseClicked(evt);
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtUsuarioKeyTyped(evt);
            }
        });

        SeparadorUser.setBackground(new java.awt.Color(240, 240, 240));
        SeparadorUser.setForeground(new java.awt.Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel2.setText("Senha");

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
        });

        SeparadorSenha.setBackground(new java.awt.Color(240, 240, 240));
        SeparadorSenha.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout PaneInicialLayout = new javax.swing.GroupLayout(PaneInicial);
        PaneInicial.setLayout(PaneInicialLayout);
        PaneInicialLayout.setHorizontalGroup(
            PaneInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaneInicialLayout.createSequentialGroup()
                .addGroup(PaneInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PaneInicialLayout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addGroup(PaneInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BtnEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PaneInicialLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(lblDados))))
                    .addGroup(PaneInicialLayout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addGroup(PaneInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(TxtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PaneInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(SeparadorUser, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(PaneInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(SeparadorSenha)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TxtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(100, 100, 100))
        );
        PaneInicialLayout.setVerticalGroup(
            PaneInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PaneInicialLayout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(SeparadorUser, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(SeparadorSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblDados)
                .addGap(49, 49, 49)
                .addComponent(BtnEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        getContentPane().add(PaneInicial, "card2");

        PaneCadastrar.setPreferredSize(new java.awt.Dimension(500, 500));

        jLabel6.setText("CADASTRAR");

        BtnEntrar1.setBackground(new java.awt.Color(230, 230, 230));
        BtnEntrar1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BtnEntrar1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BtnEntrar1.setText("Cancelar");
        BtnEntrar1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210)));
        BtnEntrar1.setOpaque(true);
        BtnEntrar1.setPreferredSize(new java.awt.Dimension(150, 50));
        BtnEntrar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnEntrar1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BtnEntrar1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BtnEntrar1MouseExited(evt);
            }
        });

        javax.swing.GroupLayout PaneCadastrarLayout = new javax.swing.GroupLayout(PaneCadastrar);
        PaneCadastrar.setLayout(PaneCadastrarLayout);
        PaneCadastrarLayout.setHorizontalGroup(
            PaneCadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PaneCadastrarLayout.createSequentialGroup()
                .addContainerGap(184, Short.MAX_VALUE)
                .addGroup(PaneCadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BtnEntrar1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(206, 206, 206))
        );
        PaneCadastrarLayout.setVerticalGroup(
            PaneCadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaneCadastrarLayout.createSequentialGroup()
                .addGap(210, 210, 210)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 189, Short.MAX_VALUE)
                .addComponent(BtnEntrar1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );

        getContentPane().add(PaneCadastrar, "card4");

        PaneModificar.setPreferredSize(new java.awt.Dimension(500, 500));

        jLabel5.setText("MODIFICAR");

        BtnEntrar2.setBackground(new java.awt.Color(230, 230, 230));
        BtnEntrar2.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BtnEntrar2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BtnEntrar2.setText("Cancelar");
        BtnEntrar2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210)));
        BtnEntrar2.setOpaque(true);
        BtnEntrar2.setPreferredSize(new java.awt.Dimension(150, 50));
        BtnEntrar2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnEntrar2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BtnEntrar2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BtnEntrar2MouseExited(evt);
            }
        });

        javax.swing.GroupLayout PaneModificarLayout = new javax.swing.GroupLayout(PaneModificar);
        PaneModificar.setLayout(PaneModificarLayout);
        PaneModificarLayout.setHorizontalGroup(
            PaneModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaneModificarLayout.createSequentialGroup()
                .addGroup(PaneModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PaneModificarLayout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(jLabel5))
                    .addGroup(PaneModificarLayout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addComponent(BtnEntrar2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(218, Short.MAX_VALUE))
        );
        PaneModificarLayout.setVerticalGroup(
            PaneModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaneModificarLayout.createSequentialGroup()
                .addGap(167, 167, 167)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 228, Short.MAX_VALUE)
                .addComponent(BtnEntrar2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );

        getContentPane().add(PaneModificar, "card3");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnEntrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnEntrarMouseClicked
        if (this.checkLogin(TxtUsuario.getText(), new String(TxtSenha.getPassword()))) {
            Principal prin = new Principal();
            prin.setVisible(true);
            this.dispose();
        } else {
            lblDados.setForeground(new Color(153,0,0));
            SeparadorUser.setForeground(new Color(153,0,0));
            SeparadorSenha.setForeground(new Color(153,0,0));
        }
    }//GEN-LAST:event_BtnEntrarMouseClicked

    private void BtnEntrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnEntrarMouseEntered
        BtnEntrar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(190,190,190)));
    }//GEN-LAST:event_BtnEntrarMouseEntered

    private void BtnEntrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnEntrarMouseExited
        BtnEntrar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 220, 220)));
    }//GEN-LAST:event_BtnEntrarMouseExited

    private void TxtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtUsuarioActionPerformed
        
    }//GEN-LAST:event_TxtUsuarioActionPerformed

    private void TxtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtUsuarioKeyPressed
        SeparadorUser.setForeground(new Color(25,25,112));
        SeparadorSenha.setForeground(new Color(0,0,0));
        lblDados.setForeground(new Color(240,240,240));
         if(evt.getKeyCode()==KeyEvent.VK_ENTER){ 
            if (this.checkLogin(TxtUsuario.getText(), new String(TxtSenha.getPassword()))) {
            Principal prin = new Principal();
            prin.setVisible(true);
            this.dispose();
        } else {
            lblDados.setForeground(new Color(153,0,0));
            SeparadorUser.setForeground(new Color(153,0,0));
            SeparadorSenha.setForeground(new Color(153,0,0));
        }
        }
    }//GEN-LAST:event_TxtUsuarioKeyPressed

    private void TxtSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtSenhaKeyPressed
        SeparadorSenha.setForeground(new Color(25,25,112));
        SeparadorUser.setForeground(new Color(0,0,0));
        lblDados.setForeground(new Color(240,240,240));
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){ 
            if (this.checkLogin(TxtUsuario.getText(), new String(TxtSenha.getPassword()))) {
            Principal prin = new Principal();
            prin.setVisible(true);
            this.dispose();
        } else {
            lblDados.setForeground(new Color(153,0,0));
            SeparadorUser.setForeground(new Color(153,0,0));
            SeparadorSenha.setForeground(new Color(153,0,0));
        }
        }
    }//GEN-LAST:event_TxtSenhaKeyPressed

    private void TxtUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtUsuarioKeyTyped

    }//GEN-LAST:event_TxtUsuarioKeyTyped

    private void TxtUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtUsuarioMouseClicked
        SeparadorUser.setForeground(new Color(25,25,112));
        SeparadorSenha.setForeground(new Color(0,0,0));
        lblDados.setForeground(new Color(240,240,240));
    }//GEN-LAST:event_TxtUsuarioMouseClicked

    private void TxtSenhaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtSenhaMouseClicked
       SeparadorSenha.setForeground(new Color(25,25,112));
       SeparadorUser.setForeground(new Color(0,0,0));
       lblDados.setForeground(new Color(240,240,240));// TODO add your handling code here:
    }//GEN-LAST:event_TxtSenhaMouseClicked

    private void BtnEntrar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnEntrar1MouseClicked
        PaneInicial.setVisible(true);
        PaneCadastrar.setVisible(false);
        PaneModificar.setVisible(false); 
        lblDados.setForeground(new Color(240,240,240));
        SeparadorUser.setForeground(new Color(0,0,0));
        SeparadorSenha.setForeground(new Color(0,0,0));// TODO add your handling code here:
    }//GEN-LAST:event_BtnEntrar1MouseClicked

    private void BtnEntrar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnEntrar1MouseEntered
        BtnEntrar1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(190,190,190)));
    }//GEN-LAST:event_BtnEntrar1MouseEntered

    private void BtnEntrar1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnEntrar1MouseExited
        BtnEntrar1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 220, 220)));
    }//GEN-LAST:event_BtnEntrar1MouseExited

    private void BtnEntrar2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnEntrar2MouseClicked
        PaneInicial.setVisible(true);
        PaneCadastrar.setVisible(false);
        PaneModificar.setVisible(false);
        lblDados.setForeground(new Color(240,240,240));
        SeparadorUser.setForeground(new Color(0,0,0));
        SeparadorSenha.setForeground(new Color(0,0,0));// TODO add your handling code here:
    }//GEN-LAST:event_BtnEntrar2MouseClicked

    private void BtnEntrar2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnEntrar2MouseEntered
        BtnEntrar2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(190,190,190)));
    }//GEN-LAST:event_BtnEntrar2MouseEntered

    private void BtnEntrar2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnEntrar2MouseExited
        BtnEntrar2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 220, 220)));
    }//GEN-LAST:event_BtnEntrar2MouseExited

  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BtnEntrar;
    private javax.swing.JLabel BtnEntrar1;
    private javax.swing.JLabel BtnEntrar2;
    private javax.swing.JPanel PaneCadastrar;
    private javax.swing.JPanel PaneInicial;
    private javax.swing.JPanel PaneModificar;
    private javax.swing.JSeparator SeparadorSenha;
    private javax.swing.JSeparator SeparadorUser;
    private javax.swing.JPasswordField TxtSenha;
    private javax.swing.JTextField TxtUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblDados;
    // End of variables declaration//GEN-END:variables
}
