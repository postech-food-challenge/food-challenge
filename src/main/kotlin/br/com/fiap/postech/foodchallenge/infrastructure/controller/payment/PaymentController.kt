package br.com.fiap.postech.foodchallenge.infrastructure.controller.payment

import br.com.fiap.postech.foodchallenge.application.usecases.payment.FindPaymentByOrderIdInteract
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/orders/{orderId}/payments")
class PaymentController(
    private val findPaymentStatusInteract: FindPaymentByOrderIdInteract
) {


    @GetMapping
    fun getPaymentStatus(@PathVariable orderId: Long): PaymentResponse =
        findPaymentStatusInteract.findPaymentByOrderId(orderId)
            .let { domainResponse -> PaymentResponse.fromDomain(domainResponse) }
}