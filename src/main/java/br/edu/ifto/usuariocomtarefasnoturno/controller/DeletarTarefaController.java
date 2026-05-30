package br.edu.ifto.usuariocomtarefasnoturno.controller;

import br.edu.ifto.usuariocomtarefasnoturno.dao.TarefaDAO;
import br.edu.ifto.usuariocomtarefasnoturno.model.entities.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

// Rota corrigida para bater com o botão do vertarefas.jsp
@WebServlet("/removertarefa")
public class DeletarTarefaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuarioLogado") == null) {
            request.setAttribute("mensagemErro", "Acesso negado. Faça login.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        String tarefaId = request.getParameter("id");

        if (tarefaId != null && !tarefaId.isBlank()) {
            try {
                int id = Integer.parseInt(tarefaId);

                // Agora sim, deleta do banco de dados!
                TarefaDAO dao = new TarefaDAO();
                dao.remover(id, usuarioLogado.getId());

                request.getSession().setAttribute("mensagemSucesso", "Tarefa deletada com sucesso!");
            } catch (Exception e) {
                request.getSession().setAttribute("mensagemErro", "Erro ao deletar: " + e.getMessage());
            }
        }

        // Redireciona via GET para forçar o recarregamento da tabela
        response.sendRedirect("vertarefas");
    }
}