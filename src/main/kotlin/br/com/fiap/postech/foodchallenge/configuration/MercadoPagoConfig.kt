package br.com.fiap.postech.foodchallenge.configuration

import br.com.fiap.postech.foodchallenge.application.gateways.MercadoPagoGateway
import br.com.fiap.postech.foodchallenge.infrastructure.gateways.MercadoPagoClientGateway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MercadoPagoConfig {

    @Bean
    fun mercadoPagoGateway(): MercadoPagoGateway =
        MercadoPagoClientGateway()
}