package br.com.fiap.postech.foodchallenge.domain.exceptions

import br.com.fiap.postech.foodchallenge.domain.entities.Category

class ProductsNotFoundInCategoryException(category: Category) :
    RuntimeException("There are no products for category $category.") {
}