package br.com.fiap.postech.foodchallenge.domain.entities.order

data class OrderItem(
    val productId: Long,
    val quantity: Int,
    val observations: String? = null,
    val toGo: Boolean
) {
    companion object {
        fun create(productId: Long, quantity: Int, observations: String?, toGo: Boolean) =
            OrderItem(productId, quantity, observations, toGo)
    }
}