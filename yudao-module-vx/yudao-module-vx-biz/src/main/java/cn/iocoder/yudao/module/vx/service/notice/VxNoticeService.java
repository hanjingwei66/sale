package cn.iocoder.yudao.module.vx.service.notice;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.vx.controller.admin.notice.vo.*;
import cn.iocoder.yudao.module.vx.dal.dataobject.notice.VxNoticeDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 公告栏 Service 接口
 *
 * @author 芋道源码
 */
public interface VxNoticeService {

    /**
     * 创建公告栏
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createNotice(@Valid NoticeCreateReqVO createReqVO);

    /**
     * 更新公告栏
     *
     * @param updateReqVO 更新信息
     */
    void updateNotice(@Valid NoticeUpdateReqVO updateReqVO);

    /**
     * 删除公告栏
     *
     * @param id 编号
     */
    void deleteNotice(Integer id);

    /**
     * 获得公告栏
     *
     * @param id 编号
     * @return 公告栏
     */
    VxNoticeDO getNotice(Integer id);

    /**
     * 获得公告栏列表
     *
     * @param ids 编号
     * @return 公告栏列表
     */
    List<VxNoticeDO> getNoticeList(Collection<Integer> ids);

    /**
     * 获得公告栏分页
     *
     * @param pageReqVO 分页查询
     * @return 公告栏分页
     */
    PageResult<VxNoticeDO> getNoticePage(NoticePageReqVO pageReqVO);

    /**
     * 获得公告栏列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 公告栏列表
     */
    List<VxNoticeDO> getNoticeList(NoticeExportReqVO exportReqVO);

}