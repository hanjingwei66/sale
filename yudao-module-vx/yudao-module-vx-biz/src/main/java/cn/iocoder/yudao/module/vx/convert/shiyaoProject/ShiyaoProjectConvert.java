package cn.iocoder.yudao.module.vx.convert.shiyaoProject;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.vx.controller.admin.shiyaoProject.vo.*;
import cn.iocoder.yudao.module.vx.dal.dataobject.shiyaoProject.ShiyaoProjectDO;

/**
 * 试药项目 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface ShiyaoProjectConvert {

    ShiyaoProjectConvert INSTANCE = Mappers.getMapper(ShiyaoProjectConvert.class);

    ShiyaoProjectDO convert(ShiyaoProjectCreateReqVO bean);

    ShiyaoProjectDO convert(ShiyaoProjectUpdateReqVO bean);

    ShiyaoProjectRespVO convert(ShiyaoProjectDO bean);

    List<ShiyaoProjectRespVO> convertList(List<ShiyaoProjectDO> list);

    PageResult<ShiyaoProjectRespVO> convertPage(PageResult<ShiyaoProjectDO> page);

    List<ShiyaoProjectExcelVO> convertList02(List<ShiyaoProjectDO> list);
    @Mapping(source = "id",target = "projectId")
    ShiyaoProjectListVO convertList031(ShiyaoProjectDO list);
    List<ShiyaoProjectListVO> convertList03(List<ShiyaoProjectDO> list);

}
