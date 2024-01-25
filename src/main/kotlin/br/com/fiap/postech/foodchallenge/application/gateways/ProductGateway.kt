package br.com.fiap.postech.foodchallenge.application.gateways

import br.com.fiap.postech.foodchallenge.domain.entities.Category
import br.com.fiap.postech.foodchallenge.domain.entities.Product

interface ProductGateway {
    fun findByName(name: String): Product?

    fun save(product: Product): Product

    fun findById(id: Long): Product?

    fun findByCategory(category: Category): List<Product?>

    fun delete(product: Product)

}