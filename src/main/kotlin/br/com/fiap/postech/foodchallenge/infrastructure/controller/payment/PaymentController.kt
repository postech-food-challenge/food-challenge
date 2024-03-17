package br.com.fiap.postech.foodchallenge.infrastructure.controller.payment

import br.com.fiap.postech.foodchallenge.application.usecases.payment.FindPaymentByOrderIdInteract
import br.com.fiap.postech.foodchallenge.application.usecases.payment.UpdatePaymentInteract
import br.com.fiap.postech.foodchallenge.domain.entities.Payment
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/payments/orders")
class PaymentController(
    private val findPaymentStatusInteract: FindPaymentByOrderIdInteract,
    private val updatePaymentInteract: UpdatePaymentInteract
) {

    @PutMapping("/webhook")
    fun updatePaymentStatusByOrderId(@RequestBody request: PaymentRequest) =
        updatePaymentInteract.updatePaymentStatusByOrderId(request.let { Payment.fromRequest(request) })


    @GetMapping("/{orderId}")
    fun getPaymentStatus(@PathVariable orderId: Long): PaymentResponse =
        findPaymentStatusInteract.findPaymentByOrderId(orderId)
            .let { domainResponse -> PaymentResponse.fromDomain(domainResponse) }
}