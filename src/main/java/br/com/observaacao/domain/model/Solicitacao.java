package br.com.observaacao.domain.model;

import br.com.observaacao.domain.enums.Categoria;
import br.com.observaacao.domain.enums.Prioridade;
import br.com.observaacao.domain.enums.Status;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "solicitacoes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING, length = 20)
public abstract class Solicitacao {

    @Id
    @Column(nullable = false, unique = true)
    private String protocolo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @Column(nullable = false, length = 1000)
    private String descricao;

    @Column(nullable = false)
    private String localizacao;

    private String anexo;

    @Column(name = "data_abertura", nullable = false)
    private LocalDateTime dataAbertura;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridade prioridade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "solicitacao_protocolo")
    @OrderBy("dataHora ASC")
    private List<Movimentacao> historico = new ArrayList<>();

    // JPA: construtor protegido sem argumentos
    protected Solicitacao() {}

    protected Solicitacao(String protocolo, Categoria categoria, String descricao,
                          String localizacao, String anexo, Prioridade prioridade) {
        this.protocolo    = protocolo;
        this.categoria    = categoria;
        this.descricao    = descricao;
        this.localizacao  = localizacao;
        this.anexo        = anexo;
        this.prioridade   = prioridade;
        this.status       = Status.ABERTO;
        this.dataAbertura = LocalDateTime.now();
        this.historico    = new ArrayList<>();
    }

    public abstract boolean isAnonima();

    public void atualizarStatus(Status novoStatus, String comentario, String responsavel) {
        if (!this.status.podeTransicionarPara(novoStatus)) {
            throw new IllegalStateException(
                "Transição inválida: " + this.status.name() + " → " + novoStatus.name()
            );
        }
        Movimentacao movimentacao = new Movimentacao(this.status, novoStatus, comentario, responsavel);
        this.historico.add(movimentacao);
        this.status = novoStatus;
    }

    public LocalDateTime calcularPrazoSla() {
        return dataAbertura.plusDays(prioridade.getSlaDias());
    }

    public boolean estaDentroDoPrazo() {
        return LocalDateTime.now().isBefore(calcularPrazoSla());
    }

    public String getProtocolo()          { return protocolo; }
    public Categoria getCategoria()       { return categoria; }
    public String getDescricao()          { return descricao; }
    public String getLocalizacao()        { return localizacao; }
    public String getAnexo()              { return anexo; }
    public Prioridade getPrioridade()     { return prioridade; }
    public Status getStatus()             { return status; }
    public LocalDateTime getDataAbertura(){ return dataAbertura; }
    public List<Movimentacao> getHistorico() {
        return Collections.unmodifiableList(historico);
    }
}
