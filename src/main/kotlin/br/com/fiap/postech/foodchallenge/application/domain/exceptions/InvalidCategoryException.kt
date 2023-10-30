package br.com.fiap.postech.foodchallenge.application.domain.exceptions

class InvalidCategoryException(category: String):
    RuntimeException(category) {
}