package cn.iocoder.yudao.module.vx.controller.app.dict;

import cn.hutool.core.collection.CollectionUtil;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.controller.admin.dict.vo.area.DictAreaVO;
import cn.iocoder.yudao.module.system.controller.admin.dict.vo.data.DictDataExportReqVO;
import cn.iocoder.yudao.module.system.convert.area.AreaConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.area.AreaDO;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictDataDO;
import cn.iocoder.yudao.module.system.dal.redis.common.AreaRedisDAO;
import cn.iocoder.yudao.module.system.service.area.SystemAreaService;
import cn.iocoder.yudao.module.system.service.dict.DictDataService;
import cn.iocoder.yudao.module.vx.controller.app.dict.vo.DictDataVO;
import cn.iocoder.yudao.module.vx.convert.shiyaoProject.DictDataConvert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static java.time.Duration.ZERO;

@Api(tags = "管理后台 - 地区管理")
@RestController
@RequestMapping("/system")
@Validated
@Slf4j
public class DictAreaSysController {

    @Resource
    private AreaRedisDAO areaRedisDAO;
    @Autowired
    private SystemAreaService systemAreaService;
    @Autowired
    private DictDataService dictDataService;

    @GetMapping("/getDict")
    public CommonResult<List<DictDataVO>> publicgetProjectDict(DictDataExportReqVO reqVO){
        List<DictDataDO> dictDatas = dictDataService.getDictDatas(reqVO);
        return success(DictDataConvert.INSTANCE.convert(dictDatas));
    }
    @GetMapping("/list-area")
    @ApiOperation("地区树形字典")
    public CommonResult<List<DictAreaVO>> getArea() {
//        List<AreaDO> areaList = systemAreaService.getAreaList();
        List<AreaDO> areaList=null;
        areaList = areaRedisDAO.getList("areaList");
        if(CollectionUtil.isEmpty(areaList)) {
            areaList = systemAreaService.getAreaList();
            areaRedisDAO.setList("areaList",areaList,ZERO);
        }
        // 转换成 Tree 结构返回
        return success(AreaConvert.INSTANCE.buildMenuTree(areaList));
    }

}
