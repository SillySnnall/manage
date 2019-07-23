package silly.manage.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.google.gson.Gson
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * 用户
 */
@Entity
data class Users(
        var code: String = "",// 用户编码
        var name: String = "",// 用户名称
        var phone: String = "",// 用户手机号
        var username: String = "",// 用户账号
        var password: String = "",// 用户密码
        var authority: String = "",// 用户权限
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

//CREATE TABLE `users` (
//`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
//`code` varchar(42) NOT NULL COMMENT '用户编码',
//`name` varchar(5) NOT NULL COMMENT '用户名称',
//`phone` varchar(11) NOT NULL COMMENT '用户手机号',
//`username` varchar(18) NOT NULL COMMENT '用户账号',
//`password` varchar(18) NOT NULL COMMENT '用户密码',
//`authority` varchar(32) NOT NULL COMMENT '用户权限',
//`create_time` int(10) NOT NULL COMMENT '创建时间',
//PRIMARY KEY (`id`)
//) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;