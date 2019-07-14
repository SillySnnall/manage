package silly.manage.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.google.gson.Gson
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * 商品
 */
@Entity
data class Commodity(
        var code: String = "",// 商品编码
        var name: String = "",// 商品名称
        var weight: String = "",// 商品重量
        var specifications: String = "",// 商品规格
        var home: String = "",// 出产地
        var barCode: String = "",// 条形码
        var expire: String = "",// 保质期
        var priceIn: Double = 0.0,// 进货价格
        var priceOut: Double = 0.0,// 出货价格
        var imgUrl: String = "",// 商品图片
        var createTime: Long = 0,// 创建时间
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonIgnore // 过滤json返回字段
        var id: Long? = null// 自增id
        // 非数据库字段
) {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}

//CREATE TABLE `commodity` (
//`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
//`code` varchar(32) NOT NULL COMMENT '商品编码',
//`name` varchar(255) NOT NULL COMMENT '商品名称',
//`weight` varchar(11) NOT NULL COMMENT '商品重量',
//`specifications` varchar(11) NOT NULL COMMENT '商品规格',
//`home` varchar(11) NOT NULL COMMENT '出产地',
//`bar_code` varchar(15) NOT NULL COMMENT '条形码',
//`expire` varchar(5) NOT NULL COMMENT '保质期',
//`price_in` decimal(10,2) NOT NULL COMMENT '进货价格',
//`price_out` decimal(10,2) NOT NULL COMMENT '出货价格',
//`img_url` varchar(255) NOT NULL COMMENT '商品图片',
//`create_time` int(10) NOT NULL COMMENT '创建时间',
//PRIMARY KEY (`id`)
//) ENGINE=InnoDB DEFAULT CHARSET=utf8;