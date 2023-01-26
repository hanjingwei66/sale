package cn.iocoder.yudao.module.hq.service.customer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.hq.controller.admin.customer.vo.*;
import cn.iocoder.yudao.module.hq.dal.dataobject.customer.CustomerDO;
import cn.iocoder.yudao.module.hq.dal.mysql.customer.CustomerMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;

import static cn.hutool.core.util.RandomUtil.*;
import static cn.iocoder.yudao.module.hq.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
* {@link CustomerServiceImpl} 的单元测试类
*
* @author hanjingwei
*/
@Import(CustomerServiceImpl.class)
public class CustomerServiceImplTest extends BaseDbUnitTest {

    @Resource
    private CustomerServiceImpl customerService;

    @Resource
    private CustomerMapper customerMapper;

    @Test
    public void testCreateCustomer_success() {
        // 准备参数
        CustomerCreateReqVO reqVO = randomPojo(CustomerCreateReqVO.class);

        // 调用
        Long customerId = customerService.createCustomer(reqVO);
        // 断言
        assertNotNull(customerId);
        // 校验记录的属性是否正确
        CustomerDO customer = customerMapper.selectById(customerId);
        assertPojoEquals(reqVO, customer);
    }

    @Test
    public void testUpdateCustomer_success() {
        // mock 数据
        CustomerDO dbCustomer = randomPojo(CustomerDO.class);
        customerMapper.insert(dbCustomer);// @Sql: 先插入出一条存在的数据
        // 准备参数
        CustomerUpdateReqVO reqVO = randomPojo(CustomerUpdateReqVO.class, o -> {
            o.setId(dbCustomer.getId()); // 设置更新的 ID
        });

        // 调用
        customerService.updateCustomer(reqVO);
        // 校验是否更新正确
        CustomerDO customer = customerMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, customer);
    }

    @Test
    public void testUpdateCustomer_notExists() {
        // 准备参数
        CustomerUpdateReqVO reqVO = randomPojo(CustomerUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> customerService.updateCustomer(reqVO), CUSTOMER_NOT_EXISTS);
    }

    @Test
    public void testDeleteCustomer_success() {
        // mock 数据
        CustomerDO dbCustomer = randomPojo(CustomerDO.class);
        customerMapper.insert(dbCustomer);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbCustomer.getId();

        // 调用
        customerService.deleteCustomer(id);
       // 校验数据不存在了
       assertNull(customerMapper.selectById(id));
    }

    @Test
    public void testDeleteCustomer_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> customerService.deleteCustomer(id), CUSTOMER_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetCustomerPage() {
       // mock 数据
       CustomerDO dbCustomer = randomPojo(CustomerDO.class, o -> { // 等会查询到
           o.setCustomerName(null);
           o.setTelphone(null);
           o.setSex(null);
           o.setOrderMoney(null);
           o.setUnit(null);
           o.setLoginIp(null);
           o.setLoginDate(null);
           o.setCreateTime(null);
           o.setStatus(null);
           o.setEmail(null);
           o.setAllocationStatus(null);
       });
       customerMapper.insert(dbCustomer);
       // 测试 customerName 不匹配
       customerMapper.insert(cloneIgnoreId(dbCustomer, o -> o.setCustomerName(null)));
       // 测试 telphone 不匹配
       customerMapper.insert(cloneIgnoreId(dbCustomer, o -> o.setTelphone(null)));
       // 测试 sex 不匹配
       customerMapper.insert(cloneIgnoreId(dbCustomer, o -> o.setSex(null)));
       // 测试 orderMoney 不匹配
       customerMapper.insert(cloneIgnoreId(dbCustomer, o -> o.setOrderMoney(null)));
       // 测试 unit 不匹配
       customerMapper.insert(cloneIgnoreId(dbCustomer, o -> o.setUnit(null)));
       // 测试 loginIp 不匹配
       customerMapper.insert(cloneIgnoreId(dbCustomer, o -> o.setLoginIp(null)));
       // 测试 loginDate 不匹配
       customerMapper.insert(cloneIgnoreId(dbCustomer, o -> o.setLoginDate(null)));
       // 测试 createTime 不匹配
       customerMapper.insert(cloneIgnoreId(dbCustomer, o -> o.setCreateTime(null)));
       // 测试 status 不匹配
       customerMapper.insert(cloneIgnoreId(dbCustomer, o -> o.setStatus(null)));
       // 测试 email 不匹配
       customerMapper.insert(cloneIgnoreId(dbCustomer, o -> o.setEmail(null)));
       // 测试 allocationStatus 不匹配
       customerMapper.insert(cloneIgnoreId(dbCustomer, o -> o.setAllocationStatus(null)));
       // 准备参数
       CustomerPageReqVO reqVO = new CustomerPageReqVO();
       reqVO.setCustomerName(null);
       reqVO.setTelphone(null);
       reqVO.setSex(null);
       reqVO.setOrderMoney(null);
       reqVO.setUnit(null);
       reqVO.setLoginIp(null);
       reqVO.setBeginLoginDate(null);
       reqVO.setEndLoginDate(null);
       reqVO.setBeginCreateTime(null);
       reqVO.setEndCreateTime(null);
       reqVO.setStatus(null);
       reqVO.setEmail(null);
       reqVO.setAllocationStatus(null);

       // 调用
       PageResult<CustomerDO> pageResult = customerService.getCustomerPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbCustomer, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetCustomerList() {
       // mock 数据
       CustomerDO dbCustomer = randomPojo(CustomerDO.class, o -> { // 等会查询到
           o.setCustomerName(null);
           o.setTelphone(null);
           o.setSex(null);
           o.setOrderMoney(null);
           o.setUnit(null);
           o.setLoginIp(null);
           o.setLoginDate(null);
           o.setCreateTime(null);
           o.setStatus(null);
           o.setEmail(null);
           o.setAllocationStatus(null);
       });
       customerMapper.insert(dbCustomer);
       // 测试 customerName 不匹配
       customerMapper.insert(cloneIgnoreId(dbCustomer, o -> o.setCustomerName(null)));
       // 测试 telphone 不匹配
       customerMapper.insert(cloneIgnoreId(dbCustomer, o -> o.setTelphone(null)));
       // 测试 sex 不匹配
       customerMapper.insert(cloneIgnoreId(dbCustomer, o -> o.setSex(null)));
       // 测试 orderMoney 不匹配
       customerMapper.insert(cloneIgnoreId(dbCustomer, o -> o.setOrderMoney(null)));
       // 测试 unit 不匹配
       customerMapper.insert(cloneIgnoreId(dbCustomer, o -> o.setUnit(null)));
       // 测试 loginIp 不匹配
       customerMapper.insert(cloneIgnoreId(dbCustomer, o -> o.setLoginIp(null)));
       // 测试 loginDate 不匹配
       customerMapper.insert(cloneIgnoreId(dbCustomer, o -> o.setLoginDate(null)));
       // 测试 createTime 不匹配
       customerMapper.insert(cloneIgnoreId(dbCustomer, o -> o.setCreateTime(null)));
       // 测试 status 不匹配
       customerMapper.insert(cloneIgnoreId(dbCustomer, o -> o.setStatus(null)));
       // 测试 email 不匹配
       customerMapper.insert(cloneIgnoreId(dbCustomer, o -> o.setEmail(null)));
       // 测试 allocationStatus 不匹配
       customerMapper.insert(cloneIgnoreId(dbCustomer, o -> o.setAllocationStatus(null)));
       // 准备参数
       CustomerExportReqVO reqVO = new CustomerExportReqVO();
       reqVO.setCustomerName(null);
       reqVO.setTelphone(null);
       reqVO.setSex(null);
       reqVO.setOrderMoney(null);
       reqVO.setUnit(null);
       reqVO.setLoginIp(null);
       reqVO.setBeginLoginDate(null);
       reqVO.setEndLoginDate(null);
       reqVO.setBeginCreateTime(null);
       reqVO.setEndCreateTime(null);
       reqVO.setStatus(null);
       reqVO.setEmail(null);
       reqVO.setAllocationStatus(null);

       // 调用
       List<CustomerDO> list = customerService.getCustomerList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbCustomer, list.get(0));
    }

}
