package silly.manage.util

import org.apache.commons.codec.digest.DigestUtils
import java.util.*
import org.springframework.web.multipart.MultipartFile
import sun.misc.BASE64Decoder
import java.io.*
import javax.xml.bind.DatatypeConverter
import java.io.IOException
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.FileInputStream
import java.text.SimpleDateFormat



/**
 * 生成时间+时间戳
 */
fun cTimec(): String{
   return "${SimpleDateFormat("yyyyMMdd").format(Date())}${cdateTime()}"
}
/**
 * 生成创建时间
 */
fun cdateTime(): Long {
    return System.currentTimeMillis() / 1000
}

/**
 * 生成唯一的时间戳id，17位
 */
fun cTimeId(): String {
    val n = (1000 + Math.random() * (9999 - 1000 + 1)).toInt()
    val d = Date().time
    return "$n$d"
}

/**
 * 生成UUID,32位
 */
fun cUUID(): String {
    return UUID.randomUUID().toString().replace("-", "")
}

/**
 * MD5加密
 */
fun md5(text: String): String {
    return DigestUtils.md5Hex(text)
}

/**
 * 生成用户名
 */
fun cUsername(): String {
    return "用户${System.currentTimeMillis()}"
}

/**
 * 保存上传文件
 * @param file 文件
 * @param path 文件存放路径
 * @param fileName 源文件名，如果为空则使用原文件名
 * @return
 */
fun upload(file: MultipartFile, path: String, fileName: String = ""): String {

    val dest = File(path)
    if (!dest.exists()) {
        dest.mkdirs()
    }
    val fileNameN =
    // 使用自动生成的文件名
            if (fileName.isEmpty()) "${cTimeId()}${getSuffix(file.originalFilename ?: "")}"
            // 使用原文件名
            else fileName

    val realPath = File("$path/$fileNameN")
    return try {
        //保存文件
        file.transferTo(realPath.absoluteFile)
        fileNameN
    } catch (e: IllegalStateException) {
        // TODO Auto-generated catch block
        e.printStackTrace()
        ""
    } catch (e: IOException) {
        // TODO Auto-generated catch block
        e.printStackTrace()
        ""
    }
}

/**
 * 获取文件后缀
 * @param fileName
 * @return
 */
fun getSuffix(fileName: String): String {
    return fileName.substring(fileName.lastIndexOf("."))
}

/**
 * ase64字符串转化成图片
 */
fun GenerateImage(imgStr: String, outPath: String): String {
    if (imgStr.isEmpty()) return ""
    val strings = imgStr.split(",")
    val extension: String
    extension = when (strings[0]) {
        //check image's extension
        "data:image/jpeg;base64" -> "jpg"
        "data:image/png;base64" -> "png"
        else//should write cases for more images types
        -> "jpg"
    }
    //convert base64 string to binary data
    val data = DatatypeConverter.parseBase64Binary(strings[1])
    val path = "$outPath/${cTimeId()}.$extension"
    val file = File(path)
    try {
        val outputStream = BufferedOutputStream(FileOutputStream(file))
        outputStream.write(data)
        return path.substringAfterLast("/")
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return ""
}

/**
 * Base64加密
 */
fun encodeBase64(text: String): String {
    if (text.isEmpty()) return ""
    return Base64.getEncoder().encodeToString(text.toByteArray(charset("utf-8")))
}

/**
 * Base64解密
 */
fun decoderBase64(text: String): String {
    if (text.isEmpty()) return ""
    return String(Base64.getDecoder().decode(text))
}

/**
 * 复制文件
 */
fun copyFile(inFile: String, outFile: String) {
    val file1 = File(inFile)
    val file2 = File(outFile)

    val dest = File(outFile.replace("/${file2.name}", ""))
    if (!dest.exists()) {
        dest.mkdirs()
    }
    var fileOutputStream: FileOutputStream? = null
    var inputStream: InputStream? = null
    val bytes = ByteArray(1024)
    var temp = 0
    try {
        inputStream = FileInputStream(file1)
        fileOutputStream = FileOutputStream(file2)
        while (temp != -1) {
            fileOutputStream.write(bytes, 0, temp)
            fileOutputStream.flush()
            temp = inputStream.read(bytes)
        }

    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        if (inputStream != null) {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}

/**
 * 删除文件夹
 */
fun delFolder(folderPath: String) {
    try {
        delAllFile(folderPath) //删除完里面所有内容
        val myFilePath = File(folderPath)
        myFilePath.delete() //删除空文件夹
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

/**
 * 删除指定文件夹下所有文件
 */
fun delAllFile(path: String): Boolean {
    var flag = false
    val file = File(path)
    if (!file.exists()) {
        return flag
    }
    if (!file.isDirectory) {
        return flag
    }
    val tempList = file.list()
    var temp: File? = null
    for (i in tempList.indices) {
        if (path.endsWith(File.separator)) {
            temp = File(path + tempList[i])
        } else {
            temp = File(path + File.separator + tempList[i])
        }
        if (temp.isFile) {
            temp.delete()
        }
        if (temp.isDirectory) {
            delAllFile(path + "/" + tempList[i])//先删除文件夹里面的文件
            delFolder(path + "/" + tempList[i])//再删除空文件夹
            flag = true
        }
    }
    return flag
}