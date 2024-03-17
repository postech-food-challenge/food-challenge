package br.com.fiap.postech.foodchallenge.infrastructure.controller.payment

class CreatePaymentResponse(
    val price: Int,
    val qrData: String,
    val inStoreOrderId: String
)