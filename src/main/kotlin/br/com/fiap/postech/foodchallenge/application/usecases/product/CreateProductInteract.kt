package br.com.fiap.postech.foodchallenge.application.usecases.product

import br.com.fiap.postech.foodchallenge.application.gateways.ProductGateway
import br.com.fiap.postech.foodchallenge.domain.entities.Product
import br.com.fiap.postech.foodchallenge.domain.exceptions.ProductAlreadyExistsException

class CreateProductInteract(private val gateway: ProductGateway) {
    fun createProduct(product: Product) =
        gateway.findByName(product.name)?.let { throw ProductAlreadyExistsException(product.name) } ?: gateway.save(
            product
        )

}