package silly.manage.controller

import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import silly.manage.entity.Commodity
import silly.manage.entity.Data
import silly.manage.entity.Page
import silly.manage.service.CommodityService
import silly.manage.service.UpdateService

/**
 * 文件控制器
 */
@RestController
class FileController(
        private val updateService: UpdateService
) {

    /**
     * 上传图片
     */
    @RequestMapping("/upload_img")
    fun uploadImg(@RequestParam("img_file") file: MultipartFile): Data {
        return try {
            updateService.uploadImg(file)
        } catch (e: Exception) {
            Data("上传图片失败", -1)
        }
    }
}
