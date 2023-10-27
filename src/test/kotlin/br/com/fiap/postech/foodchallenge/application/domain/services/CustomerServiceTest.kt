package br.com.fiap.postech.foodchallenge.application.domain.services

import br.com.fiap.postech.foodchallenge.adapters.persistence.CustomerRepository
import br.com.fiap.postech.foodchallenge.application.domain.exceptions.CustomerAlreadyRegisteredException
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.Customer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*

class CustomerServiceTest {

    private lateinit var customerRepository: CustomerRepository
    private lateinit var customerService: CustomerService

    @BeforeEach
    fun setUp() {
        customerRepository = mock()
        customerService = CustomerService(customerRepository)
    }

    @Test
    fun `registerCustomer - when customer with CPF already exists should throw exception`() {
        val existingCustomer =
            Customer(id = 1L, name = "Tanjiro Kamado", cpf = "123.456.789-10", email = "tanjiro@example.com")
        whenever(customerRepository.findByCpf(existingCustomer.cpf)).thenReturn(existingCustomer)

        assertThrows<CustomerAlreadyRegisteredException> {
            customerService.registerCustomer(existingCustomer)
        }
        verify(customerRepository, never()).save(any())
    }

    @Test
    fun `registerCustomer - when customer is new should save and return the customer`() {
        val newCustomer = Customer(name = "Nezuko Kamado", cpf = "111.222.333-44", email = "nezuko@example.com")
        whenever(customerRepository.findByCpf(newCustomer.cpf)).thenReturn(null)
        whenever(customerRepository.save(newCustomer)).thenReturn(newCustomer.copy(id = 2L))

        val result = customerService.registerCustomer(newCustomer)

        verify(customerRepository).save(newCustomer)
        assert(result.id == 2L)
        assert(result.name == "Nezuko Kamado")
        assert(result.cpf == "111.222.333-44")
        assert(result.email == "nezuko@example.com")
    }
}