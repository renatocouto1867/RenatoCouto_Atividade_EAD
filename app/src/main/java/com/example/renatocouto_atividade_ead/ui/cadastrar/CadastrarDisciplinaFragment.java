package com.example.renatocouto_atividade_ead.ui.cadastrar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.renatocouto_atividade_ead.R;
import com.example.renatocouto_atividade_ead.auxiliar.Mensagens;
import com.example.renatocouto_atividade_ead.databinding.FragmentCadastrarDisciplinaBinding;
import com.example.renatocouto_atividade_ead.model.entity.Disciplina;
import com.example.renatocouto_atividade_ead.ui.listadiciplina.ListarDisciplinasFragment;

public class CadastrarDisciplinaFragment extends Fragment {
    private FragmentCadastrarDisciplinaBinding binding;
    private CadastrarDisciplinaViewModel viewModel;
    private Disciplina disciplina;
    private TextView textTitulo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(CadastrarDisciplinaViewModel.class);
        binding = FragmentCadastrarDisciplinaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle bundle = getArguments();
        if (bundle != null) {
            disciplina = (Disciplina) bundle.getSerializable("disciplina");
            binding.editNomeDisciplina.setText(disciplina.getNome());
            binding.editNotaObtida.setText(String.valueOf(disciplina.getNotaObtida()));
            textTitulo = getActivity().findViewById(R.id.txt_titulo_toolbar);
            textTitulo.setText(R.string.editar_disciplina);

        } else disciplina = new Disciplina();

        binding.buttonSalvar.setOnClickListener(v -> salvarDisciplina(disciplina));

        viewModel.getMsResult().observe(getViewLifecycleOwner(), msResult -> {
            if (msResult.equals("sucesso")) {
                Mensagens.showSucesso(requireView(), getString(R.string.disciplina_gravada_com_sucesso));

                ListarDisciplinasFragment listarDisciplinasFragment = new ListarDisciplinasFragment();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, listarDisciplinasFragment)
                        .addToBackStack(null)
                        .commit();
            }
            if (msResult.equals("Erro")) {

                Mensagens.showErro(requireView(), getString(R.string.houve_um_erro_ao_gravar_a_disciplina));
            }
        });

        return root;
    }
// codigo significa
// -1	okay
// -2	nome vazio
// -3	nota vazia
// -4	nome maximo
// -5	nota maxima
// -6	nota negativa
// -7	nota invalida

    private void salvarDisciplina(Disciplina disciplina) {
        String nome = binding.editNomeDisciplina.getText().toString();
        String notaStr = binding.editNotaObtida.getText().toString();

        double notaValida = convertValidaNota(notaStr);

        int codigo = checarCamposVazios(nome, notaStr);

        if (codigo == -2) {//nome vazio
            focusNomeMsg(getString(R.string.nome_obrigatorio));
            return;
        } else if (codigo == -3) {//nota vazia
            focusNotaMsg(getString(R.string.nota_obrigatoria));
            return;
        }

        if (!validaTamanhoMaximoNome(nome)) {//nome maximo
            focusNomeMsg(getString(R.string.max_40_caracteres));
            return;
        }

        if (notaValida == -6) {//nota negativa
            focusNotaMsg(getString(R.string.nota_negativa));
        } else if (notaValida == -5) {//nota maior que maxima
            focusNotaMsg(getString(R.string.nota_maxima));
        } else if (notaValida == -7) {//nota invalida
            focusNotaMsg(getString(R.string.nota_invalida));
        } else {
            disciplina.setNome(nome);
            disciplina.setNotaObtida(notaValida);
            viewModel.salvarDisciplina(disciplina);
            limparFormulario();
        }
    }

    private void limparFormulario() {
        binding.editNomeDisciplina.setText("");
        binding.editNotaObtida.setText("");
        binding.editNomeDisciplina.requestFocus();
    }

    private void focusNotaMsg(String mensagem) {
        binding.editNotaObtida.requestFocus();
        binding.editNotaObtida.setError(mensagem);
    }


    private void focusNomeMsg(String mensagem) {
        binding.editNomeDisciplina.requestFocus();
        binding.editNomeDisciplina.setError(mensagem);
    }

    private int checarCamposVazios(String nome, String notaStr) {
        if (nome.isEmpty()) {
            return -2;//codigo para nome vazio
        }

        if (notaStr.isEmpty()) {
            return -3;//codigo para nota vazia
        }
        return -1;
    }

    private boolean validaTamanhoMaximoNome(String nome) {
        return (nome.length() < 41);
    }

    private double convertValidaNota(String notaStr) {
        try {
            double nota = Double.parseDouble(notaStr);
            return checarNotaMaxima(nota);
        } catch (NumberFormatException e) {
            return -7; //codigo para nota invalida
        }
    }

    private double checarNotaMaxima(double nota) {
        if (nota >= 0 && nota <= 10) {
            return nota;
        }
        return nota < 0 ? -6 : -5; // -6 para nota negativa, -5 para acima do máximo
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}