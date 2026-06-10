package br.com.observaacao.repository;

import br.com.observaacao.domain.enums.Categoria;
import br.com.observaacao.domain.enums.Prioridade;
import br.com.observaacao.domain.enums.Status;
import br.com.observaacao.domain.model.Solicitacao;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class SolicitacaoRepositoryH2 implements SolicitacaoRepository {

    private final SolicitacaoJpaRepository jpa;

    public SolicitacaoRepositoryH2(SolicitacaoJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public void salvar(Solicitacao solicitacao) {
        jpa.save(solicitacao);
    }

    @Override
    public Optional<Solicitacao> buscarPorProtocolo(String protocolo) {
        return jpa.findById(protocolo.toUpperCase());
    }

    @Override
    public List<Solicitacao> listarTodas() {
        return jpa.findAll();
    }

    @Override
    public List<Solicitacao> listarPorStatus(Status status) {
        return jpa.findByStatus(status);
    }

    @Override
    public List<Solicitacao> listarPorCategoria(Categoria categoria) {
        return jpa.findByCategoria(categoria);
    }

    @Override
    public List<Solicitacao> listarPorPrioridade(Prioridade prioridade) {
        return jpa.findByPrioridade(prioridade);
    }
}
