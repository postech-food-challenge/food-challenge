package br.com.fiap.postech.foodchallenge.application.domain.services

import br.com.fiap.postech.foodchallenge.adapters.controller.dto.CustomerRequest
import br.com.fiap.postech.foodchallenge.adapters.persistence.CustomerRepository
import br.com.fiap.postech.foodchallenge.application.configuration.toDomain
import br.com.fiap.postech.foodchallenge.application.domain.exceptions.CustomerAlreadyRegisteredException
import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.Customer
import org.springframework.stereotype.Service

@Service
class CustomerService(private val repository: CustomerRepository) {

    fun registerCustomer(customer: CustomerRequest): Customer {
        repository.findByCpf(customer.cpf)?.let { throw CustomerAlreadyRegisteredException(customer.cpf) }

        return repository.save(customer.toDomain())
    }

    fun getCustomerByCpf(cpf: String): Customer? {
        return repository.findByCpf(cpf)
    }
}