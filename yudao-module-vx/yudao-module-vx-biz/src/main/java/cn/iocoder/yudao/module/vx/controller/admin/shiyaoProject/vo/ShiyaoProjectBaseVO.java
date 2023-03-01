package cn.iocoder.yudao.module.vx.controller.admin.shiyaoProject.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
* 试药项目 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class ShiyaoProjectBaseVO {

    @ApiModelProperty(value = "项目名称", required = true)
    @NotNull(message = "项目名称不能为空")
    private String projectName;

    @ApiModelProperty(value = "物品名称")
    private String goodsName;

    @ApiModelProperty(value = "地址", required = true)
    @NotNull(message = "地址不能为空")
    private String address;

    @ApiModelProperty(value = "地址详情")
    private String addressDetails;

    @ApiModelProperty(value = "0女1男2可男可女", required = true)
    @NotNull(message = "0女1男2可男可女不能为空")
    private Integer sex;

    @ApiModelProperty(value = "入院天数")
    private String inpDays;

    @ApiModelProperty(value = "男性价格")
    private Long priceMan;

    @ApiModelProperty(value = "女性价格")
    private String priceWoman;

    @ApiModelProperty(value = "最小年龄")
    private Integer minAge;

    @ApiModelProperty(value = "最大年龄")
    private Integer maxAge;

    @ApiModelProperty(value = "最小BMI值")
    private String minBmi;

    @ApiModelProperty(value = "最大BMI值")
    private String maxBmi;

    @ApiModelProperty(value = "0招募中1停止招募")
    private String status;

    @ApiModelProperty(value = "0连住项目1不联网项目2项目患者3健康项目")
    private String category;

    @ApiModelProperty(value = "0 无烟检 1 烟检")
    private Integer isSmoke;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "到场时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date startTime;

    @ApiModelProperty(value = "截至时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date stopTime;

    @ApiModelProperty(value = "详情")
    private String context;

}
