package br.com.fiap.postech.foodchallenge.application.domain.model.aggregates

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

@Entity
data class Customer(
    @Id @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,

    @field:NotBlank(message = "CPF cannot be blank")
    @field:Pattern(
        regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
        message = "CPF format is invalid. Expected format: 111.111.111-11"
    )
    val cpf: String,

    @field:NotBlank(message = "Name cannot be blank")
    val name: String,

    @field:NotBlank(message = "Email cannot be blank")
    @field:Email(message = "Invalid email format")
    val email: String
)