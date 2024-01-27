package br.com.fiap.postech.foodchallenge.infrastructure.controller.order

import jakarta.validation.constraints.NotNull

data class UpdateOrderStatusRequest(
    @field:NotNull(message = "New status should not be null")
    val status: String
)
