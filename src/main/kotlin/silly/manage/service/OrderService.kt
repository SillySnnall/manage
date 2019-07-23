package silly.manage.service

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import silly.manage.dao.CommodityDao
import silly.manage.dao.CustomerDao
import silly.manage.dao.OrdersDao
import silly.manage.dao.OrderItemDao
import silly.manage.entity.*
import silly.manage.util.cTimec
import silly.manage.util.cUUID
import silly.manage.util.cdateTime
import silly.manage.util.money2Up
import javax.transaction.Transactional

/**
 * 订单服务
 */
@Service
class OrderService(
        private val ordersDao: OrdersDao,
        private val orderItemDao: OrderItemDao,
        private val customerDao: CustomerDao,
        private val commodityDao: CommodityDao

) {

    fun isEmptys(order: Orders): Data {
        if (order.customerCode.isEmpty()) return Data(error = "客户编码为空")
        if (order.num == 0L) return Data(error = "订单总数为空")
        if (order.price == 0.0) return Data(error = "订单总金额为空")
        return Data(code = 0)
    }

    /**
     * 添加订单
     */
    @Transactional
    fun addOrder(order: Orders): Data {
        val emptys = isEmptys(order)
        if (emptys.code == -1) return emptys

        val code = cUUID() + cdateTime()
        val cdateTime = cdateTime()

        val orderItemList = Gson().fromJson<List<OrderItem>>(order.orderItemListJson, object : TypeToken<List<OrderItem>>() {}.type)

        for (orderItem in orderItemList) {
            if (orderItem.commodityCode.isEmpty()) return Data(error = "商品编码为空")
            if (orderItem.num == 0L) return Data(error = "商品数量为空")
            orderItem.code = cUUID() + cdateTime()
            orderItem.orderCode = code
            orderItem.createTime = cdateTime
            orderItemDao.save(orderItem)
        }

        order.code = code
        order.orderNum = cTimec()
        order.priceUp = money2Up(order.price)
        order.createTime = cdateTime

        ordersDao.save(order)
        return Data(order, 0)
    }

    /**
     * 修改订单
     */
    @Transactional
    fun updateOrder(order: Orders): Data {
        val emptys = isEmptys(order)
        if (emptys.code == -1) return emptys

        val findByCode = ordersDao.findByCode(order.code) ?: return Data(error = "订单不存在")

        // 删除这个订单下的所有商品，重新添加
        orderItemDao.deleteAll(orderItemDao.findByOrderCode(findByCode.code))

        val orderItemList = Gson().fromJson<List<OrderItem>>(order.orderItemListJson, object : TypeToken<List<OrderItem>>() {}.type)

        for (orderItem in orderItemList) {
            if (orderItem.commodityCode.isEmpty()) return Data(error = "商品编码为空")
            if (orderItem.num == 0L) return Data(error = "商品数量为空")
            orderItem.code = cUUID() + cdateTime()
            orderItem.orderCode = findByCode.code
            orderItem.createTime = findByCode.createTime
            orderItemDao.save(orderItem)
        }

        findByCode.num = order.num
        findByCode.price = order.price
        findByCode.priceUp = money2Up(order.price)
        findByCode.state = order.state

        ordersDao.save(findByCode)
        return Data(findByCode, 0)
    }


    /**
     * 删除订单
     */
    @Transactional
    fun deleteOrder(order: Orders): Data {

        val findByCode = ordersDao.findByCode(order.code) ?: return Data(error = "订单不存在")

        orderItemDao.deleteAll(orderItemDao.findByOrderCode(findByCode.code))

        ordersDao.delete(findByCode)
        return Data("删除成功", 0)
    }

    /**
     * 查找订单
     */
    fun findOrder(fromPage: Page, order: Orders): Data {
        val orderList: List<Orders>
        if (order.shop.isEmpty()) {
            val sort = Sort(Sort.Direction.DESC, "createTime")
            val pageable = PageRequest.of(fromPage.page, fromPage.size, sort)
            orderList = ordersDao.findByState(order.state, pageable)!!
        } else {
            val findByShopLike = customerDao.findByShopLike("%${order.shop}%")!!
            orderList = arrayListOf()
            for (customer in findByShopLike) {
                orderList.addAll(ordersDao.findByCustomerCodeAndState(customer.code, order.state)!!)
            }
        }

        for (orders in orderList) {
            val customer = customerDao.findByCode(orders.customerCode)
            orders.shop = customer?.shop!!
        }
        return Data(orderList, 0)
    }

    /**
     * 查找订单详情
     */
    fun findOrderItem(order: Orders): Data {

        if (order.code.isEmpty()) return Data(error = "订单编码为空")

        val findAll = orderItemDao.findByOrderCode(order.code)

        for (orderItem in findAll) {
            val commodity = commodityDao.findByCode(orderItem.commodityCode)
            orderItem.name = commodity?.name!!
        }

        return Data(findAll, 0)
    }
}
