package cn.iocoder.yudao.module.system.service.sms;

import cn.iocoder.yudao.module.system.controller.admin.sms.vo.template.SmsTemplateCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.sms.vo.template.SmsTemplateExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.sms.vo.template.SmsTemplatePageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.sms.vo.template.SmsTemplateUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.sms.SmsChannelDO;
import cn.iocoder.yudao.module.system.dal.dataobject.sms.SmsTemplateDO;
import cn.iocoder.yudao.module.system.dal.mysql.sms.SmsTemplateMapper;
import cn.iocoder.yudao.module.system.mq.producer.sms.SmsProducer;
import cn.iocoder.yudao.module.system.enums.sms.SmsTemplateTypeEnum;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.collection.ArrayUtils;
import cn.iocoder.yudao.framework.common.util.object.ObjectUtils;
import cn.iocoder.yudao.framework.sms.core.client.SmsClient;
import cn.iocoder.yudao.framework.sms.core.client.SmsClientFactory;
import cn.iocoder.yudao.framework.sms.core.client.SmsCommonResult;
import cn.iocoder.yudao.framework.sms.core.client.dto.SmsTemplateRespDTO;
import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static cn.hutool.core.bean.BeanUtil.getFieldValue;
import static cn.hutool.core.util.RandomUtil.randomEle;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.max;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.buildTime;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.assertPojoEquals;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.assertServiceException;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Import(SmsTemplateServiceImpl.class)
public class SmsTemplateServiceTest extends BaseDbUnitTest {

    @Resource
    private SmsTemplateServiceImpl smsTemplateService;

    @Resource
    private SmsTemplateMapper smsTemplateMapper;

    @MockBean
    private SmsChannelService smsChannelService;
    @MockBean
    private SmsClientFactory smsClientFactory;
    @MockBean
    private SmsClient smsClient;
    @MockBean
    private SmsProducer smsProducer;

    @Test
    @SuppressWarnings("unchecked")
    void testInitLocalCache() {
        // mock ??????
        SmsTemplateDO smsTemplate01 = randomSmsTemplateDO();
        smsTemplateMapper.insert(smsTemplate01);
        SmsTemplateDO smsTemplate02 = randomSmsTemplateDO();
        smsTemplateMapper.insert(smsTemplate02);

        // ??????
        smsTemplateService.initLocalCache();
        // ?????? deptCache ??????
        Map<String, SmsTemplateDO> smsTemplateCache = (Map<String, SmsTemplateDO>) getFieldValue(smsTemplateService, "smsTemplateCache");
        assertEquals(2, smsTemplateCache.size());
        assertPojoEquals(smsTemplate01, smsTemplateCache.get(smsTemplate01.getCode()));
        assertPojoEquals(smsTemplate02, smsTemplateCache.get(smsTemplate02.getCode()));
        // ?????? maxUpdateTime ??????
        Date maxUpdateTime = (Date) getFieldValue(smsTemplateService, "maxUpdateTime");
        assertEquals(max(smsTemplate01.getUpdateTime(), smsTemplate02.getUpdateTime()), maxUpdateTime);
    }

