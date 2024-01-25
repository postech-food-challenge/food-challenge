package br.com.fiap.postech.foodchallenge.adapters.controller.dto

import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.OrderStatus
import jakarta.validation.constraints.NotNull

data class UpdateOrderStatusRequest(
    @field:NotNull(message = "New status should not be null")
    val status: OrderStatus

)
