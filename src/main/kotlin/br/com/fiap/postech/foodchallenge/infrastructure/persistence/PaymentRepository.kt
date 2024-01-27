package br.com.fiap.postech.foodchallenge.infrastructure.persistence

import br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities.PaymentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentRepository : JpaRepository<PaymentEntity, Long> {
    fun findByOrderId(orderId: Long): PaymentEntity
}