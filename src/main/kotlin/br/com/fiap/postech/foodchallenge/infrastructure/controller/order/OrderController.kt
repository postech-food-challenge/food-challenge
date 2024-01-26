package br.com.fiap.postech.foodchallenge.infrastructure.controller.order

import br.com.fiap.postech.foodchallenge.application.domain.services.OrderService
import br.com.fiap.postech.foodchallenge.application.usecases.order.OrderCheckoutInteract
import br.com.fiap.postech.foodchallenge.domain.entities.order.Order
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/orders")
class OrderController(
    private val service: OrderService,
    private val orderCheckoutInteract: OrderCheckoutInteract
) {

    @PostMapping("/checkout")
    fun checkout(@RequestBody request: CheckoutRequest) = orderCheckoutInteract.checkout(request)

    @GetMapping
    fun listOrders(@RequestParam(required = false) status: String?): List<Order> = service.getOrders(status)

    @PatchMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody newStatusWrapper: UpdateOrderStatusRequest
    ): Order = service.updateOrderStatus(id, newStatusWrapper)

}