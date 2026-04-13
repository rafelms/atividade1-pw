package br.edu.ifto.usuariocomtarefasnoturno.config;

import br.edu.ifto.usuariocomtarefasnoturno.model.Categoria;
import br.edu.ifto.usuariocomtarefasnoturno.model.Tarefa;
import br.edu.ifto.usuariocomtarefasnoturno.model.Usuario;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebListener
public class Ouvinte implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent evento) {

        ServletContext application = evento.getServletContext();
        Set<Usuario> usuarios = new HashSet<>();

        Usuario admin = new Usuario(1, "Rafael", "admin", "admin123");
        admin.setPerfil("ADMIN"); // define o perfil como ADMIN
        usuarios.add(admin);

        Usuario u = new Usuario(2, "José", "jose", "123");
        u.setPerfil("NORMAL");
        u.getTarefas().add(new Tarefa(1, "Reunião", "Reunião com pais", LocalDate.parse("2026-03-31"), "Trabalho"));
        u.getTarefas().add(new Tarefa(2, "Compras", "Comprar pão", null, "Casa"));
        usuarios.add(u);

        Usuario u2 = new Usuario(3, "Maria", "maria", "abcd");
        u2.setPerfil("NORMAL");
        usuarios.add(u2);

        // guardando a lista de usuários e o serial no Contexto
        application.setAttribute("usuarios", usuarios);
        application.setAttribute("serial", 4);

        // criando e guardando as categorias gerais
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria(1, "Trabalho"));
        categorias.add(new Categoria(2, "Estudos"));
        categorias.add(new Categoria(3, "Lazer"));
        categorias.add(new Categoria(4, "Exercício"));
        categorias.add(new Categoria(5, "Casa"));

        application.setAttribute("categorias", categorias);
    }
}