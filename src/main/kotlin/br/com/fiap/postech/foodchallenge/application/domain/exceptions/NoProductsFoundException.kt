package br.com.fiap.postech.foodchallenge.application.domain.exceptions

class NoProductsFoundException (category: String):
    RuntimeException("No products found for category $category.") {
}