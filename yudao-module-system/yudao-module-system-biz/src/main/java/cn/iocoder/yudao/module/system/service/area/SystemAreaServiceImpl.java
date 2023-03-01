package cn.iocoder.yudao.module.system.service.area;

import cn.iocoder.yudao.module.system.dal.dataobject.area.AreaDO;
import cn.iocoder.yudao.module.system.dal.mysql.area.AreaMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@Slf4j
public class SystemAreaServiceImpl  implements  SystemAreaService{
    @Autowired
    private AreaMapper areaMapper;
    @Override
    public List<AreaDO> getAreaList() {
        List<AreaDO> areaDOS = areaMapper.selectList();
        return areaDOS;
    }
}
