package br.com.observaacao.dto;

import br.com.observaacao.domain.enums.Categoria;
import br.com.observaacao.domain.enums.Prioridade;

public class SolicitacaoAnonimaRequest {

    private Categoria categoria;
    private Prioridade prioridade;
    private String localizacao;
    private String descricao;
    private String motivoAnonimato;

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMotivoAnonimato() {
        return motivoAnonimato;
    }

    public void setMotivoAnonimato(String motivoAnonimato) {
        this.motivoAnonimato = motivoAnonimato;
    }
}
