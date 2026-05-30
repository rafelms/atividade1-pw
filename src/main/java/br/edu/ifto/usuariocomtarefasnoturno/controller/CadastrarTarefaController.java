package br.edu.ifto.usuariocomtarefasnoturno.controller;

import br.edu.ifto.usuariocomtarefasnoturno.dao.CategoriaDAO;
import br.edu.ifto.usuariocomtarefasnoturno.dao.TarefaDAO;
import br.edu.ifto.usuariocomtarefasnoturno.model.entities.Categoria;
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
import java.util.List;

@WebServlet("/cadastrartarefa")
public class CadastrarTarefaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuarioLogado") == null) {
            request.setAttribute("mensagemErro", "Você precisa estar logado.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // Pega as categorias DO BANCO DE DADOS usando o DAO
        CategoriaDAO dao = new CategoriaDAO();
        List<Categoria> categorias = dao.listarTodas();
        request.setAttribute("listaCategorias", categorias);

        request.getRequestDispatcher("/WEB-INF/jsp/cadastrartarefa.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogado") == null) {
            request.setAttribute("mensagemErro", "Sessão expirada.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        String titulo = request.getParameter("titulo");
        String descricao = request.getParameter("descricao");
        String data = request.getParameter("data");
        String categoriaId = request.getParameter("categoria"); // Agora receberemos o ID da Categoria do Select

        if (titulo == null || titulo.isBlank() || descricao == null || descricao.isBlank()) {
            request.setAttribute("mensagemErro", "Você precisa informar o título e a descrição.");
            request.getRequestDispatcher("cadastrartarefa").forward(request, response); // recarrega a página via GET para buscar categorias
            return;
        }

        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");

        Tarefa t = new Tarefa();
        t.setTitulo(titulo);
        t.setDescricao(descricao);
        if (data != null && !data.isBlank()) {
            t.setData(LocalDate.parse(data));
        }

        // Configura os relacionamentos usando IDs (Chaves Estrangeiras)
        t.setIdUsuario(logado.getId());
        t.setIdCategoria(Integer.parseInt(categoriaId));

        TarefaDAO dao = new TarefaDAO();
        try {
            dao.cadastrar(t);
            request.setAttribute("mensagemSucesso", "Tarefa adicionada com sucesso!");
        } catch (Exception e) {
            request.setAttribute("mensagemErro", "Erro ao salvar tarefa: " + e.getMessage());
        }

        request.getRequestDispatcher("vertarefas").forward(request, response);
    }
}