package br.com.observaacao.repository;

import br.com.observaacao.domain.enums.Categoria;
import br.com.observaacao.domain.enums.Prioridade;
import br.com.observaacao.domain.enums.Status;
import br.com.observaacao.domain.model.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitacaoJpaRepository extends JpaRepository<Solicitacao, String> {
    List<Solicitacao> findByStatus(Status status);
    List<Solicitacao> findByCategoria(Categoria categoria);
    List<Solicitacao> findByPrioridade(Prioridade prioridade);
}
