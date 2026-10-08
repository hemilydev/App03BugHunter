package com.hemily.app03_bughunter;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hemily.app03_bughunter.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private final List<Bug> bugs = new ArrayList<>();
    private BugAdapter adapter;
    private int contadorCodigo = 0;

    private static final int TAMANHO_MINIMO_DESCRICAO = 10;
    private static final int PONTOS_POR_GRAVIDADE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new BugAdapter(
                bugs,
                this::alternarStatus,
                this::removerBug
        );
        binding.recyclerBugs.setLayoutManager(
                new LinearLayoutManager(this)
        );
        binding.recyclerBugs.setAdapter(adapter);

        atualizarTextoGravidade(binding.seekGravidade.getProgress());

        binding.seekGravidade.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                atualizarTextoGravidade(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        binding.buttonRegistrar.setOnClickListener(view -> registrarBug());

        atualizarPainel();
    }

    private void registrarBug() {
        String titulo = binding.editTitulo.getText().toString().trim();
        String modulo = binding.editModulo.getText().toString().trim();
        String descricao = binding.editDescricao.getText().toString().trim();
        int gravidade = binding.seekGravidade.getProgress();

        if (titulo.isEmpty()) {
            binding.editTitulo.setError(getString(R.string.erro_titulo));
            binding.editTitulo.requestFocus();
            return;
        }

        if (modulo.isEmpty()) {
            binding.editModulo.setError(getString(R.string.erro_modulo));
            binding.editModulo.requestFocus();
            return;
        }

        if (descricao.length() < TAMANHO_MINIMO_DESCRICAO) {
            binding.editDescricao.setError(getString(R.string.erro_descricao));
            binding.editDescricao.requestFocus();
            return;
        }

        contadorCodigo++;
        String codigo = getString(R.string.formato_codigo, contadorCodigo);

        Bug bug = new Bug(
                codigo,
                titulo,
                modulo,
                descricao,
                gravidade,
                false,
                obterIcone(gravidade)
        );

        bugs.add(bug);
        int posicao = bugs.size() - 1;
        adapter.notifyItemInserted(posicao);
        binding.recyclerBugs.scrollToPosition(posicao);

        limparFormulario();
        atualizarPainel();

        Toast.makeText(
                this,
                getString(R.string.msg_registrado, codigo),
                Toast.LENGTH_SHORT
        ).show();
    }

    private void alternarStatus(Bug bug) {
        int posicao = bugs.indexOf(bug);
        if (posicao == RecyclerView.NO_POSITION) {
            return;
        }

        bug.setCorrigido(!bug.isCorrigido());
        adapter.notifyItemChanged(posicao);
        atualizarPainel();
    }

    private void removerBug(Bug bug) {
        int posicao = bugs.indexOf(bug);
        if (posicao == RecyclerView.NO_POSITION) {
            return;
        }

        bugs.remove(posicao);
        adapter.notifyItemRemoved(posicao);
        atualizarPainel();

        Toast.makeText(
                this,
                getString(R.string.msg_removido, bug.getCodigo()),
                Toast.LENGTH_SHORT
        ).show();
    }

    private void atualizarPainel() {
        int corrigidos = 0;
        int pontos = 0;

        for (Bug bug : bugs) {
            if (bug.isCorrigido()) {
                corrigidos++;
                pontos += bug.getGravidade() * PONTOS_POR_GRAVIDADE;
            }
        }

        int total = bugs.size();
        int abertos = total - corrigidos;

        binding.textTotal.setText(String.valueOf(total));
        binding.textAbertos.setText(String.valueOf(abertos));
        binding.textCorrigidos.setText(String.valueOf(corrigidos));
        binding.textPontos.setText(String.valueOf(pontos));

        if (bugs.isEmpty()) {
            binding.textListaVazia.setVisibility(View.VISIBLE);
        } else {
            binding.textListaVazia.setVisibility(View.GONE);
        }
    }

    private void atualizarTextoGravidade(int gravidade) {
        binding.textValorGravidade.setText(
                getString(R.string.valor_gravidade, gravidade, getString(obterNivelGravidade(gravidade)))
        );
    }

    private void limparFormulario() {
        binding.editTitulo.setText("");
        binding.editModulo.setText("");
        binding.editDescricao.setText("");
        binding.editTitulo.setError(null);
        binding.editModulo.setError(null);
        binding.editDescricao.setError(null);
        binding.seekGravidade.setProgress(1);
        binding.editTitulo.requestFocus();
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

    private int obterIcone(int gravidade) {
        switch (gravidade) {
            case 1:
            case 2:
                return R.drawable.ic_bug_baixa;
            case 3:
                return R.drawable.ic_bug_media;
            default:
                return R.drawable.ic_bug_alta;
        }
    }
}