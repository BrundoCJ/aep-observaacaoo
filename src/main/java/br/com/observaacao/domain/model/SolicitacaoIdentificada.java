package br.com.observaacao.domain.model;

import br.com.observaacao.domain.enums.Categoria;
import br.com.observaacao.domain.enums.Prioridade;

public class SolicitacaoIdentificada extends Solicitacao {

    private final String nomeCompleto;
    private final String email;
    private final String telefone;

    public SolicitacaoIdentificada(String protocolo, Categoria categoria, String descricao,
                                   String localizacao, String anexo, Prioridade prioridade,
                                   String nomeCompleto, String email, String telefone) {
        super(protocolo, categoria, descricao, localizacao, anexo, prioridade);
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.telefone = telefone;
    }

    @Override
    public boolean isAnonima() {
        return false;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }
}
