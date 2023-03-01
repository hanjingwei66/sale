package cn.iocoder.yudao.module.vx.dal.dataobject.vxUserProject;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 微信用户信息 DO
 *
 * @author hjw
 */
@TableName("vx_user_project")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VxUserProjectDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 项目id
     */
    private Integer projectId;
    /**
     * 报名时间
     */
    private Date reportTime;
    /**
     * 是否查看0未查看 1 查看
     */
    private Integer isLook;


}
