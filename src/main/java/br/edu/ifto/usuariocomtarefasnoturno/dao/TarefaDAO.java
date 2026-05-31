package br.edu.ifto.usuariocomtarefasnoturno.dao;

import br.edu.ifto.usuariocomtarefasnoturno.config.Conexao;
import br.edu.ifto.usuariocomtarefasnoturno.model.entities.Tarefa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {

    public void cadastrar(Tarefa tarefa) {
        String sql = "INSERT INTO tarefa (titulo, descricao, data, id_usuario, id_categoria) VALUES (?, ?, ?, ?, ?)";

        // Declarados fora do try para que o bloco finally consiga enxergá-los e fechá-los
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = Conexao.getConexao();
            stmt = con.prepareStatement(sql);

            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());

            // Tratamento caso a data venha nula
            if (tarefa.getData() != null) {
                stmt.setDate(3, java.sql.Date.valueOf(tarefa.getData()));
            } else {
                stmt.setNull(3, java.sql.Types.DATE);
            }

            stmt.setInt(4, tarefa.getIdUsuario());
            stmt.setInt(5, tarefa.getIdCategoria());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar tarefa", e);

        } finally {
            // Sem ResultSet neste método — fechamento na ordem: Statement → Connection
            Conexao.fecharStatement(stmt);
            Conexao.fecharConexao(con);
        }
    }

    public void atualizar(Tarefa tarefa) {
        String sql = "UPDATE tarefa SET titulo = ?, descricao = ?, data = ?, id_categoria = ? WHERE id = ? AND id_usuario = ?";

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = Conexao.getConexao();
            stmt = con.prepareStatement(sql);

            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());
            if (tarefa.getData() != null) {
                stmt.setDate(3, java.sql.Date.valueOf(tarefa.getData()));
            } else {
                stmt.setNull(3, java.sql.Types.DATE);
            }
            stmt.setInt(4, tarefa.getIdCategoria());
            stmt.setInt(5, tarefa.getId());
            stmt.setInt(6, tarefa.getIdUsuario()); // Segurança: Garante que só o dono edita

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar tarefa", e);

        } finally {
            // Sem ResultSet neste método — fechamento na ordem: Statement → Connection
            Conexao.fecharStatement(stmt);
            Conexao.fecharConexao(con);
        }
    }

    public void remover(int idTarefa, int idUsuario) {
        String sql = "DELETE FROM tarefa WHERE id = ? AND id_usuario = ?";

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = Conexao.getConexao();
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idTarefa);
            stmt.setInt(2, idUsuario);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar tarefa", e);

        } finally {
            // Sem ResultSet neste método — fechamento na ordem: Statement → Connection
            Conexao.fecharStatement(stmt);
            Conexao.fecharConexao(con);
        }
    }

    public List<Tarefa> listarPorUsuario(int idUsuario) {
        List<Tarefa> lista = new ArrayList<>();

        // O JOIN traz o nome da categoria atrelada
        String sql = "SELECT t.*, c.nome AS nome_categoria FROM tarefa t JOIN categoria c ON t.id_categoria = c.id WHERE t.id_usuario = ?";

        // Declarados fora do try — ResultSet também é necessário aqui para ser fechado no finally
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = Conexao.getConexao();
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Tarefa t = new Tarefa();
                t.setId(rs.getInt("id"));
                t.setTitulo(rs.getString("titulo"));
                t.setDescricao(rs.getString("descricao"));

                java.sql.Date sqlDate = rs.getDate("data");
                if (sqlDate != null) {
                    t.setData(sqlDate.toLocalDate());
                }

                t.setIdUsuario(rs.getInt("id_usuario"));
                t.setIdCategoria(rs.getInt("id_categoria"));
                t.setCategoria(rs.getString("nome_categoria")); // Guarda o nome para exibir no JSP

                lista.add(t);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar tarefas do usuário", e);

        } finally {
            // Com ResultSet — fechamento na ordem obrigatória: ResultSet → Statement → Connection
            Conexao.fecharResultSet(rs);
            Conexao.fecharStatement(stmt);
            Conexao.fecharConexao(con);
        }

        return lista;
    }

    public Tarefa buscarPorId(int idTarefa, int idUsuario) {
        String sql = "SELECT * FROM tarefa WHERE id = ? AND id_usuario = ?";

        // Declarados fora do try — ResultSet também é necessário aqui para ser fechado no finally
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = Conexao.getConexao();
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idTarefa);
            stmt.setInt(2, idUsuario);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Tarefa t = new Tarefa();
                t.setId(rs.getInt("id"));
                t.setTitulo(rs.getString("titulo"));
                t.setDescricao(rs.getString("descricao"));

                java.sql.Date sqlDate = rs.getDate("data");
                if (sqlDate != null) t.setData(sqlDate.toLocalDate());

                t.setIdUsuario(rs.getInt("id_usuario"));
                t.setIdCategoria(rs.getInt("id_categoria"));
                return t;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar tarefa", e);

        } finally {
            // Com ResultSet — fechamento na ordem obrigatória: ResultSet → Statement → Connection
            Conexao.fecharResultSet(rs);
            Conexao.fecharStatement(stmt);
            Conexao.fecharConexao(con);
        }

        return null;
    }
}