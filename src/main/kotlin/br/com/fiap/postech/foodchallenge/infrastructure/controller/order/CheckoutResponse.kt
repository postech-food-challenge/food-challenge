package br.com.fiap.postech.foodchallenge.infrastructure.controller.order

data class CheckoutResponse(
    val orderId: Long,
    val status: String
)
