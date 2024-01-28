package br.com.fiap.postech.foodchallenge.application.usecases.order

import br.com.fiap.postech.foodchallenge.application.gateways.CustomerGateway
import br.com.fiap.postech.foodchallenge.application.gateways.OrderGateway
import br.com.fiap.postech.foodchallenge.application.gateways.ProductGateway
import br.com.fiap.postech.foodchallenge.application.usecases.payment.CreatePaymentInteract
import br.com.fiap.postech.foodchallenge.domain.entities.order.Order
import br.com.fiap.postech.foodchallenge.domain.entities.order.OrderItem
import br.com.fiap.postech.foodchallenge.domain.exceptions.ProductNotFoundException
import br.com.fiap.postech.foodchallenge.infrastructure.controller.order.CheckoutRequest
import br.com.fiap.postech.foodchallenge.infrastructure.controller.order.OrderResponse

class OrderCheckoutInteract(
    private val orderGateway: OrderGateway,
    private val customerGateway: CustomerGateway,
    private val productGateway: ProductGateway,
    private val createPaymentInteract: CreatePaymentInteract
) {

    fun checkout(request: CheckoutRequest): OrderResponse {
        val orderItems = request.items.map { orderItemRequest ->
            productGateway.findById(orderItemRequest.productId)?.let {
                OrderItem.create(
                    orderItemRequest.productId,
                    orderItemRequest.quantity,
                    orderItemRequest.observations,
                    orderItemRequest.toGo
                )
            } ?: throw ProductNotFoundException(orderItemRequest.productId)
        }

        val customerCpf = request.cpf?.let(customerGateway::findByCpf)?.cpf
        val price = createPaymentInteract.createPayment(orderItems)

        return Order.createOrder(customerCpf, orderItems, price).run {
            orderGateway.save(this)
        }.let {
            OrderResponse.fromDomain(it) ?: throw IllegalStateException("Saved order ID should not be null.")
        }
    }
}