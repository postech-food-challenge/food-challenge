package br.com.fiap.postech.foodchallenge.application.domain.model.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Entity
data class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @field:NotBlank
    val name: String,
    @field:NotBlank
    val description: String,
    @field:NotBlank
    val image: String,
    @field:NotNull
    val price: Int,
    @field:NotNull
    val category: ProductCategory
)

fun Product.update(newProduct: Product) : Product =
    Product(
        id = id,
        name = newProduct.name,
        description = newProduct.description,
        image = newProduct.image,
        price = newProduct.price,
        category = newProduct.category
    )

enum class ProductCategory {
    MAIN, SIDE, DRINK, DESSERT;
}
