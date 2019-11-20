/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import static java.awt.Frame.MAXIMIZED_BOTH;
import view.TelaLogin;
import java.sql.SQLException;
import view.PrincipalADM;
/**
 *
 * @author Real
 */
public class main {
        public static void main(String[] args) throws SQLException {
           
       
        PrincipalADM login = new PrincipalADM();
        login.setVisible(true);
        
        
        }
}
