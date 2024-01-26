package br.com.fiap.postech.foodchallenge.infrastructure.controller.order

import br.com.fiap.postech.foodchallenge.domain.entities.order.OrderStatus
import jakarta.validation.constraints.NotNull

data class UpdateOrderStatusRequest(
    @field:NotNull(message = "New status should not be null")
    val status: OrderStatus
)
