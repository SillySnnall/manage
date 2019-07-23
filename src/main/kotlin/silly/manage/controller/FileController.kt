package silly.manage.controller

import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import silly.manage.entity.Data
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
            Data(error = "上传图片失败")
        }
    }
}
