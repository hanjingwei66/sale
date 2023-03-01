package cn.iocoder.yudao.module.vx.convert.vxUserProject;

import cn.iocoder.yudao.module.vx.controller.admin.vxUser.vo.VxUserCreateReqVO;
import cn.iocoder.yudao.module.vx.controller.admin.vxUser.vo.VxUserSingUpVO;
import cn.iocoder.yudao.module.vx.controller.admin.vxUser.vo.VxUserUpdateReqVO;
import cn.iocoder.yudao.module.vx.dal.dataobject.vxUserProject.VxUserProjectDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface VxUserProjectConvert {
    VxUserProjectConvert INSTANCE = Mappers.getMapper(VxUserProjectConvert.class);
    VxUserCreateReqVO convertCreate (VxUserSingUpVO vxUserSingUpVO);
    @Mapping(target = "id",source = "userId")
    VxUserUpdateReqVO convertUpdate (VxUserSingUpVO vxUserSingUpVO);
    VxUserProjectDO convertDO(VxUserSingUpVO vxUserSingUpVO);
}
