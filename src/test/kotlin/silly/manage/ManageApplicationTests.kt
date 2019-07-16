package silly.manage

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import silly.manage.dao.CommodityDao
import silly.manage.dao.OrdersDao
import silly.manage.entity.OrderItem
import silly.manage.entity.Orders
import silly.manage.service.OrderService
import silly.manage.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
class ManageApplicationTests {

    @Autowired
    private lateinit var commodityDao: CommodityDao
    @Autowired
    private lateinit var ordersDao: OrdersDao
    @Autowired
    private lateinit var orderService: OrderService

    @Test
    fun contextLoads() {
//        commodityDao.save(Commodity(cUUID() + cdateTime(),"qweq","1010g","1x12","广州",
//                "1232432445","180天",12.6,20.6,"",cdateTime()))
//        commodityDao.save(Commodity(id = 1,priceIn = 100.9))

//        println("qqqqqqqqqqqqqqqqqqqqq-----${cTimec()}")

//        orderService.addOrder(Orders(customerCode = "31212", num = 14, price = 523.56, orderItemList = arrayListOf(
//                OrderItem(commodityCode="adsa321555",num = 4,price = 2.3,createTime = cdateTime()),
//                OrderItem(commodityCode="adsarr",num = 16,price = 32.53,createTime = cdateTime()),
//                OrderItem(commodityCode="ad332dxcc12",num = 1,price = 222.3,createTime = cdateTime())
//        )))
        orderService.updateOrder(Orders(customerCode = "31212", num = 14, price = 523.56, orderItemList = arrayListOf(
                OrderItem(commodityCode="adsa321555",num = 4,price = 2.3,createTime = cdateTime()),
                OrderItem(commodityCode="adsarr",num = 16,price = 32.53,createTime = cdateTime()),
                OrderItem(commodityCode="ad332dxcc12",num = 1,price = 222.3,createTime = cdateTime())
        )))

    }
}
