package br.edu.ifto.usuariocomtarefasnoturno.controller;

import br.edu.ifto.usuariocomtarefasnoturno.dao.TarefaDAO;
import br.edu.ifto.usuariocomtarefasnoturno.model.entities.Tarefa;
import br.edu.ifto.usuariocomtarefasnoturno.model.entities.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/vertarefas")
public class VerTarefasController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuarioLogado") == null) {
            request.setAttribute("mensagemErro", "Você precisa estar logado para ver suas tarefas.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // 1. Recupera o usuário logado da sessão para saber o ID dele
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");

        try {
            // 2. Instancia o DAO e busca as tarefas reais salvas no banco
            TarefaDAO dao = new TarefaDAO();
            List<Tarefa> lista = dao.listarPorUsuario(logado.getId());

            // 3. Envia a lista para a JSP através do escopo do Request
            request.setAttribute("listaTarefas", lista);

        } catch (Exception e) {
            request.setAttribute("mensagemErro", "Erro ao carregar tarefas: " + e.getMessage());
        }

        // Tudo certo! Despacha para o JSP montado com os dados acoplados
        request.getRequestDispatcher("/WEB-INF/jsp/vertarefas.jsp").forward(request, response);
    }

    // Adicionamos o doPost repassando para o doGet, pois outros controllers
    // vão fazer 'forward' via POST para esta rota após cadastrar/editar.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
