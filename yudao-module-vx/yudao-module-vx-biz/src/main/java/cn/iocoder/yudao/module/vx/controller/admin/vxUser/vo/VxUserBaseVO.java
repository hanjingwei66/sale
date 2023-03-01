package cn.iocoder.yudao.module.vx.controller.admin.vxUser.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

/**
* 微信用户信息 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class VxUserBaseVO {

    @ApiModelProperty(value = "微信id", required = true)
    @NotNull(message = "微信id不能为空")
    private Integer vxId;

    @ApiModelProperty(value = "姓名", required = true)
    @NotNull(message = "姓名不能为空")
    private String userName;

    @ApiModelProperty(value = "证件号", required = true)
    @NotNull(message = "证件号不能为空")
    private String userCardId;

    @ApiModelProperty(value = "电话")
    private String userPhone;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "身高")
    private Double height;

    @ApiModelProperty(value = "体重")
    private Double weight;

    @ApiModelProperty(value = "是否抽烟1抽0不抽")
    private Integer isSmoke;

    @ApiModelProperty(value = "常驻地址", required = true)
    @NotNull(message = "常驻地址不能为空")
    private String address;

    @ApiModelProperty(value = "详细地址")
    private String addressDetail;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否提供身份证")
    private Integer isCard;

}
