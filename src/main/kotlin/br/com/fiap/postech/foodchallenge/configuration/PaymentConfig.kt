package br.com.fiap.postech.foodchallenge.configuration

import br.com.fiap.postech.foodchallenge.application.gateways.OrderGateway
import br.com.fiap.postech.foodchallenge.application.gateways.PaymentGateway
import br.com.fiap.postech.foodchallenge.application.gateways.ProductGateway
import br.com.fiap.postech.foodchallenge.application.usecases.payment.CreatePaymentInteract
import br.com.fiap.postech.foodchallenge.application.usecases.payment.FindPaymentByOrderIdInteract
import br.com.fiap.postech.foodchallenge.application.usecases.payment.UpdatePaymentInteract
import br.com.fiap.postech.foodchallenge.infrastructure.gateways.PaymentRepositoryGateway
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.PaymentRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PaymentConfig {

    @Bean
    fun createPaymentUseCases(paymentGateway: PaymentGateway, productGateway: ProductGateway) =
        CreatePaymentInteract(paymentGateway, productGateway)

    @Bean
    fun createUpdatePaymentUseCases(paymentGateway: PaymentGateway, orderGateway: OrderGateway) =
        UpdatePaymentInteract(paymentGateway, orderGateway)

    @Bean
    fun createFindPaymentUseCases(gateway: PaymentGateway) =
        FindPaymentByOrderIdInteract(gateway)

    @Bean
    fun createPaymentGateway(repository: PaymentRepository): PaymentGateway =
        PaymentRepositoryGateway(repository)

}