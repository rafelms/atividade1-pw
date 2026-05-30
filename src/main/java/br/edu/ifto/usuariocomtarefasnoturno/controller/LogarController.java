package br.edu.ifto.usuariocomtarefasnoturno.controller;

import br.edu.ifto.usuariocomtarefasnoturno.dao.UsuarioDAO;
import br.edu.ifto.usuariocomtarefasnoturno.model.entities.Usuario;
import br.edu.ifto.usuariocomtarefasnoturno.model.enums.PerfilEnum;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;

@WebServlet("/logar") // Confirme se essa é a rota no form do seu index.jsp
public class LogarController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String senha  = request.getParameter("senha");

        UsuarioDAO dao = new UsuarioDAO();

        try {
            // O DAO vai no MySQL procurar este usuário e senha
            Usuario u = dao.autenticar(login, senha);

            if (u != null) {
                // Achou! Cria a sessão
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogado", u);

                // Redireciona dependendo do perfil
                if (PerfilEnum.ADMIN.equals(u.getPerfil())) {
                    response.sendRedirect("gerenciarcategorias"); // ou um painel de admin
                } else {
                    response.sendRedirect("vertarefas");
                }
            } else {
                // Não achou ou senha errada
                request.setAttribute("mensagemErro", "Login ou senha incorretos.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("mensagemErro", "Erro ao conectar com o banco: " + e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
