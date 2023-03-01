package cn.iocoder.yudao.module.vx.dal.dataobject.vxUser;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 微信用户信息 DO
 *
 * @author hjw
 */
@TableName("vx_user")
@KeySequence("vx_user_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VxUserDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Integer id;
    /**
     * 微信id
     */
    private Integer vxId;
    /**
     * 姓名
     */
    private String userName;
    /**
     * 证件号
     */
    private String userCardId;
    /**
     * 电话
     */
    private String userPhone;
    /**
     * 性别
     *
     * 枚举 {@link TODO system_user_sex 对应的类}
     */
    private Integer sex;
    /**
     * 身高
     */
    private Double height;
    /**
     * 体重
     */
    private Double weight;
    /**
     * 是否抽烟1抽0不抽
     *
     * 枚举 {@link TODO is_smoke 对应的类}
     */
    private Integer isSmoke;
    /**
     * 常驻地址
     */
    private String address;
    /**
     * 详细地址
     */
    private String addressDetail;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否提供身份证
     *
     * 枚举 {@link TODO infra_boolean_string 对应的类}
     */
    private Integer isCard;

}
