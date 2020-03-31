package com.vsj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.dao.MaterialDao;
import com.vsj.dao.MemberDao;
import com.vsj.mapper.MaterialMapper;
import com.vsj.model.RegisterRecord;
import com.vsj.model.VsjMaterial;
import com.vsj.model.VsjMemberProfit;
import com.vsj.model.VsjOrderReceiptsRecord;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.BonusDetailsResponse;
import com.vsj.model.response.MemberResponse;
import com.vsj.model.response.UserBalanceResponse;
import com.vsj.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private AbstractObjectConverter<QueryStat, BaseQueryStat> queryStatConvert;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private MaterialDao materialDao;

    @Override
    public Object getMemberList(QueryStat queryStat) {
        PageHelper.startPage(queryStat.getPageNum(), queryStat.getPageSize());
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        List<MemberResponse> memberList = memberDao.getMemberList(baseQueryStat);
        PageInfo info = new PageInfo(memberList);
        return BaseResponseParam.success(info);
    }

    @Override
    public Object getBalanceDeTail(Integer userId) {
        UserBalanceResponse userBalanceResponse = memberDao.getBalanceDeTail(userId);
        return BaseResponseParam.success(userBalanceResponse);
    }

    @Override
    public Object getTransactionDetail(Integer userId) {
        RegisterRecord registerRecord = memberDao.getTransactionDetail(userId);
        return BaseResponseParam.success(registerRecord);
    }

    @Override
    public Object getBonusDetails(Integer userId,Integer isSettle) {
        //定义一个奖励金返回对象
        BonusDetailsResponse bonusDetailsResponse = new BonusDetailsResponse();
        //获取会员分润数据
        VsjMemberProfit memberProfit = memberDao.memberProfit(userId);
        if(isSettle == null){
            //获取会员全部订单收益明细数据
            List<VsjOrderReceiptsRecord> orderReceiptsRecordAll = memberDao.orderReceiptsRecord(userId,isSettle);
            if(orderReceiptsRecordAll!=null && orderReceiptsRecordAll.size()>0){
                bonusDetailsResponse.setNoSettledWages(memberProfit.getNoSettledWages());
                bonusDetailsResponse.setOrderReceipt(orderReceiptsRecordAll);
                bonusDetailsResponse.setSettledWages(memberProfit.getSettledWages());
                bonusDetailsResponse.setTotalWages(memberProfit.getTotalWages());
                return BaseResponseParam.success(bonusDetailsResponse);
            }
            return BaseResponse.fail();
        }else if (isSettle == 0){
            //获取会员未结算订单收益明细数据
            List<VsjOrderReceiptsRecord> orderReceiptsRecordList = memberDao.orderReceiptsRecord(userId,isSettle);
            if(orderReceiptsRecordList!=null && orderReceiptsRecordList.size()>0){
                bonusDetailsResponse.setNoSettledWages(memberProfit.getNoSettledWages());
                bonusDetailsResponse.setOrderReceipt(orderReceiptsRecordList);
                bonusDetailsResponse.setSettledWages(memberProfit.getSettledWages());
                bonusDetailsResponse.setTotalWages(memberProfit.getTotalWages());
                return BaseResponseParam.success(bonusDetailsResponse);
            }
            return BaseResponse.fail();
        }else if (isSettle == 1){
            //获取会员已结算订单收益明细数据
            List<VsjOrderReceiptsRecord> orderReceiptsRecordList = memberDao.orderReceiptsRecord(userId,isSettle);
            if(orderReceiptsRecordList!=null && orderReceiptsRecordList.size()>0){
                bonusDetailsResponse.setNoSettledWages(memberProfit.getNoSettledWages());
                bonusDetailsResponse.setOrderReceipt(orderReceiptsRecordList);
                bonusDetailsResponse.setSettledWages(memberProfit.getSettledWages());
                bonusDetailsResponse.setTotalWages(memberProfit.getTotalWages());
                return BaseResponseParam.success(bonusDetailsResponse);
            }
            return BaseResponse.fail();
        }

        return BaseResponse.fail();
    }

    @Override
    public Object getMaterialList(QueryStat queryStat) {
        PageHelper.startPage(queryStat.getPageNum(), queryStat.getPageSize());
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        List<VsjMaterial> vsjMaterials = materialDao.getMaterialList();
        PageInfo<VsjMaterial> info = new PageInfo<>(vsjMaterials);
        return BaseResponseParam.success(info);
    }


}
