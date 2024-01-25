package br.com.fiap.postech.foodchallenge.infrastructure.controller.customer

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateCustomerRequest(
    @field:NotBlank(message = "CPF cannot be blank")
    @field:Size(min = 11, max = 11, message = "CPF must be exactly 11 characters long")
    val cpf: String,

    @field:NotBlank(message = "Name cannot be blank")
    val name: String,

    @field:NotBlank(message = "Email cannot be blank")
    @field:Email(message = "Invalid email format")
    val email: String
)
