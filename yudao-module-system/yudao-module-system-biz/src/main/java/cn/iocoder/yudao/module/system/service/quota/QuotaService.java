package cn.iocoder.yudao.module.system.service.quota;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.quota.vo.QuotaCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.quota.vo.QuotaExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.quota.vo.QuotaPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.quota.vo.QuotaUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.quota.QuotaActualStatisticsDO;
import cn.iocoder.yudao.module.system.dal.dataobject.quota.QuotaDO;
import cn.iocoder.yudao.module.system.dal.dataobject.quota.QuotaStatisticsDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 指标 Service 接口
 *
 * @author hjw
 */
public interface QuotaService {

    /**
     * 创建指标
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createQuota(@Valid QuotaCreateReqVO createReqVO);

    /**
     * 更新指标
     *
     * @param updateReqVO 更新信息
     */
    void updateQuota(@Valid QuotaUpdateReqVO updateReqVO);

    Long createAndUpdateQuota(@Valid List<QuotaCreateReqVO> createReqVO);

    /**
     * 删除指标
     *
     * @param id 编号
     */
    void deleteQuota(Long id);

    /**
     * 获得指标
     *
     * @param id 编号
     * @return 指标
     */
    QuotaDO getQuota(Long id);

    List<QuotaDO> getQuotaBySaleId(Long id);

    /**
     * 获得指标列表
     *
     * @param ids 编号
     * @return 指标列表
     */
    List<QuotaDO> getQuotaList(Collection<Long> ids);

    /**
     * 获得指标分页
     *
     * @param pageReqVO 分页查询
     * @return 指标分页
     */
    PageResult<QuotaDO> getQuotaPage(QuotaPageReqVO pageReqVO);

    /**
     * 获得指标列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 指标列表
     */
    List<QuotaDO> getQuotaList(QuotaExportReqVO exportReqVO);


    List<QuotaDO>  getQuotaListByUserId(Long userId );
    List<QuotaStatisticsDO> selectExpectEveryOne ();
    List<QuotaActualStatisticsDO> selectActualEveryOne ();
}