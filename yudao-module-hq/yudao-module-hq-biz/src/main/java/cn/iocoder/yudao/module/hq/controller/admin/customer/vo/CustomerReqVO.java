package cn.iocoder.yudao.module.hq.controller.admin.customer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>标题: </p>
 * <p>描述: </p>
 * <p>版权: Copyright (c) 2022</p>
 * <p>公司: --软件股份有限公司</p>
 *
 * @version: 1.0
 * @author: HanJingwei
 * @date 2022-12-25
 */
@ApiModel("管理后台 - hq用户 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerReqVO extends CustomerBaseVO{
    @ApiModelProperty(value = "客户id", required = true)
    private Long id;
}
