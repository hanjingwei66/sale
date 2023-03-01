package cn.iocoder.yudao.module.vx.controller.app.notice.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

/**
* 公告栏 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class AppNoticeBaseVO {

    @ApiModelProperty(value = "公告")
    private String notice;

    @ApiModelProperty(value = "通知状态", required = true)
    @NotNull(message = "通知状态不能为空")
    private Integer status;

}
