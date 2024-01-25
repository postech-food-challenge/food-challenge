package br.com.fiap.postech.foodchallenge.application.usecases.customer

import br.com.fiap.postech.foodchallenge.application.gateways.CustomerGateway
import br.com.fiap.postech.foodchallenge.domain.entities.CPF
import br.com.fiap.postech.foodchallenge.domain.entities.Customer
import br.com.fiap.postech.foodchallenge.domain.exceptions.CustomerNotFoundException

class IdentifyCustomerInteract(private val gateway: CustomerGateway) {
    fun identify(cpfString: String): Customer {
        val cpf = CPF(cpfString)
        return gateway.findByCpf(cpf.value) ?: throw CustomerNotFoundException(cpf.value)
    }
}
