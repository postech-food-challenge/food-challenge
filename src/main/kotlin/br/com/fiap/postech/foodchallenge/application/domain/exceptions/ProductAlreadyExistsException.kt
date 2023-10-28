package br.com.fiap.postech.foodchallenge.application.domain.exceptions

class ProductAlreadyExistsException(name: String) :
    RuntimeException("Product with name $name already exists.") {
}