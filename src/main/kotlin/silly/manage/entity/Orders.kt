package silly.manage.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.google.gson.Gson
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * 订单
 */
@Entity
data class Orders(
        var code: String = "",// 订单编码
        var customerCode: String = "",// 客户编码
        var orderNum: String = "",// 单据编号
        var num: Long = 0,// 订单总数
        var price: Double = 0.0,// 订单总金额
        var priceUp: String = "",// 订单总金额大写
        var state: Int = 0,// 订单状态（0未过账，1过账）
        var createTime: Long = 0,// 创建时间
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonIgnore // 过滤json返回字段
        var id: Long? = null,// 自增id
        // 非数据库字段
        @javax.persistence.Transient
        var shop: String = "",// 商店名称
        @javax.persistence.Transient
        var orderItemListJson: String = ""// 订单列表json
) {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}

//CREATE TABLE `order` (
//`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
//`code` varchar(42) NOT NULL COMMENT '订单编码',
//`customer_code` varchar(42) NOT NULL COMMENT '客户编码',
//`order_num` varchar(18) NOT NULL COMMENT '单据编号',
//`num` int(11) NOT NULL COMMENT '订单总数',
//`price` decimal(10,2) NOT NULL COMMENT '订单总金额',
//`price_up` varchar(25) NOT NULL COMMENT '订单总金额大写',
//`state` int(1) NOT NULL COMMENT '订单状态（0未过账，1过账）',
//`create_time` int(10) NOT NULL COMMENT '创建时间',
//PRIMARY KEY (`id`)
//) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;