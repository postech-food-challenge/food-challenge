package br.com.fiap.postech.foodchallenge.infrastructure.controller.payment

import br.com.fiap.postech.foodchallenge.domain.entities.Payment

class CreatePaymentResponse(
    val price: Int,
    val qrData: String,
    val inStoreOrderId: String
)