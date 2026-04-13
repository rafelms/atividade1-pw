package br.edu.ifto.usuariocomtarefasnoturno.model;

import java.time.LocalDate;
import java.util.Objects;

public class Tarefa {
    private int id;
    private String titulo, descricao;
    private LocalDate data;
    private String categoria;


    public Tarefa() {
    }

    public Tarefa(int id, String titulo, String descricao, LocalDate data, String categoria) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.categoria = categoria;
    }

    public Tarefa(String titulo, String descricao, LocalDate data, String categoria) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.categoria = categoria;
    }

    public Tarefa(String titulo, String descricao, String categoria) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tarefa tarefa = (Tarefa) o;
        return id == tarefa.id && Objects.equals(titulo, tarefa.titulo) && Objects.equals(descricao, tarefa.descricao) && Objects.equals(data, tarefa.data) && Objects.equals(categoria, tarefa.categoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, descricao, data, categoria);
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", data=" + data +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
