package br.com.fiap.postech.foodchallenge.application.usecases.payment

import br.com.fiap.postech.foodchallenge.application.gateways.OrderGateway
import br.com.fiap.postech.foodchallenge.domain.entities.Payment
import br.com.fiap.postech.foodchallenge.domain.entities.order.OrderStatus
import br.com.fiap.postech.foodchallenge.domain.exceptions.OrderNotFoundException

class UpdatePaymentInteract(private val orderGateway: OrderGateway) {
    fun updatePaymentStatusByOrderId(updatedPayment: Payment): Payment {
        val order = orderGateway.findById(updatedPayment.orderId)
            ?: throw OrderNotFoundException(updatedPayment.orderId)

        updatedPayment.paymentValidated?.equals(true).apply {
            orderGateway.save(order.withUpdatedPayment(updatedPayment.paymentValidated).withUpdatedStatus(OrderStatus.IN_PREPARATION.name))
        }

        return updatedPayment
    }
}