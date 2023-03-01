package cn.iocoder.yudao.module.vx.controller.admin.notice;

import cn.iocoder.yudao.module.vx.dal.dataobject.notice.VxNoticeDO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.*;

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

import cn.iocoder.yudao.module.vx.controller.admin.notice.vo.*;
import cn.iocoder.yudao.module.vx.convert.notice.NoticeConvert;
import cn.iocoder.yudao.module.vx.service.notice.VxNoticeService;

@Api(tags = "管理后台 - 公告栏")
@RestController("VxNoticeController")
@RequestMapping("/vx/notice")
@Validated
public class NoticeController {

    @Resource
    private VxNoticeService vxNoticeService;

    @PostMapping("/create")
    @ApiOperation("创建公告栏")
    @PreAuthorize("@ss.hasPermission('vx:notice:create')")
    public CommonResult<Integer> createNotice(@Valid @RequestBody NoticeCreateReqVO createReqVO) {
        return success(vxNoticeService.createNotice(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新公告栏")
    @PreAuthorize("@ss.hasPermission('vx:notice:update')")
    public CommonResult<Boolean> updateNotice(@Valid @RequestBody NoticeUpdateReqVO updateReqVO) {
        vxNoticeService.updateNotice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除公告栏")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('vx:notice:delete')")
    public CommonResult<Boolean> deleteNotice(@RequestParam("id") Integer id) {
        vxNoticeService.deleteNotice(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得公告栏")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('vx:notice:query')")
    public CommonResult<NoticeRespVO> getNotice(@RequestParam("id") Integer id) {
        VxNoticeDO notice = vxNoticeService.getNotice(id);
        return success(NoticeConvert.INSTANCE.convert(notice));
    }

    @GetMapping("/list")
    @ApiOperation("获得公告栏列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('vx:notice:query')")
    public CommonResult<List<NoticeRespVO>> getNoticeList(@RequestParam("ids") Collection<Integer> ids) {
        List<VxNoticeDO> list = vxNoticeService.getNoticeList(ids);
        return success(NoticeConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得公告栏分页")
    @PreAuthorize("@ss.hasPermission('vx:notice:query')")
    public CommonResult<PageResult<NoticeRespVO>> getNoticePage(@Valid NoticePageReqVO pageVO) {
        PageResult<VxNoticeDO> pageResult = vxNoticeService.getNoticePage(pageVO);
        return success(NoticeConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出公告栏 Excel")
    @PreAuthorize("@ss.hasPermission('vx:notice:export')")
    @OperateLog(type = EXPORT)
    public void exportNoticeExcel(@Valid NoticeExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<VxNoticeDO> list = vxNoticeService.getNoticeList(exportReqVO);
        // 导出 Excel
        List<NoticeExcelVO> datas = NoticeConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "公告栏.xls", "数据", NoticeExcelVO.class, datas);
    }

}
