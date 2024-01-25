package br.com.fiap.postech.foodchallenge.application.usecases.customer

import br.com.fiap.postech.foodchallenge.application.domain.exceptions.CustomerAlreadyRegisteredException
import br.com.fiap.postech.foodchallenge.application.gateways.CustomerGateway
import br.com.fiap.postech.foodchallenge.domain.entities.Customer

class RegisterCustomerInteract(private val gateway: CustomerGateway) {
    fun registerCustomer(customer: Customer) =
        gateway.findByCpf(customer.cpf.value)
            ?.let { throw CustomerAlreadyRegisteredException(customer.cpf.value) }
            ?: gateway.create(customer)
}