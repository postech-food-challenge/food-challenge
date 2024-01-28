package br.com.fiap.postech.foodchallenge.infrastructure.persistence

import br.com.fiap.postech.foodchallenge.infrastructure.persistence.entities.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<OrderEntity, Long> {

    fun findByStatus(status: String): List<OrderEntity>

    @Query("SELECT o FROM OrderEntity o WHERE o.status NOT IN ('COMPLETED', 'CANCELED') ORDER BY CASE o.status WHEN 'READY' THEN 1 WHEN 'IN_PREPARATION' THEN 2 WHEN 'RECEIVED' THEN 3 ELSE 4 END, o.createdAt")
    fun findActiveOrdersSorted(): List<OrderEntity>
}