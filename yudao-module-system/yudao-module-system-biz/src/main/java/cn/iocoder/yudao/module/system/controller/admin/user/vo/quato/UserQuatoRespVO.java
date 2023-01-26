package cn.iocoder.yudao.module.system.controller.admin.user.vo.quato;

import cn.iocoder.yudao.module.system.controller.admin.user.vo.user.UserPageItemRespVO;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.user.UserRespVO;
import cn.iocoder.yudao.module.system.dal.dataobject.quota.QuotaDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

/**
 * <p>标题: </p>
 * <p>描述: </p>
 * <p>版权: Copyright (c) 2022</p>
 * <p>公司: --软件股份有限公司</p>
 *
 * @version: 1.0
 * @author: HanJingwei
 * @date 2022-12-20
 */
@Data
public class UserQuatoRespVO extends UserPageItemRespVO {
    List<QuotaDO> quotas;
}
