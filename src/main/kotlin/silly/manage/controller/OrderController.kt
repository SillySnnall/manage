package silly.manage.controller

import org.springframework.web.bind.annotation.*
import silly.manage.entity.*
import silly.manage.service.OrderService

/**
 * 订单控制器
 */
@RestController
class OrderController(
        private val orderService: OrderService
) {

    /**
     * 添加订单
     */
    @RequestMapping("/add_order")
    fun addOrder(formOrder: Orders): Data {
        return try {
            orderService.addOrder(formOrder)
        } catch (e: Exception) {
            Data("添加失败", -1)
        }
    }

    /**
     * 修改订单
     */
    @RequestMapping("/update_order")
    fun updateOrder(formOrder: Orders): Data {
        return try {
            orderService.updateOrder(formOrder)
        } catch (e: Exception) {
            Data("修改失败", -1)
        }
    }

    /**
     * 删除订单
     */
    @RequestMapping("/delete_order")
    fun deleteOrder(formOrder: Orders): Data {
        return try {
            orderService.deleteOrder(formOrder)
        } catch (e: Exception) {
            Data("删除失败", -1)
        }
    }

    /**
     * 查找订单
     */
    @RequestMapping("/find_order")
    fun findOrder(fromPage: Page, formOrder: Orders): Data {
        return try {
            orderService.findOrder(fromPage, formOrder)
        } catch (e: Exception) {
            Data("查找失败", -1)
        }
    }

    /**
     * 查找订单详情
     */
    @RequestMapping("/find_order_item")
    fun findOrderItem(formOrder: Orders): Data {
        return try {
            orderService.findOrderItem(formOrder)
        } catch (e: Exception) {
            Data("查找失败", -1)
        }
    }
}
