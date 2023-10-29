package br.com.fiap.postech.foodchallenge.application.domain.services

import br.com.fiap.postech.foodchallenge.adapters.persistence.ProductRepository
import br.com.fiap.postech.foodchallenge.application.domain.exceptions.NoProductsFoundException
import br.com.fiap.postech.foodchallenge.application.domain.exceptions.ProductAlreadyExistsException
import br.com.fiap.postech.foodchallenge.application.domain.exceptions.ProductNotFoundException
import br.com.fiap.postech.foodchallenge.application.domain.exceptions.ProductsNotFoundInCategoryException
import br.com.fiap.postech.foodchallenge.application.domain.model.entities.Product
import br.com.fiap.postech.foodchallenge.application.domain.model.entities.ProductCategoryEnum
import br.com.fiap.postech.foodchallenge.application.domain.model.entities.update
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    fun createProduct(product: Product): Product {
        productRepository.findByName(product.name)?.let {
            throw ProductAlreadyExistsException(product.name)
        }
        return productRepository.save(product)
    }


    fun updateProduct(id: Long, newProduct: Product): Product {
        val currentProduct = productRepository.findById(id).orElse(null) ?: throw ProductNotFoundException(id)
        val updatedProduct = currentProduct.update(newProduct)

        return productRepository.save(updatedProduct)
    }

    fun deleteProduct(id: Long): ResponseEntity<Any> {
        val foundProduct = productRepository.findById(id).orElse(null)?: throw ProductNotFoundException(id)
        productRepository.delete(foundProduct)

        return ResponseEntity(HttpStatus.ACCEPTED)
    }

    fun findProductByCategory(category: String): List<Product?> {
        val validatedCategory = ProductCategoryEnum.validateCategory(category)

        return productRepository.findByCategory(validatedCategory).takeIf { it.isNotEmpty() }
            //adicionar tratamento no errorHandling
            ?: throw NoProductsFoundException(validatedCategory.name)
    }
}

