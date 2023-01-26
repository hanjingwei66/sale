package cn.iocoder.yudao.module.hq.controller.admin.customer.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import com.alibaba.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;


/**
 * hq用户 Excel VO
 *
 * @author hanjingwei
 */
@Data
public class CustomerExcelVO {

    @ExcelProperty("客户id")
    private Long id;

    @ExcelProperty("客户名称")
    private String customerName;

    @ExcelProperty("客户电话")
    private String telphone;

    @ExcelProperty(value = "1男2女", converter = DictConvert.class)
    @DictFormat("system_user_sex") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private String sex;

    @ExcelProperty("订单金额")
    private Integer orderMoney;

    @ExcelProperty("单位")
    private String unit;

    @ExcelProperty("最后登录IP")
    private String loginIp;

    @ExcelProperty("最后登录时间")
    private Date loginDate;

    @ExcelProperty("创建时间")
    private Date createTime;

    @ExcelProperty("帐号状态（0正常 1停用）")
    private Integer status;

    @ExcelProperty("用户邮箱")
    private String email;

    @ExcelProperty("分配状态")
    private String allocationStatus;

}
