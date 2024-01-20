package br.com.fiap.postech.foodchallenge.application.usecases

import br.com.fiap.postech.foodchallenge.application.domain.exceptions.CustomerAlreadyRegisteredException
import br.com.fiap.postech.foodchallenge.application.gateways.CustomerGateway
import br.com.fiap.postech.foodchallenge.domain.entities.Customer

class RegisterCustomerInteract(private val gateway: CustomerGateway) {
    fun registerCustomer(customer: Customer) =
        gateway.findByCpf(customer.cpf)
            ?.let { throw CustomerAlreadyRegisteredException(customer.cpf) }
            ?: gateway.create(customer)
}