package br.com.fiap.postech.foodchallenge.adapters.controller

import br.com.fiap.postech.foodchallenge.application.domain.model.entities.Product
import br.com.fiap.postech.foodchallenge.application.domain.services.ProductService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/products")
class ProductController(
    private val productService: ProductService
) {
    @PostMapping
    fun createProduct(@RequestBody @Valid product: Product): Product =
        productService.createProduct(product)

    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: Long,
                      @RequestBody @Valid product: Product): Product =
        productService.updateProduct(id, product)

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Any> = productService.deleteProduct(id)

}