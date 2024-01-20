package br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities

import br.com.fiap.postech.foodchallenge.domain.entities.Product
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class ProductEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,

    @Column(unique = true) val name: String,

    val description: String, val image: String, val price: Int, val category: String
) {
    companion object {
        fun fromDomain(domain: Product): ProductEntity {
            return ProductEntity(
                name = domain.name,
                description = domain.description,
                image = domain.image,
                price = domain.price,
                category = domain.category.name
            )
        }
    }
}