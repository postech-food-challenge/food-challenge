package br.com.fiap.postech.foodchallenge.application.domain.model.aggregates

import br.com.fiap.postech.foodchallenge.adapters.controller.dto.CustomerRequest
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id

@Entity
data class Customer(
    @Id @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,
    val cpf: String,
    val name: String,
    val email: String
) {
    companion object {
        fun createCustomer(request: CustomerRequest): Customer {
            return Customer(cpf = request.cpf, name = request.name, email = request.email)
        }
    }
}