package br.com.fiap.postech.foodchallenge.infrastructure.gateways

import br.com.fiap.postech.foodchallenge.application.gateways.CustomerGateway
import br.com.fiap.postech.foodchallenge.domain.entities.Customer
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.CustomerRepository
import br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities.CustomerEntity

class CustomerRepositoryGateway(private val repository: CustomerRepository) : CustomerGateway {
    override fun findByCpf(cpf: String) =
        repository.findByCpf(cpf)
            ?.let { entity -> Customer.fromEntity(entity) }

    override fun create(customer: Customer) =
        CustomerEntity.fromDomain(customer)
            .let { domain -> repository.save(domain) }
            .let { entity -> Customer.fromEntity(entity) }
}