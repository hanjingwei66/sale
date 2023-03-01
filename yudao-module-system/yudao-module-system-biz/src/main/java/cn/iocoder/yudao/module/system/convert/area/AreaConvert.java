package cn.iocoder.yudao.module.system.convert.area;

import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.module.system.controller.admin.auth.vo.AuthMenuRespVO;
import cn.iocoder.yudao.module.system.controller.admin.dict.vo.area.DictAreaVO;
import cn.iocoder.yudao.module.system.convert.auth.AuthConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.area.AreaDO;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.MenuDO;
import cn.iocoder.yudao.module.system.enums.permission.MenuIdEnum;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface AreaConvert {
    AreaConvert INSTANCE = Mappers.getMapper(AreaConvert.class);
    DictAreaVO convertTreeNode(AreaDO areaDO);
    default List<DictAreaVO> buildMenuTree(List<AreaDO> areaList) {
        // 排序，保证菜单的有序性
//        menuList.sort(Comparator.comparing(MenuDO::getSort));
        // 构建菜单树
        // 使用 LinkedHashMap 的原因，是为了排序 。实际也可以用 Stream API ，就是太丑了。
        Map<String, DictAreaVO> treeNodeMap = new LinkedHashMap<>();
        areaList.forEach(area -> treeNodeMap.put(area.getAreaCode(), AreaConvert.INSTANCE.convertTreeNode(area)));
        // 处理父子关系
        treeNodeMap.values().stream().filter(node -> !node.getParentId().equals("0")).forEach(childNode -> {
            // 获得父节点
            DictAreaVO parentNode = treeNodeMap.get(childNode.getParentId());
            if (parentNode == null) {
                LoggerFactory.getLogger(getClass()).error("[buildRouterTree][resource({}) 找不到父资源({})]",
                        childNode.getAreaCode(), childNode.getParentId());
                return;
            }
            // 将自己添加到父节点中
            if (parentNode.getChildren() == null) {
                parentNode.setChildren(new ArrayList<>());
            }
            parentNode.getChildren().add(childNode);
        });
        // 获得到所有的根节点
        return CollectionUtils.filterList(treeNodeMap.values(), node -> "0".equals(node.getParentId()));
    }
}
