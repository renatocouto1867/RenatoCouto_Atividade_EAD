package com.example.renatocouto_atividade_ead.ui.listadiciplina;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renatocouto_atividade_ead.R;
import com.example.renatocouto_atividade_ead.model.entity.Disciplina;

import java.util.List;

public class ItemListarDisciplinasAdapter extends RecyclerView.Adapter<ItemListarDisciplinasAdapter.ViewHolder> {
    private final OnItemClickListener onItemClickListener;
    private final List<Disciplina> disciplinas;

    public ItemListarDisciplinasAdapter(List<Disciplina> disciplinas, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.disciplinas = disciplinas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_disciplina_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Disciplina disciplina = disciplinas.get(position);

        holder.tvDisciplina.setText(disciplina.getNome());
        holder.tvNota.setText(String.format("%.2f", disciplina.getNotaObtida()));

        holder.btnEditDisciplina.setOnClickListener(v -> onItemClickListener.onEditClick(disciplina));
        holder.btnDeleteDisciplina.setOnClickListener(v -> onItemClickListener.onDeleteClick(disciplina));
    }

    @Override
    public int getItemCount() {
        return disciplinas == null ? 0 : disciplinas.size();
    }

    public interface OnItemClickListener {
        void onEditClick(Disciplina disciplina);

        void onDeleteClick(Disciplina disciplina);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDisciplina;
        TextView tvNota;
        ImageButton btnEditDisciplina;
        ImageButton btnDeleteDisciplina;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDisciplina = itemView.findViewById(R.id.tv_disciplina);
            tvNota = itemView.findViewById(R.id.tv_nota);
            btnEditDisciplina = itemView.findViewById(R.id.btn_edit_Disciplina);
            btnDeleteDisciplina = itemView.findViewById(R.id.btn_delete_Disciplina);
        }
    }
}
