package br.com.fiap.postech.foodchallenge.application.domain.exceptions

class ProductNotFoundException(productId: Long) : RuntimeException("Product with ID: $productId not found.") {
}
