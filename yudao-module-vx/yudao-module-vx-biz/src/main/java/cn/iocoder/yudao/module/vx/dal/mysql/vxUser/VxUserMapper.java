package cn.iocoder.yudao.module.vx.dal.mysql.vxUser;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.vx.dal.dataobject.vxUser.VxUserDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.vx.controller.admin.vxUser.vo.*;

/**
 * 微信用户信息 Mapper
 *
 * @author hjw
 */
@Mapper
public interface VxUserMapper extends BaseMapperX<VxUserDO> {

    default PageResult<VxUserDO> selectPage(VxUserPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<VxUserDO>()
                .eqIfPresent(VxUserDO::getVxId, reqVO.getVxId())
                .likeIfPresent(VxUserDO::getUserName, reqVO.getUserName())
                .eqIfPresent(VxUserDO::getUserCardId, reqVO.getUserCardId())
                .betweenIfPresent(VxUserDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(VxUserDO::getId));
    }

    default List<VxUserDO> selectList(VxUserExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<VxUserDO>()
                .eqIfPresent(VxUserDO::getVxId, reqVO.getVxId())
                .likeIfPresent(VxUserDO::getUserName, reqVO.getUserName())
                .eqIfPresent(VxUserDO::getUserCardId, reqVO.getUserCardId())
                .betweenIfPresent(VxUserDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(VxUserDO::getId));
    }

}
