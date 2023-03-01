package cn.iocoder.yudao.module.vx.controller.admin.helpinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 帮助信息 Excel VO
 *
 * @author hjw
 */
@Data
public class HelpInfoExcelVO {

    @ExcelProperty("id")
    private Integer id;

    @ExcelProperty("帮助标题")
    private String helpName;

    @ExcelProperty("帮助详情")
    private String helpDetail;

    @ExcelProperty("创建时间")
    private Date createTime;

}