    @Test
    public void testParseTemplateContentParams() {
        // ????????????
        String content = "????????????????????????{operation}?????????????????????{code}";
        // mock ??????

        // ??????
        List<String> params = smsTemplateService.parseTemplateContentParams(content);
        // ??????
        assertEquals(Lists.newArrayList("operation", "code"), params);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCreateSmsTemplate_success() {
        // ????????????
        SmsTemplateCreateReqVO reqVO = randomPojo(SmsTemplateCreateReqVO.class, o -> {
            o.setContent("????????????????????????{operation}?????????????????????{code}");
            o.setStatus(randomEle(CommonStatusEnum.values()).getStatus()); // ?????? status ?????????
            o.setType(randomEle(SmsTemplateTypeEnum.values()).getType()); // ?????? type ??? ??????
        });
        // mock Channel ?????????
        SmsChannelDO channelDO = randomPojo(SmsChannelDO.class, o -> {
            o.setId(reqVO.getChannelId());
            o.setStatus(CommonStatusEnum.ENABLE.getStatus()); // ?????? status ???????????????????????????????????????
        });
        when(smsChannelService.getSmsChannel(eq(channelDO.getId()))).thenReturn(channelDO);
        // mock ?????? API ??????????????????
        when(smsClientFactory.getSmsClient(eq(reqVO.getChannelId()))).thenReturn(smsClient);
        when(smsClient.getSmsTemplate(eq(reqVO.getApiTemplateId()))).thenReturn(randomPojo(SmsCommonResult.class, SmsTemplateRespDTO.class,
                o -> o.setCode(GlobalErrorCodeConstants.SUCCESS.getCode())));

        // ??????
        Long smsTemplateId = smsTemplateService.createSmsTemplate(reqVO);
        // ??????
        assertNotNull(smsTemplateId);
        // ?????????????????????????????????
        SmsTemplateDO smsTemplate = smsTemplateMapper.selectById(smsTemplateId);
        assertPojoEquals(reqVO, smsTemplate);
        assertEquals(Lists.newArrayList("operation", "code"), smsTemplate.getParams());
        assertEquals(channelDO.getCode(), smsTemplate.getChannelCode());
        // ????????????
        verify(smsProducer, times(1)).sendSmsTemplateRefreshMessage();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUpdateSmsTemplate_success() {
        // mock ??????
        SmsTemplateDO dbSmsTemplate = randomSmsTemplateDO();
        smsTemplateMapper.insert(dbSmsTemplate);// @Sql: ?????????????????????????????????
        // ????????????
        SmsTemplateUpdateReqVO reqVO = randomPojo(SmsTemplateUpdateReqVO.class, o -> {
            o.setId(dbSmsTemplate.getId()); // ??????????????? ID
            o.setContent("????????????????????????{operation}?????????????????????{code}");
            o.setStatus(randomEle(CommonStatusEnum.values()).getStatus()); // ?????? status ?????????
            o.setType(randomEle(SmsTemplateTypeEnum.values()).getType()); // ?????? type ??? ??????
        });
        // mock ??????
        SmsChannelDO channelDO = randomPojo(SmsChannelDO.class, o -> {
            o.setId(reqVO.getChannelId());
            o.setStatus(CommonStatusEnum.ENABLE.getStatus()); // ?????? status ???????????????????????????????????????
        });
        when(smsChannelService.getSmsChannel(eq(channelDO.getId()))).thenReturn(channelDO);
        // mock ?????? API ??????????????????
        when(smsClientFactory.getSmsClient(eq(reqVO.getChannelId()))).thenReturn(smsClient);
        when(smsClient.getSmsTemplate(eq(reqVO.getApiTemplateId()))).thenReturn(randomPojo(SmsCommonResult.class, SmsTemplateRespDTO.class,
                o -> o.setCode(GlobalErrorCodeConstants.SUCCESS.getCode())));

        // ??????
        smsTemplateService.updateSmsTemplate(reqVO);
        // ????????????????????????
        SmsTemplateDO smsTemplate = smsTemplateMapper.selectById(reqVO.getId()); // ???????????????
        assertPojoEquals(reqVO, smsTemplate);
        assertEquals(Lists.newArrayList("operation", "code"), smsTemplate.getParams());
        assertEquals(channelDO.getCode(), smsTemplate.getChannelCode());
        // ????????????
        verify(smsProducer, times(1)).sendSmsTemplateRefreshMessage();
    }

    @Test
    public void testUpdateSmsTemplate_notExists() {
        // ????????????
        SmsTemplateUpdateReqVO reqVO = randomPojo(SmsTemplateUpdateReqVO.class);

        // ??????, ???????????????
        assertServiceException(() -> smsTemplateService.updateSmsTemplate(reqVO), SMS_TEMPLATE_NOT_EXISTS);
    }

    @Test
    public void testDeleteSmsTemplate_success() {
        // mock ??????
        SmsTemplateDO dbSmsTemplate = randomSmsTemplateDO();
        smsTemplateMapper.insert(dbSmsTemplate);// @Sql: ?????????????????????????????????
        // ????????????
        Long id = dbSmsTemplate.getId();

        // ??????
        smsTemplateService.deleteSmsTemplate(id);
       // ????????????????????????
       assertNull(smsTemplateMapper.selectById(id));
        // ????????????
        verify(smsProducer, times(1)).sendSmsTemplateRefreshMessage();
    }

    @Test
    public void testDeleteSmsTemplate_notExists() {
        // ????????????
        Long id = randomLongId();

        // ??????, ???????????????
        assertServiceException(() -> smsTemplateService.deleteSmsTemplate(id), SMS_TEMPLATE_NOT_EXISTS);
    }

    @Test
    public void testGetSmsTemplatePage() {
       // mock ??????
       SmsTemplateDO dbSmsTemplate = randomPojo(SmsTemplateDO.class, o -> { // ???????????????
           o.setType(SmsTemplateTypeEnum.PROMOTION.getType());
           o.setStatus(CommonStatusEnum.ENABLE.getStatus());
           o.setCode("tudou");
           o.setContent("???????????????");
           o.setApiTemplateId("yunai");
           o.setChannelId(1L);
           o.setCreateTime(buildTime(2021, 11, 11));
       });
       smsTemplateMapper.insert(dbSmsTemplate);
       // ?????? type ?????????
       smsTemplateMapper.insert(ObjectUtils.cloneIgnoreId(dbSmsTemplate, o -> o.setType(SmsTemplateTypeEnum.VERIFICATION_CODE.getType())));
       // ?????? status ?????????
       smsTemplateMapper.insert(ObjectUtils.cloneIgnoreId(dbSmsTemplate, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));
       // ?????? code ?????????
       smsTemplateMapper.insert(ObjectUtils.cloneIgnoreId(dbSmsTemplate, o -> o.setCode("yuanma")));
       // ?????? content ?????????
       smsTemplateMapper.insert(ObjectUtils.cloneIgnoreId(dbSmsTemplate, o -> o.setContent("??????")));
       // ?????? apiTemplateId ?????????
       smsTemplateMapper.insert(ObjectUtils.cloneIgnoreId(dbSmsTemplate, o -> o.setApiTemplateId("nai")));
       // ?????? channelId ?????????
       smsTemplateMapper.insert(ObjectUtils.cloneIgnoreId(dbSmsTemplate, o -> o.setChannelId(2L)));
       // ?????? createTime ?????????
       smsTemplateMapper.insert(ObjectUtils.cloneIgnoreId(dbSmsTemplate, o -> o.setCreateTime(buildTime(2021, 12, 12))));
       // ????????????
       SmsTemplatePageReqVO reqVO = new SmsTemplatePageReqVO();
       reqVO.setType(SmsTemplateTypeEnum.PROMOTION.getType());
       reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
       reqVO.setCode("tu");
       reqVO.setContent("?????????");
       reqVO.setApiTemplateId("yu");
       reqVO.setChannelId(1L);
       reqVO.setBeginCreateTime(buildTime(2021, 11, 1));
       reqVO.setEndCreateTime(buildTime(2021, 12, 1));

       // ??????
       PageResult<SmsTemplateDO> pageResult = smsTemplateService.getSmsTemplatePage(reqVO);
       // ??????
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbSmsTemplate, pageResult.getList().get(0));
    }

    @Test
    public void testGetSmsTemplateList() {
        // mock ??????
        SmsTemplateDO dbSmsTemplate = randomPojo(SmsTemplateDO.class, o -> { // ???????????????
            o.setType(SmsTemplateTypeEnum.PROMOTION.getType());
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
            o.setCode("tudou");
            o.setContent("???????????????");
            o.setApiTemplateId("yunai");
            o.setChannelId(1L);
            o.setCreateTime(buildTime(2021, 11, 11));
        });
        smsTemplateMapper.insert(dbSmsTemplate);
        // ?????? type ?????????
        smsTemplateMapper.insert(ObjectUtils.cloneIgnoreId(dbSmsTemplate, o -> o.setType(SmsTemplateTypeEnum.VERIFICATION_CODE.getType())));
        // ?????? status ?????????
        smsTemplateMapper.insert(ObjectUtils.cloneIgnoreId(dbSmsTemplate, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));
        // ?????? code ?????????
        smsTemplateMapper.insert(ObjectUtils.cloneIgnoreId(dbSmsTemplate, o -> o.setCode("yuanma")));
        // ?????? content ?????????
        smsTemplateMapper.insert(ObjectUtils.cloneIgnoreId(dbSmsTemplate, o -> o.setContent("??????")));
        // ?????? apiTemplateId ?????????
        smsTemplateMapper.insert(ObjectUtils.cloneIgnoreId(dbSmsTemplate, o -> o.setApiTemplateId("nai")));
        // ?????? channelId ?????????
        smsTemplateMapper.insert(ObjectUtils.cloneIgnoreId(dbSmsTemplate, o -> o.setChannelId(2L)));
        // ?????? createTime ?????????
        smsTemplateMapper.insert(ObjectUtils.cloneIgnoreId(dbSmsTemplate, o -> o.setCreateTime(buildTime(2021, 12, 12))));
        // ????????????
        SmsTemplateExportReqVO reqVO = new SmsTemplateExportReqVO();
        reqVO.setType(SmsTemplateTypeEnum.PROMOTION.getType());
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        reqVO.setCode("tu");
        reqVO.setContent("?????????");
        reqVO.setApiTemplateId("yu");
        reqVO.setChannelId(1L);
        reqVO.setBeginCreateTime(buildTime(2021, 11, 1));
        reqVO.setEndCreateTime(buildTime(2021, 12, 1));

       // ??????
       List<SmsTemplateDO> list = smsTemplateService.getSmsTemplateList(reqVO);
       // ??????
       assertEquals(1, list.size());
       assertPojoEquals(dbSmsTemplate, list.get(0));
    }

    @Test
    public void testCheckSmsChannel_success() {
        // ????????????
        Long channelId = randomLongId();
        // mock ??????
        SmsChannelDO channelDO = randomPojo(SmsChannelDO.class, o -> {
            o.setId(channelId);
            o.setStatus(CommonStatusEnum.ENABLE.getStatus()); // ?????? status ???????????????????????????????????????
        });
        when(smsChannelService.getSmsChannel(eq(channelId))).thenReturn(channelDO);

        // ??????
        SmsChannelDO returnChannelDO = smsTemplateService.checkSmsChannel(channelId);
        // ??????
        assertPojoEquals(returnChannelDO, channelDO);
    }

    @Test
    public void testCheckSmsChannel_notExists() {
        // ????????????
        Long channelId = randomLongId();

        // ?????????????????????
        assertServiceException(() -> smsTemplateService.checkSmsChannel(channelId),
                SMS_CHANNEL_NOT_EXISTS);
    }

    @Test
    public void testCheckSmsChannel_disable() {
        // ????????????
        Long channelId = randomLongId();
        // mock ??????
        SmsChannelDO channelDO = randomPojo(SmsChannelDO.class, o -> {
            o.setId(channelId);
            o.setStatus(CommonStatusEnum.DISABLE.getStatus()); // ?????? status ?????????????????????
        });
        when(smsChannelService.getSmsChannel(eq(channelId))).thenReturn(channelDO);

        // ?????????????????????
        assertServiceException(() -> smsTemplateService.checkSmsChannel(channelId),
                SMS_CHANNEL_DISABLE);
    }

    @Test
    public void testCheckDictDataValueUnique_success() {
        // ???????????????
        smsTemplateService.checkSmsTemplateCodeDuplicate(randomLongId(), randomString());
    }

    @Test
    public void testCheckSmsTemplateCodeDuplicate_valueDuplicateForCreate() {
        // ????????????
        String code = randomString();
        // mock ??????
        smsTemplateMapper.insert(randomSmsTemplateDO(o -> o.setCode(code)));

        // ?????????????????????
        assertServiceException(() -> smsTemplateService.checkSmsTemplateCodeDuplicate(null, code),
                SMS_TEMPLATE_CODE_DUPLICATE, code);
    }

    @Test
    public void testCheckDictDataValueUnique_valueDuplicateForUpdate() {
        // ????????????
        Long id = randomLongId();
        String code = randomString();
        // mock ??????
        smsTemplateMapper.insert(randomSmsTemplateDO(o -> o.setCode(code)));

        // ?????????????????????
        assertServiceException(() -> smsTemplateService.checkSmsTemplateCodeDuplicate(id, code),
                SMS_TEMPLATE_CODE_DUPLICATE, code);
    }

    // ========== ???????????? ==========

    @SafeVarargs
    private static SmsTemplateDO randomSmsTemplateDO(Consumer<SmsTemplateDO>... consumers) {
        Consumer<SmsTemplateDO> consumer = (o) -> {
            o.setStatus(randomEle(CommonStatusEnum.values()).getStatus()); // ?????? status ?????????
            o.setType(randomEle(SmsTemplateTypeEnum.values()).getType()); // ?????? type ??? ??????
        };
        return randomPojo(SmsTemplateDO.class, ArrayUtils.append(consumer, consumers));
    }

}
