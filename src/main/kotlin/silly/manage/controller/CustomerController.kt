package silly.manage.controller

import org.springframework.web.bind.annotation.*
import silly.manage.entity.Customer
import silly.manage.entity.Data
import silly.manage.entity.Page
import silly.manage.service.CustomerService

/**
 * 客户控制器
 */
@RestController
class CustomerController(
        private val customerService: CustomerService
) {

    /**
     * 添加客户
     */
    @RequestMapping("/add_customer")
    fun addCommodity(formCustomer: Customer): Data {
        return try {
            customerService.addCustomer(formCustomer)
        } catch (e: Exception) {
            Data(error = "添加失败")
        }
    }

    /**
     * 修改客户
     */
    @RequestMapping("/update_customer")
    fun updateCommodity(formCustomer: Customer): Data {
        return try {
            customerService.updateCustomer(formCustomer)
        } catch (e: Exception) {
            Data(error = "修改失败")
        }
    }

    /**
     * 删除客户
     */
    @RequestMapping("/delete_customer")
    fun deleteCommodity(formCustomer: Customer): Data {
        return try {
            customerService.deleteCustomer(formCustomer)
        } catch (e: Exception) {
            Data(error = "删除失败")
        }
    }

    /**
     * 查找客户
     */
    @RequestMapping("/find_customer")
    fun findCommodity(fromPage: Page, formCustomer: Customer): Data {
        return try {
            customerService.findCustomer(fromPage, formCustomer)
        } catch (e: Exception) {
            Data(error = "查找失败")
        }
    }
}
