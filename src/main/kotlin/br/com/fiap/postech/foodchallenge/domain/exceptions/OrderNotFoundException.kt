package br.com.fiap.postech.foodchallenge.domain.exceptions

class OrderNotFoundException(orderId: Long): RuntimeException("Order with ID: $orderId not found.") {
}