package br.edu.ifto.usuariocomtarefasnoturno.model.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Tarefa {

    private int id;
    private String titulo;
    private String descricao;
    private LocalDate data;

    private int idUsuario;
    private int idCategoria;
    private String categoria;

    public Tarefa() {
    }

    public Tarefa(String titulo, String descricao, LocalDate data, String categoria) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.categoria = categoria;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tarefa tarefa = (Tarefa) o;
        return id == tarefa.id && idUsuario == tarefa.idUsuario && idCategoria == tarefa.idCategoria && Objects.equals(titulo, tarefa.titulo) && Objects.equals(descricao, tarefa.descricao) && Objects.equals(data, tarefa.data) && Objects.equals(categoria, tarefa.categoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, descricao, data, idUsuario, idCategoria, categoria);
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", data=" + data +
                ", idUsuario=" + idUsuario +
                ", idCategoria=" + idCategoria +
                ", categoria='" + categoria + '\'' +
                '}';
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
