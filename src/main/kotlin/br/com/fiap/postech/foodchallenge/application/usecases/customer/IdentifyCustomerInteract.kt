package br.com.fiap.postech.foodchallenge.application.usecases.customer

import br.com.fiap.postech.foodchallenge.application.domain.exceptions.InvalidCpfException
import br.com.fiap.postech.foodchallenge.application.gateways.CustomerGateway
import br.com.fiap.postech.foodchallenge.domain.entities.Customer
import br.com.fiap.postech.foodchallenge.domain.exceptions.CustomerNotFoundException

class IdentifyCustomerInteract(private val gateway: CustomerGateway) {

    fun identify(cpf: String): Customer {
        validateCpf(cpf)
        return formatCpf(cpf).let { formattedCpf ->
            gateway.findByCpf(formattedCpf) ?: throw CustomerNotFoundException(formattedCpf)
        }
    }

    private fun validateCpf(cpf: String) {
        if (cpf.length != 11 || !cpf.all { it.isDigit() }) {
            throw InvalidCpfException("Invalid CPF format")
        }
    }

    private fun formatCpf(cpf: String): String = cpf.run {
        "${take(3)}.${drop(3).take(3)}.${drop(6).take(3)}-${drop(9)}"
    }
}
