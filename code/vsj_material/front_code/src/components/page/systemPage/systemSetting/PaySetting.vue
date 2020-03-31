<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 支付参数</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box">
                <el-form ref="mallsetting" :model="mallsetting" :rules="rules" label-width="160px" label-position="left">
                    <div class="titleTag">微信支付</div>
                    <el-form-item label="appid" prop="wx_pay_appid">
                        <el-input v-model="mallsetting.wx_pay_appid"></el-input>
                    </el-form-item>
                    <el-form-item label="appSecret" prop="wx_pay_appsecret">
                        <el-input v-model="mallsetting.wx_pay_appsecret"></el-input>
                    </el-form-item>
                    <el-form-item label="mchid" prop="wx_pay_mchid">
                        <el-input v-model="mallsetting.wx_pay_mchid"></el-input>
                    </el-form-item>
                    <el-form-item label="apiSecret" prop="wx_pay_apisecret">
                        <el-input v-model="mallsetting.wx_pay_apisecret"></el-input>
                    </el-form-item>
                    <el-form-item label="CERT证书文件" >
                        <el-upload
                            action="http://172.16.0.231:8751/api/v1/upload/uploadFile"
                            :limit="1"
                            :headers="headers"
                            :on-exceed="handleExceed"
                            :on-success="certSuccess">
                            <el-button size="small" type="primary">点击上传</el-button>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="KEY密钥文件">
                        <el-upload
                            action="http://172.16.0.231:8751/api/v1/upload/uploadFile"
                            :limit="1"
                            :headers="headers"
                            :on-exceed="handleExceed"
                            :on-success="keyfileSuccess">
                            <el-button size="small" type="primary">点击上传</el-button>
                        </el-upload>
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
    import {rules} from '../../../common/rule'
    export default {
        name: 'baseform',
        data: function(){
            return {
                mallsetting:{},
                rules:rules.paySetting,
                fileList: [],
                headers:{
                    "token":localStorage.getItem('token'),
                    "userId":localStorage.getItem("userId"),
                    "platformCode":this.$store.state.platformCode
                }, 
            }
        },
        created(){
            this.getMallSetting()
        },
        methods: {
            //获取商城配置
            getMallSetting(){
                axiosRequest('system/query',{
                    type:"3",
                },'post').then((res)=>{
                    this.mallsetting=arrToObj(res) 
                })
            },
//          onSubmit() {
//              console.log(this.$refs)
//              this.$refs.mallsetting.validate((valid)=>{
//                  if(valid){
//                       axiosRequest( 'system/update',{'vsjSysConfigList':objToArr(this.mallsetting)},'post').then((res)=>{
//                           this.$message.success('提交成功！');
//                      })        
//                      
//                  }else{
//                      console.log('error submit!!');
//                      return false;
//                  }
//              })
//              saveMallSetting({
//
//              })
//              this.$message.success('提交成功！');
//          },
            // 文件上传成功
            certSuccess(file){
                this.mallsetting.wx_pay_cert_file=file.data.url
               
            },
            keyfileSuccess(file){
                this.mallsetting.wx_pay_key_file=file.data.url
               
            },
            handleExceed(files, fileList) {
                this.$message.warning(`证书文件最多上传一份`);
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
