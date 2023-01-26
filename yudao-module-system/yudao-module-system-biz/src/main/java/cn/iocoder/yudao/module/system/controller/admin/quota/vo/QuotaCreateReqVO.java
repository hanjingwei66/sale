package cn.iocoder.yudao.module.system.controller.admin.quota.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 指标创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QuotaCreateReqVO extends QuotaBaseVO {

    @ApiModelProperty(value = "销售id")
    private Long saleId;

}
