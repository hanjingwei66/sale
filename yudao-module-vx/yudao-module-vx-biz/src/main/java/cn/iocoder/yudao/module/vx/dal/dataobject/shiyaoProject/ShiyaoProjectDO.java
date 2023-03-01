package cn.iocoder.yudao.module.vx.dal.dataobject.shiyaoProject;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 试药项目 DO
 *
 * @author 芋道源码
 */
@TableName("vx_project")
@KeySequence("vx_project_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShiyaoProjectDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Integer id;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 物品名称
     */
    private String goodsName;
    /**
     * 地址
     */
    private String address;
    /**
     * 地址详情
     */
    private String addressDetails;
    /**
     * 0女1男2可男可女
     *
     * 枚举 {@link TODO system_user_sex 对应的类}
     */
    private Integer sex;
    /**
     * 入院天数
     */
    private String inpDays;
    /**
     * 男性价格
     */
    private Long priceMan;
    /**
     * 女性价格
     */
    private String priceWoman;
    /**
     * 最小年龄
     */
    private Integer minAge;
    /**
     * 最大年龄
     */
    private Integer maxAge;
    /**
     * 最小BMI值
     */
    private String minBmi;
    /**
     * 最大BMI值
     */
    private String maxBmi;
    /**
     * 0招募中1停止招募
     *
     * 枚举 {@link TODO recruit_status 对应的类}
     */
    private String status;
    /**
     * 0连住项目1不联网项目2项目患者3健康项目
     *
     * 枚举 {@link TODO project_category 对应的类}
     */
    private String category;
    /**
     * 0 无烟检 1 烟检
     *
     * 枚举 {@link TODO is_smoke 对应的类}
     */
    private Integer isSmoke;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 到场时间
     */
    private Date startTime;
    /**
     * 截至时间
     */
    private Date stopTime;
    /**
     * 详情
     */
    private String context;

}
