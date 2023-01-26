package cn.iocoder.yudao.module.system.service.quota;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.quota.vo.QuotaCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.quota.vo.QuotaExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.quota.vo.QuotaPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.quota.vo.QuotaUpdateReqVO;
import cn.iocoder.yudao.module.system.convert.quota.QuotaConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.quota.QuotaDO;
import cn.iocoder.yudao.module.system.dal.mysql.quota.QuotaMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;


/**
 * 指标 Service 实现类
 *
 * @author hjw
 */
@Service
@Validated
public class QuotaServiceImpl implements QuotaService {

    @Resource
    private QuotaMapper quotaMapper;

    @Override
    public Long createQuota(QuotaCreateReqVO createReqVO) {
        // 插入
        QuotaDO quota = QuotaConvert.INSTANCE.convert(createReqVO);
        quotaMapper.insert(quota);
        // 返回
        return quota.getId();
    }

    @Override
    public void updateQuota(QuotaUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateQuotaExists(updateReqVO.getId());
        // 更新
        QuotaDO updateObj = QuotaConvert.INSTANCE.convert(updateReqVO);
        quotaMapper.updateById(updateObj);
    }

    @Override
    public void deleteQuota(Long id) {
        // 校验存在
        this.validateQuotaExists(id);
        // 删除
        quotaMapper.deleteById(id);
    }

    private void validateQuotaExists(Long id) {
        if (quotaMapper.selectById(id) == null) {
//            throw exception(QUOTA_NOT_EXISTS);
        }
    }

    @Override
    public QuotaDO getQuota(Long id) {
        return quotaMapper.selectById(id);
    }
    @Override
    public List<QuotaDO> getQuotaBySaleId(Long saleId) {
        return quotaMapper.selectList(saleId);
    }
    @Override
    public List<QuotaDO> getQuotaList(Collection<Long> ids) {
        return quotaMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<QuotaDO> getQuotaPage(QuotaPageReqVO pageReqVO) {
        return quotaMapper.selectPage(pageReqVO);
    }

    @Override
    public List<QuotaDO> getQuotaList(QuotaExportReqVO exportReqVO) {
        return quotaMapper.selectList(exportReqVO);
    }
    @Override
    public List<QuotaDO> getQuotaListByUserId(Long userId) {
        return quotaMapper.selectListByUserId(userId);
    }
}
