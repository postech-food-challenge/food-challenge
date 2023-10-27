package br.com.fiap.postech.foodchallenge.adapters.persistence

import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {

    fun findByCpf(cpf: String): Customer?

}