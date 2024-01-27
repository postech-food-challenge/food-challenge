package br.com.fiap.postech.foodchallenge.configuration

import br.com.fiap.postech.foodchallenge.application.gateways.CustomerGateway
import br.com.fiap.postech.foodchallenge.application.gateways.OrderGateway
import br.com.fiap.postech.foodchallenge.application.gateways.ProductGateway
import br.com.fiap.postech.foodchallenge.application.usecases.order.ListOrdersInteract
import br.com.fiap.postech.foodchallenge.application.usecases.order.OrderCheckoutInteract
import br.com.fiap.postech.foodchallenge.application.usecases.order.UpdateOrderStatusInteract
import br.com.fiap.postech.foodchallenge.infrastructure.gateways.OrderRepositoryGateway
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.OrderRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrderConfig {
    @Bean
    fun createOrderCheckoutUseCase(
        orderGateway: OrderGateway,
        customerGateway: CustomerGateway,
        productGateway: ProductGateway
    ) =
        OrderCheckoutInteract(orderGateway, customerGateway, productGateway)

    @Bean
    fun createListOrdersUseCase(orderGateway: OrderGateway) = ListOrdersInteract(orderGateway)

    @Bean
    fun createUpdateOrderStatusUseCase(orderGateway: OrderGateway) = UpdateOrderStatusInteract(orderGateway)

    @Bean
    fun createOrderGateway(repository: OrderRepository, objectMapper: ObjectMapper): OrderGateway =
        OrderRepositoryGateway(repository, objectMapper)
}