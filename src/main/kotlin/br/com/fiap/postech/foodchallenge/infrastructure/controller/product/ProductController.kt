package br.com.fiap.postech.foodchallenge.infrastructure.controller.product

import br.com.fiap.postech.foodchallenge.application.usecases.product.CreateProductInteract
import br.com.fiap.postech.foodchallenge.application.usecases.product.DeleteProductInteract
import br.com.fiap.postech.foodchallenge.application.usecases.product.FindProductByCategoryInteract
import br.com.fiap.postech.foodchallenge.application.usecases.product.UpdateProductInteract
import br.com.fiap.postech.foodchallenge.domain.entities.Product
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.ACCEPTED
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/products")
class ProductController(
    private val createProductInteract: CreateProductInteract,
    private val updateProductInteract: UpdateProductInteract,
    private val deleteProductInteract: DeleteProductInteract,
    private val findProductByCategoryInteract: FindProductByCategoryInteract
) {
    @PostMapping
    fun createProduct(@RequestBody @Valid createProductRequest: CreateProductRequest) =
        Product.fromRequest(createProductRequest)
            .let { domain -> createProductInteract.createProduct(domain) }
            .let { domainResponse -> ProductResponse.fromDomain(domainResponse) }

    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody @Valid updateProductRequest: UpdateProductRequest
    ) = Product.fromRequest(updateProductRequest)
        .let { domain -> updateProductInteract.updateProduct(id, domain) }
        .let { domainResponse -> ProductResponse.fromDomain(domainResponse) }

    @DeleteMapping("/{id}")
    @ResponseStatus(ACCEPTED)
    fun deleteProduct(@PathVariable id: Long) = deleteProductInteract.deleteProduct(id)

    @GetMapping
    fun getProducts(@RequestParam category: String): List<ProductResponse> =
        findProductByCategoryInteract.findProductByCategory(category)
            .map { domainResponse -> ProductResponse.fromDomain(domainResponse) }

}