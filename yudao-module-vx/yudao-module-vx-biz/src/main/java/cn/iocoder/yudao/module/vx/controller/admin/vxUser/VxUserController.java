package cn.iocoder.yudao.module.vx.controller.admin.vxUser;

import cn.iocoder.yudao.module.vx.service.vxUserProject.VxUserProjectService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.*;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.vx.controller.admin.vxUser.vo.*;
import cn.iocoder.yudao.module.vx.dal.dataobject.vxUser.VxUserDO;
import cn.iocoder.yudao.module.vx.convert.vxUser.VxUserConvert;
import cn.iocoder.yudao.module.vx.service.vxUser.VxUserService;

@Api(tags = "管理后台 - 微信用户信息")
@RestController
@RequestMapping("/vx/user")
@Validated
public class VxUserController {

    @Resource
    private VxUserService userService;
    @Resource
    private VxUserProjectService vxUserProjectService;

    @PostMapping("/create")
    @ApiOperation("创建微信用户信息")
    @PreAuthorize("@ss.hasPermission('vx:user:create')")
    public CommonResult<Integer> createUser(@Valid @RequestBody VxUserCreateReqVO createReqVO) {
        return success(userService.createUser(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新微信用户信息")
    @PreAuthorize("@ss.hasPermission('vx:user:update')")
    public CommonResult<Boolean> updateUser(@Valid @RequestBody VxUserUpdateReqVO updateReqVO) {
        userService.updateUser(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除微信用户信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('vx:user:delete')")
    public CommonResult<Boolean> deleteUser(@RequestParam("id") Integer id) {
        userService.deleteUser(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得微信用户信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('vx:user:query')")
    public CommonResult<VxUserRespVO> getUser(@RequestParam("id") Integer id) {
        VxUserDO user = userService.getUser(id);
        return success(VxUserConvert.INSTANCE.convert(user));
    }

    @GetMapping("/list")
    @ApiOperation("获得微信用户信息列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('vx:user:query')")
    public CommonResult<List<VxUserRespVO>> getUserList(@RequestParam("ids") Collection<Integer> ids) {
        List<VxUserDO> list = userService.getUserList(ids);
        return success(VxUserConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得微信用户信息分页")
    @PreAuthorize("@ss.hasPermission('vx:user:query')")
    public CommonResult<PageResult<VxUserRespVO>> getUserPage(@Valid VxUserPageReqVO pageVO) {
        PageResult<VxUserDO> pageResult = userService.getUserPage(pageVO);
        return success(VxUserConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出微信用户信息 Excel")
    @PreAuthorize("@ss.hasPermission('vx:user:export')")
    @OperateLog(type = EXPORT)
    public void exportUserExcel(@Valid VxUserExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<VxUserDO> list = userService.getUserList(exportReqVO);
        // 导出 Excel
        List<VxUserExcelVO> datas = VxUserConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "微信用户信息.xls", "数据", VxUserExcelVO.class, datas);
    }

    @PostMapping("/signUp")
    @ApiOperation("报名")
    public CommonResult<Boolean> signUp(@Valid @RequestBody VxUserSingUpVO pageVO) {
        vxUserProjectService.signUp(pageVO);
        return success(true);
    }

}
