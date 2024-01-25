package br.com.fiap.postech.foodchallenge.application.usecases.customer

import br.com.fiap.postech.foodchallenge.application.gateways.CustomerGateway
import br.com.fiap.postech.foodchallenge.domain.entities.CPF
import br.com.fiap.postech.foodchallenge.domain.entities.Customer
import br.com.fiap.postech.foodchallenge.domain.exceptions.CustomerAlreadyRegisteredException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class RegisterCustomerInteractTest {

    private lateinit var gateway: CustomerGateway
    private lateinit var interactor: RegisterCustomerInteract

    @BeforeEach
    fun setUp() {
        gateway = mock()
        interactor = RegisterCustomerInteract(gateway)
    }

    @Test
    fun `should successfully register a new customer`() {
        val newCustomer = Customer(cpf = CPF("11122233344"), name = "Nezuko Kamado", email = "nezuko@kamado.com")
        whenever(gateway.findByCpf("111.222.333-44")).thenReturn(null)

        interactor.registerCustomer(newCustomer)

        verify(gateway).create(newCustomer)
    }

    @Test
    fun `should throw CustomerAlreadyRegisteredException for already existing customer`() {
        val existingCustomer =
            Customer(cpf = CPF("55566677788"), name = "Zenitsu Agatsuma", email = "zenitsu@agatsuma.com")
        whenever(gateway.findByCpf(anyString())).thenReturn(existingCustomer)

        assertThrows<CustomerAlreadyRegisteredException> {
            interactor.registerCustomer(existingCustomer)
        }

        verify(gateway, never()).create(existingCustomer)
    }
}
