package br.com.fiap.postech.foodchallenge.infrastructure.gateways

import br.com.fiap.postech.foodchallenge.application.gateways.PaymentGateway
import br.com.fiap.postech.foodchallenge.domain.entities.Payment
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.PaymentRepository
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities.PaymentEntity

class PaymentRepositoryGateway(private val repository: PaymentRepository) : PaymentGateway {

    override fun findByOrderId(orderId: Long): Payment? =
        repository.findByOrderId(orderId).let { entity -> Payment.fromEntity(entity) }

    override fun save(payment: Payment) {
        repository.save(payment.let { domain -> PaymentEntity.fromDomain(domain) })
    }
}