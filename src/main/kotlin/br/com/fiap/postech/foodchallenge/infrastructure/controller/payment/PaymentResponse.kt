package br.com.fiap.postech.foodchallenge.infrastructure.controller.payment

import br.com.fiap.postech.foodchallenge.domain.entities.Payment
import br.com.fiap.postech.foodchallenge.domain.entities.order.Order

data class PaymentResponse(
    val orderId: Long? = null,
    val paymentValidated: Boolean?
) {

    companion object {
        fun fromDomain(domain: Order): PaymentResponse {
            return PaymentResponse(
                orderId = domain.id,
                paymentValidated = domain.paymentValidated
            )
        }
    }
}