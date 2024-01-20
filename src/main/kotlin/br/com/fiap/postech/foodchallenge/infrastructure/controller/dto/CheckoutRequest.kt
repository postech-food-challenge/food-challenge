package br.com.fiap.postech.foodchallenge.infrastructure.controller.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Positive

data class CheckoutRequest(
    @field:Positive(message = "Customer ID should be positive, if present.")
    val customerId: Long?,

    @field:NotEmpty(message = "Items list should not be empty")
    val items: List<OrderItemRequest>
)
