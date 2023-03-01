package cn.iocoder.yudao.module.vx.controller.app.helpinfo;

import cn.iocoder.yudao.module.vx.controller.admin.helpinfo.vo.HelpInfoCreateReqVO;
import cn.iocoder.yudao.module.vx.controller.admin.helpinfo.vo.HelpInfoExcelVO;
import cn.iocoder.yudao.module.vx.controller.admin.helpinfo.vo.HelpInfoExportReqVO;
import cn.iocoder.yudao.module.vx.controller.admin.helpinfo.vo.HelpInfoPageReqVO;
import cn.iocoder.yudao.module.vx.controller.admin.helpinfo.vo.HelpInfoRespVO;
import cn.iocoder.yudao.module.vx.controller.admin.helpinfo.vo.HelpInfoUpdateReqVO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

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

import cn.iocoder.yudao.module.vx.controller.app.helpinfo.vo.*;
import cn.iocoder.yudao.module.vx.dal.dataobject.helpinfo.HelpInfoDO;
import cn.iocoder.yudao.module.vx.convert.helpinfo.HelpInfoConvert;
import cn.iocoder.yudao.module.vx.service.helpinfo.HelpInfoService;

@Api(tags = "用户 APP - 帮助信息")
@RestController
@RequestMapping("/vx/help-info")
@Validated
public class AppHelpInfoController {

    @Resource
    private HelpInfoService helpInfoService;

    @PostMapping("/create")
    @ApiOperation("创建帮助信息")

    public CommonResult<Integer> createHelpInfo(@Valid @RequestBody HelpInfoCreateReqVO createReqVO) {
        return success(helpInfoService.createHelpInfo(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新帮助信息")

    public CommonResult<Boolean> updateHelpInfo(@Valid @RequestBody HelpInfoUpdateReqVO updateReqVO) {
        helpInfoService.updateHelpInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除帮助信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Integer.class)

    public CommonResult<Boolean> deleteHelpInfo(@RequestParam("id") Integer id) {
        helpInfoService.deleteHelpInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得帮助信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)

    public CommonResult<HelpInfoRespVO> getHelpInfo(@RequestParam("id") Integer id) {
        HelpInfoDO helpInfo = helpInfoService.getHelpInfo(id);
        return success(HelpInfoConvert.INSTANCE.convert(helpInfo));
    }

    @GetMapping("/list")
    @ApiOperation("获得帮助信息列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)

    public CommonResult<List<HelpInfoRespVO>> getHelpInfoList(@RequestParam("ids") Collection<Integer> ids) {
        List<HelpInfoDO> list = helpInfoService.getHelpInfoList(ids);
        return success(HelpInfoConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得帮助信息分页")

    public CommonResult<PageResult<HelpInfoRespVO>> getHelpInfoPage(@Valid HelpInfoPageReqVO pageVO) {
        PageResult<HelpInfoDO> pageResult = helpInfoService.getHelpInfoPage(pageVO);
        return success(HelpInfoConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出帮助信息 Excel")

    @OperateLog(type = EXPORT)
    public void exportHelpInfoExcel(@Valid HelpInfoExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<HelpInfoDO> list = helpInfoService.getHelpInfoList(exportReqVO);
        // 导出 Excel
        List<HelpInfoExcelVO> datas = HelpInfoConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "帮助信息.xls", "数据", HelpInfoExcelVO.class, datas);
    }

}
