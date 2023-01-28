package cn.iocoder.yudao.module.system.dal.dataobject.quota;

import lombok.Data;

@Data
public class QuotaStatisticsDO {
    private  String nickName;
    private  Long quotaExpetSum;
    private  Long quotaActualSum;
    private  Long saleId;
}
