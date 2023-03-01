package cn.iocoder.yudao.module.vx.dal.dataobject.helpinfo;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 帮助信息 DO
 *
 * @author hjw
 */
@TableName("vx_help_info")
@KeySequence("vx_help_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpInfoDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Integer id;
    /**
     * 帮助标题
     */
    private String helpName;
    /**
     * 帮助详情
     */
    private String helpDetail;

}
