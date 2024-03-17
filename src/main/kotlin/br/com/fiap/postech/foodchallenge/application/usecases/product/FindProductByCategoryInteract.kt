package br.com.fiap.postech.foodchallenge.application.usecases.product

import br.com.fiap.postech.foodchallenge.application.gateways.ProductGateway
import br.com.fiap.postech.foodchallenge.domain.entities.Category
import br.com.fiap.postech.foodchallenge.domain.exceptions.NoObjectFoundException

class FindProductByCategoryInteract(private val gateway: ProductGateway) {
    fun findProductByCategory(category: String) =
        Category.validateCategory(category).let { validCategory ->
            gateway.findByCategory(validCategory)
                .filterNotNull()
                .takeIf { it.isNotEmpty() }
                ?: throw NoObjectFoundException("No products found for category $category.")
        }
}