package silly.manage

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import silly.manage.dao.CommodityDao
import silly.manage.entity.Commodity
import silly.manage.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
class ManageApplicationTests {

    @Autowired
    private lateinit var commodityDao: CommodityDao


    @Test
    fun contextLoads() {
//        commodityDao.save(Commodity(cUUID(),"qweq","1010g","1x12","广州",
//                "1232432445","180天",12.6,20.6,"",cdateTime()))
        commodityDao.save(Commodity(id = 1,priceIn = 100.9))
    }

}
