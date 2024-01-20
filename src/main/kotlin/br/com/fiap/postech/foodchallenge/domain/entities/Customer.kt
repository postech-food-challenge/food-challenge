package br.com.fiap.postech.foodchallenge.domain.entities

import br.com.fiap.postech.foodchallenge.infrastructure.controller.customer.CreateCustomerRequest
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities.CustomerEntity

data class Customer(val cpf: String, val name: String, val email: String) {
    companion object {
        fun fromEntity(entityObject: CustomerEntity) =
            Customer(cpf = entityObject.cpf, name = entityObject.name, email = entityObject.email)

        fun fromRequest(request: CreateCustomerRequest) =
            Customer(request.cpf, request.name, request.email)
    }
}
