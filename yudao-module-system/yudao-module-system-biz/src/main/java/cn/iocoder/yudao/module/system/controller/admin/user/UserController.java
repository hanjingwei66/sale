package cn.iocoder.yudao.module.system.controller.admin.user;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.module.system.controller.admin.quota.vo.QuotaCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.quato.UserQuatoRespVO;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.user.*;
import cn.iocoder.yudao.module.system.convert.user.UserConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.system.dal.dataobject.quota.QuotaActualStatisticsDO;
import cn.iocoder.yudao.module.system.dal.dataobject.quota.QuotaDO;
import cn.iocoder.yudao.module.system.dal.dataobject.quota.QuotaStatisticsDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.service.dept.DeptService;
import cn.iocoder.yudao.module.system.service.quota.QuotaService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import cn.iocoder.yudao.module.system.enums.common.SexEnum;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.collection.MapUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Api(tags = "???????????? - ??????")
@RestController
@RequestMapping("/system/user")
@Validated
public class UserController {

    @Resource
    private AdminUserService userService;
    @Resource
    private DeptService deptService;
    @Resource
    private QuotaService quotaService;

    @PostMapping("/create")
    @ApiOperation("????????????")
    @PreAuthorize("@ss.hasPermission('system:user:create')")
    public CommonResult<Long> createUser(@Valid @RequestBody UserCreateReqVO reqVO) {
        Long id = userService.createUser(reqVO);
        return success(id);
    }

