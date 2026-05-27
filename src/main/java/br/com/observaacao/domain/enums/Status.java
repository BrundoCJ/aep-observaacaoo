package br.com.observaacao.domain.enums;

public enum Status {

    ABERTO("Aberto") {
        @Override
        public boolean podeTransicionarPara(Status destino) {
            return destino == TRIAGEM;
        }
    },
    TRIAGEM("Em Triagem") {
        @Override
        public boolean podeTransicionarPara(Status destino) {
            return destino == EM_EXECUCAO;
        }
    },
    EM_EXECUCAO("Em Execução") {
        @Override
        public boolean podeTransicionarPara(Status destino) {
            return destino == RESOLVIDO;
        }
    },
    RESOLVIDO("Resolvido") {
        @Override
        public boolean podeTransicionarPara(Status destino) {
            return destino == ENCERRADO;
        }
    },
    ENCERRADO("Encerrado") {
        @Override
        public boolean podeTransicionarPara(Status destino) {
            return false;
        }
    };

    private final String descricao;

    Status(String descricao) {
        this.descricao = descricao;
    }

    public abstract boolean podeTransicionarPara(Status destino);

    public String getDescricao() {
        return descricao;
    }
}
