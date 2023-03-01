package cn.iocoder.yudao.module.vx.controller.admin.shiyaoProject;

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

import cn.iocoder.yudao.module.vx.controller.admin.shiyaoProject.vo.*;
import cn.iocoder.yudao.module.vx.dal.dataobject.shiyaoProject.ShiyaoProjectDO;
import cn.iocoder.yudao.module.vx.convert.shiyaoProject.ShiyaoProjectConvert;
import cn.iocoder.yudao.module.vx.service.shiyaoProject.ShiyaoProjectService;

@Api(tags = "管理后台 - 试药项目")
@RestController
@RequestMapping("/vx/shiyao-project")
@Validated
public class ShiyaoProjectController {

    @Resource
    private ShiyaoProjectService shiyaoProjectService;

    @PostMapping("/create")
    @ApiOperation("创建试药项目")
    @PreAuthorize("@ss.hasPermission('vx:shiyao-project:create')")
    public CommonResult<Integer> createShiyaoProject(@Valid @RequestBody ShiyaoProjectCreateReqVO createReqVO) {
        return success(shiyaoProjectService.createShiyaoProject(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新试药项目")
    @PreAuthorize("@ss.hasPermission('vx:shiyao-project:update')")
    public CommonResult<Boolean> updateShiyaoProject(@Valid @RequestBody ShiyaoProjectUpdateReqVO updateReqVO) {
        shiyaoProjectService.updateShiyaoProject(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除试药项目")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('vx:shiyao-project:delete')")
    public CommonResult<Boolean> deleteShiyaoProject(@RequestParam("id") Integer id) {
        shiyaoProjectService.deleteShiyaoProject(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得试药项目")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('vx:shiyao-project:query')")
    public CommonResult<ShiyaoProjectRespVO> getShiyaoProject(@RequestParam("id") Integer id) {
        ShiyaoProjectDO shiyaoProject = shiyaoProjectService.getShiyaoProject(id);
        return success(ShiyaoProjectConvert.INSTANCE.convert(shiyaoProject));
    }

    @GetMapping("/list")
    @ApiOperation("获得试药项目列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('vx:shiyao-project:query')")
    public CommonResult<List<ShiyaoProjectRespVO>> getShiyaoProjectList(@RequestParam("ids") Collection<Integer> ids) {
        List<ShiyaoProjectDO> list = shiyaoProjectService.getShiyaoProjectList(ids);
        return success(ShiyaoProjectConvert.INSTANCE.convertList(list));
    }
    @GetMapping("/getList")
    @ApiOperation("获得试药项目列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    public CommonResult<List<ShiyaoProjectListVO>> getList() {
        List<ShiyaoProjectDO> list = shiyaoProjectService.getShiyaoProjectList();
        return success(ShiyaoProjectConvert.INSTANCE.convertList03(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得试药项目分页")
    @PreAuthorize("@ss.hasPermission('vx:shiyao-project:query')")
    public CommonResult<PageResult<ShiyaoProjectRespVO>> getShiyaoProjectPage(@Valid ShiyaoProjectPageReqVO pageVO) {
        PageResult<ShiyaoProjectDO> pageResult = shiyaoProjectService.getShiyaoProjectPage(pageVO);
        return success(ShiyaoProjectConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出试药项目 Excel")
    @PreAuthorize("@ss.hasPermission('vx:shiyao-project:export')")
    @OperateLog(type = EXPORT)
    public void exportShiyaoProjectExcel(@Valid ShiyaoProjectExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<ShiyaoProjectDO> list = shiyaoProjectService.getShiyaoProjectList(exportReqVO);
        // 导出 Excel
        List<ShiyaoProjectExcelVO> datas = ShiyaoProjectConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "试药项目.xls", "数据", ShiyaoProjectExcelVO.class, datas);
    }

}
