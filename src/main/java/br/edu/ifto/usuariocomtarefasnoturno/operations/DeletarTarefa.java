package br.edu.ifto.usuariocomtarefasnoturno.operations;

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

@WebServlet("/deletartarefa")
public class DeletarTarefa extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        request.setCharacterEncoding("utf-8");
        out.println("<body>");
        //checa a sessao
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogado") == null) {
            out.print("<p>Acesso negado. Você precisa estar logado.</p>");
            out.println("<a href='index.html'>Voltar para o Login</a>");
            return;
        }

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        // pega o id da tarefa
        String tarefaId = request.getParameter("id");
        if (tarefaId == null || tarefaId.isBlank()) {
            out.print("<p>Você precisa informar o id da tarefa.</p>");
            return;
        }

        // converte o id passado em inteiro para
        try {
            int id = Integer.parseInt(tarefaId);

            // O removeIf retorna 'true' se ele achou e deletou algo, e 'false' se não achou o ID.
            boolean deletou = usuarioLogado.getTarefas().removeIf(t -> t.getId() == id); // remova se t for igual ao id da tarefa

            if (deletou) {
                out.println("<p>Tarefa deletada com sucesso.</p>");
            } else {
                out.print("<p>Não foi possível deletar pois o id não foi encontrado na sua lista.</p>");
            }

        } catch (NumberFormatException e) {
            // Se o usuário digitou letras no lugar do número do ID
            out.print("<p>Erro: O ID da tarefa fornecido é inválido.</p>");
        }

        out.println("<br>");
        out.println("<a href='vertarefas'><button>Voltar para Tarefas</button></a> ");
        out.println("<a href='sair'><button>Logout</button></a>");
        out.println("</body>");
    }
}