package cn.iocoder.yudao.module.hq.controller.admin.quota.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("管理后台 - 指标创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QuotaCreateReqVO extends QuotaBaseVO {

    @ApiModelProperty(value = "销售id")
    private Long saleId;

}
