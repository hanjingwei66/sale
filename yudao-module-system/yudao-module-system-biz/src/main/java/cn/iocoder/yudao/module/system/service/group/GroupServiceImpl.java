package cn.iocoder.yudao.module.system.service.group;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.system.controller.admin.group.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.group.GroupDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.system.convert.group.GroupConvert;
import cn.iocoder.yudao.module.system.dal.mysql.group.GroupMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 用户组 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class GroupServiceImpl implements GroupService {

    @Resource
    private GroupMapper groupMapper;

    @Override
    public Long createGroup(GroupCreateReqVO createReqVO) {
        // 插入
        GroupDO group = GroupConvert.INSTANCE.convert(createReqVO);
        groupMapper.insert(group);
        // 返回
        return group.getId();
    }

    @Override
    public void updateGroup(GroupUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateGroupExists(updateReqVO.getId());
        // 更新
        GroupDO updateObj = GroupConvert.INSTANCE.convert(updateReqVO);
        groupMapper.updateById(updateObj);
    }

    @Override
    public void deleteGroup(Long id) {
        // 校验存在
        this.validateGroupExists(id);
        // 删除
        groupMapper.deleteById(id);
    }

    private void validateGroupExists(Long id) {
        if (groupMapper.selectById(id) == null) {
            throw exception(GROUP_NOT_EXISTS);
        }
    }

    @Override
    public GroupDO getGroup(Long id) {
        return groupMapper.selectById(id);
    }

    @Override
    public List<GroupDO> getGroupList(Collection<Long> ids) {
        return groupMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<GroupDO> getGroupPage(GroupPageReqVO pageReqVO) {
        return groupMapper.selectPage(pageReqVO);
    }

    @Override
    public List<GroupDO> getGroupList(GroupExportReqVO exportReqVO) {
        return groupMapper.selectList(exportReqVO);
    }

}
