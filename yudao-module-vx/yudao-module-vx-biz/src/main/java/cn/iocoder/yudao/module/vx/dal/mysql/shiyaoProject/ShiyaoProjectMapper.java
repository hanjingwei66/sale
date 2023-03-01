package cn.iocoder.yudao.module.vx.dal.mysql.shiyaoProject;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.vx.dal.dataobject.shiyaoProject.ShiyaoProjectDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.vx.controller.admin.shiyaoProject.vo.*;

/**
 * 试药项目 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ShiyaoProjectMapper extends BaseMapperX<ShiyaoProjectDO> {

    default PageResult<ShiyaoProjectDO> selectPage(ShiyaoProjectPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ShiyaoProjectDO>()
                .likeIfPresent(ShiyaoProjectDO::getProjectName, reqVO.getProjectName())
                .likeIfPresent(ShiyaoProjectDO::getAddress, reqVO.getAddress())
                .eqIfPresent(ShiyaoProjectDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ShiyaoProjectDO::getCategory, reqVO.getCategory())
                .betweenIfPresent(ShiyaoProjectDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(ShiyaoProjectDO::getId));
    }

    default List<ShiyaoProjectDO> selectList(ShiyaoProjectExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ShiyaoProjectDO>()
                .likeIfPresent(ShiyaoProjectDO::getProjectName, reqVO.getProjectName())
                .eqIfPresent(ShiyaoProjectDO::getAddress, reqVO.getAddress())
                .eqIfPresent(ShiyaoProjectDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ShiyaoProjectDO::getCategory, reqVO.getCategory())
                .betweenIfPresent(ShiyaoProjectDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(ShiyaoProjectDO::getId));
    }

}
