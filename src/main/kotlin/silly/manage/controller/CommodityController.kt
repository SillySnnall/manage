package silly.manage.controller

import org.springframework.web.bind.annotation.*
import silly.manage.entity.Commodity
import silly.manage.entity.Data
import silly.manage.entity.Page
import silly.manage.service.CommodityService

/**
 * 商品控制器
 */
@RestController
class CommodityController(
        private val commodityService: CommodityService
) {

    /**
     * 添加商品
     */
    @RequestMapping("/add_commodity")
    fun addCommodity(formCommodity: Commodity): Data {
        return try {
            commodityService.addCommodity(formCommodity)
        } catch (e: Exception) {
            Data("添加失败", -1)
        }
    }

    /**
     * 修改商品
     */
    @RequestMapping("/update_commodity")
    fun updateCommodity(formCommodity: Commodity): Data {
        return try {
            commodityService.updateCommodity(formCommodity)
        } catch (e: Exception) {
            Data("修改失败", -1)
        }
    }

    /**
     * 删除商品
     */
    @RequestMapping("/delete_commodity")
    fun deleteCommodity(formCommodity: Commodity): Data {
        return try {
            commodityService.deleteCommodity(formCommodity)
        } catch (e: Exception) {
            Data("删除失败", -1)
        }
    }

    /**
     * 查找商品
     */
    @RequestMapping("/find_commodity")
    fun findCommodity(fromPage: Page, formCommodity: Commodity): Data {
        return try {
            commodityService.findCommodity(fromPage,formCommodity)
        } catch (e: Exception) {
            Data("查找失败", -1)
        }
    }
}
