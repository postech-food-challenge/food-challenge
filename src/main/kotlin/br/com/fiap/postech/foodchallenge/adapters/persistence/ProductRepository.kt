package br.com.fiap.postech.foodchallenge.adapters.persistence

import br.com.fiap.postech.foodchallenge.application.domain.model.entities.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductRepository: JpaRepository<Product, Long> {
    fun findByName(name: String): Optional<Product>

}