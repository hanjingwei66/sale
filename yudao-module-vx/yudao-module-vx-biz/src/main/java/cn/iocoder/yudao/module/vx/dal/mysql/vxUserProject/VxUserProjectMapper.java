package cn.iocoder.yudao.module.vx.dal.mysql.vxUserProject;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.vx.controller.admin.shiyaoProject.vo.ShiyaoProjectPageReqVO;
import cn.iocoder.yudao.module.vx.controller.admin.shiyaoProject.vo.ShiyaoProjectRespVO;
import cn.iocoder.yudao.module.vx.dal.dataobject.shiyaoProject.ShiyaoProjectDO;
import cn.iocoder.yudao.module.vx.dal.dataobject.vxUserProject.VxUserProjectDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VxUserProjectMapper  extends BaseMapperX<VxUserProjectDO> {
    List<ShiyaoProjectDO> getSignUpProjectList( ShiyaoProjectPageReqVO id);
}
