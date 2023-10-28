package br.com.fiap.postech.foodchallenge.application.domain.exceptions

class ProductNotFoundException(id: Long) :
    RuntimeException("Product with id $id does not exist.") {
}