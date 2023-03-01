package cn.iocoder.yudao.module.vx.controller.app.notice.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import com.alibaba.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;


/**
 * 公告栏 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class AppNoticeExcelVO {

    @ExcelProperty("id")
    private Integer id;

    @ExcelProperty("公告")
    private String notice;

    @ExcelProperty(value = "通知状态", converter = DictConvert.class)
    @DictFormat("infra_boolean_string") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Integer status;

    @ExcelProperty("创建时间")
    private Date createTime;

}
