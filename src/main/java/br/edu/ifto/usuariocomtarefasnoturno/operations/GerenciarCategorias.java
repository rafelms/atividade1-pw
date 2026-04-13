package br.edu.ifto.usuariocomtarefasnoturno.operations;

import br.edu.ifto.usuariocomtarefasnoturno.model.Categoria;
import br.edu.ifto.usuariocomtarefasnoturno.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/gerenciarcategorias")
public class GerenciarCategorias extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // so admins passam daqui
        HttpSession session = request.getSession(false);
        Usuario logado = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;
        if (logado == null || !"ADMIN".equals(logado.getPerfil())) {
            out.print("Acesso Negado.");
            return;
        }

        List<Categoria> lista = (List<Categoria>) getServletContext().getAttribute("categorias");
        String acao = request.getParameter("acao");
        String idParam = request.getParameter("id");

        // logica do remover
        if ("remover".equals(acao) && idParam != null) {
            int id = Integer.parseInt(idParam);
            lista.removeIf(c -> c.getId() == id);
            response.sendRedirect("gerenciarcategorias"); // Recarrega a página
            return;
        }

        // logica do editar
        Categoria catParaEditar = null;
        if ("editar".equals(acao) && idParam != null) {
            int id = Integer.parseInt(idParam);
            catParaEditar = lista.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
        }

        out.print("<h2>Gerenciar Categorias</h2>");

        // Formulário de Adição ou Edição
        String tituloForm = (catParaEditar == null) ? "Adicionar Nova" : "Editar Categoria";
        String valorInput = (catParaEditar == null) ? "" : catParaEditar.getNome();
        String idHidden = (catParaEditar == null) ? "" : "<input type='hidden' name='id' value='"+catParaEditar.getId()+"'>";

        out.print("<form action='gerenciarcategorias' method='post'>");
        out.print(tituloForm + ": <input type='text' name='nome' value='"+valorInput+"' required>");
        out.print(idHidden);
        out.print("<input type='submit' value='Salvar'>");
        out.print("</form><hr>");

        // Tabela de Listagem
        out.print("<table border='1'>" +
                "<tr>" +
                "<th>ID</th>" +
                "<th>Nome</th>" +
                "<th>Ações</th>" +
                "</tr>");
        for (Categoria c : lista) {
            out.print("<tr>" +
                    "<td>"+c.getId()+"</td>" +
                    "<td>"+c.getNome()+"</td>");
            out.print("<td><a href='gerenciarcategorias?acao=editar&id="+c.getId()+"'>Editar</a> | ");
            out.print("<a href='gerenciarcategorias?acao=remover&id="+c.getId()+"'>Remover</a></td></tr>");
        }
        out.print("</table>");
        out.print("<br><a href='cadastraradmin.html'>Voltar</a>");
        out.println("<a href='sair'><button>Logout</button></a>");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        List<Categoria> lista = (List<Categoria>) getServletContext().getAttribute("categorias");

        String idParam = request.getParameter("id");
        String nome = request.getParameter("nome");

        if (idParam == null || idParam.isEmpty()) {
            // ADICIONAR: Se não veio ID, é uma nova categoria
            int novoId = (lista.isEmpty()) ? 1 : lista.get(lista.size()-1).getId() + 1;
            lista.add(new Categoria(novoId, nome));
        } else {
            // se veio ID, procura e atualiza
            int id = Integer.parseInt(idParam);
            for (Categoria c : lista) {
                if (c.getId() == id) {
                    c.setNome(nome);
                    break;
                }
            }
        }
        response.sendRedirect("gerenciarcategorias");
    }
}