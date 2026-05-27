package br.com.observaacao.controller;

import br.com.observaacao.dto.SolicitacaoAnonimaRequest;
import br.com.observaacao.dto.SolicitacaoDetalhadaResponse;
import br.com.observaacao.dto.SolicitacaoIdentificadaRequest;
import br.com.observaacao.service.SolicitacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cidadao")
public class CidadaoController {

    private final SolicitacaoService solicitacaoService;

    public CidadaoController(SolicitacaoService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @PostMapping("/solicitacoes/identificada")
    public ResponseEntity<SolicitacaoDetalhadaResponse> registrarIdentificada(
            @RequestBody SolicitacaoIdentificadaRequest request) {
        SolicitacaoDetalhadaResponse response = solicitacaoService.criarSolicitacaoIdentificada(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/solicitacoes/anonima")
    public ResponseEntity<SolicitacaoDetalhadaResponse> registrarAnonima(
            @RequestBody SolicitacaoAnonimaRequest request) {
        SolicitacaoDetalhadaResponse response = solicitacaoService.criarSolicitacaoAnonima(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/solicitacoes/{protocolo}")
    public ResponseEntity<SolicitacaoDetalhadaResponse> consultarPorProtocolo(
            @PathVariable String protocolo) {
        SolicitacaoDetalhadaResponse response = solicitacaoService.buscarPorProtocolo(protocolo);
        return ResponseEntity.ok(response);
    }
}
