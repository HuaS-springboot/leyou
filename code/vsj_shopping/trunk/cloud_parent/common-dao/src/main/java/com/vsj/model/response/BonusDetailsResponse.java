package com.vsj.model.response;

import com.vsj.model.VsjOrderReceiptsRecord;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class BonusDetailsResponse {
    private BigDecimal settledWages;//结算提成
    private BigDecimal noSettledWages;//未结算提成
    private BigDecimal totalWages;//总提成
    private List<VsjOrderReceiptsRecord> orderReceipt = new ArrayList<>();//会员订单收益明细
}
