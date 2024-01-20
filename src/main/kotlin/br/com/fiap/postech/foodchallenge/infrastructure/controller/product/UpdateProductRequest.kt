package br.com.fiap.postech.foodchallenge.infrastructure.controller.product


import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import org.hibernate.validator.constraints.URL

data class UpdateProductRequest(
    @field:NotBlank(message = "Name cannot be blank")
    val name: String,

    @field:NotBlank(message = "Description cannot be blank")
    val description: String,

    @field:NotBlank(message = "Image cannot be blank")
    @field:URL(message = "Image should be an URL")
    val image: String,

    @field:NotNull
    @field:Positive(message = "Price should be greater then zero")
    val price: Int,

    @field:NotNull
    val category: String
)
