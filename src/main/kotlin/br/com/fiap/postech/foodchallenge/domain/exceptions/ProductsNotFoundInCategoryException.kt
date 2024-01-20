package br.com.fiap.postech.foodchallenge.application.domain.exceptions

import br.com.fiap.postech.foodchallenge.application.domain.model.entities.ProductCategoryEnum

class ProductsNotFoundInCategoryException(category: ProductCategoryEnum):
    RuntimeException("There are no products for category $category.") {
}