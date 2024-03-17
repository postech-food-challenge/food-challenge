package br.com.fiap.postech.foodchallenge.application.gateways

import br.com.fiap.postech.foodchallenge.domain.entities.Customer

interface CustomerGateway {
    fun findByCpf(cpf: String): Customer?
}