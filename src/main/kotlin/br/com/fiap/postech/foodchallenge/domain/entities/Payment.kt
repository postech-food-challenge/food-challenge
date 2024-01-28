package br.com.fiap.postech.foodchallenge.domain.entities

import br.com.fiap.postech.foodchallenge.infrastructure.controller.payment.PaymentRequest

data class Payment(
    val orderId: Long,
    val paymentValidated: Boolean
) {

    companion object {

        fun fromRequest(request: PaymentRequest) =
            Payment(
                orderId = request.orderId,
                paymentValidated = request.paymentValidated
            )
    }
}