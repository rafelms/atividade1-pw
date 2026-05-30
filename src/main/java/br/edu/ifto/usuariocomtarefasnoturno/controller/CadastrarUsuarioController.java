package br.edu.ifto.usuariocomtarefasnoturno.controller;

import br.edu.ifto.usuariocomtarefasnoturno.dao.UsuarioDAO;
import br.edu.ifto.usuariocomtarefasnoturno.model.entities.Usuario;
import br.edu.ifto.usuariocomtarefasnoturno.model.enums.PerfilEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/cadastrarusuario")
public class CadastrarUsuarioController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String nome = request.getParameter("nome");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        if (nome == null || nome.isBlank() || login == null || login.isBlank() || senha == null || senha.isBlank()) {
            request.setAttribute("mensagemErro", "Você precisa informar todos os campos.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        Usuario novoUsuario = new Usuario(nome, login, senha, PerfilEnum.NORMAL);

        UsuarioDAO dao = new UsuarioDAO();
        try {
            dao.cadastrar(novoUsuario);
            request.setAttribute("mensagemSucesso", "Cadastrado com sucesso!");
        } catch (Exception e) {
            // Caso o DAO identifique login duplicado (violação do UNIQUE no MySQL)
            request.setAttribute("mensagemErro", "Erro: " + e.getMessage());
        }

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}