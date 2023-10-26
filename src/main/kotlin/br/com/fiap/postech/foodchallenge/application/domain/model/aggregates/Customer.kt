package br.com.fiap.postech.foodchallenge.application.domain.model.aggregates

data class Customer(
    val id: Long? = null,
    val cpf: CPF,
    val name: String,
    val email: String
)

data class CPF(val value: String)
