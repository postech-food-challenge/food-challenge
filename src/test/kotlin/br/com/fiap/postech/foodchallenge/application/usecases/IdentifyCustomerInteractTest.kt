package br.com.fiap.postech.foodchallenge.application.usecases

import br.com.fiap.postech.foodchallenge.application.domain.exceptions.InvalidCpfException
import br.com.fiap.postech.foodchallenge.application.gateways.CustomerGateway
import br.com.fiap.postech.foodchallenge.domain.entities.Customer
import br.com.fiap.postech.foodchallenge.domain.exceptions.CustomerNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IdentifyCustomerInteractTest {

    private lateinit var gateway: CustomerGateway
    private lateinit var interactor: IdentifyCustomerInteract

    @BeforeEach
    fun setUp() {
        gateway = mock()
        interactor = IdentifyCustomerInteract(gateway)
    }

    @Test
    fun `should return Customer for valid CPF`() {
        val cpf = "12345678901"
        val expectedCharacter = Customer(cpf = "123.456.789-01", name = "Tanjiro Kamado", email = "tanjiro@kamado.com")
        whenever(gateway.findByCpf("123.456.789-01")).thenReturn(expectedCharacter)

        val result = interactor.identify(cpf)

        assertEquals(expectedCharacter, result)
    }

    @Test
    fun `should throw InvalidCpfException for invalid CPF format`() {
        val invalidCpf = "abcd123"

        assertThrows<InvalidCpfException> {
            interactor.identify(invalidCpf)
        }
    }

    @Test
    fun `should throw CustomerNotFoundException for CPF not found`() {
        val cpf = "98765432100"
        whenever(gateway.findByCpf("987.654.321-00")).thenReturn(null)

        assertThrows<CustomerNotFoundException> {
            interactor.identify(cpf)
        }
    }
}