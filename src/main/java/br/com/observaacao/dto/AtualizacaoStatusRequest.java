package br.com.observaacao.dto;

import br.com.observaacao.domain.enums.Status;

public class AtualizacaoStatusRequest {

    private Status novoStatus;
    private String comentario;
    private String responsavel;

    public Status getNovoStatus() {
        return novoStatus;
    }

    public void setNovoStatus(Status novoStatus) {
        this.novoStatus = novoStatus;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
}
