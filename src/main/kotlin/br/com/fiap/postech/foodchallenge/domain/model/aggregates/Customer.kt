package br.com.fiap.postech.foodchallenge.domain.model.aggregates

data class Customer(
    val id: CustomerId,
    val cpf: CPF,
    val name: String,
    val email: String
)

data class CustomerId(val value: String)

data class CPF(val value: String)
