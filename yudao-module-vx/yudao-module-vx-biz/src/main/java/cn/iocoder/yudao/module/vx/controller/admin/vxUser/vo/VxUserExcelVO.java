package cn.iocoder.yudao.module.vx.controller.admin.vxUser.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import com.alibaba.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;


/**
 * 微信用户信息 Excel VO
 *
 * @author hjw
 */
@Data
public class VxUserExcelVO {

    @ExcelProperty("id")
    private Integer id;

    @ExcelProperty("微信id")
    private Integer vxId;

    @ExcelProperty("姓名")
    private String userName;

    @ExcelProperty("证件号")
    private Integer userCardId;

    @ExcelProperty("电话")
    private Integer userPhone;

    @ExcelProperty(value = "性别", converter = DictConvert.class)
    @DictFormat("system_user_sex") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Integer sex;

    @ExcelProperty("身高")
    private Double height;

    @ExcelProperty("体重")
    private Double weight;

    @ExcelProperty(value = "是否抽烟1抽0不抽", converter = DictConvert.class)
    @DictFormat("is_smoke") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Integer isSmoke;

    @ExcelProperty("常驻地址")
    private String address;

    @ExcelProperty("详细地址")
    private String addressDetail;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty(value = "是否提供身份证", converter = DictConvert.class)
    @DictFormat("infra_boolean_string") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Integer isCard;

    @ExcelProperty("创建时间")
    private Date createTime;

}
