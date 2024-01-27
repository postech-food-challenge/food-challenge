package br.com.fiap.postech.foodchallenge.domain.entities.order

import br.com.fiap.postech.foodchallenge.domain.exceptions.InvalidParameterException

enum class OrderStatus {
    RECEIVED, IN_PREPARATION, READY, COMPLETED, CANCELED;

    companion object {
        fun validateStatus(status: String): OrderStatus {
            return enumValues<OrderStatus>().find { it.name == status }
                ?: throw InvalidParameterException("Invalid status: $status")
        }
    }
}