package br.com.observaacao.controller;

import br.com.observaacao.domain.enums.Categoria;
import br.com.observaacao.domain.enums.Prioridade;
import br.com.observaacao.domain.enums.Status;
import br.com.observaacao.dto.AtualizacaoStatusRequest;
import br.com.observaacao.dto.SolicitacaoDetalhadaResponse;
import br.com.observaacao.dto.SolicitacaoResumoResponse;
import br.com.observaacao.service.SolicitacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/gestor")
public class GestorController {

    private final SolicitacaoService solicitacaoService;

    public GestorController(SolicitacaoService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @GetMapping("/solicitacoes")
    public ResponseEntity<List<SolicitacaoResumoResponse>> listar(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Categoria categoria,
            @RequestParam(required = false) Prioridade prioridade) {
        List<SolicitacaoResumoResponse> response = solicitacaoService.listarComFiltros(status, categoria, prioridade);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/solicitacoes/{protocolo}")
    public ResponseEntity<SolicitacaoDetalhadaResponse> detalhar(@PathVariable String protocolo) {
        SolicitacaoDetalhadaResponse response = solicitacaoService.buscarPorProtocolo(protocolo);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/solicitacoes/{protocolo}/status")
    public ResponseEntity<SolicitacaoDetalhadaResponse> atualizarStatus(
            @PathVariable String protocolo,
            @RequestBody AtualizacaoStatusRequest request) {
        SolicitacaoDetalhadaResponse response = solicitacaoService.atualizarStatus(protocolo, request);
        return ResponseEntity.ok(response);
    }
}
