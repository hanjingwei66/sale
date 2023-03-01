package cn.iocoder.yudao.module.vx.controller.app.notice.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

@ApiModel("用户 APP - 公告栏 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppNoticeRespVO extends AppNoticeBaseVO {

    @ApiModelProperty(value = "id", required = true)
    private Integer id;

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

}
