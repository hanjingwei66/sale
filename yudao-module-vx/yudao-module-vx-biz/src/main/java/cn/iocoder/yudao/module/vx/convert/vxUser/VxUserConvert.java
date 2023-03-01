package cn.iocoder.yudao.module.vx.convert.vxUser;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.vx.controller.admin.vxUser.vo.*;
import cn.iocoder.yudao.module.vx.dal.dataobject.vxUser.VxUserDO;

/**
 * 微信用户信息 Convert
 *
 * @author hjw
 */
@Mapper
public interface VxUserConvert {

    VxUserConvert INSTANCE = Mappers.getMapper(VxUserConvert.class);

    VxUserDO convert(VxUserCreateReqVO bean);

    VxUserDO convert(VxUserUpdateReqVO bean);

    VxUserRespVO convert(VxUserDO bean);

    List<VxUserRespVO> convertList(List<VxUserDO> list);

    PageResult<VxUserRespVO> convertPage(PageResult<VxUserDO> page);

    List<VxUserExcelVO> convertList02(List<VxUserDO> list);

}
