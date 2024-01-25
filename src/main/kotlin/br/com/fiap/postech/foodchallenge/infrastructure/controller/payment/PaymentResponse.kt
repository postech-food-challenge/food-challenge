package br.com.fiap.postech.foodchallenge.infrastructure.controller.payment

import br.com.fiap.postech.foodchallenge.domain.entities.Payment

data class PaymentResponse(
    val orderId: Long,
    val paymentType: String,
    val paymentValidated: Boolean
) {

    companion object {
        fun fromDomain(domain: Payment): PaymentResponse {
            return PaymentResponse(
                orderId = domain.orderId,
                paymentType = domain.paymentType,
                paymentValidated = domain.paymentValidated
            )
        }
    }
}