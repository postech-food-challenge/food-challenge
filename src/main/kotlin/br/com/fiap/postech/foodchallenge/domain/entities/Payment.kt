package br.com.fiap.postech.foodchallenge.domain.entities

import br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities.PaymentEntity
import org.springframework.web.client.HttpClientErrorException.NotFound
import org.webjars.NotFoundException


data class Payment(
    val orderId: Long,
    val paymentType: String,
    val paymentValidated: Boolean
    )
{
    companion object {
        fun fromEntity(entityObject: PaymentEntity) =
            Payment(
                orderId = entityObject.order.id ?: throw NotFoundException("Order not found"),
                paymentType = entityObject.paymentType,
                paymentValidated = entityObject.paymentValidated
            )
    }
}