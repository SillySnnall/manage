package silly.manage.entity

import com.google.gson.Gson

data class Data(var data: Any = Any(), var code: Int = 0) {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}