package br.edu.ifto.usuariocomtarefasnoturno.controller;

import br.edu.ifto.usuariocomtarefasnoturno.dao.CategoriaDAO;
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
import java.time.LocalDate;

@WebServlet("/editartarefa")
public class EditarTarefaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuarioLogado") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        String tarefaId = request.getParameter("id");

        try {
            int id = Integer.parseInt(tarefaId);
            TarefaDAO dao = new TarefaDAO();

            // Busca a tarefa direto do banco de dados
            Tarefa tarefaEncontrada = dao.buscarPorId(id, usuarioLogado.getId());

            if (tarefaEncontrada != null) {
                request.setAttribute("tarefaEditar", tarefaEncontrada);

                // Busca as categorias sempre frescas do banco
                CategoriaDAO catDao = new CategoriaDAO();
                request.setAttribute("listaCategorias", catDao.listarTodas());

                request.getRequestDispatcher("/WEB-INF/jsp/editartarefa.jsp").forward(request, response);
            } else {
                request.getSession().setAttribute("mensagemErro", "Tarefa não encontrada no banco.");
                response.sendRedirect("vertarefas");
            }
        } catch (Exception e) {
            request.getSession().setAttribute("mensagemErro", "ID inválido.");
            response.sendRedirect("vertarefas");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession(false);
        Usuario usuarioLogado = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;

        if (usuarioLogado == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String titulo = request.getParameter("titulo");
            String descricao = request.getParameter("descricao");
            String data = request.getParameter("data");

            // Agora recebemos o ID da categoria, e não o nome
            int idCategoria = Integer.parseInt(request.getParameter("categoria"));

            // Monta o objeto Tarefa
            Tarefa t = new Tarefa();
            t.setId(id);
            t.setIdUsuario(usuarioLogado.getId());
            t.setTitulo(titulo);
            t.setDescricao(descricao);

            if(data != null && !data.isBlank()){
                t.setData(LocalDate.parse(data));
            }
            t.setIdCategoria(idCategoria);

            // Chama o DAO para ATUALIZAR NO BANCO
            TarefaDAO dao = new TarefaDAO();
            dao.atualizar(t);

            request.getSession().setAttribute("mensagemSucesso", "Tarefa atualizada com sucesso!");

        } catch (Exception e) {
            request.getSession().setAttribute("mensagemErro", "Erro ao atualizar: " + e.getMessage());
        }

        response.sendRedirect("vertarefas");
    }
}