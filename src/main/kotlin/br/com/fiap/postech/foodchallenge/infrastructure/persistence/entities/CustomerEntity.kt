package br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities

import br.com.fiap.postech.foodchallenge.domain.entities.Customer
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "customers")
data class CustomerEntity(
    @Id @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,
    val cpf: String,
    val name: String,
    val email: String
) {
    companion object {
        fun fromDomain(domainObject: Customer) =
            CustomerEntity(cpf = domainObject.cpf.value, name = domainObject.name, email = domainObject.email)
    }
}