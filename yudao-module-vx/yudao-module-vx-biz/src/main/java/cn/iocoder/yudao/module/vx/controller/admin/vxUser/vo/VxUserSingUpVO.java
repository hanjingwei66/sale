package cn.iocoder.yudao.module.vx.controller.admin.vxUser.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VxUserSingUpVO  extends VxUserBaseVO{

    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @NotNull(message = "项目id不能为空")
    @ApiModelProperty(value = "项目id")
    private Integer projectId;
}
