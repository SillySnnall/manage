package silly.manage.dao

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import silly.manage.entity.Commodity
import silly.manage.entity.Customer
import silly.manage.entity.Users

/**
 * 用户dao
 */
interface UsersDao : JpaRepository<Users, Long> {
    fun findByUsername(username: String): Users?
    fun findByUsernameAndPassword(username: String,password: String): Users?
    fun findByCode(code: String): Users?
    fun findByNameLike(name: String): List<Users>?
}
