package cn.iocoder.yudao.module.vx.service.vxUserProject;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.vx.controller.admin.shiyaoProject.vo.ShiyaoProjectPageReqVO;
import cn.iocoder.yudao.module.vx.controller.admin.shiyaoProject.vo.ShiyaoProjectRespVO;
import cn.iocoder.yudao.module.vx.controller.admin.vxUser.vo.VxUserSingUpVO;
import cn.iocoder.yudao.module.vx.dal.dataobject.shiyaoProject.ShiyaoProjectDO;
import cn.iocoder.yudao.module.vx.dal.dataobject.vxUserProject.VxUserProjectDO;

import java.util.List;

public interface VxUserProjectService {
    Integer signUp(VxUserSingUpVO vxUserSingUpVO);
    PageResult<ShiyaoProjectRespVO> getSignUpProjectList(ShiyaoProjectPageReqVO id) throws Exception;
}
