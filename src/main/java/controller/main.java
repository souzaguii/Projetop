/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import view.TelaLogin;
import java.sql.SQLException;

/**
 *
 * @author Real
 */
public class main {

    public static void main(String[] args) throws SQLException {

        TelaLogin login = new TelaLogin();
        login.setVisible(true);

    }
}
