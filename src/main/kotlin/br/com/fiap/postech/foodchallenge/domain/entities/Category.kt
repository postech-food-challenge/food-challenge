package br.com.fiap.postech.foodchallenge.domain.entities

import br.com.fiap.postech.foodchallenge.domain.exceptions.InvalidParameterException

enum class Category {
    MAIN, SIDE, DRINK, DESSERT;

    companion object {
        fun validateCategory(category: String): Category {
            return enumValues<Category>().find { it.name == category }
                ?: throw InvalidParameterException("Invalid category: $category")
        }
    }
}