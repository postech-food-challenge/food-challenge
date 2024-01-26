package br.com.fiap.postech.foodchallenge.application.gateways

import br.com.fiap.postech.foodchallenge.domain.entities.order.Order

interface OrderGateway {
    fun save(order: Order): Order
}