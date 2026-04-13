package br.edu.ifto.usuariocomtarefasnoturno.operations;

import br.edu.ifto.usuariocomtarefasnoturno.config.Serial;
import br.edu.ifto.usuariocomtarefasnoturno.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@WebServlet("/cadastraradmin")
public class CadastrarAdmin extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        Usuario logado = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;

        if (logado == null || !"ADMIN".equals(logado.getPerfil())) {
            out.print("<h3>Acesso Negado!</h3><p>Apenas administradores podem cadastrar outros administradores.</p>");
            return;
        }

        String nome = request.getParameter("nome");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        if (nome == null || login == null || senha == null || nome.isBlank()) {
            out.print("<p>Erro: Todos os campos são obrigatórios.</p>");
            return;
        }

        // criando e armazenando
        Usuario novoAdmin = new Usuario();
        novoAdmin.setNome(nome);
        novoAdmin.setLogin(login);
        novoAdmin.setSenha(senha);
        novoAdmin.setPerfil("ADMIN");
        novoAdmin.setId(Serial.proximo(getServletContext()));

        // setando em usuarios este novo
        Set<Usuario> usuarios = (Set<Usuario>) getServletContext().getAttribute("usuarios");

        if (usuarios.add(novoAdmin)) {
            out.print("<h3>Administrador '" + nome + "' cadastrado com sucesso!</h3>");
        } else {
            out.print("<h3>Erro: Este login já existe no sistema.</h3>");
        }

        out.print("<br><a href='cadastraradmin.html'>Voltar ao Painel</a>");
        out.println("<a href='sair'><button>Logout</button></a>");

    }
}
