package br.com.fiap.postech.foodchallenge.infrastructure.controller.customer

import br.com.fiap.postech.foodchallenge.domain.entities.Customer

data class CustomerResponse(
    val cpf: String,
    val name: String,
    val email: String
) {
    companion object {
        fun fromDomain(domainObject: Customer) =
            CustomerResponse(
                domainObject.cpf.value, domainObject.name, domainObject.email
            )
    }
}
