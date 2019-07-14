package silly.manage.entity

data class Page(
        var size: Int = 30, // 每页显示条数
        var page: Int = 0 //当前页数
)