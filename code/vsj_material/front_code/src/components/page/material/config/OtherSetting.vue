<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 支付参数</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box">
                <el-form ref="mallsetting" :model="mallsetting" label-width="160px" label-position="left">
                    <!--<div class="titleTag">快递服务</div>
                    <el-form-item label="快递商家" >
                         <el-select v-model="sys_kd_api" placeholder="请选择">
                            <el-option
                            v-for="item in expressApi"
                            :key="item.value"
                            :value="item.key">
                            </el-option>
                        </el-select>
                    </el-form-item>-->
                    <!--<el-form-item label="快递鸟用户id" v-show="sys_kd_api=='快递鸟'">
                        <el-input v-model="mallsetting.sys_kdn_user_id"></el-input>
                    </el-form-item>
                    <el-form-item label="快递鸟api key" v-show="sys_kd_api=='快递鸟'">
                        <el-input v-model="mallsetting.sys_kdn_api_key"></el-input>
                    </el-form-item>-->
                    <div class="titleTag">阿里云短信服务</div>
                    <el-form-item label="AccessKeyId">
                        <el-input v-model="mallsetting.other_ali_message_accesskeyid"></el-input>
                    </el-form-item>
                    <el-form-item label="AccessKeySecret  ">
                        <el-input v-model="mallsetting.other_ali_message_accesssecret"></el-input>
                    </el-form-item>
                    <el-form-item label="短信签名">
                        <el-input v-model="mallsetting.other_message_signature"></el-input>
                    </el-form-item>
                    <el-form-item label="注册模板编号">
                        <el-input v-model="mallsetting.other_register_no"></el-input>
                    </el-form-item>
                    <el-form-item label="找回密码模板编号">
                        <el-input v-model="mallsetting.other_retrieve_no"></el-input>
                    </el-form-item>
                    <div class="titleTag">远程文件配置(图片存储)</div>
                    <el-form-item label="图片存储服务商" >
                         <el-select v-model="remote_files" placeholder="请选择">
                            <el-option
                            v-for="item in file_storage"
                            :key="item.value"                            
                            :value="item.key">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="配置图片存储商信息" v-show="remote_files!=''">
                        <el-radio-group v-model="mallsetting.oss_option"  v-if="mallsetting.oss_option!=1">
                            <el-radio label="0">信息配置</el-radio>
                            <el-radio disabled label="1">官方提供(请联系管理员购买)</el-radio>
                        </el-radio-group>
                        <el-radio-group v-model="mallsetting.oss_option"  v-if="mallsetting.oss_option==1">
                            <el-radio label="0" disabled>信息配置</el-radio>
                            <el-radio label="1">官方提供(请联系管理员购买)</el-radio>
                        </el-radio-group>
                        
                    </el-form-item>
                    <el-form-item label="Access Key ID" v-if="mallsetting.oss_option==0  && remote_files=='阿里云OSS'">
                        <el-input v-model="mallsetting.access_key_id"></el-input>
                    </el-form-item>
                    <el-form-item label="Access Key Secret" v-if="mallsetting.oss_option==0&& remote_files=='阿里云OSS'" >
                        <el-input v-model="mallsetting.access_key_secret"></el-input>
                    </el-form-item>
                    <el-form-item label="Bucket名称" v-if="mallsetting.oss_option==0 && remote_files=='阿里云OSS'">
                        <el-input v-model="mallsetting.bucket_name"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">提交参数</el-button>
                        <el-button>取消</el-button>
                    </el-form-item>

                </el-form>
            </div>
        </div>

    </div>
</template>

<script>
    import { axiosRequest } from '@/api/index';
    import { arrToObj,objToArr } from '@/components/common/common';
    export default {
        name: 'baseform',
        data: function(){
            return {
                mallsetting:{},
                mallsetting2:[],
//              expressApi:'',// objToArr(JSON.parse(localStorage.getItem('dictionary')).express_api),
                fileStorageApi:objToArr(JSON.parse(localStorage.getItem('dictionary')).file_storage),
//              sys_kd_api:'',
                remote_files:''
            }
        },
        created(){
            this.getMallSetting()
        },
        methods: {
            //获取商城配置
            getMallSetting(){
                axiosRequest('admin/system/getSysConfigList',{
                    type:"4",
                },'post').then((res)=>{
                	
                    this.mallsetting=arrToObj(res) 
                    //设置两个选择器的默认选项
//                  if(this.mallsetting.sys_kd_api==0){
//                      this.mallsetting.sys_kd_api=""
//                  }else{
//                      this.sys_kd_api=this.expressApi[0].key
//                  }
                    if(this.mallsetting.remote_files==0){
                        this.remote_files=""
                    }else{
                        this.remote_files=this.fileStorageApi[0].key
                    }
//                  
                })
            },
            onSubmit() {
                console.log(this.mallsetting)
//              if(this.sys_kd_api==''){
//                  this.mallsetting.sys_kd_api=0
//              }else if(this.sys_kd_api=='快递鸟'){
//                  this.mallsetting.sys_kd_api=1
//              }
                if(this.remote_files==''){
                    this.mallsetting.remote_files=0
                }else if(this.remote_files=='阿里云OSS'){
                    this.mallsetting.remote_files=1
                }
                axiosRequest( 'admin/system/updateSysConfig',{'sysConfigList':objToArr(this.mallsetting)},'post').then((res)=>{
                    this.$message.success('提交成功！');
                })        
                
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
