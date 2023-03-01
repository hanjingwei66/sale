package cn.iocoder.yudao.module.vx.service.shiyaoProject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.vx.controller.admin.shiyaoProject.vo.*;
import cn.iocoder.yudao.module.vx.dal.dataobject.shiyaoProject.ShiyaoProjectDO;
import cn.iocoder.yudao.module.vx.dal.mysql.shiyaoProject.ShiyaoProjectMapper;
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
* {@link ShiyaoProjectServiceImpl} 的单元测试类
*
* @author 芋道源码
*/
@Import(ShiyaoProjectServiceImpl.class)
public class ShiyaoProjectServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ShiyaoProjectServiceImpl shiyaoProjectService;

    @Resource
    private ShiyaoProjectMapper shiyaoProjectMapper;

    @Test
    public void testCreateShiyaoProject_success() {
        // 准备参数
        ShiyaoProjectCreateReqVO reqVO = randomPojo(ShiyaoProjectCreateReqVO.class);

        // 调用
        Integer shiyaoProjectId = shiyaoProjectService.createShiyaoProject(reqVO);
        // 断言
        assertNotNull(shiyaoProjectId);
        // 校验记录的属性是否正确
        ShiyaoProjectDO shiyaoProject = shiyaoProjectMapper.selectById(shiyaoProjectId);
        assertPojoEquals(reqVO, shiyaoProject);
    }

    @Test
    public void testUpdateShiyaoProject_success() {
        // mock 数据
        ShiyaoProjectDO dbShiyaoProject = randomPojo(ShiyaoProjectDO.class);
        shiyaoProjectMapper.insert(dbShiyaoProject);// @Sql: 先插入出一条存在的数据
        // 准备参数
        ShiyaoProjectUpdateReqVO reqVO = randomPojo(ShiyaoProjectUpdateReqVO.class, o -> {
            o.setId(dbShiyaoProject.getId()); // 设置更新的 ID
        });

        // 调用
        shiyaoProjectService.updateShiyaoProject(reqVO);
        // 校验是否更新正确
        ShiyaoProjectDO shiyaoProject = shiyaoProjectMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, shiyaoProject);
    }

    @Test
    public void testUpdateShiyaoProject_notExists() {
        // 准备参数
        ShiyaoProjectUpdateReqVO reqVO = randomPojo(ShiyaoProjectUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> shiyaoProjectService.updateShiyaoProject(reqVO), SHIYAO_PROJECT_NOT_EXISTS);
    }

    @Test
    public void testDeleteShiyaoProject_success() {
        // mock 数据
        ShiyaoProjectDO dbShiyaoProject = randomPojo(ShiyaoProjectDO.class);
        shiyaoProjectMapper.insert(dbShiyaoProject);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Integer id = dbShiyaoProject.getId();

        // 调用
        shiyaoProjectService.deleteShiyaoProject(id);
       // 校验数据不存在了
       assertNull(shiyaoProjectMapper.selectById(id));
    }

    @Test
    public void testDeleteShiyaoProject_notExists() {
        // 准备参数
//        Integer id = randomIntegerId();

        // 调用, 并断言异常
//        assertServiceException(() -> shiyaoProjectService.deleteShiyaoProject(id), SHIYAO_PROJECT_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetShiyaoProjectPage() {
       // mock 数据
       ShiyaoProjectDO dbShiyaoProject = randomPojo(ShiyaoProjectDO.class, o -> { // 等会查询到
           o.setProjectName(null);
           o.setAddress(null);
           o.setStatus(null);
           o.setCategory(null);
           o.setCreateTime(null);
       });
       shiyaoProjectMapper.insert(dbShiyaoProject);
       // 测试 projectName 不匹配
       shiyaoProjectMapper.insert(cloneIgnoreId(dbShiyaoProject, o -> o.setProjectName(null)));
       // 测试 address 不匹配
       shiyaoProjectMapper.insert(cloneIgnoreId(dbShiyaoProject, o -> o.setAddress(null)));
       // 测试 status 不匹配
       shiyaoProjectMapper.insert(cloneIgnoreId(dbShiyaoProject, o -> o.setStatus(null)));
       // 测试 category 不匹配
       shiyaoProjectMapper.insert(cloneIgnoreId(dbShiyaoProject, o -> o.setCategory(null)));
       // 测试 createTime 不匹配
       shiyaoProjectMapper.insert(cloneIgnoreId(dbShiyaoProject, o -> o.setCreateTime(null)));
       // 准备参数
       ShiyaoProjectPageReqVO reqVO = new ShiyaoProjectPageReqVO();
       reqVO.setProjectName(null);
       reqVO.setAddress(null);
       reqVO.setStatus(null);
       reqVO.setCategory(null);
       reqVO.setBeginCreateTime(null);
       reqVO.setEndCreateTime(null);

       // 调用
       PageResult<ShiyaoProjectDO> pageResult = shiyaoProjectService.getShiyaoProjectPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbShiyaoProject, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetShiyaoProjectList() {
       // mock 数据
       ShiyaoProjectDO dbShiyaoProject = randomPojo(ShiyaoProjectDO.class, o -> { // 等会查询到
           o.setProjectName(null);
           o.setAddress(null);
           o.setStatus(null);
           o.setCategory(null);
           o.setCreateTime(null);
       });
       shiyaoProjectMapper.insert(dbShiyaoProject);
       // 测试 projectName 不匹配
       shiyaoProjectMapper.insert(cloneIgnoreId(dbShiyaoProject, o -> o.setProjectName(null)));
       // 测试 address 不匹配
       shiyaoProjectMapper.insert(cloneIgnoreId(dbShiyaoProject, o -> o.setAddress(null)));
       // 测试 status 不匹配
       shiyaoProjectMapper.insert(cloneIgnoreId(dbShiyaoProject, o -> o.setStatus(null)));
       // 测试 category 不匹配
       shiyaoProjectMapper.insert(cloneIgnoreId(dbShiyaoProject, o -> o.setCategory(null)));
       // 测试 createTime 不匹配
       shiyaoProjectMapper.insert(cloneIgnoreId(dbShiyaoProject, o -> o.setCreateTime(null)));
       // 准备参数
       ShiyaoProjectExportReqVO reqVO = new ShiyaoProjectExportReqVO();
       reqVO.setProjectName(null);
       reqVO.setAddress(null);
       reqVO.setStatus(null);
       reqVO.setCategory(null);
       reqVO.setBeginCreateTime(null);
       reqVO.setEndCreateTime(null);

       // 调用
       List<ShiyaoProjectDO> list = shiyaoProjectService.getShiyaoProjectList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbShiyaoProject, list.get(0));
    }

}
