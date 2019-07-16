package silly.manage.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.google.gson.Gson
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * 订单条目
 */
@Entity
data class OrderItem(
        var code: String = "",// 订单条目编码
        var orderCode: String = "",// 订单编码
        var commodityCode: String = "",// 商品编码
        var num: Long = 0,// 数量
        var price: Double = 0.0,// 单价
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

//CREATE TABLE `order_item` (
//`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
//`code` varchar(42) NOT NULL COMMENT '订单条目编码',
//`order_code` varchar(42) NOT NULL COMMENT '订单编码',
//`commodity_code` varchar(42) NOT NULL COMMENT '商品编码',
//`num` int(11) NOT NULL COMMENT '数量',
//`price` decimal(10,2) NOT NULL COMMENT '单价',
//`create_time` int(10) NOT NULL COMMENT '创建时间',
//PRIMARY KEY (`id`)
//) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;