package br.com.fiap.postech.foodchallenge.application.usecases.payment

import br.com.fiap.postech.foodchallenge.application.gateways.MercadoPagoGateway
import br.com.fiap.postech.foodchallenge.application.gateways.ProductGateway
import br.com.fiap.postech.foodchallenge.domain.entities.order.OrderItem
import br.com.fiap.postech.foodchallenge.infrastructure.client.mercadopago.MercadoPagoRequest
import br.com.fiap.postech.foodchallenge.infrastructure.controller.payment.CreatePaymentResponse

class CreatePaymentInteract(
    private val productGateway: ProductGateway,
    private val mercadoPagoGateway: MercadoPagoGateway
) {

    fun createPayment(orderItems: List<OrderItem>): CreatePaymentResponse {
        val price = calculatePaymentAmount(orderItems)

        val result = MercadoPagoRequest.fromDomain(orderItems, price)
            .let { request -> mercadoPagoGateway.createPayment(request) }

        return CreatePaymentResponse(price, result.qrData, result.inStoreOrderId)
    }

    private fun calculatePaymentAmount(orderItems: List<OrderItem>): Int {
        var totalAmount = 0

        orderItems.forEach { orderItem ->
            val product = productGateway.findById(orderItem.productId)

            if (product != null) {
                totalAmount += orderItem.quantity * product.price
            }
        }

        return totalAmount
    }
}