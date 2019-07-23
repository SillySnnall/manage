package silly.manage.controller

import org.springframework.web.bind.annotation.*
import silly.manage.entity.*
import silly.manage.service.OrderService
import silly.manage.service.UsersService

/**
 * 用户控制器
 */
@RestController
class UsersController(
        private val usersrService: UsersService
) {

    /**
     * 添加订单
     */
    @RequestMapping("/add_users")
    fun addUsers(formUsers: Users): Data {
        return try {
            usersrService.addUsers(formUsers)
        } catch (e: Exception) {
            Data(error = "添加失败")
        }
    }

    /**
     * 修改订单
     */
    @RequestMapping("/update_users")
    fun updateUsers(formUsers: Users): Data {
        return try {
            usersrService.updateUsers(formUsers)
        } catch (e: Exception) {
            Data(error = "修改失败")
        }
    }

    /**
     * 删除订单
     */
    @RequestMapping("/delete_users")
    fun deleteUsers(formUsers: Users): Data {
        return try {
            usersrService.deleteUsers(formUsers)
        } catch (e: Exception) {
            Data(error = "删除失败")
        }
    }

    /**
     * 查找订单
     */
    @RequestMapping("/find_users")
    fun findUsers(fromPage: Page, formUsers: Users): Data {
        return try {
            usersrService.findUsers(fromPage, formUsers)
        } catch (e: Exception) {
            Data(error = "查找失败")
        }
    }

    /**
     * 查找订单
     */
    @RequestMapping("/login")
    fun login(formUsers: Users): Data {
        return try {
            usersrService.login(formUsers)
        } catch (e: Exception) {
            Data(error = "查找失败")
        }
    }
}
