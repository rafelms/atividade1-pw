package br.edu.ifto.usuariocomtarefasnoturno.config;

import java.sql.*;

public class Conexao {

    private static final String URL = "jdbc:mysql://localhost:3306/sistemadetarefas?useTimezone=true&serverTimezone=UTC&useSSL=false";
    private static final String USUARIO = "root";
    private static final String SENHA = "novasenha";

    /**
     * Abre e retorna uma conexão ativa com o banco.
     */
    public static Connection getConexao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Erro: Driver do MySQL não encontrado.", e);
        } catch (SQLException e) {
            // Repassando a mensagem exata do MySQL para o Controller capturar e mostrar na tela!
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Metodo auxiliar de segurança para fechar a Conexão.
     */
    public static void fecharConexao(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão com o banco: " + e.getMessage());
        }
    }

    /**
     * Metodo auxiliar de segurança para fechar o PreparedStatement (Comando SQL).
     */
    public static void fecharStatement(PreparedStatement stmt) {
        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar o PreparedStatement: " + e.getMessage());
        }
    }

    /**
     * Metodo auxiliar de segurança para fechar o ResultSet (Resultado da Busca).
     */
    public static void fecharResultSet(ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar o ResultSet: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Exemplo Prático da Boa Prática com try-with-resources:
        // Passando a conexão dentro dos parênteses do try, o próprio Java 
        // SE ENCARREGA de fechar a conexão AUTOMATICAMENTE ao final do bloco,
        // mesmo se acontecer algum erro lá dentro!
        try (Connection con = getConexao()) {
            if (con != null) {
                System.out.println("SUCESSO: Conexão aberta e testada!");
            }
        } catch (SQLException e) {
            System.err.println("Erro no teste da conexão.");
            e.printStackTrace();
        } // <-- AQUI A CONEXÃO JÁ FOI FECHADA AUTOMATICAMENTE PELO JAVA!
    }
}