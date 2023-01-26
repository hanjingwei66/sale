package cn.iocoder.yudao.module.system.controller.admin.quota.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 指标 Excel VO
 *
 * @author hjw
 */
@Data
public class QuotaExcelVO {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("指标定额")
    private Double quota;

    @ExcelProperty("周期 日 周 月 季度 年")
    private String stage;

    @ExcelProperty("创建时间")
    private Date createTime;

    @ExcelProperty("指标定额单位")
    private String unit;

}
