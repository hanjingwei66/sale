package cn.iocoder.yudao.module.system.convert.quota;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.quota.vo.QuotaCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.quota.vo.QuotaExcelVO;
import cn.iocoder.yudao.module.system.controller.admin.quota.vo.QuotaRespVO;
import cn.iocoder.yudao.module.system.controller.admin.quota.vo.QuotaUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.quota.QuotaDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 指标 Convert
 *
 * @author hjw
 */
@Mapper
public interface QuotaConvert {

    QuotaConvert INSTANCE = Mappers.getMapper(QuotaConvert.class);

    QuotaDO convert(QuotaCreateReqVO bean);

    QuotaDO convert(QuotaUpdateReqVO bean);

    QuotaRespVO convert(QuotaDO bean);

    List<QuotaRespVO> convertList(List<QuotaDO> list);

    PageResult<QuotaRespVO> convertPage(PageResult<QuotaDO> page);

    List<QuotaExcelVO> convertList02(List<QuotaDO> list);
    List<QuotaDO> convertList2(List<QuotaCreateReqVO> list);

}
