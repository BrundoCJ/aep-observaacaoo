package br.com.observaacao.repository;

import br.com.observaacao.domain.enums.Categoria;
import br.com.observaacao.domain.enums.Prioridade;
import br.com.observaacao.domain.enums.Status;
import br.com.observaacao.domain.model.Solicitacao;

import java.util.List;
import java.util.Optional;

public interface SolicitacaoRepository {
    void salvar(Solicitacao solicitacao);
    Optional<Solicitacao> buscarPorProtocolo(String protocolo);
    List<Solicitacao> listarTodas();
    List<Solicitacao> listarPorStatus(Status status);
    List<Solicitacao> listarPorCategoria(Categoria categoria);
    List<Solicitacao> listarPorPrioridade(Prioridade prioridade);
}
