package cn.iocoder.yudao.module.hq.controller.admin.customer.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
* hq用户 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class CustomerBaseVO {

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "客户电话")
    private String telphone;

    @ApiModelProperty(value = "1男2女")
    private String sex;

    @ApiModelProperty(value = "订单金额")
    private Integer orderMoney;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "最后登录IP")
    private String loginIp;

    @ApiModelProperty(value = "最后登录时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date loginDate;

    @ApiModelProperty(value = "帐号状态（0正常 1停用）", required = true)
    @NotNull(message = "帐号状态（0正常 1停用）不能为空")
    private Integer status;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "分配状态")
    private String allocationStatus;

    @ApiModelProperty(value = "销售id")
    private Long saleId;

}
