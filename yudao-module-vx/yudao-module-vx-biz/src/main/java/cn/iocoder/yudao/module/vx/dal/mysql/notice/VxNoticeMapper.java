package cn.iocoder.yudao.module.vx.dal.mysql.notice;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.vx.dal.dataobject.notice.VxNoticeDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.vx.controller.admin.notice.vo.*;

/**
 * 公告栏 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface VxNoticeMapper extends BaseMapperX<VxNoticeDO> {

    default PageResult<VxNoticeDO> selectPage(NoticePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<VxNoticeDO>()
                .eqIfPresent(VxNoticeDO::getNotice, reqVO.getNotice())
                .eqIfPresent(VxNoticeDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(VxNoticeDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(VxNoticeDO::getId));
    }

    default List<VxNoticeDO> selectList(NoticeExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<VxNoticeDO>()
                .eqIfPresent(VxNoticeDO::getNotice, reqVO.getNotice())
                .eqIfPresent(VxNoticeDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(VxNoticeDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(VxNoticeDO::getId));
    }

}
