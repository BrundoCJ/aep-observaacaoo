package br.com.observaacao.domain.enums;

public enum Prioridade {

    BAIXA(7, "Baixa"),
    MEDIA(3, "Média"),
    ALTA(1, "Alta");

    private final int slaDias;
    private final String descricao;

    Prioridade(int slaDias, String descricao) {
        this.slaDias = slaDias;
        this.descricao = descricao;
    }

    public int getSlaDias() {
        return slaDias;
    }

    public String getDescricao() {
        return descricao;
    }
}
