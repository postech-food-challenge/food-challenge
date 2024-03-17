package br.com.fiap.postech.foodchallenge.infrastructure.controller.order

import br.com.fiap.postech.foodchallenge.application.usecases.order.ListOrdersInteract
import br.com.fiap.postech.foodchallenge.application.usecases.order.OrderCheckoutInteract
import br.com.fiap.postech.foodchallenge.application.usecases.order.UpdateOrderStatusInteract
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/orders")
class OrderController(
    private val orderCheckoutInteract: OrderCheckoutInteract,
    private val listOrdersInteract: ListOrdersInteract,
    private val updateOrderStatusInteract: UpdateOrderStatusInteract
) {

    @PostMapping("/checkout")
    fun checkout(@RequestBody request: CheckoutRequest, @RequestAttribute("cpf") token: String?) = orderCheckoutInteract.checkout(request, token)

    @GetMapping
    fun listOrders(@RequestParam(required = false) status: String?) = listOrdersInteract.getOrders(status)

    @PatchMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody statusRequest: UpdateOrderStatusRequest
    ) = updateOrderStatusInteract.updateOrderStatus(id, statusRequest.status).let { OrderResponse.fromDomain(it) }

}