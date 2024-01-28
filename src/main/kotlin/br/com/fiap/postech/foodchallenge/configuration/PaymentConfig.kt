package br.com.fiap.postech.foodchallenge.configuration

import br.com.fiap.postech.foodchallenge.application.gateways.OrderGateway
import br.com.fiap.postech.foodchallenge.application.gateways.ProductGateway
import br.com.fiap.postech.foodchallenge.application.usecases.payment.CreatePaymentInteract
import br.com.fiap.postech.foodchallenge.application.usecases.payment.FindPaymentByOrderIdInteract
import br.com.fiap.postech.foodchallenge.application.usecases.payment.UpdatePaymentInteract
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PaymentConfig {

    @Bean
    fun createPaymentUseCases(productGateway: ProductGateway) =
        CreatePaymentInteract(productGateway)

    @Bean
    fun createUpdatePaymentUseCases(orderGateway: OrderGateway) =
        UpdatePaymentInteract(orderGateway)

    @Bean
    fun createFindPaymentUseCases(orderGateway: OrderGateway) =
        FindPaymentByOrderIdInteract(orderGateway)

}