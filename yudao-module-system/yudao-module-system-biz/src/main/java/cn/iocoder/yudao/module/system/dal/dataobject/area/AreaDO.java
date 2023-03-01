package cn.iocoder.yudao.module.system.dal.dataobject.area;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@TableName(value = "sys_area", autoResultMap = true) // 由于 SQL Server 的 system_user 是关键字，所以使用 system_users
//@KeySequence("system_user_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
//@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AreaDO {
    @TableId
    private String id;
    private String areaCode;
    private String name;
    private String parentId;
    private String shortName;
    private String cityLv;
    private String telCode;
    private String postCode;
    private String fullName;
    private String lng;
    private String lat;
    private String pinYin;
    private String weatherCode;

}
