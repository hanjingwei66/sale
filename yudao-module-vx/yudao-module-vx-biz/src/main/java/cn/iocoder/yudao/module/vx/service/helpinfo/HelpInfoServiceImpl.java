package cn.iocoder.yudao.module.vx.service.helpinfo;

import cn.iocoder.yudao.module.vx.controller.admin.helpinfo.vo.HelpInfoCreateReqVO;
import cn.iocoder.yudao.module.vx.controller.admin.helpinfo.vo.HelpInfoExportReqVO;
import cn.iocoder.yudao.module.vx.controller.admin.helpinfo.vo.HelpInfoPageReqVO;
import cn.iocoder.yudao.module.vx.controller.admin.helpinfo.vo.HelpInfoUpdateReqVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.vx.controller.app.helpinfo.vo.*;
import cn.iocoder.yudao.module.vx.dal.dataobject.helpinfo.HelpInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.vx.convert.helpinfo.HelpInfoConvert;
import cn.iocoder.yudao.module.vx.dal.mysql.helpinfo.HelpInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.vx.enums.ErrorCodeConstants.*;

/**
 * 帮助信息 Service 实现类
 *
 * @author hjw
 */
@Service
@Validated
public class HelpInfoServiceImpl implements HelpInfoService {

    @Resource
    private HelpInfoMapper helpInfoMapper;

    @Override
    public Integer createHelpInfo(HelpInfoCreateReqVO createReqVO) {
        // 插入
        HelpInfoDO helpInfo = HelpInfoConvert.INSTANCE.convert(createReqVO);
        helpInfoMapper.insert(helpInfo);
        // 返回
        return helpInfo.getId();
    }

    @Override
    public void updateHelpInfo(HelpInfoUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateHelpInfoExists(updateReqVO.getId());
        // 更新
        HelpInfoDO updateObj = HelpInfoConvert.INSTANCE.convert(updateReqVO);
        helpInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteHelpInfo(Integer id) {
        // 校验存在
        this.validateHelpInfoExists(id);
        // 删除
        helpInfoMapper.deleteById(id);
    }

    private void validateHelpInfoExists(Integer id) {
        if (helpInfoMapper.selectById(id) == null) {
            throw exception(HELP_INFO_NOT_EXISTS);
        }
    }

    @Override
    public HelpInfoDO getHelpInfo(Integer id) {
        return helpInfoMapper.selectById(id);
    }

    @Override
    public List<HelpInfoDO> getHelpInfoList(Collection<Integer> ids) {
        return helpInfoMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<HelpInfoDO> getHelpInfoPage(HelpInfoPageReqVO pageReqVO) {
        return helpInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HelpInfoDO> getHelpInfoList(HelpInfoExportReqVO exportReqVO) {
        return helpInfoMapper.selectList(exportReqVO);
    }

}
