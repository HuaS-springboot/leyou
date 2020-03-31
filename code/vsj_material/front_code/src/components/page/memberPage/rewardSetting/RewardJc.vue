<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 经销商极差模式</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box">
                <el-form ref="formData[0]"  :model="formData[0]" label-width="160px" label-position="left">
                    <el-form-item label="奖励提成单位">
                        <el-radio-group v-model="formData[0].bonusUnits">
                            <el-radio :label='1'>按固定金额(￥)</el-radio>
                            <el-radio :label='0'>按固定比例(%)</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-form>
                <el-table :data="formData"  border  class="mt20">
                    <el-table-column prop="levelName" label="经销商等级" width="240" >
                    </el-table-column>
                    <el-table-column label="提成比例">
                        <template>
                            <el-input size="small"  v-model="formData[0].bonusNum" placeholder="请输入百分比或者固定金额"></el-input>
                        </template>
                    </el-table-column>
                    
                    
                </el-table>
                <el-form ref="formData[0]"  :model="formData[0]" label-width="160px" label-position="left" class="mt20">
                    <el-form-item label="经销商平级奖励">
                        <el-radio-group v-model="formData[0].openPeers">
                            <el-radio :label='1'>开启</el-radio>
                            <el-radio :label='0'>关闭</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="平级奖励模式" v-show='formData[0].openPeers==1'>
                        <el-radio-group v-model="formData[0].perrsUnits">
                            <el-radio :label='1'>按固定金额(￥)</el-radio>
                            <el-radio :label='0'>按固定比例(%)</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-table :data="formData"  border  class="mt20" v-show='formData[0].openPeers==1'>
                        <el-table-column prop="levelName" label="经销商等级" width="240" >

                        </el-table-column>
                        <el-table-column label="提成比例">
                            <template scope="scope">
                                <label for="">奖励层级</label>
                                <el-input size="small"  v-model="scope.row.peersHierarchy" placeholder="请输入奖励层级"></el-input>
                                <label for="">金额</label>
                                <el-input size="small"  v-model="scope.row.peersNum" placeholder="请输入百分比或者固定金额"></el-input>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-form-item class="mt20">
                        <el-button type="primary" @click="onSubmit">保存设置</el-button>
                        <el-button>取消</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>

    </div>
</template>
<script>
    import {axiosRequest} from '@/api/index'
    import {null2str} from '@/components/common/common'

    export default {
        name: 'baseform',
        data: function(){
            return {
            
                formData:[],
                form:{}
            }
        },
        created(){
            this.getJcDefault()
        },
        methods: {
            //查询
            getJcDefault(){
                axiosRequest('product/getStageSchemaList',{'productId':'0'},'post').then((res)=>{
                    //设置默认值
                    if(res[0].perrsUnits===null||res[0].perrsUnits===''){
                        res[0].perrsUnits=1
                    }
                    if(res[0].openPeers===null||res[0].openPeers===''){
                        res[0].openPeers=0
                    }
                    if(res[0].bonusUnits===null||res[0].bonusUnits===''){
                        res[0].bonusUnits=1
                    }
                    this.formData=null2str(res)
                    this.form=this.formData[0]
                })
            },
            onSubmit(){
                
                for(let i=1;i<this.formData.length;i++){
                    if(this.formData[i].perrsUnits===null||this.formData[i].perrsUnits===''){
                        this.formData[i].perrsUnits=1
                    }
                    if(this.formData[i].openPeers===null||this.formData[i].openPeers===''){
                        this.formData[i].openPeers=0
                    }
                    if(this.formData[i].bonusUnits===null||this.formData[i].bonusUnits===''){
                        this.formData[i].bonusUnits=1
                    } 
                    this.formData[i].productId=0
                }
                console.log(this.formData)
                axiosRequest('product/insertStageSchema',{
                    'isCommission':null,
                    'isDealer':null,
                    'productId':0,
                    'schemaRequestList':this.formData
                    },'post').then((res)=>{
                        this.$message.success('提交成功！');
                })
                
            },
        },
    }
</script>
<style scoped>
    .mt20{
        margin-top: 20px;
    }
</style>