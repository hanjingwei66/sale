package cn.iocoder.yudao.module.hq.dal.mysql.customer;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.hq.dal.dataobject.customer.CustomerDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.hq.controller.admin.customer.vo.*;

/**
 * hq用户 Mapper
 *
 * @author hanjingwei
 */
@Mapper
public interface CustomerMapper extends BaseMapperX<CustomerDO> {

    default PageResult<CustomerDO> selectPage(CustomerPageReqVO reqVO) {
        ArrayList<Object> ids = new ArrayList<>();
        ids.add(SecurityFrameworkUtils.getLoginUserId());
        //TODO 添加boss id
        return selectPage(reqVO, new LambdaQueryWrapperX<CustomerDO>()
                .in(CustomerDO::getSaleId,ids)
                .likeIfPresent(CustomerDO::getCustomerName, reqVO.getCustomerName())
                .eqIfPresent(CustomerDO::getTelphone, reqVO.getTelphone())
                .eqIfPresent(CustomerDO::getSex, reqVO.getSex())
                .eqIfPresent(CustomerDO::getOrderMoney, reqVO.getOrderMoney())
                .eqIfPresent(CustomerDO::getUnit, reqVO.getUnit())
                .eqIfPresent(CustomerDO::getLoginIp, reqVO.getLoginIp())
                .betweenIfPresent(CustomerDO::getLoginDate, reqVO.getBeginLoginDate(), reqVO.getEndLoginDate())
                .betweenIfPresent(CustomerDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .eqIfPresent(CustomerDO::getStatus, reqVO.getStatus())
                .eqIfPresent(CustomerDO::getEmail, reqVO.getEmail())
                .eqIfPresent(CustomerDO::getAllocationStatus, reqVO.getAllocationStatus())
                .orderByDesc(CustomerDO::getId));
    }

    default List<CustomerDO> selectList(CustomerExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<CustomerDO>()
                .likeIfPresent(CustomerDO::getCustomerName, reqVO.getCustomerName())
                .eqIfPresent(CustomerDO::getTelphone, reqVO.getTelphone())
                .eqIfPresent(CustomerDO::getSex, reqVO.getSex())
                .eqIfPresent(CustomerDO::getOrderMoney, reqVO.getOrderMoney())
                .eqIfPresent(CustomerDO::getUnit, reqVO.getUnit())
                .eqIfPresent(CustomerDO::getLoginIp, reqVO.getLoginIp())
                .betweenIfPresent(CustomerDO::getLoginDate, reqVO.getBeginLoginDate(), reqVO.getEndLoginDate())
                .betweenIfPresent(CustomerDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .eqIfPresent(CustomerDO::getStatus, reqVO.getStatus())
                .eqIfPresent(CustomerDO::getEmail, reqVO.getEmail())
                .eqIfPresent(CustomerDO::getAllocationStatus, reqVO.getAllocationStatus())
                .orderByDesc(CustomerDO::getId));
    }
    default List<CustomerDO> selectList(CustomerReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<CustomerDO>()
                .likeIfPresent(CustomerDO::getCustomerName, reqVO.getCustomerName())
                .eqIfPresent(CustomerDO::getTelphone, reqVO.getTelphone())
                .eqIfPresent(CustomerDO::getSex, reqVO.getSex())
                .eqIfPresent(CustomerDO::getOrderMoney, reqVO.getOrderMoney())
                .eqIfPresent(CustomerDO::getUnit, reqVO.getUnit())
                .eqIfPresent(CustomerDO::getLoginIp, reqVO.getLoginIp())
                .eqIfPresent(CustomerDO::getStatus, reqVO.getStatus())
                .eqIfPresent(CustomerDO::getEmail, reqVO.getEmail())
                .eqIfPresent(CustomerDO::getAllocationStatus, reqVO.getAllocationStatus())
                .orderByDesc(CustomerDO::getId));
    }
}
