package br.com.fiap.postech.foodchallenge.domain.entities

import br.com.fiap.postech.foodchallenge.domain.exceptions.InvalidParameterException
import br.com.fiap.postech.foodchallenge.infrastructure.controller.payment.PaymentRequest
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities.PaymentEntity
import org.webjars.NotFoundException


data class Payment(
    val id: Long? = null,
    val orderId: Long,
    val paymentValidated: Boolean? = null,
    val amount: Int? = null
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

        fun createPayment(id: Long?, validated: Boolean?, amount: Int) = Payment (
            orderId = id?: throw InvalidParameterException("Order Id should not be null"),
            paymentValidated = validated,
            amount = amount
        )
    }
}