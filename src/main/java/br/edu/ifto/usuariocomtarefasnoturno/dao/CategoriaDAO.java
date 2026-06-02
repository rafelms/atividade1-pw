package br.edu.ifto.usuariocomtarefasnoturno.dao;

import br.edu.ifto.usuariocomtarefasnoturno.config.Conexao;
import br.edu.ifto.usuariocomtarefasnoturno.model.entities.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public void cadastrar(Categoria categoria) {
        String sql = "INSERT INTO categoria (nome) VALUES (?)";

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = Conexao.getConexao();
            stmt = con.prepareStatement(sql);
            stmt.setString(1, categoria.getNome());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar categoria: " + e.getMessage(), e);

        } finally {
            Conexao.fecharStatement(stmt);
            Conexao.fecharConexao(con);
        }
    }

    public void atualizar(Categoria categoria) {
        String sql = "UPDATE categoria SET nome = ? WHERE id = ?";

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = Conexao.getConexao();
            stmt = con.prepareStatement(sql);
            stmt.setString(1, categoria.getNome());
            stmt.setInt(2, categoria.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar categoria: " + e.getMessage(), e);

        } finally {
            Conexao.fecharStatement(stmt);
            Conexao.fecharConexao(con);
        }
    }

    public void remover(int id) {
        String sql = "DELETE FROM categoria WHERE id = ?";

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = Conexao.getConexao();
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            // Se houver tarefa vinculada, o MySQL vai avisar aqui textualmente
            throw new RuntimeException("Não é possível remover: esta categoria está sendo usada por alguma tarefa. Detalhe: " + e.getMessage(), e);

        } finally {
            // Sem ResultSet neste método — fechamento na ordem: Statement → Connection
            Conexao.fecharStatement(stmt);
            Conexao.fecharConexao(con);
        }
    }

    public Categoria buscarPorId(int id) {
        String sql = "SELECT * FROM categoria WHERE id = ?";

        // Declarados fora do try — ResultSet também é necessário aqui para ser fechado no finally
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = Conexao.getConexao();
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                return c;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar categoria: " + e.getMessage(), e);

        } finally {
            // Com ResultSet — fechamento na ordem obrigatória: ResultSet → Statement → Connection
            Conexao.fecharResultSet(rs);
            Conexao.fecharStatement(stmt);
            Conexao.fecharConexao(con);
        }

        return null;
    }

    public List<Categoria> listarTodas() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categoria ORDER BY nome";

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = Conexao.getConexao();
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                lista.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar categorias: " + e.getMessage(), e);

        } finally {
            Conexao.fecharResultSet(rs);
            Conexao.fecharStatement(stmt);
            Conexao.fecharConexao(con);
        }

        return lista;
    }
}