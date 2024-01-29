package br.com.fiap.postech.foodchallenge.domain.entities.order

import br.com.fiap.postech.foodchallenge.domain.entities.CPF
import br.com.fiap.postech.foodchallenge.infrastructure.controller.payment.CreatePaymentResponse
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities.OrderEntity
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import java.time.LocalDateTime

data class Order(
    val id: Long? = null,
    val customerCpf: CPF? = null,
    val items: List<OrderItem>,
    val status: OrderStatus,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val paymentValidated: Boolean? = null,
    val price: Int? = null,
    val qrData: String? = null,
    val inStoreOrderId: String? = null
) {
    fun withUpdatedStatus(newStatus: String): Order {
        val updatedStatus = OrderStatus.validateStatus(newStatus)
        return this.copy(status = updatedStatus)
    }

    fun withUpdatedPayment(newPaymentStatus: Boolean): Order {
        return this.copy(paymentValidated = newPaymentStatus)
    }

    companion object {
        fun createOrder(customerId: CPF?, items: List<OrderItem>, createPaymentResponse: CreatePaymentResponse): Order {
            return Order(
                customerCpf = customerId,
                items = items,
                status = OrderStatus.RECEIVED,
                paymentValidated = false,
                price = createPaymentResponse.price,
                qrData = createPaymentResponse.qrData,
                inStoreOrderId = createPaymentResponse.inStoreOrderId
            )
        }

        fun fromEntity(entity: OrderEntity, objectMapper: ObjectMapper): Order {
            val items: List<OrderItem> = objectMapper.treeToValue(entity.itemsData)
            return Order(
                entity.id,
                entity.customerCpf?.let { CPF(it) },
                items,
                OrderStatus.valueOf(entity.status),
                entity.createdAt,
                entity.paymentValidated,
                entity.price,
                entity.qrData,
                entity.inStoreOrderId
            )
        }
    }
}
