package cn.iocoder.yudao.module.system.controller.admin.group;

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

import cn.iocoder.yudao.module.system.controller.admin.group.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.group.GroupDO;
import cn.iocoder.yudao.module.system.convert.group.GroupConvert;
import cn.iocoder.yudao.module.system.service.group.GroupService;

@Api(tags = "管理后台 - 用户组")
@RestController
@RequestMapping("/system/group")
@Validated
public class GroupController {

    @Resource
    private GroupService groupService;

    @PostMapping("/create")
    @ApiOperation("创建用户组")
    @PreAuthorize("@ss.hasPermission('system:group:create')")
    public CommonResult<Long> createGroup(@Valid @RequestBody GroupCreateReqVO createReqVO) {
        return success(groupService.createGroup(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新用户组")
    @PreAuthorize("@ss.hasPermission('system:group:update')")
    public CommonResult<Boolean> updateGroup(@Valid @RequestBody GroupUpdateReqVO updateReqVO) {
        groupService.updateGroup(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除用户组")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:group:delete')")
    public CommonResult<Boolean> deleteGroup(@RequestParam("id") Long id) {
        groupService.deleteGroup(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得用户组")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:group:query')")
    public CommonResult<GroupRespVO> getGroup(@RequestParam("id") Long id) {
        GroupDO group = groupService.getGroup(id);
        return success(GroupConvert.INSTANCE.convert(group));
    }

    @GetMapping("/list")
    @ApiOperation("获得用户组列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('system:group:query')")
    public CommonResult<List<GroupRespVO>> getGroupList(@RequestParam("ids") Collection<Long> ids) {
        List<GroupDO> list = groupService.getGroupList(ids);
        return success(GroupConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得用户组分页")
    @PreAuthorize("@ss.hasPermission('system:group:query')")
    public CommonResult<PageResult<GroupRespVO>> getGroupPage(@Valid GroupPageReqVO pageVO) {
        PageResult<GroupDO> pageResult = groupService.getGroupPage(pageVO);
        return success(GroupConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出用户组 Excel")
    @PreAuthorize("@ss.hasPermission('system:group:export')")
    @OperateLog(type = EXPORT)
    public void exportGroupExcel(@Valid GroupExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<GroupDO> list = groupService.getGroupList(exportReqVO);
        // 导出 Excel
        List<GroupExcelVO> datas = GroupConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "用户组.xls", "数据", GroupExcelVO.class, datas);
    }

}
