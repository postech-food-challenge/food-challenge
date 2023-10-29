package br.com.fiap.postech.foodchallenge.application.domain.exceptions

import java.security.InvalidParameterException

class InvalidCategoryException(category: String):
    InvalidParameterException("Invalid category $category.") {
}