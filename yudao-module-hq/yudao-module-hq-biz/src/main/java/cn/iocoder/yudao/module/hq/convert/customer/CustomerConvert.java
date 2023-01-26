package cn.iocoder.yudao.module.hq.convert.customer;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.hq.controller.admin.customer.vo.*;
import cn.iocoder.yudao.module.hq.dal.dataobject.customer.CustomerDO;

/**
 * hq用户 Convert
 *
 * @author hanjingwei
 */
@Mapper
public interface CustomerConvert {

    CustomerConvert INSTANCE = Mappers.getMapper(CustomerConvert.class);

    CustomerDO convert(CustomerCreateReqVO bean);

    CustomerDO convert(CustomerUpdateReqVO bean);

    CustomerRespVO convert(CustomerDO bean);

    List<CustomerRespVO> convertList(List<CustomerDO> list);

    PageResult<CustomerRespVO> convertPage(PageResult<CustomerDO> page);

    List<CustomerExcelVO> convertList02(List<CustomerDO> list);

}
