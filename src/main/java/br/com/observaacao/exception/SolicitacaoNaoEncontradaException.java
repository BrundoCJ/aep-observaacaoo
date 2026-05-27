package br.com.observaacao.exception;

public class SolicitacaoNaoEncontradaException extends RuntimeException {

    public SolicitacaoNaoEncontradaException(String protocolo) {
        super("Solicitação não encontrada para o protocolo: " + protocolo);
    }
}
