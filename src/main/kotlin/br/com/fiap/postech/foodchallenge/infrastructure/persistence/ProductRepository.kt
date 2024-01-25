package br.com.fiap.postech.foodchallenge.infrastructure.persistence

import br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<ProductEntity, Long> {
    fun findByName(name: String): ProductEntity?

    fun findByCategory(category: String): List<ProductEntity?>

}