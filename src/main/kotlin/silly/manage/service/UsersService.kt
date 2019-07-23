package silly.manage.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import silly.manage.dao.CommodityDao
import silly.manage.dao.UsersDao
import silly.manage.entity.*
import silly.manage.util.cUUID
import silly.manage.util.cdateTime
import silly.manage.util.copyFile
import java.io.File

/**
 * 用户服务
 */
@Service
class UsersService(
        private val usersDao: UsersDao
) {

    fun isEmptys(users: Users): Data {
        if (users.name.isEmpty()) return Data(error = "用户名称为空")
        if (users.phone.isEmpty()) return Data(error = "用户手机为空")
        if (users.username.isEmpty()) return Data(error = "用户账号为空")
        if (users.password.isEmpty()) return Data(error = "用户密码为空")
        return Data(code = 0)
    }

    /**
     * 添加用户
     */
    fun addUsers(users: Users): Data {
        val emptys = isEmptys(users)
        if (emptys.code == -1) return emptys

        val findByUsername = usersDao.findByUsername(users.username)
        if (findByUsername != null) return Data(error = "用户账号重复")

        users.code = cUUID() + cdateTime()
        users.createTime = cdateTime()

        usersDao.save(users)
        return Data(users, 0)
    }

    /**
     * 修改用户
     */
    fun updateUsers(users: Users): Data {
        val emptys = isEmptys(users)
        if (emptys.code == -1) return emptys

        usersDao.findByCode(users.code) ?: return Data(error = "用户不存在")

        usersDao.save(users)
        return Data(users, 0)
    }


    /**
     * 删除用户
     */
    fun deleteUsers(users: Users): Data {

        usersDao.findByCode(users.code) ?: return Data(error = "用户不存在")

        usersDao.delete(users)
        return Data("删除成功", 0)
    }

    /**
     * 查找用户
     */
    fun findUsers(fromPage: Page, users: Users): Data {
        val usersList: List<Users>
        if (users.name.isEmpty()) {
            val sort = Sort(Sort.Direction.DESC, "createTime")
            val pageable = PageRequest.of(fromPage.page, fromPage.size, sort)
            usersList = usersDao.findAll(pageable).content
        } else {
            usersList = usersDao.findByNameLike("%${users.name}%")!!
        }
        return Data(usersList, 0)
    }

    /**
     * 登录
     */
    fun login(users: Users): Data {

        if (users.username.isEmpty()) return Data(error = "用户账号为空")
        if (users.password.isEmpty()) return Data(error = "用户密码为空")
        val findByUsername = usersDao.findByUsernameAndPassword(users.username,users.password) ?: return Data(error = "账号密码错误")

        return Data(findByUsername, 0)
    }
}
