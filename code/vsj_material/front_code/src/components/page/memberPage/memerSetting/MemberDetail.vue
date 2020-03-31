<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 会员详情</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box" style="width:100%">
                <el-form ref="form" :model="memberInfo" label-width="120px" label-position="left">
                    <!-- <div class="titleTag">订单详情</div> -->
                    <el-form-item label="头像" class="wid50">
                        <el-input v-model="memberInfo.nickName" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="会员名称" class="wid50">
                        <el-input v-model="memberInfo.nickName" ></el-input>
                    </el-form-item>
                    <el-form-item label="会员上级" class="wid50">
                        <el-input v-model="memberInfo.parentName" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="会员上级id" class="wid50">
                        <el-input v-model="memberInfo.parentId" ></el-input>
                    </el-form-item>
                    <el-form-item label="加入时间" class="wid50" >
                        <el-input v-model="memberInfo.joinTime" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="手机号码" class="wid50">
                        <el-input v-model="memberInfo.telPhone" ></el-input>
                    </el-form-item>
                    <el-form-item label="会员等级" class="wid50">
                        <el-select v-model="memberInfo.levelName" placeholder="等级" class="mr10 handle-select" @change="levelChange">
                            <el-option
                            v-for="item in options"
                            :key="item.label"
                            :label="item.label"
                            :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="完成订单数" class="wid50" >
                        <el-input v-model="memberDetail.completOrder" size="medium" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="完成订单金额" class="wid50" >
                        <el-input v-model="memberDetail.completOrderResults" :disabled="true" ></el-input>
                    </el-form-item>
                    <el-form-item label="余额" class="wid50">
                        <el-input v-model="memberInfo.carryBalance"></el-input>
                    </el-form-item>
                    <el-form-item label="总提成" class="wid50">
                        <el-input v-model="memberDetail.totalWages"  :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="已结算提成" class="wid50">
                        <el-input v-model="memberDetail.settledWages" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="未结算提成" class="wid50">
                        <el-input v-model="memberDetail.noSettledWages" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="团队总人数" class="wid50">
                        <el-input v-model="memberDetail.lowerLevelCount" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="团队总业绩" class="wid50">
                        <el-input v-model="memberDetail.allTeamResults" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="一级团队人数" class="wid50">
                        <el-input v-model="memberDetail.firstLowerCount" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="一级团队业绩" class="wid50">
                        <el-input v-model="memberDetail.firstTeamResults" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="一二级团队人数" class="wid50">
                        <el-input v-model="memberDetail.secondLowerCount" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="一二级团队业绩" class="wid50">
                        <el-input v-model="memberDetail.secondTeamResults" :disabled="true"></el-input>
                    </el-form-item>

                    <el-form-item label="备注" class="wid50">
                        <el-input type="textarea" v-model="memberInfo.remark"></el-input>
                    </el-form-item>

                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">保存修改</el-button>
                        <el-button>取消</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>

    </div>
</template>

<script>
    import {axiosRequest} from '../../../../api/index'
    export default {
        data: function(){
            return {
               memberInfo : {
               },
               memberDetail:{

               },
               levelId:'',
               options: [],

            }
        },
        created() {
            this.memberInfo = JSON.parse(sessionStorage.getItem('memberInfo'));
            this.levelId=this,memberInfo.levelId
            this.queryMemberDetail();
            this.getLevel();
        },
        methods: {
            //保存详情修改
            onSubmit() {
                axiosRequest('memeber/editMemberDetail',{
                    'id':this.memberInfo.id,
                    'regId':this.memberInfo.regId,
                    'name':this.memberInfo.nickName,
                    'carryBalance':this.memberInfo.carryBalance,
                    'telPhone':this.memberInfo.telPhone,
                    'remark':this.memberInfo.remark,
                    'parentId':this.memberInfo.parentId,
                    'leverId':this.leverId
                },'post').then((res)=>{
                    this.$message.success('修改成功');
                })
                
            },
            queryMemberDetail(){
                axiosRequest('memeber/memberDetail',{id:this.memberInfo.id},'POST').then((res)=>{
                    this.memberDetail = res;
                })
            },
            //获取用户等级
            getLevel(){
                axiosRequest('memeber/findAllLevel','','POST').then((res)=>{
                    for(var i=0;i<res.length;i++){
                        this.options.push( {
                            value:res[i].id,
                            label:res[i].levelName
                        });
                    }
                })
            },
            //等级变化
            levelChange(value){
                this.levelId=value
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
    .wid50{
        width: 40%;
        margin-right: 10%; 
        display: inline-block;    
    }   
</style>
