package br.com.fiap.postech.foodchallenge.infrastructure.gateways

import br.com.fiap.postech.foodchallenge.application.gateways.MercadoPagoGateway
import br.com.fiap.postech.foodchallenge.infrastructure.client.mercadopago.MercadoPagoRequest
import br.com.fiap.postech.foodchallenge.infrastructure.client.mercadopago.MercadoPagoResponse

class MercadoPagoClientGateway : MercadoPagoGateway {
    override fun createPayment(request: MercadoPagoRequest): MercadoPagoResponse {
        return MercadoPagoResponse(
            qrData = "00020101021243650016COM.MERCADOLIBRE02013063638f1192a-5fd1-4180-a180-8bcae3556bc35204000053039865802BR5925IZABEL AAAA DE MELO6007BARUERI62070503***63040B6D",
            inStoreOrderId = "d4e8ca59-3e1d-4c03-b1f6-580e87c654a"
        )
    }
}