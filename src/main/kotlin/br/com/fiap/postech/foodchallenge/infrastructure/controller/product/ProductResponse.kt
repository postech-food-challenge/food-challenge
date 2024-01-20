package br.com.fiap.postech.foodchallenge.infrastructure.controller.product

import br.com.fiap.postech.foodchallenge.domain.entities.Product

data class ProductResponse(
    val id: Long?,
    val name: String,
    val description: String,
    val image: String,
    val price: Int,
    val category: String
) {
    companion object {
        fun fromDomain(domain: Product): ProductResponse {
            return ProductResponse(
                id = domain.id,
                name = domain.name,
                description = domain.description,
                image = domain.image,
                price = domain.price,
                category = domain.category.name
            )
        }
    }
}