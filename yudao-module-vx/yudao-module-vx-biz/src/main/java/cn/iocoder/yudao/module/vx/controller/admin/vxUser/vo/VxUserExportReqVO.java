package cn.iocoder.yudao.module.vx.controller.admin.vxUser.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel(value = "管理后台 - 微信用户信息 Excel 导出 Request VO", description = "参数和 VxUserPageReqVO 是一致的")
@Data
public class VxUserExportReqVO {

    @ApiModelProperty(value = "微信id")
    private Integer vxId;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "证件号")
    private Integer userCardId;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始创建时间")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束创建时间")
    private Date endCreateTime;

}
