package br.com.fiap.postech.foodchallenge.domain.entities

import br.com.fiap.postech.foodchallenge.infrastructure.controller.customer.CreateCustomerRequest
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities.CustomerEntity

data class Customer(val cpf: CPF, val name: String, val email: String) {
    companion object {
        fun fromEntity(entityObject: CustomerEntity) =
            Customer(cpf = CPF(entityObject.cpf), name = entityObject.name, email = entityObject.email)

        fun fromRequest(request: CreateCustomerRequest) =
            Customer(CPF(request.cpf), request.name, request.email)
    }
}
