<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 关系设置</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box relationSetting">
                
                <el-form ref="form" :model="form" label-width="160px" label-position="left">
                    <el-form-item label="开启会员上下级关系">
                        <el-radio-group v-model="form.relationSwitch">
                            <el-radio :label="0" >关闭</el-radio>
                            <el-radio :label="1" >开启</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <div class="titleTag">成为推广人条件</div>
                    <ul class="conditionUl">
                        <li>
                            <el-checkbox v-model="needRequireSwitch" label="复选框 A" >启用</el-checkbox>
                            <label>无条件</label> 
                            
                        </li>
                        <li>
                            <el-checkbox v-model="bindPhoneSwitch" label="复选框 A" >启用</el-checkbox>
                            <label>绑定手机号</label>
                            
                        </li>
                        
                        <li class="mt20">
                            <el-checkbox v-model="expenseMoneySwitch" label="复选框 A" >启用</el-checkbox>
                            <label>消费金额(￥)</label> <el-input v-model="form.expenseMoney" placeholder="请输入达标消费金额"></el-input>
                            
                        </li>
                        <li class="mt20">
                            <el-checkbox  v-model="expenseNumSwitch" label="复选框 A" >启用</el-checkbox>
                            <label>消费笔数</label><el-input v-model="form.expenseNum" placeholder="请输入达标消费笔数"></el-input>
                        </li>

                        <li class="mt20">
                            <el-checkbox label="复选框 A" v-model="productIdsSwitch">启用</el-checkbox>
                            
                            <label for="input2" >商品消费笔数</label><el-input v-model="form.productNum" placeholder="请输入达标消费笔数"></el-input>
                            <label for="cascader">商品种类</label>   
                            <el-button @click="changeEdit" >修改商品</el-button>
                            
                            
                        </li>
                    </ul>
                    <div class="titleTag mt20">成为下线条件</div>
                    <el-form-item label=" ">
                        <el-radio-group v-model="form.offlineConditions">
                            <el-radio :label="1">首次进入商城</el-radio>
                            <el-radio :label="2">首次下单</el-radio>
                            <el-radio :label="3">首次付款后</el-radio>
                        </el-radio-group>
                    </el-form-item>
                                    
                    <el-form-item class="mt20">
                        <el-button type="primary" @click="saveData()">保存设置</el-button>
                        <el-button>取消</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
        <!-- 选择商品组件 -->
        <vChoosegood :chooseIdArr='chooseGoods' v-if='editVisible' @saveChoose='saveChoose' @closeChoose='closeChoose'></vChoosegood>
    </div>
</template>
<script>
    import {axiosRequest} from '../../../../api/index'
    
    import vChoosegood from "@/components/common/chooseGood"
    export default {
        name: 'baseform',
        components:{
            vChoosegood
        },
        data: function(){
            return {
                editVisible:false,
                condition:{
                    
                },
                form: {
                    id:'',
                    relationSwitch: 0,
                    needRequire: '',
                    bindPhone: '',
                    expenseMoney: '',
                    expenseNum: '',
                    productIds: '',
                    productNum: '',
                    offlineConditions:''
                },
                tableData:[],
                chooseGoods:[],
                chooseMes:'选择商品',
                bindPhoneSwitch:false,
                needRequireSwitch:false,
                expenseMoneySwitch:false,
                expenseNumSwitch:false,
                productIdsSwitch:false,
            }
        },
        created(){
            this.getData()
        },
        methods: {
            getData(){
                axiosRequest('memeber/findMemberRelation','','POST').then((res)=>{
                    this.form = res;
                    if(this.form.expenseMoney > 0){
                        this.expenseMoneySwitch = true
                    }
                    if(this.form.expenseNum > 0){
                        this.expenseNumSwitch = true
                    }
                    if(this.form.productIds != null && this.productIds != ''){
                        this.productIdsSwitch = true
                    }
                    if(this.form.bindPhone > 0){
                        this.bindPhoneSwitch = true;
                    }
                    if(this.form.needRequire > 0){
                        this.needRequireSwitch = true;
                    }
                    this.chooseGoods=res.productIds.split(',')
                    
                })
            },
            
            changeEdit(){
                this.editVisible=true
            },
            closeChoose(){
                this.editVisible=false
            },
            saveChoose(value){
                this.editVisible=false
                this.chooseGoods=value
            },
            saveData(){
                if(!this.productIdsSwitch){
                    this.form.productIds = null;
                }
                if(!this.expenseNumSwitch){
                    this.form.expenseNum = -1;
                }
                if(!this.expenseMoneySwitch){
                    this.form.expenseMoney = -1;
                }
                this.form.bindPhone = this.bindPhoneSwitch ? 1 : 0;
                this.form.needRequire = this.needRequireSwitch ? 1 : 0;
                this.form.productIds=this.chooseGoods.join(',')
                axiosRequest('memeber/updateMemberRelation',this.form,'POST').then((res)=>{
                    this.getData();
                })
            }

            
        },
        

    }
</script>
<style scoped>
    .relationSetting{
        width:100%;
    }
    .mr10{
        margin-right: 10px;
    }
    .mr20{
        margin-right: 20px;
    }
    .mr60{
        margin-right: 60px; 
    }
    .ml30{
        margin-left: 30%;
    }
    .ml15px{
        margin-left: 15px;
    }
    .mt20{
        margin-top: 20px;
    }
    .titleTag{
        font-size: 17px;
        padding: 8px 0;
        color: #409EFF; 
    }
    .conditionUl{
        width: 100%;
        display: flex;
        flex-wrap: wrap;
    }
    li{
        list-style: none;
    }
    .conditionUl .el-checkbox{
        margin: 0;
    }
    .conditionUl>li{
        width: 100%;
    }
    .conditionUl>li .el-input{
        width: 20%;
        float: left;
    }
    .conditionUl>li .goodsList{
        width:20%;
        float: left;
    }
    .conditionUl>li .goodsList>.el-input{
        width: 100%;
    }
    .conditionUl>li label{
        float: left;
        width: 10%;
        font-size: 14px;
        color: #606266;
        text-align: center;
        line-height: 32px;
    }
</style>
