package br.com.fiap.postech.foodchallenge.domain.exceptions

class CustomerNotFoundException(cpf: String) : RuntimeException("Customer with CPF: $cpf not found.") {
}