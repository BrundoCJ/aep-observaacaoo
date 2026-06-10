package br.com.observaacao.domain.model;

import br.com.observaacao.domain.enums.Categoria;
import br.com.observaacao.domain.enums.Prioridade;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("IDENTIFICADA")
public class SolicitacaoIdentificada extends Solicitacao {

    private String nomeCompleto;
    private String email;
    private String telefone;

    // JPA: construtor protegido sem argumentos
    protected SolicitacaoIdentificada() {}

    public SolicitacaoIdentificada(String protocolo, Categoria categoria, String descricao,
                                   String localizacao, String anexo, Prioridade prioridade,
                                   String nomeCompleto, String email, String telefone) {
        super(protocolo, categoria, descricao, localizacao, anexo, prioridade);
        this.nomeCompleto = nomeCompleto;
        this.email        = email;
        this.telefone     = telefone;
    }

    @Override
    public boolean isAnonima() { return false; }

    public String getNomeCompleto() { return nomeCompleto; }
    public String getEmail()        { return email; }
    public String getTelefone()     { return telefone; }
}
