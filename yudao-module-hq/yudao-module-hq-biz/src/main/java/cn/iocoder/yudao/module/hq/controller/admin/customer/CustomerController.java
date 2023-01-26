package cn.iocoder.yudao.module.hq.controller.admin.customer;

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

import cn.iocoder.yudao.module.hq.controller.admin.customer.vo.*;
import cn.iocoder.yudao.module.hq.dal.dataobject.customer.CustomerDO;
import cn.iocoder.yudao.module.hq.convert.customer.CustomerConvert;
import cn.iocoder.yudao.module.hq.service.customer.CustomerService;

@Api(tags = "管理后台 - hq用户")
@RestController
@RequestMapping("/hq/customer")
@Validated
public class CustomerController {

    @Resource
    private CustomerService customerService;

    @PostMapping("/create")
    @ApiOperation("创建hq用户")
    @PreAuthorize("@ss.hasPermission('hq:customer:create')")
    public CommonResult<Long> createCustomer(@Valid @RequestBody CustomerCreateReqVO createReqVO) {
        return success(customerService.createCustomer(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新hq用户")
    @PreAuthorize("@ss.hasPermission('hq:customer:update')")
    public CommonResult<Boolean> updateCustomer(@Valid @RequestBody CustomerUpdateReqVO updateReqVO) {
        customerService.updateCustomer(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除hq用户")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('hq:customer:delete')")
    public CommonResult<Boolean> deleteCustomer(@RequestParam("id") Long id) {
        customerService.deleteCustomer(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得hq用户")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('hq:customer:query')")
    public CommonResult<CustomerRespVO> getCustomer(@RequestParam("id") Long id) {
        CustomerDO customer = customerService.getCustomer(id);
        return success(CustomerConvert.INSTANCE.convert(customer));
    }

    @GetMapping("/list")
    @ApiOperation("获得hq用户列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('hq:customer:query')")
    public CommonResult<List<CustomerRespVO>> getCustomerList(@RequestParam("ids") Collection<Long> ids) {
        List<CustomerDO> list = customerService.getCustomerList(ids);
        return success(CustomerConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得hq用户分页")
    @PreAuthorize("@ss.hasPermission('hq:customer:query')")
    public CommonResult<PageResult<CustomerRespVO>> getCustomerPage(@Valid CustomerPageReqVO pageVO) {
        PageResult<CustomerDO> pageResult = customerService.getCustomerPage(pageVO);
        return success(CustomerConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出hq用户 Excel")
    @PreAuthorize("@ss.hasPermission('hq:customer:export')")
    @OperateLog(type = EXPORT)
    public void exportCustomerExcel(@Valid CustomerExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<CustomerDO> list = customerService.getCustomerList(exportReqVO);
        // 导出 Excel
        List<CustomerExcelVO> datas = CustomerConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "hq用户.xls", "数据", CustomerExcelVO.class, datas);
    }
    @GetMapping("/statistics")
    @ApiOperation("获得hq用户")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('hq:customer:query')")
    public CommonResult<Integer> getCustomerStatistics(@Valid CustomerReqVO query) {
        Integer orderSum = customerService.getOrderSum(query);
        return success(orderSum);
    }
    @GetMapping("/allStatistics")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('hq:customer:query')")
    public CommonResult<Map<Long, Integer>  > getAllCustomerStatistics(@Valid CustomerReqVO query) {
        Map<Long, Integer> allOrderSum = customerService.getAllOrderSum(query);
        return success(allOrderSum);
    }
}
