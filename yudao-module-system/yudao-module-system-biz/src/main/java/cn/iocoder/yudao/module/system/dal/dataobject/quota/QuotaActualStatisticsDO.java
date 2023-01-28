package cn.iocoder.yudao.module.system.dal.dataobject.quota;

import lombok.Data;

@Data
public class QuotaActualStatisticsDO {
    private  String nickName;
    private  Long actualQuotaSum;
    private  Long saleId;
}
