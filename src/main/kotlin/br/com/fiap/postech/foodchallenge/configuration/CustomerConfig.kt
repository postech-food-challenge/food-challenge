package br.com.fiap.postech.foodchallenge.configuration

import br.com.fiap.postech.foodchallenge.application.gateways.CustomerGateway
import br.com.fiap.postech.foodchallenge.application.usecases.customer.IdentifyCustomerInteract
import br.com.fiap.postech.foodchallenge.application.usecases.customer.RegisterCustomerInteract
import br.com.fiap.postech.foodchallenge.infrastructure.gateways.CustomerRepositoryGateway
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.CustomerRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CustomerConfig {
    @Bean
    fun createRegisterUseCase(gateway: CustomerGateway) =
        RegisterCustomerInteract(gateway)

    @Bean
    fun createIdentifyUseCase(gateway: CustomerGateway) =
        IdentifyCustomerInteract(gateway)

    @Bean
    fun createCustomerGateway(repository: CustomerRepository): CustomerGateway =
        CustomerRepositoryGateway(repository)
}