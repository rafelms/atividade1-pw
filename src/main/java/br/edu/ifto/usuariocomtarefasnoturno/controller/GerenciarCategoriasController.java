package br.edu.ifto.usuariocomtarefasnoturno.controller;

import br.edu.ifto.usuariocomtarefasnoturno.dao.CategoriaDAO;
import br.edu.ifto.usuariocomtarefasnoturno.model.entities.Categoria;
import br.edu.ifto.usuariocomtarefasnoturno.model.entities.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/gerenciarcategorias")
public class GerenciarCategoriasController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Usuario logado = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;

        if (logado == null || logado.getPerfil() == null || !"ADMIN".equals(logado.getPerfil().toString())) {
            request.setAttribute("mensagemErro", "Acesso negado! Área restrita para administradores.");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        CategoriaDAO dao = new CategoriaDAO();
        String acao = request.getParameter("acao");
        String idParam = request.getParameter("id");
        Categoria catParaEditar = null;

        try {
            // Tenta remover
            if ("remover".equals(acao) && idParam != null) {
                dao.remover(Integer.parseInt(idParam));
                response.sendRedirect("gerenciarcategorias");
                return;
            }
            // Tenta buscar para edição
            if ("editar".equals(acao) && idParam != null) {
                catParaEditar = dao.buscarPorId(Integer.parseInt(idParam));
            }
        } catch (Exception e) {
            // Se falhar (ex: deletar categoria que tem tarefa vinculada), mostra na tela
            request.setAttribute("mensagemErro", "Ação negada pelo banco de dados. " + e.getMessage());
        }

        // Independente de dar erro ou não nas ações acima, CARREGA A LISTA sempre!
        try {
            List<Categoria> lista = dao.listarTodas();
            request.setAttribute("listaCategorias", lista);
            request.setAttribute("catParaEditar", catParaEditar);
        } catch (Exception e) {
            request.setAttribute("mensagemErro", "Erro ao carregar a tabela: " + e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/jsp/gerenciarcategorias.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        Usuario logado = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;

        if (logado == null || logado.getPerfil() == null || !"ADMIN".equals(logado.getPerfil().toString())) {
            request.setAttribute("mensagemErro", "Acesso negado! Operação não permitida.");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        String idParam = request.getParameter("id");
        String nomeParam = request.getParameter("nome");
        CategoriaDAO dao = new CategoriaDAO();

        if (nomeParam != null && !nomeParam.isBlank()) {
            try {
                if (idParam == null || idParam.isBlank()) {
                    // Instancia vazio e usa o setNome (100% seguro)
                    Categoria novaCat = new Categoria();
                    novaCat.setNome(nomeParam);

                    dao.cadastrar(novaCat);
                } else {
                    // Instancia vazio e usa os sets
                    Categoria catExistente = new Categoria();
                    catExistente.setNome(nomeParam);
                    catExistente.setId(Integer.parseInt(idParam));

                    dao.atualizar(catExistente);
                }

                // Só redireciona se deu TUDO certo
                response.sendRedirect("gerenciarcategorias");
                return;

            } catch (Exception e) {
                // Se o banco falhar, NÃO REDIRECIONA. Envia a mensagem de erro direto pra tela.
                request.setAttribute("mensagemErro", "Falha ao salvar no banco. Verifique se o ID está como AUTO_INCREMENT. Detalhe técnico: " + e.getMessage());
                request.setAttribute("listaCategorias", dao.listarTodas()); // Recarrega a tabela para a tela não sumir
                request.getRequestDispatcher("/WEB-INF/jsp/gerenciarcategorias.jsp").forward(request, response);
                return;
            }
        }

        response.sendRedirect("gerenciarcategorias");
    }
}