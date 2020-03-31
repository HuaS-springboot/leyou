package com.vsj.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.netflix.discovery.converters.Auto;
import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.MemberEditDetail;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.utils.ExcelUtils;
import com.vsj.common.utils.StringUtil;
import com.vsj.dao.MemberDao;
import com.vsj.dao.VsjMemberDAO;
import com.vsj.dao.VsjRegisterDAO;
import com.vsj.mapper.MemberMapper;
import com.vsj.model.VsjMember;
import com.vsj.model.VsjRegister;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.MemberResponse;
import com.vsj.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private VsjMemberDAO vsjMemberDAO;
    @Autowired
    private VsjRegisterDAO vsjRegisterDAO;
    @Autowired
    private AbstractObjectConverter<QueryStat, BaseQueryStat> queryStatConvert;

    @Override
    public Object getMemberList(QueryStat queryStat) {
        PageHelper.startPage(queryStat.getPageNum(), queryStat.getPageSize());
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        List<MemberResponse> memberList = memberDao.getMemberList(baseQueryStat);
        PageInfo info = new PageInfo(memberList);
        return BaseResponseParam.success(info);
    }

    @Override
    public BaseResponseParam editMemberDetail(MemberEditDetail memberEditDetail) {
        if (memberEditDetail.getParentId() != null || memberEditDetail.getLevelId() != null) {
            //需要处理member表
            String parentName = null;
            Integer parentId = memberEditDetail.getParentId();
            Integer levelId = memberEditDetail.getLevelId();
            //如果把上级改成自己了,直接返回失败
            if(parentId !=null && parentId.intValue() == memberEditDetail.getId().intValue()){
                return BaseResponseParam.fail("修改失败...不能将用户上级设定为自己...");
            }
            if (memberEditDetail.getParentId() != null) {
                VsjMember vsjMember = vsjMemberDAO.selectByPrimaryKey(memberEditDetail.getParentId());
                if (null == vsjMember) {
                    return BaseResponseParam.fail("修改失败...根据推荐人Id未查询到对应推荐人");
                }
                parentName = vsjMember.getRegName();
            }
            VsjMember vsjMember = new VsjMember();
            vsjMember.setId(memberEditDetail.getId());
            vsjMember.setLevelId(levelId);
            vsjMember.setParentId(parentId);
            vsjMember.setParentName(parentName);
            if(StringUtil.isNoEmptyStr(memberEditDetail.getName())){
                vsjMember.setRegName(memberEditDetail.getName());
            }
            vsjMember.setPlatformCode(memberEditDetail.getPlatformCode());
            if (vsjMemberDAO.updateVsjMember(vsjMember) < 1) {
                return BaseResponseParam.fail("修改会员信息失败...");
            }
        }
        //处理注册表register信息
        if (StringUtil.isEmptyStr(memberEditDetail.getName()) && memberEditDetail.getCarryBalance() == null
                && StringUtil.isEmptyStr(memberEditDetail.getTelPhone()) && StringUtil.isEmptyStr(memberEditDetail.getRemark())) {
            //无需修改
            return BaseResponseParam.success();
        } else {
            VsjRegister vsjRegister = new VsjRegister();
            vsjRegister.setId(memberEditDetail.getRegId());
            vsjRegister.setNickName(memberEditDetail.getName());
            vsjRegister.setTelPhone(memberEditDetail.getTelPhone());
            vsjRegister.setCarryBalance(memberEditDetail.getCarryBalance());
            vsjRegister.setRemark(memberEditDetail.getRemark());
            vsjRegister.setPlatformCode(memberEditDetail.getPlatformCode());
            return vsjRegisterDAO.updateRegisterDetail(vsjRegister) > 0 ? BaseResponseParam.success() : BaseResponseParam.fail();
        }

    }

    @Override
    public boolean exportMemberListExcel(QueryStat queryStat, HttpServletResponse response) {
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        //这里后续要补充限制条数
        List<MemberResponse> memberList = memberDao.getMemberList(baseQueryStat);
        if(StringUtil.isEmptyList(memberList)){
            logger.debug("根据条件={},查询到列表为空,无需导出...", JSON.toJSONString(queryStat));
            return false;
        }
        ExcelUtils.exportExcel(memberList, "会员列表", "会员列表", MemberResponse.class, "会员列表.xls", response);
        return true;
    }
}

