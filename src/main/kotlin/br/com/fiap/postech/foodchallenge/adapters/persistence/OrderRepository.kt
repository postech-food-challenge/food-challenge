package br.com.fiap.postech.foodchallenge.adapters.persistence

import br.com.fiap.postech.foodchallenge.application.domain.model.aggregates.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long> {
}