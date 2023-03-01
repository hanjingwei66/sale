//package cn.iocoder.yudao.module.vx.service.helpinfo;
//
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import javax.annotation.Resource;
//
//import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;
//
//import cn.iocoder.yudao.module.vx.controller.app.helpinfo.vo.*;
//import cn.iocoder.yudao.module.vx.dal.dataobject.helpinfo.HelpInfoDO;
//import cn.iocoder.yudao.module.vx.dal.mysql.helpinfo.HelpInfoMapper;
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
//* {@link HelpInfoServiceImpl} 的单元测试类
//*
//* @author hjw
//*/
//@Import(HelpInfoServiceImpl.class)
//public class HelpInfoServiceImplTest extends BaseDbUnitTest {
//
//    @Resource
//    private HelpInfoServiceImpl helpInfoService;
//
//    @Resource
//    private HelpInfoMapper helpInfoMapper;
//
//    @Test
//    public void testCreateHelpInfo_success() {
//        // 准备参数
//        AppHelpInfoCreateReqVO reqVO = randomPojo(AppHelpInfoCreateReqVO.class);
//
//        // 调用
//        Integer helpInfoId = helpInfoService.createHelpInfo(reqVO);
//        // 断言
//        assertNotNull(helpInfoId);
//        // 校验记录的属性是否正确
//        HelpInfoDO helpInfo = helpInfoMapper.selectById(helpInfoId);
//        assertPojoEquals(reqVO, helpInfo);
//    }
//
//    @Test
//    public void testUpdateHelpInfo_success() {
//        // mock 数据
//        HelpInfoDO dbHelpInfo = randomPojo(HelpInfoDO.class);
//        helpInfoMapper.insert(dbHelpInfo);// @Sql: 先插入出一条存在的数据
//        // 准备参数
//        AppHelpInfoUpdateReqVO reqVO = randomPojo(AppHelpInfoUpdateReqVO.class, o -> {
//            o.setId(dbHelpInfo.getId()); // 设置更新的 ID
//        });
//
//        // 调用
//        helpInfoService.updateHelpInfo(reqVO);
//        // 校验是否更新正确
//        HelpInfoDO helpInfo = helpInfoMapper.selectById(reqVO.getId()); // 获取最新的
//        assertPojoEquals(reqVO, helpInfo);
//    }
//
//    @Test
//    public void testUpdateHelpInfo_notExists() {
//        // 准备参数
//        AppHelpInfoUpdateReqVO reqVO = randomPojo(AppHelpInfoUpdateReqVO.class);
//
//        // 调用, 并断言异常
//        assertServiceException(() -> helpInfoService.updateHelpInfo(reqVO), HELP_INFO_NOT_EXISTS);
//    }
//
//    @Test
//    public void testDeleteHelpInfo_success() {
//        // mock 数据
//        HelpInfoDO dbHelpInfo = randomPojo(HelpInfoDO.class);
//        helpInfoMapper.insert(dbHelpInfo);// @Sql: 先插入出一条存在的数据
//        // 准备参数
//        Integer id = dbHelpInfo.getId();
//
//        // 调用
//        helpInfoService.deleteHelpInfo(id);
//       // 校验数据不存在了
//       assertNull(helpInfoMapper.selectById(id));
//    }
//
//    @Test
//    public void testDeleteHelpInfo_notExists() {
//        // 准备参数
//        Integer id = randomIntegerId();
//
//        // 调用, 并断言异常
//        assertServiceException(() -> helpInfoService.deleteHelpInfo(id), HELP_INFO_NOT_EXISTS);
//    }
//
//    @Test
//    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
//    public void testGetHelpInfoPage() {
//       // mock 数据
//       HelpInfoDO dbHelpInfo = randomPojo(HelpInfoDO.class, o -> { // 等会查询到
//           o.setHelpName(null);
//           o.setHelpDetail(null);
//           o.setCreateTime(null);
//       });
//       helpInfoMapper.insert(dbHelpInfo);
//       // 测试 helpName 不匹配
//       helpInfoMapper.insert(cloneIgnoreId(dbHelpInfo, o -> o.setHelpName(null)));
//       // 测试 helpDetail 不匹配
//       helpInfoMapper.insert(cloneIgnoreId(dbHelpInfo, o -> o.setHelpDetail(null)));
//       // 测试 createTime 不匹配
//       helpInfoMapper.insert(cloneIgnoreId(dbHelpInfo, o -> o.setCreateTime(null)));
//       // 准备参数
//       AppHelpInfoPageReqVO reqVO = new AppHelpInfoPageReqVO();
//       reqVO.setHelpName(null);
//       reqVO.setHelpDetail(null);
//       reqVO.setBeginCreateTime(null);
//       reqVO.setEndCreateTime(null);
//
//       // 调用
//       PageResult<HelpInfoDO> pageResult = helpInfoService.getHelpInfoPage(reqVO);
//       // 断言
//       assertEquals(1, pageResult.getTotal());
//       assertEquals(1, pageResult.getList().size());
//       assertPojoEquals(dbHelpInfo, pageResult.getList().get(0));
//    }
//
//    @Test
//    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
//    public void testGetHelpInfoList() {
//       // mock 数据
//       HelpInfoDO dbHelpInfo = randomPojo(HelpInfoDO.class, o -> { // 等会查询到
//           o.setHelpName(null);
//           o.setHelpDetail(null);
//           o.setCreateTime(null);
//       });
//       helpInfoMapper.insert(dbHelpInfo);
//       // 测试 helpName 不匹配
//       helpInfoMapper.insert(cloneIgnoreId(dbHelpInfo, o -> o.setHelpName(null)));
//       // 测试 helpDetail 不匹配
//       helpInfoMapper.insert(cloneIgnoreId(dbHelpInfo, o -> o.setHelpDetail(null)));
//       // 测试 createTime 不匹配
//       helpInfoMapper.insert(cloneIgnoreId(dbHelpInfo, o -> o.setCreateTime(null)));
//       // 准备参数
//       AppHelpInfoExportReqVO reqVO = new AppHelpInfoExportReqVO();
//       reqVO.setHelpName(null);
//       reqVO.setHelpDetail(null);
//       reqVO.setBeginCreateTime(null);
//       reqVO.setEndCreateTime(null);
//
//       // 调用
//       List<HelpInfoDO> list = helpInfoService.getHelpInfoList(reqVO);
//       // 断言
//       assertEquals(1, list.size());
//       assertPojoEquals(dbHelpInfo, list.get(0));
//    }
//
//}
