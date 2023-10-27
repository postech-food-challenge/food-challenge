package br.com.fiap.postech.foodchallenge.adapters.controller

import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.Customer
import br.com.fiap.postech.foodchallenge.application.domain.services.CustomerService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/customers")
class CustomerController(private val service: CustomerService) {

    @PostMapping("/register")
    @ResponseStatus(CREATED)
    fun register(@RequestBody @Valid customer: Customer): Customer {
        return service.registerCustomer(customer)
    }

}