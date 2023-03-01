package cn.iocoder.yudao.module.vx.service.shiyaoProject;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.vx.controller.admin.shiyaoProject.vo.*;
import cn.iocoder.yudao.module.vx.dal.dataobject.shiyaoProject.ShiyaoProjectDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 试药项目 Service 接口
 *
 * @author 芋道源码
 */
public interface ShiyaoProjectService {

    /**
     * 创建试药项目
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createShiyaoProject(@Valid ShiyaoProjectCreateReqVO createReqVO);

    /**
     * 更新试药项目
     *
     * @param updateReqVO 更新信息
     */
    void updateShiyaoProject(@Valid ShiyaoProjectUpdateReqVO updateReqVO);

    /**
     * 删除试药项目
     *
     * @param id 编号
     */
    void deleteShiyaoProject(Integer id);

    /**
     * 获得试药项目
     *
     * @param id 编号
     * @return 试药项目
     */
    ShiyaoProjectDO getShiyaoProject(Integer id);

    /**
     * 获得试药项目列表
     *
     * @param ids 编号
     * @return 试药项目列表
     */
    List<ShiyaoProjectDO> getShiyaoProjectList(Collection<Integer> ids);

    /**
     * 获得试药项目分页
     *
     * @param pageReqVO 分页查询
     * @return 试药项目分页
     */
    PageResult<ShiyaoProjectDO> getShiyaoProjectPage(ShiyaoProjectPageReqVO pageReqVO);

    /**
     * 获得试药项目列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 试药项目列表
     */
    List<ShiyaoProjectDO> getShiyaoProjectList(ShiyaoProjectExportReqVO exportReqVO);

    List<ShiyaoProjectDO> getShiyaoProjectList();

}