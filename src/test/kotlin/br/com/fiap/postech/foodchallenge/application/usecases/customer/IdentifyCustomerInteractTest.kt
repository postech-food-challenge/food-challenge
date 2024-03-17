package br.com.fiap.postech.foodchallenge.application.usecases.customer

import br.com.fiap.postech.foodchallenge.application.gateways.CustomerGateway
import br.com.fiap.postech.foodchallenge.domain.entities.CPF
import br.com.fiap.postech.foodchallenge.domain.entities.Customer
import br.com.fiap.postech.foodchallenge.domain.exceptions.CustomerNotFoundException
import br.com.fiap.postech.foodchallenge.domain.exceptions.InvalidCpfException
import br.com.fiap.postech.foodchallenge.infrastructure.controller.customer.IdentifyCustomerRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IdentifyCustomerInteractTest {

    private lateinit var interactor: IdentifyCustomerInteract

    @BeforeEach
    fun setUp() {
        interactor = IdentifyCustomerInteract()
    }

//    @Test
//    fun `should return Customer for valid CPF`() {
//        val cpf = "12345678901"
//        val expectedCharacter =
//            Customer(cpf = CPF("12345678901"), name = "Tanjiro Kamado", email = "tanjiro@kamado.com")
//        whenever(gateway.findByCpf(anyString())).thenReturn(expectedCharacter)
//
//        val result = interactor.identify(cpf)
//
//        assertEquals(expectedCharacter, result)
//    }

    @Test
    fun `should throw InvalidCpfException for invalid CPF format`() {
        val invalidCpf = "abcd123"

        assertThrows<InvalidCpfException> {
            interactor.identify(IdentifyCustomerRequest(invalidCpf, "Tanjiro", "tanjiro@kamado.com"))
        }
    }
}