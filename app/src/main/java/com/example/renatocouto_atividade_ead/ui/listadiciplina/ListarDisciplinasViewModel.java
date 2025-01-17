package com.example.renatocouto_atividade_ead.ui.listadiciplina;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.renatocouto_atividade_ead.model.entity.Disciplina;
import com.example.renatocouto_atividade_ead.model.repository.DisciplinaRepository;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListarDisciplinasViewModel extends ViewModel {
    private final MutableLiveData<List<Disciplina>> listMutableLiveData;
    private final MutableLiveData<String> msResult;
    private final ExecutorService executorService;
    private final DisciplinaRepository disciplinaRepository;

    public ListarDisciplinasViewModel() {
        listMutableLiveData = new MutableLiveData<>();
        msResult = new MutableLiveData<>();
        executorService = Executors.newSingleThreadExecutor();
        disciplinaRepository = new DisciplinaRepository();
    }

    private void baixarLista() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                disciplinaRepository.getAllDisciplinas(new DisciplinaRepository.OnDisciplinasCarregamentoListener() {
                    @Override
                    public void sucesso(ArrayList<Disciplina> disciplinas) {
                        listMutableLiveData.postValue(disciplinas);
                    }

                    @Override
                    public void error(DatabaseError error) {
                        List<Disciplina> disciplinas = new ArrayList<>();
                        listMutableLiveData.postValue(disciplinas);
                        msResult.postValue("erroSalvar");
                    }
                });

            }
        });
    }

    public LiveData<List<Disciplina>> getDisciplinas() {
        baixarLista();
        return listMutableLiveData;
    }

    public void deletar(Disciplina disciplina) {
        disciplinaRepository.deleteDisciplina(disciplina.getId(), new DisciplinaRepository.OnDeletarDisciplinaListener() {
            @Override
            public void sucesso() {
                msResult.postValue("sucessoExcluir");
                baixarLista();
            }

            @Override
            public void erro(Exception e) {
                msResult.postValue("erroExcluir");
            }
        });
    }

    public LiveData<String> getMsResult() {
        return msResult;
    }

}