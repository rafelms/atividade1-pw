package br.edu.ifto.usuariocomtarefasnoturno.dao;

import br.edu.ifto.usuariocomtarefasnoturno.config.Conexao;
import br.edu.ifto.usuariocomtarefasnoturno.model.entities.Usuario;
import br.edu.ifto.usuariocomtarefasnoturno.model.enums.PerfilEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public void cadastrar(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, login, senha, perfil) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getLogin());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getPerfil().name());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar usuário no banco: " + e.getMessage(), e);
        }
    }

    public Usuario autenticar(String login, String senha) {
        String sql = "SELECT * FROM usuario WHERE login = ? AND senha = ?";

        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setNome(rs.getString("nome"));
                    u.setLogin(rs.getString("login"));
                    u.setSenha(rs.getString("senha"));
                    u.setPerfil(PerfilEnum.valueOf(rs.getString("perfil")));
                    return u;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário para login.", e);
        }
        return null; // Retorna null se não achar login/senha correspondentes
    }

    public void atualizar(Usuario usuario) {
        String sql = "UPDATE usuario SET nome = ?, senha = ? WHERE id = ?";

        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getSenha());
            stmt.setInt(3, usuario.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário no banco: " + e.getMessage(), e);
        }
    }
}