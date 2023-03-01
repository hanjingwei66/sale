package cn.iocoder.yudao.module.vx.controller.admin.shiyaoProject.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@ToString(callSuper = true)
public class ShiyaoProjectListVO {
    @ApiModelProperty(value = "项目名称")
    private String projectName;
    private String projectId;
}
