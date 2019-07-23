package silly.manage.dao

import org.springframework.data.jpa.repository.JpaRepository
import silly.manage.entity.Commodity
import silly.manage.entity.Customer

/**
 * 商品dao
 */
interface CommodityDao : JpaRepository<Commodity, Long> {
    fun findByBarCode(barCode: String): Commodity?
    fun findByCode(code: String): Commodity?
    fun findByNameLike(name: String): List<Commodity>?
}
