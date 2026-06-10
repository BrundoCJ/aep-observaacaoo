package br.com.observaacao.domain.model;

import br.com.observaacao.domain.enums.Categoria;
import br.com.observaacao.domain.enums.Prioridade;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ANONIMA")
public class SolicitacaoAnonima extends Solicitacao {

    @Column(name = "motivo_anonimato", length = 1000)
    private String motivoAnonimato;

    // JPA: construtor protegido sem argumentos
    protected SolicitacaoAnonima() {}

    public SolicitacaoAnonima(String protocolo, Categoria categoria, String descricao,
                              String localizacao, String anexo, Prioridade prioridade,
                              String motivoAnonimato) {
        super(protocolo, categoria, descricao, localizacao, anexo, prioridade);
        this.motivoAnonimato = motivoAnonimato;
    }

    @Override
    public boolean isAnonima() { return true; }

    public String getMotivoAnonimato() { return motivoAnonimato; }
}
