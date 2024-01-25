package br.com.fiap.postech.foodchallenge.configuration

import br.com.fiap.postech.foodchallenge.application.gateways.ProductGateway
import br.com.fiap.postech.foodchallenge.application.usecases.product.CreateProductInteract
import br.com.fiap.postech.foodchallenge.application.usecases.product.DeleteProductInteract
import br.com.fiap.postech.foodchallenge.application.usecases.product.FindProductByCategoryInteract
import br.com.fiap.postech.foodchallenge.application.usecases.product.UpdateProductInteract
import br.com.fiap.postech.foodchallenge.infrastructure.gateways.ProductRepositoryGateway
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.ProductRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProductConfig {
    @Bean
    fun createCreateProductUseCase(gateway: ProductGateway) = CreateProductInteract(gateway)

    @Bean
    fun createUpdateProductUseCase(gateway: ProductGateway) = UpdateProductInteract(gateway)

    @Bean
    fun createDeleteProductUseCase(gateway: ProductGateway) = DeleteProductInteract(gateway)

    @Bean
    fun createFindProductByCategoryUseCase(gateway: ProductGateway) = FindProductByCategoryInteract(gateway)

    @Bean
    fun createProductGateway(repository: ProductRepository) = ProductRepositoryGateway(repository)
}