package br.com.fiap.postech.foodchallenge.application.usecases.payment

import br.com.fiap.postech.foodchallenge.application.gateways.ProductGateway
import br.com.fiap.postech.foodchallenge.domain.entities.order.OrderItem

class CreatePaymentInteract(private val productGateway: ProductGateway) {

    fun createPayment(orderItems: List<OrderItem>) : Int {
        val price = calculatePaymentAmount(orderItems)

        //integracao com mercado pago

        return price
    }
    private fun calculatePaymentAmount(orderItems: List<OrderItem>) : Int {
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