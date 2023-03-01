package cn.iocoder.yudao.module.vx.convert.notice;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.vx.controller.admin.notice.vo.NoticeCreateReqVO;
import cn.iocoder.yudao.module.vx.controller.admin.notice.vo.NoticeExcelVO;
import cn.iocoder.yudao.module.vx.controller.admin.notice.vo.NoticeRespVO;
import cn.iocoder.yudao.module.vx.controller.admin.notice.vo.NoticeUpdateReqVO;
import cn.iocoder.yudao.module.vx.dal.dataobject.notice.VxNoticeDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.vx.controller.app.notice.vo.*;

/**
 * 公告栏 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface NoticeConvert {

    NoticeConvert INSTANCE = Mappers.getMapper(NoticeConvert.class);

    VxNoticeDO convert(NoticeCreateReqVO bean);

    VxNoticeDO convert(NoticeUpdateReqVO bean);


    NoticeRespVO convert(VxNoticeDO bean);
    AppNoticeRespVO appconvert(VxNoticeDO bean);

    List<NoticeRespVO> convertList(List<VxNoticeDO> list);
    List<AppNoticeRespVO> appconvertList(List<VxNoticeDO> list);

    PageResult<NoticeRespVO> convertPage(PageResult<VxNoticeDO> page);
    PageResult<AppNoticeRespVO> appconvertPage(PageResult<VxNoticeDO> page);

    List<NoticeExcelVO> convertList02(List<VxNoticeDO> list);

}
