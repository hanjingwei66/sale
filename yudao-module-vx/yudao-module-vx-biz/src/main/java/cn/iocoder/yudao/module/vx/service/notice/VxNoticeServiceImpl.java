package cn.iocoder.yudao.module.vx.service.notice;

import cn.iocoder.yudao.module.vx.dal.dataobject.notice.VxNoticeDO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.vx.controller.admin.notice.vo.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.vx.convert.notice.NoticeConvert;
import cn.iocoder.yudao.module.vx.dal.mysql.notice.VxNoticeMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.vx.enums.ErrorCodeConstants.*;

/**
 * 公告栏 Service 实现类
 *
 * @author 芋道源码
 */
@Service("VxNoticeServiceImpl")
@Validated
public class VxNoticeServiceImpl implements VxNoticeService {

    @Resource
    private VxNoticeMapper vxNoticeMapper;

    @Override
    public Integer createNotice(NoticeCreateReqVO createReqVO) {
        // 插入
        VxNoticeDO notice = NoticeConvert.INSTANCE.convert(createReqVO);
        vxNoticeMapper.insert(notice);
        // 返回
        return notice.getId();
    }

    @Override
    public void updateNotice(NoticeUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateNoticeExists(updateReqVO.getId());
        // 更
        VxNoticeDO updateObj = NoticeConvert.INSTANCE.convert(updateReqVO);
        vxNoticeMapper.updateById(updateObj);
    }

    @Override
    public void deleteNotice(Integer id) {
        // 校验存在
        this.validateNoticeExists(id);
        // 删除
        vxNoticeMapper.deleteById(id);
    }

    private void validateNoticeExists(Integer id) {
        if (vxNoticeMapper.selectById(id) == null) {
            throw exception(NOTICE_NOT_EXISTS);
        }
    }

    @Override
    public VxNoticeDO getNotice(Integer id) {
        return vxNoticeMapper.selectById(id);
    }

    @Override
    public List<VxNoticeDO> getNoticeList(Collection<Integer> ids) {
        return vxNoticeMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<VxNoticeDO> getNoticePage(NoticePageReqVO pageReqVO) {
        return vxNoticeMapper.selectPage(pageReqVO);
    }

    @Override
    public List<VxNoticeDO> getNoticeList(NoticeExportReqVO exportReqVO) {
        return vxNoticeMapper.selectList(exportReqVO);
    }

}
