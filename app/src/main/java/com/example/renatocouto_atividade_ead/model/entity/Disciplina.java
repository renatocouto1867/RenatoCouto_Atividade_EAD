package com.example.renatocouto_atividade_ead.model.entity;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Disciplina implements Serializable {
    private String id;
    private String nome;
    private double notaObtida;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getNotaObtida() {
        return notaObtida;
    }

    public void setNotaObtida(double notaObtida) {
        this.notaObtida = notaObtida;
    }

    @Override
    public String toString() {
        return "Disciplina{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", notaObtida=" + notaObtida +
                '}';
    }
}
