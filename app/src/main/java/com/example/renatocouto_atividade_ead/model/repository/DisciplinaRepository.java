package com.example.renatocouto_atividade_ead.model.repository;

import androidx.annotation.NonNull;

import com.example.renatocouto_atividade_ead.model.entity.Disciplina;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisciplinaRepository {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference disciplinasRef = database.getReference("disciplinas");

    public void salvarDisciplina(Disciplina disciplina, OnSalvarDisciplinaListener listener) {

        //para gerar o novo id se necessário
        if (disciplina.getId() == null || disciplina.getId().isEmpty()) {
            disciplina.setId(disciplinasRef.push().getKey());
        }

        disciplinasRef.child(disciplina.getId()).setValue(disciplina)
                .addOnSuccessListener(aVoid -> {
                    listener.sucesso();
                })
                .addOnFailureListener(e -> {
                    listener.erro(e);
                });
    }

    public void getAllDisciplinas(OnDisciplinasCarregamentoListener listener) {
        ArrayList<Disciplina> disciplinaList = new ArrayList<>();

        disciplinasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Disciplina disciplina = dataSnapshot.getValue(Disciplina.class);
                    if (disciplina != null) {
                        disciplinaList.add(disciplina);
                    }
                }
                listener.sucesso(disciplinaList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.error(error);
            }
        });
    }

    public void deleteDisciplina(String id, OnDeletarDisciplinaListener listener) {
        if (id != null && !id.isEmpty()) {
            disciplinasRef.child(id).removeValue()
                    .addOnSuccessListener(aVoid -> {
                        listener.sucesso();
                    })
                    .addOnFailureListener(e -> {
                        listener.erro(e);
                    });
        } else {
            listener.erro(new Exception("ID inválido"));
        }
    }

    //interfaces para o callback
    public interface OnDisciplinasCarregamentoListener {
        void sucesso(ArrayList<Disciplina> disciplinas);

        void error(DatabaseError error);
    }

    public interface OnSalvarDisciplinaListener {
        void sucesso();

        void erro(Exception e);
    }

    public interface OnDeletarDisciplinaListener {
        void sucesso();

        void erro(Exception e);
    }
}
