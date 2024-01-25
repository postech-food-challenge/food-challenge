package br.com.fiap.postech.foodchallenge.infrastructure.gateways

import br.com.fiap.postech.foodchallenge.application.gateways.PaymentGateway
import br.com.fiap.postech.foodchallenge.domain.entities.Payment
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.PaymentRepository

class PaymentRepositoryGateway(private val repository: PaymentRepository) : PaymentGateway {

    override fun findByOrderId(orderId: Long): Payment =
        repository.findByOrderId(orderId).let { entity -> Payment.fromEntity(entity) }
}