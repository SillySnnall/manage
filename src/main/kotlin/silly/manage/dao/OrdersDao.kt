package silly.manage.dao

import org.springframework.data.jpa.repository.JpaRepository
import silly.manage.entity.Orders

/**
 * 订单dao
 */
interface OrdersDao : JpaRepository<Orders, Long> {
    fun findByCode(code: String): Orders?
//    fun findByUserIdAndTitle(userId: String, title: String): Commodity?
//    fun findByType(type: Int, pageable: Pageable): List<Commodity>
}
