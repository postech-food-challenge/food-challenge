package br.com.fiap.postech.foodchallenge.infrastructure.controller.customer

import br.com.fiap.postech.foodchallenge.domain.entities.Customer

data class CreateCustomerResponse(
    val cpf: String,
    val name: String,
    val email: String
) {
    companion object {
        fun fromDomain(domainObject: Customer) =
            CreateCustomerResponse(
                domainObject.cpf, domainObject.name, domainObject.email
            )
    }
}
