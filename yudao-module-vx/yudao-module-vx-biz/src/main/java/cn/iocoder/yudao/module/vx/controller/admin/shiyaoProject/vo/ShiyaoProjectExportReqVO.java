package cn.iocoder.yudao.module.vx.controller.admin.shiyaoProject.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel(value = "管理后台 - 试药项目 Excel 导出 Request VO", description = "参数和 ShiyaoProjectPageReqVO 是一致的")
@Data
public class ShiyaoProjectExportReqVO {

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "0招募中1停止招募")
    private String status;

    @ApiModelProperty(value = "0连住项目1不联网项目2项目患者3健康项目")
    private String category;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始创建时间")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束创建时间")
    private Date endCreateTime;

}
