package br.com.fiap.postech.foodchallenge.configuration

import br.com.fiap.postech.foodchallenge.application.gateways.PaymentGateway
import br.com.fiap.postech.foodchallenge.application.usecases.payment.FindPaymentByOrderIdInteract
import br.com.fiap.postech.foodchallenge.infrastructure.gateways.PaymentRepositoryGateway
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.PaymentRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PaymentConfig {

    @Bean
    fun createFindPaymentUseCases(gateway: PaymentGateway) =
        FindPaymentByOrderIdInteract(gateway)

    @Bean
    fun createPaymentGateway(repository: PaymentRepository): PaymentGateway =
        PaymentRepositoryGateway(repository)

}