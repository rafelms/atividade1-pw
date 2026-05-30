package br.edu.ifto.usuariocomtarefasnoturno.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/sair")
public class SairController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Pega a sessão atual do usuário (se ela existir)
        HttpSession sessao = request.getSession(false);

        if (sessao != null) {
            sessao.invalidate(); // Destrói a sessão e limpa o 'usuarioLogado' da memória
        }

        // Redireciona para a nossa nova página JSP pública na raiz
        response.sendRedirect("index.jsp");
    }

    // se forçarem um post, exibimos o doget e ele faz o logout do mesmo jeito.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}