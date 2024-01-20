package br.com.fiap.postech.foodchallenge.infrastructure.controller.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class CreateCustomerRequest(
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
