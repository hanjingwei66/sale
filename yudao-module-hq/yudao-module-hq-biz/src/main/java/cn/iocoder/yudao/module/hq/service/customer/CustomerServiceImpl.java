package cn.iocoder.yudao.module.hq.service.customer;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import jodd.util.StringUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

import cn.iocoder.yudao.module.hq.controller.admin.customer.vo.*;
import cn.iocoder.yudao.module.hq.dal.dataobject.customer.CustomerDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.hq.convert.customer.CustomerConvert;
import cn.iocoder.yudao.module.hq.dal.mysql.customer.CustomerMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.hq.enums.ErrorCodeConstants.*;

/**
 * hq用户 Service 实现类
 *
 * @author hanjingwei
 */
@Service
@Validated
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private AdminUserService userService;
    @Override
    public Long createCustomer(CustomerCreateReqVO createReqVO) {
        // 插入
        CustomerDO customer = CustomerConvert.INSTANCE.convert(createReqVO);
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        customer.setSaleId(loginUserId);
        customerMapper.insert(customer);
        // 返回
        return customer.getId();
    }

    @Override
    public void updateCustomer(CustomerUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateCustomerExists(updateReqVO.getId());
        // 更新
        CustomerDO updateObj = CustomerConvert.INSTANCE.convert(updateReqVO);
        customerMapper.updateById(updateObj);
    }

    @Override
    public void deleteCustomer(Long id) {
        // 校验存在
        this.validateCustomerExists(id);
        // 删除
        customerMapper.deleteById(id);
    }

    private void validateCustomerExists(Long id) {
        if (customerMapper.selectById(id) == null) {
            throw exception(CUSTOMER_NOT_EXISTS);
        }
    }

    @Override
    public CustomerDO getCustomer(Long id) {
        return customerMapper.selectById(id);
    }

    @Override
    public List<CustomerDO> getCustomerList(Collection<Long> ids) {
        return customerMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<CustomerDO> getCustomerPage(CustomerPageReqVO pageReqVO) {
        return customerMapper.selectPage(pageReqVO);
    }

    @Override
    public List<CustomerDO> getCustomerList(CustomerExportReqVO exportReqVO) {
        return customerMapper.selectList(exportReqVO);
    }

    @Override
    public List<CustomerDO> getCustomerList(CustomerReqVO baseVo) {
        return null;
    }

    @Override
    public Integer  getOrderSum(CustomerReqVO baseVo) {
        List<CustomerDO> customerDOS = customerMapper.selectList(baseVo);
        Integer sum = customerDOS.stream().filter(item -> Objects.isNull(item)).map(CustomerDO::getOrderMoney)
                .reduce((x, y) -> x + y).get();
        return sum;
    }
    @Override
    public Map<Long, Integer>  getAllOrderSum(CustomerReqVO baseVo) {
        List<AdminUserDO> list = userService.getUsersByStatus(CommonStatusEnum.ENABLE.getStatus());
        List<CustomerDO> customerDOS = customerMapper.selectList(baseVo);
        Map<Long, Integer> result = customerDOS.stream()
                .collect(Collectors.groupingBy(CustomerDO::getSaleId, Collectors.summingInt(CustomerDO::getOrderMoney)));
        for (AdminUserDO adminUserDO : list) {
            if(!result.keySet().contains(adminUserDO.getId())){
                result.put(adminUserDO.getId(),0);
            }
        }
//        List<Integer> list = new ArrayList<>();
//        collect.forEach((k ,v)->list.add(v));
        return result;
    }
}
