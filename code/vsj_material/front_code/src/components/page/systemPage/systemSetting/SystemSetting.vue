<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 系统参数</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box">
                <el-form ref="mallsetting" :model="mallsetting" :rules="rules" label-width="160px" label-position="left">
                    <div class="titleTag">系统参数</div>
                    <el-form-item label="奖励模式" v-if='mallsetting.bounty_mode!=undefined' >
                        <el-radio-group v-model="mallsetting.bounty_mode">
                            <el-radio label="0">关闭</el-radio>
                            <el-radio label="1">分销模式</el-radio>
                            <el-radio label="2">经销商极差模式</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="未付款订单关闭(小时)" v-if='mallsetting.order_close_time!=undefined' prop="order_close_time">
                        <el-input v-model="mallsetting.order_close_time"></el-input>
                    </el-form-item>
                    <el-form-item label="发货后自动收货天数" v-if='mallsetting.order_confirm_day!=undefined' prop="order_confirm_day">
                        <el-input v-model="mallsetting.order_confirm_day"></el-input>
                    </el-form-item>
                    <el-form-item label="收货后允许退款天数" v-if='mallsetting.order_allow_refund_day!=undefined' prop="order_allow_refund_day">
                        <el-input v-model="mallsetting.order_allow_refund_day"></el-input><br>(超过天数后标志订单结束,奖励金可转为余额)
                    </el-form-item>
                    
                    <el-form-item label="是否强制绑定手机号" v-if='mallsetting.compulsory_binding_phone!=undefined' >
                        <el-radio-group v-model="mallsetting.compulsory_binding_phone">
                            <el-radio label="0">否</el-radio>
                            <el-radio label="1">是</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    
                    
                    <div class="titleTag">系统分享设置</div>
                    <el-form-item label="分享标题" v-if='mallsetting.sys_share_title!=undefined'>
                        <el-input v-model="mallsetting.sys_share_title"></el-input>
                    </el-form-item>    
                    <el-form-item label="分享图标">
                        <div class="el-upload-list el-upload-list--picture-card uploadCom" v-if="sys_share_pic!=''">
                            <div tabindex="0" class="el-upload-list__item is-success">
                                <div data-v-452ce00c="">
                                    <img data-v-452ce00c="" :src="mallsetting.sys_share_pic" alt="" class="el-upload-list__item-thumbnail">
                                    <span data-v-452ce00c="" class="el-upload-list__item-actions">
                                        <span data-v-452ce00c="" class="el-upload-list__item-preview" @click="handlePictureCardPreview(mallsetting.sys_share_pic)">
                                            <i data-v-452ce00c="" class="el-icon-zoom-in"></i>
                                        </span>
                                        <span data-v-452ce00c="" class="el-upload-list__item-delete" @click='delDefaultLoad'>
                                            <i data-v-452ce00c="" class="el-icon-delete"></i>
                                        </span>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <el-upload
                        class="uploadCom"
                        action="http://172.16.0.231:8751/api/v1/upload/uploadFile"
                        ref="upload"
                        :headers="headers"
                        list-type="picture-card"
                        :on-exceed="handleExceed"
                        :on-success="loadingSuccess"
                        :limit="1"
                         >
                            <i slot="default" class="el-icon-plus"></i>
                            <div slot="file" slot-scope="{file}">
                                <img
                                    class="el-upload-list__item-thumbnail"
                                    :src="file.url" alt=""
                                >
                                <span class="el-upload-list__item-actions">
                                    <span
                                    class="el-upload-list__item-preview"
                                    @click="handlePictureCardPreview(file.url)"
                                    >
                                    <i class="el-icon-zoom-in"></i>
                                    </span>
                                    <span
                                    v-if="!disabled"
                                    class="el-upload-list__item-delete"
                                    @click="loadingRemove(file)"
                                    >
                                    <i class="el-icon-delete"></i>
                                    </span>
                                </span>
                            </div>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="分享描述" v-if='mallsetting.sys_share_desc!=undefined'>
                        <el-input type="textarea" rows="5" v-model="mallsetting.sys_share_desc"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">保存设置</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
        <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>

    </div>
</template>

<script>
import { axiosRequest } from '@/api/index';
import { arrToObj,objToArr } from '@/components/common/common';
import {rules} from '../../../common/rule'
 import "@/assets/css/upload.css";
    export default {
        name: 'baseform',
        data: function(){
            return {
                mallsetting:{
                    'bounty_mode':'0',
                    'order_close_time':'',
                    'order_confirm_day':'',
                    'order_allow_refund_day':'',
                    'compulsory_binding_phone':'0',
                    'sys_share_title':'',
                    'sys_share_pic':'',
                    'sys_share_desc':''
                },
                headers:{
                    "token":localStorage.getItem('token'),
                    "userId":localStorage.getItem("userId"),
                    "platformCode":this.$store.state.platformCode
                }, 
                sys_share_pic:'',
                rules:rules.sysSetting,
                dialogImageUrl:'',
                dialogVisible: false,
                disabled: false,
                
            }
        },
        created(){
            this.getMallSetting()
        },
        methods: {
            //获取商城配置
            getMallSetting(){
                axiosRequest('system/query',{
                    type:"2",
                },"post").then((res)=>{
                    this.mallsetting=arrToObj(res) 
                    this.sys_share_pic=this.mallsetting.sys_share_pic
                })
            },
            onSubmit() {
                this.$refs.mallsetting.validate((valid) => {
                    if (valid) {
                        axiosRequest('system/update',{'vsjSysConfigList':objToArr(this.mallsetting)},'post').then((res)=>{
                            this.$message.success('提交成功！');
                        })        
                        
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
                
            },
            // 图上传成功
            loadingSuccess(file){
                this.sys_share_pic=""
                this.mallsetting.sys_share_pic=file.data.url
                console.log(this.mallsetting)
            },

            //删除图片
            loadingRemove(file) {
                this.$refs.upload.clearFiles();
                this.mallsetting.sys_share_pic=""
            },

            //删除默认图片
            delDefaultLoad(){
                this.sys_share_pic=""
            },

            //图片放大
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file;
                this.dialogVisible = true;
            },
            handleExceed(files, fileList) {
                this.$message.warning(`LOGO图片最多上传一张`);
            },

        }
    }
</script>
<style scoped>
   
    .titleTag{
        font-size: 17px;
        padding: 8px 0;
        color: #409EFF; 
    }

</style>
