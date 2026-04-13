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
import java.time.LocalDate;

@WebServlet("/editartarefa")
public class EditarTarefa extends HttpServlet {

    //primeiro faz um get para buscar o id da tarefa de deve ser alterada e guarda-la
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogado") == null) {
            out.print("<p>Acesso negado.</p><a href='index.html'>Voltar</a>");
            return;
        }

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        String tarefaId = request.getParameter("id");

        if (tarefaId == null || tarefaId.isBlank()) {
            out.print("<p>ID da tarefa não informado.</p>");
            return;
        }

        try {
            int id = Integer.parseInt(tarefaId);
            Tarefa tarefaEncontrada = null;

            // Procura a tarefa na lista do usuário
            for (Tarefa t : usuarioLogado.getTarefas()) {
                if (t.getId() == id) {
                    tarefaEncontrada = t;
                    break;
                }
            }

            if (tarefaEncontrada != null) {
                // desenhando o formulario
                out.println("<h2>Editar Tarefa</h2>");
                out.println("<form action='editartarefa' method='post'>");

                // compo invisivel para guardar o id da tarefa
                out.println("<input type='hidden' name='id' value='" + tarefaEncontrada.getId() + "'>");

                out.println("<label>Título da Tarefa: <br>");
                out.println("<input type='text' name='titulo' value='" + tarefaEncontrada.getTitulo() + "' required>");
                out.println("<input type='text' name='descricao' value='" + tarefaEncontrada.getDescricao() + "' required>");
                out.println("<input type='date' name='data' value='" + tarefaEncontrada.getData() + "' required>");
                out.println("<input type='text' name='categoria' value='" + tarefaEncontrada.getCategoria() + "' required>");
                out.println("</label><br><br>");

                out.println("<input type='submit' value='Salvar Alterações'>");
                out.println("</form>");
                out.println("<br><a href='vertarefas'>Cancelar</a>");

            } else {
                out.print("<p>Tarefa não encontrada.</p>");
            }

        } catch (NumberFormatException e) {
            out.print("<p>ID inválido.</p>");
        }
    }


    //agora com o id da tarefa em maos, fazemos o post para alterar ela e salvar
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogado") == null) {
            out.print("<p>Acesso negado.</p>");
            return;
        }

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        // pega o id que estava no campo invisivel e o novo titulo
        String tarefaId = request.getParameter("id");
        String novoTitulo = request.getParameter("titulo");
        String novaDesc = request.getParameter("descricao");
        String novaData = request.getParameter("data");
        String novaCategoria = request.getParameter("categoria");

        try {
            int id = Integer.parseInt(tarefaId);
            boolean editou = false;

            // procura a tarefa e altera o valor
            for (Tarefa t : usuarioLogado.getTarefas()) {
                if (t.getId() == id) {
                    // Atualiza os dados da tarefa (se tiver mais campos, atualize aqui também)
                    t.setTitulo(novoTitulo);
                    t.setDescricao(novaDesc);
                    t.setData(LocalDate.parse(novaData));
                    t.setCategoria(novaCategoria);
                    editou = true;
                    break;
                }
            }

            if (editou) {
                out.println("<h3>Tarefa atualizada com sucesso!</h3>");
            } else {
                out.println("<h3>Erro: Tarefa não encontrada.</h3>");
            }

            out.println("<a href='vertarefas'><button>Voltar para Tarefas</button></a>");
            out.println("<a href='sair'><button>Logout</button></a>");


        } catch (NumberFormatException e) {
            out.print("<p>Erro ao processar o ID.</p>");
        }
    }
}


