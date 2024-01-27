package br.com.fiap.postech.foodchallenge.application.usecases.payment

import br.com.fiap.postech.foodchallenge.application.gateways.PaymentGateway
import br.com.fiap.postech.foodchallenge.domain.entities.Payment
import br.com.fiap.postech.foodchallenge.domain.exceptions.OrderNotFoundException

class UpdatePaymentInteract(private val gateway: PaymentGateway) {
    fun updatePayment(payment: Payment) {
        //encontrar o pedido, salvar o pagamento, alterar o status do pedido para em preparacao, lancar erro caso nao encontre o pedido
        gateway.findByOrderId(payment.orderId)
            ?.let { domainPayment -> domainPayment.update(payment)
                .let { updatedPayment -> gateway.save(updatedPayment) } }
            ?: throw OrderNotFoundException(payment.orderId)
                //.also { updatedOrder -> gateway }

    }
}