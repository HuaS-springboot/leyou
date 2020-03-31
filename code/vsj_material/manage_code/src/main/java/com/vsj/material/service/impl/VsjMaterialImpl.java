package com.vsj.material.service.impl;

import com.aliyuncs.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.utils.StringUtil;
import com.vsj.material.dao.VsjMaterialDao;
import com.vsj.material.dao.VsjMaterialRegisterDAO;
import com.vsj.material.model.VsjCollection;
import com.vsj.material.model.VsjMaterial;
import com.vsj.material.model.VsjMaterialRegister;
import com.vsj.material.model.convert.AbstractObjectConverter;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.model.request.VsjMaterialRequest;
import com.vsj.material.model.request.VsjMaterialRequestList;
import com.vsj.material.model.response.VsjMaterialCollectResponse;
import com.vsj.material.service.VsjMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * @Classname VsjMaterialImpl
 * @Description 素材管理实现类
 * @Date 2019/8/13 18:10
 * @Created by sxm
 */

@Service
public class VsjMaterialImpl implements VsjMaterialService {

    @Autowired
    private VsjMaterialDao vsjMaterialDao;
    @Autowired
    private VsjMaterialRegisterDAO vsjMaterialRegisterDAO;

    @Autowired
    private AbstractObjectConverter<VsjMaterialRequest, VsjMaterial> converter;

    @Autowired
    private AbstractObjectConverter<VsjMaterialRequest, VsjMaterial> convert;

    @Override
    public Object getMaterialList(QueryStat queryStat) {
        PageHelper.startPage(queryStat.getPageNum(),queryStat.getPageSize());
        List<VsjMaterial> list = vsjMaterialDao.getMaterialList(queryStat);
        for(VsjMaterial vsjMaterial:list){
            queryStat.setId(vsjMaterial.getSysUsrId());
            List<VsjMaterialRegister> registers = vsjMaterialRegisterDAO.getRegister(queryStat);
            vsjMaterial.setNickName(registers.get(0).getNickName());
        }
        PageInfo<VsjMaterial> pageInfo = new PageInfo<>(list);
        return BaseResponseParam.success(pageInfo);
    }

    @Override
    public Object updateMaterials(VsjMaterialRequestList list, Integer platformCode) {
        //  List<VsjMaterial> materialList = new ArrayList<>();
        List<VsjMaterialRequest> vsjMaterialRequestList = list.getVsjMaterialRequestList();
        if (vsjMaterialRequestList.isEmpty() || platformCode == null) {
            return BaseResponse.fail("参数错误");
        }
        for (VsjMaterialRequest materialRequest : vsjMaterialRequestList) {
            VsjMaterial vsjMaterial = convert.convert(materialRequest, VsjMaterial.class);
            vsjMaterial.setPlatformCode(platformCode);
            //materialList.add(vsjMaterial);
            vsjMaterialDao.updateMaterial(vsjMaterial);
        }

        return BaseResponse.success();
    }

    @Override
    public Object deleteMaterial(QueryStat querySet) {
        if (querySet.getIds().isEmpty() || querySet.getPlatformCode() == null) {
            return BaseResponse.fail("参数错误");
        }
        vsjMaterialDao.deleteMaterial(querySet);
        return BaseResponse.success();
    }

    @Override
    public Object insertMaterial(VsjMaterialRequest vsjMaterialRequest, Integer platformCode,Integer userId) {

        VsjMaterial vsjMaterial = convert.convert(vsjMaterialRequest, VsjMaterial.class);
        if (platformCode==null || userId==null){
            return BaseResponseParam.fail("参数错误");
        }
        if (vsjMaterial.getResourceType()==null){
            return BaseResponseParam.fail("请标明资源类型");
        }
        if(vsjMaterial.getResourceType()==2 || vsjMaterial.getResourceType()==3){
            vsjMaterial.setStatus(1);
        }
        vsjMaterial.setPlatformCode(platformCode);
        vsjMaterial.setSysUsrId(userId);

        int count = vsjMaterialDao.insertMaterial(vsjMaterial);
        if (count < 0) {
            return BaseResponse.fail("传入参数为空");
        }
        return BaseResponse.success();
    }

    @Override
    public Object keepCountingt(String mid) {
        if (StringUtils.isEmpty(mid)) {
            return BaseResponseParam.fail("参数错误");
        }
        //更新下载次数
        int updateFlag = vsjMaterialDao.updateCount(mid);
        if (updateFlag < 0) {
            return BaseResponseParam.fail("更新失败");
        }
        //查看最新下载次数
        VsjMaterial vsjMaterial = vsjMaterialDao.queryCount(mid);
        Integer downloadNum = vsjMaterial.getDownloadNum();
//        return downloadNum;
        return BaseResponseParam.success(downloadNum);
    }

    @Override
    @Transactional
    public Object addLike(String mid, Integer userid, Integer code, Long type) {


        if (StringUtils.isEmpty(mid)) {
            return BaseResponseParam.fail("参数错误");
        }
        if (type != null) {
            //删除记录
            int delete = vsjMaterialDao.deleteCollectionByid(mid, userid);
            //文章点赞数减1
            int updata = vsjMaterialDao.updateNumu(mid);
            return BaseResponseParam.success();
        } else {
            //新增记录，同时文章的点赞数加1
            int i = vsjMaterialDao.insertCollection(mid, userid, code);
            //新增记录
            int insert = vsjMaterialDao.updateNumd(mid);
            return BaseResponseParam.success();
        }
    }

    @Override
    public Object getMyMaterialList(QueryStat querySet) {
        if (querySet.getUseId() == null || querySet.getPlatformCode() == null) {
            return BaseResponseParam.fail("参数错误");
        }
        PageHelper.startPage(querySet.getPageNum(), querySet.getPageSize());
        // 根据isMylike是否传值 查看全部素材或者我的收藏
        List<VsjMaterialCollectResponse> list = vsjMaterialDao.getMyMaterialList(querySet);
        Integer useId = querySet.getUseId();
        //查看当前用户收藏的素材id 为其赋值
        List<Integer> list1 = vsjMaterialDao.getMyCollectIds(useId);
        for (VsjMaterialCollectResponse vsjMaterialCollectResponse : list) {
            for (int id : list1) {
                if (vsjMaterialCollectResponse.getId() == id) {
                    vsjMaterialCollectResponse.setCollect(1);
                }
            }
        }
        PageInfo<VsjMaterialCollectResponse> pageInfo = new PageInfo<>(list);
        return BaseResponseParam.success(pageInfo);
    }

    @Override
    public Object getAudioVideoMaterialList(QueryStat queryStat) {
        if (queryStat.getPlatformCode()==null){
            return BaseResponseParam.fail("参数错误");
        }
        PageHelper.startPage(queryStat.getPageNum(),queryStat.getPageSize());
        List<VsjMaterial> list = vsjMaterialDao.getAudioVideoMaterialList(queryStat);
        PageInfo<VsjMaterial> vsjMaterialPageInfo = new PageInfo<>(list);
        return BaseResponseParam.success(vsjMaterialPageInfo);
    }
}
