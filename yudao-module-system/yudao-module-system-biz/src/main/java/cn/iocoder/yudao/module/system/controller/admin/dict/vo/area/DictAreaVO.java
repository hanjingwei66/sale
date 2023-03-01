package cn.iocoder.yudao.module.system.controller.admin.dict.vo.area;

import cn.iocoder.yudao.module.system.controller.admin.auth.vo.AuthMenuRespVO;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

@Data
public class DictAreaVO {
    @TableId
    private String id;
    private String areaCode;
    private String name;
    private String parentId;
    private String shortName;
    private String cityLv;
    private List<DictAreaVO> children;
}
