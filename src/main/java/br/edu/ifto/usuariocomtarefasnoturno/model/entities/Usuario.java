package br.edu.ifto.usuariocomtarefasnoturno.model.entities;

import br.edu.ifto.usuariocomtarefasnoturno.model.enums.PerfilEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {
    private int id;
    private String nome, login, senha;
    private List<Tarefa> tarefas = new ArrayList<>();

    private PerfilEnum perfil;


    public Usuario() {
    }

    public Usuario(String nome, String login, String senha, PerfilEnum perfil) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    public Usuario(int id, String nome, String login, String senha) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public PerfilEnum getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilEnum perfil) {
        this.perfil = perfil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome.length() < 2)
            throw new IllegalArgumentException("Nome precisa de no mínimo 2 caracteres");
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return this.id == usuario.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", tarefas=" + tarefas +
                '}';
    }

}
