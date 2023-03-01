package cn.iocoder.yudao.module.vx.dal.mysql.helpinfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.vx.controller.admin.helpinfo.vo.HelpInfoExportReqVO;
import cn.iocoder.yudao.module.vx.controller.admin.helpinfo.vo.HelpInfoPageReqVO;
import cn.iocoder.yudao.module.vx.dal.dataobject.helpinfo.HelpInfoDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.vx.controller.app.helpinfo.vo.*;

/**
 * 帮助信息 Mapper
 *
 * @author hjw
 */
@Mapper
public interface HelpInfoMapper extends BaseMapperX<HelpInfoDO> {

    default PageResult<HelpInfoDO> selectPage(HelpInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HelpInfoDO>()
                .likeIfPresent(HelpInfoDO::getHelpName, reqVO.getHelpName())
                .eqIfPresent(HelpInfoDO::getHelpDetail, reqVO.getHelpDetail())
                .betweenIfPresent(HelpInfoDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(HelpInfoDO::getId));
    }

    default List<HelpInfoDO> selectList(HelpInfoExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<HelpInfoDO>()
                .likeIfPresent(HelpInfoDO::getHelpName, reqVO.getHelpName())
                .eqIfPresent(HelpInfoDO::getHelpDetail, reqVO.getHelpDetail())
                .betweenIfPresent(HelpInfoDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(HelpInfoDO::getId));
    }

}
