package br.edu.ifto.usuariocomtarefasnoturno.operations;

import br.edu.ifto.usuariocomtarefasnoturno.config.Serial;
import br.edu.ifto.usuariocomtarefasnoturno.model.Categoria;
import br.edu.ifto.usuariocomtarefasnoturno.model.Tarefa;
import br.edu.ifto.usuariocomtarefasnoturno.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/cadastrartarefa")
public class CadastrarTarefa extends HttpServlet {

    // mostra o formulário em branco com as categorias dinâmicas
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Verifica se está logado antes de mostrar o formulário
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogado") == null) {
            out.print("<p>Você precisa estar logado para acessar esta página.</p><a href='index.html'>Voltar</a>");
            return;
        }

        // Pega as categorias globais do sistema
        List<Categoria> categorias = (List<Categoria>) getServletContext().getAttribute("categorias");

        out.println("<h2>Cadastrar Nova Tarefa</h2>");
        out.println("<form action='cadastrartarefa' method='post'>"); // Envia os dados para o doPost desta mesma classe
        out.println("<label>Título: <input type='text' name='titulo' required></label><br><br>");
        out.println("<label>Descrição: <input type='text' name='descricao'></label><br><br>");
        out.println("<label>Data: <input type='date' name='data'></label><br><br>");

        // O Select Dinâmico
        out.println("<label>Categoria: ");
        out.println("<select name='categoria' required>");
        out.println("<option value=''>-- Selecione uma Categoria --</option>");
        if (categorias != null) {
            for (Categoria c : categorias) {
                out.println("<option value='" + c.getNome() + "'>" + c.getNome() + "</option>");
            }
        }
        out.println("</select></label><br><br>");

        out.println("<input type='submit' value='Cadastrar'>");
        out.println("</form>");
        out.println("<br><a href='vertarefas'><button>Cancelar</button></a>");
    }

    // salva os dados que vieram do formulário
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");

        String titulo = request.getParameter("titulo");
        String descricao = request.getParameter("descricao");
        String data = request.getParameter("data");
        String categoria = request.getParameter("categoria");

        if (!(titulo != null && !titulo.isBlank() && descricao != null && !descricao.isBlank())) {
            out.print("<p>Você precisa informar o título e a descrição.</p>");
            return;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogado") == null) {
            out.print("<p>Você precisa estar logado.</p>");
            return;
        }

        Tarefa t;
        if (data != null && !data.isBlank()) {
            t = new Tarefa(titulo, descricao, LocalDate.parse(data), categoria);
        } else {
            t = new Tarefa(titulo, descricao, categoria);
        }

        t.setId(Serial.proximo(getServletContext()));
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        usuarioLogado.getTarefas().add(t);
        out.print("<p>Tarefa adicionada com sucesso.</p>");

        out.println("<a href='vertarefas'><button>Voltar para Tarefas</button></a> ");
        out.println("<a href='sair'><button>Logout</button></a>");
    }
}