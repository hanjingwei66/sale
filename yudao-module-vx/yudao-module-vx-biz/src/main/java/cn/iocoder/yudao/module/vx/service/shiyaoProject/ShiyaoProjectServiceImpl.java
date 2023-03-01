package cn.iocoder.yudao.module.vx.service.shiyaoProject;

import cn.hutool.core.collection.CollectionUtil;
import cn.iocoder.yudao.module.system.dal.dataobject.area.AreaDO;
import cn.iocoder.yudao.module.system.dal.redis.common.AreaRedisDAO;
import cn.iocoder.yudao.module.system.service.area.SystemAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import cn.iocoder.yudao.module.vx.controller.admin.shiyaoProject.vo.*;
import cn.iocoder.yudao.module.vx.dal.dataobject.shiyaoProject.ShiyaoProjectDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.vx.convert.shiyaoProject.ShiyaoProjectConvert;
import cn.iocoder.yudao.module.vx.dal.mysql.shiyaoProject.ShiyaoProjectMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.vx.enums.ErrorCodeConstants.*;
import static java.time.Duration.ZERO;

/**
 * 试药项目 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ShiyaoProjectServiceImpl implements ShiyaoProjectService {

    @Resource
    private ShiyaoProjectMapper shiyaoProjectMapper;
    @Autowired
    private SystemAreaService systemAreaService;
    @Autowired
    private AreaRedisDAO<AreaDO> areaRedisDAO;

    @Override
    public Integer createShiyaoProject(ShiyaoProjectCreateReqVO createReqVO) {
        // 插入
        ShiyaoProjectDO shiyaoProject = ShiyaoProjectConvert.INSTANCE.convert(createReqVO);
        shiyaoProjectMapper.insert(shiyaoProject);
        // 返回
        return shiyaoProject.getId();
    }

    @Override
    public void updateShiyaoProject(ShiyaoProjectUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateShiyaoProjectExists(updateReqVO.getId());
        // 更新
        ShiyaoProjectDO updateObj = ShiyaoProjectConvert.INSTANCE.convert(updateReqVO);
        shiyaoProjectMapper.updateById(updateObj);
    }

    @Override
    public void deleteShiyaoProject(Integer id) {
        // 校验存在
        this.validateShiyaoProjectExists(id);
        // 删除
        shiyaoProjectMapper.deleteById(id);
    }

    private void validateShiyaoProjectExists(Integer id) {
        if (shiyaoProjectMapper.selectById(id) == null) {
            throw exception(SHIYAO_PROJECT_NOT_EXISTS);
        }
    }

    @Override
    public ShiyaoProjectDO getShiyaoProject(Integer id) {
        return shiyaoProjectMapper.selectById(id);
    }

    @Override
    public List<ShiyaoProjectDO> getShiyaoProjectList(Collection<Integer> ids) {
        return shiyaoProjectMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ShiyaoProjectDO> getShiyaoProjectPage(ShiyaoProjectPageReqVO pageReqVO) {
        List<AreaDO> areaList=null;
         areaList = areaRedisDAO.getList("areaList");
        if(CollectionUtil.isEmpty(areaList)) {
            areaList = systemAreaService.getAreaList();
            areaRedisDAO.setList("areaList",areaList,ZERO);
        }
        Map<String, String> areaMap = areaList.stream().distinct().collect(Collectors.toMap(AreaDO::getAreaCode, AreaDO::getName));
        PageResult<ShiyaoProjectDO> shiyaoProjectDOPageResult = shiyaoProjectMapper.selectPage(pageReqVO);
        List<ShiyaoProjectDO> collect = shiyaoProjectDOPageResult.getList().stream().map(shiyaoProjectDO -> {
            String address = shiyaoProjectDO.getAddress();
            String[] split = address.split(",");
            String addresss = "";
            for (String s : split) {
                String name = areaMap.get(s);
                addresss += name + "/";
            }
//                String name = areaMap.get(shiyaoProjectDO.getAddress());
            if(addresss.endsWith("/")){
                addresss = addresss.substring(0, addresss.length() - 1);
            }
            shiyaoProjectDO.setAddress(addresss);
            return shiyaoProjectDO;
        }).collect(Collectors.toList());
        shiyaoProjectDOPageResult.setList(collect);
        return shiyaoProjectDOPageResult;
    }

    @Override
    public List<ShiyaoProjectDO> getShiyaoProjectList(ShiyaoProjectExportReqVO exportReqVO) {
        return shiyaoProjectMapper.selectList(exportReqVO);
    }

    @Override
    public List<ShiyaoProjectDO> getShiyaoProjectList() {
        return shiyaoProjectMapper.selectList();
    }


}
