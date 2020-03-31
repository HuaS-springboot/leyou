package com.vsj.common.model.convert;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.request.QueryStat;
import com.vsj.model.request.BaseQueryStat;
import org.springframework.stereotype.Component;

@Component
public class QueryStatConvert extends AbstractObjectConverter<QueryStat, BaseQueryStat> {

    @Override
    protected void convertImpl(QueryStat source, BaseQueryStat target) {
        target.setId(source.getId());
        target.setIds(source.getIds());
        target.setOrderId(source.getOrderId());
        target.setType(source.getType());
        target.setStatus(source.getStatus());
        target.setStartTime(source.getStartTime());
        target.setEndTime(source.getEndTime());
        target.setTitle(source.getTitle());
        target.setOneCateId(source.getOneCateId());
        target.setTwoCateId(source.getTwoCateId());
        target.setThreeCateId(source.getThreeCateId());
        target.setPriceMax(source.getPriceMax());
        target.setPriceMin(source.getPriceMin());
        target.setTelPhone(source.getTelPhone());
        target.setProductName(source.getProductName());
        target.setCreateTime(source.getCreateTime());
        target.setSort(source.getSort());
        target.setLevelId(source.getLevelId());
        target.setParentId(source.getParentId());
        target.setParentName(source.getParentName());
        target.setNickName(source.getNickName());
        target.setOrderNo(source.getOrderNo());
        target.setPayType(source.getPayType());
        target.setCategoryStatus(source.getCategoryStatus());
        target.setPlatformCode(source.getPlatformCode());
        target.setRegId(source.getRegId());
        target.setReferrerId(source.getReferrerId());
        target.setCount(source.getCount());
        target.setProductId(source.getProductId());
        target.setOldId(source.getOldId());
        target.setIp(source.getIp());
        target.setMoney(source.getMoney());
    }

    @Override
    protected void reConvertImpl(BaseQueryStat source, QueryStat target) {

    }
}
