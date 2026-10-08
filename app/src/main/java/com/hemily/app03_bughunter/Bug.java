package com.hemily.app03_bughunter;
public class Bug {
    private String codigo;
    private String titulo;
    private String modulo;
    private String descricao;
    private int gravidade;
    private boolean corrigido;
    private int imagemResId;

    public Bug() {
    }

    public Bug(String codigo, String titulo, String modulo, String descricao, int gravidade, boolean corrigido, int imagemResId) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.modulo = modulo;
        this.descricao = descricao;
        this.gravidade = gravidade;
        this.corrigido = corrigido;
        this.imagemResId = imagemResId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getGravidade() {
        return gravidade;
    }

    public void setGravidade(int gravidade) {
        this.gravidade = gravidade;
    }

    public boolean isCorrigido() {
        return corrigido;
    }

    public void setCorrigido(boolean corrigido) {
        this.corrigido = corrigido;
    }

    public int getImagemResId() {
        return imagemResId;
    }

    public void setImagemResId(int imagemResId) {
        this.imagemResId = imagemResId;
    }
}

