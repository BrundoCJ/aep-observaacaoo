package br.com.observaacao.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class GeradorProtocolo {

    private final AtomicInteger contador = new AtomicInteger(1);

    public String gerar() {
        int ano = LocalDate.now().getYear();
        int numero = contador.getAndIncrement();
        return String.format("OBS-%d-%05d", ano, numero);
    }
}
