package br.com.fiap.postech.foodchallenge.application.domain.services

import br.com.fiap.postech.foodchallenge.adapters.persistence.ProductRepository
import br.com.fiap.postech.foodchallenge.application.domain.model.entities.Product
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    fun createProduct(product: Product): Product = productRepository.save(product)

}
