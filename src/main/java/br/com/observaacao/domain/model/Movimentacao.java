package br.com.observaacao.domain.model;

import br.com.observaacao.domain.enums.Status;

import java.time.LocalDateTime;

public class Movimentacao {

    private final Status statusAnterior;
    private final Status statusNovo;
    private final String comentario;
    private final String responsavel;
    private final LocalDateTime dataHora;

    public Movimentacao(Status statusAnterior, Status statusNovo, String comentario, String responsavel) {
        this.statusAnterior = statusAnterior;
        this.statusNovo = statusNovo;
        this.comentario = comentario;
        this.responsavel = responsavel;
        this.dataHora = LocalDateTime.now();
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
