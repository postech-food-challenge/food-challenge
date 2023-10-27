package br.com.fiap.postech.foodchallenge.application.domain.exceptions

class CustomerAlreadyRegisteredException(cpf: String) :
    RuntimeException("Customer with CPF $cpf is already registered.") {
}