package cn.iocoder.yudao.module.hq.dal.dataobject.customer;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * hq用户 DO
 *
 * @author hanjingwei
 */
@TableName("hq_customer")
@KeySequence("hq_customer_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDO extends BaseDO {

    /**
     * 客户id
     */
    @TableId
    private Long id;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 客户电话
     */
    private String telphone;
    /**
     * 销售id
     */
    private Long saleId;
    /**
     * 1男2女
     *
     * 枚举 {@link TODO system_user_sex 对应的类}
     */
    private String sex;
    /**
     * 订单金额
     */
    private Integer orderMoney;
    /**
     * 单位
     */
    private String unit;
    /**
     * 最后登录IP
     */
    private String loginIp;
    /**
     * 最后登录时间
     */
    private Date loginDate;
    /**
     * 帐号状态（0正常 1停用）
     */
    private Integer status;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 分配状态
     */
    private String allocationStatus;
    /**
     * 老板id
     */
    private Long bossId;
    /**
     * 订单id
     */
    private Long orderId;

}
