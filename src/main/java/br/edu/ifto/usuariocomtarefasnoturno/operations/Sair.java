package br.edu.ifto.usuariocomtarefasnoturno.operations;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/sair")
public class Sair extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //pegar a sessão atual do usuário
        //o false fala: me dê essa sessão se existir, se não existir, não crie uma nova.
        HttpSession sessao = request.getSession(false);

        if (sessao != null){
            sessao.invalidate(); // invalida/apaga a sessão
        }

        response.sendRedirect("index.html"); // retorna para o index
    }
}
