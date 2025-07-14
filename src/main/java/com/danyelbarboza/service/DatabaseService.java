package com.danyelbarboza.service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import java.util.logging.Level;


public class DatabaseService {
    private String url;
    private static final Logger logger = Logger.getLogger(DatabaseService.class.getName());

    public DatabaseService(String dbFileName) {
        this.url = "jdbc:sqlite:" + dbFileName;
    }

    /**
     * Conecta ao banco de dados SQLite.
     * @return Objeto Connection se a conexão for bem-sucedida, caso contrário, null.
     */
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            logger.log(Level.INFO, "Conexão com o SQLite estabelecida.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return conn;
    }

    /**
     * Cria uma nova tabela 'medicamentos' se ela não existir.
     */
    public void createTableMedicamentos() {
        String sql = "CREATE TABLE IF NOT EXISTS medicamentos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL, " +
                "quantidade_comprimidos INTEGER," +
                "validade TEXT," +
                "frequencia INTEGER NOT NULL);";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            logger.log(Level.INFO, "Tabela 'medicamentos' criada ou já existe.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao criar tabela: " + e.getMessage());
        }
    }

    /**
     * Insere um novo meidcamento na tabela 'medicamentos'.
     * @param nome O nome do medicamento.
     * @param quantidade A quantidade de comprimidos.
     * @param validade A validade do medicamento.
     */
    public void insertMedicamento(String nome, int quantidade, String validade, int frequencia) {
        String sql = "INSERT INTO medicamentos(nome, quantidade_comprimidos, validade, frequencia) VALUES(?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setInt(2, quantidade);
            pstmt.setString(3, validade);
            pstmt.setInt(4, frequencia);
            pstmt.executeUpdate();
            System.out.println("Usuário " + nome + " inserido com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
        }
    }

    /**
     * Seleciona todos os usuários da tabela 'medicamentos'.
     */
    public void selectAllMedicamentos() {
        String sql = "SELECT * FROM medicamentos";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            logger.log(Level.INFO, "Medicamentos Cadastrados:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + "\t" +
                        "Nome: " + rs.getString("nome") + "\t" +
                        "Quantidade: " + rs.getInt("quantidade_comprimidos") + "\t" +
                        "Validade: " + rs.getString("validade") + "\t" +
                        "Frequencia: " + rs.getInt("frequencia"));
            }
            System.out.println("--------------------------");

        } catch (SQLException e) {
            System.err.println("Erro ao selecionar usuários: " + e.getMessage());
        }
    }

    /**
     * Atualiza a quantidade de comprimidos de um usuário na tabela 'medicamentos'.
     * @param id O ID do usuário a ser atualizado.
     * @param novaQuantidade A nova quantidade de comprimidos.
     */
    public void updateQuantidade(int id, int novaQuantidade ) {
        String sql = "UPDATE medicamentos SET quantidade_comprimidos = ? WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, novaQuantidade);
            pstmt.setInt(2, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Email do usuário com ID " + id + " atualizado para " + novaQuantidade + ".");
            } else {
                System.out.println("Usuário com ID " + id + " não encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    /**
     * Deleta um usuário da tabela 'medicamentos' com base no ID.
     * @param id O ID do usuário a ser deletado.
     */
    public void deleteMed(int id) {
        String sql = "DELETE FROM medicamentos WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Medicamento com ID " + id + " deletado com sucesso!");
            } else {
                System.out.println("Medicamento com ID " + id + " não encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar medicamento: " + e.getMessage());
        }
    }

    public void deleteAllMedicamentos() {
        String sql = "DELETE FROM medicamentos";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            int rowsAffected = stmt.executeUpdate(sql);
            if (rowsAffected > 0) {
                System.out.println("Todos os medicamentos deletados com sucesso!");
            } else {
                System.out.println("Nenhum medicamento encontrado para deletar.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar medicamentos: " + e.getMessage());
        }
    }


}
