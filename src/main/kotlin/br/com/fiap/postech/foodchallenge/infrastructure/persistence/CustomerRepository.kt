package br.com.fiap.postech.foodchallenge.infrastructure.persistence

import br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities.CustomerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<CustomerEntity, Long> {

    fun findByCpf(cpf: String): CustomerEntity?

}