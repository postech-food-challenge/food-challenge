package br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities

import br.com.fiap.postech.foodchallenge.domain.entities.Payment
import br.com.fiap.postech.foodchallenge.domain.entities.order.Order
import jakarta.persistence.*

@Entity
@Table(name = "payments")
data class PaymentEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val orderId: Long,

    val amount: Int? = null,

    val paymentValidated: Boolean? = null
) {

    companion object {
        fun fromDomain(domain: Payment) =
            PaymentEntity(
                id = domain.id,
                orderId = domain.orderId,
                amount = domain.amount,
                paymentValidated = domain.paymentValidated
            )
    }
}