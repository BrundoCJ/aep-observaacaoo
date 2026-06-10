package br.com.observaacao.repository;

import br.com.observaacao.domain.enums.Categoria;
import br.com.observaacao.domain.enums.Prioridade;
import br.com.observaacao.domain.enums.Status;
import br.com.observaacao.domain.model.Solicitacao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

// @Repository — substituído por SolicitacaoRepositoryH2
public class SolicitacaoRepositoryEmMemoria implements SolicitacaoRepository {

    private final Map<String, Solicitacao> dados = new ConcurrentHashMap<>();

    @Override
    public void salvar(Solicitacao solicitacao) {
        dados.put(solicitacao.getProtocolo().toUpperCase(), solicitacao);
    }

    @Override
    public Optional<Solicitacao> buscarPorProtocolo(String protocolo) {
        return Optional.ofNullable(dados.get(protocolo.toUpperCase()));
    }

    @Override
    public List<Solicitacao> listarTodas() {
        return new ArrayList<>(dados.values());
    }

    @Override
    public List<Solicitacao> listarPorStatus(Status status) {
        return filtrar(solicitacao -> solicitacao.getStatus() == status);
    }

    @Override
    public List<Solicitacao> listarPorCategoria(Categoria categoria) {
        return filtrar(solicitacao -> solicitacao.getCategoria() == categoria);
    }

    @Override
    public List<Solicitacao> listarPorPrioridade(Prioridade prioridade) {
        return filtrar(solicitacao -> solicitacao.getPrioridade() == prioridade);
    }

    private List<Solicitacao> filtrar(Predicate<Solicitacao> criterio) {
        List<Solicitacao> resultado = new ArrayList<>();
        for (Solicitacao solicitacao : dados.values()) {
            if (criterio.test(solicitacao)) resultado.add(solicitacao);
        }
        return resultado;
    }
}
