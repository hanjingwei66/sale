package cn.iocoder.yudao.module.system.dal.dataobject.quota;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 指标 DO
 *
 * @author hjw
 */
@TableName("hq_quota")
@KeySequence("hq_quota_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuotaDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 销售id
     */
    private Long saleId;
    /**
     * 指标定额
     */
    private Double quota;
    /**
     * 周期 日 周 月 季度 年
     */
    private String stage;
    /**
     * 指标定额单位
     */
    private String unit;

}
