package br.com.fiap.postech.foodchallenge.application.usecases.payment

import br.com.fiap.postech.foodchallenge.application.gateways.OrderGateway
import br.com.fiap.postech.foodchallenge.application.gateways.PaymentGateway
import br.com.fiap.postech.foodchallenge.domain.entities.Payment
import br.com.fiap.postech.foodchallenge.domain.entities.order.OrderStatus
import br.com.fiap.postech.foodchallenge.domain.exceptions.OrderNotFoundException

class UpdatePaymentInteract(private val paymentGateway: PaymentGateway, private val orderGateway: OrderGateway) {
    fun updatePayment(payment: Payment): Payment {
        val updatedPayment =
            paymentGateway.findByOrderId(payment.orderId)?.let { domainPayment -> domainPayment.update(payment) }
                ?: throw OrderNotFoundException(payment.orderId)

        updatedPayment.paymentValidated?.equals(true).apply {
            paymentGateway.save(updatedPayment).also {
                orderGateway.findById(payment.orderId).let {
                    orderGateway.save(
                        it?.withUpdatedStatus(OrderStatus.IN_PREPARATION.name) ?: throw OrderNotFoundException(
                            payment.orderId
                        )
                    )
                }
            }
        }

        return updatedPayment
    }
}