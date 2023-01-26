package cn.iocoder.yudao.module.system.controller.admin.quota.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* 指标 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class QuotaBaseVO {

    @ApiModelProperty(value = "指标定额")
    private Double quota;

    @ApiModelProperty(value = "周期 日 周 月 季度 年")
    private String stage;

    @ApiModelProperty(value = "指标定额单位")
    private String unit;

}
