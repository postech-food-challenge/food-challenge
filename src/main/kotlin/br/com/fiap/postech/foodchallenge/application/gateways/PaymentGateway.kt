package br.com.fiap.postech.foodchallenge.application.gateways

import br.com.fiap.postech.foodchallenge.domain.entities.Payment

interface PaymentGateway {
    fun findByOrderId(id: Long): Payment
}