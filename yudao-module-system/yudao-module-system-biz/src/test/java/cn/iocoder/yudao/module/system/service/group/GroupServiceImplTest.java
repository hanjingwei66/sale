package cn.iocoder.yudao.module.system.service.group;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.system.controller.admin.group.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.group.GroupDO;
import cn.iocoder.yudao.module.system.dal.mysql.group.GroupMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;

import static cn.hutool.core.util.RandomUtil.*;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
* {@link GroupServiceImpl} 的单元测试类
*
* @author 芋道源码
*/
@Import(GroupServiceImpl.class)
public class GroupServiceImplTest extends BaseDbUnitTest {

    @Resource
    private GroupServiceImpl groupService;

    @Resource
    private GroupMapper groupMapper;

    @Test
    public void testCreateGroup_success() {
        // 准备参数
        GroupCreateReqVO reqVO = randomPojo(GroupCreateReqVO.class);

        // 调用
        Long groupId = groupService.createGroup(reqVO);
        // 断言
        assertNotNull(groupId);
        // 校验记录的属性是否正确
        GroupDO group = groupMapper.selectById(groupId);
        assertPojoEquals(reqVO, group);
    }

    @Test
    public void testUpdateGroup_success() {
        // mock 数据
        GroupDO dbGroup = randomPojo(GroupDO.class);
        groupMapper.insert(dbGroup);// @Sql: 先插入出一条存在的数据
        // 准备参数
        GroupUpdateReqVO reqVO = randomPojo(GroupUpdateReqVO.class, o -> {
            o.setId(dbGroup.getId()); // 设置更新的 ID
        });

        // 调用
        groupService.updateGroup(reqVO);
        // 校验是否更新正确
        GroupDO group = groupMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, group);
    }

    @Test
    public void testUpdateGroup_notExists() {
        // 准备参数
        GroupUpdateReqVO reqVO = randomPojo(GroupUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> groupService.updateGroup(reqVO), GROUP_NOT_EXISTS);
    }

    @Test
    public void testDeleteGroup_success() {
        // mock 数据
        GroupDO dbGroup = randomPojo(GroupDO.class);
        groupMapper.insert(dbGroup);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbGroup.getId();

        // 调用
        groupService.deleteGroup(id);
       // 校验数据不存在了
       assertNull(groupMapper.selectById(id));
    }

    @Test
    public void testDeleteGroup_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> groupService.deleteGroup(id), GROUP_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetGroupPage() {
       // mock 数据
       GroupDO dbGroup = randomPojo(GroupDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setDescription(null);
           o.setStatus(null);
           o.setCreateTime(null);
       });
       groupMapper.insert(dbGroup);
       // 测试 name 不匹配
       groupMapper.insert(cloneIgnoreId(dbGroup, o -> o.setName(null)));
       // 测试 description 不匹配
       groupMapper.insert(cloneIgnoreId(dbGroup, o -> o.setDescription(null)));
       // 测试 status 不匹配
       groupMapper.insert(cloneIgnoreId(dbGroup, o -> o.setStatus(null)));
       // 测试 createTime 不匹配
       groupMapper.insert(cloneIgnoreId(dbGroup, o -> o.setCreateTime(null)));
       // 准备参数
       GroupPageReqVO reqVO = new GroupPageReqVO();
       reqVO.setName(null);
       reqVO.setDescription(null);
       reqVO.setStatus(null);
       reqVO.setBeginCreateTime(null);
       reqVO.setEndCreateTime(null);

       // 调用
       PageResult<GroupDO> pageResult = groupService.getGroupPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbGroup, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetGroupList() {
       // mock 数据
       GroupDO dbGroup = randomPojo(GroupDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setDescription(null);
           o.setStatus(null);
           o.setCreateTime(null);
       });
       groupMapper.insert(dbGroup);
       // 测试 name 不匹配
       groupMapper.insert(cloneIgnoreId(dbGroup, o -> o.setName(null)));
       // 测试 description 不匹配
       groupMapper.insert(cloneIgnoreId(dbGroup, o -> o.setDescription(null)));
       // 测试 status 不匹配
       groupMapper.insert(cloneIgnoreId(dbGroup, o -> o.setStatus(null)));
       // 测试 createTime 不匹配
       groupMapper.insert(cloneIgnoreId(dbGroup, o -> o.setCreateTime(null)));
       // 准备参数
       GroupExportReqVO reqVO = new GroupExportReqVO();
       reqVO.setName(null);
       reqVO.setDescription(null);
       reqVO.setStatus(null);
       reqVO.setBeginCreateTime(null);
       reqVO.setEndCreateTime(null);

       // 调用
       List<GroupDO> list = groupService.getGroupList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbGroup, list.get(0));
    }

}
