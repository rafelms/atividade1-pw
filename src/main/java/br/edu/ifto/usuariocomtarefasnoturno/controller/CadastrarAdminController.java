package br.edu.ifto.usuariocomtarefasnoturno.controller;

import br.edu.ifto.usuariocomtarefasnoturno.dao.UsuarioDAO;
import br.edu.ifto.usuariocomtarefasnoturno.model.entities.Usuario;
import br.edu.ifto.usuariocomtarefasnoturno.model.enums.PerfilEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/cadastraradmin")
public class CadastrarAdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Usuario logado = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;

        if (logado == null || !PerfilEnum.ADMIN.equals(logado.getPerfil())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso Negado.");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/jsp/cadastraradmin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession(false);
        Usuario logado = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;

        if (logado == null || !PerfilEnum.ADMIN.equals(logado.getPerfil())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso Negado.");
            return;
        }

        String nome = request.getParameter("nome");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        if (nome == null || nome.isBlank() || login == null || login.isBlank() || senha == null || senha.isBlank()) {
            request.setAttribute("mensagemErro", "Erro: Todos os campos são obrigatórios.");
            request.getRequestDispatcher("/WEB-INF/jsp/cadastraradmin.jsp").forward(request, response);
            return;
        }

        Usuario novoAdmin = new Usuario();
        novoAdmin.setNome(nome);
        novoAdmin.setLogin(login);
        novoAdmin.setSenha(senha);
        novoAdmin.setPerfil(PerfilEnum.ADMIN);

        UsuarioDAO dao = new UsuarioDAO();
        try {
            dao.cadastrar(novoAdmin); // O DAO lidará com duplicidades ou falhas do DB
            request.setAttribute("mensagemSucesso", "Administrador '" + nome + "' cadastrado com sucesso!");
        } catch (Exception e) {
            request.setAttribute("mensagemErro", "Erro ao cadastrar: " + e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/jsp/cadastraradmin.jsp").forward(request, response);
    }
}
