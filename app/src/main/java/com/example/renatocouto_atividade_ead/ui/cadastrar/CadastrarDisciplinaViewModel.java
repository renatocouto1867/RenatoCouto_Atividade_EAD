package com.example.renatocouto_atividade_ead.ui.cadastrar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.renatocouto_atividade_ead.model.entity.Disciplina;
import com.example.renatocouto_atividade_ead.model.repository.DisciplinaRepository;

public class CadastrarDisciplinaViewModel extends ViewModel {

    private final MutableLiveData<String> msResult;
    private final DisciplinaRepository repository;


    public CadastrarDisciplinaViewModel() {
        msResult = new MutableLiveData<>();
        repository = new DisciplinaRepository();
    }

    public void salvarDisciplina(Disciplina disciplina) {
        repository.salvarDisciplina(disciplina, new DisciplinaRepository.OnSalvarDisciplinaListener() {
            @Override
            public void sucesso() {
                msResult.setValue("sucesso");
            }

            @Override
            public void erro(Exception e) {
                msResult.setValue("Erro");
            }
        });

    }

    public LiveData<String> getMsResult() {
        return msResult;
    }
}