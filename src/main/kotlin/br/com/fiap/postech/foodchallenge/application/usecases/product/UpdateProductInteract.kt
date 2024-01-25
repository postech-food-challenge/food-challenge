package br.com.fiap.postech.foodchallenge.application.usecases.product

import br.com.fiap.postech.foodchallenge.application.gateways.ProductGateway
import br.com.fiap.postech.foodchallenge.domain.entities.Product
import br.com.fiap.postech.foodchallenge.domain.exceptions.ProductNotFoundException

class UpdateProductInteract(private val gateway: ProductGateway) {
    fun updateProduct(id: Long, newProduct: Product) =
        gateway.findById(id)
            ?.let { domainProduct ->
                domainProduct.update(newProduct).let { updatedProduct ->
                    gateway.save(updatedProduct)
                }
            } ?: throw ProductNotFoundException(id)
}