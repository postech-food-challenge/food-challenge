package br.com.fiap.postech.foodchallenge.application.domain.model.aggregates

import br.com.fiap.postech.foodchallenge.adapters.persistence.entities.OrderEntity
import java.time.LocalDateTime

class OrderEntityComparator : Comparator<OrderEntity> {
    override fun compare(order1: OrderEntity, order2: OrderEntity): Int {
        val statusComparison = compareByStatus(order1.status, order2.status)
        return if (statusComparison != 0) {
            statusComparison
        } else {
            compareByCreatedAt(order1.createdAt, order2.createdAt)
        }
    }

    private fun compareByStatus(status1: OrderStatus, status2: OrderStatus): Int {
        val order1 = getOrderStatusOrder(status1)
        val order2 = getOrderStatusOrder(status2)

        return order1.compareTo(order2)
    }

    private fun compareByCreatedAt(date1: LocalDateTime, date2: LocalDateTime): Int {
        return date1.compareTo(date2)
    }

    private fun getOrderStatusOrder(status: OrderStatus): Int {
        return when (status) {
            OrderStatus.READY -> 0
            OrderStatus.IN_PREPARATION -> 1
            OrderStatus.RECEIVED -> 2
            OrderStatus.COMPLETED -> 3
            OrderStatus.CANCELED -> 4
        }
    }
}