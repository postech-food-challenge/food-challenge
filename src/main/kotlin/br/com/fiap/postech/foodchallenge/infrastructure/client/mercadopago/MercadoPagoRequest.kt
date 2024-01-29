package br.com.fiap.postech.foodchallenge.infrastructure.client.mercadopago

import br.com.fiap.postech.foodchallenge.domain.entities.order.OrderItem
import com.fasterxml.jackson.annotation.JsonProperty

class MercadoPagoRequest(
    @JsonProperty("cash_out")
    val cashOut: CashOut,

    val description: String? = null,

    @JsonProperty("external_reference")
    val externalReference: String? = null,

    val items: List<ItemRequest>,

    @JsonProperty("notification_uri")
    val notificationUri: String? = null,

    val sponsor: Sponsor? = null,

    val title: String? = null,

    @JsonProperty("total_amount")
    val totalAmount: Int? = null


) {
    class CashOut(
        val amount: Int? = null
    )

    class ItemRequest(
        @JsonProperty("sku_number")
        val skuNumber: String? = null,

        val category: String? = null,
        val title: String? = null,
        val description: String? = null,

        @JsonProperty("unit_price")
        val unitPrice: Int? = null,

        val quantity: Int? = null,

        @JsonProperty("total_amount")
        val totalAmount: Int? = null,
    )

    class Sponsor(
        val id: String
    )

    companion object {
        fun fromDomain(orderItems: List<OrderItem>, price: Int) =
            MercadoPagoRequest(
                cashOut = CashOut(price),
                items = orderItems.map {
                    ItemRequest(
                        description = it.observations,
                        quantity = it.quantity
                    )
                },
                totalAmount = price
            )
    }
}