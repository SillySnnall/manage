package silly.manage.dao

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import silly.manage.entity.Commodity
import silly.manage.entity.Customer

/**
 * 客户dao
 */
interface CustomerDao : JpaRepository<Customer, Long> {
    fun findByCode(code: String): Customer?
//    fun findByUserIdAndTitle(userId: String, title: String): Commodity?
//    fun findByType(type: Int, pageable: Pageable): List<Commodity>
}
