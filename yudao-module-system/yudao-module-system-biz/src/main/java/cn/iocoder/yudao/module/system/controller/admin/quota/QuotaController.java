package cn.iocoder.yudao.module.system.controller.admin.quota;

import cn.hutool.core.collection.CollectionUtil;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.system.controller.admin.quota.vo.QuotaCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.quota.vo.QuotaExcelVO;
import cn.iocoder.yudao.module.system.controller.admin.quota.vo.QuotaExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.quota.vo.QuotaPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.quota.vo.QuotaRespVO;
import cn.iocoder.yudao.module.system.controller.admin.quota.vo.QuotaUpdateReqVO;
import cn.iocoder.yudao.module.system.convert.quota.QuotaConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.quota.QuotaDO;
import cn.iocoder.yudao.module.system.service.quota.QuotaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

@Api(tags = "???????????? - ??????")
@RestController
@RequestMapping("/hq/quota")
@Validated
public class QuotaController {

    @Resource
    private QuotaService quotaService;

    @PostMapping("/create")
    @ApiOperation("????????????")
    @PreAuthorize("@ss.hasPermission('hq:quota:create')")
    public CommonResult<Long> createQuota(@Valid @RequestBody QuotaCreateReqVO createReqVO) {
        return success(quotaService.createQuota(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("????????????")
    @PreAuthorize("@ss.hasPermission('hq:quota:update')")
    public CommonResult<Boolean> updateQuota(@Valid @RequestBody QuotaUpdateReqVO updateReqVO) {
        quotaService.updateQuota(updateReqVO);
        return success(true);
    }
    @PostMapping("/createAndUpdate")
    @ApiOperation("????????????")
    @PreAuthorize("@ss.hasPermission('hq:quota:create')")
    public CommonResult<Long> createAndUpdateQuota(@Valid @RequestBody List<QuotaCreateReqVO> quotaReqVO) {
        return success(quotaService.createAndUpdateQuota(quotaReqVO));
    }
    @DeleteMapping("/delete")
    @ApiOperation("????????????")
    @ApiImplicitParam(name = "id", value = "??????", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('hq:quota:delete')")
    public CommonResult<Boolean> deleteQuota(@RequestParam("id") Long id) {
        quotaService.deleteQuota(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("????????????")
    @ApiImplicitParam(name = "id", value = "??????", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('hq:quota:query')")
    public CommonResult<QuotaRespVO> getQuota(@RequestParam("id") Long id) {
        QuotaDO quota = quotaService.getQuota(id);
        return success(QuotaConvert.INSTANCE.convert(quota));
    }

    @GetMapping("/getBySaleId")
    @ApiOperation("????????????")
    @ApiImplicitParam(name = "id", value = "??????", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('hq:quota:query')")
    public CommonResult<List<QuotaRespVO>> getQuotaBySaleId(@RequestParam("id") Long id) {
        List<QuotaDO> list = quotaService.getQuotaBySaleId(id);
        if(CollectionUtil.isEmpty(list)){
            list.add(new QuotaDO());
        }
        return success(QuotaConvert.INSTANCE.convertList(list));
    }
    @GetMapping("/list")
    @ApiOperation("??????????????????")
    @ApiImplicitParam(name = "ids", value = "????????????", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('hq:quota:query')")
    public CommonResult<List<QuotaRespVO>> getQuotaList(@RequestParam("ids") Collection<Long> ids) {
        List<QuotaDO> list = quotaService.getQuotaList(ids);
        return success(QuotaConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("??????????????????")
    @PreAuthorize("@ss.hasPermission('hq:quota:query')")
    public CommonResult<PageResult<QuotaRespVO>> getQuotaPage(@Valid QuotaPageReqVO pageVO) {
        PageResult<QuotaDO> pageResult = quotaService.getQuotaPage(pageVO);
        return success(QuotaConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("???????????? Excel")
    @PreAuthorize("@ss.hasPermission('hq:quota:export')")
    @OperateLog(type = EXPORT)
    public void exportQuotaExcel(@Valid QuotaExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<QuotaDO> list = quotaService.getQuotaList(exportReqVO);
        // ?????? Excel
        List<QuotaExcelVO> datas = QuotaConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "??????.xls", "??????", QuotaExcelVO.class, datas);
    }

}
