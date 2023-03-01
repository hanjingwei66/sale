package cn.iocoder.yudao.module.vx.controller.app.shiyaoProject;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;

import cn.iocoder.yudao.module.system.controller.admin.dict.vo.data.DictDataExportReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictDataDO;
import cn.iocoder.yudao.module.system.service.dict.DictDataService;
import cn.iocoder.yudao.module.vx.controller.admin.shiyaoProject.vo.ShiyaoProjectExcelVO;
import cn.iocoder.yudao.module.vx.controller.admin.shiyaoProject.vo.ShiyaoProjectExportReqVO;
import cn.iocoder.yudao.module.vx.controller.admin.shiyaoProject.vo.ShiyaoProjectPageReqVO;
import cn.iocoder.yudao.module.vx.controller.admin.shiyaoProject.vo.ShiyaoProjectRespVO;
import cn.iocoder.yudao.module.vx.convert.shiyaoProject.ShiyaoProjectConvert;
import cn.iocoder.yudao.module.vx.dal.dataobject.shiyaoProject.ShiyaoProjectDO;
import cn.iocoder.yudao.module.vx.service.shiyaoProject.ShiyaoProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Api(tags = "管理前台 - 试药项目")
@RestController
@RequestMapping("/vx/shiyao-project")
@Validated
public class ShiyaoProjectAppController {

    @Resource
    private ShiyaoProjectService shiyaoProjectService;


    @GetMapping("/get")
    @ApiOperation("获得试药项目")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
//    @PreAuthorize("@ss.hasPermission('vx:shiyao-project:query')")
    public CommonResult<ShiyaoProjectRespVO> getShiyaoProject(@RequestParam("id") Integer id) {
        ShiyaoProjectDO shiyaoProject = shiyaoProjectService.getShiyaoProject(id);
        return success(ShiyaoProjectConvert.INSTANCE.convert(shiyaoProject));
    }

    @GetMapping("/list")
    @ApiOperation("获得试药项目列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
//    @PreAuthorize("@ss.hasPermission('vx:shiyao-project:query')")
    public CommonResult<List<ShiyaoProjectRespVO>> getShiyaoProjectList(@RequestParam("ids") Collection<Integer> ids) {
        List<ShiyaoProjectDO> list = shiyaoProjectService.getShiyaoProjectList(ids);
        return success(ShiyaoProjectConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得试药项目分页")
//    @PreAuthorize("@ss.hasPermission('vx:shiyao-project:query')")
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
