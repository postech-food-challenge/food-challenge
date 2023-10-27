package br.com.fiap.postech.foodchallenge.adapters.controller

import br.com.fiap.postech.foodchallenge.application.domain.model.entities.Product
import br.com.fiap.postech.foodchallenge.application.domain.services.ProductService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/products")
class ProductController(
    private val productService: ProductService
) {
    @PostMapping
    fun createProduct(@RequestParam @Valid product: Product): Product =
        productService.createProduct(product)

}