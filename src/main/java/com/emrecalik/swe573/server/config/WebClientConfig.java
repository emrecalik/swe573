package com.emrecalik.swe573.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {
    private static final String API_BASE_URL = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils";

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(API_BASE_URL)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.newConnection().compress(true)))
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(16 * 1024 * 1024))
                        .build())
                .build();
    }
}
