package br.com.fiap.postech.foodchallenge.infrastructure.controller.order

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class CheckoutRequest(
    @field:Size(min = 11, max = 11, message = "CPF must be exactly 11 characters long, if present")
    val cpf: String?,

    @field:NotEmpty(message = "Items list should not be empty")
    val items: List<OrderItemRequest>
)
