package br.com.fiap.postech.foodchallenge.infrastructure.controller.payment

import br.com.fiap.postech.foodchallenge.application.usecases.payment.FindPaymentByOrderIdInteract
import br.com.fiap.postech.foodchallenge.application.usecases.payment.UpdatePaymentInteract
import br.com.fiap.postech.foodchallenge.domain.entities.Payment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/payments/orders")
class PaymentController(
    private val findPaymentStatusInteract: FindPaymentByOrderIdInteract,
    private val updatePaymentInteract: UpdatePaymentInteract
) {

    @PutMapping("/webhook")
    fun updatePaymentStatus(@RequestBody request: PaymentRequest) =
        updatePaymentInteract.updatePayment(request.let { request -> Payment.fromRequest(request) })


    @GetMapping("/{orderId}")
    fun getPaymentStatus(@PathVariable orderId: Long): PaymentResponse =
        findPaymentStatusInteract.findPaymentByOrderId(orderId)
            .let { domainResponse -> PaymentResponse.fromDomain(domainResponse) }
}