package cn.iocoder.yudao.module.vx.service.vxUser;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.vx.controller.admin.vxUser.vo.*;
import cn.iocoder.yudao.module.vx.dal.dataobject.vxUser.VxUserDO;
import cn.iocoder.yudao.module.vx.dal.mysql.vxUser.VxUserMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;

import static cn.hutool.core.util.RandomUtil.*;
import static cn.iocoder.yudao.module.vx.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
* {@link VxUserServiceImpl} 的单元测试类
*
* @author hjw
*/
@Import(VxUserServiceImpl.class)
public class VxUserServiceImplTest extends BaseDbUnitTest {

    @Resource
    private VxUserServiceImpl userService;

    @Resource
    private VxUserMapper userMapper;

    @Test
    public void testCreateUser_success() {
        // 准备参数
        VxUserCreateReqVO reqVO = randomPojo(VxUserCreateReqVO.class);

        // 调用
        Integer userId = userService.createUser(reqVO);
        // 断言
        assertNotNull(userId);
        // 校验记录的属性是否正确
        VxUserDO user = userMapper.selectById(userId);
        assertPojoEquals(reqVO, user);
    }

    @Test
    public void testUpdateUser_success() {
        // mock 数据
        VxUserDO dbUser = randomPojo(VxUserDO.class);
        userMapper.insert(dbUser);// @Sql: 先插入出一条存在的数据
        // 准备参数
        VxUserUpdateReqVO reqVO = randomPojo(VxUserUpdateReqVO.class, o -> {
            o.setId(dbUser.getId()); // 设置更新的 ID
        });

        // 调用
        userService.updateUser(reqVO);
        // 校验是否更新正确
        VxUserDO user = userMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, user);
    }

    @Test
    public void testUpdateUser_notExists() {
        // 准备参数
        VxUserUpdateReqVO reqVO = randomPojo(VxUserUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> userService.updateUser(reqVO), USER_NOT_EXISTS);
    }

    @Test
    public void testDeleteUser_success() {
        // mock 数据
        VxUserDO dbUser = randomPojo(VxUserDO.class);
        userMapper.insert(dbUser);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Integer id = dbUser.getId();

        // 调用
        userService.deleteUser(id);
       // 校验数据不存在了
       assertNull(userMapper.selectById(id));
    }

//    @Test
//    public void testDeleteUser_notExists() {
//        // 准备参数
////        Integer id = randomIntegerId();
//
//        // 调用, 并断言异常
//        assertServiceException(() -> userService.deleteUser(id), USER_NOT_EXISTS);
//    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetUserPage() {
       // mock 数据
       VxUserDO dbUser = randomPojo(VxUserDO.class, o -> { // 等会查询到
           o.setVxId(null);
           o.setUserName(null);
           o.setUserCardId(null);
           o.setCreateTime(null);
       });
       userMapper.insert(dbUser);
       // 测试 vxId 不匹配
       userMapper.insert(cloneIgnoreId(dbUser, o -> o.setVxId(null)));
       // 测试 userName 不匹配
       userMapper.insert(cloneIgnoreId(dbUser, o -> o.setUserName(null)));
       // 测试 userCardId 不匹配
       userMapper.insert(cloneIgnoreId(dbUser, o -> o.setUserCardId(null)));
       // 测试 createTime 不匹配
       userMapper.insert(cloneIgnoreId(dbUser, o -> o.setCreateTime(null)));
       // 准备参数
       VxUserPageReqVO reqVO = new VxUserPageReqVO();
       reqVO.setVxId(null);
       reqVO.setUserName(null);
       reqVO.setUserCardId(null);
       reqVO.setBeginCreateTime(null);
       reqVO.setEndCreateTime(null);

       // 调用
       PageResult<VxUserDO> pageResult = userService.getUserPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbUser, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetUserList() {
       // mock 数据
       VxUserDO dbUser = randomPojo(VxUserDO.class, o -> { // 等会查询到
           o.setVxId(null);
           o.setUserName(null);
           o.setUserCardId(null);
           o.setCreateTime(null);
       });
       userMapper.insert(dbUser);
       // 测试 vxId 不匹配
       userMapper.insert(cloneIgnoreId(dbUser, o -> o.setVxId(null)));
       // 测试 userName 不匹配
       userMapper.insert(cloneIgnoreId(dbUser, o -> o.setUserName(null)));
       // 测试 userCardId 不匹配
       userMapper.insert(cloneIgnoreId(dbUser, o -> o.setUserCardId(null)));
       // 测试 createTime 不匹配
       userMapper.insert(cloneIgnoreId(dbUser, o -> o.setCreateTime(null)));
       // 准备参数
       VxUserExportReqVO reqVO = new VxUserExportReqVO();
       reqVO.setVxId(null);
       reqVO.setUserName(null);
       reqVO.setUserCardId(null);
       reqVO.setBeginCreateTime(null);
       reqVO.setEndCreateTime(null);

       // 调用
       List<VxUserDO> list = userService.getUserList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbUser, list.get(0));
    }

}
