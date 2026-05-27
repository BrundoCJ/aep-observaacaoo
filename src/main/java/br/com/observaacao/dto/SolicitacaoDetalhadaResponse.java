package br.com.observaacao.dto;

import br.com.observaacao.domain.enums.Categoria;
import br.com.observaacao.domain.enums.Prioridade;
import br.com.observaacao.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SolicitacaoDetalhadaResponse {

    private final String protocolo;
    private final Categoria categoria;
    private final Prioridade prioridade;
    private final Status status;
    private final LocalDateTime dataAbertura;
    private final LocalDateTime prazoSla;
    private final String indicadorSla;
    private final String descricao;
    private final String localizacao;
    private final String anexo;
    private final boolean anonima;
    private final String nomeCompleto;
    private final String email;
    private final String telefone;
    private final List<MovimentacaoResponse> historico;

    private SolicitacaoDetalhadaResponse(Builder builder) {
        this.protocolo = builder.protocolo;
        this.categoria = builder.categoria;
        this.prioridade = builder.prioridade;
        this.status = builder.status;
        this.dataAbertura = builder.dataAbertura;
        this.prazoSla = builder.prazoSla;
        this.indicadorSla = builder.indicadorSla;
        this.descricao = builder.descricao;
        this.localizacao = builder.localizacao;
        this.anexo = builder.anexo;
        this.anonima = builder.anonima;
        this.nomeCompleto = builder.nomeCompleto;
        this.email = builder.email;
        this.telefone = builder.telefone;
        this.historico = builder.historico;
    }

    public String getProtocolo() { return protocolo; }
    public Categoria getCategoria() { return categoria; }
    public Prioridade getPrioridade() { return prioridade; }
    public Status getStatus() { return status; }
    public LocalDateTime getDataAbertura() { return dataAbertura; }
    public LocalDateTime getPrazoSla() { return prazoSla; }
    public String getIndicadorSla() { return indicadorSla; }
    public String getDescricao() { return descricao; }
    public String getLocalizacao() { return localizacao; }
    public String getAnexo() { return anexo; }
    public boolean isAnonima() { return anonima; }
    public String getNomeCompleto() { return nomeCompleto; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public List<MovimentacaoResponse> getHistorico() { return historico; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String protocolo;
        private Categoria categoria;
        private Prioridade prioridade;
        private Status status;
        private LocalDateTime dataAbertura;
        private LocalDateTime prazoSla;
        private String indicadorSla;
        private String descricao;
        private String localizacao;
        private String anexo;
        private boolean anonima;
        private String nomeCompleto;
        private String email;
        private String telefone;
        private List<MovimentacaoResponse> historico;

        public Builder protocolo(String protocolo) { this.protocolo = protocolo; return this; }
        public Builder categoria(Categoria categoria) { this.categoria = categoria; return this; }
        public Builder prioridade(Prioridade prioridade) { this.prioridade = prioridade; return this; }
        public Builder status(Status status) { this.status = status; return this; }
        public Builder dataAbertura(LocalDateTime dataAbertura) { this.dataAbertura = dataAbertura; return this; }
        public Builder prazoSla(LocalDateTime prazoSla) { this.prazoSla = prazoSla; return this; }
        public Builder indicadorSla(String indicadorSla) { this.indicadorSla = indicadorSla; return this; }
        public Builder descricao(String descricao) { this.descricao = descricao; return this; }
        public Builder localizacao(String localizacao) { this.localizacao = localizacao; return this; }
        public Builder anexo(String anexo) { this.anexo = anexo; return this; }
        public Builder anonima(boolean anonima) { this.anonima = anonima; return this; }
        public Builder nomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder telefone(String telefone) { this.telefone = telefone; return this; }
        public Builder historico(List<MovimentacaoResponse> historico) { this.historico = historico; return this; }

        public SolicitacaoDetalhadaResponse build() {
            return new SolicitacaoDetalhadaResponse(this);
        }
    }
}
