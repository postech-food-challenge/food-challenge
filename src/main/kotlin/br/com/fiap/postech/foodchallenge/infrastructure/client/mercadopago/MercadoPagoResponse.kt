package br.com.fiap.postech.foodchallenge.infrastructure.client.mercadopago

import com.fasterxml.jackson.annotation.JsonProperty

class MercadoPagoResponse(

    @JsonProperty("qr_data")
    val qrData: String,

    @JsonProperty("in_store_order_id")
    val inStoreOrderId: String
) {
}