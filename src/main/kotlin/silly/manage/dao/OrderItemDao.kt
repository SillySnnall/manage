package silly.manage.dao

import org.springframework.data.jpa.repository.JpaRepository
import silly.manage.entity.OrderItem

/**
 * 订单条目dao
 */
interface OrderItemDao : JpaRepository<OrderItem, Long> {
//    fun findByCode(code: String): OrderItem?
    fun findByOrderCode(orderCode: String): List<OrderItem>
//    fun findByUserIdAndTitle(userId: String, title: String): Commodity?
//    fun findByType(type: Int, pageable: Pageable): List<Commodity>
}
