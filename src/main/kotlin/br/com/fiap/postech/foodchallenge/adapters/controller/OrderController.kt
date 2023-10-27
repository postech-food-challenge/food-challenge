package br.com.fiap.postech.foodchallenge.adapters.controller

import br.com.fiap.postech.foodchallenge.adapters.controller.dto.CheckoutRequest
import br.com.fiap.postech.foodchallenge.adapters.controller.dto.CheckoutResponse
import br.com.fiap.postech.foodchallenge.application.domain.services.OrderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/orders")
class OrderController(private val service: OrderService) {

    @PostMapping("/checkout")
    fun fakeCheckout(@RequestBody request: CheckoutRequest): ResponseEntity<Any> {
        val order = service.createOrder(request)

        val orderId = order.id
            ?: return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create order")

        return ResponseEntity.ok(CheckoutResponse(orderId, order.status))
    }

}