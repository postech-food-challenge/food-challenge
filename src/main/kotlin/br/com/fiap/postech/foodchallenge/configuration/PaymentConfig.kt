package br.com.fiap.postech.foodchallenge.configuration

import br.com.fiap.postech.foodchallenge.application.gateways.MercadoPagoGateway
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
    fun createPaymentUseCases(productGateway: ProductGateway, mercadoPagoGateway: MercadoPagoGateway) =
        CreatePaymentInteract(productGateway, mercadoPagoGateway)

    @Bean
    fun createUpdatePaymentUseCases(orderGateway: OrderGateway) =
        UpdatePaymentInteract(orderGateway)

    @Bean
    fun createFindPaymentUseCases(orderGateway: OrderGateway) =
        FindPaymentByOrderIdInteract(orderGateway)

}