package br.com.observaacao.domain.enums;

public enum Categoria {

    ILUMINACAO("Iluminação"),
    BURACO("Buraco na via"),
    LIMPEZA("Limpeza urbana"),
    SAUDE("Saúde"),
    SEGURANCA("Segurança"),
    ZELADORIA("Zeladoria"),
    OUTROS("Outros");

    private final String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
