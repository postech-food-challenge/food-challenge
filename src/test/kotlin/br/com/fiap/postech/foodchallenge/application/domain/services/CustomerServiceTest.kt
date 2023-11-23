package br.com.fiap.postech.foodchallenge.application.domain.services

import br.com.fiap.postech.foodchallenge.adapters.controller.dto.CustomerRequest
import br.com.fiap.postech.foodchallenge.adapters.persistence.CustomerRepository
import br.com.fiap.postech.foodchallenge.application.configuration.toDomain
import br.com.fiap.postech.foodchallenge.application.domain.exceptions.CustomerAlreadyRegisteredException
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
        val existingCustomerRequest =
            CustomerRequest(name = "Tanjiro Kamado", cpf = "123.456.789-10", email = "tanjiro@example.com")
        whenever(customerRepository.findByCpf(existingCustomerRequest.cpf)).thenReturn(existingCustomerRequest.toDomain())

        assertThrows<CustomerAlreadyRegisteredException> {
            customerService.registerCustomer(existingCustomerRequest)
        }
        verify(customerRepository, never()).save(any())
    }

    @Test
    fun `registerCustomer - when customer is new should save and return the customer`() {
        val newCustomerRequest =
            CustomerRequest(name = "Nezuko Kamado", cpf = "111.222.333-44", email = "nezuko@example.com")
        whenever(customerRepository.findByCpf(newCustomerRequest.cpf)).thenReturn(null)
        whenever(customerRepository.save(newCustomerRequest.toDomain())).thenReturn(
            newCustomerRequest.toDomain().copy(id = 2L)
        )

        val result = customerService.registerCustomer(newCustomerRequest)

        verify(customerRepository).save(newCustomerRequest.toDomain())
        assert(result.id == 2L)
        assert(result.name == "Nezuko Kamado")
        assert(result.cpf == "111.222.333-44")
        assert(result.email == "nezuko@example.com")
    }
}