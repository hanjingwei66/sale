package cn.iocoder.yudao.module.system.service.group;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.system.controller.admin.group.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.group.GroupDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 用户组 Service 接口
 *
 * @author 芋道源码
 */
public interface GroupService {

    /**
     * 创建用户组
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createGroup(@Valid GroupCreateReqVO createReqVO);

    /**
     * 更新用户组
     *
     * @param updateReqVO 更新信息
     */
    void updateGroup(@Valid GroupUpdateReqVO updateReqVO);

    /**
     * 删除用户组
     *
     * @param id 编号
     */
    void deleteGroup(Long id);

    /**
     * 获得用户组
     *
     * @param id 编号
     * @return 用户组
     */
    GroupDO getGroup(Long id);

    /**
     * 获得用户组列表
     *
     * @param ids 编号
     * @return 用户组列表
     */
    List<GroupDO> getGroupList(Collection<Long> ids);

    /**
     * 获得用户组分页
     *
     * @param pageReqVO 分页查询
     * @return 用户组分页
     */
    PageResult<GroupDO> getGroupPage(GroupPageReqVO pageReqVO);

    /**
     * 获得用户组列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 用户组列表
     */
    List<GroupDO> getGroupList(GroupExportReqVO exportReqVO);

}
