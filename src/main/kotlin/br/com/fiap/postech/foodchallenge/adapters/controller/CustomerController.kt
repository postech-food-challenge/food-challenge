package br.com.fiap.postech.foodchallenge.adapters.controller

import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.Customer
import br.com.fiap.postech.foodchallenge.application.domain.services.CustomerService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/customers")
class CustomerController(private val service: CustomerService) {

    @PostMapping("/register")
    @ResponseStatus(CREATED)
    fun register(@RequestBody @Valid customer: Customer): Customer {
        return service.registerCustomer(customer)
    }

    @GetMapping("/identify/{cpf}")
    fun identifyCustomer(@PathVariable cpf: String): ResponseEntity<Any> {
        if (cpf.length != 11 || !cpf.all { it.isDigit() }) {
            return ResponseEntity.badRequest().body("Invalid CPF format")
        }

        val formattedCpf = cpf.run {
            "${take(3)}.${drop(3).take(3)}.${drop(6).take(3)}-${drop(9)}"
        }

        val customer = service.getCustomerByCpf(formattedCpf)
        return customer?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
    }

}