package cn.iocoder.yudao.module.system.convert.group;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.system.controller.admin.group.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.group.GroupDO;

/**
 * 用户组 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface GroupConvert {

    GroupConvert INSTANCE = Mappers.getMapper(GroupConvert.class);

    GroupDO convert(GroupCreateReqVO bean);

    GroupDO convert(GroupUpdateReqVO bean);

    GroupRespVO convert(GroupDO bean);

    List<GroupRespVO> convertList(List<GroupDO> list);

    PageResult<GroupRespVO> convertPage(PageResult<GroupDO> page);

    List<GroupExcelVO> convertList02(List<GroupDO> list);

}
