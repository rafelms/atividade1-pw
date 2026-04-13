package br.edu.ifto.usuariocomtarefasnoturno.operations;

import br.edu.ifto.usuariocomtarefasnoturno.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/editarusuario")
public class EditarUsuario extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        request.setCharacterEncoding("utf-8");

        HttpSession sessao = request.getSession(false);

        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            out.println("<body>");
            out.print("<h3>Acesso Negado!</h3>");
            out.print("<p>Você precisa estar logado para editar seu perfil.</p>");
            out.print("<a href='index.html'>Voltar para o Início</a>");
            out.println("</body>");
            return;
        }

        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");


        String novoNome = request.getParameter("nome");
        String novaSenha = request.getParameter("senha");

        usuarioLogado.setNome(novoNome.trim());
        usuarioLogado.setSenha(novaSenha.trim());


        out.print("<body>");
        out.print("<h3>Perfil atualizado com sucesso!</h3>");
        out.print("<p>Seu novo nome é: " + usuarioLogado.getNome() + "</p>");
        out.print("<a href='index.html'>Voltar para o Início</a>");
        out.println("<a href='sair'><button>Logout</button></a>");
        out.print("<body>");


    }


}
