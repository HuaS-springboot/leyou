<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 商城装修</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box mallSetting">
                <el-form :model="mallsetting" :rules="rules" ref="mallsetting" label-width="160px" label-position="left">
                    <el-form-item label="是否开启启动页" >
                        <el-radio-group v-model="mallsetting.loading_page_switch">
                            <el-radio label="1">开启</el-radio>
                            <el-radio label="0">关闭</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="启动页图片" v-show="mallsetting.loading_page_switch==1">
                        <div class="el-upload-list el-upload-list--picture-card uploadCom" v-if="loading_page_path!=''">
                            <div tabindex="0" class="el-upload-list__item is-success">
                                <div data-v-452ce00c="">
                                    <img data-v-452ce00c="" :src="mallsetting.loading_page_path" alt="" class="el-upload-list__item-thumbnail">
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
                        action="http://172.16.0.231:8751/api/v1/upload/uploadFile"
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
                    <el-form-item label="商城名称" prop="shopping_name">
                        <el-input v-model="mallsetting.shopping_name"></el-input>
                    </el-form-item>
                    <el-form-item label="商城logo">
                        <div class="el-upload-list el-upload-list--picture-card uploadCom" v-if="shopping_logo!=''">
                            <div tabindex="0" class="el-upload-list__item is-success">
                                <div data-v-452ce00c="">
                                    <img data-v-452ce00c="" :src="mallsetting.shopping_logo" alt="" class="el-upload-list__item-thumbnail">
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
                        action="http://172.16.0.231:8751/api/v1/upload/uploadFile"
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
                    <el-form-item label="首页是否显示分类导航">
                        <el-radio-group v-model="mallsetting.show_navigation_menu">
                            <el-radio label="1">开启</el-radio>
                            <el-radio label="0">关闭</el-radio>
                        </el-radio-group>
                    </el-form-item>

                    <el-form-item>
                        <el-button type="primary" @click="onSubmit(mallsetting)">保存设置</el-button>
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
                    'loading_page_path':'',
                    'shopping_name':'',
                    'shopping_logo':'',
                    'show_navigation_menu':1
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
            this.getMallSetting()
        },
        methods: {
            //获取商城配置
            getMallSetting(){
                axiosRequest('system/query',{
                    type:"1",
                    time:new Date()
                },'post').then((res)=>{
                    this.mallsetting=arrToObj(res) 
                    this.loading_page_path=this.mallsetting.loading_page_path
                    this.shopping_logo=this.mallsetting.shopping_logo
                })
            },
            handleExceed(files, fileList) {
                this.$message.warning(`图片最多上传一张`);
            },
            //保存商城配置
            onSubmit(mallsetting) { 
                this.$refs.mallsetting.validate((valid) => {
                    if (valid) {
                        axiosRequest('system/update',{'vsjSysConfigList':objToArr(this.mallsetting)},'post').then((res)=>{
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
