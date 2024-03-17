package br.com.fiap.postech.foodchallenge.configuration

import br.com.fiap.postech.foodchallenge.application.usecases.customer.IdentifyCustomerInteract
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CustomerConfig {
    @Bean
    fun createIdentifyUseCase() =
        IdentifyCustomerInteract()

}