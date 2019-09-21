package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao{ 

public static String status = "Não conectou...";
      public Conexao() {
  }
      
public static java.sql.Connection getConexaoMySQL() {
      Connection connection = null;
try {
String driverName = "com.mysql.cj.jdbc.Driver";
Class.forName(driverName);
          String serverName = "localhost";
          String mydatabase ="cadastros";
          String port ="3306";
          String aux = "?useTimezone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false";
          String url = "jdbc:mysql://"+serverName+":"+port +"/" +mydatabase+aux;
          String username = "root";      
          String password = "root";
          connection = DriverManager.getConnection(url, username, password);

          if (connection != null) {
              status = ("STATUS--->Conectado com sucesso!");
          } else {
              status = ("STATUS--->Não foi possivel realizar conexão");
          }
          return connection;
      } catch (ClassNotFoundException e) {
    	  
          System.out.println("O driver expecificado nao foi encontrado.");
          return null;
      } catch (SQLException e) {
          System.out.println("Nao foi possivel conectar ao Banco de Dados.");
          return null;
      }
  }
  public static String statusConection() {
      return status;
  }

public static boolean FecharConexao() {
      try {
    	  Conexao.getConexaoMySQL().close();
          return true;
      } catch (SQLException e) {
          return false;
      }
  } 

public static java.sql.Connection ReiniciarConexao() {
      FecharConexao();
      return Conexao.getConexaoMySQL();
  }
}

/*public class Conexao {

    public static String status = "Não conectou...";

    public Conexao() {
    }

    public static java.sql.Connection getConexaoMySQL() {
        Connection connection = null;
        try {
            String driverName = "com.mysql.cj.jdbc.Driver"; //com.mysql.jdbc.Driver
            Class.forName(driverName);


            String url = "jdbc:mysql://10.93.10.10:3306/revista?autoReconnect=true&useSSL=false";
            String username = "revista";
            String password = "Uemg2018";

            if (connection != null) {
                //System.out.println("Conectou");
                status = ("STATUS--->Conectado com sucesso!");
            } else {
                System.out.println("Não conectou");
                status = ("STATUS--->Não foi possivel realizar conexão");
            }
            return connection;
        } catch (ClassNotFoundException e) {

            System.out.println("O driver expecificado nao foi encontrado.");
            return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Nao foi possivel conectar ao Banco de Dados.");
            return null;
        }
    }

    public static String statusConection() {
        return status;
    }

    public static boolean FecharConexao() {
        try {
            Conexao.getConexaoMySQL().close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static java.sql.Connection ReiniciarConexao() {
        FecharConexao();
        return Conexao.getConexaoMySQL();
    }
}*/

