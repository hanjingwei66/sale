package cn.iocoder.yudao.module.vx.service.vxUser;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.vx.controller.admin.vxUser.vo.*;
import cn.iocoder.yudao.module.vx.dal.dataobject.vxUser.VxUserDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.vx.convert.vxUser.VxUserConvert;
import cn.iocoder.yudao.module.vx.dal.mysql.vxUser.VxUserMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.vx.enums.ErrorCodeConstants.*;

/**
 * 微信用户信息 Service 实现类
 *
 * @author hjw
 */
@Service
@Validated
public class VxUserServiceImpl implements VxUserService {

    @Resource
    private VxUserMapper userMapper;

    @Override
    public Integer createUser(VxUserCreateReqVO createReqVO) {
        // 插入
        VxUserDO user = VxUserConvert.INSTANCE.convert(createReqVO);
        userMapper.insert(user);
        // 返回
        return user.getId();
    }

    @Override
    public void updateUser(VxUserUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateUserExists(updateReqVO.getId());
        // 更新
        VxUserDO updateObj = VxUserConvert.INSTANCE.convert(updateReqVO);
        userMapper.updateById(updateObj);
    }

    @Override
    public void deleteUser(Integer id) {
        // 校验存在
        this.validateUserExists(id);
        // 删除
        userMapper.deleteById(id);
    }

    private void validateUserExists(Integer id) {
        if (userMapper.selectById(id) == null) {
            throw exception(USER_NOT_EXISTS);
        }
    }

    @Override
    public VxUserDO getUser(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<VxUserDO> getUserList(Collection<Integer> ids) {
        return userMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<VxUserDO> getUserPage(VxUserPageReqVO pageReqVO) {
        return userMapper.selectPage(pageReqVO);
    }

    @Override
    public List<VxUserDO> getUserList(VxUserExportReqVO exportReqVO) {
        return userMapper.selectList(exportReqVO);
    }

}
