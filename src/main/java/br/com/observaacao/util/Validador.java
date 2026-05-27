package br.com.observaacao.util;

import br.com.observaacao.exception.ValidacaoException;

public final class Validador {

    private static final int COMPRIMENTO_MINIMO_NOME = 3;
    private static final int COMPRIMENTO_MINIMO_DESCRICAO_COMUM = 10;
    private static final int COMPRIMENTO_MINIMO_DESCRICAO_ANONIMA = 20;

    private Validador() {}

    public static void validarNaoVazio(String valor, String nomeCampo) {
        if (valor == null || valor.isBlank()) {
            throw new ValidacaoException("O campo '" + nomeCampo + "' é obrigatório.");
        }
    }

    public static void validarTamanhoMinimo(String valor, String nomeCampo, int minimo) {
        validarNaoVazio(valor, nomeCampo);
        if (valor.trim().length() < minimo) {
            throw new ValidacaoException(
                "O campo '" + nomeCampo + "' deve ter ao menos " + minimo + " caracteres."
            );
        }
    }

    public static void validarEmail(String email) {
        validarNaoVazio(email, "email");
        if (!email.contains("@") || !email.contains(".")) {
            throw new ValidacaoException("E-mail inválido: " + email);
        }
    }

    public static void validarCamposComuns(String descricao, String localizacao) {
        validarTamanhoMinimo(descricao, "descrição", COMPRIMENTO_MINIMO_DESCRICAO_COMUM);
        validarNaoVazio(localizacao, "localização");
    }

    public static void validarSolicitacaoIdentificada(String nomeCompleto, String email) {
        validarTamanhoMinimo(nomeCompleto, "nome completo", COMPRIMENTO_MINIMO_NOME);
        validarEmail(email);
    }

    public static void validarSolicitacaoAnonima(String descricao, String motivoAnonimato) {
        validarTamanhoMinimo(descricao, "descrição", COMPRIMENTO_MINIMO_DESCRICAO_ANONIMA);
        validarNaoVazio(motivoAnonimato, "motivo do anonimato");
    }

    public static void validarAtualizacaoStatus(String comentario, String responsavel) {
        validarNaoVazio(comentario, "comentário");
        validarNaoVazio(responsavel, "responsável");
    }
}
