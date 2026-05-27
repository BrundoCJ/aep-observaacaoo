package br.com.observaacao.domain.model;

import br.com.observaacao.domain.enums.Categoria;
import br.com.observaacao.domain.enums.Prioridade;

public class SolicitacaoAnonima extends Solicitacao {

    private final String motivoAnonimato;

    public SolicitacaoAnonima(String protocolo, Categoria categoria, String descricao,
                              String localizacao, String anexo, Prioridade prioridade,
                              String motivoAnonimato) {
        super(protocolo, categoria, descricao, localizacao, anexo, prioridade);
        this.motivoAnonimato = motivoAnonimato;
    }

    @Override
    public boolean isAnonima() {
        return true;
    }

    public String getMotivoAnonimato() {
        return motivoAnonimato;
    }
}
