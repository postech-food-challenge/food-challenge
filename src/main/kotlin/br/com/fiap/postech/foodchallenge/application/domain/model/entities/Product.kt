package br.com.fiap.postech.foodchallenge.application.domain.model.entities

data class Product(
    val id: ProductId,
    val name: String,
    val description: String,
    val image: String,
    val price: Double,
    val category: ProductCategory
)

data class ProductId(val value: String)

enum class ProductCategory {
    MAIN, SIDE, DRINK, DESSERT
}
