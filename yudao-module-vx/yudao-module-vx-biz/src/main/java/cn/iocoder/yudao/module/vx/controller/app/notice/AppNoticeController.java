package cn.iocoder.yudao.module.vx.controller.app.notice;

import cn.iocoder.yudao.module.vx.controller.admin.notice.vo.NoticeCreateReqVO;
import cn.iocoder.yudao.module.vx.controller.admin.notice.vo.NoticePageReqVO;
import cn.iocoder.yudao.module.vx.controller.admin.notice.vo.NoticeUpdateReqVO;
import cn.iocoder.yudao.module.vx.dal.dataobject.notice.VxNoticeDO;
import cn.iocoder.yudao.module.vx.service.notice.VxNoticeService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.*;

import javax.validation.*;
import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import cn.iocoder.yudao.module.vx.controller.app.notice.vo.*;
import cn.iocoder.yudao.module.vx.convert.notice.NoticeConvert;

@Api(tags = "用户 APP - 公告栏")
@RestController
@RequestMapping("/vx/notice")
@Validated
public class AppNoticeController {

    @Resource
    private VxNoticeService noticeService;

    @PostMapping("/create")
    @ApiOperation("创建公告栏")

    public CommonResult<Integer> createNotice(@Valid @RequestBody NoticeCreateReqVO createReqVO) {
        return success(noticeService.createNotice(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新公告栏")

    public CommonResult<Boolean> updateNotice(@Valid @RequestBody NoticeUpdateReqVO updateReqVO) {
        noticeService.updateNotice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除公告栏")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Integer.class)

    public CommonResult<Boolean> deleteNotice(@RequestParam("id") Integer id) {
        noticeService.deleteNotice(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得公告栏")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)

    public CommonResult<AppNoticeRespVO> getNotice(@RequestParam("id") Integer id) {
        VxNoticeDO notice = noticeService.getNotice(id);
        return success(NoticeConvert.INSTANCE.appconvert(notice));
    }

    @GetMapping("/list")
    @ApiOperation("获得公告栏列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)

    public CommonResult<List<AppNoticeRespVO>> getNoticeList(@RequestParam("ids") Collection<Integer> ids) {
        List<VxNoticeDO> list = noticeService.getNoticeList(ids);
        return success(NoticeConvert.INSTANCE.appconvertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得公告栏分页")

    public CommonResult<PageResult<AppNoticeRespVO>> getNoticePage(@Valid NoticePageReqVO pageVO) {
        PageResult<VxNoticeDO> pageResult = noticeService.getNoticePage(pageVO);
        return success(NoticeConvert.INSTANCE.appconvertPage(pageResult));
    }

}