    @PutMapping("update")
    @ApiOperation("????????????")
    @PreAuthorize("@ss.hasPermission('system:user:update')")
    public CommonResult<Boolean> updateUser(@Valid @RequestBody UserUpdateReqVO reqVO) {
        userService.updateUser(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("????????????")
    @ApiImplicitParam(name = "id", value = "??????", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:user:delete')")
    public CommonResult<Boolean> deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return success(true);
    }

    @PutMapping("/update-password")
    @ApiOperation("??????????????????")
    @PreAuthorize("@ss.hasPermission('system:user:update-password')")
    public CommonResult<Boolean> updateUserPassword(@Valid @RequestBody UserUpdatePasswordReqVO reqVO) {
        userService.updateUserPassword(reqVO.getId(), reqVO.getPassword());
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("??????????????????")
    @PreAuthorize("@ss.hasPermission('system:user:update')")
    public CommonResult<Boolean> updateUserStatus(@Valid @RequestBody UserUpdateStatusReqVO reqVO) {
        userService.updateUserStatus(reqVO.getId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping("/page")
    @ApiOperation("????????????????????????")
    @PreAuthorize("@ss.hasPermission('system:user:list')")
    public CommonResult<PageResult<UserPageItemRespVO>> getUserPage(@Valid UserPageReqVO reqVO) {
        // ????????????????????????
        PageResult<AdminUserDO> pageResult = userService.getUserPage(reqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(new PageResult<>(pageResult.getTotal())); // ?????????
        }

        // ???????????????????????????
        Collection<Long> deptIds = convertList(pageResult.getList(), AdminUserDO::getDeptId);
        Map<Long, DeptDO> deptMap = deptService.getDeptMap(deptIds);
        // ??????????????????
        List<UserPageItemRespVO> userList = new ArrayList<>(pageResult.getList().size());
        pageResult.getList().forEach(user -> {
            UserPageItemRespVO respVO = UserConvert.INSTANCE.convert(user);
            respVO.setDept(UserConvert.INSTANCE.convert(deptMap.get(user.getDeptId())));
            userList.add(respVO);
        });
        return success(new PageResult<>(userList, pageResult.getTotal()));
    }
    @GetMapping("/allocationQuota")
    @ApiOperation("????????????")
    public CommonResult<Long> allocationQuota(@Valid QuotaCreateReqVO createReqVO){
        // ????????????????????????
        Long id = quotaService.createQuota(createReqVO);
        return success(id);
    }
    @GetMapping("/getQuota")
    @ApiOperation("????????????")
    public CommonResult<PageResult<UserQuatoRespVO>> getQuota(@Valid UserPageReqVO reqVO){
        // ????????????????????????
        PageResult<AdminUserDO> pageResult = userService.getUserPage(reqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(new PageResult<>(pageResult.getTotal())); // ?????????
        }
        // ???????????????????????????
        Collection<Long> deptIds = convertList(pageResult.getList(), AdminUserDO::getDeptId);
        Map<Long, DeptDO> deptMap = deptService.getDeptMap(deptIds);
        // ??????????????????
        List<UserQuatoRespVO> userList = new ArrayList<>(pageResult.getList().size());
        pageResult.getList().forEach(user -> {
            UserQuatoRespVO userQuatoRespVO = UserConvert.INSTANCE.convert3(user);
            List<QuotaDO> QuotaDOs = quotaService.getQuotaListByUserId(user.getId());
            if(null==QuotaDOs){
                QuotaDO init = new QuotaDO();
                QuotaDOs.add(init);
            }
            userQuatoRespVO.setQuotas(QuotaDOs);
            userQuatoRespVO.setDept(UserConvert.INSTANCE.convert(deptMap.get(user.getDeptId())));
            userList.add(userQuatoRespVO);
        });
        return  success(new PageResult<>(userList, pageResult.getTotal()));
    }

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "??????????????????????????????", notes = "???????????????????????????????????????????????????????????????")
    public CommonResult<List<UserSimpleRespVO>> getSimpleUsers() {
        // ??????????????????????????????????????????
        List<AdminUserDO> list = userService.getUsersByStatus(CommonStatusEnum.ENABLE.getStatus());
        // ???????????????????????????
        return success(UserConvert.INSTANCE.convertList04(list));
    }

    @GetMapping("/list-all-user-name")
    @ApiOperation(value = "??????????????????????????????", notes = "???????????????????????????????????????????????????????????????")
    public CommonResult<Map<String , List>> getAllUserNames() {
        // ??????????????????????????????????????????
        List<AdminUserDO> list = userService.getUsersByStatus(CommonStatusEnum.ENABLE.getStatus());
        List<String> collect = list.stream().map(item -> item.getNickname()).collect(Collectors.toList());
        List<QuotaStatisticsDO> expect = quotaService.selectExpectEveryOne();
        Map<String , List> result = new HashMap<>();
        List<String> nickNameList = expect.stream().map(quotaStatisticsDO -> quotaStatisticsDO.getNickName()).collect(Collectors.toList());
        List<Long> actualList = expect.stream().map(quotaStatisticsDO -> quotaStatisticsDO.getQuotaActualSum()).collect(Collectors.toList());
        List<Long> expectList = expect.stream().map(quotaStatisticsDO -> quotaStatisticsDO.getQuotaExpetSum()).collect(Collectors.toList());
        result.put("nickName",nickNameList);
        result.put("actual",actualList);
        result.put("expect",expectList);
        // ???????????????????????????
        return success(result);
    }
    @GetMapping("/get")
    @ApiOperation("??????????????????")
    @ApiImplicitParam(name = "id", value = "??????", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:user:query')")
    public CommonResult<UserRespVO> getInfo(@RequestParam("id") Long id) {
        return success(UserConvert.INSTANCE.convert(userService.getUser(id)));
    }

    @GetMapping("/export")
    @ApiOperation("????????????")
    @PreAuthorize("@ss.hasPermission('system:user:export')")
    @OperateLog(type = EXPORT)
    public void exportUsers(@Validated UserExportReqVO reqVO,
                            HttpServletResponse response) throws IOException {
        // ??????????????????
        List<AdminUserDO> users = userService.getUsers(reqVO);

        // ???????????????????????????
        Collection<Long> deptIds = convertList(users, AdminUserDO::getDeptId);
        Map<Long, DeptDO> deptMap = deptService.getDeptMap(deptIds);
        Map<Long, AdminUserDO> deptLeaderUserMap = userService.getUserMap(
                convertSet(deptMap.values(), DeptDO::getLeaderUserId));
        // ????????????
        List<UserExcelVO> excelUsers = new ArrayList<>(users.size());
        users.forEach(user -> {
            UserExcelVO excelVO = UserConvert.INSTANCE.convert02(user);
            // ????????????
            MapUtils.findAndThen(deptMap, user.getDeptId(), dept -> {
                excelVO.setDeptName(dept.getName());
                // ??????????????????????????????
                MapUtils.findAndThen(deptLeaderUserMap, dept.getLeaderUserId(),
                        deptLeaderUser -> excelVO.setDeptLeaderNickname(deptLeaderUser.getNickname()));
            });
            excelUsers.add(excelVO);
        });

        // ??????
        ExcelUtils.write(response, "????????????.xls", "????????????", UserExcelVO.class, excelUsers);
    }

    @GetMapping("/get-import-template")
    @ApiOperation("????????????????????????")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // ?????????????????? demo
        List<UserImportExcelVO> list = Arrays.asList(
                UserImportExcelVO.builder().username("yunai").deptId(1L).email("yunai@iocoder.cn").mobile("15601691300")
                        .nickname("?????????").status(CommonStatusEnum.ENABLE.getStatus()).sex(SexEnum.MALE.getSex()).build(),
                UserImportExcelVO.builder().username("yuanma").deptId(2L).email("yuanma@iocoder.cn").mobile("15601701300")
                        .nickname("??????").status(CommonStatusEnum.DISABLE.getStatus()).sex(SexEnum.FEMALE.getSex()).build()
        );

        // ??????
        ExcelUtils.write(response, "??????????????????.xls", "????????????", UserImportExcelVO.class, list);
    }

    @PostMapping("/import")
    @ApiOperation("????????????")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "Excel ??????", required = true, dataTypeClass = MultipartFile.class),
            @ApiImplicitParam(name = "updateSupport", value = "?????????????????????????????? false", example = "true", dataTypeClass = Boolean.class)
    })
    @PreAuthorize("@ss.hasPermission('system:user:import')")
    public CommonResult<UserImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                      @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<UserImportExcelVO> list = ExcelUtils.read(file, UserImportExcelVO.class);
        return success(userService.importUsers(list, updateSupport));
    }

}
