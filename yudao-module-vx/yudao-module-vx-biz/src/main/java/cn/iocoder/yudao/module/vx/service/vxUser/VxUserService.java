package cn.iocoder.yudao.module.vx.service.vxUser;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.vx.controller.admin.vxUser.vo.*;
import cn.iocoder.yudao.module.vx.dal.dataobject.vxUser.VxUserDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 微信用户信息 Service 接口
 *
 * @author hjw
 */
public interface VxUserService {

    /**
     * 创建微信用户信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createUser(@Valid VxUserCreateReqVO createReqVO);

    /**
     * 更新微信用户信息
     *
     * @param updateReqVO 更新信息
     */
    void updateUser(@Valid VxUserUpdateReqVO updateReqVO);

    /**
     * 删除微信用户信息
     *
     * @param id 编号
     */
    void deleteUser(Integer id);

    /**
     * 获得微信用户信息
     *
     * @param id 编号
     * @return 微信用户信息
     */
    VxUserDO getUser(Integer id);

    /**
     * 获得微信用户信息列表
     *
     * @param ids 编号
     * @return 微信用户信息列表
     */
    List<VxUserDO> getUserList(Collection<Integer> ids);

    /**
     * 获得微信用户信息分页
     *
     * @param pageReqVO 分页查询
     * @return 微信用户信息分页
     */
    PageResult<VxUserDO> getUserPage(VxUserPageReqVO pageReqVO);

    /**
     * 获得微信用户信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 微信用户信息列表
     */
    List<VxUserDO> getUserList(VxUserExportReqVO exportReqVO);

}