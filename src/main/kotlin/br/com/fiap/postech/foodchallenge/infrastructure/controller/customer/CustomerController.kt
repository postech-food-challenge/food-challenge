package br.com.fiap.postech.foodchallenge.infrastructure.controller.customer

import br.com.fiap.postech.foodchallenge.application.usecases.customer.IdentifyCustomerInteract
import br.com.fiap.postech.foodchallenge.application.usecases.customer.RegisterCustomerInteract
import br.com.fiap.postech.foodchallenge.domain.entities.Customer
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/customers")
class CustomerController(
    private val registerUseCase: RegisterCustomerInteract,
    private val identifyUseCase: IdentifyCustomerInteract
) {

    @PostMapping
    @ResponseStatus(CREATED)
    fun register(@RequestBody @Valid createCustomerRequest: CreateCustomerRequest) =
        Customer.fromRequest(createCustomerRequest)
            .let { customer -> registerUseCase.registerCustomer(customer) }
            .let { domainResponse -> CreateCustomerResponse.fromDomain(domainResponse) }

    @GetMapping("/identify/{cpf}")
    fun identifyCustomer(@PathVariable cpf: String) =
        identifyUseCase.identify(cpf)
            .let { CreateCustomerResponse.fromDomain(it) }
}