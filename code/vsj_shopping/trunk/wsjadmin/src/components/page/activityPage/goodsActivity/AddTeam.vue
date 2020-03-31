<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 发起拼团活动</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box addSeckillForm">
                <el-form ref="condition" :model="condition" label-width="160px" label-position="left">
                    <el-form-item label="拼团活动名称" class="seckillTitle">
                        <el-input v-model="condition.activityName" placeholder="请输入拼团活动名称"></el-input>
                    </el-form-item>
                    <el-form-item label="拼团商品">
                        <el-button type="primary" plain @click="changeEdit">{{condition.productName}}</el-button>
                    </el-form-item>
                    <el-form-item label="活动时间" class="mt20">
                        <el-date-picker v-model="queryTime" type="datetimerange" 
                        :disabled="seckillAbled"
                        range-separator="至" 
                        start-placeholder="开始日期" end-placeholder="结束日期" 
                        align="right">
                        </el-date-picker>
                    </el-form-item>
                    <el-table :data="condition.vsjActivityGroup"  border  class="mt20">
                        <el-table-column prop="attrJson2" label="规格" width="160" ></el-table-column>
                        <el-table-column prop="productStock" label="库存" width="120" ></el-table-column>
                        <el-table-column prop='productNumber' label="参与活动数量" width="160">
                        <template scope="scope">
                            <el-input size="small" v-show="true" v-model="scope.row.productNumber"  
                            placeholder="请输入参与活动的商品数量" 
                            :disabled="seckillAbled"></el-input>
                        </template>
                        </el-table-column>
                        <el-table-column peop='quotaNumber' label="同一用户限购数量">
                            <template scope="scope">
                                <el-input
                                size="small"
                                v-model="scope.row.quotaNumber"
                                placeholder="请输入限购数量" 
                                :disabled="seckillAbled"
                                ></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column label="成团人数">
                            <template scope="scope">
                                <el-input size="small"  v-model="scope.row.groupNumber" placeholder="请输入成团人数"></el-input>
                            </template>
                        </el-table-column> 
                        <!-- <el-table-column label="发起团数">
                            <template scope="scope">
                                <el-input size="small"  v-model="scope.row.name" placeholder="请输入发起团数"></el-input>
                            </template>
                        </el-table-column>                      -->
                        <el-table-column prop="productPrice" label="原售价" width="160" ></el-table-column>
                        <el-table-column prop='activityPrice' label="拼团价">
                            <template scope="scope">
                                <el-input size="small"  v-model="scope.row.activityPrice" placeholder="请输入拼团价"></el-input>
                            </template>
                        </el-table-column>                        
                    </el-table>

                    <el-form-item class="mt20">
                        <el-button type="primary" @click="onSubmit">保存设置</el-button>
                        <el-button @click="cancel">返回</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    <!-- 选择商品组件 -->
        <vChoosegood :isSingle=true :chooseIdArr='chooseGoods' v-if='editVisible' @saveChoose='saveChoose' @closeChoose='closeChoose'></vChoosegood>
    </div>
</template>

<script>
    import { mallSetting } from '@/api/index';
    import { axiosRequest } from "../../../../api";
    import { objToArr ,formatDate} from "@/components/common/common";
    import vChoosegood from "@/components/common/chooseGood"
    export default {
        name: 'baseform',
        components:{
            vChoosegood
        },
        data: function(){
            return {
                condition:{
                    activityName: "",
                    endTime: "",
                    startTime: "",
                    productId: '',
                    productName: "选择商品",
                    activityType:'0',
                    sysUserId:localStorage.getItem('userId'),
                    nickName:localStorage.getItem('nickName'),
                    vsjActivityGroup: []
                },
                seckillAbled:false,
                specList:[],
                queryTime: [],
                editVisible:false,
                chooseGoods:[]
            }
        },
        created(){
            this.getDetail()
        },
        methods: {
            //获取活动详情
            getDetail(){
                if(sessionStorage.getItem('activeId')){   //编辑以及查看活动详情
                    axiosRequest('activity/getActivityById',{
                    id:sessionStorage.getItem('activeId'),
                    type:'0'
                    },'post').then((res)=>{
                        console.log(res)
                        this.condition=res
                        if(this.condition.activityStatus!=0){
                            this.seckillAbled=true
                        }
                        let time2=[]
                        time2[0]=new Date(this.condition.startTime)
                        time2[1]=new Date(this.condition.endTime)
                        this.queryTime=time2

                        this.condition.vsjActivityGroup=res.vsjActivityGroup
                        this.condition.vsjActivityGroup.forEach(item=>{
                            item.attrJson2=item.attrJson.replace(/[&\|\\\*^%$#@,{}:'\-]/g,"")
                            item.specsId=item.id
                        })
                    })
                }
            },
            changeEdit(){
                if(this.condition.productId!=""){
                    this.chooseGoods[0]=this.condition.productId
                }
                this.editVisible=true
                },
                closeChoose(){
                    this.editVisible=false
                },
                saveChoose(id,name){
                    this.editVisible=false
                    this.chooseGoods=id
                    this.condition.productId=this.chooseGoods[0]
                    this.condition.productName=name
                    this.goodsSpecs()
                },
            //选择商品后  查询商品规格
            goodsSpecs() {
                axiosRequest("activity/getSpecsList", {
                    'id':this.condition.productId
                }, "post").then(res => {
                    // productNumber	number	参加活动的数量
                    // quotaNumber	number	限购数量
                    // groupNumber	number	成团人数
                    // activityPrice	number	活动价格
                    // specsId	number	规格ID
                    // productPrice	number	实际销售价格
                    // productStock	number	库存
                    // attrJson	String	规格参数
                    this.condition.vsjActivityGroup=res
                    this.condition.vsjActivityGroup.forEach(item=>{
                        item.attrJson2=item.attrJson.replace(/[&\|\\\*^%$#@,{}:'\-]/g,"")
                        item.specsId=item.id
                        // item.quotaNumber="1";
                        // item.productNumber='200';
                        // item.activityPrice="12";
                        // item.groupNumber='20'
                    })
                    
                });
            },

            //保存
            onSubmit() {
            this.condition.startTime =formatDate(this.queryTime[0]);
            this.condition.endTime = formatDate(this.queryTime[1]);
            console.log(this.condition)
            if(sessionStorage.getItem('activeId')){
                axiosRequest("activity/updateActivityAll", this.condition, "post").then(
                res => {
                    this.$message.success("活动修改成功！");
                }
                );
            }else{
                axiosRequest("activity/insertActivity", this.condition, "post").then(
                    res => {
                        this.$message.success("活动创建成功！");
                    }
                );
            }
            
            },
            cancel() {
                this.$router.go(-1);
            }
            
        },
        

    }
</script>
<style scoped>
    .mt20{
        margin-top: 20px;
    }
    .seckillTitle .el-input{
        width: 19.7%;
    }
    .addSeckillForm{
        width: 100%;
    }
    .red {
    color: #ff0000;
    }
</style>