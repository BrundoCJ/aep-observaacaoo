package br.com.observaacao.exception;

public class TransicaoStatusInvalidaException extends RuntimeException {

    public TransicaoStatusInvalidaException(String mensagem) {
        super(mensagem);
    }
}
