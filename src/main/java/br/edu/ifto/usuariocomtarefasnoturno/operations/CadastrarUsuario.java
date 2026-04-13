package br.edu.ifto.usuariocomtarefasnoturno.operations;

import br.edu.ifto.usuariocomtarefasnoturno.config.Serial;
import br.edu.ifto.usuariocomtarefasnoturno.model.Usuario;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@WebServlet("/cadastrarusuario")
public class CadastrarUsuario extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        String nome = request.getParameter("nome");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        String perfil = request.getParameter("perfil");

        // validando que todos os campos estejam preenchidos
        if (nome == null || nome.isBlank() || login == null || login.isBlank() || senha == null || senha.isBlank() || perfil == null || perfil.isBlank()) {
            out.print("<p>Você precisa informar todos os campos.</p>");
            return;
        }

        ServletContext application = getServletContext();
        Set<Usuario> usuarios = (Set<Usuario>) application.getAttribute("usuarios");

        // Verifica se o login já existe ANTES de criar o usuário e gastar um ID da classe Serial
        if (usuarios != null) {
            for (Usuario usuarioCadastrado : usuarios) {
                // Compara direto com a String 'login' que veio do formulário
                if (usuarioCadastrado.getLogin().equals(login)) {
                    out.println("<p>Usuário não cadastrado pois já existe este login.</p>");
                    return; // Para a execução se achar duplicidade
                }
            }
        }

        // Cria o objeto.
        Usuario u = new Usuario(nome, login, senha, perfil);
        u.setId(Serial.proximo(application));

        //salvar o usuário na lista!
        if (usuarios != null) {
            usuarios.add(u);
        }

        out.print("<p>Cadastrado com sucesso.</p>");
        System.out.println(usuarios);
    }
}