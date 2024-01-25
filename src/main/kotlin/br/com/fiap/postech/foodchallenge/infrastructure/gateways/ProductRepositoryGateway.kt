package br.com.fiap.postech.foodchallenge.infrastructure.gateways

import br.com.fiap.postech.foodchallenge.application.gateways.ProductGateway
import br.com.fiap.postech.foodchallenge.domain.entities.Category
import br.com.fiap.postech.foodchallenge.domain.entities.Product
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.ProductRepository
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities.ProductEntity

class ProductRepositoryGateway(private val repository: ProductRepository) : ProductGateway {
    override fun findByName(name: String) = repository.findByName(name)?.let { Product.fromEntity(it) }

    override fun save(product: Product) =
        ProductEntity.fromDomain(product)
            .let { entity -> repository.save(entity) }
            .let { savedEntity -> Product.fromEntity(savedEntity) }

    override fun delete(product: Product) =
        ProductEntity.fromDomain(product)
            .let { entity -> repository.delete(entity) }

    override fun findById(id: Long) = repository.findById(id).orElse(null)?.let { Product.fromEntity(it) }

    override fun findByCategory(category: Category): List<Product?> =
        repository.findByCategory(category.name).mapNotNull { entity ->
            entity?.let { Product.fromEntity(it) }
        }
}