package cn.iocoder.yudao.module.vx.service.helpinfo;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.vx.controller.admin.helpinfo.vo.HelpInfoCreateReqVO;
import cn.iocoder.yudao.module.vx.controller.admin.helpinfo.vo.HelpInfoExportReqVO;
import cn.iocoder.yudao.module.vx.controller.admin.helpinfo.vo.HelpInfoPageReqVO;
import cn.iocoder.yudao.module.vx.controller.admin.helpinfo.vo.HelpInfoUpdateReqVO;
import cn.iocoder.yudao.module.vx.controller.app.helpinfo.vo.*;
import cn.iocoder.yudao.module.vx.dal.dataobject.helpinfo.HelpInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 帮助信息 Service 接口
 *
 * @author hjw
 */
public interface HelpInfoService {

    /**
     * 创建帮助信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createHelpInfo(@Valid HelpInfoCreateReqVO createReqVO);

    /**
     * 更新帮助信息
     *
     * @param updateReqVO 更新信息
     */
    void updateHelpInfo(@Valid HelpInfoUpdateReqVO updateReqVO);

    /**
     * 删除帮助信息
     *
     * @param id 编号
     */
    void deleteHelpInfo(Integer id);

    /**
     * 获得帮助信息
     *
     * @param id 编号
     * @return 帮助信息
     */
    HelpInfoDO getHelpInfo(Integer id);

    /**
     * 获得帮助信息列表
     *
     * @param ids 编号
     * @return 帮助信息列表
     */
    List<HelpInfoDO> getHelpInfoList(Collection<Integer> ids);

    /**
     * 获得帮助信息分页
     *
     * @param pageReqVO 分页查询
     * @return 帮助信息分页
     */
    PageResult<HelpInfoDO> getHelpInfoPage(HelpInfoPageReqVO pageReqVO);

    /**
     * 获得帮助信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 帮助信息列表
     */
    List<HelpInfoDO> getHelpInfoList(HelpInfoExportReqVO exportReqVO);

}