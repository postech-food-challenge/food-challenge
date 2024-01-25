package br.com.fiap.postech.foodchallenge.infrastructure.controller.dto

import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.OrderStatus

data class CheckoutResponse(
    val orderId: Long,
    val status: OrderStatus
)
