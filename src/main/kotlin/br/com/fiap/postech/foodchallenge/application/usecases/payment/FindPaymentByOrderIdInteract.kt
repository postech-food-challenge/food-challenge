package br.com.fiap.postech.foodchallenge.application.usecases.payment

import br.com.fiap.postech.foodchallenge.application.gateways.PaymentGateway

class FindPaymentByOrderIdInteract(private val gateway: PaymentGateway) {

    fun findPaymentByOrderId(orderId: Long) =
        gateway.findByOrderId(orderId)
}