package br.com.fiap.postech.foodchallenge.application.usecases.payment

import br.com.fiap.postech.foodchallenge.application.gateways.PaymentGateway
import br.com.fiap.postech.foodchallenge.application.gateways.ProductGateway
import br.com.fiap.postech.foodchallenge.domain.entities.Payment
import br.com.fiap.postech.foodchallenge.domain.entities.order.Order

class CreatePaymentInteract(private val paymentGateway: PaymentGateway, private val productGateway: ProductGateway) {
    fun createPayment(order: Order) {
        val amount = calculatePaymentAmount(order)

        //integrar com mercado e lançar um erro caso não seja possível:

        paymentGateway.save(Payment.createPayment(order.id, false, amount))
    }

    private fun calculatePaymentAmount(order: Order) : Int {
        var totalAmount = 0

        order.items.forEach { orderItem ->
            val product = productGateway.findById(orderItem.productId)

            if (product != null) {
                totalAmount += orderItem.quantity * product.price
            }
        }

        return totalAmount
    }
}