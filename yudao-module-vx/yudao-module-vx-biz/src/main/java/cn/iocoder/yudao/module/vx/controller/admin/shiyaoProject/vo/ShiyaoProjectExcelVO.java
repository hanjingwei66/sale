package cn.iocoder.yudao.module.vx.controller.admin.shiyaoProject.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import com.alibaba.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;


/**
 * 试药项目 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class ShiyaoProjectExcelVO {

    @ExcelProperty("id")
    private Integer id;

    @ExcelProperty("项目名称")
    private String projectName;

    @ExcelProperty("物品名称")
    private String goodsName;

    @ExcelProperty("地址")
    private String address;

    @ExcelProperty("地址详情")
    private String addressDetails;

    @ExcelProperty(value = "0女1男2可男可女", converter = DictConvert.class)
    @DictFormat("system_user_sex") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Integer sex;

    @ExcelProperty("入院天数")
    private String inpDays;

    @ExcelProperty("男性价格")
    private Long priceMan;

    @ExcelProperty("女性价格")
    private String priceWoman;

    @ExcelProperty("最小年龄")
    private Integer minAge;

    @ExcelProperty("最大年龄")
    private Integer maxAge;

    @ExcelProperty("最小BMI值")
    private String minBmi;

    @ExcelProperty("最大BMI值")
    private String maxBmi;

    @ExcelProperty(value = "0招募中1停止招募", converter = DictConvert.class)
    @DictFormat("recruit_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private String status;

    @ExcelProperty(value = "0连住项目1不联网项目2项目患者3健康项目", converter = DictConvert.class)
    @DictFormat("project_category") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private String category;

    @ExcelProperty(value = "0 无烟检 1 烟检", converter = DictConvert.class)
    @DictFormat("is_smoke") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Integer isSmoke;

    @ExcelProperty("排序")
    private Integer sort;

    @ExcelProperty("到场时间")
    private Date startTime;

    @ExcelProperty("截至时间")
    private Date stopTime;

    @ExcelProperty("详情")
    private String context;

    @ExcelProperty("创建时间")
    private Date createTime;

}
