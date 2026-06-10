package br.com.observaacao.domain.model;

import br.com.observaacao.domain.enums.Status;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacoes")
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_anterior")
    private Status statusAnterior;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_novo", nullable = false)
    private Status statusNovo;

    @Column(nullable = false, length = 1000)
    private String comentario;

    @Column(nullable = false)
    private String responsavel;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    // JPA: construtor protegido sem argumentos
    protected Movimentacao() {}

    public Movimentacao(Status statusAnterior, Status statusNovo,
                        String comentario, String responsavel) {
        this.statusAnterior = statusAnterior;
        this.statusNovo     = statusNovo;
        this.comentario     = comentario;
        this.responsavel    = responsavel;
        this.dataHora       = LocalDateTime.now();
    }

    public Status getStatusAnterior() { return statusAnterior; }
    public Status getStatusNovo()     { return statusNovo; }
    public String getComentario()     { return comentario; }
    public String getResponsavel()    { return responsavel; }
    public LocalDateTime getDataHora(){ return dataHora; }
}
