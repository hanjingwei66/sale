package cn.iocoder.yudao.module.system.service.user;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.module.infra.api.file.FileApi;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.profile.UserProfileUpdatePasswordReqVO;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.profile.UserProfileUpdateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.user.*;
import cn.iocoder.yudao.module.system.convert.user.UserConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.UserPostDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.dept.UserPostMapper;
import cn.iocoder.yudao.module.system.dal.mysql.user.AdminUserMapper;
import cn.iocoder.yudao.module.system.service.dept.DeptService;
import cn.iocoder.yudao.module.system.service.dept.PostService;
import cn.iocoder.yudao.module.system.service.permission.PermissionService;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * ???????????? Service ?????????
 * @author ????????????
 */
@Service("adminUserService")
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {

    @Value("${sys.user.init-password:yudaoyuanma}")
    private String userInitPassword;

    @Resource
    private AdminUserMapper userMapper;

    @Resource
    private DeptService deptService;
    @Resource
    private PostService postService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserPostMapper userPostMapper;

    @Resource
    private FileApi fileApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUser(UserCreateReqVO reqVO) {
        // ??????????????????
        // ???????????????
        checkCreateOrUpdate(null, reqVO.getUsername(), reqVO.getMobile(), reqVO.getEmail(),
                reqVO.getDeptId(), reqVO.getPostIds());
        // ????????????
        AdminUserDO user = UserConvert.INSTANCE.convert(reqVO);
        user.setStatus(CommonStatusEnum.ENABLE.getStatus()); // ????????????
        user.setPassword(passwordEncoder.encode(reqVO.getPassword())); // ????????????
        userMapper.insert(user);
        // ??????????????????
        if (CollectionUtil.isNotEmpty(user.getPostIds())) {
            userPostMapper.insertBatch(convertList(user.getPostIds(),
                    postId -> new UserPostDO().setUserId(user.getId()).setPostId(postId)));
        }
        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserUpdateReqVO reqVO) {
        // ???????????????
        checkCreateOrUpdate(reqVO.getId(), reqVO.getUsername(), reqVO.getMobile(), reqVO.getEmail(),
                reqVO.getDeptId(), reqVO.getPostIds());
        // ????????????
        AdminUserDO updateObj = UserConvert.INSTANCE.convert(reqVO);
        userMapper.updateById(updateObj);
        // ????????????
        updateUserPost(reqVO, updateObj);
    }

    private void updateUserPost(UserUpdateReqVO reqVO, AdminUserDO updateObj) {
        Long userId = reqVO.getId();
        Set<Long> dbPostIds = convertSet(userPostMapper.selectListByUserId(userId), UserPostDO::getPostId);
        // ????????????????????????????????????
        Set<Long> postIds = updateObj.getPostIds();
        Collection<Long> createPostIds = CollUtil.subtract(postIds, dbPostIds);
        Collection<Long> deletePostIds = CollUtil.subtract(dbPostIds, postIds);
        // ???????????????????????????????????????????????????????????????????????????
        if (!CollectionUtil.isEmpty(createPostIds)) {
            userPostMapper.insertBatch(convertList(createPostIds,
                    postId -> new UserPostDO().setUserId(userId).setPostId(postId)));
        }
        if (!CollectionUtil.isEmpty(deletePostIds)) {
            userPostMapper.deleteByUserIdAndPostId(userId, deletePostIds);
        }
    }

    @Override
    public void updateUserLogin(Long id, String loginIp) {
        userMapper.updateById(new AdminUserDO().setId(id).setLoginIp(loginIp).setLoginDate(new Date()));
    }

    @Override
    public void updateUserProfile(Long id, UserProfileUpdateReqVO reqVO) {
        // ???????????????
        checkUserExists(id);
        checkEmailUnique(id, reqVO.getEmail());
        checkMobileUnique(id, reqVO.getMobile());
        // ????????????
        userMapper.updateById(UserConvert.INSTANCE.convert(reqVO).setId(id));
    }

    @Override
    public void updateUserPassword(Long id, UserProfileUpdatePasswordReqVO reqVO) {
        // ?????????????????????
        checkOldPassword(id, reqVO.getOldPassword());
        // ????????????
        AdminUserDO updateObj = new AdminUserDO().setId(id);
        updateObj.setPassword(encodePassword(reqVO.getNewPassword())); // ????????????
        userMapper.updateById(updateObj);
    }

    @Override
    public String updateUserAvatar(Long id, InputStream avatarFile) throws Exception {
        checkUserExists(id);
        // ????????????
        String avatar = fileApi.createFile(IoUtil.readBytes(avatarFile));
        // ????????????
        AdminUserDO sysUserDO = new AdminUserDO();
        sysUserDO.setId(id);
        sysUserDO.setAvatar(avatar);
        userMapper.updateById(sysUserDO);
        return avatar;
    }

    @Override
    public void updateUserPassword(Long id, String password) {
        // ??????????????????
        checkUserExists(id);
        // ????????????
        AdminUserDO updateObj = new AdminUserDO();
        updateObj.setId(id);
        updateObj.setPassword(encodePassword(password)); // ????????????
        userMapper.updateById(updateObj);
    }

    @Override
    public void updateUserStatus(Long id, Integer status) {
        // ??????????????????
        checkUserExists(id);
        // ????????????
        AdminUserDO updateObj = new AdminUserDO();
        updateObj.setId(id);
        updateObj.setStatus(status);
        userMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        // ??????????????????
        checkUserExists(id);
        // ????????????
        userMapper.deleteById(id);
        // ????????????????????????
        permissionService.processUserDeleted(id);
        // ??????????????????
        userPostMapper.deleteByUserId(id);
    }

    @Override
    public AdminUserDO getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public AdminUserDO getUserByMobile(String mobile) {
        return userMapper.selectByMobile(mobile);
    }

    @Override
    public PageResult<AdminUserDO> getUserPage(UserPageReqVO reqVO) {
        return userMapper.selectPage(reqVO, getDeptCondition(reqVO.getDeptId()));
    }

    @Override
    public AdminUserDO getUser(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<AdminUserDO> getUsersByDeptIds(Collection<Long> deptIds) {
        if (CollUtil.isEmpty(deptIds)) {
            return Collections.emptyList();
        }
        return userMapper.selectListByDeptIds(deptIds);
    }

    @Override
    public List<AdminUserDO> getUsersByPostIds(Collection<Long> postIds) {
        if (CollUtil.isEmpty(postIds)) {
            return Collections.emptyList();
        }
        Set<Long> userIds = convertSet(userPostMapper.selectListByPostIds(postIds), UserPostDO::getUserId);
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        return userMapper.selectBatchIds(userIds);
    }

    @Override
    public List<AdminUserDO> getUsers(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return userMapper.selectBatchIds(ids);
    }

    @Override
    public void validUsers(Set<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // ??????????????????
        List<AdminUserDO> users = userMapper.selectBatchIds(ids);
        Map<Long, AdminUserDO> userMap = CollectionUtils.convertMap(users, AdminUserDO::getId);
        // ??????
        ids.forEach(id -> {
            AdminUserDO user = userMap.get(id);
            if (user == null) {
                throw exception(USER_NOT_EXISTS);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(user.getStatus())) {
                throw exception(USER_IS_DISABLE, user.getNickname());
            }
        });
    }

    @Override
    public List<AdminUserDO> getUsers(UserExportReqVO reqVO) {
        return userMapper.selectList(reqVO, getDeptCondition(reqVO.getDeptId()));
    }

    @Override
    public List<AdminUserDO> getUsersByNickname(String nickname) {
        return userMapper.selectListByNickname(nickname);
    }

    @Override
    public List<AdminUserDO> getUsersByUsername(String username) {
        return userMapper.selectListByUsername(username);
    }

    /**
     * ???????????????????????????????????????????????????????????????????????????
     * @param deptId ????????????
     * @return ??????????????????
     */
    private Set<Long> getDeptCondition(Long deptId) {
        if (deptId == null) {
            return Collections.emptySet();
        }
        Set<Long> deptIds = convertSet(deptService.getDeptsByParentIdFromCache(
                deptId, true), DeptDO::getId);
        deptIds.add(deptId); // ????????????
        return deptIds;
    }

    private void checkCreateOrUpdate(Long id, String username, String mobile, String email,
                                     Long deptId, Set<Long> postIds) {
        // ??????????????????
        checkUserExists(id);
        // ?????????????????????
        checkUsernameUnique(id, username);
        // ?????????????????????
        checkMobileUnique(id, mobile);
        // ??????????????????
        checkEmailUnique(id, email);
        // ??????????????????????????????
        deptService.validDepts(CollectionUtils.singleton(deptId));
        // ??????????????????????????????
        postService.validPosts(postIds);
    }

    @VisibleForTesting
    public void checkUserExists(Long id) {
        if (id == null) {
            return;
        }
        AdminUserDO user = userMapper.selectById(id);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
    }

    @VisibleForTesting
    public void checkUsernameUnique(Long id, String username) {
        if (StrUtil.isBlank(username)) {
            return;
        }
        AdminUserDO user = userMapper.selectByUsername(username);
        if (user == null) {
            return;
        }
        // ?????? id ?????????????????????????????????????????? id ?????????
        if (id == null) {
            throw exception(USER_USERNAME_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw exception(USER_USERNAME_EXISTS);
        }
    }

    @VisibleForTesting
    public void checkEmailUnique(Long id, String email) {
        if (StrUtil.isBlank(email)) {
            return;
        }
        AdminUserDO user = userMapper.selectByEmail(email);
        if (user == null) {
            return;
        }
        // ?????? id ?????????????????????????????????????????? id ?????????
        if (id == null) {
            throw exception(USER_EMAIL_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw exception(USER_EMAIL_EXISTS);
        }
    }

    @VisibleForTesting
    public void checkMobileUnique(Long id, String mobile) {
        if (StrUtil.isBlank(mobile)) {
            return;
        }
        AdminUserDO user = userMapper.selectByMobile(mobile);
        if (user == null) {
            return;
        }
        // ?????? id ?????????????????????????????????????????? id ?????????
        if (id == null) {
            throw exception(USER_MOBILE_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw exception(USER_MOBILE_EXISTS);
        }
    }

    /**
     * ???????????????
     * @param id          ?????? id
     * @param oldPassword ?????????
     */
    @VisibleForTesting
    public void checkOldPassword(Long id, String oldPassword) {
        AdminUserDO user = userMapper.selectById(id);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        if (!isPasswordMatch(oldPassword, user.getPassword())) {
            throw exception(USER_PASSWORD_FAILED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // ??????????????????????????????????????????
    public UserImportRespVO importUsers(List<UserImportExcelVO> importUsers, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importUsers)) {
            throw exception(USER_IMPORT_LIST_IS_EMPTY);
        }
        UserImportRespVO respVO = UserImportRespVO.builder().createUsernames(new ArrayList<>())
                .updateUsernames(new ArrayList<>()).failureUsernames(new LinkedHashMap<>()).build();
        importUsers.forEach(importUser -> {
            // ??????????????????????????????????????????
            try {
                checkCreateOrUpdate(null, null, importUser.getMobile(), importUser.getEmail(),
                        importUser.getDeptId(), null);
            } catch (ServiceException ex) {
                respVO.getFailureUsernames().put(importUser.getUsername(), ex.getMessage());
                return;
            }
            // ???????????????????????????????????????
            AdminUserDO existUser = userMapper.selectByUsername(importUser.getUsername());
            if (existUser == null) {
                userMapper.insert(UserConvert.INSTANCE.convert(importUser)
                        .setPassword(encodePassword(userInitPassword))); // ??????????????????
                respVO.getCreateUsernames().add(importUser.getUsername());
                return;
            }
            // ???????????????????????????????????????
            if (!isUpdateSupport) {
                respVO.getFailureUsernames().put(importUser.getUsername(), USER_USERNAME_EXISTS.getMsg());
                return;
            }
            AdminUserDO updateUser = UserConvert.INSTANCE.convert(importUser);
            updateUser.setId(existUser.getId());
            userMapper.updateById(updateUser);
            respVO.getUpdateUsernames().add(importUser.getUsername());
        });
        return respVO;
    }

    @Override
    public List<AdminUserDO> getUsersByStatus(Integer status) {
        return userMapper.selectListByStatus(status);
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * ?????????????????????
     *
     * @param password ??????
     * @return ??????????????????
     */
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
