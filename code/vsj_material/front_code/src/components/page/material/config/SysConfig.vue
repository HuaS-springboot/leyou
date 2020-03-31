<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 系统配置</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box mallSetting">
            	
            	
                <el-form :model="mallsetting" :rules="rules" ref="mallsetting" label-width="160px" label-position="left">
                	
                	<el-form-item label="头部标题" prop="sys_header_title">
                        <el-input v-model="mallsetting.sys_config.sys_header_title"></el-input>
                    </el-form-item>
                    <el-form-item label="分享标题" prop="sys_share_title">
                        <el-input v-model="mallsetting.sys_config.sys_share_title"></el-input>
                    </el-form-item>
                    <el-form-item label="分享描述" prop="sys_share_description">
                        <el-input v-model="mallsetting.sys_config.sys_share_description"></el-input>
                    </el-form-item>
                	
                    <el-form-item label="分享图片">
                        <div class="el-upload-list el-upload-list--picture-card uploadCom" v-if="shopping_logo!=''">
                            <div tabindex="0" class="el-upload-list__item is-success">
                                <div data-v-452ce00c="">
                                    <img data-v-452ce00c="" :src="mallsetting.sys_config.sys_share_image" alt="" class="el-upload-list__item-thumbnail">
                                    <span data-v-452ce00c="" class="el-upload-list__item-actions">
                                        <span data-v-452ce00c="" class="el-upload-list__item-preview" @click="handlePictureCardPreview(mallsetting.shopping_logo)">
                                            <i data-v-452ce00c="" class="el-icon-zoom-in"></i>
                                        </span>
                                        <span data-v-452ce00c="" class="el-upload-list__item-delete" @click='delDefaultLogo'>
                                            <i data-v-452ce00c="" class="el-icon-delete"></i>
                                        </span>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <el-upload
                        class="uploadCom"
                        action="http://172.16.0.225:8888/api/v1/admin/systeme"
                        ref="uploadLogo"
                        :headers="headers"
                        list-type="picture-card"
                        :on-exceed="handleExceed"
                        :on-success="logoSuccess"
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
                                    @click="logoRemove(file)"
                                    >
                                    <i class="el-icon-delete"></i>
                                    </span>
                                </span>
                            </div>
                        </el-upload>
                        
                    </el-form-item>
                    
                    
                     <el-form-item label="指定用户名" prop="sys_specify_username">
                        <el-input v-model="mallsetting.sys_config.sys_specify_username"></el-input>
                        <p>（后台上传的素材,显示的用户名和头像,也可用作商城所有素材统一显示）</p>
                    </el-form-item>
                    
                 
                    
                    
                    <el-form-item label="指定头像" v-show="mallsetting.loading_page_switch==1">
                        <div class="el-upload-list el-upload-list--picture-card uploadCom" v-if="loading_page_path!=''">
                            <div tabindex="0" class="el-upload-list__item is-success">
                                <div data-v-452ce00c="">
                                    <img data-v-452ce00c="" :src="mallsetting.sys_config.sys_specify_header" alt="" class="el-upload-list__item-thumbnail">
                                    <span data-v-452ce00c="" class="el-upload-list__item-actions">
                                        <span data-v-452ce00c="" class="el-upload-list__item-preview" @click="handlePictureCardPreview(mallsetting.loading_page_path)">
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
                        action="http://172.16.0.225:8888/api/v1/admin/systeme"
                        ref="uploadLoad"
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
                    
                   
                    
                    <el-form-item label="素材头像昵称">
                        <el-radio-group v-model="mallsetting.sys_config.sys_specify_header">
                            <el-radio label='0'>使用系统指定的用户名头像</el-radio>
                            <el-radio label='1'>使用登录用户的用户名的头像</el-radio>
                            <el-radio label='2'>使用上传者的用户名头像</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    
                    <el-form-item label="是否强制绑定手机号"  >
                        <el-radio-group v-model="mallsetting.sys_config.sys_building_phone">
                            <el-radio label='0'>是</el-radio>
                            <el-radio label='1'>否</el-radio>
                        </el-radio-group>
                    </el-form-item>

                    <el-form-item>
                        <el-button type="primary" @click="onSubmit()">保存设置</el-button>
                        <el-button>取消</el-button>
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
    import {rules} from '../../../common/rule';
    import "@/assets/css/upload.css";
    export default {
        name: 'baseform',
        data: function(){
            return {
                //配置，默认启动页关闭，分类导航开启
                mallsetting:{
                    "loading_page_switch":0,
					'sys_config':{}

                },
                
                loading_page_path:'',
                shopping_logo:'',
                headers:{
                    "token":localStorage.getItem('token'),
                    "userId":localStorage.getItem("userId"),
                    "platformCode":this.$store.state.platformCode
                }, 
                rules:rules.mallSetting,
                dialogImageUrl:'',
                dialogVisible: false,
                disabled: false,
                
                

            }
        },
        created(){
            this.getSysSetting()
        },
        methods: {
            //获取系统配置
            getSysSetting(){
				
                axiosRequest('admin/system/getSysConfigList',{
                    type:"2"
                   
                },'post').then((res)=>{
                   this.mallsetting.sys_config=arrToObj(res)
                 console.log(this.mallsetting.sys_config)
                })
                
            },
            handleExceed(files, fileList) {
                this.$message.warning(`图片最多上传一张`);
            },
            //保存系统配置
            onSubmit() { 
				
				console.log(objToArr(this.mallsetting.sys_config));
                this.$refs.mallsetting.validate((valid) => {
                    if (valid) {
                        axiosRequest('admin/system/updateSysConfig',{'sysConfigList':objToArr(this.mallsetting.sys_config)},'post').then((res)=>{
                            this.$message.success('保存成功！');
							
                        })        
                        
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            // loding图上传成功
            loadingSuccess(file){
                this.loading_page_path=""
                this.mallsetting.loading_page_path=file.data.url
            },
            //文件上传
            logoSuccess(file) {
                this.mallsetting.shopping_logo=file.data.url
            },
            //删除load图
            loadingRemove(file) {
                this.$refs.uploadLoad.clearFiles();
                this.mallsetting.loading_page_path=""
            },
            //删除logo图
            logoRemove(file) {
                this.$refs.uploadLogo.clearFiles();
                this.mallsetting.shopping_logo=""
            },
            //删除load默认图片
            delDefaultLoad(){
                this.loading_page_path=""
            },
            //删除logo默认图片
            delDefaultLogo(){
                this.shopping_logo=""
            },
            //图片放大
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file;
                this.dialogVisible = true;
            },

            

        },
        

    }
</script>
<style scoped>
   
    .mallSetting{
        width: 100%;
    }
    .mallSetting .el-input{
        width: 40%;
    }

 
</style>
