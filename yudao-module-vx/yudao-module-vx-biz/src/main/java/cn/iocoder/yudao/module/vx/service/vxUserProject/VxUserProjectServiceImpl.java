package cn.iocoder.yudao.module.vx.service.vxUserProject;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.vx.controller.admin.shiyaoProject.vo.ShiyaoProjectPageReqVO;
import cn.iocoder.yudao.module.vx.controller.admin.shiyaoProject.vo.ShiyaoProjectRespVO;
import cn.iocoder.yudao.module.vx.controller.admin.vxUser.vo.VxUserCreateReqVO;
import cn.iocoder.yudao.module.vx.controller.admin.vxUser.vo.VxUserSingUpVO;
import cn.iocoder.yudao.module.vx.controller.admin.vxUser.vo.VxUserUpdateReqVO;
import cn.iocoder.yudao.module.vx.convert.shiyaoProject.ShiyaoProjectConvert;
import cn.iocoder.yudao.module.vx.convert.vxUserProject.VxUserProjectConvert;
import cn.iocoder.yudao.module.vx.dal.dataobject.shiyaoProject.ShiyaoProjectDO;
import cn.iocoder.yudao.module.vx.dal.dataobject.vxUser.VxUserDO;
import cn.iocoder.yudao.module.vx.dal.dataobject.vxUserProject.VxUserProjectDO;
import cn.iocoder.yudao.module.vx.dal.mysql.vxUserProject.VxUserProjectMapper;
import cn.iocoder.yudao.module.vx.service.vxUser.VxUserService;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.mchange.lang.IntegerUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

@Service
@Validated
public class VxUserProjectServiceImpl implements VxUserProjectService{
    @Resource
    private VxUserProjectMapper vxUserProjectMapper;
    @Resource
    private VxUserService userService;
    @Override
    public Integer signUp(VxUserSingUpVO vxUserSingUpVO) {
        //如果id为空为新增 如果id不为空修改
        if(null==vxUserSingUpVO.getUserId()) {
            VxUserCreateReqVO vxUserCreateReqVO = VxUserProjectConvert.INSTANCE.convertCreate(vxUserSingUpVO);
            Integer user = userService.createUser(vxUserCreateReqVO);
        }else {
            VxUserUpdateReqVO vxUserCreateReqVO = VxUserProjectConvert.INSTANCE.convertUpdate(vxUserSingUpVO);
            userService.updateUser(vxUserCreateReqVO);
        }
        VxUserProjectDO vxUserProjectDO = VxUserProjectConvert.INSTANCE.convertDO(vxUserSingUpVO);
        int success = vxUserProjectMapper.insert(vxUserProjectDO);
        return success;
    }

    @Override
    public PageResult<ShiyaoProjectRespVO> getSignUpProjectList(ShiyaoProjectPageReqVO id) throws Exception {
        if(StringUtils.isEmpty(id.getUserId())){
            throw new Exception("用户id不能为空");
        }
        PageResult<ShiyaoProjectDO> pageResult = new PageResult<>();
        List<ShiyaoProjectDO> signUpProjectList1 = vxUserProjectMapper.getSignUpProjectList(id);
        Long total = vxUserProjectMapper.selectCount();
        pageResult.setList(signUpProjectList1);
        pageResult.setTotal(total);
        PageResult<ShiyaoProjectRespVO> shiyaoProjectRespVOS = ShiyaoProjectConvert.INSTANCE.convertPage(pageResult);
        return shiyaoProjectRespVOS;
    }
}
