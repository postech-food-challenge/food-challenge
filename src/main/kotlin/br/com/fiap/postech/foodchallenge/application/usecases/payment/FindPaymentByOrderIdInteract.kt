package br.com.fiap.postech.foodchallenge.application.usecases.payment

import br.com.fiap.postech.foodchallenge.application.gateways.OrderGateway
import br.com.fiap.postech.foodchallenge.domain.exceptions.OrderNotFoundException

class FindPaymentByOrderIdInteract(private val gateway: OrderGateway) {

    fun findPaymentByOrderId(orderId: Long) =
        gateway.findById(orderId) ?: throw OrderNotFoundException(orderId)
}