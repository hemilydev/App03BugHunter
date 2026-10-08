package com.hemily.app03_bughunter;

import android.content.Context;
import android.content.res.ColorStateList;

import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hemily.app03_bughunter.databinding.ItemBugBinding;

public class BugViewHolder extends RecyclerView.ViewHolder {
    private final ItemBugBinding binding;

    public BugViewHolder(ItemBugBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Bug bug, BugAdapter.OnBugClickListener listener, BugAdapter.OnBugLongClickListener longListener) {
        Context context = binding.getRoot().getContext();
        int gravidade = bug.getGravidade();
        String nivel = context.getString(obterNivelGravidade(gravidade));

        binding.textCodigo.setText(bug.getCodigo());
        binding.textTitulo.setText(bug.getTitulo());
        binding.textModulo.setText(context.getString(R.string.formato_modulo, bug.getModulo()));
        binding.textDescricao.setText(bug.getDescricao());
        binding.textGravidade.setText(context.getString(R.string.formato_gravidade, gravidade, nivel));
        binding.imageBug.setImageResource(bug.getImagemResId());

        ColorStateList corGravidade = ContextCompat.getColorStateList(
                context,
                obterCorGravidade(gravidade)
        );
        ImageViewCompat.setImageTintList(binding.imageBug, corGravidade);
        binding.textGravidade.setBackgroundTintList(corGravidade);

        configurarStatus(context, bug.isCorrigido());

        binding.getRoot().setOnClickListener(
                view -> listener.onBugClick(bug)
        );
        binding.getRoot().setOnLongClickListener(view -> {
            longListener.onBugLongClick(bug);
            return true;
        });
    }

    private void configurarStatus(Context context, boolean corrigido) {
        if (corrigido) {
            binding.textStatus.setText(R.string.status_corrigido);
            binding.textStatus.setTextColor(ContextCompat.getColor(context, R.color.status_corrigido));
            binding.cardBug.setCardBackgroundColor(ContextCompat.getColor(context, R.color.fundo_card_corrigido));
            binding.cardBug.setStrokeColor(ContextCompat.getColorStateList(context, R.color.status_corrigido));
            binding.getRoot().setAlpha(0.6f);
        } else {
            binding.textStatus.setText(R.string.status_aberto);
            binding.textStatus.setTextColor(ContextCompat.getColor(context, R.color.status_aberto));
            binding.cardBug.setCardBackgroundColor(ContextCompat.getColor(context, R.color.fundo_card));
            binding.cardBug.setStrokeColor(ContextCompat.getColorStateList(context, R.color.status_aberto));
            binding.getRoot().setAlpha(1f);
        }
    }

    private int obterNivelGravidade(int gravidade) {
        switch (gravidade) {
            case 1:
            case 2:
                return R.string.nivel_baixa;
            case 3:
                return R.string.nivel_media;
            case 4:
                return R.string.nivel_alta;
            default:
                return R.string.nivel_critica;
        }
    }

    private int obterCorGravidade(int gravidade) {
        switch (gravidade) {
            case 1:
            case 2:
                return R.color.gravidade_baixa;
            case 3:
                return R.color.gravidade_media;
            default:
                return R.color.gravidade_alta;
        }
    }
}