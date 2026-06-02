package br.edu.ifto.usuariocomtarefasnoturno.controller;

import br.edu.ifto.usuariocomtarefasnoturno.dao.UsuarioDAO;
import br.edu.ifto.usuariocomtarefasnoturno.model.entities.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/editarusuario")
public class EditarUsuarioController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession(false);

        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            request.setAttribute("mensagemErro", "Você precisa estar logado para editar seu perfil.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("/WEB-INF/jsp/editarusuario.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        HttpSession sessao = request.getSession(false);
        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            request.setAttribute("mensagemErro", "Sessão expirada. Faça login novamente.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

        String novoNome = request.getParameter("nome");
        String novaSenha = request.getParameter("senha");

        if (novoNome == null || novoNome.isBlank() || novaSenha == null || novaSenha.isBlank()) {
            request.setAttribute("mensagemErro", "Todos os campos são obrigatórios para a atualização.");
            request.getRequestDispatcher("/WEB-INF/jsp/editarusuario.jsp").forward(request, response);
            return;
        }

        usuarioLogado.setNome(novoNome.trim());
        usuarioLogado.setSenha(novaSenha.trim());

        try {
            UsuarioDAO dao = new UsuarioDAO();
            dao.atualizar(usuarioLogado);
            request.setAttribute("mensagemSucesso", "Perfil atualizado com sucesso!");
        } catch (Exception e) {
            request.setAttribute("mensagemErro", "Erro ao salvar no banco: " + e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/jsp/editarusuario.jsp").forward(request, response);
    }
}
