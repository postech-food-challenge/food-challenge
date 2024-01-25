package br.com.fiap.postech.foodchallenge.infrastructure.controller.product


import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive

data class CreateProductRequest(
    @field:NotBlank(message = "Name cannot be blank")
    val name: String,

    @field:NotBlank(message = "Description cannot be blank")
    val description: String,

    @field:NotBlank(message = "Image cannot be blank")
    @field:Pattern(
        regexp = "^(https?|ftp)://?([^:/\\s]+)(:[0-9]+)?((/\\w+)*/)([\\w-.]+[^#?\\s]+)([^#\\s]*)?(#[\\w\\-]+)?$",
        message = "Image should be a valid URL"
    )
    val image: String,

    @field:NotNull
    @field:Positive(message = "Price should be greater than zero")
    val price: Int,

    @field:NotNull
    val category: String
)

