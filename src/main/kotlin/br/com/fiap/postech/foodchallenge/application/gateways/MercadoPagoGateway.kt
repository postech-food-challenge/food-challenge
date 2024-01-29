package br.com.fiap.postech.foodchallenge.application.gateways

import br.com.fiap.postech.foodchallenge.infrastructure.client.mercadopago.MercadoPagoRequest
import br.com.fiap.postech.foodchallenge.infrastructure.client.mercadopago.MercadoPagoResponse

interface MercadoPagoGateway {

    fun createPayment(request: MercadoPagoRequest): MercadoPagoResponse
}