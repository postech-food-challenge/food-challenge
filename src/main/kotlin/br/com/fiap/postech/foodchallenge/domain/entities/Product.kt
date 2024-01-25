package br.com.fiap.postech.foodchallenge.domain.entities

import br.com.fiap.postech.foodchallenge.infrastructure.controller.product.CreateProductRequest
import br.com.fiap.postech.foodchallenge.infrastructure.controller.product.UpdateProductRequest
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities.ProductEntity

data class Product(
    val id: Long? = null,
    val name: String,
    val description: String,
    val image: String,
    val price: Int,
    val category: Category
) {
    fun update(newProduct: Product) =
        this.copy(
            name = newProduct.name,
            description = newProduct.description,
            image = newProduct.image,
            price = newProduct.price,
            category = newProduct.category
        )

    companion object {
        fun fromEntity(entity: ProductEntity): Product {
            return Product(
                id = entity.id,
                name = entity.name,
                description = entity.description,
                image = entity.image,
                price = entity.price,
                category = Category.valueOf(entity.category)
            )
        }

        fun fromRequest(request: CreateProductRequest): Product {
            return Product(
                name = request.name,
                description = request.description,
                image = request.image,
                price = request.price,
                category = Category.validateCategory(request.category)
            )
        }

        fun fromRequest(request: UpdateProductRequest): Product {
            return Product(
                name = request.name,
                description = request.description,
                image = request.image,
                price = request.price,
                category = Category.validateCategory(request.category)
            )
        }
    }
}