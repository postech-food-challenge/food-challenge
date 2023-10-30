package br.com.fiap.postech.foodchallenge.adapters.controller.dto

import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.OrderStatus

data class CheckoutResponse(
    val orderId: Long,
    val status: OrderStatus
)
