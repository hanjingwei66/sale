package cn.iocoder.yudao.module.vx.convert.helpinfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.vx.controller.admin.helpinfo.vo.HelpInfoCreateReqVO;
import cn.iocoder.yudao.module.vx.controller.admin.helpinfo.vo.HelpInfoExcelVO;
import cn.iocoder.yudao.module.vx.controller.admin.helpinfo.vo.HelpInfoRespVO;
import cn.iocoder.yudao.module.vx.controller.admin.helpinfo.vo.HelpInfoUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.vx.controller.app.helpinfo.vo.*;
import cn.iocoder.yudao.module.vx.dal.dataobject.helpinfo.HelpInfoDO;

/**
 * 帮助信息 Convert
 *
 * @author hjw
 */
@Mapper
public interface HelpInfoConvert {

    HelpInfoConvert INSTANCE = Mappers.getMapper(HelpInfoConvert.class);

    HelpInfoDO convert(AppHelpInfoCreateReqVO bean);
    HelpInfoDO convert(HelpInfoCreateReqVO bean);

    HelpInfoDO convert(AppHelpInfoUpdateReqVO bean);
    HelpInfoDO convert(HelpInfoUpdateReqVO bean);

//    AppHelpInfoRespVO convert(HelpInfoDO bean);
    HelpInfoRespVO convert(HelpInfoDO bean);

    List<HelpInfoRespVO> convertList(List<HelpInfoDO> list);
//    List<AppHelpInfoRespVO> convertList(List<HelpInfoDO> list);

//    PageResult<AppHelpInfoRespVO> convertPage(PageResult<HelpInfoDO> page);
    PageResult<HelpInfoRespVO> convertPage(PageResult<HelpInfoDO> page);

//    List<AppHelpInfoExcelVO> convertList02(List<HelpInfoDO> list);
    List<HelpInfoExcelVO> convertList02(List<HelpInfoDO> list);

}
