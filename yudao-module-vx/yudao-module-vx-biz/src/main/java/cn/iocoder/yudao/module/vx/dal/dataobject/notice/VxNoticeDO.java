package cn.iocoder.yudao.module.vx.dal.dataobject.notice;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 公告栏 DO
 *
 * @author 芋道源码
 */
@TableName("vx_notice")
@KeySequence("vx_notice_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VxNoticeDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Integer id;
    /**
     * 公告
     */
    private String notice;
    /**
     * 通知状态
     *
     * 枚举 {@link TODO infra_boolean_string 对应的类}
     */
    private Integer status;

}
