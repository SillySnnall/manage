package silly.manage.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.google.gson.Gson
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * 客户
 */
@Entity
data class Customer(
        var code: String = "",// 客户编码
        var shop: String = "",// 商店名称
        var name: String = "",// 客户名称
        var phone: String = "",// 电话号码
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

//CREATE TABLE `customer` (
//`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
//`code` varchar(32) NOT NULL COMMENT '客户编码',
//`shop` varchar(255) NOT NULL COMMENT '商店名称',
//`name` varchar(11) NOT NULL COMMENT '客户名称',
//`phone` varchar(11) NOT NULL COMMENT '电话号码',
//`create_time` int(10) NOT NULL COMMENT '创建时间',
//PRIMARY KEY (`id`)
//) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;