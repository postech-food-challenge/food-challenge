package br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities

import jakarta.persistence.*

@Entity
@Table(name = "payments")
data class PaymentEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne
    @JoinColumn(name= "order", referencedColumnName = "id")
    val order: OrderEntity,

    val paymentType: String,

    val paymentValidated: Boolean
)