package br.com.fiap.postech.foodchallenge.adapters.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class OrderItemRequest(
    @field:NotBlank(message = "Product ID should not be blank")
    val productId: Long,

    @field:Positive(message = "Quantity should be positive")
    val quantity: Int,

    val observations: String? = null,

    @field:NotNull(message = "ToGo must be provided")
    val toGo: Boolean
)
