package br.com.observaacao.dto;

import br.com.observaacao.domain.enums.Categoria;
import br.com.observaacao.domain.enums.Prioridade;
import br.com.observaacao.domain.enums.Status;

import java.time.LocalDateTime;

public class SolicitacaoResumoResponse {

    private final String protocolo;
    private final Categoria categoria;
    private final Prioridade prioridade;
    private final Status status;
    private final LocalDateTime dataAbertura;
    private final String indicadorSla;

    public SolicitacaoResumoResponse(String protocolo, Categoria categoria, Prioridade prioridade,
                                     Status status, LocalDateTime dataAbertura, String indicadorSla) {
        this.protocolo = protocolo;
        this.categoria = categoria;
        this.prioridade = prioridade;
        this.status = status;
        this.dataAbertura = dataAbertura;
        this.indicadorSla = indicadorSla;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public String getIndicadorSla() {
        return indicadorSla;
    }
}
