package silly.manage.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import silly.manage.dao.CommodityDao
import silly.manage.entity.Commodity
import silly.manage.entity.Customer
import silly.manage.entity.Data
import silly.manage.entity.Page
import silly.manage.util.cUUID
import silly.manage.util.cdateTime
import silly.manage.util.copyFile
import java.io.File

/**
 * 商品服务
 */
@Service
class CommodityService(
        private val commodityDao: CommodityDao
) {

    @Value("\${update.file.temp}")
    private lateinit var temp: String
    @Value("\${update.file.commodity-image}")
    private lateinit var commodityImage: String

    fun isEmptys(commodity: Commodity): Data {
        if (commodity.name.isEmpty()) return Data(error = "商品名字为空")
        if (commodity.barCode.isEmpty()) return Data(error = "条形码为空")
        if (commodity.priceIn == 0.0) return Data(error = "进价为空")
        if (commodity.priceOut == 0.0) return Data(error = "正发价为空")
        if (commodity.priceOutDown == 0.0) return Data(error = "批发价为空")
        return Data(code = 0)
    }

    fun copyImg(commodity: Commodity) {
        if (commodity.imgUrl.isEmpty()) return
        // 复制临时图片到正式文件夹
        val name = File(commodity.imgUrl).name
        copyFile("$temp/$name", "$commodityImage/$name")
        commodity.imgUrl = commodity.imgUrl.replace("/temp/", "/commodityImage/")
    }

    /**
     * 添加商品
     */
    fun addCommodity(commodity: Commodity): Data {
        val emptys = isEmptys(commodity)
        if (emptys.code == -1) return emptys

        val findByBarCode = commodityDao.findByBarCode(commodity.barCode)
        if (findByBarCode != null) return Data(error = "条码重复")

        val commodityN = Commodity(cUUID() + cdateTime(), commodity.name, commodity.weight, commodity.specifications, commodity.home,
                commodity.barCode, commodity.expire, commodity.priceIn, commodity.priceOut, commodity.priceOutDown, commodity.imgUrl, cdateTime())

        copyImg(commodityN)

        commodityDao.save(commodityN)
        return Data(commodityN, 0)
    }

    /**
     * 修改商品
     */
    fun updateCommodity(commodity: Commodity): Data {
        val emptys = isEmptys(commodity)
        if (emptys.code == -1) return emptys

        val findByCode = commodityDao.findByCode(commodity.code) ?: return Data(error = "商品不存在")

        // 判断是否修改条码
        if (commodity.barCode != findByCode.barCode) {
            val findByBarCode = commodityDao.findByBarCode(commodity.barCode)
            if (findByBarCode != null) return Data(error = "条码重复")
        }

        // 复制临时图片到正式文件夹
        if (commodity.imgUrl != findByCode.imgUrl) {
            copyImg(commodity)
        }

        findByCode.name = commodity.name
        findByCode.weight = commodity.weight
        findByCode.specifications = commodity.specifications
        findByCode.home = commodity.home
        findByCode.barCode = commodity.barCode
        findByCode.expire = commodity.expire
        findByCode.priceIn = commodity.priceIn
        findByCode.priceOut = commodity.priceOut
        findByCode.imgUrl = commodity.imgUrl

        commodityDao.save(findByCode)
        return Data(commodity, 0)
    }


    /**
     * 删除商品
     */
    fun deleteCommodity(commodity: Commodity): Data {

        val findByCode = commodityDao.findByCode(commodity.code) ?: return Data(error = "商品不存在")

        commodityDao.delete(findByCode)
        return Data("删除成功", 0)
    }

    /**
     * 查找商品
     */
    fun findCommodity(fromPage: Page, commodity: Commodity): Data {
        val customerList: List<Commodity>
        if (commodity.name.isEmpty()) {
            val sort = Sort(Sort.Direction.DESC, "createTime")
            val pageable = PageRequest.of(fromPage.page, fromPage.size, sort)
            customerList = commodityDao.findAll(pageable).content
        } else {
            customerList = commodityDao.findByNameLike("%${commodity.name}%")!!
        }
        return Data(customerList, 0)
    }

}
