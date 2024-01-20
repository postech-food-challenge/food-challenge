package br.com.fiap.postech.foodchallenge.infrastructure.persistence

import br.com.fiap.postech.foodchallenge.application.domain.model.entities.Product
import br.com.fiap.postech.foodchallenge.application.domain.model.entities.ProductCategoryEnum
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun findByName(name: String): Product?

    fun findByCategory(category: ProductCategoryEnum): List<Product?>
}