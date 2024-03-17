package br.com.fiap.postech.foodchallenge.infrastructure.controller.customer

import br.com.fiap.postech.foodchallenge.application.usecases.customer.IdentifyCustomerInteract
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/customers")
class CustomerController(
    private val identifyUseCase: IdentifyCustomerInteract
) {
    @PostMapping("/identify/")
    fun identifyCustomer(@RequestBody dto: IdentifyCustomerRequest) =
        identifyUseCase.identify(dto)
}
