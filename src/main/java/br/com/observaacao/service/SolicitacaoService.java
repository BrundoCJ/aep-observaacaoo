package br.com.observaacao.service;

import br.com.observaacao.domain.enums.Categoria;
import br.com.observaacao.domain.enums.Prioridade;
import br.com.observaacao.domain.enums.Status;
import br.com.observaacao.domain.model.Movimentacao;
import br.com.observaacao.domain.model.Solicitacao;
import br.com.observaacao.domain.model.SolicitacaoAnonima;
import br.com.observaacao.domain.model.SolicitacaoIdentificada;
import br.com.observaacao.dto.AtualizacaoStatusRequest;
import br.com.observaacao.dto.MovimentacaoResponse;
import br.com.observaacao.dto.SolicitacaoAnonimaRequest;
import br.com.observaacao.dto.SolicitacaoDetalhadaResponse;
import br.com.observaacao.dto.SolicitacaoIdentificadaRequest;
import br.com.observaacao.dto.SolicitacaoResumoResponse;
import br.com.observaacao.exception.SolicitacaoNaoEncontradaException;
import br.com.observaacao.exception.TransicaoStatusInvalidaException;
import br.com.observaacao.repository.SolicitacaoRepository;
import br.com.observaacao.util.GeradorProtocolo;
import br.com.observaacao.util.Validador;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class SolicitacaoService {

    private final SolicitacaoRepository repository;
    private final GeradorProtocolo geradorProtocolo;

    public SolicitacaoService(SolicitacaoRepository repository, GeradorProtocolo geradorProtocolo) {
        this.repository = repository;
        this.geradorProtocolo = geradorProtocolo;
    }

    public SolicitacaoDetalhadaResponse criarSolicitacaoIdentificada(SolicitacaoIdentificadaRequest request) {
        Validador.validarCamposComuns(request.getDescricao(), request.getLocalizacao());
        Validador.validarSolicitacaoIdentificada(request.getNomeCompleto(), request.getEmail());

        String protocolo = geradorProtocolo.gerar();
        SolicitacaoIdentificada solicitacao = new SolicitacaoIdentificada(
            protocolo,
            request.getCategoria(),
            request.getDescricao().trim(),
            request.getLocalizacao().trim(),
            request.getAnexo(),
            request.getPrioridade(),
            request.getNomeCompleto().trim(),
            request.getEmail().trim(),
            request.getTelefone()
        );

        repository.salvar(solicitacao);
        return construirRespostaDetalhada(solicitacao);
    }

    public SolicitacaoDetalhadaResponse criarSolicitacaoAnonima(SolicitacaoAnonimaRequest request) {
        Validador.validarCamposComuns(request.getDescricao(), request.getLocalizacao());
        Validador.validarSolicitacaoAnonima(request.getDescricao(), request.getMotivoAnonimato());

        String protocolo = geradorProtocolo.gerar();
        SolicitacaoAnonima solicitacao = new SolicitacaoAnonima(
            protocolo,
            request.getCategoria(),
            request.getDescricao().trim(),
            request.getLocalizacao().trim(),
            null,
            request.getPrioridade(),
            request.getMotivoAnonimato().trim()
        );

        repository.salvar(solicitacao);
        return construirRespostaDetalhada(solicitacao);
    }

    public SolicitacaoDetalhadaResponse buscarPorProtocolo(String protocolo) {
        Solicitacao solicitacao = buscarOuLancarErro(protocolo);
        return construirRespostaDetalhada(solicitacao);
    }

    public SolicitacaoDetalhadaResponse atualizarStatus(String protocolo, AtualizacaoStatusRequest request) {
        Validador.validarAtualizacaoStatus(request.getComentario(), request.getResponsavel());

        Solicitacao solicitacao = buscarOuLancarErro(protocolo);

        try {
            solicitacao.atualizarStatus(
                request.getNovoStatus(),
                request.getComentario().trim(),
                request.getResponsavel().trim()
            );
        } catch (IllegalStateException ex) {
            throw new TransicaoStatusInvalidaException(ex.getMessage());
        }

        repository.salvar(solicitacao);
        return construirRespostaDetalhada(solicitacao);
    }

    public List<SolicitacaoResumoResponse> listarComFiltros(Status status, Categoria categoria, Prioridade prioridade) {
        return repository.listarTodas().stream()
            .filter(s -> status     == null || s.getStatus()     == status)
            .filter(s -> categoria  == null || s.getCategoria()  == categoria)
            .filter(s -> prioridade == null || s.getPrioridade() == prioridade)
            .map(this::construirRespostaResumo)
            .toList();
    }

    private Solicitacao buscarOuLancarErro(String protocolo) {
        return repository.buscarPorProtocolo(protocolo)
            .orElseThrow(() -> new SolicitacaoNaoEncontradaException(protocolo));
    }

    private SolicitacaoResumoResponse construirRespostaResumo(Solicitacao solicitacao) {
        return new SolicitacaoResumoResponse(
            solicitacao.getProtocolo(),
            solicitacao.getCategoria(),
            solicitacao.getPrioridade(),
            solicitacao.getStatus(),
            solicitacao.getDataAbertura(),
            calcularIndicadorSla(solicitacao),
            solicitacao.isAnonima()
        );
    }

    private SolicitacaoDetalhadaResponse construirRespostaDetalhada(Solicitacao solicitacao) {
        List<MovimentacaoResponse> historico = solicitacao.getHistorico().stream()
            .map(this::construirMovimentacaoResponse)
            .toList();

        SolicitacaoDetalhadaResponse.Builder builder = SolicitacaoDetalhadaResponse.builder()
            .protocolo(solicitacao.getProtocolo())
            .categoria(solicitacao.getCategoria())
            .prioridade(solicitacao.getPrioridade())
            .status(solicitacao.getStatus())
            .dataAbertura(solicitacao.getDataAbertura())
            .prazoSla(solicitacao.calcularPrazoSla())
            .indicadorSla(calcularIndicadorSla(solicitacao))
            .descricao(solicitacao.getDescricao())
            .localizacao(solicitacao.getLocalizacao())
            .anexo(solicitacao.getAnexo())
            .anonima(solicitacao.isAnonima())
            .historico(historico);

        if (solicitacao instanceof SolicitacaoIdentificada identificada) {
            builder
                .nomeCompleto(identificada.getNomeCompleto())
                .email(identificada.getEmail())
                .telefone(identificada.getTelefone());
        }

        return builder.build();
    }

    private MovimentacaoResponse construirMovimentacaoResponse(Movimentacao movimentacao) {
        return new MovimentacaoResponse(
            movimentacao.getStatusAnterior(),
            movimentacao.getStatusNovo(),
            movimentacao.getComentario(),
            movimentacao.getResponsavel(),
            movimentacao.getDataHora()
        );
    }

    private String calcularIndicadorSla(Solicitacao solicitacao) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime prazo = solicitacao.calcularPrazoSla();
        LocalDateTime abertura = solicitacao.getDataAbertura();

        if (agora.isAfter(prazo)) {
            return "VENCIDO";
        }

        long totalMinutos = ChronoUnit.MINUTES.between(abertura, prazo);
        long minutosRestantes = ChronoUnit.MINUTES.between(agora, prazo);

        if (totalMinutos == 0) {
            return "VENCIDO";
        }

        double percentualRestante = (double) minutosRestantes / totalMinutos;

        if (percentualRestante > 0.5) {
            return "VERDE";
        } else if (percentualRestante > 0.25) {
            return "AMARELO";
        } else {
            return "AMARELO";
        }
    }
}
