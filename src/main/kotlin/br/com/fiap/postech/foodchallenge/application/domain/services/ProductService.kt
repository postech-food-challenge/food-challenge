package br.com.fiap.postech.foodchallenge.application.domain.services

import br.com.fiap.postech.foodchallenge.adapters.persistence.ProductRepository
import br.com.fiap.postech.foodchallenge.application.domain.exceptions.ProductAlreadyExistsException
import br.com.fiap.postech.foodchallenge.application.domain.exceptions.ProductNotFoundException
import br.com.fiap.postech.foodchallenge.application.domain.model.entities.Product
import br.com.fiap.postech.foodchallenge.application.domain.model.entities.update
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    fun createProduct(product: Product): Product {
        val searchedProduct = productRepository.findByName(product.name)

        if(searchedProduct.isPresent)
            throw ProductAlreadyExistsException(product.name)

        return productRepository.save(product)
    }


    fun updateProduct(id: Long, newProduct: Product): Product {
        val currentProductOptional = productRepository.findById(id)

        if(currentProductOptional.isEmpty)
            throw ProductNotFoundException(id)

        val currentProduct = currentProductOptional.get().update(newProduct)

        try {
            return productRepository.save(currentProduct)
        }catch (ex:Exception){
            throw ex
        }
    }

    fun deleteProduct(id: Long): ResponseEntity<Any> {
        val productOptional = productRepository.findById(id)

        if(productOptional.isEmpty)
            throw ProductNotFoundException(id)

        val foundProduct = productOptional.get()

        productRepository.delete(foundProduct)

        return ResponseEntity(HttpStatus.ACCEPTED)
    }

}
