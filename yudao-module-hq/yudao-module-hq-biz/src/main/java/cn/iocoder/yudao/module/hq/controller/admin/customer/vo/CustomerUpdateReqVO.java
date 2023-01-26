package cn.iocoder.yudao.module.hq.controller.admin.customer.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("管理后台 - hq用户更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerUpdateReqVO extends CustomerBaseVO {

    @ApiModelProperty(value = "客户id", required = true)
    @NotNull(message = "客户id不能为空")
    private Long id;

}
