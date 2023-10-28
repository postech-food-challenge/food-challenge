package br.com.fiap.postech.foodchallenge.application.domain.model.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import org.hibernate.validator.constraints.URL

@Entity
data class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @field:NotBlank(message = "Name cannot be blank")
    @Column(unique = true)
    val name: String,
    @field:NotBlank(message = "Description cannot be blank")
    val description: String,
    @field:NotBlank(message = "Image cannot be blank")
    @field:URL(message = "Image should be an URL")
    val image: String,
    @field:NotNull
    @field:Positive(message = "Price should be greater then zero")
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
