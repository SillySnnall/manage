package silly.manage.service

import org.springframework.stereotype.Service
import org.springframework.data.domain.PageRequest
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
        if (customer.shop.isEmpty()) return Data("商店名字为空", -1)

        val customerN = Customer(cUUID()+cdateTime(), customer.shop, customer.name, customer.phone, cdateTime())

        customerDao.save(customerN)
        return Data(customerN)
    }

    /**
     * 修改客户
     */
    fun updateCustomer(customer: Customer): Data {
        if (customer.shop.isEmpty()) return Data("商店名字为空", -1)

        val findByCode = customerDao.findByCode(customer.code) ?: return Data("客户不存在", -1)

        findByCode.shop = customer.shop
        findByCode.name = customer.name
        findByCode.phone = customer.phone

        customerDao.save(findByCode)
        return Data(findByCode)
    }


    /**
     * 删除客户
     */
    fun deleteCustomer(customer: Customer): Data {

        val findByCode = customerDao.findByCode(customer.code) ?: return Data("客户不存在", -1)

        customerDao.delete(findByCode)
        return Data("删除成功")
    }

    /**
     * 查找客户
     */
    fun findCustomer(fromPage: Page, customer: Customer): Data {

        val pageable = PageRequest.of(fromPage.page, fromPage.size)

        val findAll = customerDao.findAll(pageable).content

        return Data(findAll)
    }

}
