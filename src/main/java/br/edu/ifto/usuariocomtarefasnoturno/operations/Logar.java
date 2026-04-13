package br.edu.ifto.usuariocomtarefasnoturno.operations;

import br.edu.ifto.usuariocomtarefasnoturno.model.Usuario;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@WebServlet("/logar")
public class Logar extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        request.setCharacterEncoding("utf-8");

        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        if (login != null && !login.isBlank() && senha != null && !senha.isBlank()) {
            ServletContext application = getServletContext();
            Set<Usuario> usuarios = (Set<Usuario>) application.getAttribute("usuarios");
            boolean logou = false;

            for (Usuario usuarioLogado : usuarios) {
                if (usuarioLogado.getLogin().equals(login) && usuarioLogado.getSenha().equals(senha)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("usuarioLogado", usuarioLogado);
                    logou = true;
                    out.print("<p>Logado com sucesso.");
                    break;
                }
            }

            if (!logou)
                out.print("<p>Login ou senha incorretos.");
        } else {
            out.print("<p>Você precisa informar o login e a senha.");
        }


        out.println("<a href='vertarefas'><button>Voltar para Tarefas</button></a> ");
        out.println("<a href='sair'><button>Logout</button></a>");
    }
}
