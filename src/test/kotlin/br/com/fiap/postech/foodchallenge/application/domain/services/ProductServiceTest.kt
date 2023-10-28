package br.com.fiap.postech.foodchallenge.application.domain.services

import br.com.fiap.postech.foodchallenge.adapters.persistence.ProductRepository
import br.com.fiap.postech.foodchallenge.application.domain.exceptions.ProductAlreadyExistsException
import br.com.fiap.postech.foodchallenge.application.domain.exceptions.ProductNotFoundException
import br.com.fiap.postech.foodchallenge.application.domain.model.entities.Product
import br.com.fiap.postech.foodchallenge.application.domain.model.entities.ProductCategory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.anyVararg
import org.mockito.kotlin.doNothing

import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.http.HttpStatus
import java.util.*

class ProductServiceTest {
    private lateinit var productRepository: ProductRepository
    private lateinit var productService: ProductService
    @BeforeEach
    fun setUp() {
        productRepository = mock()
        productService = ProductService(productRepository)
    }

    @Test
    fun `createProduct - should create new product`() {
        val newProduct = createNewProduct()
        whenever(productRepository.findByName(newProduct.name)).thenReturn(Optional.empty())
        whenever(productRepository.save(newProduct)).thenReturn(newProduct.copy(id = 1))

        val createdProduct = productService.createProduct(newProduct)
        verify(productRepository).save(newProduct)

        assert(newProduct.name == createdProduct.name)
    }
    @Test
    fun `createProduct - should throw ProductAlreadyExistsException`() {
        val newProduct = createNewProduct()
        whenever(productRepository.findByName(newProduct.name)).thenReturn(Optional.of(newProduct))

        assertThrows<ProductAlreadyExistsException> { productService.createProduct(newProduct) }

        verify(productRepository, never()).save(any())
    }

    @Test
    fun `updateProduct - should update product`() {
        val createdProduct = createNewProduct()
        val productToUpdate = updateProduct()
        val productId = 1L

        whenever(productRepository.findById(1L)).thenReturn(Optional.of(createdProduct.copy(id = productId)))
        whenever(productRepository.save(anyVararg(Product::class))).thenReturn(productToUpdate.copy(id = productId))


        val updatedProduct = productService.updateProduct(productId, productToUpdate)

        assert(updatedProduct.id == productId)
        assert(updatedProduct.name == productToUpdate.name)
        assert(updatedProduct.price == productToUpdate.price)
    }

    @Test
    fun `updateProduct - should throw ProductNotFoundException`() {
        val productToUpdate = updateProduct()
        val productId = 1L

        whenever(productRepository.findById(1L)).thenReturn(Optional.empty())

        assertThrows<ProductNotFoundException> { productService.updateProduct(productId, productToUpdate) }

        verify(productRepository, never()).save(any())
    }

    @Test
    fun `deleteProduct - should delete product`() {
        val createdProduct = createNewProduct()
        val productId = 1L

        whenever(productRepository.findById(1L)).thenReturn(Optional.of(createdProduct.copy(id = productId)))
        doNothing().`when`(productRepository).delete(any())


        val status = productService.deleteProduct(productId)

        assert(status.statusCode == HttpStatus.ACCEPTED)

    }

    @Test
    fun `deleteProduct - should throw ProductNotFoundException`() {
        val createdProduct = createNewProduct()
        val productId = 1L

        whenever(productRepository.findById(1L)).thenReturn(Optional.empty())

        assertThrows<ProductNotFoundException> { productService.deleteProduct(productId) }

        verify(productRepository, never()).delete(any())

    }



    private fun createNewProduct():Product {
        return Product(
            id = null,
            name = "Batata",
            description = "Batata em palitos",
            image = "www.google.com",
            price = 999,
            category = ProductCategory.SIDE
        )
    }
    private fun updateProduct():Product {
        return Product(
            id = null,
            name = "Batata2",
            description = "Batata em palitos",
            image = "www.google.com",
            price = 1200,
            category = ProductCategory.SIDE
        )
    }
}