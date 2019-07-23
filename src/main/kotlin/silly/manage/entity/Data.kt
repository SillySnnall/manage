package silly.manage.entity

import com.google.gson.Gson

data class Data(var data: Any? = null, var code: Int = -1, var error: String = "") {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}
