package br.com.observaacao.dto;

import br.com.observaacao.domain.enums.Status;

import java.time.LocalDateTime;

public class MovimentacaoResponse {

    private final Status statusAnterior;
    private final Status statusNovo;
    private final String comentario;
    private final String responsavel;
    private final LocalDateTime dataHora;

    public MovimentacaoResponse(Status statusAnterior, Status statusNovo,
                                String comentario, String responsavel, LocalDateTime dataHora) {
        this.statusAnterior = statusAnterior;
        this.statusNovo = statusNovo;
        this.comentario = comentario;
        this.responsavel = responsavel;
        this.dataHora = dataHora;
    }

    public Status getStatusAnterior() {
        return statusAnterior;
    }

    public Status getStatusNovo() {
        return statusNovo;
    }

    public String getComentario() {
        return comentario;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}
