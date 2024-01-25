package br.com.fiap.postech.foodchallenge.application.usecases.product

import br.com.fiap.postech.foodchallenge.application.gateways.ProductGateway
import br.com.fiap.postech.foodchallenge.domain.exceptions.ProductNotFoundException

class DeleteProductInteract(private val gateway: ProductGateway) {
    fun deleteProduct(id: Long) =
        gateway.findById(id)
            ?.let { domainProduct ->
                gateway.delete(domainProduct)
            } ?: throw ProductNotFoundException(id)
}