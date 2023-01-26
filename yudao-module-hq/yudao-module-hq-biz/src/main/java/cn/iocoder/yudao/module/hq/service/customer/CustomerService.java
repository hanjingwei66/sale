package cn.iocoder.yudao.module.hq.service.customer;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.hq.controller.admin.customer.vo.*;
import cn.iocoder.yudao.module.hq.dal.dataobject.customer.CustomerDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * hq用户 Service 接口
 *
 * @author hanjingwei
 */
public interface CustomerService {

    /**
     * 创建hq用户
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCustomer(@Valid CustomerCreateReqVO createReqVO);

    /**
     * 更新hq用户
     *
     * @param updateReqVO 更新信息
     */
    void updateCustomer(@Valid CustomerUpdateReqVO updateReqVO);

    /**
     * 删除hq用户
     *
     * @param id 编号
     */
    void deleteCustomer(Long id);

    /**
     * 获得hq用户
     *
     * @param id 编号
     * @return hq用户
     */
    CustomerDO getCustomer(Long id);

    /**
     * 获得hq用户列表
     *
     * @param ids 编号
     * @return hq用户列表
     */
    List<CustomerDO> getCustomerList(Collection<Long> ids);

    /**
     * 获得hq用户分页
     *
     * @param pageReqVO 分页查询
     * @return hq用户分页
     */
    PageResult<CustomerDO> getCustomerPage(CustomerPageReqVO pageReqVO);

    /**
     * 获得hq用户列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return hq用户列表
     */
    List<CustomerDO> getCustomerList(CustomerExportReqVO exportReqVO);
    List<CustomerDO> getCustomerList(CustomerReqVO baseVo);


    Integer getOrderSum(CustomerReqVO query);
    Map<Long, Integer> getAllOrderSum(CustomerReqVO query);
}