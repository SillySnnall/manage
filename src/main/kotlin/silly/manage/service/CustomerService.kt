package silly.manage.service

import org.springframework.stereotype.Service
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import silly.manage.dao.CustomerDao
import silly.manage.entity.Customer
import silly.manage.entity.Data
import silly.manage.entity.Page
import silly.manage.util.cUUID
import silly.manage.util.cdateTime

/**
 * 客户服务
 */
@Service
class CustomerService(
        private val customerDao: CustomerDao
) {

    /**
     * 添加客户
     */
    fun addCustomer(customer: Customer): Data {
        if (customer.shop.isEmpty()) return Data(error = "商店名字为空")

        val customerN = Customer(cUUID() + cdateTime(), customer.shop, customer.name, customer.phone, cdateTime())

        customerDao.save(customerN)
        return Data(customerN, 0)
    }

    /**
     * 修改客户
     */
    fun updateCustomer(customer: Customer): Data {
        if (customer.shop.isEmpty()) return Data(error = "商店名字为空")

        val findByCode = customerDao.findByCode(customer.code) ?: return Data(error = "客户不存在")

        findByCode.shop = customer.shop
        findByCode.name = customer.name
        findByCode.phone = customer.phone

        customerDao.save(findByCode)
        return Data(findByCode, 0)
    }


    /**
     * 删除客户
     */
    fun deleteCustomer(customer: Customer): Data {

        val findByCode = customerDao.findByCode(customer.code) ?: return Data(error = "客户不存在")

        customerDao.delete(findByCode)
        return Data("删除成功", 0)
    }

    /**
     * 查找客户
     */
    fun findCustomer(fromPage: Page, customer: Customer): Data {
        val customerList: List<Customer>
        if (customer.shop.isEmpty()) {
            val sort = Sort(Sort.Direction.DESC, "createTime")
            val pageable = PageRequest.of(fromPage.page, fromPage.size, sort)
            customerList = customerDao.findAll(pageable).content
        } else {
            customerList = customerDao.findByShopLike("%${customer.shop}%")!!
        }
        return Data(customerList, 0)
    }

}
