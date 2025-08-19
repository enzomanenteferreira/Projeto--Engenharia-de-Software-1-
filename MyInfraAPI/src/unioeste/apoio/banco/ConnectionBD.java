package unioeste.apoio.banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBD {
    private static final String URL = "jdbc:postgresql://localhost:5432/es1";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";
    
    public static Connection getConnection() throws Exception {
        // Utilize o driver adequado para sua versão do MySQL, por exemplo:
        // Class.forName("com.mysql.cj.jdbc.Driver");
        Class.forName("org.postgresql.Driver");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new IllegalStateException("Não foi possível conectar ao banco de dados!", e);
        }
        return conn;
    }
    
    public static void main(String[] args) {
        Connection conn = null;
        try {
            System.out.println("Tentando conectar ao banco de dados...");
            conn = getConnection();
            if (conn != null) {
                System.out.println("Conectado com sucesso!");
            }
        } catch (Exception e) {
            System.err.println("Erro na conexão:");
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Conexão fechada.");
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar a conexão:");
                    e.printStackTrace();
                }
            }
        }
    }
}
