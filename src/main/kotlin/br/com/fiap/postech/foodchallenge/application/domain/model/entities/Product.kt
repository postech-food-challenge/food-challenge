package br.com.fiap.postech.foodchallenge.application.domain.model.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id

@Entity
data class Product(
    @Id @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,
    val name: String,
    val description: String,
    val image: String,
    val price: Double,
    val category: ProductCategory
)

enum class ProductCategory {
    MAIN, SIDE, DRINK, DESSERT
}
