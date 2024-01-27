package br.com.fiap.postech.foodchallenge.domain.entities

import br.com.fiap.postech.foodchallenge.infrastructure.controller.payment.PaymentRequest
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities.PaymentEntity
import org.webjars.NotFoundException


data class Payment(
    val orderId: Long,
    val paymentValidated: Boolean
) {
    fun update(newPayment: Payment) =
        this.copy(
            orderId = newPayment.orderId,
            paymentValidated = newPayment.paymentValidated
        )

    companion object {
        fun fromEntity(entityObject: PaymentEntity) =
            Payment(
                orderId = entityObject.id ?: throw NotFoundException("Order not found"),
                paymentValidated = entityObject.paymentValidated
            )

        fun fromRequest(request: PaymentRequest) =
            Payment(
                orderId = request.orderId,
                paymentValidated = request.paymentValidated
            )
    }
}