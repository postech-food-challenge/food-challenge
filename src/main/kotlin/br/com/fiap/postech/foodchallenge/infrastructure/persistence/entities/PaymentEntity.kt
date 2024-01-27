package br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities

import br.com.fiap.postech.foodchallenge.domain.entities.Payment
import jakarta.persistence.*

@Entity
@Table(name = "payments")
data class PaymentEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne
    @JoinColumn(name= "orders", referencedColumnName = "id")
    val orderId: Long,

    val amount: Long? = null,

    val paymentValidated: Boolean
) {

    companion object {
        fun fromDomain(domain: Payment) =
            PaymentEntity(
                orderId = domain.orderId,
                paymentValidated = domain.paymentValidated
            )
    }
}