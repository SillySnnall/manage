package silly.manage.service

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
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
        private val orderItemDao: OrderItemDao

) {

    fun isEmptys(order: Orders): Data {
        if (order.customerCode.isEmpty()) return Data("客户编码为空", -1)
        if (order.num == 0L) return Data("订单总数为空", -1)
        if (order.price == 0.0) return Data("订单总金额为空", -1)
        return Data()
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
        for (orderItem in order.orderItemList) {
            if (orderItem.commodityCode.isEmpty()) return Data("商品编码为空", -1)
            if (orderItem.num == 0L) return Data("商品数量为空", -1)
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
        return Data(order)
    }

    /**
     * 修改订单
     */
    @Transactional
    fun updateOrder(order: Orders): Data {
        val emptys = isEmptys(order)
        if (emptys.code == -1) return emptys

        val findByCode = ordersDao.findByCode(order.code) ?: return Data("订单不存在", -1)

        // 删除这个订单下的所有商品，重新添加
        orderItemDao.deleteAll(orderItemDao.findByOrderCode(findByCode.code))

        for (orderItem in order.orderItemList) {
            if (orderItem.commodityCode.isEmpty()) return Data("商品编码为空", -1)
            if (orderItem.num == 0L) return Data("商品数量为空", -1)
            orderItem.code = cUUID() + cdateTime()
            orderItem.orderCode = findByCode.code
            orderItem.createTime = findByCode.createTime
            orderItemDao.save(orderItem)
        }

        findByCode.num = order.num
        findByCode.price = order.price
        findByCode.priceUp = money2Up(order.price)

        ordersDao.save(findByCode)
        return Data(findByCode)
    }


    /**
     * 删除订单
     */
    @Transactional
    fun deleteOrder(order: Orders): Data {

        val findByCode = ordersDao.findByCode(order.code) ?: return Data("订单不存在", -1)

        orderItemDao.deleteAll(orderItemDao.findByOrderCode(findByCode.code))

        ordersDao.delete(findByCode)
        return Data("删除成功")
    }

    /**
     * 查找订单
     */
    fun findOrder(fromPage: Page, order: Orders): Data {

        val pageable = PageRequest.of(fromPage.page, fromPage.size)

        val findAll = ordersDao.findAll(pageable).content

        return Data(findAll)
    }

    /**
     * 查找订单详情
     */
    fun findOrderItem(order: Orders): Data {

        if (order.code.isEmpty()) return Data("订单编码为空", -1)

        val findAll = orderItemDao.findByOrderCode(order.code)

        return Data(findAll)
    }
}
