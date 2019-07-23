package silly.manage.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import silly.manage.entity.Data
import silly.manage.util.upload


@Service
class UpdateService {
    @Value("\${update.file.temp}")
    private lateinit var temp: String
    @Value("\${main.url}")
    private lateinit var mainUrl: String

    /**
     * 上传图片
     */
    fun uploadImg(file: MultipartFile): Data {
        val upload = upload(file, temp)
        if (upload.isEmpty()) return Data(error = "上传失败")
        return Data("$mainUrl/temp/$upload",0)
    }
}
