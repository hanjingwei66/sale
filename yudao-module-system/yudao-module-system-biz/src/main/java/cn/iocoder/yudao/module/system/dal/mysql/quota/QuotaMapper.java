package cn.iocoder.yudao.module.system.dal.mysql.quota;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.quota.vo.QuotaExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.quota.vo.QuotaPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.quota.QuotaActualStatisticsDO;
import cn.iocoder.yudao.module.system.dal.dataobject.quota.QuotaDO;
import cn.iocoder.yudao.module.system.dal.dataobject.quota.QuotaStatisticsDO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 指标 Mapper
 *
 * @author hjw
 */
@Mapper
public interface QuotaMapper extends BaseMapperX<QuotaDO> {

    default PageResult<QuotaDO> selectPage(QuotaPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<QuotaDO>()
                .betweenIfPresent(QuotaDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(QuotaDO::getId));
    }

    default List<QuotaDO> selectList(QuotaExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<QuotaDO>()
                .betweenIfPresent(QuotaDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(QuotaDO::getId));
    }

    default List<QuotaDO> selectListByUserId(Long userId) {
        return selectList(new LambdaQueryWrapperX<QuotaDO>().eq(QuotaDO::getSaleId ,userId));
    }
        default List<QuotaDO> selectList(Long saleId) {
        return selectList( new LambdaQueryWrapperX<QuotaDO>()
                .eq(QuotaDO::getSaleId, saleId));

    }

    List<QuotaStatisticsDO> selectExpectEveryOne ();
    List<QuotaActualStatisticsDO> selectActualEveryOne ();
}
