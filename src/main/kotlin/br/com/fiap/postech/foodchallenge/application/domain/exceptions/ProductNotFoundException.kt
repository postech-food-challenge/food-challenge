package br.com.fiap.postech.foodchallenge.application.domain.exceptions

class ProductNotFoundException(id: Long) :
    RuntimeException("Product with $id does not exist.") {
}