package br.com.fiap.postech.foodchallenge.infrastructure.controller.payment

data class PaymentRequest(
    val orderId: Long,
    val paymentValidated: Boolean
) {
}