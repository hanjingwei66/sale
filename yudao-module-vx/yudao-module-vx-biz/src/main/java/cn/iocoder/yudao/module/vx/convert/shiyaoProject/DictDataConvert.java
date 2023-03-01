package cn.iocoder.yudao.module.vx.convert.shiyaoProject;

import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictDataDO;
import cn.iocoder.yudao.module.vx.controller.app.dict.vo.DictDataVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;
@Mapper
public interface DictDataConvert {
    DictDataConvert INSTANCE = Mappers.getMapper(DictDataConvert.class);
    List<DictDataVO> convert(List<DictDataDO> dictDataDO);
}
