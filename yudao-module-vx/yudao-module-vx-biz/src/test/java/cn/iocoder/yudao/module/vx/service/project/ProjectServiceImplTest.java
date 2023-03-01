//package cn.iocoder.yudao.module.vx.service.project;
//
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import javax.annotation.Resource;
//
//import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;
//
////import cn.iocoder.yudao.module.vx.controller.admin.project.vo.*;
////import cn.iocoder.yudao.module.vx.dal.dataobject.project.ProjectDO;
////import cn.iocoder.yudao.module.vx.dal.mysql.project.ProjectMapper;
//import cn.iocoder.yudao.framework.common.pojo.PageResult;
//
//import javax.annotation.Resource;
//import org.springframework.context.annotation.Import;
//import java.util.*;
//
//import static cn.hutool.core.util.RandomUtil.*;
//import static cn.iocoder.yudao.module.vx.enums.ErrorCodeConstants.*;
//import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
//import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
//import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
//import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
///**
//* {@link ProjectServiceImpl} 的单元测试类
//*
//* @author 芋道源码
//*/
//@Import(ProjectServiceImpl.class)
//public class ProjectServiceImplTest extends BaseDbUnitTest {
//
//    @Resource
//    private ProjectServiceImpl projectService;
//
//    @Resource
//    private ProjectMapper projectMapper;
//
//    @Test
//    public void testCreateProject_success() {
//        // 准备参数
//        ProjectCreateReqVO reqVO = randomPojo(ProjectCreateReqVO.class);
//
//        // 调用
//        Integer projectId = projectService.createProject(reqVO);
//        // 断言
//        assertNotNull(projectId);
//        // 校验记录的属性是否正确
//        ProjectDO project = projectMapper.selectById(projectId);
//        assertPojoEquals(reqVO, project);
//    }
//
//    @Test
//    public void testUpdateProject_success() {
//        // mock 数据
//        ProjectDO dbProject = randomPojo(ProjectDO.class);
//        projectMapper.insert(dbProject);// @Sql: 先插入出一条存在的数据
//        // 准备参数
//        ProjectUpdateReqVO reqVO = randomPojo(ProjectUpdateReqVO.class, o -> {
//            o.setId(dbProject.getId()); // 设置更新的 ID
//        });
//
//        // 调用
//        projectService.updateProject(reqVO);
//        // 校验是否更新正确
//        ProjectDO project = projectMapper.selectById(reqVO.getId()); // 获取最新的
//        assertPojoEquals(reqVO, project);
//    }
//
//    @Test
//    public void testUpdateProject_notExists() {
//        // 准备参数
//        ProjectUpdateReqVO reqVO = randomPojo(ProjectUpdateReqVO.class);
//
//        // 调用, 并断言异常
//        assertServiceException(() -> projectService.updateProject(reqVO), PROJECT_NOT_EXISTS);
//    }
//
//    @Test
//    public void testDeleteProject_success() {
//        // mock 数据
//        ProjectDO dbProject = randomPojo(ProjectDO.class);
//        projectMapper.insert(dbProject);// @Sql: 先插入出一条存在的数据
//        // 准备参数
//        Integer id = dbProject.getId();
//
//        // 调用
//        projectService.deleteProject(id);
//       // 校验数据不存在了
//       assertNull(projectMapper.selectById(id));
//    }
//
//    @Test
//    public void testDeleteProject_notExists() {
//        // 准备参数
////        Integer id = randomIntegerId();
//
//        // 调用, 并断言异常
////        assertServiceException(() -> projectService.deleteProject(id), PROJECT_NOT_EXISTS);
//    }
//
//    @Test
//    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
//    public void testGetProjectPage() {
//       // mock 数据
//       ProjectDO dbProject = randomPojo(ProjectDO.class, o -> { // 等会查询到
//           o.setProjectName(null);
//           o.setGoodsName(null);
//           o.setAddress(null);
//           o.setAddressDetails(null);
//           o.setSex(null);
//           o.setInpDays(null);
//           o.setPriceMan(null);
//           o.setPriceWoman(null);
//           o.setMinAge(null);
//           o.setMaxAge(null);
//           o.setMinBmi(null);
//           o.setMaxBmi(null);
//           o.setStatus(null);
//           o.setCategory(null);
//           o.setIsSmoke(null);
//           o.setSort(null);
//           o.setStartTime(null);
//           o.setStopTime(null);
//           o.setContext(null);
//           o.setCreateTime(null);
//       });
//       projectMapper.insert(dbProject);
//       // 测试 projectName 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setProjectName(null)));
//       // 测试 goodsName 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setGoodsName(null)));
//       // 测试 address 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setAddress(null)));
//       // 测试 addressDetails 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setAddressDetails(null)));
//       // 测试 sex 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setSex(null)));
//       // 测试 inpDays 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setInpDays(null)));
//       // 测试 priceMan 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setPriceMan(null)));
//       // 测试 priceWoman 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setPriceWoman(null)));
//       // 测试 minAge 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setMinAge(null)));
//       // 测试 maxAge 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setMaxAge(null)));
//       // 测试 minBmi 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setMinBmi(null)));
//       // 测试 maxBmi 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setMaxBmi(null)));
//       // 测试 status 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setStatus(null)));
//       // 测试 category 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setCategory(null)));
//       // 测试 isSmoke 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setIsSmoke(null)));
//       // 测试 sort 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setSort(null)));
//       // 测试 startTime 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setStartTime(null)));
//       // 测试 stopTime 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setStopTime(null)));
//       // 测试 context 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setContext(null)));
//       // 测试 createTime 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setCreateTime(null)));
//       // 准备参数
//       ProjectPageReqVO reqVO = new ProjectPageReqVO();
//       reqVO.setProjectName(null);
//       reqVO.setGoodsName(null);
//       reqVO.setAddress(null);
//       reqVO.setAddressDetails(null);
//       reqVO.setSex(null);
//       reqVO.setInpDays(null);
//       reqVO.setPriceMan(null);
//       reqVO.setPriceWoman(null);
//       reqVO.setMinAge(null);
//       reqVO.setMaxAge(null);
//       reqVO.setMinBmi(null);
//       reqVO.setMaxBmi(null);
//       reqVO.setStatus(null);
//       reqVO.setCategory(null);
//       reqVO.setIsSmoke(null);
//       reqVO.setSort(null);
//       reqVO.setBeginStartTime(null);
//       reqVO.setEndStartTime(null);
//       reqVO.setBeginStopTime(null);
//       reqVO.setEndStopTime(null);
//       reqVO.setContext(null);
//       reqVO.setBeginCreateTime(null);
//       reqVO.setEndCreateTime(null);
//
//       // 调用
//       PageResult<ProjectDO> pageResult = projectService.getProjectPage(reqVO);
//       // 断言
//       assertEquals(1, pageResult.getTotal());
//       assertEquals(1, pageResult.getList().size());
//       assertPojoEquals(dbProject, pageResult.getList().get(0));
//    }
//
//    @Test
//    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
//    public void testGetProjectList() {
//       // mock 数据
//       ProjectDO dbProject = randomPojo(ProjectDO.class, o -> { // 等会查询到
//           o.setProjectName(null);
//           o.setGoodsName(null);
//           o.setAddress(null);
//           o.setAddressDetails(null);
//           o.setSex(null);
//           o.setInpDays(null);
//           o.setPriceMan(null);
//           o.setPriceWoman(null);
//           o.setMinAge(null);
//           o.setMaxAge(null);
//           o.setMinBmi(null);
//           o.setMaxBmi(null);
//           o.setStatus(null);
//           o.setCategory(null);
//           o.setIsSmoke(null);
//           o.setSort(null);
//           o.setStartTime(null);
//           o.setStopTime(null);
//           o.setContext(null);
//           o.setCreateTime(null);
//       });
//       projectMapper.insert(dbProject);
//       // 测试 projectName 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setProjectName(null)));
//       // 测试 goodsName 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setGoodsName(null)));
//       // 测试 address 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setAddress(null)));
//       // 测试 addressDetails 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setAddressDetails(null)));
//       // 测试 sex 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setSex(null)));
//       // 测试 inpDays 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setInpDays(null)));
//       // 测试 priceMan 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setPriceMan(null)));
//       // 测试 priceWoman 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setPriceWoman(null)));
//       // 测试 minAge 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setMinAge(null)));
//       // 测试 maxAge 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setMaxAge(null)));
//       // 测试 minBmi 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setMinBmi(null)));
//       // 测试 maxBmi 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setMaxBmi(null)));
//       // 测试 status 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setStatus(null)));
//       // 测试 category 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setCategory(null)));
//       // 测试 isSmoke 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setIsSmoke(null)));
//       // 测试 sort 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setSort(null)));
//       // 测试 startTime 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setStartTime(null)));
//       // 测试 stopTime 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setStopTime(null)));
//       // 测试 context 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setContext(null)));
//       // 测试 createTime 不匹配
//       projectMapper.insert(cloneIgnoreId(dbProject, o -> o.setCreateTime(null)));
//       // 准备参数
//       ProjectExportReqVO reqVO = new ProjectExportReqVO();
//       reqVO.setProjectName(null);
//       reqVO.setGoodsName(null);
//       reqVO.setAddress(null);
//       reqVO.setAddressDetails(null);
//       reqVO.setSex(null);
//       reqVO.setInpDays(null);
//       reqVO.setPriceMan(null);
//       reqVO.setPriceWoman(null);
//       reqVO.setMinAge(null);
//       reqVO.setMaxAge(null);
//       reqVO.setMinBmi(null);
//       reqVO.setMaxBmi(null);
//       reqVO.setStatus(null);
//       reqVO.setCategory(null);
//       reqVO.setIsSmoke(null);
//       reqVO.setSort(null);
//       reqVO.setBeginStartTime(null);
//       reqVO.setEndStartTime(null);
//       reqVO.setBeginStopTime(null);
//       reqVO.setEndStopTime(null);
//       reqVO.setContext(null);
//       reqVO.setBeginCreateTime(null);
//       reqVO.setEndCreateTime(null);
//
//       // 调用
//       List<ProjectDO> list = projectService.getProjectList(reqVO);
//       // 断言
//       assertEquals(1, list.size());
//       assertPojoEquals(dbProject, list.get(0));
//    }
//
//}
