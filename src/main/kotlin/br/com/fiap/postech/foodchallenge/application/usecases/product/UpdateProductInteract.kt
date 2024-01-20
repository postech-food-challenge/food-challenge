package br.com.fiap.postech.foodchallenge.application.usecases.product

import br.com.fiap.postech.foodchallenge.application.gateways.ProductGateway
import br.com.fiap.postech.foodchallenge.domain.entities.Product
import br.com.fiap.postech.foodchallenge.domain.exceptions.ProductNotFoundException

class UpdateProductInteract(private val gateway: ProductGateway) {
    fun updateProduct(id: Long, updatedProduct: Product) =
        gateway.findById(id)
            ?.let { domainProduct ->
                domainProduct.update(updatedProduct)
                gateway.save(domainProduct)
            } ?: throw ProductNotFoundException(id)
}