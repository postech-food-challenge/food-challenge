package br.com.fiap.postech.foodchallenge.adapters.controller

import br.com.fiap.postech.foodchallenge.adapters.controller.dto.CheckoutRequest
import br.com.fiap.postech.foodchallenge.application.domain.services.OrderService
import org.springframework.http.ResponseEntity
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.Order
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/orders")
class OrderController(private val service: OrderService) {

    @PostMapping("/checkout")
    fun fakeCheckout(@RequestBody request: CheckoutRequest) = ResponseEntity.ok(service.createOrder(request))

    @GetMapping
    fun listOrders(@RequestParam(required = false) status: String?) : List<Order> = service.getOrders(status)

}